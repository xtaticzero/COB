/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.processor.impl;

import mx.gob.sat.siat.cob.background.processor.ArchivoMasivoProcessor;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.DocumentoARCA;
import mx.gob.sat.siat.cob.seguimiento.util.Utilerias;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author Juan
 */
@Service("archivoMasivoProcessor")
@Scope("step")
public class ArchivoMasivoProcessorImpl implements ArchivoMasivoProcessor {

    @Override
    public DocumentoARCA process(DocumentoARCA documento) {
        if (Utilerias.isVacio(documento.getResolucion())) {
            documento.setResolucion(null);
        }
        if (Utilerias.isVacio(documento.getCodPostal())) {
            documento.setCodPostal(null);
        }
        if (Utilerias.isVacio(documento.getImporte())) {
            documento.setImporte(null);
        }
        return documento;
    }
}
