/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.service.afectaciones.impl;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DetalleDocumento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Documento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.MultaDocumento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.MultaIcep;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.AfectacionEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoDocumentoEnum;
import static mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoDocumentoEnum.ATENDIDO_PARCIAL;
import static mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoDocumentoEnum.EMITIDO;
import static mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoDocumentoEnum.NOTIFICADO;
import static mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoDocumentoEnum.PENDIENTE_DE_IMPRIMIR;
import static mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoDocumentoEnum.PENDIENTE_DE_NOTIFICAR;
import static mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoDocumentoEnum.SOLVENTADO_PARCIAL;
import static mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoDocumentoEnum.VENCIDO;
import static mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoDocumentoEnum.VENCIDO_PARCIAL;
import static mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EtapaVigilanciaEnum.ETAPA_1;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.SituacionIcepEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoMedioEnvioEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoMultaEnum;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.service.afectaciones.AfectacionService;
import mx.gob.sat.siat.cob.seguimiento.service.afectaciones.CambiaEstadosCumplimientoService;
import mx.gob.sat.siat.cob.seguimiento.service.afectaciones.MultaService;
import mx.gob.sat.siat.cob.seguimiento.service.afectaciones.SolventarIcepsCumplimientoService;
import mx.gob.sat.siat.cob.seguimiento.service.extraccion.DocumentoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author root
 */
@Service
public class CambiaEstadosCumplimientoServiceImpl implements CambiaEstadosCumplimientoService {

