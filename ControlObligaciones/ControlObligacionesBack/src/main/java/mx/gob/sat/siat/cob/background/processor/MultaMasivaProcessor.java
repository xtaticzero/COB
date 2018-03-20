/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.processor;

import mx.gob.sat.siat.cob.seguimiento.dto.arca.DocumentoARCA;
import org.springframework.batch.item.ItemProcessor;

/**
 *
 * @author Juan
 */
public interface MultaMasivaProcessor extends ItemProcessor<DocumentoARCA, DocumentoARCA>{
    
}
