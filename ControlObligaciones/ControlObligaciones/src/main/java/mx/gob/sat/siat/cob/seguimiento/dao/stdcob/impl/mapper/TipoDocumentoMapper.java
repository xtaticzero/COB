package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class TipoDocumentoMapper implements RowMapper {
    /**
     *
     */
    public TipoDocumentoMapper() {
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
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        Integer tipoDoc;
        tipoDoc = null;
        tipoDoc = resultSet.getInt("IDTIPODOCUMENTO");
        return tipoDoc;
    }
}
