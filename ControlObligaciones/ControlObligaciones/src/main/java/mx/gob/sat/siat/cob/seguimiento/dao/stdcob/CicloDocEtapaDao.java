package mx.gob.sat.siat.cob.seguimiento.dao.stdcob;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoDocumentoEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EtapaVigilanciaEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoDocumentoEnum;


/**
 *
 * @author root
 */
public interface CicloDocEtapaDao  {
    
    /**
     *
     * @param IdEstadoOrigen
     * @param IdTipoDocumento
     * @param IdEtapaVigilancia
     * @param IdEstadoDestino
     * @return
     */
    List<Integer> consultaEstados(EstadoDocumentoEnum idEstadoOrigen,
                                         TipoDocumentoEnum idTipoDocumento,
                                         EtapaVigilanciaEnum idEtapaVigilancia,
                                         EstadoDocumentoEnum idEstadoDestino);

}
