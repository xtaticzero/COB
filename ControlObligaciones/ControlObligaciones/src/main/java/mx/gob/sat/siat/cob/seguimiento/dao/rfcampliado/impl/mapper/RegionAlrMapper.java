package mx.gob.sat.siat.cob.seguimiento.dao.rfcampliado.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.ComboStr;

import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author root
 */
public class RegionAlrMapper implements RowMapper {
    /**
     *
     */
    public RegionAlrMapper() {
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
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        ComboStr cb = new ComboStr();
        
        cb.setIdCombo(resultSet.getString("IDADMONLOCAL"));
        cb.setDescripcion(resultSet.getString("DESCRIPCION"));
               
        return cb;
    }
}
