package mx.gob.sat.siat.cob.seguimiento.service.otros;

import mx.gob.sat.siat.cob.seguimiento.dto.arca.Obligacion;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;

/**
 *
 * @author root
 */
public interface SustitucionMotivacionService {
    
    /**
     *
     * @param dato
     */
    void sustituirMotivacion(Obligacion dato) throws SGTServiceException;
    
}
