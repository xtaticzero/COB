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
public class MultaAprobarGrupo implements Serializable{
    private String tipoAdministracion;
    private Date fechaEmision;    
    private Long cantidadMultas;
    private Long cantidadPosiblesEmitir;
    private String tipoMulta;
    private String tipoFirma;
    private String medioEnvio;
    private String idTipoMulta;
    private Integer idTipoFirma;
    private Integer idMedioEnvio;
    private Long cantidadMultasEmitidas;
    private Integer conteoElementos;
    

    public Date getFechaEmision() {
        return (Date)fechaEmision.clone();
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = (Date)fechaEmision.clone();
    }

    public Long getCantidadMultas() {
        return cantidadMultas;
    }

    public void setCantidadMultas(Long cantidadMultas) {
        this.cantidadMultas = cantidadMultas;
    }

    public String getTipoMulta() {
        return tipoMulta;
    }

    public void setTipoMulta(String tipoMulta) {
        this.tipoMulta = tipoMulta;
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

    public String getIdTipoMulta() {
        return idTipoMulta;
    }

    public void setIdTipoMulta(String idTipoMulta) {
        this.idTipoMulta = idTipoMulta;
    }

    public Integer getIdTipoFirma() {
        return idTipoFirma;
    }

    public void setIdTipoFirma(Integer idTipoFirma) {
        this.idTipoFirma = idTipoFirma;
    }

    public Integer getIdMedioEnvio() {
        return idMedioEnvio;
    }

    public void setIdMedioEnvio(Integer idMedioEnvio) {
        this.idMedioEnvio = idMedioEnvio;
    }

    public Long getCantidadMultasEmitidas() {
        return cantidadMultasEmitidas;
    }

    public void setCantidadMultasEmitidas(Long cantidadMultasEmitidas) {
        this.cantidadMultasEmitidas = cantidadMultasEmitidas;
    }
    public Long getCantidadPosiblesEmitir() {
        return cantidadPosiblesEmitir;
    }

    public void setCantidadPosiblesEmitir(Long cantidadPosiblesEmitir) {
        this.cantidadPosiblesEmitir = cantidadPosiblesEmitir;
    }

    public String getTipoAdministracion() {
        return tipoAdministracion;
    }

    public void setTipoAdministracion(String tipoAdministracion) {
        this.tipoAdministracion = tipoAdministracion;
    }

    public Integer getConteoElementos() {
        return conteoElementos;
    }

    public void setConteoElementos(Integer conteoElementos) {
        this.conteoElementos = conteoElementos;
    }

    @Override
    public String toString() {
        return "{" + "\"tipoAdministracion\":\"" +( tipoAdministracion==null ? "" :tipoAdministracion )+ "\", " +
                "\"fechaEmision\":\"" + Utilerias.formatearFechaAAAAMMDD(fechaEmision) + "\", " +
                "\"cantidadMultas\":" + cantidadMultas + ", " +
                "\"cantidadPosiblesEmitir\":" + cantidadPosiblesEmitir + ", " +
                "\"tipoMulta\":\"" + tipoMulta + "\", " +
                "\"tipoFirma\":\"" + tipoFirma + "\", " +
                "\"medioEnvio\":\"" + medioEnvio + "\", " +
                "\"idTipoMulta\":\"" + idTipoMulta + "\", " +
                "\"idTipoFirma\":" + idTipoFirma + ", " +
                "\"idMedioEnvio\":" + idMedioEnvio + ", " +
                "\"cantidadMultasEmitidas\":" + cantidadMultasEmitidas + ", " +
                "\"conteoElementos\":" + conteoElementos + "}";
    }
    
}
