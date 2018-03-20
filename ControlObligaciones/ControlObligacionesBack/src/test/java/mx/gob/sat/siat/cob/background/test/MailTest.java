/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.SocketException;
import java.sql.SQLException;
import javax.mail.MessagingException;
import mx.gob.sat.siat.cob.seguimiento.dto.compartidos.Mensaje;
import mx.gob.sat.siat.cob.seguimiento.service.otros.MailService;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.core.io.Resource;

/**
 *
 * @author root
 */
public class MailTest extends ContextLoaderTest {

    @Test
    @Ignore
    public void pruebaEnviaCorreo() {
        Resource resource = getContext().getResource("classpath:mail/prueba.mail");
        try {
            BufferedReader br=new BufferedReader(new FileReader(resource.getFile()));
            StringBuilder b=new StringBuilder();
            String s=br.readLine();
            while(s!=null){
                b.append(s);
                s=br.readLine();
            } 
            log.info(b.toString());
        } catch (IOException ex) {
            log.error(ex);
        }
        MailService mailService = (MailService) getContext().getBean("mailServiceImpl");
        Mensaje mensaje = new Mensaje("MAPR88BB@dssat.sat.gob.mx", "Hola Correo", "<b>Hola</b>");
        mailService.enviarCorreo(mensaje);
    }

    @Test
    @Ignore
    public void envioIdEmpleado(){
        MailService mailService = (MailService) getContext().getBean("mailServiceImpl");
        try {
//            mailService.enviarCorreoPara("correo@gmail.com", "asunto", "mensaje 1");
            mailService.enviarCorreoIdEmpleado("00000006884", "asunto", "mensaje");
        } catch (MessagingException ex) {
            log.error(ex);
        } catch (SQLException ex) {
            log.error(ex);
        } catch (SocketException ex) {
            log.error(ex);
        }
    }
}
