package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.NumeroControlDTO;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author christian.ventura
 */
public class NumeroResolucionMapper implements RowMapper<NumeroControlDTO> {

    /**
     * 
     * @param rs
     * @param i
     * @return
     * @throws SQLException 
     */
    @Override
    public NumeroControlDTO mapRow(ResultSet rs, int i) throws SQLException {
        NumeroControlDTO parametros=new NumeroControlDTO();
        parametros.setNumeroControl(rs.getString("NUMEROCONTROL"));
        parametros.setNumeroResolucion(rs.getString("NUMERORESOLUCION"));
        return parametros;
    }
    
}
