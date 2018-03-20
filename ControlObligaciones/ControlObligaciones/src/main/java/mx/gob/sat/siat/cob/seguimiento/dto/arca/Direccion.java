/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dto.arca;

import java.io.Serializable;
import mx.gob.sat.siat.cob.seguimiento.util.Utilerias;
import static mx.gob.sat.siat.cob.seguimiento.util.Utilerias.isVacio;

/**
 *
 * @author Juan
 */
public class Direccion implements Serializable {

    private static final long serialVersionUID = -5147943L;
    private String calle;
    private String numeroExterior;
    private String numeroInterior;
    private String descripcionColonia;
    private String descripcionLocalidad;
    private String entreCalle1;
    private String entreCalle2;
    private String descripcionMunicipio;
    private String descripcionEntidadFederativa;
    private String codigoPostal;
    private StringBuilder builderDireccion;
    private String referenciaAdicionales;

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumeroExterior() {
        return numeroExterior;
    }

    public void setNumeroExterior(String numeroExterior) {
        this.numeroExterior = numeroExterior;
    }

    public String getNumeroInterior() {
        return numeroInterior;
    }

    public void setNumeroInterior(String numeroInterior) {
        this.numeroInterior = numeroInterior;
    }

    public String getDescripcionColonia() {
        return descripcionColonia;
    }

    public void setDescripcionColonia(String descripcionColonia) {
        this.descripcionColonia = descripcionColonia;
    }

    public String getDescripcionLocalidad() {
        return descripcionLocalidad;
    }

    public void setDescripcionLocalidad(String descripcionLocalidad) {
        this.descripcionLocalidad = descripcionLocalidad;
    }

    public String getEntreCalle1() {
        return entreCalle1;
    }

    public void setEntreCalle1(String entreCalle1) {
        this.entreCalle1 = entreCalle1;
    }

    public String getEntreCalle2() {
        return entreCalle2;
    }

    public void setEntreCalle2(String entreCalle2) {
        this.entreCalle2 = entreCalle2;
    }

    public String getDescripcionMunicipio() {
        return descripcionMunicipio;
    }

    public void setDescripcionMunicipio(String descripcionMunicipio) {
        this.descripcionMunicipio = descripcionMunicipio;
    }

    public String getDescripcionEntidadFederativa() {
        return descripcionEntidadFederativa;
    }

    public void setDescripcionEntidadFederativa(String descripcionEntidadFederativa) {
        this.descripcionEntidadFederativa = descripcionEntidadFederativa;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    @Override
    public String toString() {
        builderDireccion = new StringBuilder();
        calleLocalidad();
        String referencia = Utilerias.obtenerReferenciaUbicacion(entreCalle1, entreCalle2, referenciaAdicionales);
        builderDireccion.append(referencia.length() == 0 ? "" : ", ")
                .append(referencia);
        entidadFederativa();
        return builderDireccion.toString();
    }
    
    private void calleLocalidad() {
        if (!isVacio(calle)) {
            builderDireccion.append(calle);
        }
        if (!isVacio(numeroExterior)) {
            builderDireccion.append(" ").append(numeroExterior);
        }
        if (!isVacio(numeroInterior)) {
            builderDireccion.append(" - ").append(numeroInterior);
        }
        if (!isVacio(descripcionColonia)) {
            builderDireccion.append(" ").append(descripcionColonia);
        }
        if (!isVacio(descripcionLocalidad)) {
            builderDireccion.append(" ").append(descripcionLocalidad);
        }
    }

    private void entidadFederativa() {
        if (!isVacio(descripcionMunicipio)) {
            builderDireccion.append(", ").append(descripcionMunicipio);
        }
        if (!isVacio(descripcionEntidadFederativa)) {
            builderDireccion.append(" ").append(descripcionEntidadFederativa);
        }
        if (!isVacio(codigoPostal)) {
            builderDireccion.append(" C.P. ").append(codigoPostal);
        }
    }

    public String getReferenciaAdicionales() {
        return referenciaAdicionales;
    }

    public void setReferenciaAdicionales(String referenciaAdicionales) {
        this.referenciaAdicionales = referenciaAdicionales;
    }
    
}
