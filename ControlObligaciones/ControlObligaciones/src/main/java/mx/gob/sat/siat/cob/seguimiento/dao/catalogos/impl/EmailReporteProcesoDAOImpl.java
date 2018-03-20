package mx.gob.sat.siat.cob.seguimiento.dao.catalogos.impl;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.EmailReporteProcesoDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.mapper.EmailReporteProcesoMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.sql.EmailReporteProcesoSQL;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.EmailReporteProceso;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EmailReporteProcesoDAOImpl implements EmailReporteProcesoDAO {

    private static Logger logger = Logger.getLogger(EmailReporteProcesoDAOImpl.class);
    @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG)
    private JdbcTemplate template;
    
    /**
     *
     * @return
     */
    @Override
    public List<EmailReporteProceso> todosLosEmailReporteProceso(boolean isEmailEc) {
        List<EmailReporteProceso> listEmailReporteProceso;
        String query = "";
        if (isEmailEc) {
            query = EmailReporteProcesoSQL.OBTEN_TODOS_EMAILREPORTEPROCESO.replace("{1}", "IDEMAILREPORTEEC");
            query = query.replace("{2}", "sgtp_emailreportec");
        }else{
            query = EmailReporteProcesoSQL.OBTEN_TODOS_EMAILREPORTEPROCESO.replace("{1}", "IDEMAILPROCESODETENIDO");
            query = query.replace("{2}", "sgtp_emailprocsdet");
        }
        
        listEmailReporteProceso = template.query(query, new EmailReporteProcesoMapper());
        if (listEmailReporteProceso == null || listEmailReporteProceso.isEmpty()) {
            logger.info(EmailReporteProcesoSQL.OBTEN_TODOS_EMAILREPORTEPROCESO);
        }
        return listEmailReporteProceso;
    }

    /**
     *
     * @param emailReporteProceso
     *
     */
    @Override
    @Propagable
    public void agregaEmailReporteProceso(EmailReporteProceso emailReporteProceso, boolean isEmailEc) {
        
        String query = "";
        if (isEmailEc) {
            query = EmailReporteProcesoSQL.AGREGA_EMAILREPORTEPROCESO.replace("{1}", "sgtp_emailreportec");
            query = query.replace("{2}", "IDEMAILREPORTEEC");
            query = query.replace("{3}", "sgtq_emailreportec.nextval");
        }else{
            query = EmailReporteProcesoSQL.AGREGA_EMAILREPORTEPROCESO.replace("{1}", "sgtp_emailprocsdet");
            query = query.replace("{2}", "IDEMAILPROCESODETENIDO");
            query = query.replace("{3}", "sgtq_emailprocsdet.nextval");
        }
        int resultado = template.update(query, emailReporteProceso.getNombreCompleto(), emailReporteProceso.getCorreoElectronico(), emailReporteProceso.getCorreoElectronicoAlterno(), 
                emailReporteProceso.getFechaInicio(), emailReporteProceso.getFechaFin());
        if (resultado == -1) {
            logger.info(EmailReporteProcesoSQL.AGREGA_EMAILREPORTEPROCESO);
        }

    }

    /**
     *
     * @param emailReporteProceso
     *
     */
    @Override
    @Propagable
    public void editaEmailReporteProceso(EmailReporteProceso emailReporteProceso, boolean isEmailEc) {
         String query = "";
        if (isEmailEc) {
            query = EmailReporteProcesoSQL.EDITA_EMAILREPORTEPROCESO.replace("{1}", "sgtp_emailreportec");
            query = query.replace("{2}", "IDEMAILREPORTEEC");         
        }else{
            query = EmailReporteProcesoSQL.EDITA_EMAILREPORTEPROCESO.replace("{1}", "sgtp_emailprocsdet");
            query = query.replace("{2}", "IDEMAILPROCESODETENIDO");     
        }
        int resultado = template.update(query, emailReporteProceso.getNombreCompleto(),
                emailReporteProceso.getCorreoElectronico(), emailReporteProceso.getCorreoElectronicoAlterno(), emailReporteProceso.getFechaFin() ,
                emailReporteProceso.getIdEmailReporteProceso());
        if (resultado == -1) {
            logger.info(EmailReporteProcesoSQL.EDITA_EMAILREPORTEPROCESO);
        }

    }

    /**
     *
     * @param emailReporteProceso
     * @return
     *
     */
    @Override
    @Propagable
    public Integer eliminaEmailReporteProceso(EmailReporteProceso emailReporteProceso, boolean isEmailEc) {
        Integer reg;
         String query = "";
        if (isEmailEc) {
            query = EmailReporteProcesoSQL.ELIMINA_EMAILREPORTEPROCESO.replace("{1}", "sgtp_emailreportec");
            query = query.replace("{2}", "IDEMAILREPORTEEC");         
        }else{
            query = EmailReporteProcesoSQL.ELIMINA_EMAILREPORTEPROCESO.replace("{1}", "sgtp_emailprocsdet");
            query = query.replace("{2}", "IDEMAILPROCESODETENIDO"); 
        }
        reg = template.update(query, emailReporteProceso.getFechaFin(),
                emailReporteProceso.getIdEmailReporteProceso());
        if (reg == -1) {
            logger.info(EmailReporteProcesoSQL.ELIMINA_EMAILREPORTEPROCESO);
        }
        return reg;

    }


}
