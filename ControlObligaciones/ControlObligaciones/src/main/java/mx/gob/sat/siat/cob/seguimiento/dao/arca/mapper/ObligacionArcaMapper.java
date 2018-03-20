/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.arca.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.Obligacion;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Juan
 */
public class ObligacionArcaMapper implements RowMapper<Obligacion> {

    @Override
    public Obligacion mapRow(ResultSet rs, int i) throws SQLException {
        Obligacion obligacion = new Obligacion();

        obligacion.setIdDocumento(rs.getString("IdDocumento"));
        obligacion.setDescripcionDeObligacion(rs.getString("fcDescripcionDeObligacion"));
        obligacion.setDescripcionDePeriodo(rs.getString("fcDescripcionDePeriodo"));
        obligacion.setEjercicio(rs.getInt("fiEjercicio"));
        obligacion.setFundamentoLegal(rs.getString("fcFundamentoLegal"));
        obligacion.setFechaDeVencimiento(rs.getString("fdFechaDeVencimiento"));
        obligacion.setSancion(rs.getString("fcSancion"));
        obligacion.setInfraccion(rs.getString("fcInfraccion"));
        obligacion.setImporte(rs.getString("fcimporte"));
        obligacion.setMotivacion(rs.getString("fcMotivacion"));

        return obligacion;
    }
}