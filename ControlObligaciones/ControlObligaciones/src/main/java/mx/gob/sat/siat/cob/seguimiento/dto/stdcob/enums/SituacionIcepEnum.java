/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums;

/**
 *
 * @author root
 */
public enum SituacionIcepEnum {
    INCUMPLIDO(0),
    CUMPLIDO(1),
    CANCELADO_POR_MOVIMIENTOS_PADRON(2);
    
    private final int valor;

    private SituacionIcepEnum(int valor) {
        this.valor = valor;
    }
    public int getValor(){
        return valor;
    }
}
