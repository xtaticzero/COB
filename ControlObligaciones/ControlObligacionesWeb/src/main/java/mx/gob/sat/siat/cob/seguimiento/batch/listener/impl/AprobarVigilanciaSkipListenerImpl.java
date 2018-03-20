package mx.gob.sat.siat.cob.seguimiento.batch.listener.impl;

import mx.gob.sat.siat.cob.seguimiento.batch.listener.AprobarVigilanciaSkipListener;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.FirmaDigital;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.FirmaDigitalDTO;
import org.apache.log4j.Logger;
import org.springframework.batch.core.SkipListener;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author christian.ventura
 */
@Service("aprobarVigilanciaSkipListener")
@Scope("step")
public class AprobarVigilanciaSkipListenerImpl implements AprobarVigilanciaSkipListener, SkipListener<Object, Object> {
    
    private Logger log = Logger.getLogger(AprobarVigilanciaSkipListenerImpl.class);
    
    /**
     *
     * @param throwable
     */
    @Override
    public void onSkipInRead(Throwable throwable) {
        log.debug("--------------onSkipInRead: "+throwable.getCause().getMessage());
    }

    /**
     *
     * @param s
     * @param throwable
     */
    @Override
    public void onSkipInWrite(Object s, Throwable throwable) {
        log.debug("--------------onSkipInWrite:" +s.getClass()+", "+throwable.getClass());
        if(s instanceof FirmaDigital){
            FirmaDigital detalle=(FirmaDigital)s;
            log.debug(detalle);
        }
    }

    /**
     *
     * @param t
     * @param throwable
     */
    @Override
    public void onSkipInProcess(Object t, Throwable throwable) {
        log.debug("--------------onSkipInProcess: "+throwable.getCause().getMessage());
        if(t instanceof FirmaDigital){
            FirmaDigital detalle=(FirmaDigital)t;
            log.debug(detalle);
        }
        if(t instanceof FirmaDigitalDTO){
            FirmaDigitalDTO detalle=(FirmaDigitalDTO)t;
            log.debug(detalle);
        }
    }
    
}
