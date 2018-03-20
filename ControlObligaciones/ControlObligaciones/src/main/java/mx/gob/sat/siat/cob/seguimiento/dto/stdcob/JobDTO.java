package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

import java.io.Serializable;

import java.util.Date;

public class JobDTO implements Serializable {

    @SuppressWarnings("compatibility:-1635354973005265674")
    private static final long serialVersionUID = 6033665197696620836L;
    private Integer id;
    private String nombre;
    private String descripcion;
    private Integer estado;
    private Integer intento;
    private Date fechaInicio;
    private Date fechaFin;
    private String duracion;
    private String duracionAcumulada;
    private String intentosMax;
    private Integer prioridad;
    private Date siguienteEjecucion;
    private String dependencias;
    private String horaInicio;
    private String horaFin;
    private String horaSiguienteEjecucion;
    private String fechaInicioStr;
    private String fechaFinStr;
    private String fechaSiguienteEjecucionStr;

    public JobDTO() {
        super();
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setIntento(Integer intento) {
        this.intento = intento;
    }

    public Integer getIntento() {
        return intento;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio != null ? new Date(fechaInicio.getTime()) : null;
    }

    public Date getFechaInicio() {
        return this.fechaInicio != null ? new Date(this.fechaInicio.getTime()) : null;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin != null ? new Date(fechaFin.getTime()) : null;
    }

    public Date getFechaFin() {
        return this.fechaFin != null ? new Date(this.fechaFin.getTime()) : null;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracionAcumulada(String duracionAcumulada) {
        this.duracionAcumulada = duracionAcumulada;
    }

    public String getDuracionAcumulada() {
        return duracionAcumulada;
    }

    public void setIntentosMax(String intentosMax) {
        this.intentosMax = intentosMax;
    }

    public String getIntentosMax() {
        return intentosMax;
    }

    public void setPrioridad(Integer prioridad) {
        this.prioridad = prioridad;
    }

    public Integer getPrioridad() {
        return prioridad;
    }

    public void setSiguienteEjecucion(Date siguienteEjecucion) {
        this.siguienteEjecucion = siguienteEjecucion != null ? new Date(siguienteEjecucion.getTime()) : null;
    }

    public Date getSiguienteEjecucion() {
        return siguienteEjecucion == null ? null : (Date) siguienteEjecucion.clone();
    }

    public void setDependencias(String dependencias) {
        this.dependencias = dependencias;
    }

    public String getDependencias() {
        return dependencias;
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

    public void setHoraSiguienteEjecucion(String horaSiguienteEjecucion) {
        this.horaSiguienteEjecucion = horaSiguienteEjecucion;
    }

    public String getHoraSiguienteEjecucion() {
        return horaSiguienteEjecucion;
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

    public void setFechaSiguienteEjecucionStr(String fechaSiguienteEjecucionStr) {
        this.fechaSiguienteEjecucionStr = fechaSiguienteEjecucionStr;
    }

    public String getFechaSiguienteEjecucionStr() {
        return fechaSiguienteEjecucionStr;
    }
}
