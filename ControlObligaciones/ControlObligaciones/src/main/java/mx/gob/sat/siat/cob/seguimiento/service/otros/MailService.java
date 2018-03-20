package mx.gob.sat.siat.cob.seguimiento.service.otros;

import java.io.File;
import java.net.SocketException;

import java.sql.SQLException;

import javax.mail.MessagingException;

import org.springframework.mail.SimpleMailMessage;


/**
 *
 * @author root
 */
public interface MailService {
    /**
     *
     * @param message
     */
    void enviarCorreo(SimpleMailMessage message);

    /**
     *
     * @param destinatario
     * @param asunto
     * @param mensaje
     * @throws SocketException
     * @throws MessagingException
     */
    void enviarCorreoPara(String destinatario,String asunto, String mensaje) throws SocketException,MessagingException;

    /**
     *
     * @param destinatario
     * @param asunto
     * @param mensaje
     * @throws MessagingException
     */
    void enviarCorreoPara(String[] destinatario,String asunto, String mensaje) throws MessagingException;

    /**
     *
     * @param idEmpleado
     * @param asunto
     * @param mensaje
     * @param files
     * @throws MessagingException
     * @throws SQLException
     * @throws SocketException
     */
    void enviarCorreoIdEmpleado(String idEmpleado, String asunto, String mensaje,File... files) 
            throws MessagingException,SQLException,SocketException;
    
    /**
     *
     * @param destinatario
     * @param asunto
     * @param mensaje
     * @param files
     * @throws MessagingException
     */
    void enviarCorreoPara(String[] destinatario,String asunto, String mensaje,File... files) throws MessagingException;
    
}
