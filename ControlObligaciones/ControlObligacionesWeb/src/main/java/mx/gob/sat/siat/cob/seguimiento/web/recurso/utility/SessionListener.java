package mx.gob.sat.siat.cob.seguimiento.web.recurso.utility;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public final class SessionListener implements HttpSessionListener {
    /**
     *
     */
    public SessionListener() {
        super();
    }

    /**
     *
     * @param httpSessionEvent
     */
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
    }

    /**
     *
     * @param httpSessionEvent
     */
    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
    }
}
