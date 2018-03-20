/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

import java.io.Serializable;

/**
 *
 * @author root
 */
public class VisualizaDetalleRenuente implements Serializable{
    private Long claveObligacion;
    private String obligacion;
    private Integer ejercicio;
    private String periodo;

    public Long getClaveObligacion() {
        return claveObligacion;
    }

    public void setClaveObligacion(Long claveObligacion) {
        this.claveObligacion = claveObligacion;
    }

    public String getObligacion() {
        return obligacion;
    }

    public void setObligacion(String obligacion) {
        this.obligacion = obligacion;
    }

    public Integer getEjercicio() {
        return ejercicio;
    }

    public void setEjercicio(Integer ejercicio) {
        this.ejercicio = ejercicio;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    } 
    
}
