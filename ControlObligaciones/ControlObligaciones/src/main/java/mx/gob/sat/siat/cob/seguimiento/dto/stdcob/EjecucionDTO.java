package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

import java.io.Serializable;

import java.util.Date;


public class EjecucionDTO implements Serializable {
    @SuppressWarnings("compatibility:-7684725994176671844")
    private static final long serialVersionUID = 5690962160805339136L;
    
   private Integer id;
   private Integer idProceso;
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

    public void setIdProceso(Integer idProceso) {
        this.idProceso = idProceso;
    }

    public Integer getIdProceso() {
        return idProceso;
    }

    public void setIntento(Integer intento) {
        this.intento = intento;
    }

    public Integer getIntento() {
        return intento;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio != null?(Date)inicio.clone():null;
    }

    public Date getInicio() {
        return inicio != null?(Date)inicio.clone():null;
    }

    public void setFin(Date fin) {
        this.fin = fin != null?(Date)fin.clone():null;
    }

    public Date getFin() {
        return fin != null?(Date)fin.clone():null;
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
