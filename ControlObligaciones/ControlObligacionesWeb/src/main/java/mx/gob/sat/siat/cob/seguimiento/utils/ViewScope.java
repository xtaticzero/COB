package mx.gob.sat.siat.cob.seguimiento.utils;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Map;
import javax.faces.context.FacesContext;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

/**
 *
 * @author Ing. Rodrigo Amador Martinez Pacheco<orugiho@hotmail.com>
 */
public class ViewScope implements Scope {

    /**
     *
     * @param name
     * @param objectFactory
     * @return
     */
    @Override
    public Object get(String name, ObjectFactory objectFactory) {
        Map<String, Object> viewMap = FacesContext.getCurrentInstance().getViewRoot().getViewMap();

        if (viewMap.containsKey(name)) {
            return viewMap.get(name);
        } else {
            Object object = objectFactory.getObject();
            viewMap.put(name, object);

            return object;
        }
    }

    /**
     *
     * @param name
     * @return
     */
    @Override
    public Object remove(String name) {
        return FacesContext.getCurrentInstance().getViewRoot().getViewMap().remove(name);
    }

    /**
     *
     * @return
     */
    @Override
    public String getConversationId() {
        return null;
    }

    /**
     *
     * @param name
     * @param callback
     */
    @Override
    public void registerDestructionCallback(String name, Runnable callback) {
        //Not supported
    }

    /**
     *
     * @param key
     * @return
     */
    @Override
    public Object resolveContextualObject(String key) {
        return null;
    }
}
