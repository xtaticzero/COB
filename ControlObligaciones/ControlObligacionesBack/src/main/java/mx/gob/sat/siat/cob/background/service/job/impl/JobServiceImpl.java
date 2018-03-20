package mx.gob.sat.siat.cob.background.service.job.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.mail.MessagingException;

import mx.gob.sat.siat.cob.background.service.job.JobService;
import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.EmailReporteProcesoDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.OracleDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.EjecucionDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.IntentoDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.ParametroSgtDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.ProcesoDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.EmailReporteProceso;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.EjecucionDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.IntentoDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ParametrosSgtDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Proceso;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoProceso;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.ParametroSgtEnum;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.service.otros.MailService;
import mx.gob.sat.siat.cob.seguimiento.util.Utilerias;

import org.apache.log4j.Logger;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;


@Service(value = "jobService")
public class JobServiceImpl implements JobService,Runnable {
    private static Logger logger = Logger.getLogger(JobServiceImpl.class);
    private ProcesoDAO procesoDAO;
    private EjecucionDAO ejecucionDAO;
    private IntentoDAO intentoDAO;
    private OracleDAO oracleDAO;
    private ApplicationContext context;
    private Proceso proceso;
    private String managerName;
    private EjecucionDTO ejecucionDto;
    private IntentoDTO intentoDto;

    public JobServiceImpl() {
        super();
    }

    @Override
    public void ejecutar(Proceso proceso) throws SeguimientoDAOException {
        start();
        proceso.setEstado(EstadoProceso.EN_EJECUCION.getIdEdoDoc());
        proceso.setFechaInicioEjecucion(oracleDAO.consultarFechaActual());
        procesoDAO.actualizarInicioEjecucion(proceso);
        if (proceso.getNombre().indexOf(".") >= 0) {
            //Job Spring Bean
            try {
                String[] elementos = proceso.getNombre().split("\\.");
                if (elementos.length == 2) {
                    String interfase = elementos[0];
                    String metodo = elementos[1];
                    Object bean = context.getBean(interfase);
                    Method method = bean.getClass().getMethod(metodo);
                    method.invoke(bean);
                    success();
                    List<Proceso> procesosRestraso = (ArrayList<Proceso>)context.getBean("procesosRestraso");
                    for(Proceso retrasado:procesosRestraso){
                        if(retrasado.getIdProceso().equals(proceso.getIdProceso())){
                            procesosRestraso.remove(retrasado);
                            enviaCorreoExito(retrasado);
                        }
                    }
                }
            } catch (NoSuchMethodException e) {
                failure(e);
            } catch (IllegalAccessException e) {
                failure(e);
            } catch (InvocationTargetException e) {
                failure(e);
            } catch (SeguimientoDAOException e) {
                failure(e);
            }catch (NoSuchBeanDefinitionException e) {
                failure(e);
            }
        } else {
            //Job Spring Batch
            try {
                context.getBean(proceso.getNombre());
                JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
                JobParameters jobParameters =
                        new JobParametersBuilder()
                        .addLong("time", System.currentTimeMillis()).toJobParameters();
                Job job = (Job) context.getBean(proceso.getNombre());
                JobExecution execution = jobLauncher.run(job, jobParameters);
                if (execution.getStatus().toString().equals("COMPLETED")) {
                    success();
                    List<Proceso> procesosRestraso = (List<Proceso>)context.getBean("procesosRestraso");
                    for(Proceso retrasado:procesosRestraso){
                        if(retrasado.getIdProceso().equals(proceso.getIdProceso())){
                            procesosRestraso.remove(retrasado);
                            enviaCorreoExito(retrasado);
                        }
                    }
                } else {
                    failure(new Exception("FAIL"));
                }
            } catch (JobExecutionAlreadyRunningException e) {
                failure(e);
            } catch (JobRestartException e) {
                failure(e);
            } catch (JobInstanceAlreadyCompleteException e) {
                failure(e);
            } catch (JobParametersInvalidException e) {
                failure(e);
            } catch(NoSuchBeanDefinitionException e){
                failure(e);
            } catch(BeansException e){
                failure(e);
            }
        }
    }

    @Override
    public void run(){
        try{
            logger.debug ("Ejecutando Proceso:"+proceso.getDescripcion() + "Con: "+proceso.getIdManager()+" | Original:"+managerName);
                this.ejecutar(proceso);
            logger.debug("Fin de proceso"+proceso.getDescripcion());
        } catch (SeguimientoDAOException e) {
            //No se pudo guardar la bitacora
            logger.debug("No fue posible actualizar la bitacora."+e.toString());
        }
    }

    public void start() throws SeguimientoDAOException{
        EjecucionDTO ultima = ejecucionDAO.consultarUltima(proceso);
        if(ultima != null && ultima.getEstado() != EstadoProceso.COMPLETADO.getIdEdoDoc()){
            ejecucionDto = ultima;
            ejecucionDto.setEstado(EstadoProceso.EN_EJECUCION.getIdEdoDoc());
            ejecucionDto.setObservaciones("En ejecucion...");
            ejecucionDto.setInicio(oracleDAO.consultarFechaActual());
            ejecucionDto.setIntento(ejecucionDto.getIntento()+1);
            ejecucionDAO.actualizar(ejecucionDto);
        }
        else{
            ejecucionDto = new EjecucionDTO();
            ejecucionDto.setIdProceso(proceso.getIdProceso());
            ejecucionDto.setIntento(1);
            ejecucionDto.setEstado(EstadoProceso.EN_EJECUCION.getIdEdoDoc());
            ejecucionDto.setObservaciones("En ejecucion...");
            ejecucionDto.setInicio(oracleDAO.consultarFechaActual());
            ejecucionDto.setIntento(1);
            ejecucionDAO.insertar(ejecucionDto);
        }

        intentoDto = new IntentoDTO();
        intentoDto.setIdEjecucion(ejecucionDto.getId());
        intentoDto.setIntento(ejecucionDto.getIntento());
        intentoDto.setInicio(oracleDAO.consultarFechaActual());
        intentoDto.setEstado(EstadoProceso.EN_EJECUCION.getIdEdoDoc());
        intentoDto.setObservaciones("Ejecutando intento "+intentoDto.getIntento());
        intentoDAO.insertar(intentoDto);
    }

