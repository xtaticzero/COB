package mx.gob.sat.siat.cob.seguimiento.dao.arca.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.SeguimientoARCA;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author root
 */
public class RetroArcaMapper implements RowMapper<SeguimientoARCA>{

    /**
     *
     * @param resultSet
     * @param i
     * @return
     * @throws SQLException
     */
    @Override
    public SeguimientoARCA mapRow(ResultSet resultSet, int i) throws SQLException {
        SeguimientoARCA resultado = new SeguimientoARCA();
        resultado.setId(resultSet.getInt("Id"));
        resultado.setFechaRegistro(resultSet.getDate("fdFechaDeRegistro"));
        resultado.setFechaResultado(resultSet.getDate("fdFechaDeResultado"));
        resultado.setIdDocumento(resultSet.getString("IdDocumento"));
        resultado.setIdEstadoDocumento(resultSet.getInt("IdEstadoDeDocumento"));
        return resultado;
    }
}
