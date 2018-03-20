package mx.gob.sat.siat.cob.seguimiento.dao.catalogos.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.MtvoCancelDoc;
import org.springframework.jdbc.core.RowMapper;

public class MtvoCancelDocMapper implements RowMapper<MtvoCancelDoc> {
    /**
     *
     */
    public MtvoCancelDocMapper() {
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
    public MtvoCancelDoc mapRow(ResultSet rs, int rowNum) throws SQLException {
        MtvoCancelDoc mtvoCancelDoc = new MtvoCancelDoc();
                
        mtvoCancelDoc.setIdMotivoCancelacion(rs.getLong("IDMOTIVOCANCELACION"));
        mtvoCancelDoc.setNombre(rs.getString("NOMBRE"));
        mtvoCancelDoc.setDescripcion(rs.getString("DESCRIPCION"));
        mtvoCancelDoc.setFechaInicio(rs.getDate("FECHAINICIO"));
        mtvoCancelDoc.setFechaFin(rs.getDate("FECHAFIN"));
        mtvoCancelDoc.setOrden(rs.getLong("ORDEN"));
        
        return mtvoCancelDoc;
    }
}