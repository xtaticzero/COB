/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums;

/**
 *
 * @author Juan
 */
public enum EsMultaEnum {

    ES_MULTA(1),
    NO_ES_MULTA(0);
    private final Integer valor;

    private EsMultaEnum(Integer valor) {
        this.valor = valor;
    }

    public Integer getValor() {
        return valor;
    }
}
