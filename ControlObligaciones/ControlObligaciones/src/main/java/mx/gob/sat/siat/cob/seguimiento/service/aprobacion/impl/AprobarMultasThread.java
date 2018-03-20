/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.service.aprobacion.impl;

import org.apache.log4j.Logger;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.AdministrativoNivelCargo;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.MultaAprobarGrupo;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoServiciosConcurrentesEnum;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.service.aprobacion.AprobarMultasService;
import static mx.gob.sat.siat.cob.seguimiento.service.aprobacion.AprobarMultasService.PORCENTAJE_TOTAL;
import mx.gob.sat.siat.cob.seguimiento.service.otros.ConcurrenceService;
import mx.gob.sat.siat.cob.seguimiento.service.otros.MailService;
import mx.gob.sat.siat.cob.seguimiento.util.IntegerMutable;
import mx.gob.sat.siat.cob.seguimiento.util.ProcesoEnvioEMail;
import mx.gob.sat.siat.cob.seguimiento.util.Utilerias;
import mx.gob.sat.siat.cob.seguimiento.util.constante.TipoEnvioEnum;
import mx.gob.sat.siat.modelo.dto.SegbMovimientoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

/**
 *
 * @author root
 */
@Component
@Scope("prototype")
public class AprobarMultasThread extends Thread {
    
    @Autowired
    private AprobarMultasService aprobarMultasService;
    @Autowired
    private ConcurrenceService concurrenceServiceImpl;
    @Autowired
    private MailService mailService;
    @Autowired
    @Qualifier("taskExecutorMail")
    private ThreadPoolTaskExecutor taskExecutorMail;
    private final Logger logger = Logger.getLogger(AprobarMultasThread.class);
    private MultaAprobarGrupo multaAprobarGrupo;
    private SegbMovimientoDTO segMovDto;
    private AdministrativoNivelCargo administrativoNivelCargo;
    private IntegerMutable progress;
    private String archivoResultados = null;
    private SGTServiceException exception = null;
    private String firma;
    
    public void establecerValoresEjecucion(MultaAprobarGrupo multaAprobarGrupo, SegbMovimientoDTO segMovDto,
            AdministrativoNivelCargo administrativoNivelCargo, IntegerMutable progress) throws SGTServiceException {
        firma = aprobarMultasService.generaFirma(multaAprobarGrupo, administrativoNivelCargo);
        logger.info("Intentando bloquear el grupo de multas " + firma);
        if (!concurrenceServiceImpl.lockServices(TipoServiciosConcurrentesEnum.IDENTIFICADOR_APROBAR_MULTAS, firma)) {
            progress.setValor(PORCENTAJE_TOTAL);
            exception = new SGTServiceException(AprobarMultasService.MULTAS_EN_EJECUCION);
            throw exception;
        }
        logger.info("Si se logro hacer el bloqueo " + firma);
        this.administrativoNivelCargo = administrativoNivelCargo;
        this.segMovDto = segMovDto;
        this.multaAprobarGrupo = multaAprobarGrupo;
        this.progress = progress;
    }
    
    @Override
    public void run() {
        try {
            archivoResultados = aprobarMultasService.emitirMultas(multaAprobarGrupo, segMovDto, administrativoNivelCargo, progress);
        } catch (SGTServiceException ex) {
            exception = ex;
            logger.error("No se pudieron aprobar las multas para la firma " + firma + "\n" + ex);
            StringBuilder contenidoCorreo = new StringBuilder();
            contenidoCorreo.append("No terminó satisfactoriamente el proceso de emisión de multas del ")
                    .append(Utilerias.formatearFechaAAAAMMDD(multaAprobarGrupo.getFechaEmision())).append(", ")
                    .append("con los siguientes datos: </br>")
                    .append("Medio de envio: ")
                    .append(multaAprobarGrupo.getMedioEnvio()).append("</br>")
                    .append("Tipo de firma: ")
                    .append(multaAprobarGrupo.getTipoFirma()).append("</br>")
                    .append("Tipo de multa: ")
                    .append(multaAprobarGrupo.getTipoMulta()).append("</br>")
                    .append("Por tal razón, se requiere que intente nuevamente su emisión.")
                    .append("</br>")
                    .append("<p>Por favor no responda a este mensaje, fue enviado desde una cuenta de correo electr&oacute;nico no monitoreada.</p>");
            taskExecutorMail.execute(new ProcesoEnvioEMail(administrativoNivelCargo.getNumeroEmpleado(),
                    "MAT CO - Inconsistencia Emisión de Multas.",
                    contenidoCorreo.toString(), mailService, TipoEnvioEnum.ID));
            
            logger.error(ex, ex);
        } catch (Exception ex) {
            logger.error("Excepcion general\n" + ex);
            exception = new SGTServiceException("Error al emitir las multas.");
        } finally {
            logger.info("Desbloqueando la firma " + firma);
            if (!concurrenceServiceImpl.unlockServices(TipoServiciosConcurrentesEnum.IDENTIFICADOR_APROBAR_MULTAS, firma)) {
                exception = new SGTServiceException("No se pudo hacer el desbloqueo de este proceso de emision de multas.");
            }
            progress.setValor(PORCENTAJE_TOTAL);
        }
    }
    
    public String getArchivoResultados() {
        return archivoResultados;
    }
    
    public void setArchivoResultados(String archivoResultados) {
        this.archivoResultados = archivoResultados;
    }
    
    public SGTServiceException getException() {
        return exception;
    }
}
