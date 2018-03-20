package mx.gob.sat.siat.cob.seguimiento.dto.idc;

import java.io.Serializable;
import java.util.Date;


public class AsociacionPersona implements Serializable {


    @SuppressWarnings("compatibility:-6882788019356897693")
    private static final long serialVersionUID = 4788356600213530294L;

    private Long idPerAsociacion;
    private Long idPersonaTipo;
    private String nombre;
    private String descripcion;
    private Long oredenSec;
    private Date fechaInicio;
    private Date fechaFin;

    /**
     *
     */
    public AsociacionPersona() {
        super();
    }


    /**
     *
     * @return
     */
    public Long getIdPerAsociacion() {
        return idPerAsociacion;
    }

    /**
     *
     * @param idPerAsociacion
     */
    public void setIdPerAsociacion(Long idPerAsociacion) {
        this.idPerAsociacion = idPerAsociacion;
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
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
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
     * @param descripcion
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     *
     * @return
     */
    public Long getOredenSec() {
        return oredenSec;
    }

    /**
     *
     * @param oredenSec
     */
    public void setOredenSec(Long oredenSec) {
        this.oredenSec = oredenSec;
    }

    /**
     *
     * @return
     */
    public Date getFechaInicio() {
        if(fechaInicio==null){
            return null;
        }else{
            return (Date)fechaInicio.clone();
        }
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
    public Date getFechaFin() {
        if(fechaFin==null){
            return null;
        }else{
            return (Date)fechaFin.clone();
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
    public Long getIdPersonaTipo() {
        return idPersonaTipo;
    }

    /**
     *
     * @param idPersonaTipo
     */
    public void setIdPersonaTipo(Long idPersonaTipo) {
        this.idPersonaTipo = idPersonaTipo;
    }
}
