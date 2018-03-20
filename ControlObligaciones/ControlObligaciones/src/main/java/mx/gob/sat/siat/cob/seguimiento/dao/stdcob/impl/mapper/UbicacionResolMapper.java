package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.UbicacionResolDTO;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author christian.ventura
 */
public class UbicacionResolMapper implements RowMapper<UbicacionResolDTO> {
    
    /**
     * 
     * @param resultSet
     * @param i
     * @return
     * @throws SQLException 
     */
    @Override
    public UbicacionResolDTO mapRow(ResultSet resultSet, int i) throws SQLException {
        UbicacionResolDTO ubicacion = new UbicacionResolDTO();
        ubicacion.setClvALR(resultSet.getString("ALR"));
        ubicacion.setClvCRH(resultSet.getString("CLAVECRH"));
        ubicacion.setBoid(resultSet.getString("BOID"));
        ubicacion.setNumeroControl(resultSet.getString("NUMEROCONTROL"));
        return ubicacion;
    }
    
}
