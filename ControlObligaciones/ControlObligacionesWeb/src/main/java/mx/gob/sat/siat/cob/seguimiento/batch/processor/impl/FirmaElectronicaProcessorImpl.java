/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.batch.processor.impl;

import mx.gob.sat.siat.cob.seguimiento.batch.processor.FirmaElectronicaProcessor;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.FirmaDigital;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author root
 */
@Service("firmaElectronicaProcessor")
@Scope("step")
public class FirmaElectronicaProcessorImpl implements FirmaElectronicaProcessor {

    private String numEmpleado;

    @Value("#{jobParameters[numeroEmpleado]}")
    public void setNumEmpleado(String numEmpleado) {
        this.numEmpleado = numEmpleado;
    }

    @Override
    public FirmaDigital process(FirmaDigital i) {
        i.setNumControlResolucion(i.getCadenaOriginal().split("\\|")[0]);
        i.setEmpleadoFirma(numEmpleado);
        return i;
    }

}
