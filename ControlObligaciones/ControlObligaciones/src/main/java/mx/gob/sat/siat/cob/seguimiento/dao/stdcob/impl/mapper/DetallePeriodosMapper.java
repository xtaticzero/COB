package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DetallePeriodos;
import org.springframework.jdbc.core.RowMapper;


public class DetallePeriodosMapper implements RowMapper<DetallePeriodos> {

    /**
     *
     * @param resultSet
     * @param i
     * @return
     * @throws SQLException
     */
    @Override
    public DetallePeriodos mapRow(ResultSet resultSet, int i) throws SQLException {
            DetallePeriodos detallePeriodo = new DetallePeriodos();

            detallePeriodo.setNumeroControl(resultSet.getString("NUMEROCONTROL"));
            detallePeriodo.setDescripcionPeriodo(resultSet.getString("DESCRIPCIONPERIODO"));
            detallePeriodo.setEjercicio(resultSet.getString("EJERCICIO"));
            detallePeriodo.setConceptoImpuesto(resultSet.getString("CONCEPTOIMPUESTO"));
            return detallePeriodo;
    }
}
