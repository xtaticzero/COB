package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Vigilancia;
import org.springframework.jdbc.core.RowMapper;


public class ConsultaDocsVigilanciaPorEstadoMapper implements RowMapper{

    /**
     *
     * @param resultSet
     * @param i
     * @return
     * @throws SQLException
     */
    @Override
    public Vigilancia mapRow(ResultSet resultSet, int i) throws SQLException {
        Vigilancia  vig= new Vigilancia ();
        vig.setIdVigilancia(resultSet.getLong("IDVIGILANCIA"));
        vig.setIdTipoDocumento(resultSet.getInt("IDTIPODOCUMENTO"));
        vig.setIdTipoMedio(resultSet.getInt("IDTIPOMEDIO"));
        vig.setIdMovimiento(resultSet.getLong("TOTALDOCS"));
        return vig;
    }
}
