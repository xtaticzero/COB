/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.arca.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.MonitorArchivoArca;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Ulises
 */
public class MonitorArchivoArcaMapper implements RowMapper<MonitorArchivoArca> {
    
    @Override
    public MonitorArchivoArca mapRow(ResultSet rs, int i) throws SQLException {
        MonitorArchivoArca monitorArchivoArca = new MonitorArchivoArca();
        
        monitorArchivoArca.setIdVigilancia(rs.getLong("idVigilancia"));
        monitorArchivoArca.setIdAdmonLocal(rs.getString("idAdmonLocal"));
        monitorArchivoArca.setEsEnvioResolucion(rs.getInt("esEnvioResoluciones"));
        monitorArchivoArca.setCantidadDocumentos(rs.getInt("cantidadDocumentos"));
        monitorArchivoArca.setCantidadObligacionPeriodo(rs.getInt("cantidadObligacionPeriodo"));
        monitorArchivoArca.setCantidadOrigenMulta(rs.getInt("cantidadOrigenMulta"));
        monitorArchivoArca.setCantidadReqAnteriores(rs.getInt("cantidadReqsAnteriores"));
        monitorArchivoArca.setFechaEnvioArca(rs.getString("fechaEnvioArca"));
        
        return monitorArchivoArca;
    }
}
