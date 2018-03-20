/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

import java.io.Serializable;

/**
 *
 * @author root
 */
public class VisualizaDocumentoRenuente implements Serializable {

    private String numeroControl;
    private String rfc;
    private String numeroControlRequerimiento;
    private String tipoFirma;
    private String medioEnvio;
    private String tipoDocumento;
    private String cadenaOriginal;

    public String getNumeroControl() {
        return numeroControl;
    }

    public void setNumeroControl(String numeroControl) {
        this.numeroControl = numeroControl;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getNumeroControlRequerimiento() {
        return numeroControlRequerimiento;
    }

    public void setNumeroControlRequerimiento(String numeroControlRequerimiento) {
        this.numeroControlRequerimiento = numeroControlRequerimiento;
    }

    public String getTipoFirma() {
        return tipoFirma;
    }

    public void setTipoFirma(String tipoFirma) {
        this.tipoFirma = tipoFirma;
    }

    public String getMedioEnvio() {
        return medioEnvio;
    }

    public void setMedioEnvio(String medioEnvio) {
        this.medioEnvio = medioEnvio;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getCadenaOriginal() {
        return cadenaOriginal;
    }

    public void setCadenaOriginal(String cadenaOriginal) {
        this.cadenaOriginal = cadenaOriginal;
    }

    @Override
    public String toString() {
        return "VisualizaDocumentoRenuente{" + "numeroControl=" + numeroControl + ", rfc=" + rfc + ", numeroControlRequerimiento=" + numeroControlRequerimiento + ", tipoFirma=" + tipoFirma + ", medioEnvio=" + medioEnvio + ", tipoDocumento=" + tipoDocumento + ", cadenaOriginal=" + cadenaOriginal + '}';
    }

    
}
