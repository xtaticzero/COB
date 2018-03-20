/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

import java.io.Serializable;

import java.util.Date;
import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoMultaEnum;

/**
 *
 * @author rodrigo referencia tabla sgtt_resoluciondoc
 */
public class MultaDocumento implements Serializable {

    private String numeroResolucion;
    private Long idResolucion;
    private String numeroControl;
    private Date fechaRegistro;
    private String constanteResolucionMotivo;
    private int ultimoEstado;
    private List<MultaIcep> iceps;
    private Double monto;
    private Double montoTotal;
    private String rfc;

    /**
     *
     */
    public MultaDocumento() {
    }

    /**
     *
     * @param numeroResolucion
     * @param idResolucion
     * @param numeroControl
     * @param fechaRegistro
     * @param ultimoEstado
     * @param constanteResolucionMotivo
     */
    public MultaDocumento(String numeroResolucion, Long idResolucion, String numeroControl,
            Date fechaRegistro, String constanteResolucionMotivo, int ultimoEstado) {
        this.numeroResolucion = numeroResolucion;
        this.idResolucion = idResolucion;
        this.numeroControl = numeroControl;
        if (fechaRegistro != null) {
            this.fechaRegistro = (Date) fechaRegistro.clone();
        }
        this.ultimoEstado = ultimoEstado;
        this.constanteResolucionMotivo = constanteResolucionMotivo;
    }

    /**
     *
     * @return
     */
    public String getNumeroResolucion() {
        return numeroResolucion;
    }

    /**
     *
     * @param numeroResolucion
     */
    public void setNumeroResolucion(String numeroResolucion) {
        this.numeroResolucion = numeroResolucion;
    }

    /**
     *
     * @return
     */
    public Long getIdResolucion() {
        return idResolucion;
    }

    /**
     *
     * @param idResolucion
     */
    public void setIdResolucion(Long idResolucion) {
        this.idResolucion = idResolucion;
    }

    /**
     *
     * @return
     */
    public String getNumeroControl() {
        return numeroControl;
    }

    /**
     *
     * @param numeroControl
     */
    public void setNumeroControl(String numeroControl) {
        this.numeroControl = numeroControl;
    }

    /**
     *
     * @return
     */
    public Date getFechaRegistro() {
        if (fechaRegistro != null) {
            return (Date) fechaRegistro.clone();
        } else {
            return null;
        }
    }

    /**
     *
     * @param fechaRegistro
     */
    public void setFechaRegistro(Date fechaRegistro) {
        if (fechaRegistro != null) {
            this.fechaRegistro = (Date) fechaRegistro.clone();
        }
    }

    public int getUltimoEstado() {
        return ultimoEstado;
    }

    public void setUltimoEstado(int ultimoEstado) {
        this.ultimoEstado = ultimoEstado;
    }

    public List<MultaIcep> getIceps() {
        return iceps;
    }

    public void setIceps(List<MultaIcep> iceps) {
        this.iceps = iceps;
    }

    /**
     *
     * @return
     */
    public String getConstanteResolucionMotivo() {
        return constanteResolucionMotivo;
    }

    /**
     *
     * @param constanteResolucionMotivo
     */
    public void setConstanteResolucionMotivo(String constanteResolucionMotivo) {
        this.constanteResolucionMotivo = constanteResolucionMotivo;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }
    

    @Override
    public String toString() {
        return "MultaDocumento{" + "numeroResolucion=" + numeroResolucion + ", idResolucion=" + idResolucion + ", numeroControl=" + numeroControl + ", fechaRegistro=" + fechaRegistro + ", ultimoEstado=" + ultimoEstado + ", constanteResolucionMotivo=" + constanteResolucionMotivo + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + (this.numeroResolucion != null ? this.numeroResolucion.hashCode() : 0);
        hash = 43 * hash + (this.idResolucion != null ? this.idResolucion.hashCode() : 0);
        hash = 43 * hash + (this.numeroControl != null ? this.numeroControl.hashCode() : 0);
        hash = 43 * hash + (this.fechaRegistro != null ? this.fechaRegistro.hashCode() : 0);
        hash = 43 * hash + this.ultimoEstado;
        hash = 43 * hash + (this.constanteResolucionMotivo != null ? this.constanteResolucionMotivo.hashCode() : 0);
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
        final MultaDocumento other = (MultaDocumento) obj;
        if ((this.numeroResolucion == null) ? (other.numeroResolucion != null) : !this.numeroResolucion.equals(other.numeroResolucion)) {
            return false;
        }
        if (this.idResolucion != other.idResolucion && (this.idResolucion == null || !this.idResolucion.equals(other.idResolucion))) {
            return false;
        }
        if ((this.numeroControl == null) ? (other.numeroControl != null) : !this.numeroControl.equals(other.numeroControl)) {
            return false;
        }
        if (this.fechaRegistro != other.fechaRegistro && (this.fechaRegistro == null || !this.fechaRegistro.equals(other.fechaRegistro))) {
            return false;
        }
        if (this.ultimoEstado != other.ultimoEstado) {
            return false;
        }
        if ((this.constanteResolucionMotivo == null) ? (other.constanteResolucionMotivo != null) : !this.constanteResolucionMotivo.equals(other.constanteResolucionMotivo)) {
            return false;
        }
        return true;
    }

    public EstadoMultaEnum getSituacionIcep() {
        for (EstadoMultaEnum estado : EstadoMultaEnum.values()) {
            if (estado.getValor() == ultimoEstado) {
                return estado;
            }
        }
        return null;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public Double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(Double montoTotal) {
        this.montoTotal = montoTotal;
    }
}
