package mx.gob.sat.siat.cob.seguimiento.dao.stdcob;

import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.SGTBRetroARCA;

public interface SGTBRetroARCADAO {

    int updateUltimoActualizado(SGTBRetroARCA sgtbRetroARCA) throws SeguimientoDAOException;

    int updateUltimoActualizado(int idProcesado) throws SeguimientoDAOException;

    int insertUltimoActualizado(SGTBRetroARCA sgtbRetroARCA) throws SeguimientoDAOException;

    SGTBRetroARCA getUltimoActualizado() throws SeguimientoDAOException;
}
