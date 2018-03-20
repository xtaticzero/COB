package mx.gob.sat.siat.cob.background.service.multa.impl;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import mx.gob.sat.siat.cob.background.holder.CargaArchivosHolder;
import mx.gob.sat.siat.cob.background.holder.HolderCargaMasivaMap;
import mx.gob.sat.siat.cob.background.service.job.impl.GenerarMultaCreditoThreadServiceImpl;
import mx.gob.sat.siat.cob.background.service.multa.GenerarMulta;
import mx.gob.sat.siat.cob.background.service.multa.ValidaDatosService;
import mx.gob.sat.siat.cob.background.util.UtileriasBackground;
import mx.gob.sat.siat.cob.seguimiento.dao.arca.MonitorArchivoArcaDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.EstadoAdmonLocalMATDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.MonitorArchivoArca;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.EstadoAdmonLocalMAT;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VigilanciaAdministracionLocal;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EsMultaEnum;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.service.catalogos.FuncionarioService;
import mx.gob.sat.siat.cob.seguimiento.service.extraccion.VigilanciaAdministracionLocalService;
import mx.gob.sat.siat.cob.seguimiento.service.extraccion.VigilanciaService;
import mx.gob.sat.siat.cob.seguimiento.service.otros.PlantillaArcaService;
import mx.gob.sat.siat.cob.seguimiento.util.PoolThread;
import mx.gob.sat.siat.cob.seguimiento.util.Utilerias;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;

import org.apache.log4j.Logger;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service("generarMulta")
public class GenerarMultaImpl implements GenerarMulta {

    private Logger log = Logger.getLogger(GenerarMultaImpl.class);
    @Autowired
    @Qualifier("jobLauncher")
    private JobLauncher launcher;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired(required = false)
    @Qualifier("generarCreditoJob")
    private Job generarCreditoJob;
    @Autowired
    private VigilanciaAdministracionLocalService vigilanciaLocalService;
    @Autowired
    private PlantillaArcaService plantillaArcaService;
    @Autowired
    private FuncionarioService funcionarioService;
    @Autowired
    private CargaArchivosHolder holder;
    @Autowired
    private HolderCargaMasivaMap holderCargaMasiva;
    @Autowired
    private ValidaDatosService validaDatosService;
    @Autowired
    private MonitorArchivoArcaDAO monitorDAO;
    @Autowired
    private EstadoAdmonLocalMATDAO alRegistroCbz;
    @Autowired
    private VigilanciaService vigilanciaService;

    @Override
    public void generarMulta() throws SGTServiceException {
        try {
            log.info("###  Inicia el proceso Generar Multas ... ");
            log.info("###  Intento 27 marzo ... ");
            /**
             * Valida que los datos enviados a ARCA sean correctos.
             */
            validacionDeDatos();
            /**
             * Reinicia el Holder, elimina la informacion en las tablas para el
             * proceso y elimina del fileSystem los archivos generados pr el
             * proceso.
             */
            limpiarHolder();
            eliminarInformacionTablasBatch();
            eliminaArchivos();
            /**
             * Recupera la vigilancia a ejecutar.
             */
            VigilanciaAdministracionLocal vigilancia = obtenerIdVigilancia();
            /**
             * Ejecuta el proceso encargado del envio de multas a ARCA.
             */
            if (vigilancia != null) {
                /**
                 * Determina tipoFirma de la vigilancia.
                 */
                Map<String, String> referencias = parametrosSQL(vigilancia);
                /**
                 * Obtiene las locales de la vigilancia anteriormente obtenida.
                 */
                List<VigilanciaAdministracionLocal> vigilanciasLocales =
                        obtenerAdministracionLocalMultaXIdVigilanica(vigilancia.getIdVigilancia());
                /**
                 * Registrar datos de monitoreo.
                 */
                registrarDatosMonitoreo(vigilanciasLocales);
                /**
                 * Ejecuta Proceso que genera multas.
                 */
                ejecutaJobGenerarMultas(vigilanciasLocales, referencias.get("camposFirmaDigital"), referencias.get("joinFirmaDigital"));
            }
        } catch (SGTServiceException ex) {
            log.error(ex);
            throw new SGTServiceException(ex);
        }
    }

