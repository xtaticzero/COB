package mx.gob.sat.siat.cob.seguimiento.service.jobmanager.impl;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.EjecucionDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.IntentoDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.ProcesoDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.SgtcProcesoDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.BitacoraEjecucionDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ConsultarBitacoraFiltroDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ConsultarEjecuionFiltroDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ConsultarIntentosFiltroDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ConsultarProcesosFiltroDto;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.EjecucionDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.IntentoDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.JobDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Proceso;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.SgtcProcesoDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoProceso;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.service.jobmanager.MonitorService;
import mx.gob.sat.siat.cob.seguimiento.util.FileUtility;
import mx.gob.sat.siat.cob.seguimiento.util.Utilerias;
import static mx.gob.sat.siat.cob.seguimiento.util.constante.FilesPath.NOMBRE_ARCHIVO_TXT;
import org.apache.log4j.Logger;
import org.quartz.CronExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author root
 */
@Service
public class MonitorServiceImpl implements MonitorService {

    private Logger logger = Logger.getLogger(MonitorServiceImpl.class);
    @Autowired
    private ProcesoDAO procesoDao;
    @Autowired
    private EjecucionDAO ejecucionDao;
    @Autowired
    private IntentoDAO intentoDao;
    @Autowired
    private SgtcProcesoDAO sgtcProcesoDAOImpl;

    /**
     *
     * @return @throws SeguimientoDAOException
     */
    @Override
    public List<JobDTO> consultarMonitor() throws SeguimientoDAOException {
        ConsultarProcesosFiltroDto filtro = new ConsultarProcesosFiltroDto();
        List<Proceso> procesos = procesoDao.consultarProcesos(filtro);
        List<JobDTO> jobsDto = new ArrayList<JobDTO>();

        for (Proceso p : procesos) {
            EjecucionDTO ultimaEjecucion = ejecucionDao.consultarUltima(p);
            IntentoDTO primerIntento = null;
            IntentoDTO ultimoIntento = null;
            if (ultimaEjecucion != null) {
                ConsultarIntentosFiltroDTO filtroIntentos = new ConsultarIntentosFiltroDTO();
                filtroIntentos.setIdEjecucion(ultimaEjecucion.getId());
                primerIntento = intentoDao.consultarPrimer(ultimaEjecucion);
                ultimoIntento = intentoDao.consultarUltimo(ultimaEjecucion);
            }

            JobDTO jobDto = new JobDTO();
            jobDto.setId(p.getIdProceso());
            jobDto.setNombre(p.getNombre());
            jobDto.setDescripcion(p.getDescripcion());
            jobDto.setEstado(p.getEstado());
            jobDto.setIntento(p.getIntentos());

            jobDto.setFechaInicio(p.getFechaInicioEjecucion());
            jobDto.setFechaFin(p.getFechaFinEjecucion());
            Date ahora = new Date();
            if (p.getEstado().equals(EstadoProceso.EN_EJECUCION.getIdEdoDoc())) {
                jobDto.setDuracion(Utilerias.obtenerDiferenciaFechas(ultimoIntento.getInicio(), ahora));

                if (primerIntento != null) {
                    jobDto.setDuracionAcumulada(Utilerias.obtenerDiferenciaFechas(primerIntento.getInicio(), ahora));
                } else {
                    jobDto.setDuracionAcumulada("");
                }

            } else {
                if (ultimoIntento != null && ultimoIntento.getInicio() != null && ultimoIntento.getFin() != null) {
                    jobDto.setDuracion(Utilerias.obtenerDiferenciaFechas(ultimoIntento.getInicio(), ultimoIntento.getFin()));
                }

                if (primerIntento != null && primerIntento.getInicio() != null && ultimoIntento.getFin() != null) {
                    jobDto.setDuracionAcumulada(Utilerias.obtenerDiferenciaFechas(primerIntento.getInicio(), ultimoIntento.getFin()));
                }
            }

            jobDto.setIntentosMax(p.getIntentosMax() + "");
            jobDto.setPrioridad(p.getPrioridad());
            try {
                Date siguiente = null;
                if (p.getProgramacion() != null && primerIntento != null) {
                    CronExpression cronExpression = new CronExpression(p.getProgramacion());
                    siguiente = cronExpression.getNextValidTimeAfter(primerIntento.getInicio());
                } else {
                    siguiente = new Date();
                }

                jobDto.setSiguienteEjecucion(siguiente);
            } catch (ParseException e) {
                logger.error(e);
            }
            jobDto.setDependencias(p.getLanzador());
            jobsDto.add(jobDto);
        }
        return jobsDto;
    }

