package mx.gob.sat.siat.cob.background.test;

import java.text.ParseException;
import java.util.Calendar;

import java.util.Date;
import java.util.List;

import mx.gob.sat.siat.cob.background.manager.JobManager;
import mx.gob.sat.siat.cob.background.service.afectaciones.AfectacionCumplimientoService;
import mx.gob.sat.siat.cob.background.service.afectaciones.AfectacionMovimientosPadronService;
import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.OracleDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.EjecucionDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.ProcesoDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.BitacoraEjecucionDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ConsultarBitacoraFiltroDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ConsultarEjecuionFiltroDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ConsultarProcesosFiltroDto;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Proceso;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.service.jobmanager.MonitorService;
import mx.gob.sat.siat.cob.seguimiento.util.Utilerias;

import org.apache.log4j.Logger;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class JobManagerTest{
    
    private static final Logger logger = Logger.getLogger(JobManagerTest.class);
    private ApplicationContext context;
    
    public static void main(String[] arg) throws SeguimientoDAOException, SGTServiceException, ParseException {
        new JobManagerTest().testEjecutar();
    }
    
    public JobManagerTest() {
        super();
        context = new ClassPathXmlApplicationContext("applicationContext-main.xml");
    }
    
    public void testCensar() throws SeguimientoDAOException, SGTServiceException {
        logger.debug("Cargando contexto...");
        JobManager jobManager = (JobManager) context.getBean("jobManager");
        logger.debug("Censando...");
        jobManager.censar();
        logger.debug("Fin del Test");
    }
    public void testEjecutar() throws SeguimientoDAOException, SGTServiceException {
        JobManager jobManager = (JobManager) context.getBean("jobManager");
        jobManager.ejecutarProcesos();
    }
    
    public void testInsertarProceso() throws SeguimientoDAOException{
        ProcesoDAO procesoDao = (ProcesoDAO) context.getBean("procesoDAOImpl");
        Proceso proceso = new Proceso();
        proceso.setNombre("proceso.prueba");
        proceso.setDescripcion("Un proceso de prueba");
        proceso.setLanzador("");
        proceso.setProgramacion("");
        proceso.setExcluir("");
        proceso.setEstado(1);
        proceso.setPrioridad(1);
        proceso.setIntentosMax(5);
        procesoDao.crear(proceso);
        logger.debug(proceso.getIdProceso()+" Inserted!");
    }
    
    public void testJobManagerWeb() throws SeguimientoDAOException{
           logger.debug("Ready!");
           EjecucionDAO ejecucionDao = (EjecucionDAO) context.getBean("ejecucionDAOImpl");
           ejecucionDao.consultar(new ConsultarEjecuionFiltroDTO());
           logger.debug("Done!");
       }
    
    public void testMonitor() throws SeguimientoDAOException, SGTServiceException, ParseException {
        MonitorService monitorService = (MonitorService) context.getBean("monitorServiceImpl");
        ConsultarBitacoraFiltroDTO dto = new ConsultarBitacoraFiltroDTO();
        dto.setIdProceso(4);
        dto.setFechaInicioDe(Utilerias.getYesterday());
        dto.setFechaInicioA(Calendar.getInstance().getTime());
        List<BitacoraEjecucionDTO> bitacora = monitorService.consultarBitacora(dto);
        bitacora = monitorService.obtenerFechaYHoraDetalle(bitacora);
        for(BitacoraEjecucionDTO bitacoraEjecucionDTO : bitacora){
            logger.debug(bitacoraEjecucionDTO.getIntento()+ "->"+bitacoraEjecucionDTO.getFechaInicioStr()+" "+bitacoraEjecucionDTO.getHoraInicio());
        }
    }
    
    public void testFechaActual() throws SeguimientoDAOException, SGTServiceException, ParseException {
        OracleDAO oracleDAO = (OracleDAO) context.getBean("oracleDAOImpl");
        Date fechaActual = oracleDAO.consultarFechaActual();
        logger.debug("Fecha Actual:"+Utilerias.formatearFechaAAAAMMDDHHMMSS(fechaActual));
    }
    
    public void testDatasources() throws SeguimientoDAOException {
        logger.debug("Ready!");
        ProcesoDAO procesoDAO = (ProcesoDAO) context.getBean("procesoDAOImpl");
        List<Proceso> procesos = null;
        ConsultarProcesosFiltroDto filtro = new ConsultarProcesosFiltroDto();

        procesos = procesoDAO.consultarProcesos(filtro);
        
        for(Proceso proceso:procesos){
            logger.debug(proceso.getIdProceso()+" - "+proceso.getFechaInicioEjecucion());
            logger.debug(proceso.getIdProceso()+" - "+proceso.getFechaFinEjecucion());
        }
    }
    
    public void afectacionCumplimiento() throws SeguimientoDAOException, SGTServiceException{
        logger.debug("Ready!");
        AfectacionCumplimientoService afectacionCumplimientoService = (AfectacionCumplimientoService) context.getBean("afectacionCumplimientoService");
        afectacionCumplimientoService.afectarPorCumplimientos();
    }
    
    
    public void afetcacionPadron() throws SGTServiceException{
        AfectacionMovimientosPadronService afectacionMovimientosPadronService = (AfectacionMovimientosPadronService) context.getBean("afectacionMovimientosPadronService");
        afectacionMovimientosPadronService.afectarPorMovimientoEnPadron();
    }
}
