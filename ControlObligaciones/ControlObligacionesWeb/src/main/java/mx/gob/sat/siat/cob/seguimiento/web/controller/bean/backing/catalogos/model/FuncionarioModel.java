package mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing.catalogos.model;

import java.io.Serializable;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Combo;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Funcionario;

public class FuncionarioModel implements Serializable{
    private static final long serialVersionUID = -5197691180506547942L;
    
    private List<Funcionario> listFuncionario;
    private List<Funcionario> listFuncionarioTmp;
    
    private String numeroEmpleado;
    private String nombreFuncionario;
    private String correoElectronicoFuncionario;
    private String correoElectronicoAlterno;
    private String fechaFinStr;
    private String situacion;
    private Long areaDeAdscripcion;
    private String descripcionCargo;
    
    private Funcionario funcionarioEdit;
    private Funcionario funcionarioEli;
    
    private boolean tblVisibleFuncionario=true;
    private boolean nvoVisible=false;
    private boolean edtVisible=false;
    private boolean elmVisible=false;
    
    private List<Combo> listaComboArea;
    private Long  idTipoArea;
    
    public FuncionarioModel() {
        super();
    }

    public void setListFuncionario(List<Funcionario> listFuncionario) {
        this.listFuncionario = listFuncionario;
    }

    public List<Funcionario> getListFuncionario() {
        return listFuncionario;
    }

    public void setListFuncionarioTmp(List<Funcionario> listFuncionarioTmp) {
        this.listFuncionarioTmp = listFuncionarioTmp;
    }

    public List<Funcionario> getListFuncionarioTmp() {
        return listFuncionarioTmp;
    }

    public void setNumeroEmpleado(String numeroEmpleado) {
        this.numeroEmpleado = numeroEmpleado;
    }

    public String getNumeroEmpleado() {
        return numeroEmpleado;
    }

    public void setNombreFuncionario(String nombreFuncionario) {
        this.nombreFuncionario = nombreFuncionario;
    }

    public String getNombreFuncionario() {
        return nombreFuncionario;
    }

    public void setCorreoElectronicoFuncionario(String correoElectronicoFuncionario) {
        this.correoElectronicoFuncionario = correoElectronicoFuncionario;
    }

    public String getCorreoElectronicoFuncionario() {
        return correoElectronicoFuncionario;
    }

    public void setCorreoElectronicoAlterno(String correoElectronicoAlterno) {
        this.correoElectronicoAlterno = correoElectronicoAlterno;
    }

    public String getCorreoElectronicoAlterno() {
        return correoElectronicoAlterno;
    }

    public void setFechaFinStr(String fechaFinStr) {
        this.fechaFinStr = fechaFinStr;
    }

    public String getFechaFinStr() {
        return fechaFinStr;
    }

    public void setFuncionarioEdit(Funcionario funcionarioEdit) {
        this.funcionarioEdit = funcionarioEdit;
    }

    public Funcionario getFuncionarioEdit() {
        return funcionarioEdit;
    }

    public void setFuncionarioEli(Funcionario funcionarioEli) {
        this.funcionarioEli = funcionarioEli;
    }

    public Funcionario getFuncionarioEli() {
        return funcionarioEli;
    }

    public void setTblVisibleFuncionario(boolean tblVisibleFuncionario) {
        this.tblVisibleFuncionario = tblVisibleFuncionario;
    }

    public boolean isTblVisibleFuncionario() {
        return tblVisibleFuncionario;
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

    public void setSituacion(String situacion) {
        this.situacion = situacion;
    }

    public String getSituacion() {
        return situacion;
    }

    public void setAreaDeAdscripcion(Long areaDeAdscripcion) {
        this.areaDeAdscripcion = areaDeAdscripcion;
    }

    public Long getAreaDeAdscripcion() {
        return areaDeAdscripcion;
    }

    public void setListaComboArea(List<Combo> listaComboArea) {
        this.listaComboArea = listaComboArea;
    }

    public List<Combo> getListaComboArea() {
        return listaComboArea;
    }

    public void setIdTipoArea(Long idTipoArea) {
        this.idTipoArea = idTipoArea;
    }

    public Long getIdTipoArea() {
        return idTipoArea;
    }

    public void setDescripcionCargo(String descripcionCargo) {
        this.descripcionCargo = descripcionCargo;
    }

    public String getDescripcionCargo() {
        return descripcionCargo;
    }
}
