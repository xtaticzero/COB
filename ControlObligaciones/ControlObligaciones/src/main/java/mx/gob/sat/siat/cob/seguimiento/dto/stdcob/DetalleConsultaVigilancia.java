package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

import java.io.Serializable;

public class DetalleConsultaVigilancia implements Serializable {

    private String rutaLocalArchivo = "";
    private String nombreArchivo;

    /**
     *
     */
    public DetalleConsultaVigilancia() {
        super();
    }

    /**
     *
     * @param rutaLocalArchivo
     */
    public void setRutaLocalArchivo(String rutaLocalArchivo) {
        this.rutaLocalArchivo = rutaLocalArchivo;
    }

    /**
     *
     * @return
     */
    public String getRutaLocalArchivo() {
        return rutaLocalArchivo;
    }
    /**
     * 
     * @return
     */
    public String getNombreArchivo() {
        return nombreArchivo;
    }
    /**
     *
     * @param nombreArchivo
     */
    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }
    
}
