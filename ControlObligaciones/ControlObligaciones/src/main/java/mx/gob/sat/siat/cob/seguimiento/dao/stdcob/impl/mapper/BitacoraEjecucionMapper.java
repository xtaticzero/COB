package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.BitacoraEjecucionDTO;
import mx.gob.sat.siat.cob.seguimiento.util.Utilerias;

import org.springframework.jdbc.core.RowMapper;


public class BitacoraEjecucionMapper implements RowMapper {
    public BitacoraEjecucionMapper() {
        super();
    }

    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        List<BitacoraEjecucionDTO> bitacora = new ArrayList<BitacoraEjecucionDTO>();
        do{
            String duracion = Utilerias.obtenerDiferenciaFechas(resultSet.getDate("FECHAINICIOINTENTO"), resultSet.getDate("FECHAFININTENTO")==null?new Date():resultSet.getDate("FECHAFININTENTO") );
            BitacoraEjecucionDTO dto = new BitacoraEjecucionDTO();
            dto.setIdEjecucion(resultSet.getInt("IDEJECUCION"));
            dto.setIntento(resultSet.getInt("INTENTO"));
            dto.setInicio(resultSet.getTimestamp("FECHAINICIOINTENTO"));
            dto.setFin(resultSet.getTimestamp("FECHAFININTENTO"));
            dto.setDuracion(duracion);
            dto.setEstado(resultSet.getInt("IDESTADOJOBINTENTO"));
            dto.setObservaciones(resultSet.getString("OBSERVACIONESINTENTO"));
            bitacora.add(dto);
        }while(resultSet.next());
        return bitacora;
    }
}
