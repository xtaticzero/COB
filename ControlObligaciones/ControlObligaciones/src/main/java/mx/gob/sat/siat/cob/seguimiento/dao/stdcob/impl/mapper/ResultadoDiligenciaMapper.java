package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;

import java.sql.SQLException;

import mx.gob.sat.siat.cob.seguimiento.dto.compartidos.CatalogoBase;

import org.springframework.jdbc.core.RowMapper;

public class ResultadoDiligenciaMapper implements RowMapper<CatalogoBase>{
    public ResultadoDiligenciaMapper() {
        super();
    }

    @Override
    public CatalogoBase mapRow(ResultSet resultSet, int i) throws SQLException {
        CatalogoBase pd = new CatalogoBase();
        pd.setId(resultSet.getInt("idestadodocto"));
        pd.setNombre(resultSet.getString("nombre"));
        return pd;
    }
}
