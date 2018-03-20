/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing.helper;

import java.io.Serializable;
import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.db2.Paginador;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.MultaAprobar;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.MultaAprobarDetalle;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.MultaAprobarGrupo;
import mx.gob.sat.siat.cob.seguimiento.util.IntegerMutable;

/**
 *
 * @author root
 */
public class AprobarMultasManagedBeanHelper implements Serializable{
    private List<MultaAprobar> listaMultaAprobar;
    private List<MultaAprobar> listaMultaNoAprobar;
    private Paginador paginador;
    private long pagina;
    private MultaAprobarGrupo multaAprobarGrupo;
    private List<MultaAprobarDetalle> listaDetalle;
    private MultaAprobar multaSeleccionada;
    private IntegerMutable progress;

    public AprobarMultasManagedBeanHelper() {
        progress=new IntegerMutable();
    }
    
    

    public List<MultaAprobar> getListaMultaAprobar() {
        return listaMultaAprobar;
    }

    public void setListaMultaAprobar(List<MultaAprobar> listaMultaAprobar) {
        this.listaMultaAprobar = listaMultaAprobar;
    }

    public Paginador getPaginador() {
        return paginador;
    }

    public void setPaginador(Paginador paginador) {
        this.paginador = paginador;
    }

    public long getPagina() {
        return pagina;
    }

    public void setPagina(long pagina) {
        this.pagina = pagina;
    }

    public MultaAprobarGrupo getMultaAprobarGrupo() {
        return multaAprobarGrupo;
    }

    public void setMultaAprobarGrupo(MultaAprobarGrupo multaAprobarGrupo) {
        this.multaAprobarGrupo = multaAprobarGrupo;
    }

    public List<MultaAprobarDetalle> getListaDetalle() {
        return listaDetalle;
    }

    public void setListaDetalle(List<MultaAprobarDetalle> listaDetalle) {
        this.listaDetalle = listaDetalle;
    }

    public MultaAprobar getMultaSeleccionada() {
        return multaSeleccionada;
    }

    public void setMultaSeleccionada(MultaAprobar multaSeleccionada) {
        this.multaSeleccionada = multaSeleccionada;
    }
    public List<MultaAprobar> getListaMultaNoAprobar() {
        return listaMultaNoAprobar;
    }

    public void setListaMultaNoAprobar(List<MultaAprobar> listaMultaNoAprobar) {
        this.listaMultaNoAprobar = listaMultaNoAprobar;
    }

    public IntegerMutable getProgress() {
        return progress;
    }

    public void setProgress(IntegerMutable progress) {
        this.progress = progress;
    }
    
    
}
