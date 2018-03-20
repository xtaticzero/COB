/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing;

import mx.gob.sat.siat.cob.seguimiento.utils.AbstractBaseMB;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author root
 */
@Controller("fielMB")
@Scope(value = "view")
public class FielManagedBean extends AbstractBaseMB{
    
    @Value("#{urlWidgetFirmado['ui.web.widgetFirmado']}")
    private String urlWidgetFirmado;

    public String getUrlWidgetFirmado() {
        return urlWidgetFirmado;
    }  
}
