package mx.gob.sat.siat.cob.seguimiento.dao.catalogos.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Regimen;
import org.springframework.jdbc.core.RowMapper;

public class RegimenMapper implements RowMapper<Regimen> {
    /**
     *
     */
    public RegimenMapper() {
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
    public Regimen mapRow(ResultSet rs, int rowNum) throws SQLException {
        Regimen regimen = new Regimen();
                
        regimen.setIdRegimen(rs.getLong("IDREGIMEN"));
        regimen.setNombre(rs.getString("NOMBRE"));
        regimen.setDescripcion(rs.getString("DESCRIPCION"));
        regimen.setFechaInicio(rs.getDate("FECHAINICIO"));
        regimen.setFechaFin(rs.getDate("FECHAFIN"));
        regimen.setOrden(rs.getLong("ORDEN"));
        if (rs.getDate("FECHAFIN")==null){
            regimen.setFechaFinStr("1");
        }else {
            regimen.setFechaFinStr("0");
        }
        
        return regimen;
    }
}
