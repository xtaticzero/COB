package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.AlscDTO;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author root
 */
public class AlscMapper implements RowMapper<AlscDTO>{
    /**
     *
     * @param rs
     * @param i
     * @return
     * @throws SQLException
     */
    @Override
    public AlscDTO mapRow(ResultSet rs, int i) throws SQLException {
        AlscDTO alsc = new  AlscDTO();
        alsc.setCvAlsc(rs.getInt("CV_ALSC"));
        alsc.setAlsc(rs.getString("ALSC"));
        alsc.setCantidadDocumentos(rs.getInt("CANTIDADDOCUMENTOS"));
        alsc.setCantidadImpresos(rs.getInt("CANTIDADIMPRESOS"));
        alsc.setFechaDeterminacion(rs.getDate("FECHADETERMINAEMISION"));
        alsc.setSituacionVigilancia(rs.getString("SITUACIONVIGILANCIA"));        
        return alsc;        
    }
    
}
