/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.sat.siat.cob.seguimiento.dto.compartidos;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoDocumentoEnum;

/**
 *
 * @author emmanuel
 */
public class EstadoDocumentoDTO {
    private EstadoDocumentoEnum estado;
    private String numeroControl;

    public EstadoDocumentoEnum getEstado() {
        return estado;
    }

    public void setEstado(EstadoDocumentoEnum estado) {
        this.estado = estado;
    }

    public String getNumeroControl() {
        return numeroControl;
    }

    public void setNumeroControl(String numeroControl) {
        this.numeroControl = numeroControl;
    }
}
