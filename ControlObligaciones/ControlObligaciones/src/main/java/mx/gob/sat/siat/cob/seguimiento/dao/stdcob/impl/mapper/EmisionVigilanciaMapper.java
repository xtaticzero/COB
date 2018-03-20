package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.EmisionVigilancia;
import org.springframework.jdbc.core.RowMapper;

public class EmisionVigilanciaMapper implements RowMapper<EmisionVigilancia> {

    /**
     *
     * @param resultSet
     * @param i
     * @return
     * @throws SQLException
     */
    @Override
    public EmisionVigilancia mapRow(ResultSet resultSet, int i) throws SQLException {
        EmisionVigilancia emision = new EmisionVigilancia();
        emision.setIdVigilancia(resultSet.getInt("IDVIGILANCIA"));
        emision.setIdTipoDocumento(resultSet.getInt("IDTIPODOCUMENTO"));
        emision.setIdEtapaVigilancia(resultSet.getInt("IDETAPAVIGILANCIA"));
        emision.setEsPlantillaDIOT(resultSet.getInt("ESPLANTILLADIOT"));
        return emision;
    }
}
