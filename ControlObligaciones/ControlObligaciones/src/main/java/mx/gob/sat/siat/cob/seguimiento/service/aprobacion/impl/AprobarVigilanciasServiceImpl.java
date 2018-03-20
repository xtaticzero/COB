/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.service.aprobacion.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.SocketException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.mail.MessagingException;
import mx.gob.sat.siat.cob.seguimiento.dao.arca.PlantillaArcaDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.dbd2.HistoricoCumplimientoDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.APVigilanciasLogDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.DocumentoAprobarDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.IcepsAprobarDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.VigilanciaAdministracionLocalDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.VigilanciaAprobarDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.compartidos.EstadoDocumentoDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.db2.HistoricoCumplimiento;
import mx.gob.sat.siat.cob.seguimiento.dto.db2.Paginador;
import static mx.gob.sat.siat.cob.seguimiento.dto.report.enums.FacturaVigilanciaAprobadaEnum.EXTENSION;
import static mx.gob.sat.siat.cob.seguimiento.dto.report.enums.FacturaVigilanciaAprobadaEnum.NOMBRE_ARCHIVO;
import static mx.gob.sat.siat.cob.seguimiento.dto.report.enums.FacturaVigilanciaAprobadaEnum.RUTA_REPORTE;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.AdministrativoNivelCargo;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DetalleDocumento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DocumentoAprobar;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.IcepsAprobar;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ParametrosValidacionCumplimiento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ReporteAprobacionVigilancia;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VigilanciaAdministracionLocal;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VigilanciaAprobar;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VigilanciasLogDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoDocumentoEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.NivelEmisionEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.SituacionVigilanciaEnum;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.service.aprobacion.AprobarVigilanciaDelegateService;
import mx.gob.sat.siat.cob.seguimiento.service.aprobacion.AprobarVigilanciasService;
import static mx.gob.sat.siat.cob.seguimiento.service.aprobacion.AprobarVigilanciasService.PORCENTAJE_TOTAL;
import static mx.gob.sat.siat.cob.seguimiento.service.aprobacion.AprobarVigilanciasService.REGISTROS_A_PROCESAR_CUMPLIMIENTO;
import static mx.gob.sat.siat.cob.seguimiento.service.aprobacion.AprobarVigilanciasService.VALOR_INICIAL;
import mx.gob.sat.siat.cob.seguimiento.service.aprobacion.ValidacionCumplimientoService;
import mx.gob.sat.siat.cob.seguimiento.service.otros.MailService;
import mx.gob.sat.siat.cob.seguimiento.util.IntegerMutable;
import static mx.gob.sat.siat.cob.seguimiento.util.MensajesError.ERR_ENVIO_CORREO;
import static mx.gob.sat.siat.cob.seguimiento.util.MensajesError.ERR_GENERAR_FACTURA;
import mx.gob.sat.siat.cob.seguimiento.util.Utilerias;
import mx.gob.sat.siat.cob.seguimiento.util.UtileriasDocx;
import mx.gob.sat.siat.cob.seguimiento.util.report.Reporter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author rodrigo
 */
@Service("aprobarVigilanciasService")
public class AprobarVigilanciasServiceImpl implements AprobarVigilanciasService {

    @Autowired
    private VigilanciaAprobarDAO vigilanciaAprobarDAO;
    @Autowired
    private DocumentoAprobarDAO documentoAprobarDAO;
    @Autowired
    private VigilanciaAdministracionLocalDAO vigilanciaAdministracionLocalDAO;
    @Autowired
    private IcepsAprobarDAO icepsAprobarDAO;
    @Autowired
    private MailService mailService;
    @Autowired
    private HistoricoCumplimientoDAO historicoCumplimientoDAO;
    @Autowired
    private ValidacionCumplimientoService validacionCumplimientoService;
    @Autowired
    private PlantillaArcaDAO plantillaArcaDAO;
    @Autowired
    private AprobarVigilanciaDelegateService aprobarVigilanciaDelegateServiceImpl;
    @Autowired
    private APVigilanciasLogDAO aPVigilanciasLogDAO;

