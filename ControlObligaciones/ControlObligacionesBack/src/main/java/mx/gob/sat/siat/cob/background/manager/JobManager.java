package mx.gob.sat.siat.cob.background.manager;

import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;

public interface JobManager {

    void ejecutarProcesos() throws SeguimientoDAOException, SGTServiceException;

    void censar() throws SeguimientoDAOException, SGTServiceException;
}
