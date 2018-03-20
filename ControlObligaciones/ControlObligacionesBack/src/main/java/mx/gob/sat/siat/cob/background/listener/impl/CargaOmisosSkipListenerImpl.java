package mx.gob.sat.siat.cob.background.listener.impl;

import mx.gob.sat.siat.cob.background.listener.CargaOmisosSkipListener;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DetalleDocumento;
import mx.gob.sat.siat.cob.background.service.carga.LogErrorCargaService;
import org.apache.log4j.Logger;
import org.springframework.batch.core.SkipListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("cargaOmisosSkipListener")
public class CargaOmisosSkipListenerImpl implements CargaOmisosSkipListener, SkipListener<Object, Object> {

    private Logger log = Logger.getLogger(CargaOmisosSkipListenerImpl.class);

    @Autowired
    private LogErrorCargaService logErrorCarga;

    public CargaOmisosSkipListenerImpl() {
    }

    @Override
    public void onSkipInRead(Throwable throwable) {
    }

    @Override
    public void onSkipInWrite(Object s, Throwable throwable) {
        log.debug("--------------onSkipInWrite:" +s.getClass()+", "+throwable.getClass());
        if(s instanceof DetalleDocumento){
            DetalleDocumento detalle=(DetalleDocumento)s;
            log.debug("escribe");
            logErrorCarga.getListaArchivos().add(detalle.getRutaArchivo());
            logErrorCarga.escribirLog("archivo:"+detalle.getRutaArchivo()+
                    ", boid:"+ detalle.getBoid() +
                    ", icep:" + detalle.getClaveIcep() +
                    ", exception:" + throwable.getClass(),detalle.getNumeroControl());
            log.debug(detalle);
        }
    }

    @Override
    public void onSkipInProcess(Object t, Throwable throwable) {
    }
}
