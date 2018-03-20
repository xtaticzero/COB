/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.processor;

import mx.gob.sat.siat.cob.seguimiento.dto.arca.ObligacionPeriodo;
import org.springframework.batch.item.ItemProcessor;

/**
 *
 * @author Daniel
 */
public interface DatosObligacionPeriodoCreditoProcessor
        extends ItemProcessor<ObligacionPeriodo, ObligacionPeriodo>{
    
}
