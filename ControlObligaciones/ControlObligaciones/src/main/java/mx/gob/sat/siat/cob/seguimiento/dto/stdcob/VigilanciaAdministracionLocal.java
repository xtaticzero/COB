/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

import java.io.Serializable;
import java.util.Date;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.MotRechazoVig;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.SituacionVigilanciaEnum;

/**
 *
 * @author root
 */
public class VigilanciaAdministracionLocal implements Serializable {

    private Long idVigilancia;
    private String numeroCarga;
    private String idAdministracionLocal;
    private String administracionLocal;
    private Long cantidadDocumentos;
    private SituacionVigilanciaEnum situacionVigilanciaEnum;
    private Long idSituacionVigilancia;
    private MotRechazoVig motivoRechazoVigilancia;
    private String descripcionVigilancia;
    private String numeroEmpleado;
    private String medioEnvio;
    private int idPlantilla;
    private Date fechaEnvioArca;
    private Date ultimaValidacion;
    private Integer idNivelEmision;

    public VigilanciaAdministracionLocal() {
        motivoRechazoVigilancia = new MotRechazoVig();
    }

    public Long getIdVigilancia() {
        return idVigilancia;
    }

    public void setIdVigilancia(Long idVigilancia) {
        this.idVigilancia = idVigilancia;
    }

    public String getIdAdministracionLocal() {
        return idAdministracionLocal;
    }

    public void setIdAdministracionLocal(String idAdministracionLocal) {
        this.idAdministracionLocal = idAdministracionLocal;
    }

    public Long getCantidadDocumentos() {
        return cantidadDocumentos;
    }

    public void setCantidadDocumentos(Long cantidadDocumentos) {
        this.cantidadDocumentos = cantidadDocumentos;
    }

    public Long getIdSituacionVigilancia() {
        return idSituacionVigilancia;
    }

    public void setIdSituacionVigilancia(Long idSituacionVigilancia) {
        this.idSituacionVigilancia = idSituacionVigilancia;
    }

    public SituacionVigilanciaEnum getSituacionVigilanciaEnum() {
        if (situacionVigilanciaEnum != null) {
            return situacionVigilanciaEnum;
        } else {
            for (SituacionVigilanciaEnum situacion : SituacionVigilanciaEnum.values()) {
                if (situacion.getIdSituacion() == idSituacionVigilancia) {
                    return situacion;
                }
            }
            return null;
        }

    }

    public void setSituacionVigilanciaEnum(SituacionVigilanciaEnum situacionVigilanciaEnum) {
        this.situacionVigilanciaEnum = situacionVigilanciaEnum;
    }

    public MotRechazoVig getMotivoRechazoVigilancia() {
        return motivoRechazoVigilancia;
    }

    public void setMotivoRechazoVigilancia(MotRechazoVig motivoRechazoVigilancia) {
        this.motivoRechazoVigilancia = motivoRechazoVigilancia;
    }

    public String getNumeroCarga() {
        return numeroCarga;
    }

    public void setNumeroCarga(String numeroCarga) {
        this.numeroCarga = numeroCarga;
    }

    public String getNumeroEmpleado() {
        return numeroEmpleado;
    }

    public void setNumeroEmpleado(String numeroEmpleado) {
        this.numeroEmpleado = numeroEmpleado;
    }

    public String getDescripcionVigilancia() {
        return descripcionVigilancia;
    }

    public void setDescripcionVigilancia(String descripcionVigilancia) {
        this.descripcionVigilancia = descripcionVigilancia;
    }

    public Date getFechaEnvioArca() {
        if (fechaEnvioArca != null) {
            return (Date) fechaEnvioArca.clone();
        }
        return null;
    }

    public void setFechaEnvioArca(Date fechaEnvioArca) {
        if (fechaEnvioArca != null) {
            this.fechaEnvioArca = (Date) fechaEnvioArca.clone();
        }
    }

    public String getMedioEnvio() {
        return medioEnvio;
    }

    public void setMedioEnvio(String medioEnvio) {
        this.medioEnvio = medioEnvio;
    }

    public String getAdministracionLocal() {
        return administracionLocal;
    }

    public void setAdministracionLocal(String administracionLocal) {
        this.administracionLocal = administracionLocal;
    }

    public Date getUltimaValidacion() {
        if (ultimaValidacion != null) {
            return (Date) ultimaValidacion.clone();
        } else {
            return null;
        }
    }

    public void setUltimaValidacion(Date ultimaValidacion) {
        if (ultimaValidacion != null) {
            this.ultimaValidacion = (Date) ultimaValidacion.clone();
        }
    }

    public int getIdPlantilla() {
        return idPlantilla;
    }

    public void setIdPlantilla(int idPlantilla) {
        this.idPlantilla = idPlantilla;
    }

    public Integer getIdNivelEmision() {
        return idNivelEmision;
    }

    public void setIdNivelEmision(Integer idNivelEmision) {
        this.idNivelEmision = idNivelEmision;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + (this.idVigilancia != null ? this.idVigilancia.hashCode() : 0);
        hash = 29 * hash + (this.idAdministracionLocal != null ? this.idAdministracionLocal.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final VigilanciaAdministracionLocal other = (VigilanciaAdministracionLocal) obj;
        if (this.idVigilancia != other.idVigilancia && (this.idVigilancia == null || !this.idVigilancia.equals(other.idVigilancia))) {
            return false;
        }
        if ((this.idAdministracionLocal == null) ? (other.idAdministracionLocal != null) : !this.idAdministracionLocal.equals(other.idAdministracionLocal)) {
            return false;
        }
        return true;
    }
}