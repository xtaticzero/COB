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
public class OrigenMultaArcaMapper implements RowMapper<OrigenMulta> {

    @Override
    public OrigenMulta mapRow(ResultSet rs, int i) throws SQLException {
        OrigenMulta origenMulta = new OrigenMulta();

        origenMulta.setIdDocumento(rs.getString("IdDocumento"));
        origenMulta.setNumControlOrigen(rs.getString("fcNumeroDeControlOrigen"));
        origenMulta.setFechaNotificacionOrigen(rs.getString("fdFechaDeNotificacionOrigen"));
        origenMulta.setNumControlPrimero(rs.getString("fcNumeroDeControlPrimero"));
        origenMulta.setFechaNotificacionPrimero(rs.getString("fdFechaDeNotificacionPrimero"));
        origenMulta.setNumControlSegundo(rs.getString("fcNumeroDeControlSegundo"));
        origenMulta.setFechaNotificacionSegundo(rs.getString("fdFechaDeNotificacionSegundo"));

        return origenMulta;
    }
}