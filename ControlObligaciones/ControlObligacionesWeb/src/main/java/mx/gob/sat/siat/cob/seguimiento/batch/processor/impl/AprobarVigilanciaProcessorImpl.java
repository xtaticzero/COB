package mx.gob.sat.siat.cob.seguimiento.batch.processor.impl;

import mx.gob.sat.siat.cob.seguimiento.batch.processor.AprobarVigilanciaProcessor;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.FirmaDigital;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.FirmaDigitalDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author christian.ventura
 */
@Service("aprobarVigilanciaProcessor")
@Scope("step")
public class AprobarVigilanciaProcessorImpl implements AprobarVigilanciaProcessor {

    private String numEmpleado;

    /**
     *
     * @param numEmpleado
     */
    @Value("#{jobParameters[numEmpleado]}")
    public void setNumEmpleado(String numEmpleado) {
        this.numEmpleado = numEmpleado;
    }

    /**
     *
     * @return
     */
    public String getNumEmpleado() {
        return numEmpleado;
    }

    /**
     *
     * @param i
     * @return
     */
    @Override
    public FirmaDigital process(FirmaDigitalDTO i) {
        FirmaDigital firma = new FirmaDigital();
        firma.setCadenaOriginal(i.getCadenaOriginal());
        firma.setFirmaDigital(i.getFirmaDigital());
        firma.setCadenaOriginalGenerada(i.getCadenaOriginalGenerada());
        firma.setNumControlResolucion(i.getCadenaOriginal().split("\\|")[0]);
        firma.setEmpleadoFirma(getNumEmpleado());
        return firma;
    }

}
