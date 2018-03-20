/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VisualizaVigilanciaRenuentes;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author root
 */
public class VisualizaVigilanciaRenuentesMapper implements RowMapper {

    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        VisualizaVigilanciaRenuentes visualizaVigilanciaRenuentes = new VisualizaVigilanciaRenuentes();
        visualizaVigilanciaRenuentes.setFechaEmision(rs.getDate("fecha_registro"));        
        visualizaVigilanciaRenuentes.setCantidadDocumentos(rs.getLong("cantidad_documentos"));
        visualizaVigilanciaRenuentes.setTipoDocumento(rs.getString("tipo_documento"));
        visualizaVigilanciaRenuentes.setTipoFirma(rs.getString("tipo_firma"));
        visualizaVigilanciaRenuentes.setMedioEnvio(rs.getString("medio_envio"));
        visualizaVigilanciaRenuentes.setIdTipoDocumento(rs.getLong("id_tipo_documento"));
        visualizaVigilanciaRenuentes.setIdTipoFirma(rs.getInt("id_tipo_firma"));
        visualizaVigilanciaRenuentes.setIdMedioEnvio(rs.getLong("id_tipo_medio"));
        return visualizaVigilanciaRenuentes;
    }
}
