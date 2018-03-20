package mx.gob.sat.siat.cob.seguimiento.dao.stdcob;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.EmisionVigilancia;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;


/**
 *
 * @author root
 */
public interface EmisionVigilanciaDAO {

    /**
     * @param emisionVIgilancia
     * @throws mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException
     */
    void insert(EmisionVigilancia emisionVIgilancia) throws SeguimientoDAOException;

    /**
     * @return
     * @throws mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException
     */
    List<EmisionVigilancia> consultaEmisionVigilancia() throws SeguimientoDAOException;

    /**
     * @return
     * @throws mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException
     */
    List<EmisionVigilancia> emisionVigXEdoEmision() throws SeguimientoDAOException;
    
    /**
     *
     * @param idestadoemision
     * @param idvigilancia
     * @param idetapavigilancia
     * @param idtipodocumento
     * @return
     * @throws mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException
     */
    Integer actualizaEstadoEmision(int idestadoemision, 
            long idvigilancia, 
            long idetapavigilancia, 
            int idtipodocumento) throws SeguimientoDAOException;
}
