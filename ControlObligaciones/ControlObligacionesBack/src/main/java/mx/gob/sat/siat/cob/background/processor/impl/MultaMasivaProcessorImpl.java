/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.processor.impl;

import java.io.IOException;
import mx.gob.sat.siat.cob.background.holder.CargaArchivosHolder;
import mx.gob.sat.siat.cob.background.processor.MultaMasivaProcessor;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.DocumentoARCA;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.ObligacionPeriodo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author Juan
 */
@Service("multaMasivaProcessor")
@Scope("step")
public class MultaMasivaProcessorImpl implements MultaMasivaProcessor {

    private Logger log = Logger.getLogger(MultaMasivaProcessorImpl.class);
    @Autowired
    private CargaArchivosHolder holder;

    @Override
    public DocumentoARCA process(DocumentoARCA documento) {
        try {
            setImporteMulta(documento);
        } catch (IOException e) {
            log.error("MultaMasivaProcessorImpl -->" + e);
        }
        return documento;
    }

    private void setImporteMulta(DocumentoARCA documento) throws IOException {
        log.info("### setImporte");
        double importe = 0;
        if (holder.getObligacionPeriodos() != null) {
            log.info("### tamObligacionPeriodos " + holder.getObligacionPeriodos().size());
            for (ObligacionPeriodo obligaPeriodo : holder.getObligacionPeriodos()) {
                if (obligaPeriodo.getObligacion().getIdDocumento().equals(documento.getId())) {
                    importe += Double.parseDouble(obligaPeriodo.getObligacion().getImporte());
                }
            }
        }
        documento.setImporte(String.valueOf(importe));
    }
}
