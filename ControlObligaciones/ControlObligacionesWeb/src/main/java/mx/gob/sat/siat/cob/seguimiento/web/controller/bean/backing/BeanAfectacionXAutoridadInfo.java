/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing;

import java.io.Serializable;

/**
 *
 * @author Marco Murakami
 */
public class BeanAfectacionXAutoridadInfo implements Serializable{
    
    private String numeroControl;
    private String rfc;
    private String fechaEmision;
    private String fechaNotificacion;
    private String fechaVencimientoDocto;
    private String estadoDocto;
    private int claveObligacion;
    private String descObligacion;
    private String edoObligacion;
    private int tipoMulta;
    private int resolucion;
    private String tipoDocto;
    private String motivoCaptura;

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
    public String getRfc() {
        return rfc;
    }

    /**
     *
     * @param rfc
     */
    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    /**
     *
     * @return
     */
    public String getFechaEmision() {
        return fechaEmision;
    }

    /**
     *
     * @param fechaEmision
     */
    public void setFechaEmision(String fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    /**
     *
     * @return
     */
    public String getFechaNotificacion() {
        return fechaNotificacion;
    }

    /**
     *
     * @param fechaNotificacion
     */
    public void setFechaNotificacion(String fechaNotificacion) {
        this.fechaNotificacion = fechaNotificacion;
    }

    /**
     *
     * @return
     */
    public String getFechaVencimientoDocto() {
        return fechaVencimientoDocto;
    }

    /**
     *
     * @param fechaVencimientoDocto
     */
    public void setFechaVencimientoDocto(String fechaVencimientoDocto) {
        this.fechaVencimientoDocto = fechaVencimientoDocto;
    }

    /**
     *
     * @return
     */
    public String getEstadoDocto() {
        return estadoDocto;
    }

    /**
     *
     * @param estadoDocto
     */
    public void setEstadoDocto(String estadoDocto) {
        this.estadoDocto = estadoDocto;
    }

    /**
     *
     * @return
     */
    public int getClaveObligacion() {
        return claveObligacion;
    }

    /**
     *
     * @param claveObligacion
     */
    public void setClaveObligacion(int claveObligacion) {
        this.claveObligacion = claveObligacion;
    }

    /**
     *
     * @return
     */
    public String getDescObligacion() {
        return descObligacion;
    }

    /**
     *
     * @param descObligacion
     */
    public void setDescObligacion(String descObligacion) {
        this.descObligacion = descObligacion;
    }

    /**
     *
     * @return
     */
    public String getEdoObligacion() {
        return edoObligacion;
    }

    /**
     *
     * @param edoObligacion
     */
    public void setEdoObligacion(String edoObligacion) {
        this.edoObligacion = edoObligacion;
    }

    /**
     *
     * @return
     */
    public int getTipoMulta() {
        return tipoMulta;
    }

    /**
     *
     * @param tipoMulta
     */
    public void setTipoMulta(int tipoMulta) {
        this.tipoMulta = tipoMulta;
    }

    /**
     *
     * @return
     */
    public int getResolucion() {
        return resolucion;
    }

    /**
     *
     * @param resolucion
     */
    public void setResolucion(int resolucion) {
        this.resolucion = resolucion;
    }

    /**
     *
     * @return
     */
    public String getTipoDocto() {
        return tipoDocto;
    }

    /**
     *
     * @param tipoDocto
     */
    public void setTipoDocto(String tipoDocto) {
        this.tipoDocto = tipoDocto;
    }

    /**
     *
     * @return
     */
    public String getMotivoCaptura() {
        return motivoCaptura;
    }

    /**
     *
     * @param motivoCaptura
     */
    public void setMotivoCaptura(String motivoCaptura) {
        this.motivoCaptura = motivoCaptura;
    }
}
