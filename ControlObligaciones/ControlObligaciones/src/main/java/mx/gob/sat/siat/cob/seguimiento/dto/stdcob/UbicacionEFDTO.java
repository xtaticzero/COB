package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

import java.io.Serializable;

/**
 *
 * @author christian.ventura
 */
public class UbicacionEFDTO implements Serializable {

    private int claveEntidadFed;
    private int claveCrh;
    private String idPersona;
    private String calle;
    private String numeroExterior;
    private String numeroInterior;
    private String claveColonia;
    private String descripcionColonia;
    private String claveLocalidad;
    private String descripcionLocalidad;
    private String entreCalle1;
    private String entreCalle2;
    private String referenciaAdicionales;
    private String codigoPostal;
    private String claveMunicipio;

    /**
     * @return the claveEntidadFed
     */
    public int getClaveEntidadFed() {
        return claveEntidadFed;
    }

    /**
     * @param claveEntidadFed the claveEntidadFed to set
     */
    public void setClaveEntidadFed(int claveEntidadFed) {
        this.claveEntidadFed = claveEntidadFed;
    }

    /**
     * @return the claveCrh
     */
    public int getClaveCrh() {
        return claveCrh;
    }

    /**
     * @param claveCrh the claveCrh to set
     */
    public void setClaveCrh(int claveCrh) {
        this.claveCrh = claveCrh;
    }

    /**
     * @return the idPersona
     */
    public String getIdPersona() {
        return idPersona;
    }

    /**
     * @param idPersona the idPersona to set
     */
    public void setIdPersona(String idPersona) {
        this.idPersona = idPersona;
    }

    /**
     * @return the calle
     */
    public String getCalle() {
        return calle==null?"":calle;
    }

    /**
     * @param calle the calle to set
     */
    public void setCalle(String calle) {
        this.calle = calle;
    }

    /**
     * @return the numeroExterior
     */
    public String getNumeroExterior() {
        return numeroExterior==null?"":numeroExterior;
    }

    /**
     * @param numeroExterior the numeroExterior to set
     */
    public void setNumeroExterior(String numeroExterior) {
        this.numeroExterior = numeroExterior;
    }

    /**
     * @return the numeroInterior
     */
    public String getNumeroInterior() {
        return numeroInterior==null?"":numeroInterior;
    }

    /**
     * @param numeroInterior the numeroInterior to set
     */
    public void setNumeroInterior(String numeroInterior) {
        this.numeroInterior = numeroInterior;
    }

    /**
     * @return the claveColonia
     */
    public String getClaveColonia() {
        return claveColonia==null?"":claveColonia;
    }

    /**
     * @param claveColonia the claveColonia to set
     */
    public void setClaveColonia(String claveColonia) {
        this.claveColonia = claveColonia;
    }

    /**
     * @return the descripcionColonia
     */
    public String getDescripcionColonia() {
        return descripcionColonia==null?"":descripcionColonia;
    }

    /**
     * @param descripcionColonia the descripcionColonia to set
     */
    public void setDescripcionColonia(String descripcionColonia) {
        this.descripcionColonia = descripcionColonia;
    }

    /**
     * @return the claveLocalidad
     */
    public String getClaveLocalidad() {
        return claveLocalidad==null?"":claveLocalidad;
    }

    /**
     * @param claveLocalidad the claveLocalidad to set
     */
    public void setClaveLocalidad(String claveLocalidad) {
        this.claveLocalidad = claveLocalidad;
    }

    /**
     * @return the descripcionLocalidad
     */
    public String getDescripcionLocalidad() {
        return descripcionLocalidad==null?"":descripcionLocalidad;
    }

    /**
     * @param descripcionLocalidad the descripcionLocalidad to set
     */
    public void setDescripcionLocalidad(String descripcionLocalidad) {
        this.descripcionLocalidad = descripcionLocalidad;
    }

    /**
     * @return the entreCalle1
     */
    public String getEntreCalle1() {
        return entreCalle1==null?"":entreCalle1;
    }

    /**
     * @param entreCalle1 the entreCalle1 to set
     */
    public void setEntreCalle1(String entreCalle1) {
        this.entreCalle1 = entreCalle1;
    }

    /**
     * @return the entreCalle2
     */
    public String getEntreCalle2() {
        return entreCalle2==null?"":entreCalle2;
    }

    /**
     * @param entreCalle2 the entreCalle2 to set
     */
    public void setEntreCalle2(String entreCalle2) {
        this.entreCalle2 = entreCalle2;
    }

    /**
     * @return the referenciaAdicionales
     */
    public String getReferenciaAdicionales() {
        return referenciaAdicionales==null?"":referenciaAdicionales;
    }

    /**
     * @param referenciaAdicionales the referenciaAdicionales to set
     */
    public void setReferenciaAdicionales(String referenciaAdicionales) {
        this.referenciaAdicionales = referenciaAdicionales;
    }

    /**
     * @return the codigoPostal
     */
    public String getCodigoPostal() {
        return codigoPostal==null?"":codigoPostal;
    }

    /**
     * @param codigoPostal the codigoPostal to set
     */
    public void setCodigoPostal(String codigopostal) {
        this.codigoPostal = codigopostal;
    }

    /**
     * @return the claveMunicipio
     */
    public String getClaveMunicipio() {
        return claveMunicipio==null?"":claveMunicipio;
    }

    /**
     * @param claveMunicipio the claveMunicipio to set
     */
    public void setClaveMunicipio(String clavemunicipio) {
        this.claveMunicipio = clavemunicipio;
    }

    public String getReferenciaCalles(){
        boolean calle1Empty=entreCalle1==null || entreCalle1.trim().isEmpty();
        boolean calle2Empty=entreCalle2==null || entreCalle2.trim().isEmpty();
        return ((calle1Empty?"":entreCalle1)+
                ((!calle1Empty && !calle1Empty)?" y ":"")+
                (calle2Empty?"":entreCalle2));
    }
}
