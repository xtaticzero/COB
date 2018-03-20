/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DocumentoAprobar;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoDocumentoEnum;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author root
 */
public class DocumentoAprobarMapper implements RowMapper<DocumentoAprobar>{

    @Override
    public DocumentoAprobar mapRow(ResultSet rs, int i) throws SQLException {
        DocumentoAprobar documentoAprobar=new DocumentoAprobar();
        documentoAprobar.setDescripcionVigilancia(rs.getString("descripcion_vigilancia"));
        documentoAprobar.setNumeroCarga(rs.getString("numero_carga"));
        documentoAprobar.setNumeroControl(rs.getString("numero_control"));
        documentoAprobar.setRfc(rs.getString("rfc"));
        documentoAprobar.setIdAdministracionLocal(rs.getString("id_administracion_local"));
        documentoAprobar.setAdministracionLocal(rs.getString("administracion_local"));
        int estado=rs.getInt("estado_documento");
        if(estado!=EstadoDocumentoEnum.PENDIENTE_DE_PROCESAR.getValor()){
            documentoAprobar.setExcluir(true);
            documentoAprobar.setEstadoValido(true);
        }
        return documentoAprobar;
    }
    
}
