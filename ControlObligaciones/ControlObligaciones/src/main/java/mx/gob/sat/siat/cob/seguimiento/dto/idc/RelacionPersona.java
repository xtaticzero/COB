package mx.gob.sat.siat.cob.seguimiento.dto.idc;

import java.io.Serializable;
import java.util.Date;


public class RelacionPersona implements Serializable {

    @SuppressWarnings("compatibility:5797387070304231927")
    private static final long serialVersionUID = 6903178020380452679L;

    private Long idPerRelacion;
    private String nombre;
    private String descripcion;
    private Long oredenSec;
    private Date fechaInicio;
    private Date fechaFin;
    private Long idPersona1;
    private Long idPersona2;

    /**
     *
     */
    public RelacionPersona() {
        super();
    }

    /**
     *
     * @return
     */
    public Long getIdPerRelacion() {
        return idPerRelacion;
    }

    /**
     *
     * @param idPerRelacion
     */
    public void setIdPerRelacion(Long idPerRelacion) {
        this.idPerRelacion = idPerRelacion;
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
    public Long getIdPersona1() {
        return idPersona1;
    }

    /**
     *
     * @param idPersona1
     */
    public void setIdPersona1(Long idPersona1) {
        this.idPersona1 = idPersona1;
    }

    /**
     *
     * @return
     */
    public Long getIdPersona2() {
        return idPersona2;
    }

    /**
     *
     * @param idPersona2
     */
    public void setIdPersona2(Long idPersona2) {
        this.idPersona2 = idPersona2;
    }
}
