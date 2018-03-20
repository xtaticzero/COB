package mx.gob.sat.siat.cob.background.service.job.impl;

import mx.gob.sat.siat.cob.background.service.job.GenerarMultaCreditoThreadService;
import mx.gob.sat.siat.cob.background.util.UtileriasBackground;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VigilanciaAdministracionLocal;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EsMultaEnum;
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
 * @author Juan
 */
@Service(value = "generarMultaCreditoThreadService")
public class GenerarMultaCreditoThreadServiceImpl extends PoolThread
        implements GenerarMultaCreditoThreadService, Runnable {

    private static Logger log = Logger.getLogger(GenerarMultaCreditoThreadServiceImpl.class);
    private VigilanciaAdministracionLocal vigilancia;
    private JobParameters jobParameters;
    private JobLauncher launcher;
    private Job generarCreditoJob;
    private VigilanciaAdministracionLocalService vigilanciaService;
    private String joinNivelEmison;
    private String esOperacionMat;
    private String camposCbz;
    private String joinCbz;
    private String camposFirmaDigital;
    private String joinFirmaDigital;

    @Override
    public void ejecutar(VigilanciaAdministracionLocal vigilancia) throws SeguimientoDAOException {
        log.info("### ID_VIGILANCIA -->" + vigilancia.getIdVigilancia());
        log.info("### ID_ADMON_LOCAL -->" + vigilancia.getIdAdministracionLocal());
        joinNivelEmison = UtileriasBackground.determinaNivelEmision(vigilancia, EsMultaEnum.ES_MULTA);
        try {
            jobParameters = new JobParametersBuilder()
                    .addLong("idVigilancia", vigilancia.getIdVigilancia())
                    .addString("idAdmonLocal", vigilancia.getIdAdministracionLocal())
                    .addString("joinNivelEmision", joinNivelEmison)
                    .addString("isEnvioMulta", String.valueOf(ConstantsCatalogos.UNO))
                    .addString("joinCbz", joinCbz)
                    .addString("camposCbz", camposCbz)
                    .addString("esOperacionMat", esOperacionMat)
                    .addString("camposFirmaDigital", camposFirmaDigital)
                    .addString("joinFirmaDigital", joinFirmaDigital)
                    .addLong("time", System.currentTimeMillis()).toJobParameters();
            launcher.run(generarCreditoJob, jobParameters);
            log.info("###  Termina proceso MULTAS COBRANZA");
        } catch (JobExecutionAlreadyRunningException ex) {
            log.error(ex);
        } catch (JobRestartException ex) {
            log.error(ex);
        } catch (JobInstanceAlreadyCompleteException ex) {
            log.error(ex);
        } catch (JobParametersInvalidException ex) {
            log.error(ex);
        } finally {
            try {
                if (vigilanciaService.obtenerVigilancia(vigilancia.getIdVigilancia(), vigilancia.getIdAdministracionLocal()) != null) {
                    vigilanciaService.actualizaFechaVigilancia(vigilancia.getIdVigilancia(), vigilancia.getIdAdministracionLocal());
                } else {
                    vigilanciaService.guardaVigAdminLocal(vigilancia.getIdVigilancia(), vigilancia.getIdAdministracionLocal());
                }
                setEstado(FINISHED);
            } catch (SGTServiceException ex) {
                log.error(" No se pudo actualizar la fecha envio arca " + ex);
            }
        }
    }

    public VigilanciaAdministracionLocal obtenerIdVigilancia() {
        try {
            return vigilanciaService.obtenerVigilanciaMultaBatch();
        } catch (SGTServiceException ex) {
            log.error(ex);
        }
        return null;
    }

    @Override
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRES_NEW)
    public void run() {
        try {
            log.debug("Ejecutando Vigilancia: " + vigilancia.getIdVigilancia());
            this.ejecutar(vigilancia);
            log.debug("Fin de proceso " + vigilancia.getIdVigilancia());
        } catch (Exception e) {
            log.debug("No fue posible ejecutar la vigilancia." + e.toString());
        }
    }

    public VigilanciaAdministracionLocal getVigilancia() {
        return vigilancia;
    }

    public void setVigilancia(VigilanciaAdministracionLocal vigilancia) {
        this.vigilancia = vigilancia;
    }

    public void setLauncher(JobLauncher launcher) {
        this.launcher = launcher;
    }

    public void setGenerarCreditoJob(Job generarCreditoJob) {
        this.generarCreditoJob = generarCreditoJob;
    }

    public void setVigilanciaService(VigilanciaAdministracionLocalService vigilanciaService) {
        this.vigilanciaService = vigilanciaService;
    }

    public void setJoinNivelEmison(String joinNivelEmison) {
        this.joinNivelEmison = joinNivelEmison;
    }

    public void setCamposCbz(String camposCbz) {
        this.camposCbz = camposCbz;
    }

    public void setJoinCbz(String joinCbz) {
        this.joinCbz = joinCbz;
    }

    public void setEsOperacionMat(String esOperacionMat) {
        this.esOperacionMat = esOperacionMat;
    }

    public void setCamposFirmaDigital(String camposFirmaDigital) {
        this.camposFirmaDigital = camposFirmaDigital;
    }

    public void setJoinFirmaDigital(String joinFirmaDigital) {
        this.joinFirmaDigital = joinFirmaDigital;
    }
}
