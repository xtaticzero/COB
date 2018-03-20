/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DetalleDocumento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Documento;

import org.springframework.jdbc.core.RowMapper;


/**
 *
 * @author root
 */
public class DocumentoPadronMapper implements RowMapper {

    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        Map<String,Documento> documentos = new HashMap<String,Documento>();
        Documento documento = null;
        do{
            documento = documentos.get(rs.getString("numerocontrol"));
            if(documento == null){
                documento = new Documento();
                documento.setNumeroControl(rs.getString("numerocontrol"));
                documento.setNumeroControlPadre(rs.getString("numerocontrolpadre"));
                documento.setFechaNotificacion(rs.getDate("fechanotificacion"));
                documento.setFechaImpresion(rs.getDate("fechaimpresion"));
                documento.setRfc(rs.getString("rfc"));
                documento.setBoid(rs.getString("boid"));
                documento.setFechaVencimiento(rs.getDate("fechavencimientodocto"));
                documento.setIdEtapaVigilancia(rs.getInt("idetapavigilancia"));
                documento.setFechaNoLocalizadoContribuyente(
                        rs.getDate("fechanolocalizadocontribuyente"));
                documento.getVigilancia().setIdVigilancia(rs.getLong("idVigilancia"));
                documento.setEsUltimoGenerado(rs.getInt("esultimogenerado"));
                documento.setUltimoEstado(rs.getInt("ultimoestado"));
                documento.setFechaRegistro(rs.getDate("fecharegistro"));
                documento.setConsideraRenuencia(rs.getInt("considerarenuencia"));
                documento.setFechaNoTrabajado(rs.getString("fechanotrabajado"));
                documento.setIdAdmonLocal(rs.getString("idadmonlocal"));
                documento.setCodigoPostal(rs.getString("codigopostal"));
                documento.setIdcrh(rs.getInt("idcrh"));
                documento.setIdentidadFederativa(rs.getInt("identidadfederativa"));
                documento.setDetalles(new ArrayList<DetalleDocumento>());
                documento.getVigilancia().setIdTipoDocumento(rs.getInt("idtipodocumento"));
                documento.getVigilancia().setIdTipoMedio(rs.getInt("idtipomedio"));
                documentos.put(rs.getString("numerocontrol"),documento);
            }
            
            DetalleDocumento detalleDocumento = new DetalleDocumento();
            detalleDocumento.setBoid(documento.getBoid());
            detalleDocumento.setClaveIcep(rs.getLong("CLAVEICEP"));
            detalleDocumento.setIdObligacion(rs.getInt("IDOBLIGACION"));
            detalleDocumento.setIdRegimen(rs.getString("IDREGIMEN"));
            detalleDocumento.setIdEjercicio(rs.getString("EJERCICIO"));
            detalleDocumento.setIdPeriodo(rs.getString("IDPERIODO"));
            detalleDocumento.setIdPeriodicidad(rs.getString("IDPERIODICIDAD"));
            detalleDocumento.setFechaVencimiento(rs.getString("FECHAVENCIMIENTOOBLIGACION"));
            detalleDocumento.setFechaBaja(rs.getDate("FECHABAJA"));
            detalleDocumento.setFechaMantenimiento(rs.getDate("FECHAMANTENIMIENTO"));
            detalleDocumento.setIdSituacionIcep(
                    rs.getInt("idsituacionicep"));
            documento.getDetalles().add(detalleDocumento);
        }while (rs.next());
        return new ArrayList<Documento>(documentos.values());
    }

}
