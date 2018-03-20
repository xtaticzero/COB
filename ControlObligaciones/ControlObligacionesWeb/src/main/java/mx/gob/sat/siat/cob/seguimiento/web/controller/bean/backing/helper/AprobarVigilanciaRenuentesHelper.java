/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing.helper;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VisualizaVigilanciaRenuentes;
import java.io.Serializable;

/**
 *
 * @author root
 */
public class AprobarVigilanciaRenuentesHelper implements Serializable {
     private List<VisualizaVigilanciaRenuentes> vigilancias;    
     private VisualizaVigilanciaRenuentes visualizaVigilanciaRenuentes;

    public List<VisualizaVigilanciaRenuentes> getVigilancias() {
        return vigilancias;
    }

    public void setVigilancias(List<VisualizaVigilanciaRenuentes> vigilancias) {
        this.vigilancias = vigilancias;
    }

    public VisualizaVigilanciaRenuentes getVisualizaVigilanciaRenuentes() {
        return visualizaVigilanciaRenuentes;
    }

    public void setVisualizaVigilanciaRenuentes(VisualizaVigilanciaRenuentes visualizaVigilanciaRenuentes) {
        this.visualizaVigilanciaRenuentes = visualizaVigilanciaRenuentes;
    }     
}
