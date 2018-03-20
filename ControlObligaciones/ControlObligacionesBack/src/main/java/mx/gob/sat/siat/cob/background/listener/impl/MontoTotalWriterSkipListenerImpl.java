/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.listener.impl;

import mx.gob.sat.siat.cob.background.holder.CargaArchivosHolder;
import mx.gob.sat.siat.cob.background.listener.MontoTotalWriterSkipListener;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.MultaIcepDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.MultaDocumento;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author kapi
 */
@Service("montoTotalWriterSkipListener")
@Scope("step")
public class MontoTotalWriterSkipListenerImpl implements MontoTotalWriterSkipListener {

    private Logger log = Logger.getLogger(MontoTotalWriterSkipListenerImpl.class);
    @Autowired
    private CargaArchivosHolder holder;
    @Autowired
    private MultaIcepDAO multaIcepDAO;

    @Override
    public void onSkipInRead(Throwable thrwbl) {
    }

    @Override
    public void onSkipInWrite(MultaDocumento multa, Throwable thrwbl) {

        try {
            log.error("no se pudo procesar documento multado con numero resolucion " + multa.getNumeroResolucion());
            log.error(thrwbl);
            multaIcepDAO.borrarMontosResolucionIcep(multa.getNumeroResolucion());
            holder.getMultasDocumentos().remove(multa);
        } catch (Exception ex) {
            log.error("No se pudo dar de baja el id Resolucion " + multa.getNumeroResolucion());
            log.error(ex);
        }
    }

    @Override
    public void onSkipInProcess(Object t, Throwable thrwbl) {
    }

}
