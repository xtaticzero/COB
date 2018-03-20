package mx.gob.sat.siat.cob.seguimiento.dao.catalogos.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.ValorBooleano;
import org.springframework.jdbc.core.RowMapper;


public class ValorBooleanoMapper implements RowMapper<ValorBooleano>{
    /**
     *
     */
    public ValorBooleanoMapper() {
        super();
    }

    /**
     *
     * @param resultSet
     * @param i
     * @return
     * @throws SQLException
     */
    @Override
    public ValorBooleano mapRow(ResultSet resultSet, int i) throws SQLException {
        ValorBooleano valorBooleano = new ValorBooleano();
        valorBooleano.setIdValorBooleano(resultSet.getLong("IDVALORBOOLEAN"));
        valorBooleano.setNombre(resultSet.getString("NOMBRE"));
        valorBooleano.setDescripcion(resultSet.getString("DESCRIPCION"));
        valorBooleano.setFechaInicio(resultSet.getDate("FECHAINICIO"));
        valorBooleano.setFechaFin(resultSet.getDate("FECHAFIN"));
        valorBooleano.setOrden(resultSet.getLong("ORDEN"));
        return valorBooleano;
    }
}
