package mx.gob.sat.siat.cob.seguimiento.dao.stdcob;

import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.EjecucionSeguimiento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EjecucionSeguimientoEnum;

public interface EjecucionSeguimientoDAO {
    
    /**
     * @return boolean
     * @throws mx.gob.sat.siat.cob.seguimiento.comun.dao.stdcob.CargaInformacionSGT_DAOException
     */
    EjecucionSeguimiento consultaEjecucionProceso() throws SeguimientoDAOException;
    
    EjecucionSeguimientoEnum enEjecucion() throws SeguimientoDAOException;
}
