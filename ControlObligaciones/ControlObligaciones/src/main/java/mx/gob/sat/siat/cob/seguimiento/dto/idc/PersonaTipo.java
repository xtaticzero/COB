package mx.gob.sat.siat.cob.seguimiento.dto.idc;

import java.io.Serializable;
import java.util.Date;

public class PersonaTipo implements Serializable {

    @SuppressWarnings("compatibility:5905758177205107317")
    private static final long serialVersionUID = -2446852056339089289L;
    private Long idPersonaTipo;
    private String nombre;
    private Long ordenSec;
    private Date fechaInicio;
    private Date fechaFin;

    /**
     *
     */
    public PersonaTipo() {
        super();
    }

    /**
     *
     * @param idPersonaTipo
     */
    public PersonaTipo(Long idPersonaTipo) {
        super();
        this.idPersonaTipo=idPersonaTipo;
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
        if (fechaInicio != null) {
            return (Date) fechaInicio.clone();
        }
        return null;
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
    public Date getFechaFin() {
        if (fechaFin != null) {
            return (Date) fechaFin.clone();
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
}
