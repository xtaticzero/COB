/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VigilanciasLogDTO;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author emmanuel
 */
public class VigilanciasLogMapper implements RowMapper<VigilanciasLogDTO>{

    @Override
    public VigilanciasLogDTO mapRow(ResultSet rs, int i) throws SQLException {
        VigilanciasLogDTO vigilanciasLogDTO = new VigilanciasLogDTO();
        
        vigilanciasLogDTO.setIdVigilancia(rs.getInt("IDVIGILANCIA"));
        vigilanciasLogDTO.setIdAdmonLocal(rs.getString("IDADMONLOCAL"));
        vigilanciasLogDTO.setFechaRegistro(rs.getDate("FECHAREGISTRO"));
        vigilanciasLogDTO.setDescripcion(rs.getString("DESCRIPCION"));
        
        return vigilanciasLogDTO;
    }    
    
}
