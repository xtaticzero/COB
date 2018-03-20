/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.listener.impl;

import mx.gob.sat.siat.cob.background.holder.CargaArchivosHolder;
import mx.gob.sat.siat.cob.background.listener.MontoIcepWriterSkipListener;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.MultaIcepDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.MultaDocumento;
import mx.gob.sat.siat.cobranza.domain.Resolucion;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author root
 */
@Service("montoIcepWriterSkipListener")
@Scope("step")
public class MontoIcepWriterSkipListenerImpl implements MontoIcepWriterSkipListener {

    private Logger log = Logger.getLogger(MontoIcepWriterSkipListenerImpl.class);
    @Autowired
    private CargaArchivosHolder holder;
    @Autowired
    private MultaIcepDAO multaIcepDAO;

    @Override
    public void onSkipInRead(Throwable thrwbl) {
    }

    @Override
    public void onSkipInWrite(Resolucion resolucion, Throwable thrwbl) {
        try {
            log.error("no se pudo procesar documento multado con numero resolucion " + resolucion.getNumResolucionDet());
            log.error(thrwbl);
            multaIcepDAO.borrarMontosResolucionIcep(resolucion.getNumResolucionDet());
            MultaDocumento documento = new MultaDocumento();
            documento.setNumeroResolucion(resolucion.getNumResolucionDet());
            documento.setMonto(Double.parseDouble(resolucion.getImporteDet().toString()));
            holder.getMultasDocumentos().remove(documento);
        } catch (NumberFormatException ex) {
            log.error("No se pudo dar de baja el id Resolucion " + resolucion.getNumResolucionDet());
            log.error(ex);
        }
    }

    @Override
    public void onSkipInProcess(Object t, Throwable thrwbl) {
    }
}
