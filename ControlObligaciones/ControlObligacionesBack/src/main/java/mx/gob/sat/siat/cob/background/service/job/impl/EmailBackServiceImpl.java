/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.service.job.impl;

import java.util.List;
import javax.mail.MessagingException;
import mx.gob.sat.siat.cob.background.service.job.EmailBackService;
import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.EmailReporteProcesoDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.MonitorArchivoArca;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.EmailReporteProceso;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ParametrosSgtDTO;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.service.otros.MailService;
import mx.gob.sat.siat.cob.seguimiento.service.otros.SGTService;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Juan
 */
@Service
public class EmailBackServiceImpl implements EmailBackService {

    private Logger log = Logger.getLogger(EmailBackServiceImpl.class);
    @Autowired
    private MailService mailService;
    @Autowired
    private EmailReporteProcesoDAO emailReporteProcesoDAO;
    @Autowired
    private SGTService sgtService;

    @Override
    public void enviarCorreo(String asuntoProceso, MonitorArchivoArca monitor) throws SGTServiceException {
        try {
            List<ParametrosSgtDTO> parametrosVigentesSgt = sgtService.obtenerParametrosVigentesSgt();
            String ambiente = "";
            for (ParametrosSgtDTO parametro : parametrosVigentesSgt) {
                if (parametro.getIdParametro() == ConstantsCatalogos.SEIS) {
                    ambiente = parametro.getValor();
                }
            }
            List<EmailReporteProceso> emails = emailReporteProcesoDAO.todosLosEmailReporteProceso(false);
            StringBuilder sEmails = new StringBuilder("");
            for (EmailReporteProceso emailReporteProceso : emails) {
                sEmails.append(emailReporteProceso.getCorreoElectronico()).append(",");
                if (emailReporteProceso.getCorreoElectronicoAlterno() != null) {
                    sEmails.append(emailReporteProceso.getCorreoElectronicoAlterno()).append(",");
                }
            }
            String[] destinatarios = sEmails.toString().split(",");
            StringBuilder mensaje = new StringBuilder();
            mensaje.append("Ocurrió un error en base de datos. <br/>").
                    append("Falló el envío de ").append(asuntoProceso).
                    append(" relacionados a la vigilancia ").
                    append(monitor.getIdVigilancia()).
                    append(", la local ").append(monitor.getIdAdmonLocal()).
                    append(" y ").append(asuntoProceso).append(" con fecha y hora de envio ").
                    append(monitor.getFechaEnvioArca()).append(". En breve se intentará de nuevo el envío.");
            mailService.enviarCorreoPara(destinatarios, "MAT CO " + ambiente + " - " + "Error al enviar " + asuntoProceso, mensaje.toString());
        } catch (MessagingException ex) {
            log.error(ex.getMessage(), ex);
            throw new SGTServiceException(ex);
        }

    }
}
