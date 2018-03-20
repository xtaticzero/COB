/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums;

/**
 *
 * @author root
 */
public enum TipoMultaEnum {

    INCUMPLIMIENTO, EXTEMPORANEIDAD, INCUMPLIMIENTO_EXTEMPORANEIDAD, COMPLEMENTARIA,LIQUIDACION;

    @Override
    public String toString() {
        switch (this) {
            case INCUMPLIMIENTO:
                return "RESOLMOTIVO_INCUMPLIMIENTO";
            case EXTEMPORANEIDAD:
                return "RESOLMOTIVO_EXTEMPORANEIDAD";
            case INCUMPLIMIENTO_EXTEMPORANEIDAD:
                return "RESOLMOTIVO_AMBOS";
            case LIQUIDACION:
                return "RESOLMOTIVO_LIQUIDACION";
            default:
                return "RESOLMOTIVO_COMPLEMENTARIA";
        }
    }

}
