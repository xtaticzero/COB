package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

import java.io.Serializable;

public class ReporteAfectacionXAutoridadDTO implements Serializable {
    
    private static final long serialVersionUID = -5197691180506547942L;
    private String numeroControl;
    private String fechaRegistro;
    private String fechaNotificacion;
    private String fechaVencimiento;
    private String estado;
    private int    claveObligacion;
    private String obDescripcion;
    private String ejercicio;
    private String periodo;
    private String tipoDocumento;
    private String estadoDocumento;
    private String fechaNoTrabajado;
    private String fechaNoLocalizado;
    
    public ReporteAfectacionXAutoridadDTO() {
        super();
    }

    public void setNumeroControl(String numeroControl) {
        this.numeroControl = numeroControl;
    }

    public String getNumeroControl() {
        return numeroControl;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaNotificacion(String fechaNotificacion) {
        this.fechaNotificacion = fechaNotificacion;
    }

    public String getFechaNotificacion() {
        return fechaNotificacion;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEstado() {
        return estado;
    }

    public void setClaveObligacion(int claveObligacion) {
        this.claveObligacion = claveObligacion;
    }

    public int getClaveObligacion() {
        return claveObligacion;
    }

    public void setObDescripcion(String obDescripcion) {
        this.obDescripcion = obDescripcion;
    }

    public String getObDescripcion() {
        return obDescripcion;
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

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public String getEstadoDocumento() {
        return estadoDocumento;
    }

    public void setEstadoDocumento(String estadoDocumento) {
        this.estadoDocumento = estadoDocumento;
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
    
}
