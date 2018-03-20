/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author root
 */
public class ReporteVigilanciaDTO implements Serializable {

    private Integer numeroVigilanciqa;
    private String tipoDocto;
    private String descripcion;
    private String periodo;
    private String ejercicio;
    private Date fechaLiberacion;
    private List<AlscDTO> lstALSC;

    public Integer getNumeroVigilanciqa() {
        return numeroVigilanciqa;
    }

    public void setNumeroVigilanciqa(Integer numeroVigilanciqa) {
        this.numeroVigilanciqa = numeroVigilanciqa;
    }

    public String getTipoDocto() {
        return tipoDocto;
    }

    public void setTipoDocto(String tipoDocto) {
        this.tipoDocto = tipoDocto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getEjercicio() {
        return ejercicio;
    }

    public void setEjercicio(String ejercicio) {
        this.ejercicio = ejercicio;
    }

    public Date getFechaLiberacion() {
        if (fechaLiberacion != null) {
            return (Date) fechaLiberacion.clone();
        }
        return null;
    }

    public void setFechaLiberacion(Date fechaLiberacion) {
        if (fechaLiberacion != null) {
            this.fechaLiberacion = (Date) fechaLiberacion.clone();
        }
    }

    public List<AlscDTO> getLstALSC() {
        return lstALSC;
    }

    public void setLstALSC(List<AlscDTO> lstALSC) {
        this.lstALSC = lstALSC;
    }
}
