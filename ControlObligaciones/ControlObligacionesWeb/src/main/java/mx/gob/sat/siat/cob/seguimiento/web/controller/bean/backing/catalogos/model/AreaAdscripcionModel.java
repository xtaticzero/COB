package mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing.catalogos.model;

import java.io.Serializable;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.AreaAdscripcion;

public class AreaAdscripcionModel implements Serializable{
    private static final long serialVersionUID = -5197691180506547942L;
    
    private List<AreaAdscripcion> listAreaAdscripcion;
    private List<AreaAdscripcion> listAreaAdscripcionTmp;
       
    private Long idAreaAdscripcion;
    private String nombre;
    private String descripcion;
    private String fechaFinStr;
    private String situacion;
    
    private AreaAdscripcion areaAdscripcionEdit;
    private AreaAdscripcion areaAdscripcionEli;
    
    private boolean tblVisible=true;
    private boolean nvoVisible=false;
    private boolean edtVisible=false;
    private boolean elmVisible=false;
    
    
    public AreaAdscripcionModel() {
        super();
    }

    public void setListAreaAdscripcion(List<AreaAdscripcion> listAreaAdscripcion) {
        this.listAreaAdscripcion = listAreaAdscripcion;
    }

    public List<AreaAdscripcion> getListAreaAdscripcion() {
        return listAreaAdscripcion;
    }

    public void setListAreaAdscripcionTmp(List<AreaAdscripcion> listAreaAdscripcionTmp) {
        this.listAreaAdscripcionTmp = listAreaAdscripcionTmp;
    }

    public List<AreaAdscripcion> getListAreaAdscripcionTmp() {
        return listAreaAdscripcionTmp;
    }

    public void setIdAreaAdscripcion(Long idAreaAdscripcion) {
        this.idAreaAdscripcion = idAreaAdscripcion;
    }

    public Long getIdAreaAdscripcion() {
        return idAreaAdscripcion;
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

    public void setAreaAdscripcionEdit(AreaAdscripcion areaAdscripcionEdit) {
        this.areaAdscripcionEdit = areaAdscripcionEdit;
    }

    public AreaAdscripcion getAreaAdscripcionEdit() {
        return areaAdscripcionEdit;
    }

    public void setAreaAdscripcionEli(AreaAdscripcion areaAdscripcionEli) {
        this.areaAdscripcionEli = areaAdscripcionEli;
    }

    public AreaAdscripcion getAreaAdscripcionEli() {
        return areaAdscripcionEli;
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
