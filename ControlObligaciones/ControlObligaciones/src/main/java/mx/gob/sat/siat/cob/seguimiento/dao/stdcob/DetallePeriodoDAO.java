package mx.gob.sat.siat.cob.seguimiento.dao.stdcob;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DetallePeriodos;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;


/**
 *
 * @author root
 */
public interface DetallePeriodoDAO {

    /**
     * @param detallePeriodo
     * @throws SeguimientoDAOException
     */
    void insert(DetallePeriodos detallePeriodo) throws SeguimientoDAOException;

    /**
     * @return
     * @throws SeguimientoDAOException
     */
    List<DetallePeriodos> consultaDetallePeriodos() throws SeguimientoDAOException;

    /**
     * @param numControl
     * @return
     * @throws SeguimientoDAOException
     */
    List<DetallePeriodos> detallePeriodosXNumControl(String numControl) throws SeguimientoDAOException;
}
