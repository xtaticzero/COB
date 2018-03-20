/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.batch.job.impl;

import java.util.HashMap;
import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.batch.job.FirmaDocumentoJob;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.FirmaDigital;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author root
 */
@Service("firmaDocumentosRenuentes")
public class FirmaDocumentosRenuentesImpl implements FirmaDocumentoJob {

    @Autowired
    @Qualifier("jobLauncher")
    private JobLauncher jobLauncher;
    @Autowired
    @Qualifier("firmaRenuentesJob")
    private Job job;
    @Autowired
    @Qualifier("mapaFirmas")
    private HashMap mapaFirmas;

    @Override
    public void firmar(List<FirmaDigital> firmas, String numeroEmpleado) throws SGTServiceException {

        JobExecution execution;
        Long time = System.currentTimeMillis() + firmas.hashCode();
        mapaFirmas.put(String.valueOf(time), firmas);
        try {
            execution = jobLauncher.run(job, new JobParametersBuilder()
                    .addLong("time", time)
                    .addString("numeroEmpleado", numeroEmpleado)
                    .toJobParameters());
            if (execution != null && execution.getStatus().equals(BatchStatus.FAILED)) {
                StringBuilder errores = new StringBuilder().append("\n");
                for (Throwable throwable : execution.getAllFailureExceptions()) {
                    errores.append(throwable.getMessage()).append("\n").append(throwable.getCause()).append("\n");
                }
                throw new SGTServiceException("Error al realizar guardado de firmas \n" + errores.toString());
            }
        } catch (JobExecutionException ex) {
            throw new SGTServiceException("No se pudo realizar el guardado de firmas\n" + ex);
        }
    }
}
