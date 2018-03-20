package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DetalleConsultaVigilancia;
import org.springframework.jdbc.core.RowMapper;

public class DetalleConsultaVigilanciaMapper implements RowMapper {

    /**
     *
     * @param resultSet
     * @param i
     * @return
     * @throws SQLException
     */
    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        DetalleConsultaVigilancia vig = new DetalleConsultaVigilancia();
        vig.setRutaLocalArchivo(resultSet.getString("RUTA"));
        vig.setNombreArchivo(resultSet.getString("NOMBREARCHIVO"));
        return vig;
    }
}

