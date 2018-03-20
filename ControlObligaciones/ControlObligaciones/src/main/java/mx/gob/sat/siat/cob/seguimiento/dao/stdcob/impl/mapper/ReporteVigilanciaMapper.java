package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ReporteVigilancia;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author root
 */
public class ReporteVigilanciaMapper implements RowMapper<ReporteVigilancia>{

    @Override
    public ReporteVigilancia mapRow(ResultSet rs, int i) throws SQLException {
        ReporteVigilancia v=new ReporteVigilancia();
        v.setIdVigilancia(rs.getString("numeroVigilancia"));
        v.setTipoDocumento(rs.getString("tipoDocumento"));
        v.setTipoVigilancia(rs.getString("descripcionVigilancia"));
        v.setPeriodoRequerido(rs.getString("periodosRequeridos"));
        v.setEjercicioRequerido(rs.getString("ejerciciosRequeridos"));
        v.setFechaLiberacion(rs.getDate("fechaLiberacionVigilancia"));
        v.setIdTipoMedio(rs.getInt("idTipoMedio"));
        v.setTipoMedio(rs.getString("tipoMedio"));
        return v;
    }

    
    
}
