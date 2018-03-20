package mx.gob.sat.siat.cob.seguimiento.dao.catalogos.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Combo;
import org.springframework.jdbc.core.RowMapper;


public class ComboMapper implements RowMapper<Combo> {
    /**
     *
     */
    public ComboMapper() {
        super();
    }

    /**
     *
     * @param resultSet
     * @param i
     * @return
     * @throws SQLException
     */
    @Override
    public Combo mapRow(ResultSet resultSet, int i) throws SQLException {
        Combo cb = new Combo();
        
        cb.setIdCombo(resultSet.getLong("ID"));
        int numCol = resultSet.getMetaData().getColumnCount();
        if(numCol > 1){
            cb.setDescripcion(resultSet.getString("DESCRIPCION"));
        }
         
        
    return cb; 
    }
        
}
