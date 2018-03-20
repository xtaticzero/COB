/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums;

/**
 *
 * @author emmanuel
 */
public enum CSColumnsOrderEnum {
    IDENTIFICADOR_PROCESO(0),
    IDENTIFICADOR_FIRMA_PROCESO(1),
    IDENTIFICADOR_ESTADO(2);
    
    private final int valor;

    private CSColumnsOrderEnum(int valor) {
        this.valor = valor;
    }
    public int getValor(){
        return valor;
    }
}