    private int isComplete;
    private int contador = 0;
    private final String central = "central";
    private List<EstadoDocumentoDTO> listaActEstado;

    private final Logger log = Logger.getLogger(AprobarVigilanciasServiceImpl.class);
    private final List<ParametrosValidacionCumplimiento> parametrosEnProcesoValidacion
            = new ArrayList<ParametrosValidacionCumplimiento>();
    private final List<ParametrosValidacionCumplimiento> parametrosProcesoAprobacion
            = new ArrayList<ParametrosValidacionCumplimiento>();
    private static final String[] COLUMNAS = {"Fecha de Corte", "RFC",
        "Número de Control", "Clave de Obligación",
        "Descripción de la Obligación", "Ejercicio", "Periodo", "Estado de la Obligación"};

    public AprobarVigilanciasServiceImpl() {
        this.isComplete = 0;
    }

    /**
     *
     * @param numeroEmpleado Cuando es nulo se regresan todas las vigilancias
     * sin filtro y contadas por localidad Cuando tiene valor y es un empleado
     * central regresa los valores unicamente filtrados por cargo Cuando tiene
     * valor y es un empleado local regresa las vigilancias de la administracion
     * local y el cargo
     * @return
     * @throws SGTServiceException
     */
    @Override
    @Transactional(readOnly = true)
    public List<VigilanciaAprobar> listarVigilanciasPorAprobar(String numeroEmpleado) throws SGTServiceException {
        List<VigilanciaAprobar> lista;
        if (numeroEmpleado == null) {
            lista = vigilanciaAprobarDAO.listarVigilanciasAprobar();
        } else {
            AdministrativoNivelCargo administrativo = aprobarVigilanciaDelegateServiceImpl.buscarCargoAdministrativo(numeroEmpleado);
            switch (administrativo.getNivelEmision()) {
                case CENTRAL:
                    lista = vigilanciaAprobarDAO.listarVigilanciasAprobar(administrativo.getIdCargoAdministrativo());
                    break;
                case LOCAL:
                    lista = vigilanciaAprobarDAO.listarVigilanciasAprobar(administrativo.getIdCargoAdministrativo(),
                            administrativo.getIdAdministacionLocal());
                    break;
                default:
                    throw new SGTServiceException("Error inesperado, caso inaccesible");
            }
        }
        if (lista == null) {
            throw new SGTServiceException("No se pudo listar las vigilancias, por favor intente mas tarde.");
        }
        return lista;
    }

    /**
     * Este metodo recibe un objeto paginador con los datos de la pagina que se
     * desea obtener con respecto al numero de carga de una vigilancia y con los
     * privilegios de un empleado especifico
     *
     * @param numeroCarga Es el idVigilancia de la vigilancia a la que
     * pertenecen los documentos a buscar
     * @param paginador objeto que tiene el control de que pagina se require
     * @param numeroEmpleado Es el numero del empleado para el cual se
     * regresaran los documentos segun sus privilegios
     */
    @Override
    @Transactional(readOnly = true)
    public List<DocumentoAprobar> listarDocumentosPorAprobar(String numeroCarga,
            Paginador paginador,
            String numeroEmpleado, String filtroRFC) throws SGTServiceException {
        AdministrativoNivelCargo adminitrativo = aprobarVigilanciaDelegateServiceImpl.buscarCargoAdministrativo(numeroEmpleado);
        List<DocumentoAprobar> lista = documentoAprobarDAO.
                listarDocumentos(paginador,
                        numeroCarga,
                        adminitrativo.getIdAdministacionLocal(), filtroRFC);
        if (lista == null) {
            throw new SGTServiceException(
                    "No se pudo listar los documentos, por favor intente mas tarde.");
        }
        return lista;
    }

