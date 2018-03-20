package mx.gob.sat.siat.cob.seguimiento.dao.compartidos.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.compartidos.CatalogoBase;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author root
 */
public class CatalogoMapper implements RowMapper<CatalogoBase> {

    @Override
    public CatalogoBase mapRow(ResultSet resultSet, int i) throws SQLException {
        CatalogoBase cb = new CatalogoBase();
        cb.setId(resultSet.getInt("ID"));
        cb.setNombre(resultSet.getString("NOMBRE"));
        return cb;
    }
}
