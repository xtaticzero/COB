package mx.gob.sat.siat.cob.seguimiento.dao.catalogos.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.EjercicioFiscal;
import org.springframework.jdbc.core.RowMapper;

public class EjercicioFiscalMapper implements RowMapper<EjercicioFiscal>{
    /**
     *
     */
    public EjercicioFiscalMapper() {
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
    public EjercicioFiscal mapRow(ResultSet rs, int rowNum) throws SQLException {
        EjercicioFiscal ejercicioFiscal = new EjercicioFiscal();
                
        ejercicioFiscal.setIdEjercicioFiscal(rs.getLong("IDEJERCICIOFISCAL"));
        ejercicioFiscal.setNombre(rs.getString("NOMBRE"));
        ejercicioFiscal.setDescripcion(rs.getString("DESCRIPCION"));
        ejercicioFiscal.setFechaInicio(rs.getDate("FECHAINICIO"));
        ejercicioFiscal.setFechaFin(rs.getDate("FECHAFIN"));
        ejercicioFiscal.setOrden(rs.getLong("ORDEN"));
        if (rs.getDate("FECHAFIN")==null){
            ejercicioFiscal.setFechaFinStr("1");
        }else {
            ejercicioFiscal.setFechaFinStr("0");
        }
        
        return ejercicioFiscal;
    }
}
