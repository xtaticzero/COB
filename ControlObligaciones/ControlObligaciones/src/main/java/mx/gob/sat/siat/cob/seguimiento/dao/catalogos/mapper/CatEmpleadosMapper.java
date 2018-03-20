package mx.gob.sat.siat.cob.seguimiento.dao.catalogos.mapper;

import java.sql.ResultSet;

import java.sql.SQLException;

import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.ComboStr;

import org.springframework.jdbc.core.RowMapper;

public class CatEmpleadosMapper implements RowMapper{
    public CatEmpleadosMapper() {
        super();
    }

    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
            ComboStr cb = new ComboStr();
            StringBuilder sb=new StringBuilder();
            sb.append(resultSet.getString("ID")).append("-").append(resultSet.getString("DESCRIPCION"));
            cb.setIdCombo(resultSet.getString("ID"));
            cb.setDescripcion(sb.toString());
                   
            return cb;
        }
}
