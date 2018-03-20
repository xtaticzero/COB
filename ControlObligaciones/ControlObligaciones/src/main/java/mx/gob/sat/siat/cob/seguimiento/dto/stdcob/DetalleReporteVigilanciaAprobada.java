/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

import java.io.Serializable;

/**
 *
 * @author root
 */
public class DetalleReporteVigilanciaAprobada implements Serializable{
    
    private String rfc;
    private String numeroControl;

    public DetalleReporteVigilanciaAprobada(String rfc, String numeroControl) {
        this.rfc = rfc;
        this.numeroControl = numeroControl;
    }

    public DetalleReporteVigilanciaAprobada() {
    }
    
    

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getNumeroControl() {
        return numeroControl;
    }

    public void setNumeroControl(String numeroControl) {
        this.numeroControl = numeroControl;
    }
}
