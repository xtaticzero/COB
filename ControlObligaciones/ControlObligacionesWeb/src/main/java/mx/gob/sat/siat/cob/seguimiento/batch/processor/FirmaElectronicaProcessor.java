/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.batch.processor;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.FirmaDigital;
import org.springframework.batch.item.ItemProcessor;

/**
 *
 * @author root
 */
public interface FirmaElectronicaProcessor extends ItemProcessor<FirmaDigital, FirmaDigital> {
    
}
