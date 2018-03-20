/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.CadenaOriginal;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author root
 */
public class CadenaOriginalMapper implements RowMapper<CadenaOriginal> {

    @Override
    public CadenaOriginal mapRow(ResultSet rs, int i) throws SQLException {
        CadenaOriginal cadenaOriginal= new CadenaOriginal();
        cadenaOriginal.setCadenaOriginal(rs.getString("cadenaOriginal"));
        return cadenaOriginal;
    }
    
}
