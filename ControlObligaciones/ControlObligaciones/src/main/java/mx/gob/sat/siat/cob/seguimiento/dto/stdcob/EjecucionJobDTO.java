package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

import java.io.Serializable;

import java.util.Date;


public class EjecucionJobDTO implements Serializable{
   @SuppressWarnings("compatibility:2700916176058118789")
   private static final long serialVersionUID = 1939169447524442442L;
   
   private Integer id;
   private Proceso proceso;
   private Integer intento;
   private Date inicio;
   private Date fin;
   private Integer estado;
   private String observaciones;


    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setProceso(Proceso proceso) {
        this.proceso = proceso;
    }

    public Proceso getProceso() {
        return proceso;
    }

    public void setIntento(Integer intento) {
        this.intento = intento;
    }

    public Integer getIntento() {
        return intento;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio != null ? new Date(inicio.getTime()) : null;
    }

    public Date getInicio() {
        return this.inicio != null ? new Date(this.inicio.getTime()) : null;
    }

    public void setFin(Date fin) {
        this.fin = fin != null ? new Date(fin.getTime()) : null;
    }

    public Date getFin() {
        return this.fin != null ? new Date(this.fin.getTime()) : null;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getObservaciones() {
        return observaciones;
    }
}