    /**
     * Crea un paginador con el numero de paginas que indiques en base a
     * privilegios y una vigilancia
     *
     * @param numeroCarga Es la vigilancia que se desea
     * @param numeroEmpleado Segun este empleado se mostraran los documentos
     * para los que tiene privilegios
     * @param numeroPaginas Es el numero de filas que se desean por pagina
     */
    @Override
    @Transactional(readOnly = true)
    public Paginador crearPaginador(String numeroCarga,
            String numeroEmpleado,
            int numeroFilas) throws SGTServiceException {
        AdministrativoNivelCargo adminitrativo
                = aprobarVigilanciaDelegateServiceImpl.buscarCargoAdministrativo(numeroEmpleado);
        Long numeroRegistros = documentoAprobarDAO.
                contarRegistros(numeroCarga,
                        adminitrativo.getIdAdministacionLocal());
        if (numeroRegistros == null) {
            throw new SGTServiceException(
                    "No se pudo obtener el numero de registros, intente mas tarde");
        }
        return new Paginador(numeroRegistros, numeroFilas);
    }

    /**
     * Crea un paginador con el numero de paginas que indiques en base a
     * privilegios y una vigilancia
     *
     * @param numeroCarga Es la vigilancia que se desea
     * @param numeroEmpleado Segun este empleado se mostraran los documentos
     * para los que tiene privilegios
     * @param numeroPaginas Es el numero de filas que se desean por pagina
     * @param filtro Es el parametro de busqueda
     */
    @Override
    @Transactional(readOnly = true)
    public Paginador crearPaginador(String numeroCarga,
            String numeroEmpleado,
            int numeroFilas, String filtro) throws SGTServiceException {
        AdministrativoNivelCargo adminitrativo
                = aprobarVigilanciaDelegateServiceImpl.buscarCargoAdministrativo(numeroEmpleado);
        Long numeroRegistros = documentoAprobarDAO.
                contarRegistros(numeroCarga,
                        adminitrativo.getIdAdministacionLocal(), filtro);
        if (numeroRegistros == null) {
            throw new SGTServiceException(
                    "No se pudo obtener el numero de registros, intente mas tarde");
        }
        return new Paginador(numeroRegistros, numeroFilas);
    }

    /**
     * Crea un paginador con el numero de paginas que indiques en base a
     * privilegios y una vigilancia
     *
     * @param numeroCarga Es la vigilancia que se desea
     * @param numeroEmpleado Segun este empleado se mostraran los documentos
     * para los que tiene privilegios
     * @param numeroFilas
     * @return
     * @throws mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException
     */
    @Override
    public Paginador crearPaginadorIcep(String numeroCarga,
            String numeroEmpleado,
            int numeroFilas) throws SGTServiceException {
        AdministrativoNivelCargo adminitrativo
                = aprobarVigilanciaDelegateServiceImpl.buscarCargoAdministrativo(numeroEmpleado);
        Long numeroRegistros = documentoAprobarDAO.
                contarRegistrosIcep(numeroCarga,
                        adminitrativo.getIdAdministacionLocal());
        if (numeroRegistros == null) {
            throw new SGTServiceException(
                    "No se pudo obtener el numero de registros, intente mas tarde");
        }
        return new Paginador(numeroRegistros, numeroFilas);
    }

