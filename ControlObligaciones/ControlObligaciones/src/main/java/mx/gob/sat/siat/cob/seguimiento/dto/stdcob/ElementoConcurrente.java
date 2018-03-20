/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoServiciosConcurrentesEnum;

/**
 *
 * @author root
 */
public class ElementoConcurrente {
    private TipoServiciosConcurrentesEnum idServicio;
    private String firmaProceso;
    private boolean checado;

    public ElementoConcurrente(TipoServiciosConcurrentesEnum idServicio, String firmaProceso) {
        this.idServicio = idServicio;
        this.firmaProceso = firmaProceso;
    }

    
    public TipoServiciosConcurrentesEnum getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(TipoServiciosConcurrentesEnum idServicio) {
        this.idServicio = idServicio;
    }

    public String getFirmaProceso() {
        return firmaProceso;
    }

    public void setFirmaProceso(String firmaProceso) {
        this.firmaProceso = firmaProceso;
    }

    public boolean isChecado() {
        return checado;
    }

    public void setChecado(boolean checado) {
        this.checado = checado;
    }

    @Override
    public String toString() {
        return "ElementoConcurrente{" + "idServicio=" + idServicio + ", firmaProceso=" + firmaProceso + ", checado=" + checado + '}';
    } 
}
