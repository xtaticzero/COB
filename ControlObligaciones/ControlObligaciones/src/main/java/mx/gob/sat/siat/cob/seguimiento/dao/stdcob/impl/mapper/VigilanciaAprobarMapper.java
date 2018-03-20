/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VigilanciaAprobar;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author root
 */
public class VigilanciaAprobarMapper implements RowMapper<VigilanciaAprobar>{
    
    private Integer valor;

    @Override
    public VigilanciaAprobar mapRow(ResultSet rs, int i) throws SQLException {
        VigilanciaAprobar vigilanciaAprobar=new VigilanciaAprobar();
        vigilanciaAprobar.setCantidadDocumentos(rs.getLong("cantidad_documentos"));
        vigilanciaAprobar.setDescripcionVigilancia(rs.getString("descripcion_vigilancia"));
        vigilanciaAprobar.setFechaCarga(rs.getDate("fecha_carga"));
        vigilanciaAprobar.setFechaCorte(rs.getDate("fecha_corte"));
        vigilanciaAprobar.setNombrePlantilla(rs.getString("nombre_plantilla"));
        vigilanciaAprobar.setIdPlantilla(rs.getInt("id_plantilla"));
        vigilanciaAprobar.setNumeroCarga(rs.getString("numero_carga"));
        vigilanciaAprobar.setTipoDocumento(rs.getString("tipo_documento"));
        vigilanciaAprobar.setTipoFirma(rs.getString("tipo_firma"));
        vigilanciaAprobar.setIdFirma(rs.getString("id_firma"));
        vigilanciaAprobar.setNumeroEmpleado(rs.getString("numero_empleado"));
        vigilanciaAprobar.setTipoMedio(rs.getString("tipo_medio"));
        vigilanciaAprobar.setFechaValidacion(rs.getDate("fecha_validacion"));
        if(valor!=null){
            vigilanciaAprobar.setAdministracionLocal(rs.getString("admon_local"));
            vigilanciaAprobar.setIdAdministracionLocal(rs.getString("id_admon_local"));
        }
        return vigilanciaAprobar;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }
    
}
