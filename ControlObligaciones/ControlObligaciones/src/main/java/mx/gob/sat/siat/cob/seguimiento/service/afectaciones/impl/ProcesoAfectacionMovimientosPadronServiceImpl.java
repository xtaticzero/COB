package mx.gob.sat.siat.cob.seguimiento.service.afectaciones.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.BajaIcepDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.DetalleDocumentoDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DetalleDocumento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Documento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.MultaDocumento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoDocumentoEnum;
import static mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoDocumentoEnum.ANULADO;
import static mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoDocumentoEnum.CANCELADO_POR_AUTORIDAD;
import static mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoDocumentoEnum.NO_LOCALIZADO;
import static mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoDocumentoEnum.NO_TRABAJADO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoMultaEnum;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.service.afectaciones.MultaService;
import mx.gob.sat.siat.cob.seguimiento.service.afectaciones.ProcesoAfectacionMovimientosPadronService;
import mx.gob.sat.siat.cob.seguimiento.service.afectaciones.SolventarMovimientosPadronService;
import mx.gob.sat.siat.cob.seguimiento.service.extraccion.DocumentoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Rodrigo
 */
@Service(value = "procesoAfectacionMovimientosPadronService")
public class ProcesoAfectacionMovimientosPadronServiceImpl implements ProcesoAfectacionMovimientosPadronService {

    @Autowired
    private DocumentoService documentoService;
    @Autowired
    private DetalleDocumentoDAO detalleDocumentoDAO;
    @Autowired
    private MultaService multaService;
    @Autowired
    private BajaIcepDAO bajaIcepDAO;
    @Autowired
    private SolventarMovimientosPadronService solventarMovimientosPadronService;
    /**
     *
     */
    public static final EstadoDocumentoEnum[] ESTADOS_NO_VALIDOS_CUMPLIMIENTO_TOTAL = {CANCELADO_POR_AUTORIDAD,
        ANULADO,
        NO_TRABAJADO,
        NO_LOCALIZADO};

    /**
     *
     * @param documento
     * @throws SGTServiceException
     */
    @Override
    @Transactional(rollbackFor = SGTServiceException.class)
    public void solventarPorMovimientoAlPadronTotal(Documento documento) throws SGTServiceException {
        //Se cambia el estatus de los iceps a Solventado por movimientos en el padron
        if (detalleDocumentoDAO.solventarPorMovimientoAlPadron(
                documento) == null) {
            throw new SGTServiceException("No se pudo cambiar el estatus de los iceps solventados");
        }
        //Se cambia el estatus del documento en base al tipo
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

                //TODO: Cancelar multa pendiente de implementar
                MultaDocumento multaDocumento = new MultaDocumento();
                multaDocumento.setConstanteResolucionMotivo(TipoMultaEnum.INCUMPLIMIENTO.toString());
                multaDocumento.setNumeroControl(documento.getNumeroControl());
                List<MultaDocumento> multas = multaService.buscarMultasPorTipoYNumeroControl(multaDocumento);
                if (!multas.isEmpty()) {
                    multaService.cancelarMulta(multas.get(0));
                }
                break;
        }
    }

    /**
     *
     * @param documento
     * @throws SGTServiceException
     */
    @Override
    @Transactional(rollbackFor = SGTServiceException.class, propagation = Propagation.REQUIRED)
    public void solventarPorMovimientoAlPadronParcial(Documento documento) throws SGTServiceException {
        detalleDocumentoDAO.solventarPorMovimientoAlPadron(documento);
        if (documento.getUltimoEstado() == EstadoDocumentoEnum.PENDIENTE_DE_IMPRIMIR.getValor()) {
            solventarMovimientosPadronService.solventarPendientesImprimirParcial(documento);
        } else {
            solventarMovimientosPadronService.solventarOtrosEstadosParcial(documento);
        }
    }

    /**
     *
     * @param documentosNoProcesados
     * @throws SGTServiceException
     */
    @Override
    @Transactional(rollbackFor = SGTServiceException.class)
    public void borrarDatosBajaIcep(List<Documento> documentosNoProcesados) throws SGTServiceException {
        Set<String> boids = new HashSet<String>();
        Set<Long> iceps = new HashSet<Long>();
        for (Documento documento : documentosNoProcesados) {
            boids.add(documento.getBoid());
            for (DetalleDocumento detalleDocumento : documento.getDetalles()) {
                iceps.add(detalleDocumento.getClaveIcep());
            }
        }
        if (bajaIcepDAO.borrarPorBoidEIcep(boids, iceps) == null) {
            throw new SGTServiceException("No se pudo borrar los registros de la tabla BajaIcep");
        }
    }
}
