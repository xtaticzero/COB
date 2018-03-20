/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums;

/**
 *
 * @author root
 */
public enum TipoPersona {

    PERSONA_FISICA("F"),
    PERSONA_MORAL("M");
    private String tipoPersona;

    private TipoPersona(String tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    public String getTipoPersona() {
        return tipoPersona;
    }
}
