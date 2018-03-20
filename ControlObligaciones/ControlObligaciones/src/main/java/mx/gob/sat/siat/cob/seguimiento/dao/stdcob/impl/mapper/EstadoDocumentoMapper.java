package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.EstadoDocumento;
import org.springframework.jdbc.core.RowMapper;

public class EstadoDocumentoMapper implements RowMapper<EstadoDocumento> {

    /**
     *
     * @param resultSet
     * @param i
     * @return
     * @throws SQLException
     */
    @Override
    public EstadoDocumento mapRow(ResultSet resultSet, int i) throws SQLException {
        EstadoDocumento estadoDocumento = new EstadoDocumento();
        estadoDocumento.setNumeroControl(resultSet.getString("NUMEROCONTROL"));
        estadoDocumento.setIdEstadoDocto(resultSet.getInt("IDESTADODOCTO"));
        estadoDocumento.setNombreEstado(resultSet.getString("NOMBRE"));
        return estadoDocumento;
    }
}
