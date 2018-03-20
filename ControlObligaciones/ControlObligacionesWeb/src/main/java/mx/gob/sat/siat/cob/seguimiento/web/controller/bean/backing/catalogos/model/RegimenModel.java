package mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing.catalogos.model;

import java.io.Serializable;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Regimen;


public class RegimenModel implements Serializable{
    private static final long serialVersionUID = -5197691180506547942L;
    
    private List<Regimen> listRegimen;
    private List<Regimen> listRegimenTmp;

    
    private Long idRegimen;
    private String nombre;
    private String descripcion;
    private Long orden;
    private String fechaFinStr;
    
    private Regimen regimenEdit;
    private Regimen regimenEli;
    private Regimen regimenReactivar;
    
    private boolean tblVisible=true;
    private boolean nvoVisible=false;
    private boolean edtVisible=false;
    private boolean elmVisible=false;
    
    
    /**
     *
     */
    public RegimenModel() {
        super();
    }

    /**
     *
     * @param listRegimen
     */
    public void setListRegimen(List<Regimen> listRegimen) {
        this.listRegimen = listRegimen;
    }

    /**
     *
     * @return
     */
    public List<Regimen> getListRegimen() {
        return listRegimen;
    }

    /**
     *
     * @param listRegimenTmp
     */
    public void setListRegimenTmp(List<Regimen> listRegimenTmp) {
        this.listRegimenTmp = listRegimenTmp;
    }

    /**
     *
     * @return
     */
    public List<Regimen> getListRegimenTmp() {
        return listRegimenTmp;
    }


    /**
     *
     * @param idRegimen
     */
    public void setIdRegimen(Long idRegimen) {
        this.idRegimen = idRegimen;
    }

    /**
     *
     * @return
     */
    public Long getIdRegimen() {
        return idRegimen;
    }

    /**
     *
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     *
     * @return
     */
    public String getNombre() {
        return nombre;
    }

    /**
     *
     * @param descripcion
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     *
     * @return
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     *
     * @param orden
     */
    public void setOrden(Long orden) {
        this.orden = orden;
    }

    /**
     *
     * @return
     */
    public Long getOrden() {
        return orden;
    }

    /**
     *
     * @param regimenEdit
     */
    public void setRegimenEdit(Regimen regimenEdit) {
        this.regimenEdit = regimenEdit;
    }

    /**
     *
     * @return
     */
    public Regimen getRegimenEdit() {
        return regimenEdit;
    }

    /**
     *
     * @param regimenEli
     */
    public void setRegimenEli(Regimen regimenEli) {
        this.regimenEli = regimenEli;
    }

    /**
     *
     * @return
     */
    public Regimen getRegimenEli() {
        return regimenEli;
    }

    /**
     *
     * @param regimenReactivar
     */
    public void setRegimenReactivar(Regimen regimenReactivar) {
        this.regimenReactivar = regimenReactivar;
    }

    /**
     *
     * @return
     */
    public Regimen getRegimenReactivar() {
        return regimenReactivar;
    }

    /**
     *
     * @param tblVisible
     */
    public void setTblVisible(boolean tblVisible) {
        this.tblVisible = tblVisible;
    }

    /**
     *
     * @return
     */
    public boolean isTblVisible() {
        return tblVisible;
    }

    /**
     *
     * @param nvoVisible
     */
    public void setNvoVisible(boolean nvoVisible) {
        this.nvoVisible = nvoVisible;
    }

    /**
     *
     * @return
     */
    public boolean isNvoVisible() {
        return nvoVisible;
    }

    /**
     *
     * @param edtVisible
     */
    public void setEdtVisible(boolean edtVisible) {
        this.edtVisible = edtVisible;
    }

    /**
     *
     * @return
     */
    public boolean isEdtVisible() {
        return edtVisible;
    }

    /**
     *
     * @param elmVisible
     */
    public void setElmVisible(boolean elmVisible) {
        this.elmVisible = elmVisible;
    }

    /**
     *
     * @return
     */
    public boolean isElmVisible() {
        return elmVisible;
    }

    
    /**
     *
     * @param fechaFinStr
     */
    public void setFechaFinStr(String fechaFinStr) {
        this.fechaFinStr = fechaFinStr;
    }

    /**
     *
     * @return
     */
    public String getFechaFinStr() {
        return fechaFinStr;
    }
}
