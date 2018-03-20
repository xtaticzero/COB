/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums;

import java.io.Serializable;

/**
 *
 * @author root
 */
public enum EventoCargaEnum implements Serializable {

    CARGA_OMISOS(1), RECHAZO_VIGILANCIA(2);

    private final int idEvento;

    private EventoCargaEnum(int idEvento) {
        this.idEvento = idEvento;
    }

    public int getIdEvento() {
        return idEvento;
    }

}
