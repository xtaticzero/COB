package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

import java.util.Date;

public class EstadoGenera {

    private int idEstadoGeneracion;
    private String nombre;
    private String descripcion;
    private Date fechaInicio;
    private Date fechaFin;
    private int orden;

    /**
     *
     */
    public EstadoGenera() {
    }

    /**
     *
     * @param idEstadoGeneracion
     * @param nombre
     * @param descripcion
     * @param fechaInicio
     * @param FechaFin
     * @param orden
     */
    public EstadoGenera(int idEstadoGeneracion, String nombre, String descripcion, Date fechaInicio, Date fechaFin,
            int orden) {
        super();
        this.idEstadoGeneracion = idEstadoGeneracion;
        this.nombre = nombre;
        this.descripcion = descripcion;
        if (fechaInicio != null) {
            this.fechaInicio = (Date) fechaInicio.clone();
        }
        if (fechaFin != null) {
            this.fechaFin = (Date) fechaFin.clone();
        }
        this.orden = orden;
    }

    /**
     *
     * @param idEstadoGeneracion
     */
    public void setIdEstadoGeneracion(int idEstadoGeneracion) {
        this.idEstadoGeneracion = idEstadoGeneracion;
    }

    /**
     *
     * @return
     */
    public int getIdEstadoGeneracion() {
        return idEstadoGeneracion;
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
        if (fechaInicio != null) {
            return (Date) fechaInicio.clone();
        } else {
            return null;
        }
    }

    /**
     *
     * @param FechaFin
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
        if (fechaFin != null) {
            return (Date) fechaFin.clone();
        } else {
            return null;
        }
    }

    /**
     *
     * @param orden
     */
    public void setOrden(int orden) {
        this.orden = orden;
    }

    /**
     *
     * @return
     */
    public int getOrden() {
        return orden;
    }
}
