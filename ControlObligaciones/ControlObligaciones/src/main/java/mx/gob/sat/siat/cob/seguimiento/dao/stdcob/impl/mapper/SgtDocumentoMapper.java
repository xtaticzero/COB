package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.SgtDocumento;
import org.springframework.jdbc.core.RowMapper;


public class SgtDocumentoMapper implements RowMapper {

    /**
     *
     * @param resultSet
     * @param i
     * @return
     * @throws SQLException
     */
    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        List<SgtDocumento> sgtDocumentos = new ArrayList<SgtDocumento>(0);
        do {
            SgtDocumento documento = new SgtDocumento();
            documento.setNumeroControl(resultSet.getString("NUMEROCONTROL"));
            documento.setIdEstadoDocto(resultSet.getInt("IDESTADODOCTO"));
            documento.setFechaMovimiento(resultSet.getDate("FECHAMOVIMIENTO"));
            sgtDocumentos.add(documento);
        } while (resultSet.next());
        return sgtDocumentos;
    }
}
