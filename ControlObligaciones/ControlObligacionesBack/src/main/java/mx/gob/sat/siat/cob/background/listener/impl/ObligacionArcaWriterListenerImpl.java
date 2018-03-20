/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.listener.impl;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.Obligacion;
import org.springframework.batch.core.ItemWriteListener;

/**
 *
 * @author root
 */
public class ObligacionArcaWriterListenerImpl implements ItemWriteListener<Obligacion>{

    @Override
    public void beforeWrite(List<? extends Obligacion> list) {
        
    }

    @Override
    public void afterWrite(List<? extends Obligacion> list) {
        
    }

    @Override
    public void onWriteError(Exception excptn, List<? extends Obligacion> list) {
        /*DAO 
         * delete T_Obligacion
         delete T_Periodo
         delete T_Documento
         */
    }
    
}
