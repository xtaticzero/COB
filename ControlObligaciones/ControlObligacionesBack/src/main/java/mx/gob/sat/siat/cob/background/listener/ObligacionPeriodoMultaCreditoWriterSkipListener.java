/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.listener;

import mx.gob.sat.siat.cob.seguimiento.dto.arca.ObligacionPeriodo;
import org.springframework.batch.core.SkipListener;

/**
 *
 * @author Daniel
 */
public interface ObligacionPeriodoMultaCreditoWriterSkipListener  extends SkipListener<Object, ObligacionPeriodo> {
    
}
