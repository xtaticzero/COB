package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

import java.io.Serializable;


public class PrioridadJob implements Serializable{
    @SuppressWarnings("compatibility:36389944626332711")
    private static final long serialVersionUID = 3036812067110564064L;
    
    private String strPrioridad;
    private Integer   prioridad;

    public PrioridadJob() {
        super();
    }

    public void setStrPrioridad(String strPrioridad) {
        this.strPrioridad = strPrioridad;
    }

    public String getStrPrioridad() {
        return strPrioridad;
    }

    public void setPrioridad(Integer prioridad) {
        this.prioridad = prioridad;
    }

    public Integer getPrioridad() {
        return prioridad;
    }
}
