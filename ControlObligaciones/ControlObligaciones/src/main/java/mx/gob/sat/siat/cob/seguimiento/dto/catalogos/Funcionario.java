package mx.gob.sat.siat.cob.seguimiento.dto.catalogos;

import java.io.Serializable;

import java.util.Date;

public class Funcionario implements Serializable{
    private static final long serialVersionUID = -5197691180506547942L;
    
    private String numeroEmpleado;
    private String nombreFuncionario;
    private String correoElectronicoFuncionario;
    private String correoElectronicoAlterno;
    private Date fechaInicio;
    private Date fechaFin;
    private String fechaFinStr;
    private String situacion;
    private Long areaDeAdscripcion;
    private String descAreaAdscripcion;
    private String descripcionCargo;
    
    public Funcionario() {
        super();
    }

    public void setNumeroEmpleado(String numeroEmpleado) {
        this.numeroEmpleado = numeroEmpleado;
    }

    public String getNumeroEmpleado() {
        return numeroEmpleado;
    }

    public void setNombreFuncionario(String nombreFuncionario) {
        this.nombreFuncionario = nombreFuncionario;
    }

    public String getNombreFuncionario() {
        return nombreFuncionario;
    }

    public void setCorreoElectronicoFuncionario(String correoElectronicoFuncionario) {
        this.correoElectronicoFuncionario = correoElectronicoFuncionario;
    }

    public String getCorreoElectronicoFuncionario() {
        return correoElectronicoFuncionario;
    }

    public void setCorreoElectronicoAlterno(String correoElectronicoAlterno) {
        this.correoElectronicoAlterno = correoElectronicoAlterno;
    }

    public String getCorreoElectronicoAlterno() {
        return correoElectronicoAlterno;
    }

    public void setFechaInicio(Date fechaInicio) {
        if (fechaInicio != null) {
            this.fechaInicio = (Date) fechaInicio.clone();
        }else{
            this.fechaInicio = null;
        }
    }

    public Date getFechaInicio() {
        if(fechaInicio!=null){
        return (Date) fechaInicio.clone();
        }
        return null;
    }

    public void setFechaFin(Date fechaFin) {
        if (fechaFin != null) {
            this.fechaFin = (Date) fechaFin.clone();
        }else{
            this.fechaInicio = null;
        }
    }

    public Date getFechaFin() {
        if(fechaFin!=null){
        return (Date) fechaFin.clone();
        }
        return null;
    }

    public void setFechaFinStr(String fechaFinStr) {
        this.fechaFinStr = fechaFinStr;
    }

    public String getFechaFinStr() {
        return fechaFinStr;
    }

    public void setSituacion(String situacion) {
        this.situacion = situacion;
    }

    public String getSituacion() {
        return situacion;
    }

    public void setAreaDeAdscripcion(Long areaDeAdscripcion) {
        this.areaDeAdscripcion = areaDeAdscripcion;
    }

    public Long getAreaDeAdscripcion() {
        return areaDeAdscripcion;
    }

    public void setDescAreaAdscripcion(String descAreaAdscripcion) {
        this.descAreaAdscripcion = descAreaAdscripcion;
    }

    public String getDescAreaAdscripcion() {
        return descAreaAdscripcion;
    }

    public void setDescripcionCargo(String descripcionCargo) {
        this.descripcionCargo = descripcionCargo;
    }

    public String getDescripcionCargo() {
        return descripcionCargo;
    }
}
