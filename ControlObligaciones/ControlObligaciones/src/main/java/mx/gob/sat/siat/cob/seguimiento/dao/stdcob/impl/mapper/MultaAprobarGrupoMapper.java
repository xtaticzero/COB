/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.MultaAprobarGrupo;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author root
 */
public class MultaAprobarGrupoMapper implements RowMapper {

    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException { 
        MultaAprobarGrupo multaAprobarGrupo = new MultaAprobarGrupo();
        multaAprobarGrupo.setFechaEmision(rs.getDate("fecha_registro"));    
        multaAprobarGrupo.setCantidadMultas(rs.getLong("numero_multas"));
        multaAprobarGrupo.setTipoMulta(rs.getString("tipo_multa"));
        multaAprobarGrupo.setTipoFirma(rs.getString("tipo_firma"));
        multaAprobarGrupo.setMedioEnvio(rs.getString("tipo_medio"));
        multaAprobarGrupo.setIdTipoMulta(rs.getString("id_tipo_multa"));
        multaAprobarGrupo.setIdTipoFirma(rs.getInt("id_tipo_firma"));
        multaAprobarGrupo.setIdMedioEnvio(rs.getInt("id_tipo_medio"));
        return multaAprobarGrupo;
    }
    
}
