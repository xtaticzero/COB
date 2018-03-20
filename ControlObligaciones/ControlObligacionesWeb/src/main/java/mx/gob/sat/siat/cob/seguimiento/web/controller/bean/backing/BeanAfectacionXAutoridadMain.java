/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing;

import java.io.Serializable;

/**
 *
 * @author Marco Murakami
 */
public class BeanAfectacionXAutoridadMain implements Serializable{
    
    private String numeroControl;
    private String rfc;

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
    public String getRfc() {
        return rfc;
    }

    /**
     *
     * @param rfc
     */
    public void setRfc(String rfc) {
        this.rfc = rfc;
    }
}
