package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

import java.io.Serializable;

import java.util.Date;


public class IntentoDTO implements Serializable{

    @SuppressWarnings("compatibility:-7336934695312089138")
    private static final long serialVersionUID = -6021186302021469821L;
    private Integer id;
    private Integer idEjecucion;
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

    public void setIdEjecucion(Integer idEjecucion) {
        this.idEjecucion = idEjecucion;
    }

    public Integer getIdEjecucion() {
        return idEjecucion;
    }
}
