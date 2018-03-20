/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing;

import mx.gob.sat.siat.cob.seguimiento.utils.AbstractBaseMB;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author emmanuel
 */
@Controller("redirectBean")
@Scope(value = "view")
public class RedirectManageBean extends AbstractBaseMB{
    @Value("#{urlRedirect['ui.web.redirect.sessiontimeout']}")
    private String urlRedirect;
    @Value("#{urlRedirect['timeout.value']}")
    private String timeOut;
    private final static String MILISEGUNDOS_5MIN = "300000"; 

    public String getUrlRedirect() {
        return urlRedirect;
    }

    public void setUrlRedirect(String urlRedirect) {
        this.urlRedirect = urlRedirect;
    }

    public String getTimeOut() {
        if(timeOut==null){
            timeOut=MILISEGUNDOS_5MIN;
        }
        return timeOut;
    }

    public void setTimeOut(String timeOut) {
        this.timeOut = timeOut;
    }
}
