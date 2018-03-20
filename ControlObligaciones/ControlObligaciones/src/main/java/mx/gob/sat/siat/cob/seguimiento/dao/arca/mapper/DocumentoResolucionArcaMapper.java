/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.arca.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.Contribuyente;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.Direccion;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.DocumentoARCA;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Juan
 */
public class DocumentoResolucionArcaMapper implements RowMapper<DocumentoARCA> {

    @Override
    public DocumentoARCA mapRow(ResultSet rs, int i) throws SQLException {

        DocumentoARCA documento = new DocumentoARCA();

        documento.setId(rs.getString("fcDocumento"));
        documento.setFechaDocumento(rs.getDate("fdFechaDocumento"));
        documento.setIdAlsc(rs.getString("fiClaveAlr"));
        documento.setFechaEnvioARCA(rs.getDate("fdFechaRecepcion"));
        documento.setIdEstatus(rs.getInt("fiIdEstatus"));
        documento.setIdMotivo(rs.getString("fcMotivo"));
        documento.setImporte(rs.getString("fmImporte"));
        documento.setCreditoSIR(rs.getString("fiCreditoSIR"));
        documento.setRfc(rs.getString("fcRFC"));

        Contribuyente contribuyente = new Contribuyente();
        contribuyente.setCompania(rs.getString("fcNombreRazon"));
        contribuyente.setApPaterno(rs.getString("fcApellidoPaterno"));
        contribuyente.setApMaterno(rs.getString("fcApellidoMaterno"));

        documento.setContribuyente(contribuyente);

        Direccion direccion = new Direccion();
        direccion.setCalle(rs.getString("fcCalle"));
        direccion.setNumeroExterior(rs.getString("fcNumeroExterior"));
        direccion.setNumeroInterior(rs.getString("fcNumeroInterior"));
        direccion.setDescripcionColonia(rs.getString("fcColonia"));
        direccion.setDescripcionLocalidad(rs.getString("fcLocalidad"));
        direccion.setCodigoPostal(rs.getString("fiCodigoPostal"));
        direccion.setDescripcionEntidadFederativa(rs.getString("fiEntidadFederativa"));
        direccion.setDescripcionMunicipio(rs.getString("fiDelegacionMunicipio"));

        documento.setDireccion(direccion);

        documento.setCrh(rs.getInt("fcCRH"));
        documento.setBoId(rs.getString("fcBOID"));
        documento.setMensajeErrorARCA(rs.getString("fcMensajeError"));

        return documento;
    }
}
