package mx.gob.sat.siat.cob.seguimiento.service.otros.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import java.util.List;
import java.util.Properties;

import javax.mail.MessagingException;

import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.EmailReporteProcesoDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.ParametroSgtDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.EmailReporteProceso;
import mx.gob.sat.siat.cob.seguimiento.dto.db2.HistoricoCumplimiento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ParametrosSgtDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.ParametroSgtEnum;
import mx.gob.sat.siat.cob.seguimiento.service.otros.MailService;
import mx.gob.sat.siat.cob.seguimiento.service.otros.ValidacionCumplimientoFailWriter;
import mx.gob.sat.siat.cob.seguimiento.util.Utilerias;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Service;


@Service("validacionCumplimientoFailWriter")
public class ValidacionCumplimientoFailWriterImpl implements ValidacionCumplimientoFailWriter {
    private Logger logger = Logger.getLogger(ValidacionCumplimientoFailWriterImpl.class);
    @Autowired
    @Qualifier("validacionCumplimientoProperties")
    private Properties properties;
    @Autowired
    @Qualifier("mailServiceImpl")
    private MailService mailService;
    @Autowired
    private EmailReporteProcesoDAO emailReporteProcesoDAO;
    @Autowired
    private ParametroSgtDAO parametroSgtDAO;
    
    public ValidacionCumplimientoFailWriterImpl() {
        super();
    }

    @Override
    public void reportarIncidencias(String processId,String vigilancia,List<HistoricoCumplimiento> historicosCumplimiento) throws IOException {
        try {
            String path = properties.getProperty("validacionCumplimiento.filePath");
            String prefixName = properties.getProperty("validacionCumplimiento.prefixName");
            String extension = properties.getProperty("validacionCumplimiento.extension");
            File file = new File(path+"/"+prefixName+"_"+processId+extension);
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            StringBuilder stringBuilderHeader = new StringBuilder("");
            stringBuilderHeader.append("DD_ICE_ICCOENP1.C_IDC_ICDOENN1")
                .append("|").append("DD_ICE_ICCOENP1.C_ICE_ICEP1")
                .append("|").append("DD_ICE_ICCOENP1.C_IDE_EVSITGA1")
                .append("|").append("DD_ICE_ICCOENP1.C_IDE_EISCTPA1")
                .append("|").append("DD_COB_COUBMLP1.C_COB_CUMPLIM1")
                .append("|").append("DD_COB_COUBMLP1.I_COB_IPMAPGO1")
                .append("|").append("DD_COB_COUBMLP1.F_OBL_FPERCEH1")
                .append("|").append("DD_COB_COUBMLP1.C_COB_TDIEPCO1");
            writer.write(stringBuilderHeader.toString());
            writer.newLine();
            stringBuilderHeader = new StringBuilder("");
            
            stringBuilderHeader.append("BOID")
            .append("|").append("CLAVEICEP")
            .append("|").append("ESTADOVIGILABLE")
            .append("|").append("ESTADOICEP")
            .append("|").append("IDENTIFICADORCUMPLIMIENTO")
            .append("|").append("IMPORTEPAGAR")
            .append("|").append("FECHAPRESENTACION")
            .append("|").append("TIPODECLARACION");
            writer.write(stringBuilderHeader.toString());
            writer.newLine();
            for(HistoricoCumplimiento historicoCumplimiento:historicosCumplimiento){
                StringBuilder stringBuilder = new StringBuilder("");
                stringBuilder.append(historicoCumplimiento.getbOID())
                    .append("|").append(historicoCumplimiento.getClaveICEP())
                    .append("|").append(historicoCumplimiento.getEstadoVigilable())
                    .append("|").append(historicoCumplimiento.getEstadoIcep())
                    .append("|").append(historicoCumplimiento.getIdentificadorCumplimiento())
                    .append("|").append(historicoCumplimiento.getImportePagar())
                    .append("|").append(historicoCumplimiento.getFechaPresentacion() == null?null:Utilerias.formatearFechaDDMMAAAA(historicoCumplimiento.getFechaPresentacion()))
                    .append("|").append(historicoCumplimiento.getTipoDeclaracion());
                
                if(historicoCumplimiento.getIdentificadorCumplimiento() == null){
                    stringBuilder.append("- @Identificador de cumplimiento no existe");
                }
                if(historicoCumplimiento.getFechaPresentacion() == null){
                    stringBuilder.append("- @Fecha de cumplimiento no existe");
                }
                if(historicoCumplimiento.getTipoDeclaracion() == null){
                    stringBuilder.append("- @Tipo de declaración no existe");
                }
                writer.write(stringBuilder.toString());
                writer.newLine();
                
            }
            writer.close();
            ParametrosSgtDTO parametro = parametroSgtDAO.obtenerParametroSgtPorIdParametro(ParametroSgtEnum.AMBIENTE.getValor());
            
            List<EmailReporteProceso> emails = emailReporteProcesoDAO.todosLosEmailReporteProceso(true);
            StringBuilder sEmails = new StringBuilder("");
            for(EmailReporteProceso emailReporteProceso:emails){
                sEmails.append(emailReporteProceso.getCorreoElectronico()).append(",");
                if(emailReporteProceso.getCorreoElectronicoAlterno()!=null){
                    sEmails.append(emailReporteProceso.getCorreoElectronicoAlterno()).append(",");    
                }
            }
            String[] destinatarios  = sEmails.toString().split(",");
            String asunto = properties.getProperty("validacionCumplimiento.asunto");
            String mensaje = properties.getProperty("validacionCumplimiento.mensaje");
            mailService.enviarCorreoPara(destinatarios, "Ambiente:"+parametro.getValor()+" - "+asunto+" - "+vigilancia, mensaje, file);
            
        } catch (FileNotFoundException e) {
            logger.error(e.toString());    
        } catch (MessagingException e) {
            logger.error(e.toString());    
        } catch(MailSendException e){
            logger.error(e.toString());    
        }
    }

}
