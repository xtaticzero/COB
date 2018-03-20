package mx.gob.sat.siat.cob.seguimiento.dto.idc;

import java.io.Serializable;


public class Persona implements Serializable {


    @SuppressWarnings("compatibility:8854068051069653509")
    private static final long serialVersionUID = 1L;
    private Long idPersona;
    private String rfc;
    private Long idPersonaTipo;
    private Long idStatus;
    private Estatus estatus;
    private String boId;
    private Boolean perFisica;
    private PersonaFisica personaFisica;
    private PersonaMoral personaMoral;
    private Boolean contribuyente;
    private Boolean esSiat;
    private Boolean nuevo;
    private PersonaTipo personaTipo;
    private PersonaStatus personaStatus;

    /**
     *
     */
    public Persona() {
        super();
    }

    /**
     *
     * @param rfc
     */
    public Persona(String rfc) {
        super();
        this.rfc=rfc;
    }

    /**
     *
     * @return
     */
    public String getRfc() {
        return rfc;
    }

    /**
     *
     * @param rfc
     */
    public void setRfc(String rfc) {
        this.rfc = rfc;
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
    public PersonaStatus getPersonaStatus() {
        return personaStatus;
    }

    /**
     *
     * @param personaStatus
     */
    public void setPersonaStatus(PersonaStatus personaStatus) {
        this.personaStatus = personaStatus;
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
    public Long getIdPersona() {
        return idPersona;
    }

    /**
     *
     * @param nuevo
     */
    public void setNuevo(Boolean nuevo) {
        this.nuevo = nuevo;
    }

    /**
     *
     * @return
     */
    public Boolean getNuevo() {
        return nuevo;
    }

    /**
     *
     * @param boId
     */
    public void setBoId(String boId) {
        this.boId = boId;
    }

    /**
     *
     * @return
     */
    public String getBoId() {
        return boId;
    }

    /**
     *
     * @param contribuyente
     */
    public void setContribuyente(Boolean contribuyente) {
        this.contribuyente = contribuyente;
    }

    /**
     *
     * @return
     */
    public Boolean getContribuyente() {
        return contribuyente;
    }


    /**
     *
     * @param personaTipo
     */
    public void setPersonaTipo(PersonaTipo personaTipo) {
        this.personaTipo = personaTipo;
    }

    /**
     *
     * @return
     */
    public PersonaTipo getPersonaTipo() {
        return personaTipo;
    }


    /**
     *
     * @return
     */
    public Estatus getEstatus() {
        return estatus;
    }

    /**
     *
     * @param estatus
     */
    public void setEstatus(Estatus estatus) {
        this.estatus = estatus;
    }

    /**
     *
     * @return
     */
    public Boolean getEsSiat() {
        return esSiat;
    }

    /**
     *
     * @param esSiat
     */
    public void setEsSiat(Boolean esSiat) {
        this.esSiat = esSiat;
    }

    /**
     *
     * @return
     */
    public Boolean getPerFisica() {
        return perFisica;
    }

    /**
     *
     * @param perFisica
     */
    public void setPerFisica(Boolean perFisica) {
        this.perFisica = perFisica;
    }

    /**
     *
     * @return
     */
    public PersonaFisica getPersonaFisica() {
        return personaFisica;
    }

    /**
     *
     * @param personaFisica
     */
    public void setPersonaFisica(PersonaFisica personaFisica) {
        this.personaFisica = personaFisica;
    }

    /**
     *
     * @return
     */
    public PersonaMoral getPersonaMoral() {
        return personaMoral;
    }

    /**
     *
     * @param personaMoral
     */
    public void setPersonaMoral(PersonaMoral personaMoral) {
        this.personaMoral = personaMoral;
    }

}
