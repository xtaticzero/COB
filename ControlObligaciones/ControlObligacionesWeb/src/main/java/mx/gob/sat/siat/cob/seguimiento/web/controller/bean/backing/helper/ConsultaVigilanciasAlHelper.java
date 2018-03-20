/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing.helper;

import java.io.Serializable;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VigilanciaAl;

/**
 *
 * @author root
 */
public class ConsultaVigilanciasAlHelper implements Serializable{
    
    
    private VigilanciaAl vigilancia;

    public ConsultaVigilanciasAlHelper() {
        vigilancia=new VigilanciaAl();
    }

    public VigilanciaAl getVigilancia() {
        return vigilancia;
    }

    public void setVigilancia(VigilanciaAl vigilancia) {
        this.vigilancia = vigilancia;
    }
    
    
    
}
