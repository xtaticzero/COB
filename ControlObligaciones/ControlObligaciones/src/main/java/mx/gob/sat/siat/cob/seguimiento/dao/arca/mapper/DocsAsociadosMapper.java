/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.arca.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.RequerimientosAnteriores;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Marco Murakami
 */
public class DocsAsociadosMapper implements RowMapper<RequerimientosAnteriores>{
    /**
     *
     */
    public DocsAsociadosMapper(){
        super();
    }

    /**
     * Mapeo del resultado de la consulta
     * @param rs
     * @param i
     * @return
     * @throws SQLException 
     */
    @Override
    public RequerimientosAnteriores mapRow(ResultSet rs, int i) throws SQLException {
        RequerimientosAnteriores reqsAnteriores = new RequerimientosAnteriores();

        reqsAnteriores.setIdDocumento(rs.getString("NumeroControl"));
        reqsAnteriores.setIdDocumentoPadre(rs.getString("NumeroControlPadre"));
        reqsAnteriores.setFechaNotificacion(rs.getString("FechaNotificacion"));
        reqsAnteriores.setTipoDocumento(rs.getString("Nombre"));

        return reqsAnteriores;
    }
}
