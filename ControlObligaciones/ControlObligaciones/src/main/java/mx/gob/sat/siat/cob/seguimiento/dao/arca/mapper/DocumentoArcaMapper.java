package mx.gob.sat.siat.cob.seguimiento.dao.arca.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.DocumentoARCA;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Juan
 */
public class DocumentoArcaMapper implements RowMapper<DocumentoARCA> {

    /**
     *
     * @param rs
     * @param i
     * @return
     * @throws java.sql.SQLException
     */
    @Override
    public DocumentoARCA mapRow(ResultSet rs, int i) throws SQLException {
        DocumentoARCA documento = new DocumentoARCA();

        documento.setId(rs.getString("id"));
        documento.setIdTipoDocumento(rs.getInt("idTipoDocumento"));
        documento.setIdMedioEnvio(rs.getInt("idMedioDeEnvio"));
        documento.setIdEstadoDocumento(rs.getInt("idEstadoDeDocumento"));
        documento.setIdDocumentoPlantilla(rs.getInt("idDocumentoPlantilla"));
        documento.setIdAlsc(rs.getString("idALSC"));
        documento.setIdResolucion(rs.getInt("idResolucion"));
        documento.setDescripcionContribuyente(rs.getString("fcContribuyente"));
        documento.setRfc(rs.getString("fcRFC"));
        documento.setCurp(rs.getString("fcCURP"));
        documento.setDescripcionDireccion(rs.getString("fcDireccion"));
        documento.setCrh(rs.getInt("fiCRH"));
        documento.setImporte(rs.getString("ffImporte"));
        documento.setCodPostal(rs.getString("fcCP"));
        documento.setIdTipoPersona(rs.getString("fcTipoPersona"));
        documento.setInfromacionQR(rs.getString("fcInformacionQR"));

        return documento;
    }
}
