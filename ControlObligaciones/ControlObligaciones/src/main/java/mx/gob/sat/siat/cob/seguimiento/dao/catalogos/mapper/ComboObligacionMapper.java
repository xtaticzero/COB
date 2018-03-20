package mx.gob.sat.siat.cob.seguimiento.dao.catalogos.mapper;

import java.sql.ResultSet;

import java.sql.SQLException;

import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Combo;

import org.springframework.jdbc.core.RowMapper;

public class ComboObligacionMapper implements RowMapper<Combo> {
    /**
     *
     */
    public ComboObligacionMapper() {
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
    public Combo mapRow(ResultSet resultSet, int i) throws SQLException {
        Combo cb = new Combo();
        
        cb.setIdCombo(resultSet.getLong("ID"));
        cb.setDescripcion(resultSet.getString("DESCRIPCIONCORTA"));
        cb.setIdAux(resultSet.getString("DESCRIPCIONLARGA"));

    return cb; 
    }
        
}