    @Override
    @Transactional(rollbackFor = SGTServiceException.class, propagation = Propagation.REQUIRED)
    public void verificarCumplimientos(String numeroCarga, String numeroEmpleado, IntegerMutable progress)
            throws DataAccessException, SGTServiceException {
        progress.setValor(VALOR_INICIAL);
        if (validarEnProceso(numeroCarga, numeroEmpleado)) {
            Paginador paginador = crearPaginadorIcep(numeroCarga, numeroEmpleado, REGISTROS_A_PROCESAR_CUMPLIMIENTO);
            float avance = (float) PORCENTAJE_TOTAL / (float) paginador.getNumeroPaginas();
            float total = 0;
            setListaActEstado(new ArrayList<EstadoDocumentoDTO>());
            do {
                //Obtener documentos de la vigilancia
                List<DocumentoAprobar> documentosAprobar
                        = aprobarVigilanciaDelegateServiceImpl.listarDocumentosIcepPorAprobar(numeroCarga, paginador, numeroEmpleado);

                if (documentosAprobar != null) {
                    for (DocumentoAprobar doc : documentosAprobar) {
                        StringBuffer iceps = new StringBuffer("[");
                        for (DetalleDocumento det : doc.getDetalles()) {
                            iceps.append(",").append(det.getClaveIcep());
                            contador++;
                        }
                        iceps.append("]");
                        log.error(doc.getNumeroControl() + "|" + iceps.toString());
                    }
                }
                log.error("numero de iceps : " + contador);

                if ((documentosAprobar != null) && (documentosAprobar.size() > 0)) {
                    isComplete++;
                    //Obtener cumplimientos
                    List<HistoricoCumplimiento> cumplimientos = historicoCumplimientoDAO.buscarCumplimientos(documentosAprobar);

                    total = aumentarProgreso(total, avance, progress);
                    if (cumplimientos == null) {
                        throw new SGTServiceException("No se pudo consultar los cumplimientos, por favor intente mas tarde");
                    }
                    if (!cumplimientos.isEmpty()) {
                        validacionCumplimientoService.validarHistoricosCumplimiento(numeroCarga, cumplimientos);
                        validacionCumplimientoService.afectarCumplidos(cumplimientos, documentosAprobar);
                    }
                    total = aumentarProgreso(total, avance, progress);
                }

            } while (paginador.next());
            log.debug("-----------inicio actualiza estados---------------");
            for (EstadoDocumentoDTO dato : getListaActEstado()) {
                validacionCumplimientoService.actualizarEstatusDocumento(
                        dato.getNumeroControl(), dato.getEstado());
            }
            log.debug("-----------fin actualiza estados---------------");
            if (vigilanciaAdministracionLocalDAO.updateFechaValidacionCumplimiento(numeroCarga,
                    aprobarVigilanciaDelegateServiceImpl.buscarCargoAdministrativo(numeroEmpleado).getIdAdministacionLocal()) == null) {
                throw new SGTServiceException("No se pudo actualizar la fecha de validación");
            }
            if (isComplete == 0) {
                throw new SGTServiceException(
                        "Todos los ICEP ya han sido cumplidos o cancelados");
            }
        }

    }

    private float aumentarProgreso(float total, float avance, IntegerMutable progress) {
        float t = (avance / MITAD) + total;
        progress.setValor((int) t);
        return t;
    }

    @Override
    public List<IcepsAprobar> listarIcepsPagina(String numeroCarga, Paginador paginador, String numeroEmpleado) throws SGTServiceException {
        AdministrativoNivelCargo adminitrativo
                = aprobarVigilanciaDelegateServiceImpl.buscarCargoAdministrativo(numeroEmpleado);
        log.debug("--------------------listarIcepsPagina----------------------");
        log.debug(adminitrativo.getIdAdministacionLocal());
        log.debug(numeroCarga);
        log.debug(paginador);
        List<IcepsAprobar> list = icepsAprobarDAO.listarIcepsPorPagina(adminitrativo.getIdAdministacionLocal(),
                numeroCarga, paginador);
        if (list == null) {
            throw new SGTServiceException("No se pudo listar los icep, intente mas tarde");
        }
        return list;
    }

