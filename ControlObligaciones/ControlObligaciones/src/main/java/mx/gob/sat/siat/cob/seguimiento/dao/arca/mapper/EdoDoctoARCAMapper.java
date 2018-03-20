package mx.gob.sat.siat.cob.seguimiento.dao.arca.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author root
 */
public class EdoDoctoARCAMapper implements RowMapper<Integer> {
    /**
     *
     */
    public EdoDoctoARCAMapper() {
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
    public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
        Integer edoDoctoArca;
        edoDoctoArca = null;
        if(resultSet!=null){
            edoDoctoArca = resultSet.getInt("IdEstadoDeDocumento");
        }
        return edoDoctoArca;
    }
}
