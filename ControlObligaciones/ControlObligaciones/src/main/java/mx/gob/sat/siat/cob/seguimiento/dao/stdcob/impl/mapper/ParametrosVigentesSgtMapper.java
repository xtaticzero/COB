package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;

import java.sql.SQLException;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ParametrosSgtDTO;

import org.springframework.jdbc.core.RowMapper;

public class ParametrosVigentesSgtMapper implements RowMapper<ParametrosSgtDTO> {
    
    public ParametrosVigentesSgtMapper() {
        super();
    }
    
    @Override
    public ParametrosSgtDTO mapRow(ResultSet rs, int i) throws SQLException {
        ParametrosSgtDTO param = new ParametrosSgtDTO();
        
        param.setNombre(rs.getString("nombre"));
        param.setValor(rs.getString("valor"));        
        param.setIdParametro(rs.getInt("idparametro"));
        param.setTipoDato(rs.getString("tipoDato"));
        
        return param;
    }
}
