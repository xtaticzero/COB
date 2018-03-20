/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.AfectacionXAutoridad;

import org.springframework.jdbc.core.RowMapper;


/**
 *
 * @author Marco Murakami
 */
public class AfectacionXAutoridadMapper implements RowMapper<AfectacionXAutoridad> {

    /**
     *
     * @param rs
     * @param i
     * @return
     * @throws SQLException
     */
    @Override
    public AfectacionXAutoridad mapRow(ResultSet rs, int i) throws SQLException {

        AfectacionXAutoridad afectacionXAutoridad = new AfectacionXAutoridad();
        afectacionXAutoridad.setNumeroControl(rs.getString("NUMEROCONTROL"));
        afectacionXAutoridad.setRfc(rs.getString("RFC"));
        afectacionXAutoridad.setFechaRegistro(rs.getString("FECHAREGISTRO"));
        afectacionXAutoridad.setFechaNotificacion(rs.getString("FECHANOTIFICACION"));
        afectacionXAutoridad.setFechaVencimiento(rs.getString("FECHAVENCIMIENTO"));
        afectacionXAutoridad.setEstado(rs.getString("ESTADO"));
        afectacionXAutoridad.setClaveObligacion(rs.getInt("CLAVEOBLIGACION"));
        afectacionXAutoridad.setObDescripcion(rs.getString("DESCRIPCION"));
        afectacionXAutoridad.setSolventado(rs.getString("SITUACIONICEP"));
        afectacionXAutoridad.setNumResolucion(rs.getString("NUMERORESOLUCION"));
        afectacionXAutoridad.setMotivo(rs.getString("MOTIVO"));
        afectacionXAutoridad.setTipoDoc(rs.getString("TIPODOCUMENTO"));
        afectacionXAutoridad.setNombre(rs.getString("NOMBRE"));
        afectacionXAutoridad.setTipoMedio(rs.getString("TIPOMEDIO"));
        return afectacionXAutoridad;
    }
}
