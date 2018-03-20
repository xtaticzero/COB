/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

import java.util.Date;

/**
 *
 * @author Juan
 */
public class BitacoraDocumento {

    private String numeroControl;
    private int idEstadoDocto;
    private Date fechaMovimiento;

    public BitacoraDocumento() {
    }

    public String getNumeroControl() {
        return numeroControl;
    }

    public void setNumeroControl(String numeroControl) {
        this.numeroControl = numeroControl;
    }

    public int getIdEstadoDocto() {
        return idEstadoDocto;
    }

    public void setIdEstadoDocto(int idEstadoDocto) {
        this.idEstadoDocto = idEstadoDocto;
    }

    public Date getFechaMovimiento() {
        if (fechaMovimiento != null) {
            return (Date) fechaMovimiento.clone();
        }
        return null;
    }

    public void setFechaMovimiento(Date fechaMovimiento) {
        if (fechaMovimiento != null) {
            this.fechaMovimiento = (Date) fechaMovimiento.clone();
        }
    }
}
