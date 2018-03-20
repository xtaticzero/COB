/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.service.renuente.impl;

import java.util.Map;
import mx.gob.sat.siat.cob.background.service.renuente.ArchivoRenuenteThreadService;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.util.PoolThread;
import org.apache.log4j.Logger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author juan
 */
@Service(value = "archivoRenuenteThreadService")
public class ArchivoRenuenteThreadServiceImpl extends PoolThread
        implements ArchivoRenuenteThreadService, Runnable {

    private final Logger log = Logger.getLogger(ArchivoRenuenteThreadServiceImpl.class);
    private JobParameters jobParameters;
    private JobLauncher launcher;
    private Job archivoRenuenteJob;
    private Map<String, String> referencias;

    @Override
    public void ejecutar() throws SGTServiceException {
        try {
            jobParameters = new JobParametersBuilder()
                    .addString("idTipoDocumento", referencias.get("idTipoDocumento"))
                    .addString("ultimoEstado", referencias.get("ultimoEstado"))
                    .addString("idObligaciones", referencias.get("idObligaciones"))
                    .addString("rutaArchivo", referencias.get("rutaArchivo"))
                    .addString("idsBusqRen", referencias.get("idsBusqRen"))
                    .addString("wherePB", referencias.get("wherePB"))
                    .addLong("time", System.currentTimeMillis()).toJobParameters();

            launcher.run(archivoRenuenteJob, jobParameters);
            log.info("###  Termina Generar Archivos");
        } catch (JobExecutionAlreadyRunningException ex) {
            log.error(ex);
        } catch (JobRestartException ex) {
            log.error(ex);
        } catch (JobInstanceAlreadyCompleteException ex) {
            log.error(ex);
        } catch (JobParametersInvalidException ex) {
            log.error(ex);
        } finally {
            setEstado(FINISHED);
        }
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRES_NEW)
    public void run() {
        try {
            ejecutar();
        } catch (SGTServiceException ex) {
            log.debug("No fue posible ejecutar el ID: "
                    + referencias.get("idBusqRenuente")
                    + " para la generacion de archivo renuentes. "
                    + ex.toString());
        }
    }

    public void setLauncher(JobLauncher launcher) {
        this.launcher = launcher;
    }

    public void setArchivoRenuenteJob(Job archivoRenuenteJob) {
        this.archivoRenuenteJob = archivoRenuenteJob;
    }

    public void setReferencias(Map<String, String> referencias) {
        this.referencias = referencias;
    }

}
