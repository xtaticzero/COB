/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DetalleReporteVigilanciaAprobada;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author root
 */
public class DetalleReporteVigilanciaAprobadaMapper implements RowMapper<DetalleReporteVigilanciaAprobada>{

    @Override
    public DetalleReporteVigilanciaAprobada mapRow(ResultSet rs, int i) throws SQLException {
        DetalleReporteVigilanciaAprobada detalle=new DetalleReporteVigilanciaAprobada();
        detalle.setRfc(rs.getString("rfc"));
        detalle.setNumeroControl(rs.getString("numerocontrol"));
        return detalle;
    }
    
}
