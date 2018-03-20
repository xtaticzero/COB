package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;


import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.IntentoDTO;

import org.springframework.jdbc.core.RowMapper;


public class IntentoMapper implements RowMapper<IntentoDTO> {
   
    @Override
    public IntentoDTO mapRow(ResultSet resultSet, int i) throws SQLException {
        
        IntentoDTO dto = new IntentoDTO();
        dto.setId(resultSet.getInt("IDINTENTOJOB"));
        dto.setIdEjecucion(resultSet.getInt("IDEJECUCION"));
        dto.setIntento(resultSet.getInt("INTENTO"));
        dto.setInicio(resultSet.getTimestamp("FECHAINICIO"));
        dto.setFin(resultSet.getTimestamp("FECHAFIN"));
        dto.setEstado(resultSet.getInt("IDESTADOJOB"));
        dto.setObservaciones(resultSet.getString("OBSERVACIONES"));
            
        return dto;
    }
}