    /**
     * Ejecuta el proceso de envio de datos a ARCA.
     */
    private void ejecutaJobGenerarMultas(List<VigilanciaAdministracionLocal> vigilanciasLocales,
            String camposFirmaDigital, String joinFirmaDigital)
            throws SGTServiceException {
        try {
            Properties propertiesHilos = UtileriasBackground.obtenProperties(ConstantsCatalogos.RUTA_THREADS);
            ExecutorService executor = Executors.newFixedThreadPool(
                    Integer.parseInt(propertiesHilos.getProperty("thread.cantidad")));

            guardaPlantillaBatch(EsMultaEnum.ES_MULTA);
            guardaNivelEmisionBatch();

            List<PoolThread> threads = new ArrayList<PoolThread>();
            for (VigilanciaAdministracionLocal vigilancia : vigilanciasLocales) {

                GenerarMultaCreditoThreadServiceImpl multaCredito = new GenerarMultaCreditoThreadServiceImpl();
                EstadoAdmonLocalMAT registroCobranza = alRegistroCbz.consultaPorIdAdmonLocal(vigilancia.getIdAdministracionLocal());
                if (registroCobranza != null) {

                    parametrosSQL(registroCobranza, multaCredito, vigilancia);

                    multaCredito.setVigilancia(vigilancia);
                    multaCredito.setVigilanciaService(vigilanciaLocalService);
                    multaCredito.setGenerarCreditoJob(generarCreditoJob);
                    multaCredito.setLauncher(launcher);
                    multaCredito.setCamposFirmaDigital(camposFirmaDigital);
                    multaCredito.setJoinFirmaDigital(joinFirmaDigital);
                    executor.execute(multaCredito);
                    threads.add(multaCredito);

                } else {
                    log.error("No se encuentra la local en sgtc_alregistrocbz ... " + vigilancia.getIdAdministracionLocal());
                }
            }
            Utilerias.terminaProceso(executor, threads);
            Utilerias.interrumpirProceso(executor);
            log.info("Fin Job Carga Multas.");
        } catch (InterruptedException ex) {
            log.error(ex);
            throw new SGTServiceException(ex);
        } catch (SeguimientoDAOException daoEx) {
            log.error(daoEx);
            throw new SGTServiceException(daoEx);
        }
    }

    /**
     * Valida que los datos enviados anteriormente a ARCA sean correctos, de no
     * ser asi, se eliminan los datos por vigilancia y local, para volver a
     * encolar el proceso en una posterior ejecucion
     *
     * Elimina el dato de monitoreo, una vez concluida la validacion de datos.
     *
     */
    private void validacionDeDatos() throws SGTServiceException {
        log.info("### Monitoreo de validacion de los datos");
        try {
            List<MonitorArchivoArca> monitores = monitorDAO.
                    consultarListaMonitorArchivoArca(ConstantsCatalogos.UNO);

            if (!monitores.isEmpty()) {
                for (MonitorArchivoArca monitor : monitores) {

                    log.info("###  Validacion de datos ...");
                    log.info("###  Hora Envio : " + monitor.getFechaEnvioArca());
                    log.info("###  Vigilancia : " + monitor.getIdVigilancia());
                    log.info("###  Locales : " + monitor.getIdAdmonLocal());

                    validaDatosService.eliminaDatosARCA(monitor);
                    monitorDAO.deleteMonitorArchivoArca(monitor.getIdVigilancia(),
                            monitor.getIdAdmonLocal(),
                            ConstantsCatalogos.UNO);
                }
            } else {
                log.info("### No hay datos para validar flujo ... ");
            }
        } catch (SeguimientoDAOException ex) {
            log.error(ex);
            throw new SGTServiceException(ex);
        }
    }

