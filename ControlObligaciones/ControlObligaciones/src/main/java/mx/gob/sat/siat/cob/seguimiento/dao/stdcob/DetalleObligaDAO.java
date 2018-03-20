package mx.gob.sat.siat.cob.seguimiento.dao.stdcob;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DetalleObliga;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;


/**
 *
 * @author root
 */
public interface DetalleObligaDAO {

    /**
     * @param detalleObliga
     * @throws mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException
     */
    void insert(DetalleObliga detalleObliga) throws SeguimientoDAOException;

    /**
     * @return
     * @throws mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException
     */
    List<DetalleObliga> consultaDetalleObliga() throws SeguimientoDAOException;

    /**
     * @param numControl
     * @return
     * @throws mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException
     */
    List<DetalleObliga> detalleObligaXNumControl(String numControl) throws SeguimientoDAOException;
}
