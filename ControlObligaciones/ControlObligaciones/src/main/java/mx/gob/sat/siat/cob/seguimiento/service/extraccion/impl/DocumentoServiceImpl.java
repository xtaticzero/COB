package mx.gob.sat.siat.cob.seguimiento.service.extraccion.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import mx.gob.sat.siat.cob.seguimiento.br.AfectacionPorNotificacionBR;
import mx.gob.sat.siat.cob.seguimiento.dao.arca.DocumentoARCADAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.BaseDocumentoDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.CitatorioDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.DetalleDocumentoDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.DocumentoDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.SgtDocumentoDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.VigilanciaEntidadFederativaDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.DocumentoARCA;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.MtvoCancelDoc;
import mx.gob.sat.siat.cob.seguimiento.dto.compartidos.CatalogoBase;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.BitacoraSeguimiento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Citatorio;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DetalleDocumento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Documento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.MultaPendienteDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.NumeroControlDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ResultadoDiligencia;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.TipoDocumento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VigilanciaAdministracionLocal;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VigilanciaEntidadFederativa;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoDocumentoEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.OpcionResultadoDiligenciaEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.SituacionIcepEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.SituacionVigilanciaEnum;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.service.extraccion.DetalleDocumentoService;
import mx.gob.sat.siat.cob.seguimiento.service.extraccion.DocumentoService;
import mx.gob.sat.siat.cob.seguimiento.service.extraccion.VigilanciaAdministracionLocalService;
import mx.gob.sat.siat.cob.seguimiento.service.otros.CicloDocEtapaService;
import mx.gob.sat.siat.cob.seguimiento.util.Utilerias;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DocumentoServiceImpl implements DocumentoService {

    private final Logger log = Logger.getLogger(DocumentoServiceImpl.class);
    @Autowired
    private BaseDocumentoDAO baseDocumentoDAO;
    @Autowired
    private DocumentoDAO documentoDAO;
    @Autowired
    private DetalleDocumentoService detalleDocumentoService;
    @Autowired
    private CicloDocEtapaService cicloDocEtapaService;
    @Autowired
    private VigilanciaEntidadFederativaDAO vigilanciaEntidadDAO;
    @Autowired
    private VigilanciaAdministracionLocalService administracionLocalService;
    @Autowired
    private DocumentoARCADAO documentoArcaDAO;
    @Autowired
    private SgtDocumentoDAO sgtDocumentoDAO;
    @Autowired
    private AfectacionPorNotificacionBR afectacionPorNotificacionBRImpl;
    @Autowired
    private DetalleDocumentoDAO detalleDocumentoDAO;
    @Autowired
    private CitatorioDAO citatorioDAO;
    private static final int ESTADO_NO_EXITOSO = -1;

    /**
     *
     */
    public DocumentoServiceImpl() {
        super();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MultaPendienteDTO> obtenerMultasPendientes(String rfc) {
        return documentoDAO.obtenerMultasPendientes(rfc);
    }

    /**
     * @return @throws
     * mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException
     */
    @Override
    @Transactional(readOnly = true)
    public List<Documento> consultaDocumentos() throws SGTServiceException {
        List<Documento> documentos = documentoDAO.consultaDocumentos();
        if (documentos == null) {
            throw new SGTServiceException("No se pudieron consultar los documentos");
        }
        return documentos;
    }

    /**
     *
     * @param documento
     * @throws mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException
     */
    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public void guardaDocumento(Documento documento) throws SGTServiceException {
        //guradamos los iceps
        NumeroControlDTO datos = baseDocumentoDAO.getNumeroControl(documento.getBoid(),
                documento.getTipoDocumento().getValor(), documento.getEtapaVigilancia().getValor());
        if (datos == null) {
            throw new SGTServiceException("No se pudo obtener el n\u00famero de control");
        }
        documento.setNumeroControl(datos.getNumeroControl());
        documento.setIdAdmonLocal(datos.getUbicacion().getClvALR());
        documento.setCodigoPostal(datos.getUbicacion().getCodPostal());
        documento.setIdcrh(Integer.parseInt(datos.getUbicacion().getClvCRH()));
        documento.setIdentidadFederativa(Integer.parseInt(datos.getUbicacion().getClvEntidadFederativa()));
        documento.setIdTipoPersona(datos.getUbicacion().getReferenciaAdicional());

        if (documentoDAO.guardaDocumento(documento) == null) {
            throw new SGTServiceException("no se pudo guardar documento " + documento.getEstadoDocumento());
        }
        for (DetalleDocumento detalleDocumento : documento.getDetalles()) {
            detalleDocumento.setNumeroControl(documento.getNumeroControl());
            detalleDocumentoService.guardaDetalleDocumento(detalleDocumento);
        }
        //insertamos en la bitacora de estados
        if (documentoDAO.actualizaBitacoraDocumento(documento, documento.getEstadoDocumento()) == ESTADO_NO_EXITOSO) {
            throw new SGTServiceException("No se pudo actualizar la bit\u00e1cora por el cambio de estado del documento");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public List<CatalogoBase> obtenerResultadosDiligenciacion() throws SGTServiceException {

        return documentoDAO.obtenerResultadosDiligenciacion();

    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public ResultadoDiligencia actualizarBitacoraDocumento(ResultadoDiligencia input,
            OpcionResultadoDiligenciaEnum tipoOperacion, String usuario,
            boolean isCargaPorArchivo, boolean formatoFechaIncorrecta, boolean isEstadoValido)
            throws SGTServiceException {
        ResultadoDiligencia dil = new ResultadoDiligencia();
        int resultadoDil;
        Documento documento = documentoDAO.consultaXNumControl(input.getNumeroControl());
        dil.setNumeroControl(input.getNumeroControl());
        dil.setFechaAfectacion(Utilerias.formatearFechaDDMMYYYY(new Date()));

        validaDocumento(documento, dil, tipoOperacion, input);
        if (dil.getResultado() != null) {
            return dil;
        }
        if ((input.getResultado().length() >= ConstantsCatalogos.TRES) || (isCargaPorArchivo && isEstadoValido)) {
            dil.setResultado("No cargado - Favor de usar las claves num\u00e9ricas indicadas");
            return dil;
        } else {
            resultadoDil = Integer.parseInt(input.getResultado());
        }
        if ((isCargaPorArchivo && resultadoDil >= 2 || resultadoDil <= -1) || (isCargaPorArchivo && isEstadoValido)) {
            dil.setResultado("No cargado - Favor de usar las claves num\u00e9ricas indicadas");
            return dil;
        }
        EstadoDocumentoEnum ede = validaEstadoDocumento(resultadoDil);
        List<VigilanciaEntidadFederativa> vig = vigilanciaEntidadDAO.obtenerVigilanciaPorIdVigClaveEF(documento.getVigilancia().getIdVigilancia(), input.getClaveEf());
        if (vig != null && vig.size() > 0) {
            //fecha diligenciacion,si es dif null la fecha  el resultado de la diligenciación no deberán cambiar y se considerará como "No Cargado".
            boolean mismaFechaRegistroBitacora = false;
            if (tipoOperacion.getValor().equals(OpcionResultadoDiligenciaEnum.MODIFICAR.getValor())) {
                List<BitacoraSeguimiento> listaBitacora = documentoDAO.obtenerRegistrosBitacoraSeguimiento(input.getNumeroControl());
                if (listaBitacora != null && listaBitacora.size() > 0) {
                    Date fechaRegistroBitacora = listaBitacora.get(0).getFechaMovimiento();
                    mismaFechaRegistroBitacora = DateUtils.isSameDay(new Date(), fechaRegistroBitacora);
                }
            }
            if (resultadoDil == documento.getUltimoEstado() && !mismaFechaRegistroBitacora) {
                dil.setResultado("No cargado - N\u00famero control con fecha diligencia ya existente");
                return dil;
            }
            if ((isCargaPorArchivo && input.getFechaDiligencia() == null) || isCargaPorArchivo && formatoFechaIncorrecta) {
                dil.setResultado("No cargado - Formato de fecha incorrecto, Ingrese DD/MM/AAAA");
                return dil;
            }
            Date fechaDescarga = vigilanciaEntidadDAO.consultarVigilanciaPorUsuarioEf(usuario, documento.getVigilancia().getIdVigilancia());
            boolean esFechaPosterior = false;
            if (fechaDescarga != null) {
                esFechaPosterior = input.getFechaDiligencia().compareTo(fechaDescarga) >= 0;
            } else {
                dil.setResultado("No cargado - La fecha de descarga de la vigilancia no pudo ser determinada");
                return dil;
            }
            /**
             * Valida fecha citatorio.
             */
            validaFechaCitatorio(input, dil, fechaDescarga);
            if (dil.getResultado() != null) {
                return dil;
            }
            if (esFechaPosterior) {
                if (tipoOperacion.getValor().equals(OpcionResultadoDiligenciaEnum.REGISTRAR.getValor())
                        && comparaEstado(documento, resultadoDil, ede)
                        || (mismaFechaRegistroBitacora
                        && (documento.getUltimoEstado() == EstadoDocumentoEnum.NO_LOCALIZADO.getValor()
                        || documento.getUltimoEstado() == EstadoDocumentoEnum.NOTIFICADO.getValor()
                        || documento.getUltimoEstado() == EstadoDocumentoEnum.CANCELADO.getValor()))) {
                    if (documentoDAO.actualizaBitacoraDocumento(documento, ede) != null) {
                        documento.setFechaNotificacion(input.getFechaDiligencia());
                        Citatorio citatorio = new Citatorio();
                        citatorio.setNumeroControl(input.getNumeroControl());
                        citatorio.setFechaCitatorio(input.getFechaCitatorio());
                        calculaFechaVencimiento(documento);
                        if (isValidoActualizaEdoYFechaNoLocalizado(resultadoDil, ede, documento)) {
                            documentoDAO.actualizarFechaDocumento(documento.getNumeroControl(), input.getFechaDiligencia(), documento.getFechaVencimiento(), ede);
                            if (citatorio.getFechaCitatorio() != null) {
                                citatorioDAO.insertaCitatorio(citatorio);
                            }
                        }
                        if (isValidoActualizaEdoYFechaNotificado(resultadoDil, ede, documento)) {
                            documentoDAO.actualizarFechaDocumento(documento.getNumeroControl(), input.getFechaDiligencia(), documento.getFechaVencimiento(), ede);
                            if (citatorio.getFechaCitatorio() != null) {
                                citatorioDAO.insertaCitatorio(citatorio);
                            }
                            calculaFechaVencimiento(documento);
                        }
                        if (isValidoCambioFecha(resultadoDil, ede, documento)) {
                            documentoDAO.actualizaSoloFecha(documento.getNumeroControl(), input.getFechaDiligencia(), documento.getFechaVencimiento(), ede);
                            if (citatorio.getFechaCitatorio() != null) {
                                citatorioDAO.insertaCitatorio(citatorio);
                            }
                            calculaFechaVencimiento(documento);
                        }
                        if (isValidoActualizaIceps(resultadoDil, ede, documento)) {
                            documentoDAO.actualizarFechaDocumento(documento.getNumeroControl(), input.getFechaDiligencia(), documento.getFechaVencimiento(), ede);
                            if (citatorio.getFechaCitatorio() != null) {
                                citatorioDAO.insertaCitatorio(citatorio);
                            }
                            actualizaIcep(documento);
                            calculaFechaVencimiento(documento);
                        }
                        dil.setResultado("Cargado");
                    } else {
                        dil.setResultado("No cargado - Informaci\u00f3n incorrecta");
                    }
                } else {
                    if ((documento.getUltimoEstado() == EstadoDocumentoEnum.PENDIENTE_DE_NOTIFICAR.getValor()
                            && tipoOperacion.getValor().equals(OpcionResultadoDiligenciaEnum.MODIFICAR.getValor()))) {
                        dil.setResultado("No cargado - El resultado de diligencia del n\u00famero de control no ha sido registrado, elija la opcion Registrar");
                    } else {
                        dil.setResultado("No cargado - No es posible registrar el resultado para este n\u00famero de control porque no cumple las condiciones determinadas");
                    }
                }
            } else {
                dil.setResultado("No cargado - La fecha de resultado de la diligencia no puede ser menor a la fecha de descarga de la Vigilancia: " + Utilerias.formatearFechaDDMMYYYY(fechaDescarga));
            }
        } else {
            dil.setResultado("No cargado - No corresponde a una vigilancia aceptada");
        }
        return dil;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public ResultadoDiligencia modificarBitacoraDocumento(ResultadoDiligencia input) throws SGTServiceException {
        ResultadoDiligencia dil = new ResultadoDiligencia();
        dil.setNumeroControl(input.getNumeroControl());
        dil.setFechaAfectacion(Utilerias.formatearFechaDDMMYYYY(new Date()));
        Documento documento = documentoDAO.consultaXNumControl(input.getNumeroControl());
        if (documento == null) {
            dil.setResultado("No modificado - N\u00famero control inexistente");
            return dil;
        }
        EstadoDocumentoEnum ede = null;
        if (Integer.parseInt(input.getResultado()) == (EstadoDocumentoEnum.NOTIFICADO.getValor())) {
            ede = EstadoDocumentoEnum.NOTIFICADO;
        }
        if (Integer.parseInt(input.getResultado()) == (EstadoDocumentoEnum.NO_LOCALIZADO.getValor())) {
            ede = EstadoDocumentoEnum.NO_LOCALIZADO;
        }
        Date fechaRegistroBitacora = documentoDAO.obtenerRegistrosBitacoraSeguimiento(input.getNumeroControl()).get(0).getFechaMovimiento();
        boolean esFechaPosterior = input.getFechaDiligencia().compareTo(documento.getVigilancia().getFechaCargaArchivos()) > 0;
        boolean mismaFechaRegistroBitacora = DateUtils.isSameDay(new Date(), fechaRegistroBitacora);

        if (mismaFechaRegistroBitacora) {
            if (esFechaPosterior) {
                if (documentoDAO.actualizaBitacoraDocumento(documento, ede) != null) {
                    documentoDAO.actualizarFechaDocumento(documento.getNumeroControl(), input.getFechaDiligencia(), null, ede);
                    dil.setResultado("MODIFICADO");
                }
            } else {
                dil.setResultado("La fecha de resultado de la diligencia no puede ser menor a la fecha de carga de la Vigilancia: " + Utilerias.formatearFechaDDMMYYYY(documento.getVigilancia().getFechaCargaArchivos()));
            }
        } else {
            dil.setResultado("No es posible Modificar el resultado para este n\u00famero de control");
        }
        return dil;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public boolean actualizaEstadoBitacoraDocumento(Documento documento, EstadoDocumentoEnum estado) throws SGTServiceException {
        if (cicloDocEtapaService.validaCambioEstado(documento.getEstadoDocumento(), documento.getTipoDocumento(),
                documento.getEtapaVigilancia(), estado)) {
            List<String> numsControl = new ArrayList<String>();
            numsControl.add(documento.getNumeroControl());
            Integer resp = ESTADO_NO_EXITOSO;
            resp = documentoDAO.actualizarEstadoDocumento(numsControl, estado);
            if (resp == ESTADO_NO_EXITOSO) {
                throw new SGTServiceException("Error al actualizar el estado del documento " + documento.getNumeroControl() + " a " + estado);
            }
            resp = documentoDAO.actualizaBitacoraDocumento(documento, estado);
            if (resp == ESTADO_NO_EXITOSO) {
                throw new SGTServiceException("No se pudo actualizar la bit\u00e1cora por el cambio de estado del documento " + documento.getNumeroControl());
            }
            documento.setUltimoEstado(estado.getValor());
            return true;
        } else {
            log.error("No se pudo hacer la transicion de estado " + documento.getEstadoDocumento()
                    + " a " + estado + " del documento " + documento.getNumeroControl()
                    + " siendo documento de tipo " + documento.getTipoDocumento() + " y etapa " + documento.getEtapaVigilancia());
            return false;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public boolean cancelaDocumentoXAutoridad(List<Documento> docs,
            MtvoCancelDoc motivoCancelacion,
            EstadoDocumentoEnum estado) throws SGTServiceException {
        List<String> numsControl = new ArrayList<String>();
        for (Documento doc : docs) {
            numsControl.add(doc.getNumeroControl());
        }
        Integer resp = documentoDAO.actualizarEstadoDocumento(numsControl, estado);
        if (resp == null || resp == ESTADO_NO_EXITOSO) {
            throw new SGTServiceException("No se pudo actualizar el estado del documento");
        }
        resp = documentoDAO.insertaMotivoCancelacion(numsControl, motivoCancelacion);
        if (resp == null || resp == ESTADO_NO_EXITOSO) {
            throw new SGTServiceException("No se pudo actualizar el estado del documento");
        }
        for (int i = 0; i < docs.size(); i++) {
            resp = documentoDAO.actualizaBitacoraDocumento(docs.get(i), estado);
            if (resp == null || resp == ESTADO_NO_EXITOSO) {
                throw new SGTServiceException("No se pudo actualizar la bit\u00e1cora del documento");
            }
        }
        return true;
    }

    /**
     *
     * @param documento
     * @throws SGTServiceException
     */
    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public void actualizaSgtDocumento(Documento documento) throws SGTServiceException {
        if (documentoDAO.actualizaSgtDocumento(documento) == null) {
            throw new SGTServiceException("No se pudo actualizar el documento.");
        }
    }

    @Override
    public Documento consultaXNumControl(String numControl) throws SGTServiceException {
        Documento documento = documentoDAO.consultaXNumControl(numControl);
        if (documento == null) {
            throw new SGTServiceException("No se pudo realizar la consulta por n\u00famero de control.");
        }
        return documento;
    }

    @Override
    public TipoDocumento getTipoDocumentoXNumControl(String numControl) throws SGTServiceException {
        TipoDocumento tipoDoc = documentoDAO.getTipoDocumentoXNumControl(numControl);
        if (tipoDoc == null) {
            throw new SGTServiceException("El n\u00famero de control que busca no existe, por favor verifique e intente de nuevo.");
        }
        return tipoDoc;
    }

    @Override
    public void actualizarEstadoDocumento(List<String> docs, EstadoDocumentoEnum estadoDestino) throws SGTServiceException {
        if (documentoDAO.actualizarEstadoDocumento(docs, estadoDestino) == null) {
            throw new SGTServiceException("No se pudo actualizar el estado de documento");
        }
    }

    @Transactional(readOnly = false)
    @Override
    public void actualizarEstadoDocumento(int idVigilancia, EstadoDocumentoEnum estadoDestino) throws SGTServiceException {
        if (documentoDAO.actualizarEstadoDocumento(idVigilancia, estadoDestino) == null) {
            throw new SGTServiceException("No se pudo actualizar el estado de documento");
        }
    }

    @Override
    public void actualizarVigilanciaAdministracionLocal(List<DocumentoARCA> doctos, VigilanciaAdministracionLocal admonLocal, SituacionVigilanciaEnum estado) throws SGTServiceException {
        Set<VigilanciaAdministracionLocal> valEnviadaArca = new HashSet<VigilanciaAdministracionLocal>();
        Set<VigilanciaAdministracionLocal> valErronea = new HashSet<VigilanciaAdministracionLocal>();
        log.info("### --> Vigilancia: " + admonLocal.getIdVigilancia() + " Actualiza VAL: " + admonLocal.getIdAdministracionLocal());
        try {
            for (DocumentoARCA docto : doctos) {
                if (docto.getIdAlsc().equals(admonLocal.getIdAdministracionLocal())) {
                    if (estado.getIdSituacion() == SituacionVigilanciaEnum.ENVIADA_ARCA.getIdSituacion()) {
                        if (isValidoCambioSiatuacionVigilancia(admonLocal)) {
                            valEnviadaArca.add(admonLocal);
                        }
                    } else {
                        valErronea.add(admonLocal);
                    }
                }
            }
            actualizaVAL(valEnviadaArca, valErronea);
        } catch (SGTServiceException ex) {
            log.error(ex);
        }
    }

    private void actualizaVAL(Set<VigilanciaAdministracionLocal> valEnviadaArca, Set<VigilanciaAdministracionLocal> valErronea) throws SGTServiceException {
        if (!(valEnviadaArca == null || valEnviadaArca.isEmpty())) {
            administracionLocalService.updateSituacionVigilancia(new ArrayList(valEnviadaArca), SituacionVigilanciaEnum.ENVIADA_ARCA);
        }
        if (!(valErronea == null || valErronea.isEmpty())) {
            administracionLocalService.updateSituacionVigilancia(new ArrayList(valErronea), SituacionVigilanciaEnum.ERRONEA);
        }
    }

    private boolean isValidoCambioSiatuacionVigilancia(VigilanciaAdministracionLocal admonLocal) {
        if (admonLocal.getIdSituacionVigilancia() == SituacionVigilanciaEnum.ACEPTADA.getIdSituacion()) {
            return true;
        } else if ((admonLocal.getIdSituacionVigilancia() == SituacionVigilanciaEnum.ERRONEA.getIdSituacion())
                && (admonLocal.getFechaEnvioArca() != null)) {
            if (admonLocal.getFechaEnvioArca().before(Utilerias.getYesterday())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Integer numeroDetallesXNumeroControl(DocumentoARCA documento)
            throws SGTServiceException {
        Integer numeroDetalles = documentoDAO.numeroDetallesXNumeroControl(documento);
        if (numeroDetalles == null) {
            throw new SGTServiceException("Ocurrio un error al contar los detalles por numero de control");
        }
        return numeroDetalles;
    }

    @Override
    public void eliminarDocumento(String id) throws SGTServiceException {
        if (documentoArcaDAO.eliminarDocumento(id) == null) {
            throw new SGTServiceException("Ocurrio un error al eliminar el documento con id : " + id);
        }
    }

    /**
     * registra el cambio de estado del documento en sgtdocumento
     *
     * @param doc
     * @throws SeguimientoDAOException
     */
    @Override
    public void guardaSgtDocumento(Documento doc) throws SeguimientoDAOException {
        sgtDocumentoDAO.guardaSgtDocumento(doc);
    }

    /**
     * registra el cambio de estado del documento en sgtdocumento en batch
     *
     * @param docs
     * @throws SeguimientoDAOException
     */
    @Transactional
    @Override
    public void guardaSgtDocumentoBatch(List<Documento> docs) throws SeguimientoDAOException {
        sgtDocumentoDAO.guardaSgtDocumentoBatch(docs);
    }

    private void calculaFechaVencimiento(Documento documento) {
        if (documento.getUltimoEstado() == (EstadoDocumentoEnum.NO_LOCALIZADO.getValor())
                || documento.getUltimoEstado() == (EstadoDocumentoEnum.PENDIENTE_DE_NOTIFICAR.getValor())
                || documento.getUltimoEstado() == (EstadoDocumentoEnum.NOTIFICADO.getValor())
                || documento.getUltimoEstado() == (EstadoDocumentoEnum.CANCELADO.getValor())
                || documento.getUltimoEstado() == (EstadoDocumentoEnum.CUMPLIDO_SIN_NOTIFICAR.getValor())
                || documento.getUltimoEstado() == (EstadoDocumentoEnum.SOLVENTADO_PARCIAL.getValor())) {
            try {
                Date date = afectacionPorNotificacionBRImpl.calculaFechaValidezDoc(documento.getTipoDocumento().getValor(), documento.getIdEtapaVigilancia(), documento.getFechaNotificacion());
                documento.setFechaVencimiento(date);
            } catch (SGTServiceException ex) {
                log.error(ex);
            }
        } else {
            documento.setFechaVencimiento(null);
        }

    }

    private boolean comparaEstado(Documento documento, int resultadoDil, EstadoDocumentoEnum ede) {
        if (isResultadoNotificado(resultadoDil, ede)) {
            if (documento.getUltimoEstado() == EstadoDocumentoEnum.NOTIFICADO.getValor() && documento.getFechaNotificacion() == null
                    || documento.getUltimoEstado() == EstadoDocumentoEnum.NO_LOCALIZADO.getValor() && documento.getFechaNotificacion() == null
                    || documento.getUltimoEstado() == EstadoDocumentoEnum.PENDIENTE_DE_NOTIFICAR.getValor() && documento.getFechaNotificacion() == null
                    || documento.getUltimoEstado() == EstadoDocumentoEnum.CUMPLIDO_SIN_NOTIFICAR.getValor() && documento.getFechaNotificacion() == null
                    || documento.getUltimoEstado() == EstadoDocumentoEnum.SOLVENTADO_PARCIAL.getValor() && documento.getFechaNotificacion() == null
                    || documento.getUltimoEstado() == EstadoDocumentoEnum.CANCELADO.getValor() && documento.getFechaNotificacion() == null) {
                return true;
            }
        } else if (isResultadoNoLocalizado(resultadoDil, ede)) {
            if (documento.getUltimoEstado() == EstadoDocumentoEnum.NOTIFICADO.getValor() && documento.getFechaNotificacion() == null
                    || documento.getUltimoEstado() == EstadoDocumentoEnum.NO_LOCALIZADO.getValor() && documento.getFechaNotificacion() == null
                    || documento.getUltimoEstado() == EstadoDocumentoEnum.PENDIENTE_DE_NOTIFICAR.getValor() && documento.getFechaNotificacion() == null
                    || documento.getUltimoEstado() == EstadoDocumentoEnum.CUMPLIDO_SIN_NOTIFICAR.getValor() && documento.getFechaNotificacion() == null
                    || documento.getUltimoEstado() == EstadoDocumentoEnum.SOLVENTADO_PARCIAL.getValor() && documento.getFechaNotificacion() == null
                    || documento.getUltimoEstado() == EstadoDocumentoEnum.CANCELADO.getValor() && documento.getFechaNotificacion() == null) {
                return true;
            }
        }
        return false;
    }

    private void actualizaIcep(Documento documento) {
        try {
            detalleDocumentoDAO.cambiarEstadoICEPsPorNumControl(
                    documento, SituacionIcepEnum.CUMPLIDO, SituacionIcepEnum.INCUMPLIDO);
        } catch (SeguimientoDAOException ex) {
            log.error(ex);
        }
    }

    private boolean isResultadoNotificado(int resultadoDil, EstadoDocumentoEnum ede) {
        return (resultadoDil == EstadoDocumentoEnum.NOTIFICADO.getValor() || ede.getValor() == EstadoDocumentoEnum.NOTIFICADO.getValor());
    }

    private boolean isResultadoNoLocalizado(int resultadoDil, EstadoDocumentoEnum ede) {
        return (resultadoDil == EstadoDocumentoEnum.NO_LOCALIZADO.getValor() || ede.getValor() == EstadoDocumentoEnum.NO_LOCALIZADO.getValor());

    }

    private boolean isValidoCambioFecha(int resultadoDil, EstadoDocumentoEnum ede, Documento documento) {
        if ((isResultadoNotificado(resultadoDil, ede) && documento.getUltimoEstado() == EstadoDocumentoEnum.CANCELADO.getValor()
                || (isResultadoNoLocalizado(resultadoDil, ede) && documento.getUltimoEstado() == EstadoDocumentoEnum.CANCELADO.getValor()))) {
            return true;
        }
        return false;

    }

    private boolean isValidoActualizaIceps(int resultadoDil, EstadoDocumentoEnum ede, Documento documento) {
        if ((isResultadoNotificado(resultadoDil, ede) && documento.getUltimoEstado() == EstadoDocumentoEnum.CUMPLIDO_SIN_NOTIFICAR.getValor())
                || (isResultadoNotificado(resultadoDil, ede) && documento.getUltimoEstado() == EstadoDocumentoEnum.SOLVENTADO_PARCIAL.getValor())) {
            return true;
        }
        return false;
    }

    private boolean isValidoActualizaEdoYFechaNoLocalizado(int resultadoDil, EstadoDocumentoEnum ede, Documento documento) {
        if ((isResultadoNoLocalizado(resultadoDil, ede)
                && (documento.getUltimoEstado() == EstadoDocumentoEnum.PENDIENTE_DE_NOTIFICAR.getValor())
                || (documento.getUltimoEstado() == EstadoDocumentoEnum.NO_LOCALIZADO.getValor())
                || (documento.getUltimoEstado() == EstadoDocumentoEnum.NOTIFICADO.getValor())
                || (documento.getUltimoEstado() == EstadoDocumentoEnum.SOLVENTADO_PARCIAL.getValor())
                || (documento.getUltimoEstado() == EstadoDocumentoEnum.CUMPLIDO_SIN_NOTIFICAR.getValor()))) {
            return true;
        }
        return false;
    }

    private boolean isValidoActualizaEdoYFechaNotificado(int resultadoDil, EstadoDocumentoEnum ede, Documento documento) {
        if ((isResultadoNotificado(resultadoDil, ede)
                && (documento.getUltimoEstado() == EstadoDocumentoEnum.PENDIENTE_DE_NOTIFICAR.getValor())
                || (documento.getUltimoEstado() == EstadoDocumentoEnum.NO_LOCALIZADO.getValor())
                || (documento.getUltimoEstado() == EstadoDocumentoEnum.NOTIFICADO.getValor()))) {
            return true;
        }
        return false;
    }

    /**
     * Valida Documento del resultado diligencia.
     */
    private void validaDocumento(Documento documento, ResultadoDiligencia dil,
            OpcionResultadoDiligenciaEnum tipoOperacion, ResultadoDiligencia input) {
        if (documento == null) {
            dil.setResultado("No cargado - N\u00famero control inexistente");
        } else if (tipoOperacion.getValor().equals(OpcionResultadoDiligenciaEnum.REGISTRAR.getValor())) {
            if (documento.getFechaNotificacion() != null || documento.getFechaVencimiento() != null
                    || documento.getFechaNoLocalizadoContribuyente() != null) {
                dil.setResultado("N\u00famero control previamente registrado");
            }
        } else if (!(documento.getIdentidadFederativa() + "").equals(input.getClaveEf() + "")) {
            dil.setResultado("No cargado - No corresponde a su entidad federativa");
        }
    }

    /**
     * Se asigna un estadoDocumento, dependiendo las condiciones.
     */
    private EstadoDocumentoEnum validaEstadoDocumento(int resultadoDil) {
        if (resultadoDil == (EstadoDocumentoEnum.NO_LOCALIZADO.getValor()) || resultadoDil == 0) {
            return EstadoDocumentoEnum.NO_LOCALIZADO;
        } else if (resultadoDil == (EstadoDocumentoEnum.NOTIFICADO.getValor()) || resultadoDil == 1) {
            return EstadoDocumentoEnum.NOTIFICADO;
        } else if (resultadoDil == (EstadoDocumentoEnum.CANCELADO.getValor()) || resultadoDil == 2) {
            return EstadoDocumentoEnum.CANCELADO;
        }
        return null;
    }

    /**
     * Valida que la fecha citatorio no sea incorrecta.
     */
    private void validaFechaCitatorio(ResultadoDiligencia input, ResultadoDiligencia dil, Date fechaDescarga) {
        if (input.getFechaCitatorio() != null) {
            if (!((input.getFechaCitatorio().before(input.getFechaDiligencia())//Notificacion
                    || input.getFechaCitatorio().equals(input.getFechaDiligencia()))
                    && (input.getFechaCitatorio().after(fechaDescarga) //Descarga
                    || input.getFechaCitatorio().equals(fechaDescarga)))) {
                dil.setResultado("No cargado - La fecha de citatorio no puede ser menor a la fecha de descarga de la Vigilancia: "
                        + Utilerias.formatearFechaDDMMYYYY(fechaDescarga) + ", ni mayor a la fecha de notificacion: "
                        + Utilerias.formatearFechaDDMMYYYY(input.getFechaDiligencia()));
            }
        }
    }
}
