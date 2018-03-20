package mx.gob.sat.siat.cob.seguimiento.dao.catalogos.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.EmailReporteProceso;
import org.springframework.jdbc.core.RowMapper;

public class EmailReporteProcesoMapper implements RowMapper<EmailReporteProceso>{
    
    /**
     *
     * @param rs
     * @param rowNum
     * @return
     * @throws SQLException
     */
    @Override
    public EmailReporteProceso mapRow(ResultSet rs, int rowNum) throws SQLException {
        EmailReporteProceso emailReporteProceso = new EmailReporteProceso();
                
        emailReporteProceso.setIdEmailReporteProceso(rs.getLong("ID"));
        emailReporteProceso.setNombreCompleto(rs.getString("NOMBRECOMPLETO"));
        emailReporteProceso.setCorreoElectronico(rs.getString("CORREOELECTRONICO"));
        emailReporteProceso.setCorreoElectronicoAlterno(rs.getString("CORREOELECTRONICOALTERNO"));
        emailReporteProceso.setFechaInicio(rs.getDate("FECHAINICIO"));
        emailReporteProceso.setFechaFin(rs.getDate("FECHAFIN"));
        
        return emailReporteProceso;
    }
}
