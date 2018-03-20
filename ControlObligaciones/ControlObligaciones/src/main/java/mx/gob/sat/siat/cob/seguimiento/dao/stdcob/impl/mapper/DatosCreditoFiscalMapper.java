/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cobranza.domain.Resolucion;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author daniel
 */
public class DatosCreditoFiscalMapper implements RowMapper<Resolucion> {

    @Override
    public Resolucion mapRow(ResultSet rs, int i) throws SQLException {
        Resolucion resolucion = new Resolucion();
        resolucion.setNumResolucionDet(rs.getString("NUMERORESOLUCION"));
        return resolucion;
    }
    
}
