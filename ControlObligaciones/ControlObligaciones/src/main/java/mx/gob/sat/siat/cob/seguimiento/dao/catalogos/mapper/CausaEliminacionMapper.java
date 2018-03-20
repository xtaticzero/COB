package mx.gob.sat.siat.cob.seguimiento.dao.catalogos.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.CausaEliminacion;
import org.springframework.jdbc.core.RowMapper;


public class CausaEliminacionMapper implements RowMapper<CausaEliminacion>{
    /**
     *
     */
    public CausaEliminacionMapper() {
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
    public CausaEliminacion mapRow(ResultSet rs, int rowNum) throws SQLException {
          CausaEliminacion causaEliminacion = new CausaEliminacion();
          
           causaEliminacion.setIdObligacion(rs.getString("IDOBLIGACION"));
           causaEliminacion.setIdRegimen(rs.getInt("IDREGIMEN"));
           causaEliminacion.setConcepto(rs.getString("CONCEPTO"));
           causaEliminacion.setDecripcion(rs.getString("DESCRIPCION"));
           causaEliminacion.setFundamentoLegal(rs.getString("FUNDAMENTOLEGAL"));
           causaEliminacion.setFechaInicio(rs.getDate("FECHAINICIO"));
           causaEliminacion.setFechaFin(rs.getDate("FECHAFIN"));
           causaEliminacion.setOrden(rs.getLong("ORDEN"));
          return causaEliminacion;
       }
}
