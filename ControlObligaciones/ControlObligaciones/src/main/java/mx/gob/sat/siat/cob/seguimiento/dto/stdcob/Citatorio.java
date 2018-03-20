/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

import java.util.Date;

/**
 *
 * @author juan
 */
public class Citatorio {

    private String numeroControl;
    private Date fechaCitatorio;

    public Citatorio() {
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

    /**
     *
     * @return
     */
    public Date getFechaCitatorio() {
        if (fechaCitatorio != null) {
            return (Date) fechaCitatorio.clone();
        } else {
            return null;
        }
    }

    /**
     *
     * @param fechaCitatorio
     */
    public void setFechaCitatorio(Date fechaCitatorio) {
        if (fechaCitatorio != null) {
            this.fechaCitatorio = new Date(fechaCitatorio.getTime());
        }
    }

}
