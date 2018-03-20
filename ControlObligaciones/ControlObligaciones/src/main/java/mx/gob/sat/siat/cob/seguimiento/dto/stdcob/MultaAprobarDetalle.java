package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

import java.io.Serializable;

/**
 *
 * @author root
 */
public class MultaAprobarDetalle implements Serializable{
    private Integer claveObligacion;
    private String descripcionObligacion;
    private long monto;
    private String periodo;
    private String ejercicio;

    public Integer getClaveObligacion() {
        return claveObligacion;
    }

    public void setClaveObligacion(Integer claveObligacion) {
        this.claveObligacion = claveObligacion;
    }

    public String getDescripcionObligacion() {
        return descripcionObligacion;
    }

    public void setDescripcionObligacion(String descripcionObligacion) {
        this.descripcionObligacion = descripcionObligacion;
    }

    public long getMonto() {
        return monto;
    }

    public void setMonto(long monto) {
        this.monto = monto;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getEjercicio() {
        return ejercicio;
    }

    public void setEjercicio(String ejercicio) {
        this.ejercicio = ejercicio;
    }
    
}