    /**
     * Obtiene la vigilancia a procesar.
     *
     * @return
     */
    private VigilanciaAdministracionLocal obtenerIdVigilancia()
            throws SGTServiceException {
        try {
            return vigilanciaLocalService.obtenerVigilanciaMultaBatch();
        } catch (SGTServiceException ex) {
            log.error(ex);
            throw ex;
        }
    }

    /**
     * Obtiene el listado de locales a ejecutar por IdVigilancia.
     *
     * @param numVigilancia
     * @return
     */
    private List<VigilanciaAdministracionLocal> obtenerAdministracionLocalMultaXIdVigilanica(Long numVigilancia)
            throws SGTServiceException {
        try {
            return vigilanciaLocalService.obtenerAdministracionLocalMultaXIdVigilanica(numVigilancia);
        } catch (SGTServiceException ex) {
            log.error(ex);
            throw ex;
        }
    }

    /**
     * Reinicia las instancias del holder.
     */
    private void limpiarHolder() {
        holder.reset();
        holderCargaMasiva.reset();
    }

    /**
     * Disminuye el universo de datos, y llena la tabla SGTT_PlantillaBACK con
     * la cual se hace referencia para el proceso.
     *
     * @param esMulta
     */
    private void guardaPlantillaBatch(EsMultaEnum esMulta) throws SGTServiceException {
        try {
            plantillaArcaService.guardaPlantillaArcaBatch(esMulta);
        } catch (SGTServiceException ex) {
            log.error(ex);
            throw ex;
        }
    }

    /**
     * Carga informacion en SGTA_AdmtvoNivlBK y SGTP_FuncionarioBK para la
     * ejecucion del proceso.
     */
    private void guardaNivelEmisionBatch() throws SGTServiceException {
        try {
            funcionarioService.guardaNivelEmision();
        } catch (SGTServiceException ex) {
            log.error(ex);
            throw ex;
        }
    }

    /**
     * Vacia las tablas llenadas para el proceso.
     */
    private void eliminarInformacionTablasBatch() throws SGTServiceException {
        try {
            plantillaArcaService.eliminaPlantillaArcaBatch();
            funcionarioService.eliminaNivelEmision();
        } catch (SGTServiceException ex) {
            log.error(ex);
            throw ex;
        }
    }

    /**
     * Elimina los archivos del fileSystem creados durante el proceso.
     */
    private boolean eliminaArchivos() throws SGTServiceException {
        boolean borrado = false;
        try {
            borrado = applicationContext.getResource(
                    ConstantsCatalogos.RUTA_ARCHIVO_DOCUMENTO_MULTA).getFile().delete();
            borrado &= applicationContext.getResource(
                    ConstantsCatalogos.RUTA_ARCHIVO_MULTA_MONTO_TOTAL).getFile().delete();
            borrado &= applicationContext.getResource(
                    ConstantsCatalogos.RUTA_ARCHIVO_OBLIGACION_PERIODO_MULTA).getFile().delete();
            borrado &= applicationContext.getResource(
                    ConstantsCatalogos.RUTA_ARCHIVO_ORIGEN_MULTA).getFile().delete();
            borrado &= applicationContext.getResource(
                    ConstantsCatalogos.RUTA_ARCHIVO_IDC_MULTA).getFile().delete();
        } catch (IOException ex) {
            log.error(ex);
            throw new SGTServiceException(ex);
        }
        return borrado;
    }

