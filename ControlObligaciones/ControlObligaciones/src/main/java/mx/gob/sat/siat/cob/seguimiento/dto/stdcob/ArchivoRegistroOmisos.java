package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

import mx.gob.sat.siat.cob.seguimiento.dto.compartidos.Bitacora;
import mx.gob.sat.siat.cob.seguimiento.dto.compartidos.TareaDTO;

public class ArchivoRegistroOmisos {

    private TareaDTO tarea;
    private String nombreArchivo;
    private Bitacora bitacora;

    /**
     *
     */
    public ArchivoRegistroOmisos() {
        super();
    }

    /**
     * @return the tarea
     */
    public TareaDTO getTarea() {
        return tarea;
    }

    /**
     * @param tarea the tarea to set
     */
    public void setTarea(TareaDTO tarea) {
        this.tarea = tarea;
    }

    /**
     * @return the nombreArchivo
     */
    public String getNombreArchivo() {
        return nombreArchivo;
    }

    /**
     * @param nombreArchivo the nombreArchivo to set
     */
    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    /**
     * @return the bitacora
     */
    public Bitacora getBitacora() {
        return bitacora;
    }

    /**
     * @param bitacora the bitacora to set
     */
    public void setBitacora(Bitacora bitacora) {
        this.bitacora = bitacora;
    }

}