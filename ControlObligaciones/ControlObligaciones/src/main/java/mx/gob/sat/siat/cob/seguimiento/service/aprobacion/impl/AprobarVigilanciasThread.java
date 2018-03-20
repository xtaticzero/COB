/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.service.aprobacion.impl;

import java.net.SocketException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.mail.MessagingException;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VigilanciaAdministracionLocal;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.service.aprobacion.AprobarVigilanciasService;
import mx.gob.sat.siat.cob.seguimiento.service.otros.MailService;
import mx.gob.sat.siat.cob.seguimiento.util.IntegerMutable;
import mx.gob.sat.siat.cob.seguimiento.util.Utilerias;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author root
 */
@Component
@Scope("prototype")
public class AprobarVigilanciasThread extends Thread {

    @Autowired
    private AprobarVigilanciasService aprobarVigilanciasService;
    @Autowired
    private MailService mailService;
    private VigilanciaAdministracionLocal vigilancia;
    private String numeroEmpleado;
    private IntegerMutable progress;
    private final Logger logger = Logger.getLogger(AprobarVigilanciasThread.class);
    private SGTServiceException exception;

    public void establecerValoresEjecucion(VigilanciaAdministracionLocal vigilancia,
            String numeroEmpleado,
            IntegerMutable progress) {
        this.vigilancia = vigilancia;
        this.numeroEmpleado = numeroEmpleado;
        this.progress = progress;
    }

    @Override
    public void run() {
        try {
            aprobarVigilanciasService.aprobarVigilancia(vigilancia, numeroEmpleado, progress);
        } catch (SGTServiceException e) {
            exception = e;
            if (exception.getCause() instanceof MessagingException) {
                logger.error(e, e);                
            } else {
                try {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("numeroVigilancia", vigilancia.getNumeroCarga());
                    mailService.enviarCorreoIdEmpleado(
                            numeroEmpleado,
                            "MAT CO - Falló aprobación de vigilancia",
                            Utilerias.obtenerCorreo("errorAprobacionVigilancia", map));
                } catch (MessagingException ex) {
                    logger.error(ex, ex);
                } catch (SQLException ex) {
                    logger.error(ex, ex);
                } catch (SocketException ex) {
                    logger.error(ex,ex);
                }
            }
            logger.error(e, e);
        } finally {
            synchronized (progress) {
                aprobarVigilanciasService.removerProcesoAprobar(vigilancia, numeroEmpleado);
                progress.setValor(AprobarVigilanciasService.PORCENTAJE_TOTAL);
                progress.notify();                
            }
        }
    }

    public SGTServiceException getException() {
        return exception;
    }
}
