package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProcesoDetalle implements Serializable, Cloneable {

    private Proceso proceso;
    private List<Proceso> lanzadores = new ArrayList<Proceso>();
    private List<Proceso> excluyentes = new ArrayList<Proceso>();

    public ProcesoDetalle() {
        super();
    }

    public ProcesoDetalle(Proceso proceso, List<Proceso> lanzadores, List<Proceso> excluyentes) {
        super();
        this.proceso = proceso;
        this.lanzadores = lanzadores;
        this.excluyentes = excluyentes;
    }

    public void setProceso(Proceso proceso) {
        this.proceso = proceso;
    }

    public Proceso getProceso() {
        return proceso;
    }

    public void setLanzadores(List<Proceso> lanzadores) {
        this.lanzadores = lanzadores;
    }

    public List<Proceso> getLanzadores() {
        return lanzadores;
    }

    public void setExcluyentes(List<Proceso> excluyentes) {
        this.excluyentes = excluyentes;
    }

    public List<Proceso> getExcluyentes() {
        return excluyentes;
    }

    /**
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (o != null) {
            if (o instanceof ProcesoDetalle) {
                final ProcesoDetalle procesoO = (ProcesoDetalle) o;
                if (procesoO.excluyentes.equals(getExcluyentes())
                        && procesoO.lanzadores.equals(getLanzadores())
                        && procesoO.proceso.equals(getProceso())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.proceso != null ? this.proceso.hashCode() : 0);
        hash = 97 * hash + (this.lanzadores != null ? this.lanzadores.hashCode() : 0);
        hash = 97 * hash + (this.excluyentes != null ? this.excluyentes.hashCode() : 0);
        return hash;
    }

    @Override
    public ProcesoDetalle clone() throws CloneNotSupportedException {
        ProcesoDetalle procesoDetalle = null;
        procesoDetalle = (ProcesoDetalle) super.clone();
        procesoDetalle.setProceso(this.getProceso().clone());
        procesoDetalle.setExcluyentes(new ArrayList<Proceso>(this.getExcluyentes()));
        procesoDetalle.setLanzadores(new ArrayList<Proceso>(this.getLanzadores()));
        return procesoDetalle;
    }

}
