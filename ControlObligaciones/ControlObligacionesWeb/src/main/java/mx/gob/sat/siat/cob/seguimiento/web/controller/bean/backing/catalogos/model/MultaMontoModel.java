package mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing.catalogos.model;

import java.io.Serializable;

import java.util.Date;
import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Combo;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.ComboStr;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.MultaMonto;


public class MultaMontoModel implements Serializable{
    private static final long serialVersionUID = 3995462063655855867L;
    private List<MultaMonto> listOblisancion;
    private List<MultaMonto> listOblisancionTmp;

    private List<Combo> listaComboObligacion;
    private List<ComboStr> listaComboTipoMulta;
    
    private String sancion;
    private String infraccion;
    private Date   fechaInicio;
    private Date   fechaFin;
    private Long idObligacion;
    private Long   orden;
    private String resolMotivo;
    private String motivacion;
    private Long monto;
    private String descripcionMonto;
    
    private Long idObligacionSan;
    private String idTipoMultaSel;
       
    private MultaMonto oblisancionEdit;
    private MultaMonto oblisancionEli;
    
    private boolean tblVisible=true;
    private boolean nvoVisible=false;
    private boolean edtVisible=false;
    private boolean elmVisible=false;

    /**
     *
     */
    public MultaMontoModel() {
        super();
    }


    /**
     *
     * @param listOblisancion
     */
    public void setListOblisancion(List<MultaMonto> listOblisancion) {
        this.listOblisancion = listOblisancion;
    }

    /**
     *
     * @return
     */
    public List<MultaMonto> getListOblisancion() {
        return listOblisancion;
    }

    /**
     *
     * @param listOblisancionTmp
     */
    public void setListOblisancionTmp(List<MultaMonto> listOblisancionTmp) {
        this.listOblisancionTmp = listOblisancionTmp;
    }

    /**
     *
     * @return
     */
    public List<MultaMonto> getListOblisancionTmp() {
        return listOblisancionTmp;
    }

    /**
     *
     * @param sancion
     */
    public void setSancion(String sancion) {
        this.sancion = sancion;
    }

    /**
     *
     * @return
     */
    public String getSancion() {
        return sancion;
    }

    /**
     *
     * @param infraccion
     */
    public void setInfraccion(String infraccion) {
        this.infraccion = infraccion;
    }

    /**
     *
     * @return
     */
    public String getInfraccion() {
        return infraccion;
    }

    /**
     *
     * @param fechaInicio
     */
    public void setFechaInicio(Date fechaInicio) {
        if (fechaInicio != null) {
            this.fechaInicio = (Date) fechaInicio.clone();
        }
    }

    /**
     *
     * @return
     */
    public Date getFechaInicio() {
        if(fechaInicio!=null){
        return (Date) fechaInicio.clone();
        }
        return null;
    }

    /**
     *
     * @param fechaFin
     */
    public void setFechaFin(Date fechaFin) {
        if (fechaFin != null) {
            this.fechaFin = (Date) fechaFin.clone();
        }
    }

    /**
     *
     * @return
     */
    public Date getFechaFin() {
        if(fechaFin!=null){
        return (Date) fechaFin.clone();
        }
        return null;
    }

    /**
     *
     * @param idObligacion
     */
    public void setIdObligacion(Long idObligacion) {
        this.idObligacion = idObligacion;
    }

    /**
     *
     * @return
     */
    public Long getIdObligacion() {
        return idObligacion;
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
     * @param oblisancionEdit
     */
    public void setOblisancionEdit(MultaMonto oblisancionEdit) {
        this.oblisancionEdit = oblisancionEdit;
    }

    /**
     *
     * @return
     */
    public MultaMonto getOblisancionEdit() {
        return oblisancionEdit;
    }

    /**
     *
     * @param oblisancionEli
     */
    public void setOblisancionEli(MultaMonto oblisancionEli) {
        this.oblisancionEli = oblisancionEli;
    }

    /**
     *
     * @return
     */
    public MultaMonto getOblisancionEli() {
        return oblisancionEli;
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

    public void setResolMotivo(String resolMotivo) {
        this.resolMotivo = resolMotivo;
    }

    public String getResolMotivo() {
        return resolMotivo;
    }

    public void setListaComboTipoMulta(List<ComboStr> listaComboTipoMulta) {
        this.listaComboTipoMulta = listaComboTipoMulta;
    }

    public List<ComboStr> getListaComboTipoMulta() {
        return listaComboTipoMulta;
    }


    public void setIdTipoMultaSel(String idTipoMultaSel) {
        this.idTipoMultaSel = idTipoMultaSel;
    }

    public String getIdTipoMultaSel() {
        return idTipoMultaSel;
    }

    public void setMotivacion(String motivacion) {
        this.motivacion = motivacion;
    }

    public String getMotivacion() {
        return motivacion;
    }

    public void setMonto(Long monto) {
        this.monto = monto;
    }

    public Long getMonto() {
        return monto;
    }

    public void setDescripcionMonto(String descripcionMonto) {
        this.descripcionMonto = descripcionMonto;
    }

    public String getDescripcionMonto() {
        return descripcionMonto;
    }
    }
