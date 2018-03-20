package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

import java.io.Serializable;

import java.util.Date;


public class BitacoraEjecucionDTO implements Serializable{

    @SuppressWarnings("compatibility:5336726916286176743")
    private static final long serialVersionUID = -4422881607128160843L;
    private Integer idEjecucion;
    private Integer intento;
    private Date inicio;
    private Date fin;
    private String duracion;
    private Integer estado;
    private String observaciones;
    private String horaInicio;
    private String horaFin;
    private String fechaInicioStr;
    private String fechaFinStr;

    public void setIdEjecucion(Integer idEjecucion) {
        this.idEjecucion = idEjecucion;
    }

    public Integer getIdEjecucion() {
        return idEjecucion;
    }

    public void setIntento(Integer intento) {
        this.intento = intento;
    }

    public Integer getIntento() {
        return intento;
    }

    public void setInicio(Date inicio) {
        if(inicio!=null){
            this.inicio = (Date)inicio.clone();
        }
    }

    public Date getInicio() {
        if(inicio==null){
            return null;
        }else{
            return (Date)inicio.clone();
        }
    }

    public void setFin(Date fin) {
        if(fin!=null){
            this.fin = (Date)fin.clone();
        }
    }

    public Date getFin() {
        if(fin==null){
            return null;
        }else{
            return (Date)fin.clone();
        }
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getDuracion() {
        return duracion;
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

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setFechaInicioStr(String fechaInicioStr) {
        this.fechaInicioStr = fechaInicioStr;
    }

    public String getFechaInicioStr() {
        return fechaInicioStr;
    }

    public void setFechaFinStr(String fechaFinStr) {
        this.fechaFinStr = fechaFinStr;
    }

    public String getFechaFinStr() {
        return fechaFinStr;
    }
}
