package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;

import java.sql.SQLException;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ParametrosSgtDTO;

import org.springframework.jdbc.core.RowMapper;

public class ParametroSgtMapper implements RowMapper<ParametrosSgtDTO>{
    

    @Override
    public ParametrosSgtDTO mapRow(ResultSet rs, int i) throws SQLException {
        
        ParametrosSgtDTO param=new ParametrosSgtDTO();
        param.setIdParametro(rs.getInt("idparametro"));
        param.setValor(rs.getString("valor"));  
        param.setFechaInicio(rs.getDate("fechainicio"));
        param.setFechaFin(rs.getDate("fechafin"));
        
        return param;
    }
}