    public void success()throws SeguimientoDAOException{
        intentoDto.setEstado(EstadoProceso.COMPLETADO.getIdEdoDoc());
        intentoDto.setFin(oracleDAO.consultarFechaActual());
        intentoDto.setObservaciones("Operacion Exitosa");
        intentoDAO.actualizar(intentoDto);

        ejecucionDto.setEstado(EstadoProceso.COMPLETADO.getIdEdoDoc());
        ejecucionDto.setFin(oracleDAO.consultarFechaActual());
        ejecucionDto.setObservaciones("Operacion Exitosa");
        ejecucionDAO.actualizar(ejecucionDto);

        proceso.setIntentos(0);
        proceso.setEstado(EstadoProceso.COMPLETADO.getIdEdoDoc());
        proceso.setIdManager(null);
        proceso.setFechaFinEjecucion(oracleDAO.consultarFechaActual());
        procesoDAO.actualizarFinEjecucion(proceso);
    }

    public void failure(Exception e) throws SeguimientoDAOException {
        Throwable error = e.getCause()==null?e:e.getCause();
        intentoDto.setEstado(EstadoProceso.FALLIDO.getIdEdoDoc());
        intentoDto.setFin(oracleDAO.consultarFechaActual());
        intentoDto.setObservaciones(error.getMessage());
        intentoDAO.actualizar(intentoDto);

        ejecucionDto.setEstado(EstadoProceso.FALLIDO.getIdEdoDoc());
        ejecucionDto.setFin(oracleDAO.consultarFechaActual());
        ejecucionDto.setObservaciones(error.getMessage());
        ejecucionDAO.actualizar(ejecucionDto);

        proceso.setIntentos(proceso.getIntentos()+1);
        proceso.setFechaFinEjecucion(oracleDAO.consultarFechaActual());
        proceso.setEstado(EstadoProceso.FALLIDO.getIdEdoDoc());
        proceso.setIdManager(null);
        procesoDAO.actualizarFinEjecucion(proceso);
    }

    private void enviaCorreoExito(Proceso p) throws SeguimientoDAOException{
        ParametroSgtDAO parametroSgtDAO = (ParametroSgtDAO)context.getBean("parametroSgtDAOImpl");
        EmailReporteProcesoDAO emailReporteProcesoDAO = (EmailReporteProcesoDAO)context.getBean("emailReporteProcesoDAOImpl");
        Properties mailProperties = (Properties) context.getBean("jobManagerMailProperties");
        MailService mailService = (MailService) context.getBean("mailServiceImpl");

        ParametrosSgtDTO parametro = parametroSgtDAO.obtenerParametroSgtPorIdParametro(ParametroSgtEnum.AMBIENTE.getValor());
        List<EmailReporteProceso> emails = emailReporteProcesoDAO.todosLosEmailReporteProceso(false);
        StringBuilder sEmails = new StringBuilder("");
        for(EmailReporteProceso emailReporteProceso:emails){
            sEmails.append(emailReporteProceso.getCorreoElectronico()).append(",");
            if(emailReporteProceso.getCorreoElectronicoAlterno()!=null){
                sEmails.append(emailReporteProceso.getCorreoElectronicoAlterno()).append(",");
            }
        }
        String[] destinatarios  = sEmails.toString().split(",");
        String asunto = mailProperties.getProperty("mail.exito.asunto");
        String template = mailProperties.getProperty("mail.exito.template");
        Map<String,String> sustituir = new HashMap<String,String>();
        sustituir.put("proceso.descripcion", p.getDescripcion());
        String mensaje = Utilerias.obtenerCorreo(template, sustituir);
        try{
            logger.info("Enviando correo...");
            mailService.enviarCorreoPara(destinatarios,"Ambiente:"+parametro.getValor()+" - "+asunto,mensaje);
            logger.info("Correo enviado correctamente");
        }
        catch(MessagingException e){
            logger.error("No fue posible enviar correo "+e.toString());
        }
    }

    public void setProcesoDAO(ProcesoDAO procesoDAO) {
        this.procesoDAO = procesoDAO;
    }

    public void setEjecucionDAO(EjecucionDAO ejecucionDAO) {
        this.ejecucionDAO = ejecucionDAO;
    }

    public void setIntentoDAO(IntentoDAO intentoDAO) {
        this.intentoDAO = intentoDAO;
    }

    public void setContext(ApplicationContext context) {
        this.context = context;
    }

    public void setProceso(Proceso proceso) {
        this.proceso = proceso;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public void setOracleDAO(OracleDAO oracleDAO) {
        this.oracleDAO = oracleDAO;
    }
}
