/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.service.afectaciones.impl;

import java.util.Iterator;
import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.DetalleDocumentoDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DetalleDocumento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Documento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.MultaDocumento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.MultaIcep;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.AfectacionEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoDocumentoEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.SituacionIcepEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoDocumentoEnum;
import static mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoDocumentoEnum.CARTA_EXHORTO;
import static mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoDocumentoEnum.REQUERIMIENTO_ACUMULADO;
import static mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoDocumentoEnum.REQUERIMIENTO_DIOT;
import static mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoDocumentoEnum.REQUERIMIENTO_MULTA;
import static mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoDocumentoEnum.REQUERIMIENTO_NORMAL;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoMultaEnum;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.service.afectaciones.AfectacionService;
import mx.gob.sat.siat.cob.seguimiento.service.afectaciones.MultaService;
import mx.gob.sat.siat.cob.seguimiento.service.afectaciones.SolventarMovimientosPadronService;
import mx.gob.sat.siat.cob.seguimiento.service.extraccion.DocumentoService;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author root
 */
@Service
public class SolventarMovimientosPadronServiceImpl implements SolventarMovimientosPadronService {

    @Autowired
    private AfectacionService afectacionService;
    @Autowired
    private DocumentoService documentoService;
    @Autowired
    private MultaService multaService;
    @Autowired
    private DetalleDocumentoDAO detalleDocumentoDAO;
    private final Logger logger = Logger.getLogger(SolventarMovimientosPadronServiceImpl.class);

    @Override
    @Transactional(rollbackFor = SGTServiceException.class, propagation = Propagation.REQUIRED)
    public void solventarPendientesImprimirParcial(Documento documento) throws SGTServiceException {
        logger.debug("-> solventarPendientesImprimirParcial");
        TipoDocumentoEnum tipo = documento.getVigilancia().getIdTipoDocumentoEnum();
        afectacionService.generaDocumentosAfectacion(documento,
                AfectacionEnum.AFECTACIOM_MOVIMIENTOS_PADRON);
        if (tipo == CARTA_EXHORTO) {
            afectacionService.cancelarDocumentoARCA(documento);
            if (!documentoService.actualizaEstadoBitacoraDocumento(documento, EstadoDocumentoEnum.ANULADO)) {
                throw new SGTServiceException("Error al cambiar el estado del documento");
            }

        } else if (tipo == REQUERIMIENTO_ACUMULADO
                || tipo == REQUERIMIENTO_DIOT
                || tipo == REQUERIMIENTO_MULTA
                || tipo == REQUERIMIENTO_NORMAL) {
            afectacionService.cancelarDocumento(documento);
        }
        logger.debug("<- solventarPendientesImprimirParcial");
    }

    @Override
    @Transactional(rollbackFor = SGTServiceException.class, propagation = Propagation.REQUIRED)
    public void solventarOtrosEstadosParcial(Documento documento) throws SGTServiceException {

        if (isCancelable(documento)) {
            switch (documento.getTipoDocumento()) {
                case CARTA_EXHORTO:
                    if (!documentoService.actualizaEstadoBitacoraDocumento(documento, EstadoDocumentoEnum.ANULADO)) {
                        throw new SGTServiceException("Error al cambiar el estado del documento");
                    }
                    break;
                //No puede ocurrir
                case MULTA:
                    throw new SGTServiceException("No se puede procesar documento de tipo multa");
                default:
                    if (documento.getUltimoEstado() != EstadoDocumentoEnum.CANCELADO.getValor()) {
                        //Para los requerimientos
                        if (!documentoService.actualizaEstadoBitacoraDocumento(documento, EstadoDocumentoEnum.CANCELADO)) {
                            throw new SGTServiceException("Error al cambiar el estado del documento");
                        }
                    }
                    break;
            }
        }

        MultaDocumento multa = new MultaDocumento();
        multa.setNumeroControl(documento.getNumeroControl());
        multa.setConstanteResolucionMotivo(TipoMultaEnum.INCUMPLIMIENTO.toString());
        List<MultaDocumento> multas = multaService.buscarMultasPorTipoYNumeroControl(multa);
        if (!multas.isEmpty()) {
            multa = multas.get(0);
            if (procedeCancelacionMulta(multa)) {
                multaService.cancelarMulta(multa);
            }
        }
    }

    private boolean sonIcepsDeLaMulta(List<MultaIcep> iceps, List<DetalleDocumento> detalles) {
        for (DetalleDocumento detalleDocumento : detalles) {
            boolean estaEnLaMulta = false;
            Iterator<MultaIcep> iterator = iceps.iterator();
            while (!estaEnLaMulta && iterator.hasNext()) {
                MultaIcep icep = iterator.next();
                estaEnLaMulta = icep.getClaveIcep() == detalleDocumento.getClaveIcep().longValue();
            }
            if (!estaEnLaMulta) {
                return false;
            }
        }
        return true;
    }

    private boolean isCancelable(Documento documento) {
        List<DetalleDocumento> detalles = detalleDocumentoDAO.consultaXNumControl(documento.getNumeroControl());
        for (DetalleDocumento detalleDocumento : detalles) {
            if (isOmiso(detalleDocumento)) {
                return false;
            }
        }
        return true;
    }

    private boolean isOmiso(DetalleDocumento detalleDocumento) {
        return detalleDocumento.getSituacionIcep().equals(SituacionIcepEnum.INCUMPLIDO);
    }

    private boolean procedeCancelacionMulta(MultaDocumento multaDocumento) {
        List<DetalleDocumento> detallesCancelados = detalleDocumentoDAO.consultaXNumControl(multaDocumento.getNumeroControl(), SituacionIcepEnum.CANCELADO_POR_MOVIMIENTOS_PADRON);
        return multaDocumento.getIceps().size() == detallesCancelados.size()
                && sonIcepsDeLaMulta(multaDocumento.getIceps(), detallesCancelados);
    }
}
