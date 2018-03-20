/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing.helper;

import java.io.Serializable;
import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.MultaAprobarGrupo;
import mx.gob.sat.siat.cob.seguimiento.util.IntegerMutable;

/**
 *
 * @author root
 */
public class AprobarMultaGruposManagedBeanHelper implements Serializable {

    private List<MultaAprobarGrupo> listaMultasGrupo;
    private MultaAprobarGrupo multaAprobarDescarga;
    private String nombreArchivoDescarga;
    private IntegerMutable progress;

    public List<MultaAprobarGrupo> getListaMultasGrupo() {
        return listaMultasGrupo;
    }

    public void setListaMultasGrupo(List<MultaAprobarGrupo> listaMultasGrupo) {
        this.listaMultasGrupo = listaMultasGrupo;
    }

    public MultaAprobarGrupo getMultaAprobarDescarga() {
        return multaAprobarDescarga;
    }

    public void setMultaAprobarDescarga(MultaAprobarGrupo multaAprobarDescarga) {
        this.multaAprobarDescarga = multaAprobarDescarga;
    }

    public String getNombreArchivoDescarga() {
        return nombreArchivoDescarga;
    }

    public void setNombreArchivoDescarga(String nombreArchivoDescarga) {
        this.nombreArchivoDescarga = nombreArchivoDescarga;
    }

    public IntegerMutable getProgress() {
        return progress;
    }

    public void setProgress(IntegerMutable progress) {
        this.progress = progress;
    }
}
