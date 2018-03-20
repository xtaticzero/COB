/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

import java.io.Serializable;
import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.compartidos.CatalogoBase;

/**
 *
 * @author eduardo.romero
 */
public class PeriodicidadHelper implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idBusquedaRenuentes;
    private List<CatalogoBase> catalogoEjercicioInicial;
    private List<CatalogoBase> catalogoPeriodoInicial;
    private List<CatalogoBase> catalogoEjercicioFinal;
    private List<CatalogoBase> catalogoPeriodoFinal;
    private CatalogoBase periodicidad;
    private String periodoSelected;
    private String ejercicioInicialSelected;
    private String periodoInicialSelected;
    private String ejercicioFinalSelected;
    private String periodoFinalSelected;
    private boolean checado;

    /**
     *
     * @return
     */
    public Long getIdBusquedaRenuentes() {
        return idBusquedaRenuentes;
    }

    /**
     *
     * @param idBusquedaRenuentes
     */
    public void setIdBusquedaRenuentes(Long idBusquedaRenuentes) {
        this.idBusquedaRenuentes = idBusquedaRenuentes;
    }

    /**
     *
     * @return
     */
    public List<CatalogoBase> getCatalogoEjercicioInicial() {
        return catalogoEjercicioInicial;
    }

    /**
     *
     * @param catalogoEjercicioInicial
     */
    public void setCatalogoEjercicioInicial(List<CatalogoBase> catalogoEjercicioInicial) {
        this.catalogoEjercicioInicial = catalogoEjercicioInicial;
    }

    /**
     *
     * @return
     */
    public List<CatalogoBase> getCatalogoEjercicioFinal() {
        return catalogoEjercicioFinal;
    }

    /**
     *
     * @param catalogoEjercicioFinal
     */
    public void setCatalogoEjercicioFinal(List<CatalogoBase> catalogoEjercicioFinal) {
        this.catalogoEjercicioFinal = catalogoEjercicioFinal;
    }

    /**
     * @return the periodicidad
     */
    public CatalogoBase getPeriodicidad() {
        return periodicidad;
    }

    /**
     * @param periodicidad the periodicidad to set
     */
    public void setPeriodicidad(CatalogoBase periodicidad) {
        this.periodicidad = periodicidad;
    }

    /**
     *
     * @return
     */
    public String getPeriodoSelected() {
        return periodoSelected;
    }

    /**
     *
     * @param periodoSelected
     */
    public void setPeriodoSelected(String periodoSelected) {
        this.periodoSelected = periodoSelected;
    }

    /**
     *
     * @return
     */
    public String getEjercicioInicialSelected() {
        return ejercicioInicialSelected;
    }

    /**
     *
     * @param ejercicioInicialSelected
     */
    public void setEjercicioInicialSelected(String ejercicioInicialSelected) {
        this.ejercicioInicialSelected = ejercicioInicialSelected;
    }

    /**
     *
     * @return
     */
    public String getPeriodoInicialSelected() {
        return periodoInicialSelected;
    }

    /**
     *
     * @param periodoInicialSelected
     */
    public void setPeriodoInicialSelected(String periodoInicialSelected) {
        this.periodoInicialSelected = periodoInicialSelected;
    }

    /**
     *
     * @return
     */
    public String getEjercicioFinalSelected() {
        return ejercicioFinalSelected;
    }

    /**
     *
     * @param ejercicioFinalSelected
     */
    public void setEjercicioFinalSelected(String ejercicioFinalSelected) {
        this.ejercicioFinalSelected = ejercicioFinalSelected;
    }

    /**
     *
     * @return
     */
    public String getPeriodoFinalSelected() {
        return periodoFinalSelected;
    }

    /**
     *
     * @param periodoFinalSelected
     */
    public void setPeriodoFinalSelected(String periodoFinalSelected) {
        this.periodoFinalSelected = periodoFinalSelected;
    }

    /**
     *
     * @return
     */
    public List<CatalogoBase> getCatalogoPeriodoInicial() {
        return catalogoPeriodoInicial;
    }

    /**
     *
     * @param catalogoPeriodoInicial
     */
    public void setCatalogoPeriodoInicial(List<CatalogoBase> catalogoPeriodoInicial) {
        this.catalogoPeriodoInicial = catalogoPeriodoInicial;
    }

    /**
     *
     * @return
     */
    public List<CatalogoBase> getCatalogoPeriodoFinal() {
        return catalogoPeriodoFinal;
    }

    /**
     *
     * @param catalogoPeriodoFinal
     */
    public void setCatalogoPeriodoFinal(List<CatalogoBase> catalogoPeriodoFinal) {
        this.catalogoPeriodoFinal = catalogoPeriodoFinal;
    }

    /**
     *
     * @param checado
     */
    public void setChecado(boolean checado) {
        this.checado = checado;
    }

    /**
     *
     * @return
     */
    public boolean isChecado() {
        return checado;
    }
}
