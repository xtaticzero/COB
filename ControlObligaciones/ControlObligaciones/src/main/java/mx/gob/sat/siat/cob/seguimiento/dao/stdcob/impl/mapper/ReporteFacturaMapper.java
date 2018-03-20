package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DetalleReporteFacturaBean;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author christian.ventura
 */
public class ReporteFacturaMapper implements RowMapper<DetalleReporteFacturaBean> {

    @Override
    public DetalleReporteFacturaBean mapRow(ResultSet rs, int i) throws SQLException {
        DetalleReporteFacturaBean dato=new DetalleReporteFacturaBean();
        dato.setRowNum(rs.getInt("ROWNUM"));
        dato.setNumeroControl(rs.getString("NUMEROCONTROL"));
        dato.setRfc(rs.getString("RFC"));
        dato.setCodigoPostal(rs.getString("CODIGOPOSTAL"));
        return dato;
    }
    
}
