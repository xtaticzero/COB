package mx.gob.sat.siat.cob.seguimiento.dao.catalogos.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Descripcion;
import org.springframework.jdbc.core.RowMapper;

public class DescripcionMapper implements RowMapper<Descripcion>{
    
    public DescripcionMapper() {
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
    public Descripcion mapRow(ResultSet rs, int rowNum) throws SQLException {
        Descripcion descripcion = new Descripcion();
                
        descripcion.setIdDescripcion(rs.getLong("IDDESCRIPCION"));
        descripcion.setDescripcion(rs.getString("DESCRIPCION"));
        descripcion.setFechaInicio(rs.getDate("FECHAINICIO"));
        descripcion.setFechaFin(rs.getDate("FECHAFIN"));
        descripcion.setOrden(rs.getLong("ORDEN"));
        
        return descripcion;
    }
    
}
