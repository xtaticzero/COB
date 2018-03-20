package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.AlscDTO;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author root
 */
public class AlscEFMapper implements RowMapper<AlscDTO>{
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
        alsc.setIndentidadFederativa(rs.getInt("IDENTIDADFEDERATIVA"));
        alsc.setDescripcion(rs.getString("DESCRIPCION"));
        alsc.setCantidadDocumentos(rs.getInt("CANTIDADDOCUMENTOS"));
        alsc.setTipoEnvio(rs.getString("TIPOENVIO"));
        alsc.setFechaDescarga(rs.getDate("FECHADESCARGA"));
        alsc.setSituacionVigilancia(rs.getString("SITUACIONVIGILANCIA"));        
        return alsc;        
    }
    
}