    @Autowired
    private MultaService multaService;
    @Autowired
    private DocumentoService documentoService;
    @Autowired
    private AfectacionService afectacionService;
    @Autowired
    private SolventarIcepsCumplimientoService solventarIcepsCumplimientoService;
    private final Logger log = Logger.getLogger(CambiaEstadosCumplimientoServiceImpl.class);
    private boolean cambiaEstado;

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public void procesarCumplidoSinNotificacionTotal(Documento documento) throws SGTServiceException {
        cambiaEstado = true;
        EstadoDocumentoEnum estadoOriginal;
        estadoOriginal = documento.getEstadoDocumento();
        //Cambiar estatus a Cumplido sin Notificar
        cambiaEstado = documentoService.actualizaEstadoBitacoraDocumento(documento, EstadoDocumentoEnum.CUMPLIDO_SIN_NOTIFICAR);
        log.info(documento.getNumeroControl() + "Requerimiento--> Total -- ?-->Cumplido sin notificar");
        documento.setUltimoEstado(estadoOriginal.getValor());
        if (cambiaEstado && documento.getEstadoDocumento() == EstadoDocumentoEnum.PENDIENTE_DE_IMPRIMIR) {
            //Cancelacion de documento
            afectacionService.cancelarDocumentoARCA(documento);
        }

        solventarIcepsCumplimientoService.solventarICEPS(documento.getDetalles());
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public void procesarCumplidoSinNotificacionParcial(Documento documento) throws SGTServiceException {
        cambiaEstado = true;
        switch (documento.getEstadoDocumento()) {
            case PENDIENTE_DE_IMPRIMIR:
                //antes de cancelar se genera nuevo documento
                //Generar documento nuevo
                afectacionService.generaDocumentosAfectacion(documento, AfectacionEnum.AFECTACION_CUMPLIMIENTO);
                //Cambiar estatus a Cancelado
                afectacionService.cancelarDocumento(documento);

                log.info(documento.getNumeroControl() + "Requerimiento--> Parcial -- Pendiente de Imprimir-->Cancelado");
                break;
            case PENDIENTE_DE_NOTIFICAR:
                //Cambiar estatus a Solventado Parcial
                cambiaEstado = documentoService.actualizaEstadoBitacoraDocumento(documento, EstadoDocumentoEnum.SOLVENTADO_PARCIAL);
                log.info(documento.getNumeroControl() + "Requerimiento--> Parcial -- Pendiente de Notificar-->Solventado Parcial");
                break;
            default:
                mensajeDocumentoRecibido("procesarCumplidoSinNotificacionParcial", documento.getEstadoDocumento());
        }
        solventarIcepsCumplimientoService.solventarICEPS(documento.getDetalles());
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public void procesarCumplidoAntesDeNotificacionTotal(Documento documento, Documento documentoOriginal) throws SGTServiceException {
        cambiaEstado = true;
        MultaDocumento multaDocumento;
        EstadoDocumentoEnum estadoOriginal;
        estadoOriginal = documento.getEstadoDocumento();
        cambiaEstado = documentoService.actualizaEstadoBitacoraDocumento(documento, EstadoDocumentoEnum.CUMPLIDO_ANTES_DE_LA_NOTIFICACION);
        log.info(documento.getNumeroControl() + "Requerimiento--> TOTAL -- ?-->Cumplido antes de Notificacion");
        documento.setUltimoEstado(estadoOriginal.getValor());
        if (cambiaEstado
                && (documento.getEstadoDocumento() == VENCIDO
                || (documento.getEstadoDocumento() == VENCIDO_PARCIAL))) {
            multaDocumento = obtenerMultaTipo(documento, TipoMultaEnum.INCUMPLIMIENTO);
            if (multaDocumento == null) {
                log.info("El documento " + documento.getNumeroControl() + " tiene un estado de " + documento.getEstadoDocumento()
                        + " pero no tiene multa por incumplimiento.");
            } else if (verificaIcepsMultaIncumplimiento(multaDocumento, documento, documentoOriginal)) {
                //Cancela multa
                multaService.cancelarMulta(multaDocumento);
            }
        }
        solventarIcepsCumplimientoService.solventarICEPS(documento.getDetalles());
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public void procesarCumplidoAntesDeNotificacionParcial(Documento documento, Documento documentoOriginal) throws SGTServiceException {
        switch (documento.getEstadoDocumento()) {
            case NOTIFICADO:
                //Mofica su estatus a Solventado Parcial
                cambiaEstado = documentoService.actualizaEstadoBitacoraDocumento(documento, EstadoDocumentoEnum.SOLVENTADO_PARCIAL);
                log.info(documento.getNumeroControl() + "Requerimiento--> Parcial -- Notificado-->Solventado Parcial");
                break;
            case VENCIDO:
                //Mofica su estatus a Vencido Parcial
                cambiaEstado = documentoService.actualizaEstadoBitacoraDocumento(documento, EstadoDocumentoEnum.VENCIDO_PARCIAL);
                log.info(documento.getNumeroControl() + "Requerimiento--> Parcial -- VENCIDO-->Vencido Parcial");
                break;
            default:
                mensajeDocumentoRecibido("procesarCumplidoAntesDeNotificacionParcial", documento.getEstadoDocumento());
                break;
        }
        solventarIcepsCumplimientoService.solventarICEPS(documento.getDetalles());
        solventarICEPSvirtual(documento, documentoOriginal);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public void procesarCumplidoDentroDelPlazoDeNotificacionTotal(Documento documento, Documento documentoOriginal) throws SGTServiceException {
        cambiaEstado = true;
        MultaDocumento multaDocumento;
        EstadoDocumentoEnum estadoOriginal;
        estadoOriginal = documento.getEstadoDocumento();
        cambiaEstado = documentoService.actualizaEstadoBitacoraDocumento(documento, EstadoDocumentoEnum.CUMPLIDO_DENTRO_DEL_PLAZO);
        log.info(documento.getNumeroControl() + "Requerimiento--> TOTAL -- ?-->Cumplido dentro del plazo");
        documento.setUltimoEstado(estadoOriginal.getValor());

        if (cambiaEstado) {
            if (documento.getEstadoDocumento() == VENCIDO
                    || (documento.getEstadoDocumento() == VENCIDO_PARCIAL)) {
                multaDocumento = obtenerMultaTipo(documento, TipoMultaEnum.INCUMPLIMIENTO);
                if (multaDocumento == null) {
                    log.info("El documento " + documento.getNumeroControl() + " tiene un estado de " + documento.getEstadoDocumento()
                            + " pero no tiene multa por incumplimiento.");
                } else if (verificaIcepsMultaIncumplimiento(multaDocumento, documento, documentoOriginal)) {
                    //Cancela multa
                    multaService.cancelarMulta(multaDocumento);
                }

            }

            if (documento.getEtapaVigilancia().equals(ETAPA_1)
                    && documento.getVigilancia().getIdTipoMedioEnum() != TipoMedioEnvioEnum.ENTIDAD_FEDERATIVA) {
                //genera multa extemporaneidad
                multaService.generarMulta(documento, TipoMultaEnum.EXTEMPORANEIDAD);
            }
        }

        solventarIcepsCumplimientoService.solventarICEPS(documento.getDetalles());

    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public void procesarCumplidoDentroDelPlazoDeNotificacionParcial(Documento documento, Documento documentoOriginal) throws SGTServiceException {
        cambiaEstado = true;
        switch (documento.getEstadoDocumento()) {
            case NOTIFICADO:
                //Mofica su estatus a Solventado Parcial
                cambiaEstado = documentoService.actualizaEstadoBitacoraDocumento(documento, EstadoDocumentoEnum.SOLVENTADO_PARCIAL);
                log.info(documento.getNumeroControl() + "Requerimiento--> Parcial -- Notificado-->Solventado Parcial");
                break;
            case VENCIDO:
                //Mofica su estatus a Vencido Parcial
                cambiaEstado = documentoService.actualizaEstadoBitacoraDocumento(documento, EstadoDocumentoEnum.VENCIDO_PARCIAL);
                log.info(documento.getNumeroControl() + "Requerimiento--> Parcial -- VENCIDO-->Vencido Parcial");
                break;
            default:
                mensajeDocumentoRecibido("procesarCumplidoDentroDelPlazoDeNotificacionParcial", documento.getEstadoDocumento());
                break;
        }
        if (cambiaEstado && documento.getEtapaVigilancia().equals(ETAPA_1)
                && documento.getVigilancia().getIdTipoMedioEnum() != TipoMedioEnvioEnum.ENTIDAD_FEDERATIVA) {
            //genera multa extemporaneidad
            multaService.generarMulta(documento, TipoMultaEnum.EXTEMPORANEIDAD);
        }
        solventarIcepsCumplimientoService.solventarICEPS(documento.getDetalles());
        solventarICEPSvirtual(documento, documentoOriginal);

    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public void procesarCumplidoFueraDelPlazoDeNotificacionTotal(Documento documento) throws SGTServiceException {
        cambiaEstado = true;

        EstadoDocumentoEnum estadoOriginal;
        estadoOriginal = documento.getEstadoDocumento();
        cambiaEstado = documentoService.actualizaEstadoBitacoraDocumento(documento, EstadoDocumentoEnum.CUMPLIDO_FUERA_DEL_PLAZO);
        log.info(documento.getNumeroControl() + "Requerimiento--> TOTAL -- ?-->Cumplido fuera del plazo");
        documento.setUltimoEstado(estadoOriginal.getValor());

        if (cambiaEstado && documento.getEtapaVigilancia().equals(ETAPA_1)
                && documento.getVigilancia().getIdTipoMedioEnum() != TipoMedioEnvioEnum.ENTIDAD_FEDERATIVA) {
            switch (documento.getEstadoDocumento()) {
                case NOTIFICADO:
                case SOLVENTADO_PARCIAL:
                    multaService.generarMulta(documento, TipoMultaEnum.INCUMPLIMIENTO_EXTEMPORANEIDAD);
                    break;
                case VENCIDO:
                case VENCIDO_PARCIAL:
                    //genera multa extemporaneidad
                    multaService.generarMulta(documento, TipoMultaEnum.EXTEMPORANEIDAD);
                    break;
                default:
                    mensajeDocumentoRecibido("procesarCumplidoFueraDelPlazoDeNotificacionTotal", documento.getEstadoDocumento());
            }
        }
        solventarIcepsCumplimientoService.solventarICEPS(documento.getDetalles());
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public void procesarCumplidoFueraDelPlazoDeNotificacionParcial(Documento documento) throws SGTServiceException {
        cambiaEstado = true;
        switch (documento.getEstadoDocumento()) {
            case NOTIFICADO:
                cambiaEstado = documentoService.actualizaEstadoBitacoraDocumento(documento, EstadoDocumentoEnum.SOLVENTADO_PARCIAL);
                log.info(documento.getNumeroControl() + "Requerimiento--> Parcial -- NOTIFICADO-->Vencido Parcial");
                break;
            case VENCIDO:
                cambiaEstado = documentoService.actualizaEstadoBitacoraDocumento(documento, EstadoDocumentoEnum.VENCIDO_PARCIAL);
                log.info(documento.getNumeroControl() + "Requerimiento--> Parcial -- VENCIDO-->Vencido Parcial");
                break;
            default:
                mensajeDocumentoRecibido("procesarCumplidoFueraDelPlazoDeNotificacionParcial", documento.getEstadoDocumento());
                break;
        }
        if (cambiaEstado && documento.getEtapaVigilancia().equals(ETAPA_1)
                && documento.getVigilancia().getIdTipoMedioEnum() != TipoMedioEnvioEnum.ENTIDAD_FEDERATIVA) {
            switch (documento.getEstadoDocumento()) {
                case SOLVENTADO_PARCIAL:
                    multaService.generarMulta(documento, TipoMultaEnum.INCUMPLIMIENTO_EXTEMPORANEIDAD);
                    break;
                case VENCIDO_PARCIAL:
                    //genera multa extemporaneidad
                    multaService.generarMulta(documento, TipoMultaEnum.EXTEMPORANEIDAD);
                    break;
                default:
                    mensajeDocumentoRecibido("procesarCumplidoFueraDelPlazoDeNotificacionParcial", documento.getEstadoDocumento());
                    break;
            }
        }
        solventarIcepsCumplimientoService.solventarICEPS(documento.getDetalles());
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public void procesaCartaExhortoTotal(Documento documento) throws SGTServiceException {
        cambiaEstado = true;
        EstadoDocumentoEnum estadoOriginal;
        estadoOriginal = documento.getEstadoDocumento();
        cambiaEstado = documentoService.actualizaEstadoBitacoraDocumento(documento, EstadoDocumentoEnum.CUMPLIDO);
        documento.setUltimoEstado(estadoOriginal.getValor());
        if (cambiaEstado) {
            switch (documento.getEstadoDocumento()) {
                case PENDIENTE_DE_IMPRIMIR:
                    //Se cancela la impresion del documento
                    //Cambio de estado a cumplido
                    afectacionService.cancelarDocumentoARCA(documento);
                    log.info(documento.getNumeroControl() + "Carta--> Cumplido Total-- Pendiente de Imprimir-->Paso a Cumplido ");
                    break;
                case EMITIDO:
                    //Cambio de estado a cumplido
                    log.info(documento.getNumeroControl() + "Carta--> Cumplido Total-- Emitido -->Paso a Cumplido ");
                    break;
                case ATENDIDO_PARCIAL:
                    //Cambio de estado a cumplido
                    log.info(documento.getNumeroControl() + "Carta--> Cumplido Total-- Atendido Parcial-->Paso a Cumplido ");
                    break;
                default:
                    log.info("Se recibe una carta tipo " + documento.getVigilancia().getIdTipoDocumentoEnum());
                    break;
            }
        }
        solventarIcepsCumplimientoService.solventarICEPS(documento.getDetalles());

    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public void procesaCartaExhortoParcial(Documento documento) throws SGTServiceException {
        cambiaEstado = true;
        switch (documento.getEstadoDocumento()) {
            case PENDIENTE_DE_IMPRIMIR:
                cambiaEstado = documentoService.actualizaEstadoBitacoraDocumento(documento, EstadoDocumentoEnum.ANULADO);
                log.info(documento.getNumeroControl() + "Carta--> Cumplido Parcial-- Pendiente de Imprimir-->Paso a Anulado");
                if (cambiaEstado) {
                    //Genera nuevo documento con los iceps que aun son omisos
                    afectacionService.generaDocumentosAfectacion(documento, AfectacionEnum.AFECTACION_CUMPLIMIENTO);
                    //Se cancela la impresion del documento
                    //Cambio de estado a anulado
                    afectacionService.cancelarDocumentoARCA(documento);
                }
                break;
            case EMITIDO:
                //Cambio de estado a Atendido Parcial
                cambiaEstado = documentoService.actualizaEstadoBitacoraDocumento(documento, EstadoDocumentoEnum.ATENDIDO_PARCIAL);
                log.info(documento.getNumeroControl() + "Carta--> Cumplido Parcial-- Emitido -->Atendido Parcial");
                break;
            case ATENDIDO_PARCIAL:
                //Permanece en ATENDIDO_PARCIAL
                log.info(documento.getNumeroControl() + "Carta--> Cumplido Parcial-- Atendido Parcial-->Permanece en el estado");
                break;
            default:
                log.info("Se recibe una carta tipo " + documento.getVigilancia().getIdTipoDocumentoEnum());
                break;
        }
        solventarIcepsCumplimientoService.solventarICEPS(documento.getDetalles());

    }

    private void mensajeDocumentoRecibido(String metodo, EstadoDocumentoEnum estadoDocumento) {
        log.info("Se recibe un estado de documento " + estadoDocumento + " en el método " + metodo);

    }
    //busca la multa de incumplimiento y verifica que los iceps  que se estan cumpliendo sean los mismos de la multa que se va a cancelar
    //este metodo es llamado solo cuando se cumple el documento totalmente dentro o antes de la notificacion.

    private boolean verificaIcepsMultaIncumplimiento(MultaDocumento multaDocumento, Documento documento, Documento documentoOriginal) throws SGTServiceException {
        //verificamos que los todos los iceps de la multa que se quiere cancelar esten en los detalles del documento cumplido
        boolean encontrado;
        List<MultaIcep> iceps = multaDocumento.getIceps();
        List<DetalleDocumento> detalles = documento.getDetalles();
        List<DetalleDocumento> detallesOriginal = documentoOriginal.getDetalles();

        for (MultaIcep icep : iceps) {
            encontrado = false;
            for (DetalleDocumento detalle : detalles) {
                if (detalle.getClaveIcep().equals(icep.getClaveIcep())) {
                    encontrado = true;
                }
            }
            if (!encontrado) {
                //si no fue encontrado en estos cumplimientos, buscamos en el documento original
                for (DetalleDocumento detalleOriginal : detallesOriginal) {
                    if (detalleOriginal.getClaveIcep().equals(icep.getClaveIcep())
                            && detalleOriginal.getSituacionIcep() == SituacionIcepEnum.CUMPLIDO
                            && detalleOriginal.getFechaCumplimiento().compareTo(documentoOriginal.getFechaVencimiento()) <= 0) {
                        encontrado = true;
                        break;
                    }
                }
            }
            if (!encontrado) {
                return encontrado;
            }

        }
        return true;
    }

    private MultaDocumento obtenerMultaTipo(Documento documento, TipoMultaEnum tipoMulta) throws SGTServiceException {
        MultaDocumento multaDocumento = new MultaDocumento();
        multaDocumento.setNumeroControl(documento.getNumeroControl());
        multaDocumento.setConstanteResolucionMotivo(tipoMulta.toString());
        List<MultaDocumento> multas = multaService.buscarMultasPorTipoYNumeroControl(multaDocumento);
        if (multas != null && !multas.isEmpty()) {
            return multas.get(0);
        }
        return null;
    }

    /**
     * Solventa en memoria los detalles del documento original, si a lo largo
     * del proceso se requiere verificar si están cumplidos no tendremos la
     * necesidad de buscarlos en memoria
     *
     * @param documento El documento virtual que sólo tiene los detalles que se
     * estan cumpliendo
     * @param documentoOriginal Documento original con todos los detalles
     */
    private void solventarICEPSvirtual(Documento documento, Documento documentoOriginal) {
        List<DetalleDocumento> detallesVirtual = documento.getDetalles();
        List<DetalleDocumento> detallesOriginal = documentoOriginal.getDetalles();

        for (DetalleDocumento detalleVirtual : detallesVirtual) {
            for (DetalleDocumento detalleOriginal : detallesOriginal) {
                if (detalleVirtual.getClaveIcep().equals(detalleOriginal.getClaveIcep())) {
                    detalleOriginal.setIdSituacionIcep(SituacionIcepEnum.CUMPLIDO.getValor());
                    break;
                }
            }
        }


    }
}
