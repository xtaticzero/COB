/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.arca.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.Periodo;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Juan
 */
public class PeriodoMapper implements RowMapper<Periodo> {

    @Override
    public Periodo mapRow(ResultSet rs, int i) throws SQLException {
        Periodo periodo = new Periodo();

        periodo.setIdDocumento(rs.getString("idDocumento"));
        periodo.setDescripcionPeriodo(rs.getString("descripcionPeriodo"));
        periodo.setEjercicio(Integer.parseInt(rs.getString("ejercicio")));
        periodo.setConceptoImpuesto(rs.getString("conceptoImpuesto"));

        return periodo;
    }
}