    /**
     *
     * @param job
     * @return
     * @throws ParseException
     */
    @Override
    public List<JobDTO> obtenerFechaYHora(List<JobDTO> job) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatHora = new SimpleDateFormat("HH:mm:ss");
        for (JobDTO elemento : job) {
            if (elemento.getFechaInicio() != null) {
                elemento.setFechaInicioStr(format.format(elemento.getFechaInicio()));
                elemento.setHoraInicio(formatHora.format(elemento.getFechaInicio()));
            }
            if (elemento.getFechaFin() != null) {
                elemento.setFechaFinStr(format.format(elemento.getFechaFin()));
                elemento.setHoraFin(formatHora.format(elemento.getFechaFin()));
            }
            if (elemento.getSiguienteEjecucion() != null) {
                elemento.setFechaSiguienteEjecucionStr(format.format(elemento.getSiguienteEjecucion()));
                elemento.setHoraSiguienteEjecucion(formatHora.format(elemento.getSiguienteEjecucion()));
            }
        }
        return job;
    }

    /**
     *
     * @param bitacora
     * @return
     * @throws ParseException
     */
    @Override
    public List<BitacoraEjecucionDTO> obtenerFechaYHoraDetalle(List<BitacoraEjecucionDTO> bitacora) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatHora = new SimpleDateFormat("HH:mm:ss");
        Date inicio = null;
        Date fin = new Date();
        
        for (BitacoraEjecucionDTO elementoDetalle : bitacora) {
            if (elementoDetalle.getInicio() != null) {
                inicio = elementoDetalle.getInicio();
                elementoDetalle.setFechaInicioStr(format.format(elementoDetalle.getInicio()));
                elementoDetalle.setHoraInicio(formatHora.format(elementoDetalle.getInicio()));
            }
            if (elementoDetalle.getFin() != null) {
                fin = elementoDetalle.getFin();
                elementoDetalle.setFechaFinStr(format.format(elementoDetalle.getFin()));
                elementoDetalle.setHoraFin(formatHora.format(elementoDetalle.getFin()));
            }
            
            elementoDetalle.setDuracion(Utilerias.obtenerDiferenciaFechasTexto(inicio, fin));
        }
        return bitacora;
    }

    /**
     *
     * @param filtro
     * @return
     * @throws SeguimientoDAOException
     */
    @Override
    public List<BitacoraEjecucionDTO> consultarBitacora(ConsultarBitacoraFiltroDTO filtro) throws SeguimientoDAOException {
        logger.debug("Entra bitacora Proceso:" + filtro.getIdProceso());
        ConsultarEjecuionFiltroDTO filtroEjecuciones = new ConsultarEjecuionFiltroDTO();
        filtroEjecuciones.setIdProceso(filtro.getIdProceso());
        filtroEjecuciones.setInicioDe(filtro.getFechaInicioDe());
        filtroEjecuciones.setInicioA(filtro.getFechaInicioA());

        List<BitacoraEjecucionDTO> lista = ejecucionDao.consultarBitacora(filtroEjecuciones);

        return lista;
    }

    @Override
    public String exportFileTxt() throws SGTServiceException {
        String fullNameFile = null;
        try {
            FileUtility<SgtcProcesoDTO> ar = new FileUtility<SgtcProcesoDTO>();
            fullNameFile = ar.crearArchivoTXT(NOMBRE_ARCHIVO_TXT, sgtcProcesoDAOImpl.consultar());
        } catch (IOException ex) {
            logger.error(ex);
            throw new SGTServiceException("Se produjo un error al exportar el archivo [txt]", ex);
        } catch (SeguimientoDAOException ex) {
            logger.error(ex);
            throw new SGTServiceException("Se produjo un error al consultar detalle [txt]", ex);
            
        }
        return fullNameFile;
    }
}
