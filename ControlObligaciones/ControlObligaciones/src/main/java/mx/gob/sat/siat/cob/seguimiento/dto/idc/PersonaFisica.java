package mx.gob.sat.siat.cob.seguimiento.dto.idc;

import java.io.Serializable;

public class PersonaFisica implements Serializable {

    @SuppressWarnings("compatibility:7406823579560655200")
    private static final long serialVersionUID = -5535982004132415454L;


    private Persona persona;
    private String nombre;
    private String apPaterno;
    private String apMaterno;
    private String cargo;
    private String curp;
    private Long idPersona;

    /**
     *
     */
    public PersonaFisica() {
        super();
    }

    /**
     *
     * @param persona
     */
    public PersonaFisica(Persona persona) {
        this.persona = persona;
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
    public String getApPaterno() {
        return apPaterno;
    }

    /**
     *
     * @param apPaterno
     */
    public void setApPaterno(String apPaterno) {
        this.apPaterno = apPaterno;
    }

    /**
     *
     * @return
     */
    public String getApMaterno() {
        return apMaterno;
    }

    /**
     *
     * @param apMaterno
     */
    public void setApMaterno(String apMaterno) {
        this.apMaterno = apMaterno;
    }

    /**
     *
     * @return
     */
    public String getCurp() {
        return curp;
    }

    /**
     *
     * @param curp
     */
    public void setCurp(String curp) {
        this.curp = curp;
    }


    /**
     *
     * @return
     */
    public Persona getPersona() {
        return persona;
    }

    /**
     *
     * @param persona
     */
    public void setPersona(Persona persona) {
        this.persona = persona;
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
     * @return
     */
    public String getCargo() {
        return cargo;
    }

    /**
     *
     * @param cargo
     */
    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
}
