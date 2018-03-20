/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.listener.impl;

import mx.gob.sat.siat.cob.background.listener.MontoTotalWriterCreditoSkipListener;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.MultaIcepDAO;
import mx.gob.sat.siat.cobranza.domain.Resolucion;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author Daniel
 */
@Service("montoTotalWriterCreditoSkipListener")
@Scope("step")
public class MontoTotalWriterCreditoSkipListenerImpl implements MontoTotalWriterCreditoSkipListener {

    private Logger log = Logger.getLogger(MontoTotalWriterCreditoSkipListenerImpl.class);
    @Autowired
    private MultaIcepDAO multaIcepDAO;

    @Override
    public void onSkipInRead(Throwable thrwbl) {
    }

    @Override
    public void onSkipInWrite(Resolucion multa, Throwable thrwbl) {
        try {
            log.error("no se pudo procesar documento multado con numero resolucion " + multa.getNumResolucionDet());
            log.error(thrwbl);
            multaIcepDAO.borrarMontosResolucionIcep(multa.getNumResolucionDet());
        } catch (Exception ex) {
            log.error("No se pudo borrar los montos de los iceps de la multa " + multa.getNumResolucionDet());
            log.error(ex);
        }
    }

    @Override
    public void onSkipInProcess(Object t, Throwable thrwbl) {
    }
}
