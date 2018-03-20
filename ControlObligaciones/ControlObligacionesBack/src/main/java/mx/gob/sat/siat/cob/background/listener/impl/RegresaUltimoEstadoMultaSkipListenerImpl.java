/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.listener.impl;

import mx.gob.sat.siat.cob.background.listener.RegresaUltimoEstadoMultaSkipListener;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.DocumentoARCA;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoMultaEnum;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.service.afectaciones.MultaService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author Daniel
 */
@Service("regresaUltimoEstadoMultaSkipListener")
@Scope("step")
public class RegresaUltimoEstadoMultaSkipListenerImpl implements RegresaUltimoEstadoMultaSkipListener {

    private Logger log = Logger.getLogger(RegresaUltimoEstadoMultaSkipListenerImpl.class);
    @Autowired
    private MultaService multaService;

    @Override
    public void onSkipInRead(Throwable thrwbl) {
    }

    @Override
    public void onSkipInWrite(DocumentoARCA resolucion, Throwable thrwbl) {
        try {
            log.error("no se pudo procesar documento multado con numero resolucion " + resolucion.getId());
            multaService.actualizarUltimoEstadoMulta(resolucion.getId(), EstadoMultaEnum.AUTORIZADA);
        } catch (SGTServiceException ex) {
            log.error("No se pudo regresar el ultimo estado de la multa a Autorizada");
            log.error(ex);
        }
    }

    @Override
    public void onSkipInProcess(Object t, Throwable thrwbl) {
    }
}
