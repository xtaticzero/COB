/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing.helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import mx.gob.sat.siat.cob.seguimiento.dto.db2.Paginador;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DocumentoAprobar;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.IcepsAprobar;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VigilanciaAprobar;
import mx.gob.sat.siat.cob.seguimiento.util.IntegerMutable;

/**
 *
 * @author root
 */
public class AprobarDocumentosHelper implements Serializable{
    
    private List<DocumentoAprobar> documentos;
    private final Set<DocumentoAprobar> documentosSeleccionados;
    private VigilanciaAprobar vigilanciaAprobar;
    private List<IcepsAprobar> icepsAprobar;
    private Paginador paginador;
    private Paginador paginadorIceps;
    private long pagina;
    private long paginaIceps;
    private IntegerMutable progress;
    private IntegerMutable progressValidacion;
    private boolean cumplimientosEjecutados;
    private String filtroRFC;

    public AprobarDocumentosHelper() {
        documentos=new ArrayList<DocumentoAprobar>(0);
        icepsAprobar=new ArrayList<IcepsAprobar>();
        documentosSeleccionados=new HashSet<DocumentoAprobar>(0);
        progress=new IntegerMutable();
        progressValidacion=new IntegerMutable();
    }

    public List<DocumentoAprobar> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<DocumentoAprobar> documentos) {
        this.documentos = documentos;
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

    public Set<DocumentoAprobar> getDocumentosSeleccionados() {
        return documentosSeleccionados;
    }

    public boolean isCumplimientosEjecutados() {
        return cumplimientosEjecutados;
    }

    public void setCumplimientosEjecutados(boolean cumplimientosEjecutados) {
        this.cumplimientosEjecutados = cumplimientosEjecutados;
    }

    public List<IcepsAprobar> getIcepsAprobar() {
        return icepsAprobar;
    }

    public void setIcepsAprobar(List<IcepsAprobar> icepsAprobar) {
        this.icepsAprobar = icepsAprobar;
    }

    public Paginador getPaginadorIceps() {
        return paginadorIceps;
    }

    public void setPaginadorIceps(Paginador paginadorIceps) {
        this.paginadorIceps = paginadorIceps;
    }

    public long getPaginaIceps() {
        return paginaIceps;
    }

    public void setPaginaIceps(long paginaIceps) {
        this.paginaIceps = paginaIceps;
    }

    public IntegerMutable getProgress() {
        return progress;
    }

    public void setProgress(IntegerMutable progress) {
        this.progress = progress;
    }

    public VigilanciaAprobar getVigilanciaAprobar() {
        return vigilanciaAprobar;
    }

    public void setVigilanciaAprobar(VigilanciaAprobar vigilanciaAprobar) {
        this.vigilanciaAprobar = vigilanciaAprobar;
    }

    public IntegerMutable getProgressValidacion() {
        return progressValidacion;
    }

    public void setProgressValidacion(IntegerMutable progressValidacion) {
        this.progressValidacion = progressValidacion;
    }

    public String getFiltroRFC() {
        return filtroRFC;
    }

    public void setFiltroRFC(String filtroRFC) {
        this.filtroRFC = filtroRFC;
    }
}
