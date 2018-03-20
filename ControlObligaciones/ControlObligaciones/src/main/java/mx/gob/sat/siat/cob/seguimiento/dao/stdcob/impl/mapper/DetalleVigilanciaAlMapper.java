/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VigilanciaAdministracionLocal;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author root
 */
public class DetalleVigilanciaAlMapper implements RowMapper<VigilanciaAdministracionLocal>{

    @Override
    public VigilanciaAdministracionLocal mapRow(ResultSet rs, int i) throws SQLException {
        VigilanciaAdministracionLocal v=new VigilanciaAdministracionLocal();
        v.setIdAdministracionLocal(rs.getString("id_administracion_local"));
        v.setAdministracionLocal(rs.getString("administracion_local"));
        v.setCantidadDocumentos(rs.getLong("cantidad_documentos"));
        return v;
    }
    
}
