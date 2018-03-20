package mx.gob.sat.siat.cob.background.service.multa.impl;

import mx.gob.sat.siat.cob.background.service.job.EmailBackService;
import mx.gob.sat.siat.cob.background.service.multa.ValidaDatosService;
import mx.gob.sat.siat.cob.seguimiento.dao.arca.DocumentoARCADAO;
import mx.gob.sat.siat.cob.seguimiento.dao.arca.ObligacionArcaDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.arca.PeriodoArcaDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.arca.ReqOrigenMultaDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.arca.ReqsAnterioresDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.DocumentoDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.SgttResolucionDocDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.VigilanciaAdministracionLocalDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.MonitorArchivoArca;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoDocumentoEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoMultaEnum;
import mx.gob.sat.siat.cob.seguimiento.exception.ARCADAOExcepcion;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Juan
 */
@Service
public class ValidaDatosServiceImpl implements ValidaDatosService {

    private Logger log = Logger.getLogger(ValidaDatosServiceImpl.class);
    private Boolean isEnvioResolucion;
    @Autowired
    private DocumentoARCADAO documentoARCADAO;
    @Autowired
    private ObligacionArcaDAO obligacionesARCADAO;
    @Autowired
    private PeriodoArcaDAO periodoARCADAO;
    @Autowired
    private ReqOrigenMultaDAO origenMultaARCADAO;
    @Autowired
    private ReqsAnterioresDAO reqsAnterioresDAO;
    @Autowired
    private SgttResolucionDocDAO resolucionDAO;
    @Autowired
    private VigilanciaAdministracionLocalDAO vilanciaAdmonLocalDAO;
    @Autowired
    private EmailBackService emailBackService;
    @Autowired
    private DocumentoDAO documentoDAO;

    public ValidaDatosServiceImpl() {
    }

    /**
     * Verifica que la informacion enviada sea la misma que la se recupero en
     * las consultas durante el proceso batch, de no ser asì, se eliminan los
     * datos de ARCA y se actualizan los estados de la vigilancia para que se
     * tome de forma inmediata durante la ejecucion del proceso.
     *
     * @param monitor
     * @throws SGTServiceException
     */
    @Override
    public void eliminaDatosARCA(MonitorArchivoArca monitor) throws SGTServiceException {
        try {
            if (!numeroDatosCorrecto(monitor)) {
                /**
                 * Elimina en ARCA
                 */
                log.info("### Eliminando datos de ARCA por inconsistencia de datos ...");
                if (isEnvioResolucion) {
                    elminaDatosProcesoGeneraMultas(monitor);
                } else {
                    eliminaDatosProcesoEnviaDocumentos(monitor);
                }
            }
        } catch (ARCADAOExcepcion ex) {
            log.error(ex);
            throw new SGTServiceException(ex);
        } catch (SeguimientoDAOException ex) {
            log.error(ex);
            throw new SGTServiceException(ex);
        }
    }

    /**
     * Valida que la informacion enviada sea correcta.
     *
     * @param monitor
     * @throws SGTServiceException
     * @return
     */
    private boolean numeroDatosCorrecto(MonitorArchivoArca monitor) throws SGTServiceException {
        try {
            log.info("IdVigilancia --> " + monitor.getIdVigilancia());
            log.info("Local --> " + Integer.parseInt(monitor.getIdAdmonLocal()));
            log.info("Fecha --> " + monitor.getFechaEnvioArca());
            Integer doctosARCA = documentoARCADAO.consultarDocumentoPorIdVigilancia(
                    monitor.getIdVigilancia(), Integer.parseInt(monitor.getIdAdmonLocal()),
                    monitor.getFechaEnvioArca());
            Integer obligacionesARCA
                    = obligacionesARCADAO.consultarObligacionesPorIdVigilancia(
                            monitor.getIdVigilancia(), Integer.parseInt(monitor.getIdAdmonLocal()),
                            monitor.getFechaEnvioArca());
            Integer periodosARCA
                    = periodoARCADAO.consultarPeriodosPorIdVigilancia(
                            monitor.getIdVigilancia(), Integer.parseInt(monitor.getIdAdmonLocal()),
                            monitor.getFechaEnvioArca());
            if (monitor.getEsEnvioResolucion().equals(ConstantsCatalogos.UNO)) {
                return validaEnvioResolucion(doctosARCA, obligacionesARCA, periodosARCA, monitor);
            } else if (monitor.getEsEnvioResolucion().equals(ConstantsCatalogos.CERO)) {
                return validaEnvioArchivos(doctosARCA, obligacionesARCA, periodosARCA, monitor);
            }
            return false;
        } catch (ARCADAOExcepcion ex) {
            throw new SGTServiceException(ex);
        }
    }

