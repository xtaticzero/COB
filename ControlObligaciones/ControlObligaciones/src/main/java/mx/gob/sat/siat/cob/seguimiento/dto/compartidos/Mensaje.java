/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dto.compartidos;

import org.springframework.mail.SimpleMailMessage;

/**
 *
 * @author root
 */
public class Mensaje extends SimpleMailMessage{

    /**
     *
     */
    public Mensaje() {
    }
    
    /**
     *
     * @param to
     * @param subject
     * @param body
     */
    public Mensaje(String to, String subject, String body) {
        setTo(to);
        setSubject(subject);
        setText(body);
        
    }
    /**
     *
     * @param to
     * @param cc
     * @param subject
     * @param body
     */
    public Mensaje(String[] to,String[] cc, String subject, String body) {
        setTo(to);
        setSubject(subject);
        setText(body);
        setCc(cc);
    }
    
}
