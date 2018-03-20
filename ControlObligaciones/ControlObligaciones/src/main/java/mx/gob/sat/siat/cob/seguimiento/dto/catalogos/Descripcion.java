package mx.gob.sat.siat.cob.seguimiento.dto.catalogos;

import java.io.Serializable;

import java.util.Date;

public class Descripcion implements Serializable{
    private static final long serialVersionUID = -5197691180506547942L;
    
    private Long   idDescripcion;
    private String descripcion;
    private Date   fechaInicio;
    private Date   fechaFin;
    private Long   orden;

    public Descripcion() {
        super();
    }

    public void setIdDescripcion(Long idDescripcion) {
        this.idDescripcion = idDescripcion;
    }

    public Long getIdDescripcion() {
        return idDescripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
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

    public void setOrden(Long orden) {
        this.orden = orden;
    }

    public Long getOrden() {
        return orden;
    }
}
