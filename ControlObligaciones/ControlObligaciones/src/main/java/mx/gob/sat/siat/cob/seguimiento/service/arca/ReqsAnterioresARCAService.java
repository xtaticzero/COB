/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.service.arca;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.RequerimientosAnteriores;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;

/**
 *
 * @author Juan
 */
public interface ReqsAnterioresARCAService {

    /**
     *
     * @param reqsAnteriores
     * @return
     * @throws SeguimientoDAOException
     */
    Integer guardaReqsAnteriores(List<RequerimientosAnteriores> reqsAnteriores) throws SGTServiceException;
    
    
}
