package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DatoDoctoVigilancia;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author root
 */
public class DatoDoctoVigilanciaMapper implements RowMapper<DatoDoctoVigilancia> {

    /**
     *
     * @param resultSet
     * @param i
     * @return
     * @throws SQLException
     */
    @Override
    public DatoDoctoVigilancia mapRow(ResultSet resultSet, int i) throws SQLException {
        DatoDoctoVigilancia datoDocumento = new DatoDoctoVigilancia();
        datoDocumento.setIdVigilancia(resultSet.getInt("IDVIGILANCIA"));
        datoDocumento.setIdEtapaVigilancia(resultSet.getInt("IDETAPAVIGILANCIA"));
        datoDocumento.setNumeroControl(resultSet.getString("NUMEROCONTROL"));
        datoDocumento.setRfc(resultSet.getString("RFC"));
        datoDocumento.setBoId(resultSet.getString("BOID"));
        return datoDocumento;
    }
}