    /**
     * Inserta el registro de la informacion a ejecutar.
     */
    private void registrarDatosMonitoreo(List<VigilanciaAdministracionLocal> vigilanciasLocales) throws SGTServiceException {
        try {
            Date fechaActual = new Date();
            List<MonitorArchivoArca> monitores = new ArrayList<MonitorArchivoArca>();
            for (VigilanciaAdministracionLocal local : vigilanciasLocales) {
                MonitorArchivoArca monitorConsulta = monitorDAO.
                        consultarMonitorArchivoArca(local.getIdVigilancia(), local.getIdAdministracionLocal(),
                        ConstantsCatalogos.UNO);
                if (monitorConsulta == null) {
                    MonitorArchivoArca monitorSave = new MonitorArchivoArca();
                    monitorSave.setEsEnvioResolucion(ConstantsCatalogos.UNO);
                    monitorSave.setFechaEnvioArca(Utilerias.formatearFechaYYYYMMDDHHMMSS(fechaActual));
                    monitorSave.setIdVigilancia(local.getIdVigilancia());
                    monitorSave.setIdAdmonLocal(local.getIdAdministracionLocal());
                    monitorSave.setCantidadDocumentos(ConstantsCatalogos.CERO);
                    monitorSave.setCantidadObligacionPeriodo(ConstantsCatalogos.CERO);
                    monitorSave.setCantidadOrigenMulta(ConstantsCatalogos.CERO);
                    monitores.add(monitorSave);
                }
            }
            monitorDAO.insert(monitores);
        } catch (SeguimientoDAOException ex) {
            log.error(ex);
            throw new SGTServiceException(ex);
        } catch (ParseException ex) {
            log.error(ex);
            throw new SGTServiceException(ex);
        }
    }

    /**
     * Parametros que se envian dependiendo el tipo de operacion, esto se
     * refiere a si tomara en cuenta a cobranza para la ejecucion de la local.
     */
    private void parametrosSQL(EstadoAdmonLocalMAT registroCobranza,
            GenerarMultaCreditoThreadServiceImpl multaCredito, VigilanciaAdministracionLocal vigilancia) {
        StringBuilder campos = new StringBuilder();
        StringBuilder join = new StringBuilder();
        if (registroCobranza.getEsOperacionMAT().equals(ConstantsCatalogos.UNO)) {
            campos.append("ltCbz.IDPROCESO AS idProceso,\n")
                    .append("ltCbz.IDRESOLUCION AS idSubProceso,\n")
                    .append("ltCbz.IDCMLOTE AS idLote,\n");
            join.append(" join CBZT_RESOLUCION resCbz\n")
                    .append("  on resCbz.NUMRESOLUCIONDET = multaDocto.numeroResolucion\n")
                    .append("   and resCbz.ESCARGAMASIVA=1\n")
                    .append(" join CBZD_CMLOTE_RESOL ltCbz\n")
                    .append("  on ltCbz.IDRESOLUCION= resCbz.IDRESOLUCION\n")
                    .append(" join ADMC_UNIDADADMVA ua\n")
                    .append("  on resCbz.IDAUCONTROLADORA=UA.IDUNIDADADMVA\n")
                    .append("   and UA.CLAVE_SIR='").append(vigilancia.getIdAdministracionLocal()).append("'\n");
            multaCredito.setEsOperacionMat("1");
        } else {
            campos.append("null AS idProceso,\n").
                    append("null AS idSubProceso,\n").
                    append("null AS idLote,\n");
            join.append("");
            multaCredito.setEsOperacionMat("0");
        }
        multaCredito.setCamposCbz(campos.toString());
        multaCredito.setJoinCbz(join.toString());
    }

    private Map<String, String> parametrosSQL(VigilanciaAdministracionLocal vigilancia) {
        StringBuilder campos = new StringBuilder();
        StringBuilder join = new StringBuilder();
        Map<String, String> parametros = new HashMap<String, String>();
        try {
            Integer tipoFirma = vigilanciaService.consultaIdTipoFirma(vigilancia.getIdVigilancia());
            if (tipoFirma.equals(ConstantsCatalogos.DOS)) {
                campos.append("firma.FirmaDigital,\n").
                        append("firma.CadenaOriginal,\n");
                join.append("join SGTT_FirmaResol firma\n").
                        append("on firma.numeroResolucion = multaDocto.numeroResolucion\n");
            } else {
                campos.append("null as firmaDigital,\n").
                        append("null as cadenaOriginal,\n");
                join.append("");
            }
            parametros.put("camposFirmaDigital", campos.toString());
            parametros.put("joinFirmaDigital", join.toString());
        } catch (SGTServiceException ex) {
            log.error(ex);
        }
        return parametros;
    }
}