package mx.gob.sat.siat.cob.seguimiento.service.otros.impl;

import java.io.File;
import java.net.SocketException;
import java.sql.SQLException;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import mx.gob.sat.siat.cob.seguimiento.dao.psatgj.CorreoDAO;
import mx.gob.sat.siat.cob.seguimiento.service.otros.MailService;
import static mx.gob.sat.siat.cob.seguimiento.util.MensajesError.ERR_ENVIO_CORREO;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author root
 */
@Service
public class MailServiceImpl implements MailService {

    private Logger log = Logger.getLogger(MailServiceImpl.class.getName());
    @Autowired
    @Qualifier("mailSender")
    private JavaMailSenderImpl mailSender;
    @Autowired
    private CorreoDAO correoDAO;
    
    private final static String UTF8="UTF-8";
    private final static String CORREO_SAT="vigilancias@sat.gob.mx";

    public Logger getLog() {
        return log;
    }

    public void setLog(Logger log) {
        this.log = log;
    }

    /**
     *
     * @param message
     */
    @Override
    public void enviarCorreo(SimpleMailMessage message) {
        mailSender.send(message);
    }

    /**
     *
     * @param destinatario
     * @param asunto
     * @param mensaje
     * @throws SocketException
     * @throws MessagingException
     */
    @Override
    public void enviarCorreoPara(String destinatario, String asunto, String mensaje)
            throws SocketException, MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, UTF8);
        helper.setFrom(CORREO_SAT);
        helper.setTo(destinatario);
        helper.setSubject(asunto);
        helper.setText(mensaje, true);
        mailSender.send(mimeMessage);
        getLog().info("envio de correo correcto");
    }

    /**
     *
     * @param destinatario
     * @param destinatarios
     * @param asunto
     * @param mensaje
     * @throws MessagingException
     */
    @Propagable(catched = false)
    @Override
    public void enviarCorreoPara(String[] destinatario, String asunto, String mensaje)
            throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, UTF8);
        helper.setFrom(CORREO_SAT);
        helper.setTo(destinatario);
        helper.setSubject(asunto);
        helper.setText(mensaje, true);
        mailSender.send(mimeMessage);
        getLog().info("envio de correo correcto");
    }

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
    @Transactional(readOnly = true)
    @Override
    //@Propagable(catched = false)
    public void enviarCorreoIdEmpleado(String idEmpleado, String asunto, String mensaje, File... files)
            throws MessagingException, SQLException, SocketException {
        getLog().debug("enviarCorreoIdEmpleado:" + idEmpleado);
        String destinatarios = correoDAO.obtenerCorreoPorEmpleado(idEmpleado);
        if (destinatarios != null && destinatarios.length() > 5 && destinatarios.indexOf('@') > 0) {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, UTF8);
            helper.setFrom(CORREO_SAT);
            helper.setTo(destinatarios.split(","));
            helper.setSubject(asunto);
            helper.setText(mensaje, true);
            if (files != null) {
                for (File file : files) {
                    helper.addAttachment(file.getName(), file);
                }
            }
            try {
                mailSender.send(mimeMessage);
                getLog().info("envio de correo correcto");
            } catch (MailException ex) {
                getLog().error(ERR_ENVIO_CORREO);
                throw new MessagingException(ex.getCause().toString(), ex);
            }

        } else {
            getLog().error("no se encontro registros de correo para:" + idEmpleado);
        }
    }

    /**
     *
     * @param destinatario
     * @param asunto
     * @param mensaje
     * @param files
     * @throws MessagingException
     */
    @Override
    public void enviarCorreoPara(String[] destinatario, String asunto, String mensaje,
            File[] files) throws MessagingException {
        log.debug("-->enviarCorreoPara");
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, UTF8);
        helper.setFrom(CORREO_SAT);
        helper.setTo(destinatario);
        helper.setSubject(asunto);
        helper.setText(mensaje, true);
        for (File file : files) {
            log.debug("Attaching Files...");
            helper.addAttachment(file.getName(), file);
        }
        log.debug("Attaching Files Done!");
        log.debug("Sending...");
        log.debug("Subject:" + asunto);
        log.debug("Body:" + mensaje);
        mailSender.send(mimeMessage);
        log.debug("Sent!");
        log.debug("<--enviarCorreoPara");
    }
}