    @Override
    public InputStream generarArchivoIceps(String numeroCarga, String numeroEmpleado, IntegerMutable progreso) throws SGTServiceException {
        progreso.setValor(VALOR_INICIAL);
        Paginador paginador = aprobarVigilanciaDelegateServiceImpl.crearPaginadorIceps(numeroCarga, numeroEmpleado, REGISTROS_A_PROCESAR);
        int incremento = (int) (PORCENTAJE_TOTAL / (paginador.getNumeroPaginas() + 1));
        progreso.setValor(progreso.getValor() + incremento);
        String archivo = PATH + "archivo" + Utilerias.formatearFechaAAAAMMDDHHMMSS(new Date()) + ".txt";
        do {
            progreso.setValor(progreso.getValor() + (incremento / 2));
            List<IcepsAprobar> list = listarIcepsPagina(numeroCarga, paginador, numeroEmpleado);
            escribirArchivo(list, archivo);
            progreso.setValor(progreso.getValor() + (incremento / 2));
        } while (paginador.next());
        try {
            File f = new File(archivo);
            progreso.setValor(PORCENTAJE_TOTAL);
            return new FileInputStream(f);
        } catch (FileNotFoundException e) {
            log.error(e.getMessage(), e);
            throw new SGTServiceException("No se pudo crear el archivo, por favor intente mas tarde", e);
        }
    }

    private void escribirArchivo(List<IcepsAprobar> list, String archivo) throws SGTServiceException {
        try {
            FileOutputStream fos = new FileOutputStream(archivo, true);
            OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
            BufferedWriter bf = new BufferedWriter(osw);
            for (String columna : COLUMNAS) {
                bf.write(columna);
                if (!columna.equals(COLUMNAS[COLUMNAS.length - 1])) {
                    bf.write(PIPE);
                }
            }
            bf.write(SALTO_DE_LINEA);
            for (IcepsAprobar icep : list) {
                bf.write(Utilerias.formatearFechaDDMMYYYY(icep.getFechaCorte()) + PIPE);
                bf.write(icep.getRfc() + PIPE);
                bf.write(icep.getNumeroControl() + PIPE);
                bf.write(icep.getClaveIcep() + PIPE);
                bf.write(icep.getDescripcionObligacion() + PIPE);
                bf.write(icep.getEjercicio() + PIPE);
                bf.write(icep.getPeriodo() + PIPE);
                bf.write(icep.getEstadoObligacion() + SALTO_DE_LINEA);
            }
            bf.flush();
            bf.close();
            osw.close();
            fos.close();
        } catch (IOException ex) {
            log.error(ex.getMessage(), ex);
            throw new SGTServiceException("No se pudo crear el archivo, por favor intente mas tarde", ex);
        }
    }

    @Override
    @Transactional(rollbackFor = SGTServiceException.class)
    public void aprobarVigilancia(VigilanciaAdministracionLocal vigilancia,
            String numeroEmpleado, IntegerMutable progress) throws SGTServiceException {
        ParametrosValidacionCumplimiento p
                = new ParametrosValidacionCumplimiento(numeroEmpleado, vigilancia.getNumeroCarga());
        parametrosProcesoAprobacion.add(p);
        AdministrativoNivelCargo adminitrativo
                = aprobarVigilanciaDelegateServiceImpl.buscarCargoAdministrativo(numeroEmpleado);
        vigilancia.setIdAdministracionLocal(adminitrativo.getIdAdministacionLocal());
        vigilancia.setSituacionVigilanciaEnum(SituacionVigilanciaEnum.ACEPTADA);
        vigilancia.setNumeroEmpleado(numeroEmpleado);
        progress.setValor(VALOR_INICIAL);
        if (vigilanciaAdministracionLocalDAO.actualizarVigilancias(vigilancia) == null) {
            throw new SGTServiceException("No se pudo aprobar la vigilancia, por favor intente mas tarde");
        }
        progress.setValor(MITAD * AVANCE);
        if (documentoAprobarDAO.actualizarDocumentos(vigilancia.getNumeroCarga(),
                adminitrativo.getIdAdministacionLocal(), EstadoDocumentoEnum.NO_GENERADO) == null) {
            throw new SGTServiceException("No se pudo hacer el cambio de estado de documentos, por favor intente mas tarde");
        }
        progress.setValor(MITAD * AVANCE + progress.getValor());
        if (documentoAprobarDAO.bitacoraDocumentos(vigilancia.getNumeroCarga(),
                adminitrativo.getIdAdministacionLocal(),
                EstadoDocumentoEnum.NO_GENERADO) == null) {
            throw new SGTServiceException("No se pudo registrar la bitacora de cambio de estado de documento, por favor intente mas tarde");
        }
        progress.setValor(MITAD * AVANCE + progress.getValor());
        generarReporte(vigilancia, adminitrativo, numeroEmpleado);
        progress.setValor(MITAD * AVANCE + progress.getValor());
        progress.setValor(MITAD * AVANCE + progress.getValor());
    }

