package mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing.catalogos.model;

import java.io.Serializable;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Combo;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.EstadoAdmonLocalMAT;

public class EstadoAdmonLocalMATModel implements Serializable {

    private static final long serialVersionUID = -5197691180506547942L;
    private List<EstadoAdmonLocalMAT> listEstadoAdmonLocalMAT;
    private List<EstadoAdmonLocalMAT> listEstadoAdmonLocalMATTmp;
    private String idAdmonLocal;
    private Long esOperacionMAT;
    private String entidadDesc;
    private EstadoAdmonLocalMAT estadoAdmonLocalMATEdit;
    private boolean tblVisible = true;
    private boolean nvoVisible = false;
    private boolean edtVisible = false;
    private List<Combo> listaComboBoolean;
    private Long idValorBoolean;

    public EstadoAdmonLocalMATModel() {
        super();
    }

    public List<EstadoAdmonLocalMAT> getListEstadoAdmonLocalMAT() {
        return listEstadoAdmonLocalMAT;
    }

    public void setListEstadoAdmonLocalMAT(List<EstadoAdmonLocalMAT> listEstadoAdmonLocalMAT) {
        this.listEstadoAdmonLocalMAT = listEstadoAdmonLocalMAT;
    }

    public List<EstadoAdmonLocalMAT> getListEstadoAdmonLocalMATTmp() {
        return listEstadoAdmonLocalMATTmp;
    }

    public void setListEstadoAdmonLocalMATTmp(List<EstadoAdmonLocalMAT> listEstadoAdmonLocalMATTmp) {
        this.listEstadoAdmonLocalMATTmp = listEstadoAdmonLocalMATTmp;
    }

    public String getIdAdmonLocal() {
        return idAdmonLocal;
    }

    public void setIdAdmonLocal(String idAdmonLocal) {
        this.idAdmonLocal = idAdmonLocal;
    }

    public Long getEsOperacionMAT() {
        return esOperacionMAT;
    }

    public void setEsOperacionMAT(Long esOperacionMAT) {
        this.esOperacionMAT = esOperacionMAT;
    }

    public String getEntidadDesc() {
        return entidadDesc;
    }

    public void setEntidadDesc(String entidadDesc) {
        this.entidadDesc = entidadDesc;
    }

    public EstadoAdmonLocalMAT getEstadoAdmonLocalMATEdit() {
        return estadoAdmonLocalMATEdit;
    }

    public void setEstadoAdmonLocalMATEdit(EstadoAdmonLocalMAT estadoAdmonLocalMATEdit) {
        this.estadoAdmonLocalMATEdit = estadoAdmonLocalMATEdit;
    }

    public boolean isTblVisible() {
        return tblVisible;
    }

    public void setTblVisible(boolean tblVisible) {
        this.tblVisible = tblVisible;
    }

    public boolean isNvoVisible() {
        return nvoVisible;
    }

    public void setNvoVisible(boolean nvoVisible) {
        this.nvoVisible = nvoVisible;
    }

    public boolean isEdtVisible() {
        return edtVisible;
    }

    public void setEdtVisible(boolean edtVisible) {
        this.edtVisible = edtVisible;
    }

    public List<Combo> getListaComboBoolean() {
        return listaComboBoolean;
    }

    public void setListaComboBoolean(List<Combo> listaComboBoolean) {
        this.listaComboBoolean = listaComboBoolean;
    }

    public Long getIdValorBoolean() {
        return idValorBoolean;
    }

    public void setIdValorBoolean(Long idValorBoolean) {
        this.idValorBoolean = idValorBoolean;
    }
}
