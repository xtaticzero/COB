/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing.helper;

import java.io.Serializable;
import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.compartidos.CatalogoBase;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.PeriodicidadHelper;

/**
 *
 * @author juan
 */
public class BuscaRenuentesMBeanHelper implements Serializable {

    private List<CatalogoBase> catalogoTipoDocumento;
    private List<CatalogoBase> catalogoPeriodicidad;
    private List<CatalogoBase> catalogoPeriodo;
    private List<CatalogoBase> catalogoEjercicioFiscal;
    private List<CatalogoBase> catalogoEstadoDocto;
    private List<CatalogoBase> catalogoObligacion;
    private List<CatalogoBase> listaMensual;
    private List<CatalogoBase> listaBimestral;
    private List<CatalogoBase> listaTrimestral;
    private List<CatalogoBase> listaCuatrimestral;
    private List<CatalogoBase> listaSemestral;
    private List<CatalogoBase> listaAnual;
    private boolean showButtonBitacora;
    private PeriodicidadHelper[] selectedListPeriodicidad;

    /**
     * Getters and setters.
     *
     */
    /**
     * @return
     */
    public List<CatalogoBase> getCatalogoTipoDocumento() {
        return catalogoTipoDocumento;
    }

    public void setCatalogoTipoDocumento(List<CatalogoBase> catalogoTipoDocumento) {
        this.catalogoTipoDocumento = catalogoTipoDocumento;
    }

    public List<CatalogoBase> getCatalogoPeriodicidad() {
        return catalogoPeriodicidad;
    }

    public void setCatalogoPeriodicidad(List<CatalogoBase> catalogoPeriodicidad) {
        this.catalogoPeriodicidad = catalogoPeriodicidad;
    }

    public List<CatalogoBase> getCatalogoPeriodo() {
        return catalogoPeriodo;
    }

    public void setCatalogoPeriodo(List<CatalogoBase> catalogoPeriodo) {
        this.catalogoPeriodo = catalogoPeriodo;
    }

    public List<CatalogoBase> getCatalogoEjercicioFiscal() {
        return catalogoEjercicioFiscal;
    }

    public void setCatalogoEjercicioFiscal(List<CatalogoBase> catalogoEjercicioFiscal) {
        this.catalogoEjercicioFiscal = catalogoEjercicioFiscal;
    }

    public List<CatalogoBase> getCatalogoEstadoDocto() {
        return catalogoEstadoDocto;
    }

    public void setCatalogoEstadoDocto(List<CatalogoBase> catalogoEstadoDocto) {
        this.catalogoEstadoDocto = catalogoEstadoDocto;
    }

    public List<CatalogoBase> getCatalogoObligacion() {
        return catalogoObligacion;
    }

    public void setCatalogoObligacion(List<CatalogoBase> catalogoObligacion) {
        this.catalogoObligacion = catalogoObligacion;
    }

    public List<CatalogoBase> getListaMensual() {
        return listaMensual;
    }

    public void setListaMensual(List<CatalogoBase> listaMensual) {
        this.listaMensual = listaMensual;
    }

    public List<CatalogoBase> getListaBimestral() {
        return listaBimestral;
    }

    public void setListaBimestral(List<CatalogoBase> listaBimestral) {
        this.listaBimestral = listaBimestral;
    }

    public List<CatalogoBase> getListaTrimestral() {
        return listaTrimestral;
    }

    public void setListaTrimestral(List<CatalogoBase> listaTrimestral) {
        this.listaTrimestral = listaTrimestral;
    }

    public List<CatalogoBase> getListaCuatrimestral() {
        return listaCuatrimestral;
    }

    public void setListaCuatrimestral(List<CatalogoBase> listaCuatrimestral) {
        this.listaCuatrimestral = listaCuatrimestral;
    }

    public List<CatalogoBase> getListaSemestral() {
        return listaSemestral;
    }

    public void setListaSemestral(List<CatalogoBase> listaSemestral) {
        this.listaSemestral = listaSemestral;
    }

    public List<CatalogoBase> getListaAnual() {
        return listaAnual;
    }

    public void setListaAnual(List<CatalogoBase> listaAnual) {
        this.listaAnual = listaAnual;
    }

    public boolean isShowButtonBitacora() {
        return showButtonBitacora;
    }

    public void setShowButtonBitacora(boolean showButtonBitacora) {
        this.showButtonBitacora = showButtonBitacora;
    }

    public void setSelectedListPeriodicidad(PeriodicidadHelper[] selectedListPeriodicidad) {
        if (selectedListPeriodicidad != null) {
            this.selectedListPeriodicidad = (PeriodicidadHelper[]) selectedListPeriodicidad.clone();
        }
    }

    public PeriodicidadHelper[] getSelectedListPeriodicidad() {
        if (selectedListPeriodicidad != null) {
            return (PeriodicidadHelper[]) selectedListPeriodicidad.clone();
        } else {
            return null;
        }
    }
}
