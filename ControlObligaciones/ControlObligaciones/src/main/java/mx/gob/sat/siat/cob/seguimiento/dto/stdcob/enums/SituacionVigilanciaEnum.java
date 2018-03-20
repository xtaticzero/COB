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
public enum SituacionVigilanciaEnum implements Serializable{
    PENDIENTE_PROCESAR(1),ACEPTADA(2),RECHAZADA(3),ERRONEA(4),ENVIADA_ARCA(5);

    private final int idSituacion;
    
    private SituacionVigilanciaEnum(int idSituacion) {
        this.idSituacion=idSituacion;
    }

    public int getIdSituacion() {
        return idSituacion;
    }
    
}
