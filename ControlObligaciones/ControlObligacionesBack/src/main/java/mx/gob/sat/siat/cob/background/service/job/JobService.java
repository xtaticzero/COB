package mx.gob.sat.siat.cob.background.service.job;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Proceso;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;

public interface JobService {

    void ejecutar(Proceso proceso) throws SeguimientoDAOException;
}
