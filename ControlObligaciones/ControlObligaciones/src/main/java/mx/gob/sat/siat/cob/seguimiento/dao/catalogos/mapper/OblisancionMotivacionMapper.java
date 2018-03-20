package mx.gob.sat.siat.cob.seguimiento.dao.catalogos.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.cob.seguimiento.dto.compartidos.CatalogoBase;

import org.springframework.jdbc.core.RowMapper;

public class OblisancionMotivacionMapper implements RowMapper<CatalogoBase> {

    /**
     *
     */
    public OblisancionMotivacionMapper() {
        super();
    }

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
        catalogoBase.setId(rs.getInt("IDOBLIGACION"));
        catalogoBase.setIdString(rs.getString("CONSTANTERESOLMOTIVO"));
        catalogoBase.setNombre(rs.getString("MOTIVACION"));
        return catalogoBase;
    }
}