    @Override
    public boolean validarEnProceso(String numeroCarga, String numeroEmpleado) {
        ParametrosValidacionCumplimiento pvc
                = new ParametrosValidacionCumplimiento(numeroEmpleado, numeroCarga);
        boolean contiene = !parametrosEnProcesoValidacion.contains(pvc);
        if (contiene) {
            parametrosEnProcesoValidacion.add(pvc);
        }
        return contiene;
    }

    @Override
    public void remover(String numeroCarga, String numeroEmpleado) {
        ParametrosValidacionCumplimiento pvc
                = new ParametrosValidacionCumplimiento(numeroEmpleado, numeroCarga);
        parametrosEnProcesoValidacion.remove(pvc);
    }

    private void generarReporte(VigilanciaAdministracionLocal vigilancia,
            AdministrativoNivelCargo adminitrativo,
            String numeroEmpleado) throws SGTServiceException {
        ReporteAprobacionVigilancia reporte = aprobarVigilanciaDelegateServiceImpl.buscarDatos(vigilancia, adminitrativo);
        Map<String, Object> parametros = aprobarVigilanciaDelegateServiceImpl.llenarParametros(numeroEmpleado, reporte, vigilancia);
        try {
            String nombreArchivo = PATH + NOMBRE_ARCHIVO + vigilancia.getNumeroCarga() + numeroEmpleado + EXTENSION;
            Reporter reporter = new Reporter();
            //Se cambian los detalles del reporte
            List lstDetalle = new ArrayList();
            lstDetalle.add(reporte);
            reporter.makeReportJASPER(getClass().getClassLoader().
                    getResourceAsStream(RUTA_REPORTE),
                    parametros,
                    new JRBeanCollectionDataSource(lstDetalle),
                    nombreArchivo);
            Map<String, String> map = new HashMap<String, String>();
            map.put("numeroVigilancia", vigilancia.getNumeroCarga());
            mailService.enviarCorreoIdEmpleado(
                    numeroEmpleado,
                    "MAT CO - Factura de Vigilancia Aprobada",
                    Utilerias.obtenerCorreo("aprobacionVigilancia", map),
                    new File(nombreArchivo));

            cleanVigilanciaError(vigilancia);

        } catch (FileNotFoundException e) {
            guardaErrorAprobacion(adminitrativo.getNumeroEmpleado(), vigilancia, ERR_GENERAR_FACTURA);
            throw new SGTServiceException("El reporte no fue encontrado", e);
        } catch (JRException e) {
            guardaErrorAprobacion(adminitrativo.getNumeroEmpleado(), vigilancia, ERR_GENERAR_FACTURA);
            throw new SGTServiceException("No se pudo generar el reporte", e);
        } catch (MessagingException ex) {
            guardaErrorAprobacion(adminitrativo.getNumeroEmpleado(), vigilancia, ERR_ENVIO_CORREO);
            throw new SGTServiceException("No fue posible enviar correo electronico", ex);
        } catch (SQLException ex) {
            guardaErrorAprobacion(adminitrativo.getNumeroEmpleado(), vigilancia, ERR_ENVIO_CORREO);
            log.error("No se pudo enviar la factura", ex);
        } catch (SocketException ex) {
            guardaErrorAprobacion(adminitrativo.getNumeroEmpleado(), vigilancia, ERR_ENVIO_CORREO);
            throw new SGTServiceException("No fue posible enviar correo electronico", ex);
        }

    }

