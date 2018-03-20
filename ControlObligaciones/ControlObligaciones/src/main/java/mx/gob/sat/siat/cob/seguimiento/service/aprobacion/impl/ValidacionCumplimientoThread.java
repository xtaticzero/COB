/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.service.aprobacion.impl;

import java.util.HashMap;
import java.util.Map;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.AdministrativoNivelCargo;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ParametrosValidacionCumplimiento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EventoCargaEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoServiciosConcurrentesEnum;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.service.aprobacion.AprobarVigilanciasService;
import static mx.gob.sat.siat.cob.seguimiento.service.aprobacion.AprobarVigilanciasService.PORCENTAJE_TOTAL;
import mx.gob.sat.siat.cob.seguimiento.service.otros.AdministracionFuncionariosService;
import mx.gob.sat.siat.cob.seguimiento.service.otros.ConcurrenceService;
import mx.gob.sat.siat.cob.seguimiento.service.otros.MailService;
import mx.gob.sat.siat.cob.seguimiento.util.IntegerMutable;
import mx.gob.sat.siat.cob.seguimiento.util.ProcesoEnvioEMail;
import mx.gob.sat.siat.cob.seguimiento.util.Utilerias;
import mx.gob.sat.siat.cob.seguimiento.util.constante.TipoEnvioEnum;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

/**
 *
 * @author root
 */
@Component
@Scope("prototype")
public class ValidacionCumplimientoThread extends Thread {

    @Autowired
    private AprobarVigilanciasService aprobarVigilanciasService;
    @Autowired
    private MailService mailService;
    @Autowired
    @Qualifier("taskExecutorMail")
    private ThreadPoolTaskExecutor taskExecutorMail;
    private String numeroCarga;
    private String numeroEmpleado;
    private String descripcionVigilancia;
    private IntegerMutable progress;
    private final Logger logger = Logger.getLogger(ValidacionCumplimientoThread.class);
    private SGTServiceException exception;
    @Autowired
    private ConcurrenceService concurrenceServiceImpl;
    @Autowired
    private AdministracionFuncionariosService administracionFuncionariosService;
    private String firma;
    private static final String CENTRAL = "central";

    public void setParametros(String numeroEmpleado,
            String numeroCarga,
            IntegerMutable progress, String descripcionVigilancia) throws SGTServiceException {
        ParametrosValidacionCumplimiento pvc = new ParametrosValidacionCumplimiento(numeroEmpleado, numeroCarga);
        AdministrativoNivelCargo administrativo = administracionFuncionariosService.buscarCargoAdministrativo(numeroEmpleado, EventoCargaEnum.CARGA_OMISOS);
        if (administrativo.getIdAdministacionLocal() != null) {
            firma = numeroCarga + administrativo.getIdAdministacionLocal();
        } else {
            firma = numeroCarga + CENTRAL;
        }

        try {
            if (!concurrenceServiceImpl.lockServices(TipoServiciosConcurrentesEnum.IDENTIFICADOR_VIGILANCIA_DOCUMENTOS, firma)) {
                progress.setValor(PORCENTAJE_TOTAL);
                exception = new SGTServiceException("Validación en proceso, le llegara un correo en cuanto finalice.");
                throw exception;
            }
        } catch (SGTServiceException ex) {
            logger.error(ex);
            exception = new SGTServiceException("Error al intentar hacer la validación del cumplimiento. Intente nuevamente.");
        }

        if (aprobarVigilanciasService.getParametrosEnProcesoValidacion().contains(pvc)) {
            progress.setValor(PORCENTAJE_TOTAL);

            exception = new SGTServiceException("Validación en proceso, le llegara un correo en cuanto finalice.");
            throw exception;
        }
        this.numeroCarga = numeroCarga;
        this.numeroEmpleado = numeroEmpleado;
        this.progress = progress;
        this.descripcionVigilancia = descripcionVigilancia;
    }

    @Override
    public void run() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("numeroVigilancia", numeroCarga);
        map.put("descripcionVigilancia", descripcionVigilancia);
        try {
            aprobarVigilanciasService.verificarCumplimientos(numeroCarga, numeroEmpleado, progress);
            if (!concurrenceServiceImpl.unlockServices(TipoServiciosConcurrentesEnum.IDENTIFICADOR_VIGILANCIA_DOCUMENTOS, firma)) {
                exception = new SGTServiceException("No termino satisfactoriamente la validación del cumplimiento. Intente nuevamente.");
                //borrar archivo
            } else {
                enviarCorreo("MAT CO - Terminó correctamente validación de cumplimientos",
                        Utilerias.obtenerCorreo("validacionCumplimiento", map));
            }
        } catch (SGTServiceException e) {
            logger.error(e.getMessage(), e);
            exception = new SGTServiceException("No termino satisfactoriamente la validación del cumplimiento. Espere 5 minutos e intente nuevamente.");
            enviarCorreo("MAT CO - Error al validar de cumplimientos",
                    Utilerias.obtenerCorreo("errorValidarCumplimientos", map));
        } catch (DataAccessException ex) {
            logger.error(ex.getMessage(), ex);
            exception = new SGTServiceException("No termino satisfactoriamente la validación del cumplimiento. Espere 5 minutos e intente nuevamente.");
            enviarCorreo("MAT CO - Error al validar de cumplimientos",
                    Utilerias.obtenerCorreo("errorValidarCumplimientos", map));
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            exception = new SGTServiceException("No termino satisfactoriamente la validación del cumplimiento. Espere 5 minutos e intente nuevamente.");
            enviarCorreo("MAT CO - Error al validar de cumplimientos",
                    Utilerias.obtenerCorreo("errorValidarCumplimientos", map));
        } finally {
            if (!concurrenceServiceImpl.unlockServices(TipoServiciosConcurrentesEnum.IDENTIFICADOR_VIGILANCIA_DOCUMENTOS, firma)) {
                exception = new SGTServiceException("No termino satisfactoriamente la validación del cumplimiento. Intente nuevamente.");
                //borrar archivo
            }
            aprobarVigilanciasService.remover(numeroCarga, numeroEmpleado);
            progress.setValor(PORCENTAJE_TOTAL);
        }
    }

    private void enviarCorreo(String subject, String body) {
        taskExecutorMail.execute(new ProcesoEnvioEMail(numeroEmpleado,
                subject,
                body, mailService, TipoEnvioEnum.ID));
    }

    public SGTServiceException getException() {
        return exception;
    }

    public String getNumeroCarga() {
        return numeroCarga;
    }
}
