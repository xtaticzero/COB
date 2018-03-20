package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DetalleObliga;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DetalleObligaPeriodo;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DetallePeriodos;
import org.springframework.jdbc.core.RowMapper;

public class DetalleObligaPeriodosMapper implements RowMapper<DetalleObligaPeriodo> {

    /**
     *
     * @param resultSet
     * @param i
     * @return
     * @throws SQLException
     */
    @Override
    public DetalleObligaPeriodo mapRow(ResultSet resultSet, int i) throws SQLException {
        DetalleObligaPeriodo obligaPeriodo = new DetalleObligaPeriodo();

        DetalleObliga detalleObliga = new DetalleObliga();
        DetallePeriodos detallePeriodo = new DetallePeriodos();

        detalleObliga.setDescripcionObligacion(resultSet.getString("DESCRIPCIONOBLIGACION"));
        detalleObliga.setDescripcionPeriodo(resultSet.getString("DESCRIPCIONPERIODO"));
        detalleObliga.setEjercicio(resultSet.getString("EJERCICIO"));
        detalleObliga.setFechaVencimiento(resultSet.getString("FECHAVENCIMIENTO"));
        detalleObliga.setFundamentoLegal(resultSet.getString("FUNDAMENTOLEGAL"));
        detalleObliga.setNumeroControl(resultSet.getString("NUMEROCONTROL"));

        detallePeriodo.setNumeroControl(resultSet.getString("NUMEROCONTROL"));
        detallePeriodo.setDescripcionPeriodo(resultSet.getString("DESCRIPCIONPERIODO"));
        detallePeriodo.setEjercicio(resultSet.getString("EJERCICIO"));
        detallePeriodo.setConceptoImpuesto(resultSet.getString("CONCEPTOIMPUESTO"));

        obligaPeriodo.setDetalleObliga(detalleObliga);
        obligaPeriodo.setDetallePeriodo(detallePeriodo);
        return obligaPeriodo;
    }
}
