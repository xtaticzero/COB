package mx.gob.sat.siat.cob.seguimiento.service.jobmanager;

import java.text.ParseException;
import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.BitacoraEjecucionDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ConsultarBitacoraFiltroDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.JobDTO;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;


public interface MonitorService {
    List<JobDTO> consultarMonitor() throws SeguimientoDAOException;
    List<BitacoraEjecucionDTO> consultarBitacora(ConsultarBitacoraFiltroDTO filtro)throws SeguimientoDAOException;
    List<JobDTO> obtenerFechaYHora (List<JobDTO> job) throws ParseException;
    List<BitacoraEjecucionDTO> obtenerFechaYHoraDetalle (List<BitacoraEjecucionDTO> bitacora) throws ParseException;
    String exportFileTxt() throws SGTServiceException;
}
