/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums;

/**
 *
 * @author root
 */
public enum NivelEmisionEnum {

    LOCAL(1), CENTRAL(2);

    private final int idNivelEmision;
    private String nivelEmision;

    private NivelEmisionEnum(int idNivelEmision) {
        this.idNivelEmision = idNivelEmision;
    }

    public int getIdNivelEmision() {
        return idNivelEmision;
    }

    public String getNivelEmision() {
        return nivelEmision;
    }

    public void setNivelEmision(String nivelEmision) {
        this.nivelEmision = nivelEmision;
    }

    public static NivelEmisionEnum obtenerNivel(Integer nivel) {
        for (NivelEmisionEnum buffer : NivelEmisionEnum.values()) {
            if (nivel == buffer.getIdNivelEmision()) {
                return buffer;
            }
        }
        return null;
    }

}
