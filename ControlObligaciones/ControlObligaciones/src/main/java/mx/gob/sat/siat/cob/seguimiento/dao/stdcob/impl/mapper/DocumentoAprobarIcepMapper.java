/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DetalleDocumento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DocumentoAprobar;
import mx.gob.sat.siat.cob.seguimiento.util.Utilerias;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author root
 */
public class DocumentoAprobarIcepMapper implements RowMapper {

    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        List<DocumentoAprobar> documentos = new ArrayList<DocumentoAprobar>();
        DocumentoAprobar documento = new DocumentoAprobar();

        do {
            if (!rs.getString("numero_control").equals(documento.getNumeroControl())) {
                documento = new DocumentoAprobar();
                documento.setDescripcionVigilancia(rs.getString("descripcion_vigilancia"));
                documento.setNumeroCarga(rs.getString("numero_carga"));
                documento.setNumeroControl(rs.getString("numero_control"));
                documento.setRfc(rs.getString("rfc"));
                documento.setBoid(rs.getString("boid"));
                documento.setIdTipoDocumento(rs.getString("id_tipo_documento"));
                if (Utilerias.existeColumna(rs, "ultimoestado")) {
                    documento.setUltimoEstado(rs.getInt("ultimoestado"));
                }
                documentos.add(documento);
            }
            DetalleDocumento detalleDocumento = new DetalleDocumento();
            detalleDocumento.setBoid(documento.getBoid());
            detalleDocumento.setClaveIcep(rs.getLong("CLAVE_ICEP"));
            if (Utilerias.existeColumna(rs, "importepagar")) {
                    detalleDocumento.setImporteCargo(rs.getDouble("importepagar"));
            }
            if (Utilerias.existeColumna(rs, "fechacumplimiento")) {
                    detalleDocumento.setFechaCumplimiento(rs.getDate("fechacumplimiento"));
            }
            documento.getDetalles().add(detalleDocumento);
        } while (rs.next());
        return documentos;
    }
}