    /**
     * Valida los datos del proceso de Resolucion.
     *
     * @param doctosARCA
     * @param obligacionesARCA
     * @param periodosARCA
     * @param monitor
     * @throws ARCADAOExcepcion
     * @return
     */
    private boolean validaEnvioResolucion(int doctosARCA, int obligacionesARCA,
            int periodosARCA, MonitorArchivoArca monitor) throws ARCADAOExcepcion {

        Integer origenesMultasARCA
                = origenMultaARCADAO.consultarOrigenesMultaPorIdVigilancia(
                        monitor.getIdVigilancia(), Integer.parseInt(monitor.getIdAdmonLocal()),
                        monitor.getFechaEnvioArca());
        Integer doctosResolucionARCA
                = documentoARCADAO.consultarDocumentoResolucionPorIdVigilancia(
                        monitor.getIdVigilancia(),
                        Integer.parseInt(monitor.getIdAdmonLocal()),
                        monitor.getFechaEnvioArca());
        isEnvioResolucion = true;
        if ((isEqualsTDocumento(monitor.getCantidadDocumentos(), doctosARCA)
                && isEqualsTObligacion(monitor.getCantidadObligacionPeriodo(), obligacionesARCA)
                && isEqualsTPeriodo(monitor.getCantidadObligacionPeriodo(), periodosARCA)
                && isEqualsTOrigenMulta(monitor.getCantidadOrigenMulta(), origenesMultasARCA)
                && isEqualsTDocumentoResolucionMulta(monitor.getCantidadDocumentos(), doctosResolucionARCA))) {
            log.info("### La informacion enviada es Correcta ...");
            log.info("IDVigilancia : " + monitor.getIdVigilancia());
            log.info("IDALSC : " + monitor.getIdAdmonLocal());
            return true;
        }
        log.error("### La informacion enviada es Incorrecta ... ");
        log.error("IDVigilancia : " + monitor.getIdVigilancia());
        log.error("IDALSC : " + monitor.getIdAdmonLocal());
        return false;
    }

    /**
     * Valida los datos del proceso de Resolucion.
     *
     * @param doctosARCA
     * @param obligacionesARCA
     * @param periodosARCA
     * @param monitor
     * @throws ARCADAOExcepcion
     * @return
     */
    private boolean validaEnvioArchivos(int doctosARCA, int obligacionesARCA,
            int periodosARCA, MonitorArchivoArca monitor) throws ARCADAOExcepcion {
        Integer requerimientosARCA = reqsAnterioresDAO.consultarReqsAnterioresPorIdVigilancia(
                monitor.getIdVigilancia(),
                Integer.parseInt(monitor.getIdAdmonLocal()),
                monitor.getFechaEnvioArca());
        isEnvioResolucion = false;
        if ((isEqualsTDocumento(monitor.getCantidadDocumentos(), doctosARCA)
                && isEqualsTObligacion(monitor.getCantidadObligacionPeriodo(), obligacionesARCA)
                && isEqualsTPeriodo(monitor.getCantidadObligacionPeriodo(), periodosARCA))
                && isEqualsTRequerimiento(monitor.getCantidadReqAnteriores(), requerimientosARCA)) {
            log.info("### La informacion enviada es Correcta ...");
            log.info("IDVigilancia : " + monitor.getIdVigilancia());
            log.info("IDALSC : " + monitor.getIdAdmonLocal());
            return true;
        }
        log.error("### La informacion enviada es Incorrecta ... ");
        log.error("IDVigilancia : " + monitor.getIdVigilancia());
        log.error("IDALSC : " + monitor.getIdAdmonLocal());
        return false;
    }

    /**
     * Metodos que retornan true cuando los valores son iguales.
     */
    private boolean isEqualsTDocumento(int doctosCOB, int doctosARCA) {
        log.error("T_DOCUMENTO");
        log.error("COB: " + doctosCOB + " ARCA : " + doctosARCA);
        return doctosCOB == doctosARCA;
    }

    private boolean isEqualsTObligacion(int obligacionCOB, int obligacionARCA) {
        log.error("T_OBLIGACION");
        log.error("COB: " + obligacionCOB + " ARCA : " + obligacionARCA);
        return obligacionCOB == obligacionARCA;
    }

