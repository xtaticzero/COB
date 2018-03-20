package mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing.catalogos.model;

import java.io.Serializable;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.MtvoCancelDoc;


public class MtvoCancelDocModel  implements Serializable{
    private static final long serialVersionUID = -5197691180506547942L;
    
    private List<MtvoCancelDoc> listMtvoCancelDoc;
    private List<MtvoCancelDoc> listMtvoCancelDocTmp;
    
    private Long idMotivoCancelacion;
    private String nombre;
    private String descripcion;
    private Long orden;
    
    private MtvoCancelDoc mtvoCancelDocEdit;
    private MtvoCancelDoc mtvoCancelDocEli;
    
    private boolean tblVisible=true;
    private boolean nvoVisible=false;
    private boolean edtVisible=false;
    private boolean elmVisible=false;
    
    
    /**
     *
     */
    public MtvoCancelDocModel() {
        super();
    }

    /**
     *
     * @param listMtvoCancelDoc
     */
    public void setListMtvoCancelDoc(List<MtvoCancelDoc> listMtvoCancelDoc) {
        this.listMtvoCancelDoc = listMtvoCancelDoc;
    }

    /**
     *
     * @return
     */
    public List<MtvoCancelDoc> getListMtvoCancelDoc() {
        return listMtvoCancelDoc;
    }

    /**
     *
     * @param listMtvoCancelDocTmp
     */
    public void setListMtvoCancelDocTmp(List<MtvoCancelDoc> listMtvoCancelDocTmp) {
        this.listMtvoCancelDocTmp = listMtvoCancelDocTmp;
    }

    /**
     *
     * @return
     */
    public List<MtvoCancelDoc> getListMtvoCancelDocTmp() {
        return listMtvoCancelDocTmp;
    }

    /**
     *
     * @param idMotivoCancelacion
     */
    public void setIdMotivoCancelacion(Long idMotivoCancelacion) {
        this.idMotivoCancelacion = idMotivoCancelacion;
    }

    /**
     *
     * @return
     */
    public Long getIdMotivoCancelacion() {
        return idMotivoCancelacion;
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
     * @param mtvoCancelDocEdit
     */
    public void setMtvoCancelDocEdit(MtvoCancelDoc mtvoCancelDocEdit) {
        this.mtvoCancelDocEdit = mtvoCancelDocEdit;
    }

    /**
     *
     * @return
     */
    public MtvoCancelDoc getMtvoCancelDocEdit() {
        return mtvoCancelDocEdit;
    }

    /**
     *
     * @param mtvoCancelDocEli
     */
    public void setMtvoCancelDocEli(MtvoCancelDoc mtvoCancelDocEli) {
        this.mtvoCancelDocEli = mtvoCancelDocEli;
    }

    /**
     *
     * @return
     */
    public MtvoCancelDoc getMtvoCancelDocEli() {
        return mtvoCancelDocEli;
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

   
}
