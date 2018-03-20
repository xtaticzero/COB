/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.arca.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.OrigenMulta;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Juan
 */
public class OrigenMultaMapper implements RowMapper<OrigenMulta> {

    @Override
    public OrigenMulta mapRow(ResultSet resultSet, int i) throws SQLException {
        OrigenMulta origenMulta = new OrigenMulta();
        
        origenMulta.setIdDocumento(resultSet.getString("idDocumento"));
        origenMulta.setNumControlOrigen(resultSet.getString("numeroControlOrigen"));
        origenMulta.setFechaNotificacionOrigen(resultSet.getString("fechaNotificacionOrigen"));
        origenMulta.setNumControlPrimero(resultSet.getString("numeroControlPrimero"));
        origenMulta.setFechaNotificacionPrimero(resultSet.getString("fechaNotificacionPrimero"));
        origenMulta.setNumControlSegundo(resultSet.getString("numeroControlSegundo"));
        origenMulta.setFechaNotificacionSegundo(resultSet.getString("fechaNotificacionSegundo"));
        
        return origenMulta;
    }
}