    private boolean isEqualsTPeriodo(int periodoCOB, int periodoARCA) {
        log.error("T_PERIODO");
        log.error("COB: " + periodoCOB + " ARCA : " + periodoARCA);
        return periodoCOB == periodoARCA;
    }

    private boolean isEqualsTOrigenMulta(int origenMultaCOB, int origenMultaARCA) {
        log.error("T_ORIGEN_MULTA");
        log.error("COB: " + origenMultaCOB + " ARCA : " + origenMultaARCA);
        return origenMultaCOB == origenMultaARCA;
    }

    private boolean isEqualsTDocumentoResolucionMulta(int doctoResolucionCOB, int doctoResolucionARCA) {
        log.error("T_DOCUMENTO_RESOLUCION");
        log.error("COB: " + doctoResolucionCOB + " ARCA : " + doctoResolucionARCA);
        return doctoResolucionCOB == doctoResolucionARCA;
    }

    private boolean isEqualsTRequerimiento(int cantidadReqAnteriores, int requerimientosARCA) {
        log.error("T_REQUERIMIENTOS_ANTERIORES");
        log.error("COB: " + cantidadReqAnteriores + " ARCA : " + requerimientosARCA);
        return cantidadReqAnteriores == requerimientosARCA;
    }

    /**
     * Elimacion de las tablas principales, para los flujos.
     *
     * @param monitor
     * @throws ARCADAOExcepcion
     */
    private void eleminaDatosTablasComunes(MonitorArchivoArca monitor) throws ARCADAOExcepcion {
        periodoARCADAO.deletePeriodosPorIdVigilancia(monitor.getIdVigilancia(),
                Integer.parseInt(monitor.getIdAdmonLocal()), monitor.getFechaEnvioArca());
        obligacionesARCADAO.deleteObligacionesPorIdVigilancia(monitor.getIdVigilancia(),
                Integer.parseInt(monitor.getIdAdmonLocal()), monitor.getFechaEnvioArca());
        documentoARCADAO.deleteDocumentoPorIdVigilancia(monitor.getIdVigilancia(),
                Integer.parseInt(monitor.getIdAdmonLocal()), monitor.getFechaEnvioArca());
    }

    /**
     * Elimacion de las tablas correspondientes al flujo de Generar Multas,
     * manda correo electronico.
     *
     * @param monitor
     * @throws ARCADAOExcepcion
     */
    private void elminaDatosProcesoGeneraMultas(MonitorArchivoArca monitor)
            throws ARCADAOExcepcion, SGTServiceException, SeguimientoDAOException {
        origenMultaARCADAO.deleteOrigenesMultaPorIdVigilancia(monitor.getIdVigilancia(),
                Integer.parseInt(monitor.getIdAdmonLocal()), monitor.getFechaEnvioArca());
        documentoARCADAO.deleteDocumentoResolucionPorIdVigilancia(monitor.getIdVigilancia(),
                Integer.parseInt(monitor.getIdAdmonLocal()), monitor.getFechaEnvioArca());
        eleminaDatosTablasComunes(monitor);
        resolucionDAO.actualizarEstadoResolucion(EstadoMultaEnum.AUTORIZADA.getValor(), monitor.getIdVigilancia(),
                monitor.getIdAdmonLocal(), monitor.getFechaEnvioArca());
        vilanciaAdmonLocalDAO.actualizarFechaUltimoEnvioResol(monitor.getIdVigilancia(),
                monitor.getIdAdmonLocal());
        emailBackService.enviarCorreo("multas", monitor);
    }

    /**
     * Elimacion de las tablas correspondientes al flujo de Envio de Archivos,
     * manda correo electronico.
     *
     * @param monitor
     * @throws ARCADAOExcepcion
     */
    private void eliminaDatosProcesoEnviaDocumentos(MonitorArchivoArca monitor)
            throws ARCADAOExcepcion, SGTServiceException, SeguimientoDAOException {
        reqsAnterioresDAO.deleteReqsAnterioresPorIdVigilancia(monitor.getIdVigilancia(),
                Integer.parseInt(monitor.getIdAdmonLocal()), monitor.getFechaEnvioArca());
        eleminaDatosTablasComunes(monitor);
        vilanciaAdmonLocalDAO.actualizarFechaEnvioArca(monitor.getIdVigilancia(),
                monitor.getIdAdmonLocal());
        documentoDAO.actualizarEstadoDoc(EstadoDocumentoEnum.NO_GENERADO.getValor(), monitor.getIdVigilancia(),
                monitor.getIdAdmonLocal(), monitor.getFechaEnvioArca());
        emailBackService.enviarCorreo("documentos", monitor);
    }
}
