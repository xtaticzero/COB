package mx.gob.sat.siat.cob.seguimiento.dao.catalogos.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.UsuarioEF;
import org.springframework.jdbc.core.RowMapper;

public class UsuarioEFMapper implements RowMapper<UsuarioEF>{
    public UsuarioEFMapper() {
        super();
    }
    
    /**
     *
     * @param rs
     * @param rowNum
     * @return
     * @throws SQLException
     */
    @Override
    public UsuarioEF mapRow(ResultSet rs, int rowNum) throws SQLException {
        UsuarioEF usuarioEF = new UsuarioEF();
                
        usuarioEF.setRfcCorto(rs.getString("USUARIO"));
        usuarioEF.setIdEntidadFederativa(rs.getLong("IDENTIDADFEDERATIVA"));
        usuarioEF.setNombreUsuario(rs.getString("NOMBREUSUARIO"));
        usuarioEF.setFechaInicio(rs.getDate("FECHAINICIO"));
        usuarioEF.setFechaFin(rs.getDate("FECHAFIN"));
        usuarioEF.setCorreoElectronico(rs.getString("CORREOELECTRONICO"));
        usuarioEF.setEntidadDesc(rs.getString("ENTIDADDESC")!=null?rs.getString("ENTIDADDESC"):"");
        if (rs.getDate("FECHAFIN")==null){
            usuarioEF.setFechaFinStr("1");
            usuarioEF.setSituacion("Activo");
        }else {
            usuarioEF.setFechaFinStr("0");
            usuarioEF.setSituacion("Inactivo");
        }
        
        return usuarioEF;
    }
}
