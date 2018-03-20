package mx.gob.sat.siat.cob.seguimiento.dao.stdcob;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DetalleConsultaVigilancia;

/**
 *
 * @author root
 */
public interface DetalleConsultaVigilanciaDAO {

    /**
     * @param idVigilancia
     * @return
     * @throws CargaInformacionSGT_DAOException
     */
    List<DetalleConsultaVigilancia> consultaDetalleVigilancia(int idVigilancia) throws SeguimientoDAOException;   
}
