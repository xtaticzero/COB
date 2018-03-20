/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums;

/**
 *
 * @author root
 */
public enum ProcesoEnum {    
    
    EXTRACCION_CUMPLIMIENTO(5);
    
    private int valor;
    
    private ProcesoEnum(int valor) {
        this.valor = valor;
    }
    public int getValor(){
        return valor;
    }
    
}