    @Override
    public void removerProcesoAprobar(VigilanciaAdministracionLocal vigilancia, String numeroEmpleado) {
        ParametrosValidacionCumplimiento p
                = new ParametrosValidacionCumplimiento(numeroEmpleado, vigilancia.getNumeroCarga());
        parametrosProcesoAprobacion.remove(p);
    }

    @Override
    public byte[] obtenerPlantilla(int idPlantilla) throws SGTServiceException {
        List<byte[]> archivos = plantillaArcaDAO.buscarArchivoPlantilla(idPlantilla);
        byte[] archivo = null;
        if (archivos == null || archivos.isEmpty()) {
            throw new SGTServiceException("No se pudo obtener la plantilla de ARCA");
        }
        if (archivos.size() == 1) {
            return archivos.get(VALOR_INICIAL);
        } else {
            String ruta = UtileriasDocx.mergeDocumentos(archivos);
            if (ruta == null) {
                throw new SGTServiceException("No se pudo generar la plantilla");
            } else {
                FileInputStream f = null;
                try {
                    f = new FileInputStream(ruta);
                    byte[] buffer = new byte[1024 * 50];
                    archivo = new byte[VALOR_INICIAL];
                    int leidos = f.read(buffer);
                    while (leidos > 0) {
                        byte[] bufferInterno = new byte[leidos + archivo.length];
                        System.arraycopy(archivo, 0, bufferInterno, 0, archivo.length);
                        System.arraycopy(buffer, 0, bufferInterno, archivo.length, leidos);
                        archivo = bufferInterno;
                        leidos = f.read(buffer);
                    }
                } catch (FileNotFoundException e) {
                    throw new SGTServiceException("No se pudo generar la plantilla", e);
                } catch (IOException ex) {
                    throw new SGTServiceException("No se pudo generar la plantilla", ex);
                } finally {
                    if (f != null) {
                        try {
                            f.close();
                        } catch (IOException e) {
                            log.error("No se pudo cerrar el stream al archivo ", e);
                        }
                    }
                }
            }
        }
        return archivo;
    }

    @Override
    public List<ParametrosValidacionCumplimiento> getParametrosEnProcesoValidacion() {
        return Collections.unmodifiableList(parametrosEnProcesoValidacion);
    }

    @Override
    public List<ParametrosValidacionCumplimiento> getParametrosProcesoAprobacion() {
        return Collections.unmodifiableList(parametrosProcesoAprobacion);
    }

    @Override
    public void guardaErrorAprobacion(String numeroEmpleado, VigilanciaAdministracionLocal vigilancia, String descripcionError) {

        try {
            AdministrativoNivelCargo administrativo;
            String idLocal;

            administrativo = aprobarVigilanciaDelegateServiceImpl.buscarCargoAdministrativo(numeroEmpleado);
            idLocal = administrativo.getIdAdministacionLocal();

            vigilancia.setIdAdministracionLocal(getValidIdAdmonLocal(vigilancia));

            VigilanciasLogDTO vigLogDTO;

            if (aPVigilanciasLogDAO.vigilanciaConError(getNumeroVigilancia(vigilancia), vigilancia.getIdAdministracionLocal())) {
                vigLogDTO = aPVigilanciasLogDAO.getEerrorVigilancias(getNumeroVigilancia(vigilancia), vigilancia.getIdAdministracionLocal());
                if (vigLogDTO.getDescripcion() == null) {
                    aPVigilanciasLogDAO.updateError(getNumeroVigilancia(vigilancia), idLocal, descripcionError);
                }
            } else {
                aPVigilanciasLogDAO.insertarError(getNumeroVigilancia(vigilancia), idLocal, descripcionError);
            }

        } catch (SGTServiceException ex) {
            log.error(ex);
        }

    }

