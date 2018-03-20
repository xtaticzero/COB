/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

import java.io.Serializable;

import java.util.Date;

/**
 *
 * @author root
 */
public class BitacoraMulta implements Serializable{
    
    private String numeroResolucion;
    private Integer idEstadoResolucion;
    private Date fechaMovimiento;

    public BitacoraMulta() {
    }

    public String getNumeroResolucion() {
        return numeroResolucion;
    }

    public void setNumeroResolucion(String numeroResolucion) {
        this.numeroResolucion = numeroResolucion;
    }

    public Integer getIdEstadoResolucion() {
        return idEstadoResolucion;
    }

    public void setIdEstadoResolucion(Integer idEstadoResolucion) {
        this.idEstadoResolucion = idEstadoResolucion;
    }

    public Date getFechaMovimiento() {
        if(fechaMovimiento!=null){
            return (Date) fechaMovimiento.clone();
        }else{
            return null;
        }
    }

    public void setFechaMovimiento(Date fechaMovimiento) {
        if(fechaMovimiento!=null){
            this.fechaMovimiento = new Date(fechaMovimiento.getTime());
        }
    }    
}
