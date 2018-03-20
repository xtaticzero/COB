package mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing.catalogos.model;

import java.io.Serializable;

import java.util.Date;
import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Combo;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.FundamentoLegal;

public class FundamentoLegalModel implements Serializable {

    private static final long serialVersionUID = 3995462063655855867L;
    private List<FundamentoLegal> listFundamentoLegal;
    private List<FundamentoLegal> listFundamentoLegalTmp;
    private List<Combo> listaComboObligacion;
    private List<Combo> listaComboRegimen;
    private List<Combo> listaComboEjercicioFiscal;
    private List<Combo> listaComboPeriodicidad;
    private List<Combo> listaComboPeriodo;
    private String idObligacionDescr;
    private String idRegimenDescr;
    private String idEjercicioFiscalDescr;
    private String idPeriodoDescr;
    private String fundamentoLegal;
    private Date fechaInicio;
    private Date fechaFin;
    private Date fechaVencimiento;
    private Long idObligacionFun;
    private Long idRegimenFun;
    private Long idEjercicioFiscalFun;
    private String idPeriodoFun;
    private FundamentoLegal fundamentoLegalEdit;
    private FundamentoLegal fundamentoLegalEli;
    private boolean tblVisible = true;
    private boolean nvoVisible = false;
    private boolean edtVisible = false;
    private boolean elmVisible = false;
    private boolean cargaPorArchivoVisible = false;
    private boolean agregarPorArchivo = false;

    /**
     *
     */
    public FundamentoLegalModel() {
        super();
    }

    /**
     *
     * @param listFundamentoLegal
     */
    public void setListFundamentoLegal(List<FundamentoLegal> listFundamentoLegal) {
        this.listFundamentoLegal = listFundamentoLegal;
    }

    /**
     *
     * @return
     */
    public List<FundamentoLegal> getListFundamentoLegal() {
        return listFundamentoLegal;
    }

    /**
     *
     * @param listFundamentoLegalTmp
     */
    public void setListFundamentoLegalTmp(List<FundamentoLegal> listFundamentoLegalTmp) {
        this.listFundamentoLegalTmp = listFundamentoLegalTmp;
    }

