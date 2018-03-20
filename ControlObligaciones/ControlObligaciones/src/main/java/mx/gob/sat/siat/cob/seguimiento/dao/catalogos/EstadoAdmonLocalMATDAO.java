package mx.gob.sat.siat.cob.seguimiento.dao.catalogos;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Combo;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.EstadoAdmonLocalMAT;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;

public interface EstadoAdmonLocalMATDAO {

    /**
     *
     * @return
     */
    List<EstadoAdmonLocalMAT> todosLosEstadosAdmonLocalMAT();

    /**
     *
     * @param estadoAdmonLocalMAT
     * @throws SeguimientoDAOException
     */
    void editaEstadoAdmonLocalMAT(EstadoAdmonLocalMAT estadoAdmonLocalMAT) throws SeguimientoDAOException;

    /**
     *
     * @return
     */
    List<Combo> obtenerComboEstado();

    /**
     *
     * @param idAdmonLocal
     * @return
     * @throws SeguimientoDAOException
     */
    EstadoAdmonLocalMAT consultaPorIdAdmonLocal(String idAdmonLocal) throws SeguimientoDAOException;
}
