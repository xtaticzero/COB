/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

import java.io.Serializable;

/**
 *
 * @author root
 */
public class AfectacionXAutoridad implements Serializable {
   
    private static final long serialVersionUID = 3995462063655855867L;
    private boolean selected;
    private String numeroControl;
    private String nombre;
    private String rfc;
    private String fechaRegistro;
    private String fechaNotificacion;
    private String fechaVencimiento;
    private String estado;
    private int claveObligacion;
    private String obDescripcion;
    private String solventado;
    private String numResolucion;
    private String motivo;
    private String tipoDoc;
    private String tipoMedio;
    private String ejercicio;
    private String periodo;
    private String admonLocal;
    private String fechaNoTrabajado;
    private String fechaNoLocalizado;
    private String fechaCitatorio;

    /**
     *
     * @return
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     *
     * @param selected
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
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
    public String getFechaRegistro() {
        return fechaRegistro;
    }

    /**
     *
     * @param fechaRegistro
     */
    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
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
    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    /**
     *
     * @param fechaVencimiento
     */
    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    /**
     *
     * @return
     */
    public String getEstado() {
        return estado;
    }

    /**
     *
     * @param estado
     */
    public void setEstado(String estado) {
        this.estado = estado;
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
    public String getObDescripcion() {
        return obDescripcion;
    }

    /**
     *
     * @param obDescripcion
     */
    public void setObDescripcion(String obDescripcion) {
        this.obDescripcion = obDescripcion;
    }

    /**
     *
     * @return
     */
    public String getSolventado() {
        return solventado;
    }

    /**
     *
     * @param solventado
     */
    public void setSolventado(String solventado) {
        this.solventado = solventado;
    }

    /**
     *
     * @return
     */
    public String getNumResolucion() {
        return numResolucion;
    }

    /**
     *
     * @param numResolucion
     */
    public void setNumResolucion(String numResolucion) {
        this.numResolucion = numResolucion;
    }

    /**
     *
     * @return
     */
    public String getMotivo() {
        return motivo;
    }

    /**
     *
     * @param motivo
     */
    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    /**
     *
     * @return
     */
    public String getTipoDoc() {
        return tipoDoc;
    }

    /**
     *
     * @param tipoDoc
     */
    public void setTipoDoc(String tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setTipoMedio(String tipoMedio) {
        this.tipoMedio = tipoMedio;
    }

    public String getTipoMedio() {
        return tipoMedio;
    }

    public void setEjercicio(String ejercicio) {
        this.ejercicio = ejercicio;
    }

    public String getEjercicio() {
        return ejercicio;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setAdmonLocal(String admonLocal) {
        this.admonLocal = admonLocal;
    }

    public String getAdmonLocal() {
        return admonLocal;
    }

    /**
     * @return the FechaNoTrabajado
     */
    public String getFechaNoTrabajado() {
        return fechaNoTrabajado;
    }

    /**
     * @param FechaNoTrabajado the FechaNoTrabajado to set
     */
    public void setFechaNoTrabajado(String fechaNoTrabajado) {
        this.fechaNoTrabajado = fechaNoTrabajado;
    }

    /**
     * @return the fechaNoLocalizado
     */
    public String getFechaNoLocalizado() {
        return fechaNoLocalizado;
    }

    /**
     * @param fechaNoLocalizado the fechaNoLocalizado to set
     */
    public void setFechaNoLocalizado(String fechaNoLocalizado) {
        this.fechaNoLocalizado = fechaNoLocalizado;
    }
    
    /**
     * @return the fechaCitatorio
     */
    public String getFechaCitatorio() {
        return fechaCitatorio;
    }
    
    /**
     * @param fechaCitatorio the fechaCitatorio to set
     */
    public void setFechaCitatorio(String fechaCitatorio) {
        this.fechaCitatorio = fechaCitatorio;
    }
    
}
