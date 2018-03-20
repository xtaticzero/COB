package mx.gob.sat.siat.cob.background.test;

import java.text.SimpleDateFormat;

import mx.gob.sat.siat.cob.seguimiento.dto.arca.SeguimientoARCA;

import org.apache.log4j.Logger;

import org.junit.Ignore;
import org.junit.Test;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;


public class AfectacionPorNotificacionTest extends ContextLoaderTest{
    private Logger log =Logger.getLogger(AfectacionPorNotificacionTest.class);
        
    @Test
    @Ignore
    public void prueba(){
        SeguimientoARCA seguimientoARCA = new SeguimientoARCA();
                SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
                log.info("\n=========================== AfectacionPorNotificacionTest ==============================\n");
                       
                JobLauncher jobLauncher = (JobLauncher) getContext().getBean("jobLauncher");
                Job job = (Job) getContext().getBean("afectacion-notificacion-job");
         
                try {
         
                        JobExecution execution = jobLauncher.run(job, new JobParameters());
                        System.out.println("Exit Status : " + execution.getStatus());
                        assert  job != null;
                } catch (Exception e) {
                        log.error(e);
                } 
                
                log.info("Done");
    }
}
