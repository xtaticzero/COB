package mx.gob.sat.siat.cob.seguimiento.dao.stdcob;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DatoDoctoVigilancia;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;


/**
 *
 * @author root
 */
public interface DatoDoctoVigilanciaDAO {

    /**
     * @return
     * @throws mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException
     */
    List<DatoDoctoVigilancia> consultaDatosDoctosVigilancia() throws SeguimientoDAOException;


}
