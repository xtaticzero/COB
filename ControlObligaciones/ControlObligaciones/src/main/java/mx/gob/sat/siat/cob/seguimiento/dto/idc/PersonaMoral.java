package mx.gob.sat.siat.cob.seguimiento.dto.idc;

import java.io.Serializable;

public class PersonaMoral implements Serializable {


    @SuppressWarnings("compatibility:4477386099518627459")
    private static final long serialVersionUID = -5441493381359995251L;

    private Long idPersona;
    private String denominacion;
    private String nombreComercial;
    private String tipoSociedad;
    private Long idStatus;
    private Estatus status;

    /**
     *
     */
    public PersonaMoral() {
        super();
    }

    /**
     *
     * @return
     */
    public Long getIdPersona() {
        return idPersona;
    }

    /**
     *
     * @param idPersona
     */
    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }

    /**
     *
     * @return
     */
    public String getDenominacion() {
        return denominacion;
    }

    /**
     *
     * @param denominacion
     */
    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    /**
     *
     * @return
     */
    public String getNombreComercial() {
        return nombreComercial;
    }

    /**
     *
     * @param nombreComercial
     */
    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
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
    public Estatus getStatus() {
        return status;
    }

    /**
     *
     * @param status
     */
    public void setStatus(Estatus status) {
        this.status = status;
    }

    /**
     *
     * @return
     */
    public String getTipoSociedad() {
        return tipoSociedad;
    }

    /**
     *
     * @param tipoSociedad
     */
    public void setTipoSociedad(String tipoSociedad) {
        this.tipoSociedad = tipoSociedad;
    }
}
