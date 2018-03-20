/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.processor.impl;

import mx.gob.sat.siat.cob.background.holder.CargaArchivosHolder;
import mx.gob.sat.siat.cob.background.processor.MultaDocumentoProcessor;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.MultaDocumento;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author root
 */
@Service("multaDocumentoProcessor")
@Scope("step")
public class MultaDocumentoProcessorImpl implements MultaDocumentoProcessor {

    private Logger log = Logger.getLogger(MultaDocumentoProcessorImpl.class);
    @Autowired
    private CargaArchivosHolder holder;

    public MultaDocumentoProcessorImpl() {
        log.info("### MultaDocumentoProcessor --> ");
    }
    
    @Override
    public MultaDocumento process(MultaDocumento multaDocumento) {
        log.info("### setImporte");
        double importe = 0;
        if (holder.getMultasDocumentos() != null) {
            log.info("### tamMultaDocumento " + holder.getMultasDocumentos().size());
            for (MultaDocumento multa : holder.getMultasDocumentos()) {
                if (multa.getNumeroResolucion().equals(multaDocumento.getNumeroResolucion())) {
                    importe += multaDocumento.getMonto();
                }
            }
        }
        multaDocumento.setMontoTotal(importe);
        return multaDocumento;
    }
}
