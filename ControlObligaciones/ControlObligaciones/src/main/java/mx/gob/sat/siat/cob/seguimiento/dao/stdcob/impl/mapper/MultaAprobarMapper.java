/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.MultaAprobar;
import mx.gob.sat.siat.cob.seguimiento.util.Utilerias;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author root
 */
public class MultaAprobarMapper implements RowMapper {

    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException { 
        MultaAprobar multaAprobar = new MultaAprobar();
        multaAprobar.setNumeroResolucion(rs.getString("numeroResolucion"));
        multaAprobar.setRfc(rs.getString("rfc"));
        multaAprobar.setNumeroControl(rs.getString("numerocontrol"));
        if (Utilerias.existeColumna(rs, "sumaMonto")) {
                multaAprobar.setMonto(rs.getLong("sumaMonto"));
            }
        
        return multaAprobar;
    }    
}
