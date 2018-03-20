/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.IcepsAprobar;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author root
 */
public class IcepsAprobarMapper implements RowMapper<IcepsAprobar> {

    @Override
    public IcepsAprobar mapRow(ResultSet rs, int i) throws SQLException {
        IcepsAprobar icepsAprobar = new IcepsAprobar();
        icepsAprobar.setClaveIcep(rs.getString("clave_icep"));
        icepsAprobar.setDescripcionObligacion(rs.getString("descripcion_obligacion"));
        icepsAprobar.setEstadoObligacion(rs.getString("estado_obligacion"));
        icepsAprobar.setFechaCorte(rs.getDate("fecha_corte"));
        icepsAprobar.setNumeroControl(rs.getString("numero_control"));
        icepsAprobar.setRfc(rs.getString("rfc"));
        icepsAprobar.setEjercicio(rs.getString("ejercicio"));
        icepsAprobar.setPeriodo(rs.getString("periodo"));
        return icepsAprobar;
    }

}
