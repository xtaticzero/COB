/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dto.arca.enums;

/**
 *
 * @author Daniel
 */
public enum EstadoMultaResolucionArca {
      
    CARGADO(30),
    REPLICADO(31),
    BAJA_SIR(32),
    CANCELACION(33),
    CANCELACION_APLICADA(34),
    NO_REPLICADO(35),
    COPIADO_EN_ARCA(36);
    
   private int idEdoDoc;

    private EstadoMultaResolucionArca(int value) {
            this.idEdoDoc = value;
    }
    
    public int getId(){
        return idEdoDoc;
    }
    
}
