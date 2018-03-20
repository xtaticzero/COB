/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.MultaAprobarReporteDTO;
import mx.gob.sat.siat.cob.seguimiento.util.Utilerias;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author root
 */
public class MultaAprobarReporteMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException { 
        MultaAprobarReporteDTO multaAprobar = new MultaAprobarReporteDTO();
        multaAprobar.setNumeroResolucion(rs.getString("numeroResolucion"));
        multaAprobar.setRfc(rs.getString("rfc"));
        multaAprobar.setCrh(rs.getString("crhActual"));
        if (Utilerias.existeColumna(rs, "destinoLocal")) {
                multaAprobar.setDestino(rs.getString("destinoLocal"));
            }
        return multaAprobar;
    }    
}
