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
 * @author emmanuel
 */
public class VigilanciasLogDTO implements Serializable {

    private static final long serialVersionUID = 3770505825710903640L;
    private Integer idVigilancia;
    private String idAdmonLocal;
    private String descripcion;
    private Date fechaRegistro;

    public Integer getIdVigilancia() {
        return idVigilancia;
    }

    public void setIdVigilancia(Integer idVigilancia) {
        this.idVigilancia = idVigilancia;
    }

    public String getIdAdmonLocal() {
        return idAdmonLocal;
    }

    public void setIdAdmonLocal(String idAdmonLocal) {
        this.idAdmonLocal = idAdmonLocal;
    }

    public Date getFechaRegistro() {
        if (fechaRegistro != null) {
            return (Date) fechaRegistro.clone();
        } else {
            return null;
        }
    }

    public void setFechaRegistro(Date fechaRegistro) {
        if (fechaRegistro != null) {
            this.fechaRegistro = (Date) fechaRegistro.clone();
        }
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
