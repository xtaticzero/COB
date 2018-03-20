/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.service.job.impl;

import java.util.ArrayList;
import java.util.List;
import mx.gob.sat.siat.cob.background.service.job.CargaArchivoMasivoService;
import static mx.gob.sat.siat.cob.background.service.job.impl.CargaArchivoMasivoServiceImpl.FINISHED;
import mx.gob.sat.siat.cob.background.util.UtileriasBackground;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VigilanciaAdministracionLocal;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EsMultaEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.SituacionVigilanciaEnum;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.service.extraccion.VigilanciaAdministracionLocalService;
import mx.gob.sat.siat.cob.seguimiento.util.PoolThread;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
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
 * @author root
 */
@Service(value = "CargaArchivoMasivoService")
public class CargaArchivoMasivoServiceImpl extends PoolThread
        implements CargaArchivoMasivoService, Runnable {

    private static Logger logger = Logger.getLogger(CargaArchivoMasivoServiceImpl.class);
    private VigilanciaAdministracionLocal vigilancia;
    private JobParameters jobParameters;
    private JobLauncher launcher;
    private Job cargaMasivaArchivosJob;
    private VigilanciaAdministracionLocalService vigilanciaService;
    private String camposFirmaDigital;
    private String joinFirmaDigital;

    @Override
    public void ejecutar(VigilanciaAdministracionLocal vigilancia) throws SeguimientoDAOException {
        logger.info("### ID_VIGILANCIA -->" + vigilancia.getIdVigilancia());
        logger.info("### ID_VAL -->" + vigilancia.getIdAdministracionLocal());
        String joinNivelEmison = UtileriasBackground.determinaNivelEmision(vigilancia, EsMultaEnum.NO_ES_MULTA);
        List<VigilanciaAdministracionLocal> updateVigilancias = new ArrayList<VigilanciaAdministracionLocal>();
        updateVigilancias.add(vigilancia);
        try {
            if (hayRegistros(vigilancia, joinNivelEmison)) {

                jobParameters = new JobParametersBuilder()
                        .addLong("idVigilancia", vigilancia.getIdVigilancia())
                        .addString("idAdmonLocal", vigilancia.getIdAdministracionLocal())
                        .addString("joinNivelEmision", joinNivelEmison)
                        .addLong("situacionVigilancia", vigilancia.getIdSituacionVigilancia())
                        .addString("isEnvioMulta", String.valueOf(ConstantsCatalogos.CERO))
                        .addString("camposFirmaDigital", camposFirmaDigital)
                        .addString("joinFirmaDigital", joinFirmaDigital)
                        .addLong("time", System.currentTimeMillis())
                        .toJobParameters();

                launcher.run(cargaMasivaArchivosJob, jobParameters);
                logger.info("###  Termina proceso Job ID_VIGILANCIA : "
                        + vigilancia.getIdVigilancia()
                        + " ID_VAL : " + vigilancia.getIdAdministracionLocal());
            }
            if (hayDocumentos(vigilancia)) {
                logger.info("La vigilancia: " + vigilancia.getIdVigilancia() + " con local "
                        + vigilancia.getIdAdministracionLocal()
                        + " cuenta con datos para procesar, por lo \n"
                        + " tanto a cambiado su estado; erronea");
                actualizaSituacionVigilancia(updateVigilancias, SituacionVigilanciaEnum.ERRONEA);
            } else {
                logger.info("La vigilancia: " + vigilancia.getIdVigilancia() + " con local "
                        + vigilancia.getIdAdministracionLocal()
                        + " no cuenta con datos para procesar, por lo \n"
                        + " tanto a cambiado su estado; enviada a Arca");
                actualizaSituacionVigilancia(updateVigilancias, SituacionVigilanciaEnum.ENVIADA_ARCA);
            }
        } catch (JobExecutionAlreadyRunningException ex) {
            logger.error(ex);
        } catch (JobRestartException ex) {
            logger.error(ex);
        } catch (JobInstanceAlreadyCompleteException ex) {
            logger.error(ex);
        } catch (JobParametersInvalidException ex) {
            logger.error(ex);
        } finally {
            setEstado(FINISHED);
        }
    }

    @Override
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRES_NEW)
    public void run() {
        try {
            this.ejecutar(vigilancia);
        } catch (Exception e) {
            logger.error("No fue posible ejecutar la vigilancia." + e.toString());
        }
    }

    public boolean hayRegistros(VigilanciaAdministracionLocal vigilanciaAdminLocal, String joinNivelEmision) {
        try {
            return vigilanciaService.hayRegistrosPorVigilancia(vigilanciaAdminLocal, joinNivelEmision);
        } catch (SGTServiceException ex) {
            logger.error(ex);
        }
        return false;
    }

    public void actualizaSituacionVigilancia(List<VigilanciaAdministracionLocal> vigilancias, SituacionVigilanciaEnum situacionVigilanciaEnum) {
        try {
            vigilanciaService.updateSituacionVigilancia(vigilancias, situacionVigilanciaEnum);
        } catch (SGTServiceException ex) {
            logger.error(ex);
        }
    }

    private boolean hayDocumentos(VigilanciaAdministracionLocal vigilanciaAdminLocal) {
        List<VigilanciaAdministracionLocal> vigilancias = null;
        try {
            vigilancias = vigilanciaService.obtenerVigilanciasConDocumentos(vigilanciaAdminLocal);
            if (vigilancias != null && !vigilancias.isEmpty()) {
                return true;
            }
        } catch (SGTServiceException ex) {
            logger.error(ex);
        }
        return false;
    }

    public VigilanciaAdministracionLocal getVigilancia() {
        return vigilancia;
    }

    public void setVigilancia(VigilanciaAdministracionLocal vigilancia) {
        this.vigilancia = vigilancia;
    }

    public void setJobParameters(JobParameters jobParameters) {
        this.jobParameters = jobParameters;
    }

    public void setLauncher(JobLauncher launcher) {
        this.launcher = launcher;
    }

    public void setCargaMasivaArchivosJob(Job cargaMasivaArchivosJob) {
        this.cargaMasivaArchivosJob = cargaMasivaArchivosJob;
    }

    public void setVigilanciaService(VigilanciaAdministracionLocalService vigilanciaService) {
        this.vigilanciaService = vigilanciaService;
    }

    public void setCamposFirmaDigital(String camposFirmaDigital) {
        this.camposFirmaDigital = camposFirmaDigital;
    }

    public void setJoinFirmaDigital(String joinFirmaDigital) {
        this.joinFirmaDigital = joinFirmaDigital;
    }
}