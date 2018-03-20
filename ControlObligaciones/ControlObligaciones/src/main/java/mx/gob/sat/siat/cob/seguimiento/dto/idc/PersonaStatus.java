package mx.gob.sat.siat.cob.seguimiento.dto.idc;

import java.io.Serializable;
import java.sql.Date;


public class PersonaStatus implements Serializable {
    @SuppressWarnings("compatibility:3812013158065006735")
    private static final long serialVersionUID = 676319421059111262L;

    private Long idStatus;
    private String nombre;
    private String desrcripcion;
    private Long ordenSec;
    private Date fechaInicio;
    private Date fechaFin;


    /**
     *
     */
    public PersonaStatus() {
        super();
    }

    /**
     *
     * @return
     */
    public Long getIdStatus() {
        return idStatus;
    }

    /**
     *
     * @param idStatus
     */
    public void setIdStatus(Long idStatus) {
        this.idStatus = idStatus;
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
    public String getDesrcripcion() {
        return desrcripcion;
    }

    /**
     *
     * @param desrcripcion
     */
    public void setDesrcripcion(String desrcripcion) {
        this.desrcripcion = desrcripcion;
    }

    /**
     *
     * @return
     */
    public Long getOrdenSec() {
        return ordenSec;
    }

    /**
     *
     * @param ordenSec
     */
    public void setOrdenSec(Long ordenSec) {
        this.ordenSec = ordenSec;
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
}
