/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.sat.siat.cob.seguimiento.dto.report;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.MultaAprobarGrupo;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.MultaAprobarReporteDTO;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author emmanuel
 */
public class ReporteMultaAprobar {
    private MultaAprobarGrupo multaAprobarGrupo;
    private List<MultaAprobarReporteDTO> multasFirmadas;
    private List<MultaAprobarReporteDTO> multasNoGeneradas;
    private List<MultaAprobarReporteDTO> multasTrasladadas;

    public ReporteMultaAprobar(MultaAprobarGrupo multaAprobarGrupo, List<MultaAprobarReporteDTO> multasFirmadas, List<MultaAprobarReporteDTO> multasNoGeneradas, List<MultaAprobarReporteDTO> multasTrasladadas) {
        this.multaAprobarGrupo = multaAprobarGrupo;
        this.multasFirmadas = multasFirmadas;
        this.multasNoGeneradas = multasNoGeneradas;
        this.multasTrasladadas = multasTrasladadas;
    }
    
    public MultaAprobarGrupo getMultaAprobarGrupo() {
        return multaAprobarGrupo;
    }

    public void setMultaAprobarGrupo(MultaAprobarGrupo multaAprobarGrupo) {
        this.multaAprobarGrupo = multaAprobarGrupo;
    }

    public List<MultaAprobarReporteDTO> getMultasFirmadas() {
        return multasFirmadas;
    }

    public void setMultasFirmadas(List<MultaAprobarReporteDTO> multasFirmadas) {
        this.multasFirmadas = multasFirmadas;
    }

    public List<MultaAprobarReporteDTO> getMultasNoGeneradas() {
        return multasNoGeneradas;
    }

    public void setMultasNoGeneradas(List<MultaAprobarReporteDTO> multasNoGeneradas) {
        this.multasNoGeneradas = multasNoGeneradas;
    }

    public List<MultaAprobarReporteDTO> getMultasTrasladadas() {
        return multasTrasladadas;
    }

    public void setMultasTrasladadas(List<MultaAprobarReporteDTO> multasTrasladadas) {
        this.multasTrasladadas = multasTrasladadas;
    }
    
    public JRDataSource getDsFirmadas() {
        return new JRBeanCollectionDataSource(multasFirmadas);
    }

    public JRDataSource getDsNoGeneradas() {
        return new JRBeanCollectionDataSource(multasNoGeneradas);
    }

    public JRDataSource getDsTrasladadas() {
        return new JRBeanCollectionDataSource(multasTrasladadas);
    }
}
