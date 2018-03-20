package mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing.catalogos.model;

import java.io.Serializable;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Combo;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Obligacion;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.ValorBooleano;


public class ObligacionModel implements Serializable{
    private static final long serialVersionUID = 3995462063655855867L;
    private List<Obligacion> listObligacion;
    private List<Obligacion> listObligacionTmp;
    private List<ValorBooleano> listValorBooleano;
    private List<Combo> listaComboObligacion;
       
    private String concepto;
    private String descripcion;
    private Long idObligacionSan;
    private Long idValorBooleano;
    private String valorBoolStr;
    private ValorBooleano valorBooleano;
    private String fechaFinStr;
    
    private Obligacion obligacionEdit;
    private Obligacion obligacionEli;
    
    private boolean tblVisible=true;
    private boolean nvoVisible=false;
    private boolean edtVisible=false;
    private boolean elmVisible=false;
    
    
    /**
     *
     */
    public ObligacionModel() {
        super();
    }

    /**
     *
     * @param listObligacion
     */
    public void setListObligacion(List<Obligacion> listObligacion) {
        this.listObligacion = listObligacion;
    }

    /**
     *
     * @return
     */
    public List<Obligacion> getListObligacion() {
        return listObligacion;
    }

    /**
     *
     * @param listObligacionTmp
     */
    public void setListObligacionTmp(List<Obligacion> listObligacionTmp) {
        this.listObligacionTmp = listObligacionTmp;
    }

    /**
     *
     * @return
     */
    public List<Obligacion> getListObligacionTmp() {
        return listObligacionTmp;
    }
    /**
     *
     * @param concepto
     */
    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    /**
     *
     * @return
     */
    public String getConcepto() {
        return concepto;
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
     * @param obligacionEdit
     */
    public void setObligacionEdit(Obligacion obligacionEdit) {
        this.obligacionEdit = obligacionEdit;
    }

    /**
     *
     * @return
     */
    public Obligacion getObligacionEdit() {
        return obligacionEdit;
    }

    /**
     *
     * @param obligacionEli
     */
    public void setObligacionEli(Obligacion obligacionEli) {
        this.obligacionEli = obligacionEli;
    }

    /**
     *
     * @return
     */
    public Obligacion getObligacionEli() {
        return obligacionEli;
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
     * @param listValorBooleano
     */
    public void setListValorBooleano(List<ValorBooleano> listValorBooleano) {
        this.listValorBooleano = listValorBooleano;
    }

    /**
     *
     * @return
     */
    public List<ValorBooleano> getListValorBooleano() {
        return listValorBooleano;
    }


    /**
     *
     * @param valorBooleano
     */
    public void setValorBooleano(ValorBooleano valorBooleano) {
        this.valorBooleano = valorBooleano;
    }

    /**
     *
     * @return
     */
    public ValorBooleano getValorBooleano() {
        return valorBooleano;
    }

  
    /**
     *
     * @param valorBoolStr
     */
    public void setValorBoolStr(String valorBoolStr) {
        this.valorBoolStr = valorBoolStr;
    }

    /**
     *
     * @return
     */
    public String getValorBoolStr() {
        return valorBoolStr;
    }

    /**
     *
     * @param idValorBooleano
     */
    public void setIdValorBooleano(Long idValorBooleano) {
        this.idValorBooleano = idValorBooleano;
    }

    /**
     *
     * @return
     */
    public Long getIdValorBooleano() {
        return idValorBooleano;
    }

    /**
     *
     * @param listaComboObligacion
     */
    public void setListaComboObligacion(List<Combo> listaComboObligacion) {
        this.listaComboObligacion = listaComboObligacion;
    }

    /**
     *
     * @return
     */
    public List<Combo> getListaComboObligacion() {
        return listaComboObligacion;
    }

    /**
     *
     * @param idObligacionSan
     */
    public void setIdObligacionSan(Long idObligacionSan) {
        this.idObligacionSan = idObligacionSan;
    }

    /**
     *
     * @return
     */
    public Long getIdObligacionSan() {
        return idObligacionSan;
    }

    /**
     *
     * @return
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
