/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums;

/**
 *
 * @author root
 */
public enum EstadoMultaEnum {
    
    PENDIENTE_DE_PROCESAR_ENTIDAD(0),
    PENDIENTE_DE_PROCESAR(1),
    AUTORIZADA(2),
    ENVIADA_A_ARCA(3),
    GENERADA_EN_ARCHIVO(4),
    CANCELADA(5);
    
    private final int valor;

    private EstadoMultaEnum(int valor) {
        this.valor = valor;
    }
    public int getValor(){
        return valor;
    }
    
}
