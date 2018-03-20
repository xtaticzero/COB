/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

import java.io.Serializable;
import java.util.List;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author root
 */
public class ReporteAprobacionVigilancia implements Serializable {

    private long excluidosPorResponsable;
    private long cancelados;
    private long cumplidos;
    private List<DetalleReporteVigilanciaAprobada> detalles;
    private List<DetalleReporteVigilanciaAprobada> detallesExcluidos;
    private List<DetalleReporteVigilanciaAprobada> detallesCancelados;
    private List<DetalleReporteVigilanciaAprobada> detallesCumplidos;
    
    public long getExcluidosPorResponsable() {
        return excluidosPorResponsable;
    }

    public void setExcluidosPorResponsable(long excluidosPorResponsable) {
        this.excluidosPorResponsable = excluidosPorResponsable;
    }

    public long getCancelados() {
        return cancelados;
    }

    public void setCancelados(long cancelados) {
        this.cancelados = cancelados;
    }

    public long getCumplidos() {
        return cumplidos;
    }

    public void setCumplidos(long cumplidos) {
        this.cumplidos = cumplidos;
    }

    public long getTotal() {
        return excluidosPorResponsable + cumplidos + cancelados;
    }

    public void setDetalles(List<DetalleReporteVigilanciaAprobada> detalles) {
        this.detalles = detalles;
    }

    public List<DetalleReporteVigilanciaAprobada> getDetalles() {
        return detalles;
    }

    public List<DetalleReporteVigilanciaAprobada> getDetallesExcluidos() {
        return detallesExcluidos;
    }

    public void setDetallesExcluidos(List<DetalleReporteVigilanciaAprobada> detallesExcluidos) {
        this.detallesExcluidos = detallesExcluidos;
    }

    public List<DetalleReporteVigilanciaAprobada> getDetallesCancelados() {
        return detallesCancelados;
    }

    public void setDetallesCancelados(List<DetalleReporteVigilanciaAprobada> detallesCancelados) {
        this.detallesCancelados = detallesCancelados;
    }

    public List<DetalleReporteVigilanciaAprobada> getDetallesCumplidos() {
        return detallesCumplidos;
    }

    public void setDetallesCumplidos(List<DetalleReporteVigilanciaAprobada> detallesCumplidos) {
        this.detallesCumplidos = detallesCumplidos;
    }

    public JRDataSource getDsProcesados() {
        return new JRBeanCollectionDataSource(detalles);
    }

    public JRDataSource getDsExcluidos() {
        return new JRBeanCollectionDataSource(detallesExcluidos);
    }

    public JRDataSource getDsCancelados() {
        return new JRBeanCollectionDataSource(detallesCancelados);
    }

    public JRDataSource getDsCumplidos() {
        return new JRBeanCollectionDataSource(detallesCumplidos);
    }
}
