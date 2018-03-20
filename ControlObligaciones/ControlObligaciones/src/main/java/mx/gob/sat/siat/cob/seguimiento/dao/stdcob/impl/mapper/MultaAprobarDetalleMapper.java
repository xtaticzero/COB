package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.MultaAprobarDetalle;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author root
 */
public class MultaAprobarDetalleMapper  implements RowMapper{

    /**
     * 
     * @param rs
     * @param i
     * @return
     * @throws SQLException 
     */
    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        MultaAprobarDetalle multaAprobarDetalle = new MultaAprobarDetalle();
        multaAprobarDetalle.setClaveObligacion(rs.getInt("IDOBLIGACION"));
        multaAprobarDetalle.setDescripcionObligacion(rs.getString("DESCRIPCION"));
        multaAprobarDetalle.setMonto(rs.getLong("monto"));
        multaAprobarDetalle.setPeriodo(rs.getString("PERIODO"));
        multaAprobarDetalle.setEjercicio(rs.getString("EJERCICIO"));
        return multaAprobarDetalle;
    }
    
}
