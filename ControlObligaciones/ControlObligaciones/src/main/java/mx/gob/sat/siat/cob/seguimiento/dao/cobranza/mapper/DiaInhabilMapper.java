package mx.gob.sat.siat.cob.seguimiento.dao.cobranza.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.DiaInhabil;
import org.springframework.jdbc.core.RowMapper;

public class DiaInhabilMapper implements RowMapper<DiaInhabil> {
    /**
     *
     */
    public DiaInhabilMapper() {
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
    public DiaInhabil mapRow(ResultSet rs, int rowNum) throws SQLException{
        DiaInhabil diaInhabil = new DiaInhabil();
        
        diaInhabil.setFecha(rs.getDate("FECHA"));
        diaInhabil.setMotivo(rs.getString("MOTIVO"));
                
        return diaInhabil;
    }
}
