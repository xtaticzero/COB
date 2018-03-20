package mx.gob.sat.siat.cob.seguimiento.dao.catalogos.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.UsuarioEF;

import org.springframework.jdbc.core.RowMapper;

public class UsuarioEFSingleMapper implements RowMapper<UsuarioEF>{
    
    
    @Override
    public UsuarioEF mapRow(ResultSet rs, int rowNum) throws SQLException {
        UsuarioEF usuarioEF = new UsuarioEF();
                
        usuarioEF.setRfcCorto(rs.getString("USUARIO"));
        usuarioEF.setIdEntidadFederativa(rs.getLong("IDENTIDADFEDERATIVA"));
        usuarioEF.setNombreUsuario(rs.getString("NOMBREUSUARIO"));
        usuarioEF.setFechaInicio(rs.getDate("FECHAINICIO"));
        usuarioEF.setFechaFin(rs.getDate("FECHAFIN"));
        usuarioEF.setCorreoElectronico(rs.getString("CORREOELECTRONICO"));
        usuarioEF.setEntidadDesc(rs.getString("NOMBREENTIDAD"));
       
        return usuarioEF;
    }
}
