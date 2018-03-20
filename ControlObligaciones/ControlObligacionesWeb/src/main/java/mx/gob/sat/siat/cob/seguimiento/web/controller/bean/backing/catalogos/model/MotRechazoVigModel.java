package mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing.catalogos.model;

import java.io.Serializable;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.MotRechazoVig;


public class MotRechazoVigModel  implements Serializable{
    private static final long serialVersionUID = -5197691180506547942L;
    
    private List<MotRechazoVig> listMotRechazoVig;
    private List<MotRechazoVig> listMotRechazoVigTmp;
    
    private Long idMotRechazoVig;
    private String nombre;
    private String descripcion;
    private Long orden;
    
    private MotRechazoVig motRechazoVigEdit;
    private MotRechazoVig motRechazoVigEli;
    
    private boolean tblVisible=true;
    private boolean nvoVisible=false;
    private boolean edtVisible=false;
    private boolean elmVisible=false;
    
    public MotRechazoVigModel() {
        super();
    }

    public List<MotRechazoVig> getListMotRechazoVigTmp() {
        return listMotRechazoVigTmp;
    }

    public void setIdMotRechazoVig(Long idMotRechazoVig) {
        this.idMotRechazoVig = idMotRechazoVig;
    }

    public Long getIdMotRechazoVig() {
        return idMotRechazoVig;
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

    public void setOrden(Long orden) {
        this.orden = orden;
    }

    public Long getOrden() {
        return orden;
    }

    public void setMotRechazoVigEdit(MotRechazoVig motRechazoVigEdit) {
        this.motRechazoVigEdit = motRechazoVigEdit;
    }

    public MotRechazoVig getMotRechazoVigEdit() {
        return motRechazoVigEdit;
    }

    public void setMotRechazoVigEli(MotRechazoVig motRechazoVigEli) {
        this.motRechazoVigEli = motRechazoVigEli;
    }

    public MotRechazoVig getMotRechazoVigEli() {
        return motRechazoVigEli;
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

    public void setListMotRechazoVig(List<MotRechazoVig> listMotRechazoVig) {
        this.listMotRechazoVig = listMotRechazoVig;
    }

    public List<MotRechazoVig> getListMotRechazoVig() {
        return listMotRechazoVig;
    }

    public void setListMotRechazoVigTmp(List<MotRechazoVig> listMotRechazoVigTmp) {
        this.listMotRechazoVigTmp = listMotRechazoVigTmp;
    }
}
