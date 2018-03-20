package mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing.catalogos.model;

import java.io.Serializable;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.AdmonLocal;

public class AdmonLocalModel implements Serializable{
    private static final long serialVersionUID = -5197691180506547942L;
    
    private List<AdmonLocal> listAdmonLocal;
    private List<AdmonLocal> listAdmonLocalTmp;
       
    private String idAdmonLocal;
    private String nombre;
    private String descripcion;
    private String fechaFinStr;
    private String situacion;
    
    private AdmonLocal admonLocalEdit;
    private AdmonLocal admonLocalEli;
    
    private boolean tblVisible=true;
    private boolean nvoVisible=false;
    private boolean edtVisible=false;
    private boolean elmVisible=false;
    
    public AdmonLocalModel() {
        super();
    }

    public void setListAdmonLocal(List<AdmonLocal> listAdmonLocal) {
        this.listAdmonLocal = listAdmonLocal;
    }

    public List<AdmonLocal> getListAdmonLocal() {
        return listAdmonLocal;
    }

    public void setListAdmonLocalTmp(List<AdmonLocal> listAdmonLocalTmp) {
        this.listAdmonLocalTmp = listAdmonLocalTmp;
    }

    public List<AdmonLocal> getListAdmonLocalTmp() {
        return listAdmonLocalTmp;
    }

    public void setIdAdmonLocal(String idAdmonLocal) {
        this.idAdmonLocal = idAdmonLocal;
    }

    public String getIdAdmonLocal() {
        return idAdmonLocal;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setFechaFinStr(String fechaFinStr) {
        this.fechaFinStr = fechaFinStr;
    }

    public String getFechaFinStr() {
        return fechaFinStr;
    }

    public void setSituacion(String situacion) {
        this.situacion = situacion;
    }

    public String getSituacion() {
        return situacion;
    }

    public void setAdmonLocalEdit(AdmonLocal admonLocalEdit) {
        this.admonLocalEdit = admonLocalEdit;
    }

    public AdmonLocal getAdmonLocalEdit() {
        return admonLocalEdit;
    }

    public void setAdmonLocalEli(AdmonLocal admonLocalEli) {
        this.admonLocalEli = admonLocalEli;
    }

    public AdmonLocal getAdmonLocalEli() {
        return admonLocalEli;
    }

    public void setTblVisible(boolean tblVisible) {
        this.tblVisible = tblVisible;
    }

    public boolean isTblVisible() {
        return tblVisible;
    }

    public void setNvoVisible(boolean nvoVisible) {
        this.nvoVisible = nvoVisible;
    }

    public boolean isNvoVisible() {
        return nvoVisible;
    }

    public void setEdtVisible(boolean edtVisible) {
        this.edtVisible = edtVisible;
    }

    public boolean isEdtVisible() {
        return edtVisible;
    }

    public void setElmVisible(boolean elmVisible) {
        this.elmVisible = elmVisible;
    }

    public boolean isElmVisible() {
        return elmVisible;
    }
}
