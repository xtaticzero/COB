/*	****************************************************************
 * Nombre de archivo: DataMultasBean.java
 * Autores: Emmanuel Estrada Gonzalez 		 	 		   
 * Bitácora de modificaciones:
 * CR/Defecto Fecha Autor Descripción del cambio
 * ---------------------------------------------------------------------------
 * N/A 10/02/2014 <Nombre del desarrollador que está modificando el archivo>
 * ---------------------------------------------------------------------------
 */
package mx.gob.sat.siat.cob.seguimiento.dto.report;

import java.util.List;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author root
 */
public class DataMultasBean {

    private String tipoMulta;
    private String numResolucion;
    private String montoTotal;
    private String monto;
    private String nombreEstado;
    private List<MultaDetalleBean> lstMultaDetalle;

    public String getTipoMulta() {
        return tipoMulta;
    }

    public void setTipoMulta(String tipoMulta) {
        this.tipoMulta = tipoMulta;
    }

    public String getNumResolucion() {
        return numResolucion;
    }

    public void setNumResolucion(String numResolucion) {
        this.numResolucion = numResolucion;
    }

    public String getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(String montoTotal) {
        this.montoTotal = montoTotal;
    }

    public String getNombreEstado() {
        return nombreEstado;
    }

    public void setNombreEstado(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }

    public List<MultaDetalleBean> getLstMultaDetalle() {
        return lstMultaDetalle;
    }

    public void setLstMultaDetalle(List<MultaDetalleBean> lstMultaDetalle) {
        this.lstMultaDetalle = lstMultaDetalle;
    }

    public JRDataSource getDsMultasDetalle() {
        return new JRBeanCollectionDataSource(lstMultaDetalle);
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }
    
}
