/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.holder;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 *
 * @author Juan
 */
@Component
public class HolderCargaMasivaMap {

    private Map holderCargaMasiva;

    public HolderCargaMasivaMap() {
        reset();
    }

    public Map getHolderCargaMasiva() {
        return holderCargaMasiva;
    }

    public void setHolderCargaMasiva(Map holderCargaMasiva) {
        this.holderCargaMasiva = holderCargaMasiva;
    }
    
    public void reset(){
        if(holderCargaMasiva == null){
            holderCargaMasiva = new HashMap<String, CargaArchivosHolder>();
        }
        else{
            holderCargaMasiva.clear();
        }
    }
}
