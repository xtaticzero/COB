/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dto.arca;

import java.io.Serializable;

/**
 *
 * @author Juan
 */
public class Contribuyente implements Serializable {

    private static final long serialVersionUID = -5197691180506547943L;
    private String boId;
    private Direccion direccion;
    private String nombre;
    private String apPaterno;
    private String apMaterno;
    private String curp;
    private String compania;
    private String rfc;

    /**
     *
     */
    public Contribuyente() {
        super();
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
     * @param boId
     */
    public void setBoId(String boId) {
        this.boId = boId;
    }

    /**
     *
     * @return
     */
    public Direccion getDireccion() {
        return direccion;
    }

    /**
     *
     * @param direccion
     */
    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
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
    public String getCompania() {
        return compania;
    }

    /**
     *
     * @param compania
     */
    public void setCompania(String compania) {
        this.compania = compania;
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
}
