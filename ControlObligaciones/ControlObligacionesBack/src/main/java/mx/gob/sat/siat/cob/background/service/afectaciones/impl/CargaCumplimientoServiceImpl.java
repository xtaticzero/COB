/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.service.afectaciones.impl;

import java.util.Date;
import mx.gob.sat.siat.cob.background.service.afectaciones.CargaCumplimientoService;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.util.Utilerias;
import org.apache.log4j.Logger;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author root
 */
@Service("cargaCumplimientoService")
public class CargaCumplimientoServiceImpl implements  CargaCumplimientoService {
    private Logger log = Logger.getLogger(CargaCumplimientoServiceImpl.class);
    @Autowired
    @Qualifier("jobLauncher")
    private JobLauncher jobLauncher;
    @Autowired
    @Qualifier("cargaCumplimientoJob")
    private Job job;
    
     @Override
    public void cargarCumplimientos(Date fechaExtraccion)throws SGTServiceException {
        JobExecution execution=null;
        try {            
            log.info("Inicia el proceso carga cumplimientos...");    
                JobParameters param = new JobParametersBuilder()
                        .addString("fecha", Utilerias.
                            formatearFechaAAAAMMDD(fechaExtraccion))
                        .addLong("time", System.currentTimeMillis())
                    .toJobParameters();
                execution = jobLauncher.run(job, param);
                 if(execution!=null && execution.getStatus().equals(BatchStatus.FAILED)) {
                        throw new SGTServiceException("Error al realizar la carga de cumplimientos");
                }
                log.info("Termina proceso carga cumplimientos con exito");
        } catch (JobExecutionException e) {
            throw new SGTServiceException("No se pudo realizar la carga de cumplimientos\n" +e);
        } 
    }
    
}
