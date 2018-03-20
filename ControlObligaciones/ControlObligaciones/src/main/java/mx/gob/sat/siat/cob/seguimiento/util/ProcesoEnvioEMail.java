package mx.gob.sat.siat.cob.seguimiento.util;

import java.io.File;
import java.net.SocketException;
import java.sql.SQLException;
import javax.mail.MessagingException;
import mx.gob.sat.siat.cob.seguimiento.service.otros.MailService;
import mx.gob.sat.siat.cob.seguimiento.util.constante.TipoEnvioEnum;
import org.apache.log4j.Logger;
import org.springframework.mail.MailSendException;

/**
 *
 * @author christian.ventura
 */
public class ProcesoEnvioEMail implements Runnable {
    private Logger log =Logger.getLogger(ProcesoEnvioEMail.class);
    private static final int TIEMPO_ESPERA_ENVIO_CORREO=60000;
    private static final String MENSAJE_REINTENTO_ENVIO = "se intentara reenviar el correo en 60 segundos...";
    private MailService mailService;
    private String destinatario;
    private String asunto;
    private String mensaje;
    private TipoEnvioEnum tipoEnvio;
    private File[] files;

    /**
     *
     * @param destinatario
     * @param asunto
     * @param mensaje
     * @param mailService
     * @param tipoEnvio
     */
    public ProcesoEnvioEMail(String destinatario,String asunto, String mensaje,
            MailService mailService,TipoEnvioEnum tipoEnvio, File ... files) {
        this.destinatario=destinatario;
        this.asunto=asunto;
        this.mensaje=mensaje;
        this.mailService=mailService;
        this.tipoEnvio=tipoEnvio;
        this.files=files;
    }

    @Override
    public void run() {
        boolean isSend;
        do{
            try {
                if(tipoEnvio.equals(TipoEnvioEnum.MAIL)){
                    mailService.enviarCorreoPara(destinatario,asunto,mensaje);
                }else if(tipoEnvio.equals(TipoEnvioEnum.ID)){
                    mailService.enviarCorreoIdEmpleado(destinatario,asunto,mensaje, files);
                }
                log.info("envio de correo correcto para: "+destinatario);
                isSend=true;
            } catch (MailSendException ex) {
                log.error(tipoEnvio+"-"+destinatario+"--"+ex);
                isSend=false;
                log.error(MENSAJE_REINTENTO_ENVIO);
            } catch (SocketException ex) {
                log.error(tipoEnvio+"-"+destinatario+"--"+ex);
                isSend=false;
                log.error(MENSAJE_REINTENTO_ENVIO);
            } catch (MessagingException ex) {
                log.error(tipoEnvio+"-"+destinatario+"--"+ex);
                isSend=false;
                log.error(MENSAJE_REINTENTO_ENVIO);
            } catch (SQLException ex) {
                log.error(tipoEnvio+"-"+destinatario+"--"+ex);
                isSend=false;
                log.error(MENSAJE_REINTENTO_ENVIO);
            }
            try {
                Thread.sleep(TIEMPO_ESPERA_ENVIO_CORREO);
            } catch (InterruptedException e) {
                log.error(e);
            }
        }while(!isSend);
    }
}