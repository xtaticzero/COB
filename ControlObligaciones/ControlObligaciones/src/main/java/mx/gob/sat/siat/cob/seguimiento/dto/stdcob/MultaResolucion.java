/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author root
 */
public class MultaResolucion implements Serializable {

    private String boid;
    private String rfc;
    private String numeroResolucion;
    private Date fechaResolucionDet;
    private Integer idClaveSir;
    private Double monto;
    private Integer idFirmaTipo;
    private Date fechaReqCob;
    private String resolucionMotivo;
    private Integer descuento;
    private Long claveIcep;

    public Long getClaveIcep() {
        return claveIcep;
    }

    public void setClaveIcep(Long claveIcep) {
        this.claveIcep = claveIcep;
    }
    
    public String getBoid() {
        return boid;
    }

    public void setBoid(String boid) {
        this.boid = boid;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getNumeroResolucion() {
        return numeroResolucion;
    }

    public void setNumeroResolucion(String numeroResolucion) {
        this.numeroResolucion = numeroResolucion;
    }

    public Date getFechaResolucionDet() {
        
        if (fechaResolucionDet != null) {
            return (Date) fechaResolucionDet.clone();
        } else {
            return null;
        }
    }

    public void setFechaResolucionDet(Date fechaResolucionDet) {
        if (fechaResolucionDet != null) {
            this.fechaResolucionDet = (Date) fechaResolucionDet.clone();
        }
    }

    public Integer getIdClaveSir() {
        return idClaveSir;
    }

    public void setIdClaveSir(Integer idClaveSir) {
        this.idClaveSir = idClaveSir;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public Integer getIdFirmaTipo() {
        return idFirmaTipo;
    }

    public void setIdFirmaTipo(Integer idFirmaTipo) {
        this.idFirmaTipo = idFirmaTipo;
    }

    public Date getFechaReqCob() {
        if (fechaReqCob != null) {
            return (Date) fechaReqCob.clone();
        } else {
            return null;
        }
    }

    public void setFechaReqCob(Date fechaReqCob) {
        if (fechaReqCob != null) {
            this.fechaReqCob = (Date) fechaReqCob.clone();
        }
    }

    public String getResolucionMotivo() {
        return resolucionMotivo;
    }

    public void setResolucionMotivo(String resolucionMotivo) {
        this.resolucionMotivo = resolucionMotivo;
    }

    public Integer getDescuento() {
        return descuento;
    }

    public void setDescuento(Integer descuento) {
        this.descuento = descuento;
    }    
}
