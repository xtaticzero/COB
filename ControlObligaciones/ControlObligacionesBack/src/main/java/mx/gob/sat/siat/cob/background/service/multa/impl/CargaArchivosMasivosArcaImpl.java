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
import mx.gob.sat.siat.cob.background.service.job.impl.CargaArchivoMasivoServiceImpl;
import mx.gob.sat.siat.cob.background.service.multa.CargaArchivosMasivosArca;
import mx.gob.sat.siat.cob.background.service.multa.ValidaDatosService;
import mx.gob.sat.siat.cob.background.util.UtileriasBackground;
import mx.gob.sat.siat.cob.seguimiento.dao.arca.MonitorArchivoArcaDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.MonitorArchivoArca;
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
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 *
 * @author Juan
 */
@Service("cargaArchivosMasivosArca")
public class CargaArchivosMasivosArcaImpl implements CargaArchivosMasivosArca {

    private Logger log = Logger.getLogger(CargaArchivosMasivosArcaImpl.class);
    private JobParameters jobParameters;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    @Qualifier("jobLauncher")
    private JobLauncher launcher;
    @Autowired(required = false)
    @Qualifier("cargaMasivaArchivosJob")
    private Job cargaMasivaArchivosJob;
    @Autowired
    private PlantillaArcaService plantillaArcaService;
    @Autowired
    private VigilanciaAdministracionLocalService vigilanciaLocalService;
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
    private VigilanciaService vigilanciaService;

    @Override
    public void cargaArchivos() throws SGTServiceException {
        log.info("###  Inicia el proceso... ");
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
        Long numVigilancia = obtenetNumeroVigilancia();
        /**
         * Ejecuta el proceso encargado del envio de multas a ARCA.
         */
        if (numVigilancia != null) {
            /**
             * Determina tipoFirma de la vigilancia.
             */
            Map<String, String> referencias = parametrosSQL(numVigilancia);
            /**
             * Obtiene las locales de la vigilancia anteriormente obtenida.
             */
            List<VigilanciaAdministracionLocal> vigilanciasLocales = obtenerAdministracionLocalXIdVigilanica(numVigilancia);
            /**
             * Creeacion del archivo properties para el proceso.
             */
            registrarDatosMonitoreo(vigilanciasLocales);
            /**
             * Ejecuta Proceso que envia archivos.
             */
            ejecutaJobEnvioArchivos(vigilanciasLocales, referencias.get("camposFirmaDigital"), referencias.get("joinFirmaDigital"));
        }
    }

    /**
     * Ejecuta el proceso de envio de datos a ARCA.
     */
    private void ejecutaJobEnvioArchivos(List<VigilanciaAdministracionLocal> vigilanciasLocales, 
            String camposFirmaDigital, String joinFirmaDigital) {
        try {
            Properties propertiesHilos = UtileriasBackground.obtenProperties(ConstantsCatalogos.RUTA_THREADS);
            ExecutorService executor = Executors.newFixedThreadPool(
                    Integer.parseInt(propertiesHilos.getProperty("thread.cantidad")));
            guardaPlantillaBatch(EsMultaEnum.NO_ES_MULTA);
            guardaNivelEmisionBatch();
            List<PoolThread> threads = new ArrayList<PoolThread>();
            for (VigilanciaAdministracionLocal vigilancia : vigilanciasLocales) {
                CargaArchivoMasivoServiceImpl carga = new CargaArchivoMasivoServiceImpl();

                carga.setVigilancia(vigilancia);
                carga.setVigilanciaService(vigilanciaLocalService);
                carga.setCargaMasivaArchivosJob(cargaMasivaArchivosJob);
                carga.setLauncher(launcher);
                carga.setJobParameters(jobParameters);
                carga.setCamposFirmaDigital(camposFirmaDigital);
                carga.setJoinFirmaDigital(joinFirmaDigital);

                executor.execute(carga);
                threads.add(carga);
            }
            Utilerias.terminaProceso(executor, threads);
            Utilerias.interrumpirProceso(executor);

            log.info("Fin Job Carga Archivos.");
        } catch (InterruptedException ex) {
            log.error(ex);
        }
    }

