/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VisualizaDocumentoRenuente;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author root
 */
public class VisualizaDocumentoRenuenteMapper implements RowMapper {

    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        VisualizaDocumentoRenuente visualizaDocumentoRenuente = new VisualizaDocumentoRenuente();
        visualizaDocumentoRenuente.setNumeroControl(rs.getString("numerocontrol"));
        visualizaDocumentoRenuente.setRfc(rs.getString("rfc"));
        visualizaDocumentoRenuente.setNumeroControlRequerimiento(rs.getString("numerocontrolpadre"));
        return visualizaDocumentoRenuente;
    }
    
}
