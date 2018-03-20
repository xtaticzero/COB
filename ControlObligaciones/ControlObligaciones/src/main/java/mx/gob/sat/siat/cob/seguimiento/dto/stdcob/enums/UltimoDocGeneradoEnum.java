/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums;

/**
 *
 * @author root
 */
public enum UltimoDocGeneradoEnum {
        
        SI(1),
        NO(0);
        private int valor;

        private UltimoDocGeneradoEnum(int valor) {
            this.valor = valor;
        }
        public int getValor(){
            return valor;
        }
}
