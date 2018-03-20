package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

import mx.gob.sat.siat.cob.seguimiento.dto.idc.Ubicacion;

public class NumeroControlDTO {

    private String numeroControl;
    private String numeroResolucion;
    private Ubicacion ubicacion;
    private String rfc;
    /**
     * @return the numeroControl
     */
    public String getNumeroControl() {
        return numeroControl;
    }

    /**
     * @param numeroControl the numeroControl to set
     */
    public void setNumeroControl(String numeroControl) {
        this.numeroControl = numeroControl;
    }

    /**
     * @return the ubicacion
     */
    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    /**
     * @param ubicacion the ubicacion to set
     */
    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getNumeroResolucion() {
        return numeroResolucion;
    }

    public void setNumeroResolucion(String numeroResolucion) {
        this.numeroResolucion = numeroResolucion;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }
    
    @Override
    public String toString() {
        return "NumeroControlDTO{" + "numeroControl=" + numeroControl + ", numeroResolucion=" + numeroResolucion + ", ubicacion=" + ubicacion + ", rfc=" + rfc + '}';
    }

}