    /**
     * Valida que los datos enviados anteriormente a ARCA sean correctos, de no
     * ser asi, se eliminan los datos por vigilancia y local, para volver a
     * encolar el proceso en una posterior ejecucion.
     *
     */
    private void validacionDeDatos() throws SGTServiceException {
        log.info("### Monitoreo de validacion de los datos");
        try {
            List<MonitorArchivoArca> monitores = monitorDAO.
                    consultarListaMonitorArchivoArca(ConstantsCatalogos.CERO);

            if (!monitores.isEmpty()) {
                for (MonitorArchivoArca monitor : monitores) {

                    log.info("###  Validacion de datos ...");
                    log.info("###  Hora Envio : " + monitor.getFechaEnvioArca());
                    log.info("###  Vigilancia : " + monitor.getIdVigilancia());
                    log.info("###  Locales : " + monitor.getIdAdmonLocal());

                    validaDatosService.eliminaDatosARCA(monitor);
                    monitorDAO.deleteMonitorArchivoArca(monitor.getIdVigilancia(),
                            monitor.getIdAdmonLocal(),
                            ConstantsCatalogos.CERO);
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
    private Long obtenetNumeroVigilancia() {
        try {
            return vigilanciaLocalService.obtenetNumeroVigilancia();
        } catch (SGTServiceException ex) {
            log.error(ex);
        }
        return null;
    }

    /**
     * Obtiene el listado de locales a ejecutar por IdVigilancia.
     *
     * @param idVigilancia
     * @return
     */
    private List<VigilanciaAdministracionLocal> obtenerAdministracionLocalXIdVigilanica(Long idVigilancia) {
        try {
            return vigilanciaLocalService.obtenerAdministracionLocalXIdVigilanica(idVigilancia);
        } catch (SGTServiceException ex) {
            log.error(ex);
        }
        return null;
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
    private void guardaPlantillaBatch(EsMultaEnum esMulta) {
        try {
            plantillaArcaService.guardaPlantillaArcaBatch(esMulta);
        } catch (SGTServiceException ex) {
            log.error(ex);
        }
    }

    /**
     * Carga informacion en SGTA_AdmtvoNivlBK y SGTP_FuncionarioBK para la
     * ejecucion del proceso.
     */
    private void guardaNivelEmisionBatch() {
        try {
            funcionarioService.guardaNivelEmision();
        } catch (SGTServiceException ex) {
            log.error(ex);
        }
    }

    /**
     * Vacia las tablas llenadas para el proceso.
     */
    private void eliminarInformacionTablasBatch() {
        try {
            plantillaArcaService.eliminaPlantillaArcaBatch();
            funcionarioService.eliminaNivelEmision();
        } catch (SGTServiceException ex) {
            log.error(ex);
        }
    }

    /**
     * Elimina los archivos del fileSystem creados durante el proceso.
     */
    private boolean eliminaArchivos() {
        boolean borrado;
        try {
            borrado = applicationContext.getResource(
                    ConstantsCatalogos.RUTA_ARCHIVO_COB_ARCHIVOS_MASIVOS).getFile().delete();
            borrado &= applicationContext.getResource(
                    ConstantsCatalogos.RUTA_ARCHIVO_IDC_ARCHIVOS_MASIVOS).getFile().delete();
            borrado &= applicationContext.getResource(
                    ConstantsCatalogos.RUTA_ARCHIVO_REQS_ANTERIORES_ARCHIVOS_MASIVOS).getFile().delete();
            borrado &= applicationContext.getResource(
                    ConstantsCatalogos.RUTA_ARCHIVO_OBLIGACION_PERIODO).getFile().delete();
        } catch (IOException ex) {
            log.error(ex);
            borrado = false;
        }
        return borrado;
    }

    /**
     * Inserta el registro de la informacion a ejecutar.
     */
    private void registrarDatosMonitoreo(
            List<VigilanciaAdministracionLocal> vigilanciasLocales) throws SGTServiceException {
        try {
            Date fechaActual = new Date();
            List<MonitorArchivoArca> monitores = new ArrayList<MonitorArchivoArca>();
            for (VigilanciaAdministracionLocal local : vigilanciasLocales) {
                MonitorArchivoArca monitorConsulta = monitorDAO.
                        consultarMonitorArchivoArca(local.getIdVigilancia(), local.getIdAdministracionLocal(),
                        ConstantsCatalogos.UNO);
                if (monitorConsulta == null) {
                    MonitorArchivoArca monitorSave = new MonitorArchivoArca();

                    monitorSave.setEsEnvioResolucion(ConstantsCatalogos.CERO);
                    monitorSave.setFechaEnvioArca(Utilerias.formatearFechaYYYYMMDDHHMMSS(fechaActual));
                    monitorSave.setIdVigilancia(local.getIdVigilancia());
                    monitorSave.setIdAdmonLocal(local.getIdAdministracionLocal());

                    monitorSave.setCantidadDocumentos(ConstantsCatalogos.CERO);
                    monitorSave.setCantidadObligacionPeriodo(ConstantsCatalogos.CERO);
                    monitorSave.setCantidadOrigenMulta(ConstantsCatalogos.CERO);
                    monitorSave.setCantidadReqAnteriores(ConstantsCatalogos.CERO);

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

    private Map<String, String> parametrosSQL(Long vigilancia) {
        StringBuilder campos = new StringBuilder();
        StringBuilder join = new StringBuilder();
        Map<String, String> parametros = new HashMap<String, String>();
        try {
            Integer tipoFirma = vigilanciaService.consultaIdTipoFirma(vigilancia);
            if (tipoFirma.equals(ConstantsCatalogos.DOS)) {
                campos.append("firma.FirmaDigital,\n").
                        append("firma.CadenaOriginal,\n");
                join.append("join SGTT_FirmaDoc firma\n").
                        append("on firma.numeroControl = docto.numeroControl\n");
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