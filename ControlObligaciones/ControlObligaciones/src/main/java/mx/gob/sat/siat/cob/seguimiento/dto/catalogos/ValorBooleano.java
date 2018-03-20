package mx.gob.sat.siat.cob.seguimiento.dto.catalogos;

import java.io.Serializable;
import java.util.Date;


public class ValorBooleano implements Serializable {
    private static final long serialVersionUID = -3952207764953901617L;
    
    private Long idValorBooleano;
    private String nombre;
    private String descripcion;
    private Date fechaInicio;
    private Date fechaFin;
    private Long orden;
        
    /**
     *
     */
    public ValorBooleano() {
        super();
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
     * @param idValorBooleano
     */
    public void setIdValorBooleano(Long idValorBooleano) {
        this.idValorBooleano = idValorBooleano;
    }

    /**
     *
     * @return
     */
    public Long getIdValorBooleano() {
        return idValorBooleano;
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
}
