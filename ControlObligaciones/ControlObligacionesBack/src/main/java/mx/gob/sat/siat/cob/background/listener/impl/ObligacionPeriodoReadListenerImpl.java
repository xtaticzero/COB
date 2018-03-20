/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.listener.impl;

import mx.gob.sat.siat.cob.background.holder.CargaArchivosHolder;
import mx.gob.sat.siat.cob.background.listener.ObligacionPeriodoReadListener;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.ObligacionPeriodo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author root ELIMINAR
 */
@Service("obligacionPeriodoReadListener")
@Scope("step")
public class ObligacionPeriodoReadListenerImpl implements ObligacionPeriodoReadListener {

    @Autowired
    private CargaArchivosHolder cargaArchivosHolder;
    private static Logger log = Logger.getLogger(ObligacionPeriodoReadListenerImpl.class);

    public ObligacionPeriodoReadListenerImpl() {
        log.info("### ObligacionPeriodoReadListenerImpl");
    }

    
    @Override
    public void beforeRead() {
    }

    @Override
    public void afterRead(ObligacionPeriodo obligacionPeriodo) {
        log.info("AFT _ OBLIGACION " + obligacionPeriodo.getObligacion().getIdDocumento());
        cargaArchivosHolder.getObligacionPeriodos().add(obligacionPeriodo);
    }

    @Override
    public void onReadError(Exception excptn) {
    }
}
