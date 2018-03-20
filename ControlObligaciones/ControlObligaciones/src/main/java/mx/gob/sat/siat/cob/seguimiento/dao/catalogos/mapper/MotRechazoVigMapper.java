package mx.gob.sat.siat.cob.seguimiento.dao.catalogos.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.MotRechazoVig;
import org.springframework.jdbc.core.RowMapper;

public class MotRechazoVigMapper implements RowMapper<MotRechazoVig>{
    
    public MotRechazoVigMapper() {
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
    public MotRechazoVig mapRow(ResultSet rs, int rowNum) throws SQLException {
        MotRechazoVig motRechazoVig = new MotRechazoVig();
                
        motRechazoVig.setIdMotivoRechazoVig(rs.getLong("IDMOTIVORECHAZOVIG"));
        motRechazoVig.setNombre(rs.getString("NOMBRE"));
        motRechazoVig.setDescripcion(rs.getString("DESCRIPCION"));
        motRechazoVig.setFechaInicio(rs.getDate("FECHAINICIO"));
        motRechazoVig.setFechaFin(rs.getDate("FECHAFIN"));
        motRechazoVig.setOrden(rs.getLong("ORDEN"));
        
        return motRechazoVig;
    }
}
