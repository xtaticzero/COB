/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums;

/**
 *
 * @author Juan
 */
public enum TipoDatoEnum {

    DATE("D"),
    BOOLEAN("B"),
    INTEGER("I"),
    STRING("S");

    private String valor;

    private TipoDatoEnum(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

}
