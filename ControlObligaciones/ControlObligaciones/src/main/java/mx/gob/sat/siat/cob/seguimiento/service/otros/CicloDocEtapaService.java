package mx.gob.sat.siat.cob.seguimiento.service.otros;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoDocumentoEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EtapaVigilanciaEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoDocumentoEnum;


public interface CicloDocEtapaService {

     boolean validaCambioEstado(EstadoDocumentoEnum idEstadoOrigen,
            TipoDocumentoEnum idTipoDocumento,
            EtapaVigilanciaEnum idEtapaVigilancia,
            EstadoDocumentoEnum idEstadoDestino) ;
}
