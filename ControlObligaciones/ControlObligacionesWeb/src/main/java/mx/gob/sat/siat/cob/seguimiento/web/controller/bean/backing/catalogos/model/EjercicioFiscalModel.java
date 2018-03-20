package mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing.catalogos.model;

import java.io.Serializable;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.EjercicioFiscal;


public class EjercicioFiscalModel  implements Serializable{
    private static final long serialVersionUID = -5197691180506547942L;
    
    private List<EjercicioFiscal> listEjercicioFiscal;
    private List<EjercicioFiscal> listEjercicioFiscalTmp;
       
    private Long idEjercicioFiscal;
    private String nombre;
    private String descripcion;
    private Long orden;
    private String fechaFinStr;
    
    private EjercicioFiscal ejercicioFiscalEdit;
    private EjercicioFiscal ejercicioFiscalEli;
    private EjercicioFiscal ejercicioFiscalReactivar;
    
    private boolean tblVisible=true;
    private boolean nvoVisible=false;
    private boolean edtVisible=false;
    private boolean elmVisible=false;

    
    /**
     *
     */
    public EjercicioFiscalModel() {
        super();
    }


    /**
     *
     * @param listEjercicioFiscal
     */
    public void setListEjercicioFiscal(List<EjercicioFiscal> listEjercicioFiscal) {
        this.listEjercicioFiscal = listEjercicioFiscal;
    }

    /**
     *
     * @return
     */
    public List<EjercicioFiscal> getListEjercicioFiscal() {
        return listEjercicioFiscal;
    }

    /**
     *
     * @param listEjercicioFiscalTmp
     */
    public void setListEjercicioFiscalTmp(List<EjercicioFiscal> listEjercicioFiscalTmp) {
        this.listEjercicioFiscalTmp = listEjercicioFiscalTmp;
    }

    /**
     *
     * @return
     */
    public List<EjercicioFiscal> getListEjercicioFiscalTmp() {
        return listEjercicioFiscalTmp;
    }

    /**
     *
     * @param idEjercicioFiscal
     */
    public void setIdEjercicioFiscal(Long idEjercicioFiscal) {
        this.idEjercicioFiscal = idEjercicioFiscal;
    }

    /**
     *
     * @return
     */
    public Long getIdEjercicioFiscal() {
        return idEjercicioFiscal;
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
     * @param ejercicioFiscalEdit
     */
    public void setEjercicioFiscalEdit(EjercicioFiscal ejercicioFiscalEdit) {
        this.ejercicioFiscalEdit = ejercicioFiscalEdit;
    }

    /**
     *
     * @return
     */
    public EjercicioFiscal getEjercicioFiscalEdit() {
        return ejercicioFiscalEdit;
    }

    /**
     *
     * @param ejercicioFiscalEli
     */
    public void setEjercicioFiscalEli(EjercicioFiscal ejercicioFiscalEli) {
        this.ejercicioFiscalEli = ejercicioFiscalEli;
    }

    /**
     *
     * @return
     */
    public EjercicioFiscal getEjercicioFiscalEli() {
        return ejercicioFiscalEli;
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
     * @param ejercicioFiscalReactivar
     */
    public void setEjercicioFiscalReactivar(EjercicioFiscal ejercicioFiscalReactivar) {
        this.ejercicioFiscalReactivar = ejercicioFiscalReactivar;
    }

    /**
     *
     * @return
     */
    public EjercicioFiscal getEjercicioFiscalReactivar() {
        return ejercicioFiscalReactivar;
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
