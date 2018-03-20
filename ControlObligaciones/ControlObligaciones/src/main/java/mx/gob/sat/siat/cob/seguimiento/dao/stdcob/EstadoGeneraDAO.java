package mx.gob.sat.siat.cob.seguimiento.dao.stdcob;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.EstadoGenera;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;


/**
 *
 * @author root
 */
public interface EstadoGeneraDAO {

    /**
     * @param estadoGenera
     * @throws mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException
     */
    void insert(EstadoGenera estadoGenera) throws SeguimientoDAOException;

    /**
     * @return
     * @throws mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException
     */
    List<EstadoGenera> consultaEstadoGenera() throws SeguimientoDAOException;

}
