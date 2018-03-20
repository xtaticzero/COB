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
public class MultaIcep implements Serializable{

    private String numeroResolucion;
    private String numeroControl;
    private Long claveIcep;

    public MultaIcep() {
    }

    public String getNumeroResolucion() {
        return numeroResolucion;
    }

    public void setNumeroResolucion(String numeroResolucion) {
        this.numeroResolucion = numeroResolucion;
    }

    public String getNumeroControl() {
        return numeroControl;
    }

    public void setNumeroControl(String numeroControl) {
        this.numeroControl = numeroControl;
    }

    public Long getClaveIcep() {
        return claveIcep;
    }

    public void setClaveIcep(Long claveIcep) {
        this.claveIcep = claveIcep;
    }

    @Override
    public String toString() {
        return numeroControl+","+numeroResolucion+","+claveIcep;
    }
}
