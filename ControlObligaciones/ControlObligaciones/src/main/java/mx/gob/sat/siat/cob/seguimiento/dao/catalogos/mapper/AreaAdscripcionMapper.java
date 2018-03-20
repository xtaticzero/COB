package mx.gob.sat.siat.cob.seguimiento.dao.catalogos.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.AreaAdscripcion;
import org.springframework.jdbc.core.RowMapper;

public class AreaAdscripcionMapper implements RowMapper<AreaAdscripcion>{
    
    public AreaAdscripcionMapper() {
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
    public AreaAdscripcion mapRow(ResultSet rs, int rowNum) throws SQLException {
        AreaAdscripcion areaAdscripcion = new AreaAdscripcion();
                
        areaAdscripcion.setIdAreaAdscripcion(rs.getLong("IDAREAADSCRIPCION"));
        areaAdscripcion.setNombre(rs.getString("NOMBRE"));
        areaAdscripcion.setDescripcion(rs.getString("DESCRIPCION"));
        areaAdscripcion.setFechaInicio(rs.getDate("FECHAINICIO"));
        areaAdscripcion.setFechaFin(rs.getDate("FECHAFIN"));
        areaAdscripcion.setOrden(rs.getLong("ORDEN"));
        if (rs.getDate("FECHAFIN")==null){
            areaAdscripcion.setFechaFinStr("1");
            areaAdscripcion.setSituacion("Activo");
        }else {
            areaAdscripcion.setFechaFinStr("0");
            areaAdscripcion.setSituacion("Inactivo");
        }
        
        return areaAdscripcion;
    }
}
