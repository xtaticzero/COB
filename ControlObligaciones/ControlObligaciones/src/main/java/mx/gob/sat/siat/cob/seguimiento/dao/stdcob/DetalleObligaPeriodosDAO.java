package mx.gob.sat.siat.cob.seguimiento.dao.stdcob;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DetalleObligaPeriodo;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;


/**
 *
 * @author root
 */
public interface DetalleObligaPeriodosDAO {

    /**
     * @return
     * @throws mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException
     */
    List<DetalleObligaPeriodo> buscaDetalleOblicaPeriodos() throws SeguimientoDAOException;
}
