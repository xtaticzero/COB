package mx.gob.sat.siat.cob.seguimiento.service.aprobacion;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dto.db2.HistoricoCumplimiento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DocumentoAprobar;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoDocumentoEnum;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;

/**
 *
 * @author root
 */
public interface ValidacionCumplimientoService {

    void afectarCumplidos(List<HistoricoCumplimiento> cumplimientos, List<DocumentoAprobar> documentosIniciales) throws SGTServiceException;

    void validarHistoricosCumplimiento(String vigilancia, List<HistoricoCumplimiento> cumplimientos);

    Integer actualizarEstatusDocumento(String numeroControl, EstadoDocumentoEnum estadoNuevo) throws SGTServiceException;

}
