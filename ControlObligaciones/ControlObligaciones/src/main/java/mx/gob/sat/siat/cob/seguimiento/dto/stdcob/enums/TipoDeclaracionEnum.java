/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums;

/**
 *
 * @author root
 */
public enum TipoDeclaracionEnum {
    NORMAL(1),
    COMPLEMENTARIA(2),
    CORRECTIVA(3);
    
    private int valor;

    private TipoDeclaracionEnum(int valor) {
        this.valor = valor;
    }
    public int getValor(){
        return valor;
    }
    
}
