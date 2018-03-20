package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DetalleObliga;
import org.springframework.jdbc.core.RowMapper;


public class DetalleObligaMapper implements RowMapper<DetalleObliga> {

    /**
     *
     * @param resultSet
     * @param rowNum
     * @return
     * @throws SQLException
     */
    @Override
    public DetalleObliga mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            DetalleObliga detalleObliga = new DetalleObliga();
            detalleObliga.setNumeroControl(resultSet.getString("NUMEROCONTROL"));
            detalleObliga.setDescripcionObligacion(resultSet.getString("DESCRIPCIONOBLIGACION"));
            detalleObliga.setFundamentoLegal(resultSet.getString("FUNDAMENTOLEGAL"));
            detalleObliga.setFechaVencimiento(resultSet.getString("FECHAVENCIMIENTO"));
            detalleObliga.setDescripcionPeriodo(resultSet.getString("DESCRIPCIONPERIODO"));
            detalleObliga.setEjercicio(resultSet.getString("EJERCICIO"));
            return detalleObliga;
    }
}
