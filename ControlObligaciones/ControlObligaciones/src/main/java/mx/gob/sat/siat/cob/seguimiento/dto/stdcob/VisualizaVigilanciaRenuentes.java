/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

import java.io.Serializable;
import java.util.Date;
import mx.gob.sat.siat.cob.seguimiento.util.Utilerias;

/**
 *
 * @author root
 */
public class VisualizaVigilanciaRenuentes implements Serializable {

    private Date fechaEmision;    
    private Long cantidadDocumentos;
    private String tipoDocumento;
    private String tipoFirma;
    private String medioEnvio;
    private Long idTipoDocumento;
    private Integer idTipoFirma;
    private Long idMedioEnvio;
    private Long cantidadDocumentosEmitidos;

    public Date getFechaEmision() {
        if(fechaEmision==null){
            return null;
        }else{
            return (Date)fechaEmision.clone();
        }
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = (Date) fechaEmision.clone();
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public Long getCantidadDocumentos() {
        return cantidadDocumentos;
    }

    public void setCantidadDocumentos(Long cantidadDocumentos) {
        this.cantidadDocumentos = cantidadDocumentos;
    }

    public String getTipoFirma() {
        return tipoFirma;
    }

    public void setTipoFirma(String tipoFirma) {
        this.tipoFirma = tipoFirma;
    }

    public String getMedioEnvio() {
        return medioEnvio;
    }

    public void setMedioEnvio(String medioEnvio) {
        this.medioEnvio = medioEnvio;
    }

    public Long getIdTipoDocumento() {
        return idTipoDocumento;
    }

    public void setIdTipoDocumento(Long idTipoDocumento) {
        this.idTipoDocumento = idTipoDocumento;
    }

    public Integer getIdTipoFirma() {
        return idTipoFirma;
    }

    public void setIdTipoFirma(Integer idTipoFirma) {
        this.idTipoFirma = idTipoFirma;
    }

    

    public Long getIdMedioEnvio() {
        return idMedioEnvio;
    }

    public void setIdMedioEnvio(Long idMedioEnvio) {
        this.idMedioEnvio = idMedioEnvio;
    }

    public Long getCantidadDocumentosEmitidos() {
        return cantidadDocumentosEmitidos;
    }

    public void setCantidadDocumentosEmitidos(Long cantidadDocumentosEmitidos) {
        this.cantidadDocumentosEmitidos = cantidadDocumentosEmitidos;
    }

    @Override
    public String toString() {
        
        return "{" +
                "\"fechaEmision\":\"" + Utilerias.formatearFechaAAAAMMDD(fechaEmision) + "\", " +
                "\"cantidadDocumentos\":" + cantidadDocumentos + ", " +
                "\"tipoDocumento\":\"" + tipoDocumento + "\", " +
                "\"tipoFirma\":\"" + tipoFirma + "\", " +
                "\"medioEnvio\":\"" + medioEnvio + "\", " +                
                "\"idTipoDocumento\":" + idTipoDocumento + ", " +
                "\"idTipoFirma\":" + idTipoFirma + ", " +
                "\"idMedioEnvio\":" + idMedioEnvio + ", " +
                "\"cantidadDocumentosEmitidos\":" + cantidadDocumentosEmitidos + "}";
    }
    
}
