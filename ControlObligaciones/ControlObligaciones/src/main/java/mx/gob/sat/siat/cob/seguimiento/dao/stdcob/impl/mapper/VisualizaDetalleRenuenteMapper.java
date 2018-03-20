/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VisualizaDetalleRenuente;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author root
 */
public class VisualizaDetalleRenuenteMapper implements RowMapper{

    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        VisualizaDetalleRenuente visualizaDetalleRenuente = new VisualizaDetalleRenuente();
        visualizaDetalleRenuente.setClaveObligacion(rs.getLong("idobligacion"));
        visualizaDetalleRenuente.setObligacion(rs.getString("obligacion_descripcion"));
        visualizaDetalleRenuente.setEjercicio(rs.getInt("ejercicio"));
        visualizaDetalleRenuente.setPeriodo(rs.getString("periodo_descripcion"));
        return visualizaDetalleRenuente;
    }
    
}
