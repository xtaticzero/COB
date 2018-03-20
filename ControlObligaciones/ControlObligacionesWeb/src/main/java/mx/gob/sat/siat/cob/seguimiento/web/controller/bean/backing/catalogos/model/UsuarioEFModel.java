package mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing.catalogos.model;

import java.io.Serializable;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Combo;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.UsuarioEF;

public class UsuarioEFModel implements Serializable{
    private static final long serialVersionUID = -5197691180506547942L;
    
    private List<UsuarioEF> listUsuarioEF;
    private List<UsuarioEF> listUsuarioEFTmp;
    
    private String rfcCorto;
    private Long idEntidadFederativa;
    private String nombreUsuario;
    private String correoElectronico;
    private String fechaFinStr;
    private String situacion;
    
    private UsuarioEF usuarioEFEdit;
    private UsuarioEF usuarioEFEli;
    
    private boolean tblVisible=true;
    private boolean nvoVisible=false;
    private boolean edtVisible=false;
    private boolean elmVisible=false;
        
    
    
    private List<Combo> listaComboEntidad;
    private Long  idTipoEntidad;

    
    public UsuarioEFModel() {
        super();
    }

    public void setListUsuarioEF(List<UsuarioEF> listUsuarioEF) {
        this.listUsuarioEF = listUsuarioEF;
    }

    public List<UsuarioEF> getListUsuarioEF() {
        return listUsuarioEF;
    }

    public void setListUsuarioEFTmp(List<UsuarioEF> listUsuarioEFTmp) {
        this.listUsuarioEFTmp = listUsuarioEFTmp;
    }

    public List<UsuarioEF> getListUsuarioEFTmp() {
        return listUsuarioEFTmp;
    }

    public void setRfcCorto(String rfcCorto) {
        this.rfcCorto = rfcCorto;
    }

    public String getRfcCorto() {
        return rfcCorto;
    }

    public void setIdEntidadFederativa(Long idEntidadFederativa) {
        this.idEntidadFederativa = idEntidadFederativa;
    }

    public Long getIdEntidadFederativa() {
        return idEntidadFederativa;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setUsuarioEFEdit(UsuarioEF usuarioEFEdit) {
        this.usuarioEFEdit = usuarioEFEdit;
    }

    public UsuarioEF getUsuarioEFEdit() {
        return usuarioEFEdit;
    }

    public void setUsuarioEFEli(UsuarioEF usuarioEFEli) {
        this.usuarioEFEli = usuarioEFEli;
    }

    public UsuarioEF getUsuarioEFEli() {
        return usuarioEFEli;
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

    public void setListaComboEntidad(List<Combo> listaComboEntidad) {
        this.listaComboEntidad = listaComboEntidad;
    }

    public List<Combo> getListaComboEntidad() {
        return listaComboEntidad;
    }

    public void setIdTipoEntidad(Long idTipoEntidad) {
        this.idTipoEntidad = idTipoEntidad;
    }

    public Long getIdTipoEntidad() {
        return idTipoEntidad;
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
}
