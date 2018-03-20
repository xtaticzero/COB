/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

import java.util.Date;

/**
 *
 * @author root
 */
public class Periodicidad {

    private char idPeriodicidad;
    private String nombre;
    private String descripcion;
    private Date fechaInicio;
    private Date fehcaFin;
    private int orden;

    /**
     *
     */
    public Periodicidad() {
    }

    /**
     *
     * @param idPeriodicidad
     * @param nombre
     * @param descripcion
     * @param fechaInicio
     * @param fehcaFin
     * @param orden
     */
    public Periodicidad(char idPeriodicidad, String nombre, String descripcion, Date fechaInicio, Date fehcaFin, int orden) {
        this.idPeriodicidad = idPeriodicidad;
        this.nombre = nombre;
        this.descripcion = descripcion;
        if (fechaInicio != null) {
            this.fechaInicio = (Date) fechaInicio.clone();
        }
        if (fehcaFin != null) {
            this.fehcaFin = (Date) fehcaFin.clone();
        }
        this.orden = orden;
    }

    /**
     *
     * @return
     */
    public char getIdPeriodicidad() {
        return idPeriodicidad;
    }

    /**
     *
     * @param idPeriodicidad
     */
    public void setIdPeriodicidad(char idPeriodicidad) {
        this.idPeriodicidad = idPeriodicidad;
    }

    /**
     *
     * @return
     */
    public String getNombre() {
        return nombre;
    }

    /**
     *
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     *
     * @return
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     *
     * @param descripcion
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     *
     * @return
     */
    public Date getFechaInicio() {
        if (fechaInicio != null) {
            return (Date) fechaInicio.clone();
        } else {
            return null;
        }
    }

    /**
     *
     * @param fechaInicio
     */
    public void setFechaInicio(Date fechaInicio) {
        if (fechaInicio != null) {
            this.fechaInicio = (Date) fechaInicio.clone();
        }
    }

    /**
     *
     * @return
     */
    public Date getFehcaFin() {
        if (fehcaFin != null) {
            return (Date) fehcaFin.clone();
        } else {
            return null;
        }
    }

    /**
     *
     * @param fehcaFin
     */
    public void setFehcaFin(Date fehcaFin) {
        if (fehcaFin != null) {
            this.fehcaFin = (Date) fehcaFin.clone();
        }
    }

    /**
     *
     * @return
     */
    public int getOrden() {
        return orden;
    }

    /**
     *
     * @param orden
     */
    public void setOrden(int orden) {
        this.orden = orden;
    }
}
