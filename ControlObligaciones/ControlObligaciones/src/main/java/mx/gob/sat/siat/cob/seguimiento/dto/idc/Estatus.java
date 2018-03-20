package mx.gob.sat.siat.cob.seguimiento.dto.idc;

import java.io.Serializable;
import java.util.Date;


public class Estatus implements Serializable {

    @SuppressWarnings("compatibility:-1840517385197212455")
    private static final long serialVersionUID = -8890830707185827612L;

    private Long idEstatus;
    private Long idTabla;
    private String nombre;
    private String constanteJava;
    private String descripcion;
    private Date fechaInicio;
    private Date fechaFin;

    /**
     *
     */
    public Estatus() {
        super();
    }

    /**
     *
     * @param idEstatus
     */
    public Estatus(Long idEstatus) {
        super();
        this.idEstatus = idEstatus;
    }


    /**
     *
     * @param idEstatus
     */
    public void setIdEstatus(Long idEstatus) {
        this.idEstatus = idEstatus;
    }

    /**
     *
     * @return
     */
    public Long getIdEstatus() {
        return idEstatus;
    }

    /**
     *
     * @param idTabla
     */
    public void setIdTabla(Long idTabla) {
        this.idTabla = idTabla;
    }

    /**
     *
     * @return
     */
    public Long getIdTabla() {
        return idTabla;
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
     * @param constanteJava
     */
    public void setConstanteJava(String constanteJava) {
        this.constanteJava = constanteJava;
    }

    /**
     *
     * @return
     */
    public String getConstanteJava() {
        return constanteJava;
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
        if(fechaInicio!=null){
            this.fechaInicio = (Date)fechaInicio.clone();
        }
    }

    /**
     *
     * @return
     */
    public Date getFechaInicio() {
        if(fechaInicio!=null){
            return (Date) fechaInicio.clone();
        }else{
            return null;
        }
    }

    /**
     *
     * @param fechaFin
     */
    public void setFechaFin(Date fechaFin) {
        if(fechaFin!=null){
            this.fechaFin = (Date)fechaFin.clone();
        }
    }

    /**
     *
     * @return
     */
    public Date getFechaFin() {
        if(fechaFin!=null){
            return (Date) fechaFin.clone();
        }else{
            return null;
        }
    }
}
