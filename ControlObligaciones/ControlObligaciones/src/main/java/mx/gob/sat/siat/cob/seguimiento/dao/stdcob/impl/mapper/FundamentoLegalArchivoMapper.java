package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author root
 */
public class FundamentoLegalArchivoMapper implements RowMapper<String> {

    /**
     *
     * @param rs
     * @param i
     * @return
     * @throws SQLException
     */
    @Override
    public String mapRow(ResultSet rs, int i) throws SQLException {
        StringBuilder registro=new StringBuilder();
        registro.append(rs.getString("IDVIGILANCIA")).append("|");
        registro.append(rs.getString("IDOBLIGACION")).append("|");
        registro.append(rs.getString("EJERCICIO")).append("|");
        registro.append(rs.getString("IDPERIODO")).append("|");
        registro.append(rs.getString("IDREGIMEN")).append("|");
        registro.append(rs.getString("DESCRIPCION")).append(" ").append(rs.getString("DESCRIPCIONPERIODO"));
        registro.append(" ").append(rs.getString("EJERCICIO")).append("|");
        registro.append(rs.getString("FUNDAMENTOLEGAL")).append("|");
        registro.append(rs.getString("CHARFECHAVENCIMIENTO"));
        return registro.toString();
    }
    
}
