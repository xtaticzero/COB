package mx.gob.sat.siat.cob.seguimiento.dto.arca;

/**
 *
 * @author Juan
 */
public class OrigenMulta {

    private String idDocumento;
    private String numControlOrigen;
    private String fechaNotificacionOrigen;
    private String numControlPrimero;
    private String fechaNotificacionPrimero;
    private String numControlSegundo;
    private String fechaNotificacionSegundo;

    public OrigenMulta() {
    }

    public OrigenMulta(String idDocumento, String numControlOrigen, String fechaNotificacionOrigen, String numControlPrimero, String fechaNotificacionPrimero, String numControlSegundo, String fechaNotificacionSegundo) {
        this.idDocumento = idDocumento;
        this.numControlOrigen = numControlOrigen;
        this.fechaNotificacionOrigen = fechaNotificacionOrigen;
        this.numControlPrimero = numControlPrimero;
        this.fechaNotificacionPrimero = fechaNotificacionPrimero;
        this.numControlSegundo = numControlSegundo;
        this.fechaNotificacionSegundo = fechaNotificacionSegundo;
    }

    /**
     *
     * @return
     */
    public String getIdDocumento() {
        return idDocumento;
    }

    /**
     *
     * @param idDocumento
     */
    public void setIdDocumento(String idDocumento) {
        this.idDocumento = idDocumento;
    }

    /**
     *
     * @return
     */
    public String getNumControlOrigen() {
        return numControlOrigen;
    }

    /**
     *
     * @param numControlOrigen
     */
    public void setNumControlOrigen(String numControlOrigen) {
        this.numControlOrigen = numControlOrigen;
    }

    /**
     *
     * @return
     */
    public String getFechaNotificacionOrigen() {
        return fechaNotificacionOrigen;
    }

    /**
     *
     * @param fechaNotificacionOrigen
     */
    public void setFechaNotificacionOrigen(String fechaNotificacionOrigen) {
        this.fechaNotificacionOrigen = fechaNotificacionOrigen;
    }

    /**
     *
     * @return
     */
    public String getNumControlPrimero() {
        return numControlPrimero;
    }

    /**
     *
     * @param numControlPrimero
     */
    public void setNumControlPrimero(String numControlPrimero) {
        this.numControlPrimero = numControlPrimero;
    }

    /**
     *
     * @return
     */
    public String getFechaNotificacionPrimero() {
        return fechaNotificacionPrimero;
    }

    /**
     *
     * @param fechaNotificacionPrimero
     */
    public void setFechaNotificacionPrimero(String fechaNotificacionPrimero) {
        this.fechaNotificacionPrimero = fechaNotificacionPrimero;
    }

    /**
     *
     * @return
     */
    public String getNumControlSegundo() {
        return numControlSegundo;
    }

    /**
     *
     * @param numControlSegundo
     */
    public void setNumControlSegundo(String numControlSegundo) {
        this.numControlSegundo = numControlSegundo;
    }

    /**
     *
     * @return
     */
    public String getFechaNotificacionSegundo() {
        return fechaNotificacionSegundo;
    }

    /**
     *
     * @param fechaNotificacionSegundo
     */
    public void setFechaNotificacionSegundo(String fechaNotificacionSegundo) {
        this.fechaNotificacionSegundo = fechaNotificacionSegundo;
    }
}
