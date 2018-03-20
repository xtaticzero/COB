/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

/**
 *
 * @author root
 */
public class FirmaDigital {

    private String cadenaOriginal;
    private String cadenaOriginalGenerada;
    private String firmaDigital;
    private String numControlResolucion;
    private String empleadoFirma;

    public FirmaDigital(String numControlResolucion, String cadenaOriginal, String firma) {
        this.numControlResolucion = numControlResolucion;
        this.cadenaOriginal = cadenaOriginal;
        this.firmaDigital = firma;
    }

    public FirmaDigital() {
    }

    public String getCadenaOriginal() {
        return cadenaOriginal;
    }

    public void setCadenaOriginal(String cadenaOriginal) {
        this.cadenaOriginal = cadenaOriginal;
    }

    public String getCadenaOriginalGenerada() {
        return cadenaOriginalGenerada;
    }

    public void setCadenaOriginalGenerada(String cadenaOriginalGenerada) {
        this.cadenaOriginalGenerada = cadenaOriginalGenerada;
    }

    public String getFirmaDigital() {
        return firmaDigital;
    }

    public void setFirmaDigital(String firmaDigital) {
        this.firmaDigital = firmaDigital;
    }

    public String getNumControlResolucion() {
        return numControlResolucion;
    }

    public void setNumControlResolucion(String numControlResolucion) {
        this.numControlResolucion = numControlResolucion;
    }

    public String getEmpleadoFirma() {
        return empleadoFirma;
    }

    public void setEmpleadoFirma(String empleadoFirma) {
        this.empleadoFirma = empleadoFirma;
    }

    @Override
    public String toString() {
        return "FirmaDigital{" + "cadenaOriginal=" + cadenaOriginal + ", cadenaOriginalGenerada=" + cadenaOriginalGenerada + ", firmaDigital=" + firmaDigital + ", numControlResolucion=" + numControlResolucion + ", empleadoFirma=" + empleadoFirma + '}';
    }
    
    
}
