package mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing.catalogos.model;

import java.io.Serializable;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Combo;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.ComboStr;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.MultaDescuento;


public class MultaDescuentoModel  implements Serializable{
    private static final long serialVersionUID = -5197691180506547942L;
    private List<MultaDescuento> listMultaMonto;
    private List<MultaDescuento> listMultaMontoTmp;
   
    private List<ComboStr> listaComboTipoMulta;
    private List<Combo> listaComboMultaDescuento;
    
    private MultaDescuento multaMonto;
   
    private String  idTipoMultaSel;
    private Long    idMultaDescuentoSel;
       
       
    private boolean tblVisible=true;
    private boolean nvoVisible=false;
   
    
    public MultaDescuentoModel() {
        super();
    }


   

    public void setListaComboTipoMulta(List<ComboStr> listaComboTipoMulta) {
        this.listaComboTipoMulta = listaComboTipoMulta;
    }

    public List<ComboStr> getListaComboTipoMulta() {
        return listaComboTipoMulta;
    }

    public void setListaComboMultaDescuento(List<Combo> listaComboMultaDescuento) {
        this.listaComboMultaDescuento = listaComboMultaDescuento;
    }

    public List<Combo> getListaComboMultaDescuento() {
        return listaComboMultaDescuento;
    }


    public void setIdTipoMultaSel(String idTipoMultaSel) {
        this.idTipoMultaSel = idTipoMultaSel;
    }

    public String getIdTipoMultaSel() {
        return idTipoMultaSel;
    }

    public void setIdMultaDescuentoSel(Long idMultaDescuentoSel) {
        this.idMultaDescuentoSel = idMultaDescuentoSel;
    }

    public Long getIdMultaDescuentoSel() {
        return idMultaDescuentoSel;
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

    public void setListMultaMonto(List<MultaDescuento> listMultaMonto) {
        this.listMultaMonto = listMultaMonto;
    }

    public List<MultaDescuento> getListMultaMonto() {
        return listMultaMonto;
    }

    public void setListMultaMontoTmp(List<MultaDescuento> listMultaMontoTmp) {
        this.listMultaMontoTmp = listMultaMontoTmp;
    }

    public List<MultaDescuento> getListMultaMontoTmp() {
        return listMultaMontoTmp;
    }

    public void setMultaMonto(MultaDescuento multaMonto) {
        this.multaMonto = multaMonto;
    }

    public MultaDescuento getMultaMonto() {
        return multaMonto;
    }
}