    @Override
    public String obtenerError(VigilanciaAdministracionLocal vigilancia) {
        VigilanciasLogDTO vigLogDTO;
        if (aPVigilanciasLogDAO.vigilanciaConError(getNumeroVigilancia(vigilancia), vigilancia.getIdAdministracionLocal())) {
            vigLogDTO = aPVigilanciasLogDAO.getEerrorVigilancias(getNumeroVigilancia(vigilancia), vigilancia.getIdAdministracionLocal());
            return vigLogDTO.getDescripcion();
        } else {
            return null;
        }
    }

    public Long getNumeroVigilancia(VigilanciaAdministracionLocal vigilancia) {
        if ((vigilancia != null) && (vigilancia.getIdVigilancia() == null)) {
            return new Long(vigilancia.getNumeroCarga());
        } else {
            return null;
        }
    }

    private String getValidIdAdmonLocal(VigilanciaAdministracionLocal vigilancia) {
        if (vigilancia.getIdAdministracionLocal() == null) {
            return central;
        } else {
            return vigilancia.getIdAdministracionLocal();
        }
    }

    @Override
    public List<VigilanciasLogDTO> getVigilanciaConError(String numeroEmpleado, List<VigilanciaAdministracionLocal> lstVigilanciasXaprobar) {
        Long[] numVig = new Long[lstVigilanciasXaprobar.size()];
        List<VigilanciasLogDTO> lstResult;
        Set<String> lstIdLocal = new HashSet<String>();
        AdministrativoNivelCargo administrativo;
        String idLocal;

        lstResult = new ArrayList<VigilanciasLogDTO>();

        for (int i = 0; i < lstVigilanciasXaprobar.size(); i++) {
            numVig[i] = getNumeroVigilancia(lstVigilanciasXaprobar.get(i));
            lstIdLocal.add(getValidIdAdmonLocal(lstVigilanciasXaprobar.get(i)));
        }

        try {
            administrativo = aprobarVigilanciaDelegateServiceImpl.buscarCargoAdministrativo(numeroEmpleado);
            idLocal = administrativo.getIdAdministacionLocal();
            switch (administrativo.getNivelEmision()) {
                case CENTRAL:
                    lstResult = aPVigilanciasLogDAO.getVigilanciasConError(NivelEmisionEnum.CENTRAL, numVig, idLocal);
                    break;
                case LOCAL:
                    lstResult = aPVigilanciasLogDAO.getVigilanciasConError(NivelEmisionEnum.LOCAL, numVig, idLocal);
                    break;
            }
        } catch (SGTServiceException ex) {
            log.error(ex);
        }
        return lstResult;
    }

    @Override
    public Integer cleanVigilanciaError(VigilanciaAdministracionLocal vigAdmLoc) {
        vigAdmLoc.setIdAdministracionLocal(getValidIdAdmonLocal(vigAdmLoc));
        return aPVigilanciasLogDAO.cleanError(getNumeroVigilancia(vigAdmLoc), vigAdmLoc.getIdAdministracionLocal());
    }

    @Override
    public List<EstadoDocumentoDTO> getListaActEstado() {
        return listaActEstado;
    }

    @Override
    public void setListaActEstado(List<EstadoDocumentoDTO> listaActEstado) {
        this.listaActEstado = listaActEstado;
    }

    /**
     *
     * @return @throws SGTServiceException
     */
    @Override
    public Long obtenerCantidadVigilanciasAL() throws SGTServiceException {
        return vigilanciaAprobarDAO.obtenerCantidadVigilanciasAL();
    }

    @Override
    public List<VigilanciaAprobar> obtenerVigilanciasPaginadas(Paginador paginador) throws SGTServiceException {
        return vigilanciaAprobarDAO.obtenerVigilanciasPaginadas(paginador);
    }
}
