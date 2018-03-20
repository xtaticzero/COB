package mx.gob.sat.siat.cob.seguimiento.dto.catalogos;

import java.io.Serializable;

import java.util.Date;

public class AreaAdscripcion  implements Serializable{
    private static final long serialVersionUID = -5197691180506547942L;
    
    private Long idAreaAdscripcion;
    private String nombre;
    private String descripcion;
    private Date   fechaInicio;
    private Date   fechaFin;
    private Long   orden;
    private String fechaFinStr;
    private String situacion;
    
    public AreaAdscripcion() {
        super();
    }
    
    /**
     *
     * @param idAreaAdscripcion
     */
    public void setIdAreaAdscripcion(Long idAreaAdscripcion) {
        this.idAreaAdscripcion = idAreaAdscripcion;
    }
    
    /**
     *
     * @return
     */
    public Long getIdAreaAdscripcion() {
        return idAreaAdscripcion;
    }
    
    /**
     *
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     *
     * @return
     */
    public String getNombre() {
        return nombre;
    }

    /**
     *
     * @param descripcion
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     *
     * @return
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     *
     * @param fechaInicio
     */
    public void setFechaInicio(Date fechaInicio) {
        if (fechaInicio != null) {
            this.fechaInicio = (Date) fechaInicio.clone();
        }else{
            this.fechaInicio = null;
        }
    }

    /**
     *
     * @return
     */
    public Date getFechaInicio() {
        if(fechaInicio!=null){
        return (Date) fechaInicio.clone();
        }
        return null;
    }

    /**
     *
     * @param fechaFin
     */
    public void setFechaFin(Date fechaFin) {
        if (fechaFin != null) {
            this.fechaFin = (Date) fechaFin.clone();
        }else{
            this.fechaInicio = null;
        }
    }

    /**
     *
     * @return
     */
    public Date getFechaFin() {
        if(fechaFin!=null){
        return (Date) fechaFin.clone();
        }
        return null;
    }

    /**
     *
     * @param orden
     */
    public void setOrden(Long orden) {
        this.orden = orden;
    }

    /**
     *
     * @return
     */
    public Long getOrden() {
        return orden;
    }
    
    /**
     *
     * @param fechaFinStr
     */
    public void setFechaFinStr(String fechaFinStr) {
        this.fechaFinStr = fechaFinStr;
    }
    
    /**
     *
     * @return
     */
    public String getFechaFinStr() {
        return fechaFinStr;
    }
    
    /**
     *
     * @param situacion
     */
    public void setSituacion(String situacion) {
        this.situacion = situacion;
    }
    
    /**
     *
     * @return
     */
    public String getSituacion() {
        return situacion;
    }

}
