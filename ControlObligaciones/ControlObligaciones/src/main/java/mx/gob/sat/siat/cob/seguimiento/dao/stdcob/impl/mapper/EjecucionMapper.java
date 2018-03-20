package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;


import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.EjecucionDTO;

import org.springframework.jdbc.core.RowMapper;


public class EjecucionMapper implements RowMapper<EjecucionDTO> {
   
    @Override
    public EjecucionDTO mapRow(ResultSet resultSet, int i) throws SQLException {
        EjecucionDTO dto = new EjecucionDTO();
        dto.setId(resultSet.getInt("IDEJECUCION"));
        dto.setIdProceso(resultSet.getInt("IDPROCESO"));
        dto.setIntento(resultSet.getInt("INTENTO"));
        dto.setInicio(resultSet.getTimestamp("FECHAINICIO"));
        dto.setFin(resultSet.getTimestamp("FECHAFIN"));
        dto.setEstado(resultSet.getInt("IDESTADOJOB"));
        dto.setObservaciones(resultSet.getString("OBSERVACIONES"));
        return dto;
    }
}
