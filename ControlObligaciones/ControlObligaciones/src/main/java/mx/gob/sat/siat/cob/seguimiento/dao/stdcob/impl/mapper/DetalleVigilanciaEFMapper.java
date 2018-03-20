/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VigilanciaEntidadFederativa;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author root
 */
public class DetalleVigilanciaEFMapper implements RowMapper<VigilanciaEntidadFederativa>{

    @Override
    public VigilanciaEntidadFederativa mapRow(ResultSet rs, int i) throws SQLException {
        VigilanciaEntidadFederativa v=new VigilanciaEntidadFederativa();
        v.setIdEntidadFederativa(rs.getLong("id_entidad_federativa"));
        v.setNombreEntidadFederativa(rs.getString("entidad_federativa"));
        v.setCantidadDocumentos(rs.getLong("cantidad_documentos"));
        return v;
    }
    
}
