package mx.gob.sat.siat.cob.seguimiento.dao.catalogos.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.compartidos.CatalogoBase;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Marco Murakami
 */
public class PeriodoMapper implements RowMapper<CatalogoBase>{
    
    /**
     *
     * @param rs
     * @param rowNum
     * @return
     * @throws SQLException
     */
    @Override
    public CatalogoBase mapRow(ResultSet rs, int rowNum) throws SQLException {
        CatalogoBase catalogoBase = new CatalogoBase();
        catalogoBase.setId(rs.getInt("IDPERIODO"));
        catalogoBase.setIdString(rs.getString("IDPERIODICIDAD"));
        catalogoBase.setNombre(rs.getString("DESCRIPCIONPERIODO"));
        return catalogoBase;
    }
    
}
