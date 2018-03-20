/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums;

/**
 *
 * @author root
 */
public enum DiasPlantillaEnum {

    DIAS_45(45),
    DIAS_15(15);

    private int valor;

    private DiasPlantillaEnum(int valor) {
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }
}
