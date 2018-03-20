package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ConsultasRenuentes;

import org.springframework.jdbc.core.RowMapper;


public class ConsultasRenuentesMapper implements RowMapper {
    /**
     *
     */
    public ConsultasRenuentesMapper() {
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
        ConsultasRenuentes cb = new ConsultasRenuentes();
        
        cb.setNumControl(resultSet.getString("ID"));
        cb.setDescripcion(resultSet.getString("DESCRIPCION"));
        cb.setRfc(resultSet.getString("RFC"));
        cb.setTipoDoc(resultSet.getString("TIPODOC"));
        cb.setEjercicio(resultSet.getString("EJERCICIO"));
        cb.setDescripcionPeriodo(resultSet.getString("DESCRIPCIONPERIODO"));
        cb.setNombre(resultSet.getString("NOMBRE"));
               
        return cb;
    }
}
