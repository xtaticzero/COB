/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VigilanciaAdministracionLocal;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Juan
 */
public class VigilanciaAdministracionLocalBatchMapper
        implements RowMapper<VigilanciaAdministracionLocal> {

    @Override
    public VigilanciaAdministracionLocal mapRow(ResultSet rs, int i) throws SQLException {
        VigilanciaAdministracionLocal vigilanciaAL = new VigilanciaAdministracionLocal();
        vigilanciaAL.setIdVigilancia(rs.getLong("idVigilancia"));
        vigilanciaAL.setIdAdministracionLocal(rs.getString("idAdmonLocal"));
        vigilanciaAL.setIdSituacionVigilancia(rs.getLong("idSituacionVigilancia"));
        vigilanciaAL.setFechaEnvioArca(rs.getDate("fechaEnvioARCA"));
        vigilanciaAL.setIdNivelEmision(rs.getInt("idNivelEmision"));

        return vigilanciaAL;

    }
}
