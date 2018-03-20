/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.test;

import org.apache.log4j.Logger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.support.GenericXmlApplicationContext;

/**
 *
 * @author Juan
 */
public class AfectacionNotificacionTest {

    private static final Logger log = Logger.getLogger(AfectacionNotificacionTest.class);

    private AfectacionNotificacionTest() {
        super();
    }

    public static void main(String[] arg) {
        GenericXmlApplicationContext context = new GenericXmlApplicationContext();
        context.getEnvironment().setActiveProfiles("development");
        context.load("applicationContext-main.xml");
        context.refresh();
        log.info("\n=========================== AfectacionPorNotificacionTest ==============================\n");
        JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
        Job job = (Job) context.getBean("afectacion-notificacion-job");
        try {
            JobExecution execution = jobLauncher.run(job, new JobParameters());
            log.info("Exit Status : " + execution.getStatus());
            assert job != null;
        } catch (Exception e) {
            log.error(e);
        }
    }

}
