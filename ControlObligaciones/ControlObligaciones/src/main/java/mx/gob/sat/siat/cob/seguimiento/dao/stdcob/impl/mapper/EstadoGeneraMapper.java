package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.EstadoGenera;
import org.springframework.jdbc.core.RowMapper;


public class EstadoGeneraMapper implements RowMapper <EstadoGenera>{

    /**
     *
     * @param resultSet
     * @param i
     * @return
     * @throws SQLException
     */
    @Override
    public EstadoGenera mapRow(ResultSet resultSet, int i) throws SQLException {
            EstadoGenera edoGenera = new EstadoGenera();
            edoGenera.setIdEstadoGeneracion(resultSet.getInt("IDESTADOGENERACION"));
            edoGenera.setNombre(resultSet.getString("NOMBRE"));
            edoGenera.setDescripcion(resultSet.getString("DESCRIPCION"));
            edoGenera.setFechaInicio(resultSet.getDate("FECHAINICIO"));
            edoGenera.setFechaFin(resultSet.getDate("FECHAFIN"));
            edoGenera.setOrden(resultSet.getInt("ORDEN"));
            return edoGenera;
    }
}