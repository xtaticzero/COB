/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dto.db2;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Cumplimiento;

/**
 *
 * @author rodrigo
 */
public class HistoricoCumplimiento extends Cumplimiento{

    /**
     *
     * @param bOID
     * @param claveICEP
     * @param identificadorCumplimiento
     * @param importePagar
     * @param fechaPresentacion
     * @param materia
     * @param tipoDeclaracion
     * @param tipoCumplimiento
     */
    
    
    private Integer estadoIcep;

    public Integer getEstadoIcep() {
        return estadoIcep;
    }

    public void setEstadoIcep(Integer estadoIcep) {
        this.estadoIcep = estadoIcep;
    }
    
    
}
