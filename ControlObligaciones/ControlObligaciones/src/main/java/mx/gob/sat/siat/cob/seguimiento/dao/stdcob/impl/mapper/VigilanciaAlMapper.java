/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VigilanciaAl;
import static mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.NivelEmisionEnum.obtenerNivel;
import static mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoMedioEnvioEnum.obtenerTipoMedio;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author root
 */
public class VigilanciaAlMapper implements RowMapper<VigilanciaAl>{

    @Override
    public VigilanciaAl mapRow(ResultSet rs, int i) throws SQLException {
        VigilanciaAl v=new VigilanciaAl();
        v.setFechaCarga(rs.getDate("fecha_carga"));
        v.setDescripcionVigilancia(rs.getString("descripcion_vigilancia"));
        v.setTipoDocumento(rs.getString("tipo_documento"));
        v.setTipoMedio(rs.getString("tipo_medio"));
        v.setTipoFirma(rs.getString("tipo_firma"));
        v.setNivelEmision(obtenerNivel(rs.getInt("id_nivel_emision")));
        if(v.getNivelEmision()!=null){
            v.getNivelEmision().setNivelEmision(rs.getString("nivel_emision"));
        }
        v.setCargoAdministrativo(rs.getString("cargo_administrativo"));
        v.setFechaCorte(rs.getDate("fecha_corte"));
        v.setTipoMedioEnvio(obtenerTipoMedio(rs.getInt("id_tipo_medio")));
        return v;
    }

    
    
}
