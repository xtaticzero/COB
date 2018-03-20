package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;


public class BitacoraErrores extends DetalleCarga {

    private String descripcionVigilancia;
    private String rutaBitacoraErrores;

    public BitacoraErrores() {
        super();
    }

    

    public void setDescripcionVigilancia(String descripcionVigilancia) {
        this.descripcionVigilancia = descripcionVigilancia;
    }

    public String getDescripcionVigilancia() {
        return descripcionVigilancia;
    }

    public void setRutaBitacoraErrores(String rutaBitacoraErrores) {
        this.rutaBitacoraErrores = rutaBitacoraErrores;
    }

    public String getRutaBitacoraErrores() {
        return rutaBitacoraErrores;
    }
}
