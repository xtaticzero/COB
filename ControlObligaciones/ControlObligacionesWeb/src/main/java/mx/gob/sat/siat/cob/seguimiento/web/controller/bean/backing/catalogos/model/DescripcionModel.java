package mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing.catalogos.model;

import java.io.Serializable;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Descripcion;


public class DescripcionModel  implements Serializable{
    private static final long serialVersionUID = -5197691180506547942L;
    
    private List<Descripcion> listDescripcion;
    private List<Descripcion> listDescripcionTmp;
    
    private Long idDescripcion;
    private String descripcion;
    private Long orden;
    
    private Descripcion descripcionEdit;
    private Descripcion descripcionEli;
    
    private boolean tblVisible=true;
    private boolean nvoVisible=false;
    private boolean edtVisible=false;
    private boolean elmVisible=false;
        
    public DescripcionModel() {
        super();
    }

    public void setListDescripcion(List<Descripcion> listDescripcion) {
        this.listDescripcion = listDescripcion;
    }

    public List<Descripcion> getListDescripcion() {
        return listDescripcion;
    }

    public void setListDescripcionTmp(List<Descripcion> listDescripcionTmp) {
        this.listDescripcionTmp = listDescripcionTmp;
    }

    public List<Descripcion> getListDescripcionTmp() {
        return listDescripcionTmp;
    }

    public void setIdDescripcion(Long idDescripcion) {
        this.idDescripcion = idDescripcion;
    }

    public Long getIdDescripcion() {
        return idDescripcion;
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

    public void setDescripcionEdit(Descripcion descripcionEdit) {
        this.descripcionEdit = descripcionEdit;
    }

    public Descripcion getDescripcionEdit() {
        return descripcionEdit;
    }

    public void setDescripcionEli(Descripcion descripcionEli) {
        this.descripcionEli = descripcionEli;
    }

    public Descripcion getDescripcionEli() {
        return descripcionEli;
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
