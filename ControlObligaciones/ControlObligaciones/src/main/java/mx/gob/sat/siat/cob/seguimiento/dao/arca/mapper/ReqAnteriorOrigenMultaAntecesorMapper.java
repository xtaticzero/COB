package mx.gob.sat.siat.cob.seguimiento.dao.arca.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.RequerimientosAnteriores;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author root
 */
public class ReqAnteriorOrigenMultaAntecesorMapper implements RowMapper<RequerimientosAnteriores> {

    /**
     *
     */
    public ReqAnteriorOrigenMultaAntecesorMapper() {
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
    public RequerimientosAnteriores mapRow(ResultSet resultSet, int i) throws SQLException {
        RequerimientosAnteriores reqsAnteriores = new RequerimientosAnteriores();
        reqsAnteriores.setIdDocumento(resultSet.getString("NumeroControl"));
        reqsAnteriores.setIdDocumentoPadre(resultSet.getString("NumeroControlPadre"));
        reqsAnteriores.setFechaNotificacion(resultSet.getString("FechaNotificacion"));
        reqsAnteriores.setTipoDocumento(resultSet.getString("Nombre"));
        return reqsAnteriores;
    }

}
