package mx.gob.sat.siat.cob.seguimiento.dao.compartidos.mapper;

import java.sql.ResultSet;

import java.sql.SQLException;

import mx.gob.sat.siat.cob.seguimiento.dto.compartidos.CatalogoBase;

import org.springframework.jdbc.core.RowMapper;

public class CatalogoDescripcionMapper implements RowMapper <CatalogoBase>{

    @Override
    public CatalogoBase mapRow(ResultSet resultSet, int i) throws SQLException {
        CatalogoBase pd = new CatalogoBase();
        pd.setId(resultSet.getInt("ID"));
        pd.setNombre(resultSet.getString("DESCRIPCION"));
        return pd;
    }
}
