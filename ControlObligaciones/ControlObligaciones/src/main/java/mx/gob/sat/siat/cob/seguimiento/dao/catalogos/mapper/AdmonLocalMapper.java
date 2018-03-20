package mx.gob.sat.siat.cob.seguimiento.dao.catalogos.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.AdmonLocal;
import org.springframework.jdbc.core.RowMapper;

public class AdmonLocalMapper implements RowMapper<AdmonLocal>{
    
    public AdmonLocalMapper() {
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
    public AdmonLocal mapRow(ResultSet rs, int rowNum) throws SQLException {
        AdmonLocal admonLocal = new AdmonLocal();
                
        admonLocal.setIdAdmonLocal(rs.getString("IDADMONLOCAL"));
        admonLocal.setNombre(rs.getString("NOMBRE"));
        admonLocal.setDescripcion(rs.getString("DESCRIPCION"));
        admonLocal.setFechaInicio(rs.getDate("FECHAINICIO"));
        admonLocal.setFechaFin(rs.getDate("FECHAFIN"));
        admonLocal.setOrden(rs.getLong("ORDEN"));
        if (rs.getDate("FECHAFIN")==null){
            admonLocal.setFechaFinStr("1");
            admonLocal.setSituacion("Activo");
        }else {
            admonLocal.setFechaFinStr("0");
            admonLocal.setSituacion("Inactivo");
        }
        
        return admonLocal;
    }
}
