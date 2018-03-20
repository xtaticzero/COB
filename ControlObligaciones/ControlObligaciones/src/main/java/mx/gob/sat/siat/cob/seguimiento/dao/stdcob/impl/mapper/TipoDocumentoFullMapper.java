/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.TipoDocumento;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Marco Murakami
 */
public class TipoDocumentoFullMapper implements RowMapper<TipoDocumento>{

    /**
     *
     * @param rs
     * @param i
     * @return
     * @throws SQLException
     */
    @Override
    public TipoDocumento mapRow(ResultSet rs, int i) throws SQLException {
        TipoDocumento tipoDoc = new TipoDocumento();
        
        tipoDoc.setId(rs.getInt("IDESTADODOCTO"));
        tipoDoc.setNombre(rs.getString("NOMBRE"));
        
        return tipoDoc;
    }
}
