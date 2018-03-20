/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.service.afectaciones;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Documento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoDocumentoEnum;

/**
 *
 * @author root
 */
public interface ProcesoAfectacionCumplimientoService {
    /**
     *Estados con los que se va a trabajar
     */
    static final Integer[] ESTADOS_CUMPLIMIENTO = {EstadoDocumentoEnum.PENDIENTE_DE_IMPRIMIR.getValor(),
        EstadoDocumentoEnum.PENDIENTE_DE_NOTIFICAR.getValor(),
        EstadoDocumentoEnum.NOTIFICADO.getValor(),
        EstadoDocumentoEnum.VENCIDO.getValor(),
        EstadoDocumentoEnum.SOLVENTADO_PARCIAL.getValor(),
        EstadoDocumentoEnum.VENCIDO_PARCIAL.getValor(),
        EstadoDocumentoEnum.EMITIDO.getValor(),
        EstadoDocumentoEnum.ATENDIDO_PARCIAL.getValor(),
        EstadoDocumentoEnum.NO_GENERADO.getValor(),
        EstadoDocumentoEnum.PENDIENTE_DE_PROCESAR.getValor()};

    void procesarCumplimiento(Documento documento) throws SGTServiceException;

    void afectarDetallesConCumplimiento(List<EstadoDocumentoEnum> estados) throws SGTServiceException;
    void afectarDetallesConCumplimiento(Integer[] estados) throws SGTServiceException;
    void afectarDetallesComplementaria() throws SGTServiceException;
    void procesaDocumentosCumplimiento(List<Documento> documentos) throws SGTServiceException;
}
