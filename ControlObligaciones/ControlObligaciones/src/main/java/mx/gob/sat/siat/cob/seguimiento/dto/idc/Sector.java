package mx.gob.sat.siat.cob.seguimiento.dto.idc;

import java.io.Serializable;
import java.util.Date;


public class Sector implements Serializable {

    /**
     *
     */
    public static final String NOMBRE_TABLA = "CBZC_SECTOR";

    @SuppressWarnings("compatibility:1947472121373213655")
    private static final long serialVersionUID = -5344683092564783940L;
    private Long idsector;
    private String nombre;
    private String descripcion;
    private Long orden;
    private Date fechaInicio;
    private Date fechaFin;


    /**
     *
     */
    public Sector() {
        super();
    }

    /**
     *
     * @param idsector
     */
    public void setIdsector(Long idsector) {
        this.idsector = idsector;
    }

    /**
     *
     * @return
     */
    public Long getIdsector() {
        return idsector;
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
        if(fechaInicio==null){
            return null;
        }else{
            return (Date)fechaInicio.clone();
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
        if(fechaFin==null){
            return null;
        }else{
            return (Date)fechaFin.clone();
        }
    }
}
