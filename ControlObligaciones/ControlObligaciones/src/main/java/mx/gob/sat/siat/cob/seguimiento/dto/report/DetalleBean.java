/*	****************************************************************
 * Nombre de archivo: DetalleBean.java
 * Autores: Emmanuel Estrada Gonzalez 		 	 		   
 * Bitácora de modificaciones:
 * CR/Defecto Fecha Autor Descripción del cambio
 * ---------------------------------------------------------------------------
 * N/A 10/02/2014 <Nombre del desarrollador que está modificando el archivo>
 * ---------------------------------------------------------------------------
 */
package mx.gob.sat.siat.cob.seguimiento.dto.report;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ReporteAfectacionXAutoridadDTO;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class DetalleBean {
    public DetalleBean() {
        super();
    }
    
    private List<ReporteAfectacionXAutoridadDTO> listdetalleAfectacion;
    private List<DataMultasBean> listaMultasBean;
   
    public void setListdetalleAfectacion(List<ReporteAfectacionXAutoridadDTO> listdetalleAfectacion) {
        this.listdetalleAfectacion = listdetalleAfectacion;
    }

    public List<ReporteAfectacionXAutoridadDTO> getListdetalleAfectacion() {
        return listdetalleAfectacion;
    }  

    public List<DataMultasBean> getListaMultasBean() {
        return listaMultasBean;
    }

    public void setListaMultasBean(List<DataMultasBean> listaMultasBean) {
        this.listaMultasBean = listaMultasBean;
    }   
    
    public JRDataSource getDsListaObligaciones(){
       return new JRBeanCollectionDataSource(listdetalleAfectacion);    
    }
    
    public JRDataSource getDsListaMultas(){
       return new JRBeanCollectionDataSource(listaMultasBean);    
    }

}
