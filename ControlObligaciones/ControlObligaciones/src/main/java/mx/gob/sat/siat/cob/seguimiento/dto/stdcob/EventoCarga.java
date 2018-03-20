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
public class EventoCarga implements Serializable{
    
    private Long idEventoCarga;
    private String nombre;
    private String descripcion;

    public EventoCarga() {
    }

    public Long getIdEventoCarga() {
        return idEventoCarga;
    }

    public void setIdEventoCarga(Long idEventoCarga) {
        this.idEventoCarga = idEventoCarga;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    
}
