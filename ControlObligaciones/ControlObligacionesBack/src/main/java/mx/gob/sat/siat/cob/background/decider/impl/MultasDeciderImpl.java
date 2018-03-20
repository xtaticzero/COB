/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.decider.impl;

import mx.gob.sat.siat.cob.background.decider.MultasDecider;
import org.apache.log4j.Logger;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.stereotype.Service;

/**
 *
 * @author Juan
 */
@Service("multasDecider")
public class MultasDeciderImpl implements MultasDecider {

    private Logger log = Logger.getLogger(MultasDeciderImpl.class);
    private String esOperacionMat;

    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
        log.info("### Toma decision ...");
        esOperacionMat = jobExecution.getJobParameters().getString("esOperacionMat");
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<" + esOperacionMat);
        if (esOperacionMat.equals("1")) {
            return new FlowExecutionStatus("OPERACION_MAT");
        } else {
            return new FlowExecutionStatus("COMPLETED");
        }
    }

    public String getEsOperacionMat() {
        return esOperacionMat;
    }

    public void setEsOperacionMat(String esOperacionMat) {
        this.esOperacionMat = esOperacionMat;
    }
}