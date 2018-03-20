package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

import java.io.Serializable;
import java.util.Date;

public class SgtDocumento implements Serializable {

    private String numeroControl = "";
    private int idEstadoDocto = 0;
    private Date fechaMovimiento = null;

    /**
     *
     */
    public SgtDocumento() {
        super();
    }

    /**
     *
     * @param numeroControl
     * @param idEstadoDocto
     * @param fechaMovimiento
     */
    public SgtDocumento(String numeroControl, int idEstadoDocto, Date fechaMovimiento) {
        this.numeroControl = numeroControl;
        this.idEstadoDocto = idEstadoDocto;
        if (fechaMovimiento != null) {
            this.fechaMovimiento = new Date(fechaMovimiento.getTime());
        }
    }

    /**
     *
     * @return
     */
    public Date getFechaMovimiento() {
        if (fechaMovimiento != null) {
            return (Date) fechaMovimiento.clone();
        } else {
            return null;
        }
    }

    /**
     *
     * @param fechaMovimiento
     */
    public void setFechaMovimiento(Date fechaMovimiento) {
        if (fechaMovimiento != null) {
            this.fechaMovimiento = new Date(fechaMovimiento.getTime());
        }
    }

    /**
     *
     * @return
     */
    public int getIdEstadoDocto() {
        return idEstadoDocto;
    }

    /**
     *
     * @param idEstadoDocto
     */
    public void setIdEstadoDocto(int idEstadoDocto) {
        this.idEstadoDocto = idEstadoDocto;
    }

    /**
     *
     * @return
     */
    public String getNumeroControl() {
        return numeroControl;
    }

    /**
     *
     * @param numeroControl
     */
    public void setNumeroControl(String numeroControl) {
        this.numeroControl = numeroControl;
    }

    @Override
    public String toString() {
        return "SgtDocumento{" + "numeroControl=" + numeroControl + "idEstadoDocto=" + idEstadoDocto + "fechaMovimiento=" + fechaMovimiento + '}';
    }
}
