/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.listener;

import mx.gob.sat.siat.cob.seguimiento.dto.arca.DocumentoARCA;
import org.springframework.batch.core.SkipListener;

/**
 *
 * @author root
 */
public interface MultaDocumentoResolucionCreditoWriterSkipListener  extends SkipListener<Object, DocumentoARCA> {
    
}
