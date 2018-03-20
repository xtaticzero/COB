/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.util;

import java.io.Serializable;

/**
 *
 * @author root
 */
public class IntegerMutable implements Serializable{

    private Integer valor;

    public IntegerMutable() {
        valor = 0;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return  valor +"";
    }

    
}
