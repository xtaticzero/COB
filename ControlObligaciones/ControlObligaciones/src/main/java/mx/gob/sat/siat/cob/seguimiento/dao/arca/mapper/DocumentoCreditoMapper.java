/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.arca.mapper;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Properties;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.Contribuyente;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.Direccion;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.DocumentoARCA;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoMultaEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoPersona;
import mx.gob.sat.siat.cob.seguimiento.util.Utilerias;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Juan
 */
public class DocumentoCreditoMapper implements RowMapper<DocumentoARCA> {

    private Logger log = Logger.getLogger(DocumentoCreditoMapper.class);

    @Override
    public DocumentoARCA mapRow(ResultSet rs, int i) throws SQLException {
        DocumentoARCA documento = new DocumentoARCA();
        documento.setBoId(rs.getString("boId"));
        documento.setId(rs.getString("idDocumento"));
        documento.setNumControlPadre(rs.getString("numeroControlPadre"));
        documento.setIdTipoDocumento(rs.getInt("idTipoDocto_ARCA"));
        documento.setIdMedioEnvio(rs.getInt("idMedioEnvio_ARCA"));
        documento.setIdEstadoDocumento(rs.getInt("idEstadoDeDocumento"));
        documento.setIdDocumentoPlantilla(rs.getInt("idDocumentoPlantilla"));
        documento.setIdAlsc(rs.getString("ALSC"));
        documento.setIdResolucion(rs.getInt("idResolucion"));
        documento.setIdProceso(rs.getLong("idProceso"));
        documento.setIdSubProceso(rs.getLong("idSubProceso"));
        documento.setIdLote(rs.getLong("idLote"));
        documento.setCodPostal(rs.getString("codPostal"));
        documento.setCrh(rs.getInt("CRH"));
        documento.setIdTipoPersona(rs.getString("idTipoPersona"));
        documento.setResolucion(rs.getString("resolucion"));
        documento.setIdFirmaDigitalAplicable(rs.getInt("idFirmaDigitalAplicable"));
        documento.setNombreFuncionario(rs.getString("nombreFuncionario"));
        documento.setDescripcionFuncionario(rs.getString("descripcionFuncionario"));
        documento.setImporte(rs.getString("importe"));
        documento.setCurp(rs.getString("CURP"));
        documento.setRfc(rs.getString("rfc"));
        documento.setFechaDocumento(rs.getDate("fechaDocumento"));
        documento.setIdVigilancia(rs.getLong("idVigilancia"));
        try {
            documento.setFechaEnvioARCA(Utilerias.formatearFechaYYYYMMDDHHMMSS(
                    Utilerias.formatearFechaYYYYMMDDHHMMSS(rs.getDate("fechaEnvioARCA"))));

            documento.setFechaRegistroARCA(rs.getString("fechaRegistroArca"));
        } catch (ParseException ex) {
            log.error(ex);
            throw new SQLException(ex);
        }
        documento.setIdEstatus(rs.getInt("idEstatus"));

        documento.setCreditoSIR(null);
        documento.setIdEntidadFederativa(rs.getInt("idEntidadFederativa"));
        documento.setIdDelegacionMunicipio(rs.getInt("idDelegacionMunicipio"));
        documento.setFirmaDigital(rs.getString("firmaDigital"));
        documento.setCadenaOriginal(rs.getString("cadenaOriginal"));

        setDireccion(documento, rs);
        setContribuyente(documento, rs);
        setIdMotivo(documento, rs.getString("idMotivo"));
        setQR(documento);


        return documento;
    }

    public void setDireccion(DocumentoARCA documento, ResultSet rs) throws SQLException {
        Direccion direccion = new Direccion();

        direccion.setCalle(rs.getString("calle"));
        direccion.setNumeroExterior(rs.getString("NumeroExterior"));
        direccion.setNumeroInterior(rs.getString("NumeroInterior"));
        direccion.setDescripcionColonia(rs.getString("DescripcionColonia"));
        direccion.setDescripcionLocalidad(rs.getString("DescripcionLocalidad"));
        direccion.setEntreCalle1(rs.getString("EntreCalle1"));
        direccion.setEntreCalle2(rs.getString("EntreCalle2"));
        direccion.setReferenciaAdicionales(rs.getString("ReferenciaAdicionales"));
        direccion.setDescripcionMunicipio(rs.getString("DescripcionMunicipio"));
        direccion.setDescripcionEntidadFederativa(rs.getString("DescripcionEntidadFederativa"));
        direccion.setCodigoPostal(documento.getCodPostal());

        documento.setDireccion(direccion);
        documento.setDescripcionDireccion(direccion.toString());
    }

    private void setContribuyente(DocumentoARCA documento, ResultSet rs) throws SQLException {
        Contribuyente contribuyente = new Contribuyente();
        if (documento.getIdTipoPersona().equals(TipoPersona.PERSONA_FISICA.getTipoPersona())) {
            documento.setDescripcionContribuyente(rs.getString("nombre") + " "
                    + rs.getString("apPaterno") + " " + rs.getString("apMaterno"));

            contribuyente.setNombre(rs.getString("nombre"));
            contribuyente.setApMaterno(rs.getString("apMaterno"));
            contribuyente.setApPaterno(rs.getString("apPaterno"));

        } else {
            contribuyente.setNombre(rs.getString("compania"));
            documento.setDescripcionContribuyente(rs.getString("compania"));
        }
        documento.setContribuyente(contribuyente);
    }

    private void setQR(DocumentoARCA documento) {
        Properties prop;
        InputStream fileInput = null;
        try {
            fileInput = new FileInputStream(ConstantsCatalogos.RUTA_URL_SIAT);
            prop = new Properties();
            prop.load(fileInput);
            StringBuilder qr = new StringBuilder();
            qr.append(documento.getId()).append("_").append(documento.getBoId()).append("_").append(documento.getRfc())
                    .append("_").append(0);
            documento.setInfromacionQR(prop.getProperty("url") + Utilerias.reemplazaCodificacion(qr.toString()));
        } catch (IOException ex) {
            log.error(ex);
        } finally {
            if (fileInput != null) {
                try {
                    fileInput.close();
                } catch (IOException ex) {
                    log.error(ex);
                }
            }
        }

    }

    private void setIdMotivo(DocumentoARCA documento, String idMotivo) {
        if (idMotivo.equals(TipoMultaEnum.INCUMPLIMIENTO.toString())) {
            documento.setIdMotivo("F");
        } else if (idMotivo.equals(TipoMultaEnum.EXTEMPORANEIDAD.toString())) {
            documento.setIdMotivo("I");
        } else if (idMotivo.equals(TipoMultaEnum.INCUMPLIMIENTO_EXTEMPORANEIDAD.toString())) {
            documento.setIdMotivo("J");
        } else if (idMotivo.equals(TipoMultaEnum.COMPLEMENTARIA.toString())) {
            documento.setIdMotivo("Q");
        }
    }
}
