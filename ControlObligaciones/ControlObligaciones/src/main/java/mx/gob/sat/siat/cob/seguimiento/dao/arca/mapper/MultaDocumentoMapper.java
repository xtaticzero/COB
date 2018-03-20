/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.arca.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.DocumentoARCA;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Juan
 */
public class MultaDocumentoMapper implements RowMapper<DocumentoARCA> {

    /**
     *
     */
    public MultaDocumentoMapper() {
    }

    /**
     *
     * @param rs
     * @param i
     * @return
     * @throws SQLException
     */
    @Override
    public DocumentoARCA mapRow(ResultSet rs, int i) throws SQLException {
        DocumentoARCA documento = new DocumentoARCA();
        documento.setBoId(rs.getString("boId"));
        documento.setNumControlPadre(rs.getString("numeroControlPadre"));
        documento.setId(rs.getString("idDocumento"));
        documento.setIdTipoDocumento(rs.getInt("idTipoDocto_ARCA"));
        documento.setIdMedioEnvio(rs.getInt("idMedioEnvio_ARCA"));
        documento.setIdEstadoDocumento(rs.getInt("idEstadoDeDocumento"));
        documento.setIdDocumentoPlantilla(rs.getInt("idDocumentoPlantilla"));
        documento.setIdAlsc(rs.getString("ClaveALR"));
        documento.setCurp(rs.getString("CURP"));
        if (documento.getCurp() != null) {
            documento.setDescripcionContribuyente(rs.getString("nombre") + " " + rs.getString("apPaterno") + " " + rs.getString("apMaterno"));
        } else {
            documento.setDescripcionContribuyente(rs.getString("compania"));
        }
        documento.setRfc(rs.getString("rfc"));
        documento.setDescripcionDireccion(rs.getString("Calle") + " " + rs.getString("NumeroExterior") + " - " + rs.getString("NumeroInterior") + " "
                + rs.getString("DescripcionColonia") + rs.getString("DescripconLocalidad") + " entre "
                + rs.getString("EntreCalle1") + " y " + rs.getString("EntreCalle2") + ", "
                + rs.getString("DescripcionMunicipio") + " " + rs.getString("DescripcionEntidadFederativa") + " C.P. "
                + rs.getString("CodigoPostal"));

        documento.setCrh(rs.getInt("ClaveCRH"));
        documento.setResolucion(rs.getString("resolucion"));
        documento.setIdResolucion(rs.getInt("idResolucion"));
        documento.setImporte(rs.getString("importe"));
        return documento;
    }
}
