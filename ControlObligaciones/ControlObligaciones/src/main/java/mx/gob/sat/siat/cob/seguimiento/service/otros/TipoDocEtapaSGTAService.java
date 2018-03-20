package mx.gob.sat.siat.cob.seguimiento.service.otros;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.TipoDocEtapa;
import mx.gob.sat.siat.modelo.dto.SegbMovimientoDTO;


public interface TipoDocEtapaSGTAService {
    List<TipoDocEtapa> consultarTipoDocEtapa(String id) throws SGTServiceException;
    void  actualizarParametrosPorTipoDocumento (TipoDocEtapa id,SegbMovimientoDTO dto)throws SGTServiceException;
}
