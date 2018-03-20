/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dto.db2;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author root
 */
public class Padron implements Serializable {

    private String boid;
    private Long icep;
    private Date fechaMantenimiento;

    /**
     *
     */
    public Padron() {
    }

    /**
     *
     * @return
     */
    public String getBoid() {
        return boid;
    }

    /**
     *
     * @param boid
     */
    public void setBoid(String boid) {
        this.boid = boid;
    }

    /**
     *
     * @return
     */
    public Long getIcep() {
        return icep;
    }

    /**
     *
     * @param icep
     */
    public void setIcep(Long icep) {
        this.icep = icep;
    }
    /**
     *
     * @return
     */
    public Date getFechaMantenimiento() {
        if(fechaMantenimiento!=null){
            return (Date) fechaMantenimiento.clone();
        }else{
            return null;
        }
    }

    /**
     *
     * @param fechaMantenimiento
     */
    public void setFechaMantenimiento(Date fechaMantenimiento) {
        if(fechaMantenimiento!=null){
            this.fechaMantenimiento = (Date)fechaMantenimiento.clone();
        }
    }

    
}
