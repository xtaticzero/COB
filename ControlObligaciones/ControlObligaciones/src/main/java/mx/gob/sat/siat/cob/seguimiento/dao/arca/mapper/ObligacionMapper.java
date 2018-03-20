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
public class ObligacionMapper implements RowMapper<Obligacion> {

    /**
     *
     */
    public ObligacionMapper() {
    }

    /**
     *
     * @param rs
     * @param i
     * @return
     * @throws SQLException
     */
    @Override
    public Obligacion mapRow(ResultSet rs, int i) throws SQLException {
        Obligacion obligacion = new Obligacion();

        obligacion.setIdDocumento(rs.getString("IDDOCUMENTO"));
        obligacion.setDescripcionDeObligacion(rs.getString("descripcionObligacion"));
        obligacion.setDescripcionDePeriodo(rs.getString("descripcionPeriodo"));
        obligacion.setEjercicio(rs.getInt("EJERCICIO"));
        obligacion.setFundamentoLegal(rs.getString("fundamentoLegal"));
        obligacion.setFechaDeVencimiento(rs.getString("fechaVencimiento"));
        obligacion.setSancion(rs.getString("SANCION"));
        obligacion.setInfraccion(rs.getString("INFRACCION"));
        obligacion.setImporte(rs.getString("IMPORTE"));

        return obligacion;
    }
}
