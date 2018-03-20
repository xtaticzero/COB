/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.arca.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.Obligacion;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.ObligacionPeriodo;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.Periodo;
import mx.gob.sat.siat.cob.seguimiento.util.Utilerias;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Juan
 */
public class ObligacionPeriodoMapper implements RowMapper<ObligacionPeriodo> {

    /**
     *
     */
    public ObligacionPeriodoMapper() {
    }

    /**
     *
     * @param rs
     * @param i
     * @return
     * @throws SQLException
     */
    @Override
    public ObligacionPeriodo mapRow(ResultSet rs, int i) throws SQLException {

        ObligacionPeriodo obligacionPeriodo = new ObligacionPeriodo();
        Obligacion obligacion = new Obligacion();
        Periodo periodo = new Periodo();

        try {
            periodo.setIdDocumento(rs.getString("idDocumento"));
            periodo.setDescripcionPeriodo(rs.getString("descripcionPeriodo"));
            periodo.setEjercicio(Integer.parseInt(rs.getString("ejercicio")));
            periodo.setConceptoImpuesto(rs.getString("conceptoImpuesto"));

            obligacion.setIdDocumento(rs.getString("idDocumento"));
            obligacion.setDescripcionDeObligacion(rs.getString("descripcionObligacion"));
            obligacion.setDescripcionDePeriodo(rs.getString("descripcionPeriodo"));
            obligacion.setEjercicio(rs.getInt("ejercicio"));
            obligacion.setFundamentoLegal(rs.getString("fundamentoLegal"));
            obligacion.setFechaDeVencimiento(rs.getString("fechaVencimiento"));

            if (rs.getString("fechaNotificacion") != null) {
                Date fechaNotificacion = Utilerias.formatearFechaAAAAMMDDHHMM(rs.getString("fechaNotificacion"));
                String fechaformatoNotificacion = Utilerias.formatearFechaDDMMYYYY(fechaNotificacion);
                obligacion.setFechaNotificacion(fechaformatoNotificacion);
            }

            obligacion.setSancion(rs.getString("sancion"));
            obligacion.setInfraccion(rs.getString("infraccion"));
            obligacion.setImporte(rs.getString("importe"));

            obligacion.setIdObligacion(rs.getString("idObligacion"));
            obligacion.setConstanteResolMotivo(rs.getString("constanteResolMotivo"));
            obligacion.setIdPeriodo(rs.getString("idPeriodo"));
            obligacion.setIdPeriodicidad(rs.getString("idPeriodicidad"));

            if (Utilerias.existeColumna(rs, "numeroControl")) {
                obligacion.setNumControl(rs.getString("numeroControl"));
            }

            obligacionPeriodo.setObligacion(obligacion);
            obligacionPeriodo.setPeriodo(periodo);

        } catch (ParseException ex) {
            throw new SQLException(ex);
        }
        return obligacionPeriodo;
    }
}
