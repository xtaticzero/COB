package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.RfcNombreDTO;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author christian.ventura
 */
public class RfcNombreMapper implements RowMapper<RfcNombreDTO> {

    /**
     *
     * @param rs
     * @param i
     * @return
     * @throws SQLException
     */
    @Override
    public RfcNombreDTO mapRow(ResultSet rs, int i) throws SQLException {
        RfcNombreDTO dato=new RfcNombreDTO();
        dato.setIdFederativa(rs.getInt("IDENTIDADFEDERATIVA"));
        dato.setIdAdmonLocal(rs.getString("IDADMONLOCAL"));
        dato.setBoid(rs.getString("BOID"));
        dato.setNumeroControl(rs.getString("NUMEROCONTROL"));
        dato.setFecha(rs.getString("FECHA"));
        dato.setRfc(rs.getString("RFC"));
        dato.setNombre(rs.getString("NOMBRE"));
        dato.setObligaciones(rs.getString("OBLIGACIONES"));
        return dato;
    }

}