    /**
     *
     * @return
     */
    public List<FundamentoLegal> getListFundamentoLegalTmp() {
        return listFundamentoLegalTmp;
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
     * @param listaComboRegimen
     */
    public void setListaComboRegimen(List<Combo> listaComboRegimen) {
        this.listaComboRegimen = listaComboRegimen;
    }

    /**
     *
     * @return
     */
    public List<Combo> getListaComboRegimen() {
        return listaComboRegimen;
    }

    /**
     *
     * @param idObligacionDescr
     */
    public void setIdObligacionDescr(String idObligacionDescr) {
        this.idObligacionDescr = idObligacionDescr;
    }

    /**
     *
     * @return
     */
    public String getIdObligacionDescr() {
        return idObligacionDescr;
    }

    /**
     *
     * @param idRegimenDescr
     */
    public void setIdRegimenDescr(String idRegimenDescr) {
        this.idRegimenDescr = idRegimenDescr;
    }

    /**
     *
     * @return
     */
    public String getIdRegimenDescr() {
        return idRegimenDescr;
    }

    /**
     *
     * @param idEjercicioFiscalDescr
     */
    public void setIdEjercicioFiscalDescr(String idEjercicioFiscalDescr) {
        this.idEjercicioFiscalDescr = idEjercicioFiscalDescr;
    }

    /**
     *
     * @return
     */
    public String getIdEjercicioFiscalDescr() {
        return idEjercicioFiscalDescr;
    }

    /**
     *
     * @param idPeriodoDescr
     */
    public void setIdPeriodoDescr(String idPeriodoDescr) {
        this.idPeriodoDescr = idPeriodoDescr;
    }

    /**
     *
     * @return
     */
    public String getIdPeriodoDescr() {
        return idPeriodoDescr;
    }

    /**
     *
     * @param fundamentoLegal
     */
    public void setFundamentoLegal(String fundamentoLegal) {
        this.fundamentoLegal = fundamentoLegal;
    }

    /**
     *
     * @return
     */
    public String getFundamentoLegal() {
        return fundamentoLegal;
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
        if (fechaInicio != null) {
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
        if (fechaFin != null) {
            return (Date) fechaFin.clone();
        }
        return null;
    }

    /**
     *
     * @param fundamentoLegalEdit
     */
    public void setFundamentoLegalEdit(FundamentoLegal fundamentoLegalEdit) {
        this.fundamentoLegalEdit = fundamentoLegalEdit;
    }

    /**
     *
     * @return
     */
    public FundamentoLegal getFundamentoLegalEdit() {
        return fundamentoLegalEdit;
    }

    /**
     *
     * @param fundamentoLegalEli
     */
    public void setFundamentoLegalEli(FundamentoLegal fundamentoLegalEli) {
        this.fundamentoLegalEli = fundamentoLegalEli;
    }

    /**
     *
     * @return
     */
    public FundamentoLegal getFundamentoLegalEli() {
        return fundamentoLegalEli;
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
     * @param listaComboEjercicioFiscal
     */
    public void setListaComboEjercicioFiscal(List<Combo> listaComboEjercicioFiscal) {
        this.listaComboEjercicioFiscal = listaComboEjercicioFiscal;
    }

    /**
     *
     * @return
     */
    public List<Combo> getListaComboEjercicioFiscal() {
        return listaComboEjercicioFiscal;
    }

    /**
     *
     * @param listaComboPeriodicidad
     */
    public void setListaComboPeriodicidad(List<Combo> listaComboPeriodicidad) {
        this.listaComboPeriodicidad = listaComboPeriodicidad;
    }

    /**
     *
     * @return
     */
    public List<Combo> getListaComboPeriodicidad() {
        return listaComboPeriodicidad;
    }

    /**
     *
     * @param listaComboPeriodo
     */
    public void setListaComboPeriodo(List<Combo> listaComboPeriodo) {
        this.listaComboPeriodo = listaComboPeriodo;
    }

    /**
     *
     * @return
     */
    public List<Combo> getListaComboPeriodo() {
        return listaComboPeriodo;
    }

    /**
     *
     * @param idObligacionFun
     */
    public void setIdObligacionFun(Long idObligacionFun) {
        this.idObligacionFun = idObligacionFun;
    }

    /**
     *
     * @return
     */
    public Long getIdObligacionFun() {
        return idObligacionFun;
    }

    /**
     *
     * @param idRegimenFun
     */
    public void setIdRegimenFun(Long idRegimenFun) {
        this.idRegimenFun = idRegimenFun;
    }

    /**
     *
     * @return
     */
    public Long getIdRegimenFun() {
        return idRegimenFun;
    }

    /**
     *
     * @param idPeriodoFun
     */
    public void setIdPeriodoFun(String idPeriodoFun) {
        this.idPeriodoFun = idPeriodoFun;
    }

    /**
     *
     * @return
     */
    public String getIdPeriodoFun() {
        return idPeriodoFun;
    }

    /**
     *
     * @param idEjercicioFiscalFun
     */
    public void setIdEjercicioFiscalFun(Long idEjercicioFiscalFun) {
        this.idEjercicioFiscalFun = idEjercicioFiscalFun;
    }

    /**
     *
     * @return
     */
    public Long getIdEjercicioFiscalFun() {
        return idEjercicioFiscalFun;
    }

    /**
     *
     * @param fechaVencimiento
     */
    public void setFechaVencimiento(Date fechaVencimiento) {
        if (fechaVencimiento != null) {
            this.fechaVencimiento = (Date) fechaVencimiento.clone();
        }
    }

    /**
     *
     * @return
     */
    public Date getFechaVencimiento() {
        if (fechaVencimiento != null) {
            return (Date) fechaVencimiento.clone();
        }
        return null;
    }

    public boolean isCargaPorArchivoVisible() {
        return cargaPorArchivoVisible;
    }

    public void setCargaPorArchivoVisible(boolean cargaPorArchivoVisible) {
        this.cargaPorArchivoVisible = cargaPorArchivoVisible;
    }

    public boolean isAgregarPorArchivo() {
        return agregarPorArchivo;
    }

    public void setAgregarPorArchivo(boolean agregarPorArchivo) {
        this.agregarPorArchivo = agregarPorArchivo;
    }
}
