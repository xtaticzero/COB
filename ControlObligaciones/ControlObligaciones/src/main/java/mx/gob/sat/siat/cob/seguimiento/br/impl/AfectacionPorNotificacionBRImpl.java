package mx.gob.sat.siat.cob.seguimiento.br.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.br.AfectacionPorNotificacionBR;
import mx.gob.sat.siat.cob.seguimiento.dao.cobranza.DiaInhabilDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.DetalleDocumentoDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.DocumentoDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.TipoDocEtapaDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.SeguimientoARCA;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.enums.EstadoDocArcaEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.DiaInhabil;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Documento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoDocumentoEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.SituacionIcepEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoDocumentoEnum;
import mx.gob.sat.siat.cob.seguimiento.exception.CobranzaDAOException;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.util.Utilerias;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AfectacionPorNotificacionBRImpl implements AfectacionPorNotificacionBR {

    private static final Logger LOG = Logger.getLogger(AfectacionPorNotificacionBRImpl.class);
    @Autowired
    private DocumentoDAO documentoDAO;
    @Autowired
    private DiaInhabilDAO diaInhabilDAO;
    @Autowired
    private TipoDocEtapaDAO tipoDocEtapaDAO;
    @Autowired
    private DetalleDocumentoDAO detalleDAO;

    /**
     * Metodo que obtiene el estado siguiente del Documento segun la regla de
     * negocio RN02
     *
     * @param seguimintoARCA
     * @throws SGTServiceException
     * @return
     */
    @Override
    public Documento obtenEdoSigDoc(SeguimientoARCA seguimintoARCA) throws SGTServiceException {
        Documento documento;
        Integer edoDocumento;
        Integer edoDocArca;
        Integer tipoDocumento;
        tipoDocumento = getTipoDoc(seguimintoARCA.getIdDocumento());
        edoDocArca = seguimintoARCA.getIdEstadoDocumento();
        documento = getDocumento(seguimintoARCA.getIdDocumento());
        if (documento != null) {
            edoDocumento = documento.getUltimoEstado();
            int estadoActual = obtenEstadoSiguienteDoc(tipoDocumento, edoDocArca, edoDocumento);
            documento = calculaEstado(tipoDocumento, documento, seguimintoARCA, estadoActual);
        }
        return documento;
    }

    /**
     *
     * 2.1.Cuando el documento no sea de tipo carta se cambiar el estado del
     * documento de "Pendiente de Imprimir a "Pendiente de Notificar de acuerdo
     * a la RN02 y este estado quedara en espera de la retroalimentacion de
     * Notificaciones para el resultado de la diligenciacion 2.2.Cuando el
     * documento sea de tipo carta se cambiara el estado del documento de
     * "Pendiente de imprimir a Emitido.
     *
     * @param tipoDocumento
     * @param estadoARCA
     * @param estadoCOB
     * @return
     */
    public Integer obtenEstadoSiguienteDoc(int tipoDocumento, int estadoARCA, int estadoCOB) {
        if (estadoARCA == EstadoDocArcaEnum.ARCA_IMPRESO.getIdEdoDoc()) {
            if (estadoCOB == EstadoDocumentoEnum.PENDIENTE_DE_IMPRIMIR.getValor()) {
                if (tipoDocumento == TipoDocumentoEnum.CARTA_EXHORTO.getValor()) {
                    return EstadoDocumentoEnum.EMITIDO.getValor();
                } else if ((tipoDocumento == TipoDocumentoEnum.REQUERIMIENTO_MULTA.getValor())
                        || (tipoDocumento == TipoDocumentoEnum.REQUERIMIENTO_NORMAL.getValor())
                        || (tipoDocumento == TipoDocumentoEnum.REQUERIMIENTO_ACUMULADO.getValor())
                        || (tipoDocumento == TipoDocumentoEnum.REQUERIMIENTO_DIOT.getValor())) {
                    return EstadoDocumentoEnum.PENDIENTE_DE_NOTIFICAR.getValor();
                }
            }
        } else {
            return calculaEstadoDoc(estadoARCA, estadoCOB);
        }
        return 0;
    }

    /**
     * Metodo que obtiene estado correspondiente del documento
     *
     * 7 Cuando el resultado de la notificacion recibido sea cualquiera de los
     * siguientes:
     *
     * Otra C-R-H, Robo o Extravio, Otra A-L-R, Docto No Recibido, No trabajado,
     *
     * 7.1 El estado del documento de gestion debera cambiarse de "Pendiente de
     * Notificar" a "No Trabajado" y registrar la fecha en la que se recibio el
     * resultado por parte de notificacion
     *
     * 7.2 Cuando tenga el estado "Solventado Parcial" y no tenga fecha de
     * Notificacion cambiara el estado a "No Trabajado" y registrara la fecha de
     * no trabajado
     *
     * --------------------------- "FLUJO ALTERNO" ---------------------------
     * (1)Cuando el resultado de la diligienciacion sea "Notificado" y el estado
     * en COB se encuentre como "Cumpĺido sin Notificar" o "Solventado Parcial"
     * se afecta o asigna: - Estado: "Notificado"
     *
     * (2) Cuando el resultado de la diligenciacion sea "Notificado" y el estado
     * en COB se encuentre como "Cancelado" se queda en "Cancelado"
     *
     * (3-A) Cuando el resultado de la diligenciacion sea "No Localizado" y el
     * estado en COB se encuentre como "Cancelado" se queda en "Cancelado"
     *
     * (3-B) Cuando el resultado de la diligenciacion sea "No Localizado" y el
     * estado en COB se encuentre como "Cumplido sin Notificar" se afecta o
     * asigna: - Estado: No Localizado
     *
     * (4-A) Cuando el resultado de la diligenciacion sea "No Trabajado" y el
     * estado en COB se encuentre como "Cancelado" se queda en "Cancelado"
     *
     * (4-B) Cuando el resultado de la diligenciacion sea "No Trabajado" y el
     * estado en COB se encuentre como "Cumplido sin Notificar" se afecta o
     * asigna: - Estado: No Trabajado.
     *
     *
     * @param estadoARCA
     * @param estadoCOB
     * @return
     */
    private Integer calculaEstadoDoc(int estadoARCA, int estadoCOB) {
        if (isNotificadoARCA(estadoARCA)) {
            if (isValidoParaNotificadoCOB(estadoCOB)) {
                return EstadoDocumentoEnum.NOTIFICADO.getValor();
            } else if (estadoCOB == EstadoDocumentoEnum.CANCELADO.getValor()) {
                return EstadoDocumentoEnum.CANCELADO.getValor();
            }
        } else if (isNoLocalizadoARCA(estadoARCA)) {
            if (isValidoParaNoLocalizadoCOB(estadoCOB)) {
                return EstadoDocumentoEnum.NO_LOCALIZADO.getValor();
            } else if (estadoCOB == EstadoDocumentoEnum.CANCELADO.getValor()) {
                return EstadoDocumentoEnum.CANCELADO.getValor();
            }
        } else if (isNoTrabajadoARCA(estadoARCA)) {
            if (isValidoParaNoTrabajadoCOB(estadoCOB)) {
                return EstadoDocumentoEnum.NO_TRABAJADO.getValor();
            } else if (estadoCOB == EstadoDocumentoEnum.CANCELADO.getValor()) {
                return EstadoDocumentoEnum.CANCELADO.getValor();
            }
        }
        return 0;
    }

    /**
     * Metodo que nos retorna el documento con los valores modificados
     * dependiendo las reglas de negocio
     *
     * 6 Cuando el resultado de la notificacion recibido sea "No Localizado"
     *
     * 6-1 Cambiara el estado de "Pendiente de Notificar" a "No Localizado" y
     * registrara la fecha de no localizacion
     *
     * 6-2 Cuando tenga el estado "Solventado Parcial" Notificacion cambiara el
     * estado a "No localizado" y registrara la fecha de no localizacion
     *
     * Metodo que determina estado siguiente de un documento mediante la
     * evaluacion de los estados de este y su correspondientes siguiendo los
     * estados del catalogo correspondiente -Actualiza fecha impresion
     * -Actualiza fecha impresion y fecha fin periodo -Aplica regla06 para
     * obtencion de periodo de validez -Actualiza fecha No Trabajado -Actualiza
     * fecha no localiazado
     *
     * --------------------------- "FLUJO ALTERNO" ---------------------------
     *
     * Si es un flujo alterno continua con el proceso del mismo.
     *
     * @param tipoDocumento
     * @param documento
     * @param seguimintoARCA
     * @param estadoActual
     * @throws SGTServiceException
     * @return
     */
    private Documento calculaEstado(Integer tipoDocumento, Documento documento,
            SeguimientoARCA seguimintoARCA, int estadoActual)
            throws SGTServiceException {
        if (isFlujoAlterno(seguimintoARCA.getIdEstadoDocumento(), documento, estadoActual)
                && documento.getFechaNotificacion() == null) {
            return procesaFlujoAlterno(tipoDocumento, documento, seguimintoARCA, estadoActual);
        } else if (estadoActual == EstadoDocumentoEnum.PENDIENTE_DE_NOTIFICAR.getValor()
                && documento.getUltimoEstado() == EstadoDocumentoEnum.PENDIENTE_DE_IMPRIMIR.getValor()) {
            documento.setFechaImpresion(seguimintoARCA.getFechaResultado());
            documento.setUltimoEstado(estadoActual);
            return documento;
        } else if (estadoActual == EstadoDocumentoEnum.NOTIFICADO.getValor()
                && documento.getUltimoEstado() == EstadoDocumentoEnum.PENDIENTE_DE_NOTIFICAR.getValor()) {
            documento.setFechaNotificacion((Utilerias.setFechaTrunk(seguimintoARCA.getFechaResultado())));
            documento.setFechaVencimiento(calculaFechaValidezDoc(
                    tipoDocumento, documento.getIdEtapaVigilancia(),
                    seguimintoARCA.getFechaResultado()));
            documento.setUltimoEstado(estadoActual);
            return documento;
        } else if (estadoActual == EstadoDocumentoEnum.NO_TRABAJADO.getValor()
                && documento.getUltimoEstado() == EstadoDocumentoEnum.PENDIENTE_DE_NOTIFICAR.getValor()) {
            documento.setDateNoTrabajado(seguimintoARCA.getFechaResultado());
            documento.setUltimoEstado(estadoActual);
            return documento;
        } else if (estadoActual == EstadoDocumentoEnum.NO_LOCALIZADO.getValor()
                && documento.getUltimoEstado() == EstadoDocumentoEnum.PENDIENTE_DE_NOTIFICAR.getValor()) {
            documento.setFechaNoLocalizadoContribuyente(seguimintoARCA.getFechaResultado());
            documento.setUltimoEstado(estadoActual);
            return documento;
        }
        return documento;
    }

    /**
     * Metodo que calcula La fecha de termino del periodo de validez para el
     * Documento segun la regla de negocio RN06 -mientras la fecha inicial sea
     * menor o igual que la fecha final se cuentan los dias -si el dia de la
     * semana de la fecha es diferente de sabado, domingo y dia feriado -se
     * aumentan los dias de diferencia entre min y max -numero de dias Habiles;
     * -se suma 1 dia para hacer la validacion del siguiente dia.
     *
     * @param tipoDoc
     * @param etapaVigilancia
     * @param fechaNotificacion
     * @throws SGTServiceException
     * @return
     */
    @Override
    public Date calculaFechaValidezDoc(Integer tipoDoc, Integer etapaVigilancia,
            Date fechaNotificacion) throws SGTServiceException {
        try {
            int duracionPeriodo = 0;
            int diasHabiles = 0;
            List<DiaInhabil> lstDiaInhabil = null;
            lstDiaInhabil = diaInhabilDAO.todosDiaInhabil(fechaNotificacion);
            duracionPeriodo = getDuracionPeriodo(tipoDoc, etapaVigilancia);
            Calendar fechaFinPeriodo = new GregorianCalendar();
            fechaFinPeriodo.setTime(fechaNotificacion);
            fechaFinPeriodo.add(Calendar.DATE, 1);
            while ((fechaFinPeriodo.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
                    || (fechaFinPeriodo.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
                    || (isFeriado(fechaFinPeriodo.getTime(), lstDiaInhabil))) {
                fechaFinPeriodo.add(Calendar.DATE, 1);
            }
            while (diasHabiles < duracionPeriodo) {
                if ((fechaFinPeriodo.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY)
                        && (fechaFinPeriodo.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY)
                        && !(isFeriado(fechaFinPeriodo.getTime(), lstDiaInhabil))) {

                    diasHabiles++;
                }
                fechaFinPeriodo.add(Calendar.DATE, 1);
            }
            fechaFinPeriodo.add(Calendar.DATE, -1);
            return (fechaFinPeriodo.getTime());
        } catch (CobranzaDAOException ex) {
            throw new SGTServiceException(ex);
        }
    }

    /**
     * Metodo para consultar el numero dias para el periodo de Validez
     *
     * @param tipoDoc
     * @param etapaVigilancia
     * @throws SGTServiceException
     * @return
     */
    private int getDuracionPeriodo(Integer tipoDoc, Integer etapaVigilancia)
            throws SGTServiceException {
        Integer diasDeVencimineto = null;
        try {
            diasDeVencimineto = tipoDocEtapaDAO.getDiasDeVencimineto(
                    tipoDoc, etapaVigilancia);
        } catch (SeguimientoDAOException ex) {
            throw new SGTServiceException(ex);
        }
        return diasDeVencimineto;
    }

    /**
     * Metodo que valida si una fecha esta contenida en la lista de dias
     * inhabiles
     *
     * @param fechaNotificacion
     * @param lstDiaInhabil
     * @throws SGTServiceException
     * @return
     */
    private boolean isFeriado(Date fechaNotificacion, List<DiaInhabil> lstDiaInhabil)
            throws SGTServiceException {
        Calendar dateDiaInhabil = new GregorianCalendar();
        Calendar dateNotificacion = new GregorianCalendar();
        dateNotificacion.setTime(fechaNotificacion);
        if (lstDiaInhabil != null) {
            for (DiaInhabil dia : lstDiaInhabil) {
                dateDiaInhabil.setTime(dia.getFecha());
                if ((dateDiaInhabil.getTime()).equals(dateNotificacion.getTime())) {
                    return true;
                }
            }
        } else {
            return false;
        }
        return false;
    }

    /**
     * --------------------------- "FLUJO ALTERNO" ---------------------------
     * (1)Cuando el resultado de la diligienciacion sea "Notificado" y el estado
     * en COB se encuentre como "Cumpĺido sin Notificar" o "Solventado Parcial"
     * se afecta o asigna: - Estado: "Notificado" - Fecha Notificacion - Fecha
     * Vencimiento - Situacion ICEP: "Nicumplido"
     *
     * (2) Cuando el resultado de la diligenciacion sea "Notificado" y el estado
     * en COB se encuentre como "Cancelado" se afecta o asigna: - Fecha
     * Notificacion
     *
     * (3-A) Cuando el resultado de la diligenciacion sea "No Localizado" y el
     * estado en COB se encuentre como "Cancelado" se afecta o asigna: - Fecha
     * Localizacion
     *
     * (3-B) Cuando el resultado de la diligenciacion sea "No Localizado" y el
     * estado en COB se encuentre como "Cumplido sin Notificar" se afecta o
     * asigna: - Estado: No Localizado - Fecha Localizacion
     *
     * (4-A) Cuando el resultado de la diligenciacion sea "No Trabajado" y el
     * estado en COB se encuentre como "Cancelado" se afecta o asigna: - Fecha
     * no Trabajado
     *
     * (4-B) Cuando el resultado de la diligenciacion sea "No Trabajado" y el
     * estado en COB se encuentre como "Cumplido sin Notificar" se afecta o
     * asigna: - Estado: No Trabajado - Fecha no Trabajado.
     *
     * @param tipoDocumento
     * @param documento
     * @param seguimintoARCA
     * @param estadoActual
     * @throws SGTServiceException
     * @return
     */
    private Documento procesaFlujoAlterno(Integer tipoDocumento, Documento documento,
            SeguimientoARCA seguimintoARCA, int estadoActual)
            throws SGTServiceException {
        if (isNotificadoARCA(seguimintoARCA.getIdEstadoDocumento())) {
            return accionesNotificadoARCA(documento, estadoActual, seguimintoARCA, tipoDocumento);
        } else if (isNoLocalizadoARCA(seguimintoARCA.getIdEstadoDocumento())) {
            return accionesNoLocalizadoARCA(documento, estadoActual, seguimintoARCA);
        } else if (isNoTrabajadoARCA(seguimintoARCA.getIdEstadoDocumento())) {
            return accionesNoTrabajadoARCA(documento, estadoActual, seguimintoARCA);
        }
        return documento;
    }

    /**
     * ------------- Metodos auxiliares para el proceso ------------.
     */
    /**
     * Obtiene el tipo del documento por numero de control.
     *
     * @param numControl
     * @throws SGTServiceException
     * @return
     */
    private Integer getTipoDoc(String numControl) throws SGTServiceException {
        Integer tipoDoc = documentoDAO.consultaTipoDocumento(numControl);
        if (tipoDoc == null) {
            throw new SGTServiceException("No se encontro tipo de documento asociado");
        }
        return tipoDoc;
    }

    /**
     * Consulta el documeto por numero de control.
     *
     * @param numControl
     * @throws SGTServiceException
     * @return
     */
    private Documento getDocumento(String numControl) throws SGTServiceException {
        Documento documento = documentoDAO.consultaDocumento(numControl);
        if (documento == null) {
            throw new SGTServiceException("No hay documentos con ese numero de control");
        }
        return documento;
    }

    /**
     * Regresa true si en ARCA se tiene un estado que para COB equivale a un
     * estado "Notificado".
     *
     * @param estadoARCA
     * @return
     */
    private boolean isNotificadoARCA(int estadoARCA) {
        if ((estadoARCA == EstadoDocArcaEnum.ARCA_ENTREGADO.getIdEdoDoc())
                || (estadoARCA == EstadoDocArcaEnum.ARCA_LOCALIZADO.getIdEdoDoc())) {
            return true;
        }
        return false;
    }

    /**
     * Regresa true solo cuando el estado en ARCA sea "Notificado" y el flujo
     * este considerado.
     *
     * @param documento
     * @param estadoActual
     * @return
     */
    private boolean isValidoFlujoNotificadoARCA(Documento documento, int estadoActual) {
        return (isCumplidoSinNotificarOrSolventadoParcialANotificado(documento.getUltimoEstado(), estadoActual)
                || isCanceladoACancelado(documento.getUltimoEstado(), estadoActual));

    }

    /**
     * Regresa true si en ARCA se tiene un estado que para COB equivale a un
     * estado "No Localizado".
     *
     * @param estadoARCA
     * @return
     */
    private boolean isNoLocalizadoARCA(int estadoARCA) {
        if ((estadoARCA == EstadoDocArcaEnum.ARCA_NO_LOCALIZADO.getIdEdoDoc())
                || (estadoARCA == EstadoDocArcaEnum.ARCA_DEV_NO_LOCALIZADO.getIdEdoDoc())
                || (estadoARCA == EstadoDocArcaEnum.ARCA_DEV_CAMBIO_DOM.getIdEdoDoc())
                || (estadoARCA == EstadoDocArcaEnum.ARCA_DEV_NO_EXISTE_CALLE.getIdEdoDoc())
                || (estadoARCA == EstadoDocArcaEnum.ARCA_DEV_NO_EXISTE_NUMERO.getIdEdoDoc())
                || (estadoARCA == EstadoDocArcaEnum.ARCA_DEV_DOM_INSUFICIENTE.getIdEdoDoc())
                || (estadoARCA == EstadoDocArcaEnum.ARCA_DEV_FALLECIO.getIdEdoDoc())
                || (estadoARCA == EstadoDocArcaEnum.ARCA_DEV_OTRO_DESTINATARIO.getIdEdoDoc())
                || (estadoARCA == EstadoDocArcaEnum.ARCA_DEV_NO_RECLAMADO.getIdEdoDoc())
                || (estadoARCA == EstadoDocArcaEnum.ARCA_DEV_NO_QUISO_RECIBIR.getIdEdoDoc())
                || (estadoARCA == EstadoDocArcaEnum.ARCA_DEV_SE_ENCUENTRA_EN.getIdEdoDoc())
                || (estadoARCA == EstadoDocArcaEnum.ARCA_DEV_NO_TRABAJADO.getIdEdoDoc())
                || (estadoARCA == EstadoDocArcaEnum.ARCA_DEV_OTRAS.getIdEdoDoc())) {
            return true;
        }
        return false;
    }

    /**
     * Regresa true solo cuando el estado en ARCA sea "No Lozalizado" y el flujo
     * este considerado.
     *
     * @param documento
     * @param estadoActual
     * @return
     */
    private boolean isValidoFlujoNoLocalizadoARCA(Documento documento, int estadoActual) {
        return isCanceladoACancelado(documento.getUltimoEstado(), estadoActual)
                || (isCumplidoSinNotificarANoLocalizado(documento.getUltimoEstado(), estadoActual)
                || isSolventadoParcialANoLocalizado(documento, estadoActual));
    }

    /**
     * Regresa true si en ARCA se tiene un estado que para COB equivale a un
     * estado "No Trabajado".
     *
     * @param estadoARCA
     * @return
     */
    private boolean isNoTrabajadoARCA(int estadoARCA) {
        if ((estadoARCA == EstadoDocArcaEnum.ARCA_NO_TRABAJADO.getIdEdoDoc())
                || (estadoARCA == EstadoDocArcaEnum.ARCA_DISTINTA_CRH.getIdEdoDoc())
                || (estadoARCA == EstadoDocArcaEnum.ARCA_ROBO_O_EXTRAVIO.getIdEdoDoc())
                || (estadoARCA == EstadoDocArcaEnum.ARCA_HUELGA.getIdEdoDoc())
                || (estadoARCA == EstadoDocArcaEnum.ARCA_FUSION.getIdEdoDoc())
                || (estadoARCA == EstadoDocArcaEnum.ARCA_DISTINTA_ALR.getIdEdoDoc())) {
            return true;
        }
        return false;
    }

    /**
     * Regresa true solo cuando el estado en ARCA sea "No Trabajado" y el flujo
     * este considerado.
     *
     * @param documento
     * @param estadoActual
     * @return
     */
    private boolean isValidoFlujoNoTrabajadoARCA(Documento documento, int estadoActual) {
        return isCanceladoACancelado(documento.getUltimoEstado(), estadoActual)
                || (isCumplidoSinNotificarANoTrabajado(documento.getUltimoEstado(), estadoActual)
                || isSolventadoParcialANoTrabajado(documento, estadoActual));
    }

    /**
     * Regresa true si es apto para cambiar al estado "Notificado".
     *
     * @param estadoCOB
     * @return
     */
    private boolean isValidoParaNotificadoCOB(int estadoCOB) {
        if ((estadoCOB == EstadoDocumentoEnum.PENDIENTE_DE_NOTIFICAR.getValor())
                || (estadoCOB == EstadoDocumentoEnum.EMITIDO.getValor())
                || (estadoCOB == EstadoDocumentoEnum.CUMPLIDO_SIN_NOTIFICAR.getValor())
                || (estadoCOB == EstadoDocumentoEnum.SOLVENTADO_PARCIAL.getValor())) {
            return true;
        }
        return false;
    }

    /**
     * Regresa true si es apto para cambiar al estado "No Localizado".
     *
     * @param estadoCOB
     * @return
     */
    private boolean isValidoParaNoLocalizadoCOB(int estadoCOB) {
        if ((estadoCOB == EstadoDocumentoEnum.PENDIENTE_DE_NOTIFICAR.getValor())
                || (estadoCOB == EstadoDocumentoEnum.SOLVENTADO_PARCIAL.getValor())
                || (estadoCOB == EstadoDocumentoEnum.CUMPLIDO_SIN_NOTIFICAR.getValor())) {
            return true;
        }
        return false;
    }

    /**
     * Regresa true si es apto para cambiar al estado "No Trabajado".
     *
     * @param estadoCOB
     * @return
     */
    private boolean isValidoParaNoTrabajadoCOB(int estadoCOB) {
        return (estadoCOB == EstadoDocumentoEnum.PENDIENTE_DE_NOTIFICAR.getValor())
                || (estadoCOB == EstadoDocumentoEnum.SOLVENTADO_PARCIAL.getValor())
                || (estadoCOB == EstadoDocumentoEnum.CUMPLIDO_SIN_NOTIFICAR.getValor());
    }

    /**
     * Metodo que realiza las acciones cuando el estado es "Solventado Parcial"
     * o "Cumplido sin Notifiacion" a un estado "Notificado".
     *
     * @param documento
     * @param seguimintoARCA
     * @param tipoDocumento
     * @throws SGTServiceException
     * @return
     */
    private Documento accionSolventadoParcialCumplidoSinNotificarANotificado(
            Documento documento, SeguimientoARCA seguimintoARCA, Integer tipoDocumento)
            throws SGTServiceException {
        try {
            documento.setUltimoEstado(EstadoDocumentoEnum.NOTIFICADO.getValor());
            documento.setFechaNotificacion((Utilerias.setFechaTrunk(seguimintoARCA.getFechaResultado())));
            documento.setFechaVencimiento(calculaFechaValidezDoc(
                    tipoDocumento, documento.getIdEtapaVigilancia(), seguimintoARCA.getFechaResultado()));
            detalleDAO.cambiarEstadoICEPsPorNumControl(
                    documento, SituacionIcepEnum.CUMPLIDO, SituacionIcepEnum.INCUMPLIDO);
        } catch (SeguimientoDAOException ex) {
            LOG.error(ex);
            throw new SGTServiceException(ex);
        }
        return documento;
    }

    /**
     * Regresa true cuando alguna validacion pertenezca al flujo alterno segun
     * el caso de uso.
     *
     * @param idEstadoDocumento
     * @param documento
     * @param estadoActual
     * @return
     */
    private boolean isFlujoAlterno(Integer idEstadoDocumento,
            Documento documento, int estadoActual) {
        if (isNotificadoARCA(idEstadoDocumento)
                && isValidoFlujoNotificadoARCA(documento, estadoActual)) {
            if (isCumplidoSinNotificarOrSolventadoParcialANotificado(documento.getUltimoEstado(), estadoActual)
                    || isCanceladoACancelado(documento.getUltimoEstado(), estadoActual)) {
                return true;
            }
        } else if (isNoLocalizadoARCA(idEstadoDocumento)
                && isValidoFlujoNoLocalizadoARCA(documento, estadoActual)) {
            if (isCanceladoACancelado(documento.getUltimoEstado(), estadoActual)
                    || isCumplidoSinNotificarANoLocalizado(documento.getUltimoEstado(), estadoActual)
                    || isSolventadoParcialANoLocalizado(documento, estadoActual)) {
                return true;
            }
        } else if (isNoTrabajadoARCA(idEstadoDocumento)
                && isValidoFlujoNoTrabajadoARCA(documento, estadoActual)) {
            if (isCanceladoACancelado(documento.getUltimoEstado(), estadoActual)
                    || isCumplidoSinNotificarANoTrabajado(documento.getUltimoEstado(), estadoActual)
                    || isSolventadoParcialANoTrabajado(documento, estadoActual)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Regresa true si el estado en COB es "Solventado Parcial" o "Cumplido sin
     * Notificar" y se le va a asignar un estado "Notificado".
     *
     * @param estadoDocto
     * @param estadoActual
     * @return
     */
    boolean isCumplidoSinNotificarOrSolventadoParcialANotificado(
            int estadoDocto, int estadoActual) {
        return isSolventadoParcialOrIsCumplidoSinNotificar(estadoDocto)
                && (estadoActual == EstadoDocumentoEnum.NOTIFICADO.getValor());
    }

    /**
     * Regrersa true si el estado actual es "Solventado Parcial" o "Cumplido sin
     * Notificar".
     *
     * @param estadoDocto
     * @return
     */
    private boolean isSolventadoParcialOrIsCumplidoSinNotificar(int estadoDocto) {
        return estadoDocto == EstadoDocumentoEnum.SOLVENTADO_PARCIAL.getValor()
                || estadoDocto == EstadoDocumentoEnum.CUMPLIDO_SIN_NOTIFICAR.getValor();
    }

    /**
     * Regresa true si el estado en COB es "Cancelado" y se le va a asignar un
     * estado "Cancelado".
     *
     * @param estadoDocto
     * @param estadoActual
     * @return
     */
    boolean isCanceladoACancelado(int estadoDocto, int estadoActual) {
        return estadoDocto == EstadoDocumentoEnum.CANCELADO.getValor()
                && (estadoActual == EstadoDocumentoEnum.CANCELADO.getValor());
    }

    /**
     * Regresa true si el estado en COB es "Cumplido sin Notificar" y se le va a
     * asignar un estado "No Localizado".
     *
     * @param estadoDocto
     * @param estadoActual
     * @return
     */
    boolean isCumplidoSinNotificarANoLocalizado(int estadoDocto, int estadoActual) {
        return estadoDocto == EstadoDocumentoEnum.CUMPLIDO_SIN_NOTIFICAR.getValor()
                && (estadoActual == EstadoDocumentoEnum.NO_LOCALIZADO.getValor());
    }

    /**
     * Regresa true si el estado en COB es "Cumplido sin Notificar" y se le va a
     * asignar un estado "No trabajado".
     *
     * @param estadoDocto
     * @param estadoActual
     * @return
     */
    boolean isCumplidoSinNotificarANoTrabajado(int estadoDocto, int estadoActual) {
        return estadoDocto == EstadoDocumentoEnum.CUMPLIDO_SIN_NOTIFICAR.getValor()
                && estadoActual == EstadoDocumentoEnum.NO_TRABAJADO.getValor();
    }

    /**
     * Regresa true si el estado en COB es "Solventado Parcial" y se le va a
     * asignar un estado "No Localizado".
     *
     * @param docto
     * @param estadoActual
     * @return
     */
    private boolean isSolventadoParcialANoLocalizado(Documento docto, int estadoActual) {
        return docto.getUltimoEstado() == EstadoDocumentoEnum.SOLVENTADO_PARCIAL.getValor()
                && estadoActual == EstadoDocumentoEnum.NO_LOCALIZADO.getValor()
                && docto.getFechaNotificacion() == null;
    }

    /**
     * Regresa true si el estado en COB es "Solventado Parcial" y se le va a
     * asignar un estado "No trabajado".
     *
     * @param docto
     * @param estadoActual
     * @return
     */
    private boolean isSolventadoParcialANoTrabajado(Documento docto, int estadoActual) {
        return docto.getUltimoEstado() == EstadoDocumentoEnum.SOLVENTADO_PARCIAL.getValor()
                && estadoActual == EstadoDocumentoEnum.NO_TRABAJADO.getValor()
                && docto.getFechaNotificacion() == null;
    }

    /**
     * Acciones a tomar cuando en arca se encuentre como estado "No Localizado".
     *
     * @param documento
     * @param estadoActual
     * @param seguimintoARCA
     * @return
     */
    private Documento accionesNoLocalizadoARCA(Documento documento, int estadoActual, SeguimientoARCA seguimintoARCA) {
        if (isCanceladoACancelado(documento.getUltimoEstado(), estadoActual)) {
            documento.setFechaNoLocalizadoContribuyente(seguimintoARCA.getFechaResultado());
            return documento;
        } else if (isCumplidoSinNotificarANoLocalizado(documento.getUltimoEstado(), estadoActual)
                || isSolventadoParcialANoLocalizado(documento, estadoActual)) {
            documento.setUltimoEstado(EstadoDocumentoEnum.NO_LOCALIZADO.getValor());
            documento.setFechaNoLocalizadoContribuyente(seguimintoARCA.getFechaResultado());
            return documento;
        }
        return documento;
    }

    /**
     * Acciones a tomar cuando en arca se encuentre como estado "Notificado".
     *
     * @param documento
     * @param estadoActual
     * @param seguimintoARCA
     * @param tipoDocumento
     * @return
     */
    private Documento accionesNotificadoARCA(Documento documento, int estadoActual,
            SeguimientoARCA seguimintoARCA, Integer tipoDocumento) throws SGTServiceException {
        if (isCumplidoSinNotificarOrSolventadoParcialANotificado(documento.getUltimoEstado(), estadoActual)) {
            return accionSolventadoParcialCumplidoSinNotificarANotificado(
                    documento, seguimintoARCA, tipoDocumento);
        } else if (isCanceladoACancelado(documento.getUltimoEstado(), estadoActual)) {
            documento.setFechaNotificacion((Utilerias.setFechaTrunk(seguimintoARCA.getFechaResultado())));
            return documento;
        }
        return documento;
    }

    /**
     * Acciones a tomar cuando en arca se encuentre como estado "No trabajado".
     *
     * @param documento
     * @param estadoActual
     * @param seguimintoARCA
     * @return
     */
    private Documento accionesNoTrabajadoARCA(Documento documento, int estadoActual,
            SeguimientoARCA seguimintoARCA) {
        if (isCanceladoACancelado(documento.getUltimoEstado(), estadoActual)) {
            documento.setDateNoTrabajado(seguimintoARCA.getFechaResultado());
            return documento;
        } else if (isCumplidoSinNotificarANoTrabajado(documento.getUltimoEstado(), estadoActual)
                || isSolventadoParcialANoTrabajado(documento, estadoActual)) {
            documento.setUltimoEstado(EstadoDocumentoEnum.NO_TRABAJADO.getValor());
            documento.setDateNoTrabajado(seguimintoARCA.getFechaResultado());
            return documento;
        }
        return documento;
    }

}
