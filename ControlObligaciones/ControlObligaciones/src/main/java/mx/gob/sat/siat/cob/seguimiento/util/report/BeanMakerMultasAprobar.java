/*	****************************************************************
 * Nombre de archivo: BeanMakerMultasAprobar.java
 * Autores: Emmanuel Estrada Gonzalez 		 	 		   
 * Bitácora de modificaciones:
 * CR/Defecto Fecha Autor Descripción del cambio
 * ---------------------------------------------------------------------------
 * N/A 30/10/2014 <Nombre del desarrollador que está modificando el archivo>
 * ---------------------------------------------------------------------------
 */

package mx.gob.sat.siat.cob.seguimiento.util.report;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mx.gob.sat.siat.cob.seguimiento.dto.report.ReporteMultaAprobar;
import mx.gob.sat.siat.cob.seguimiento.dto.report.enums.ReporteMultasAprobarEnum;
import mx.gob.sat.siat.cob.seguimiento.util.Utilerias;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author emmanuel
 */
public class BeanMakerMultasAprobar {
    /**
     *
     * @param reporteMultaAprobar     
     * @return parameters
     */
    public Map getParameters(ReporteMultaAprobar reporteMultaAprobar){
        Map parameters = new HashMap();        
        Long result = new Long("0");
        
        parameters.put("descripcionVigilancia", reporteMultaAprobar.getMultaAprobarGrupo().getTipoAdministracion());
        parameters.put("fechaEmision", Utilerias.formatearFechaDDMMYYYY(reporteMultaAprobar.getMultaAprobarGrupo().getFechaEmision()));
        result = (reporteMultaAprobar.getMultasFirmadas()!=null)?reporteMultaAprobar.getMultasFirmadas().size():new Long("0");
        parameters.put("firmadas", result);
        result = (reporteMultaAprobar.getMultasNoGeneradas()!=null)?reporteMultaAprobar.getMultasNoGeneradas().size():new Long("0");
        parameters.put("noGeneradas", result);
        result = (reporteMultaAprobar.getMultasTrasladadas()!=null)?reporteMultaAprobar.getMultasTrasladadas().size():new Long("0");
        parameters.put("trasladadas", result);
        parameters.put("total", reporteMultaAprobar.getMultaAprobarGrupo().getCantidadMultas());
        parameters.put("medioEnvio", reporteMultaAprobar.getMultaAprobarGrupo().getMedioEnvio());
        parameters.put("tipoFirma", reporteMultaAprobar.getMultaAprobarGrupo().getTipoFirma());
        parameters.put("tipoMulta",  reporteMultaAprobar.getMultaAprobarGrupo().getTipoMulta());
                
        return parameters;    
    }
    
    /**
     * 
     * @param reporteMultaAprobar
     * @return 
     */
    public JRBeanCollectionDataSource getDetalleReport(ReporteMultaAprobar reporteMultaAprobar){
        List<ReporteMultaAprobar> listDetalles = new ArrayList<ReporteMultaAprobar>();
        listDetalles.add(reporteMultaAprobar);
        return (new JRBeanCollectionDataSource(listDetalles));
    }
    
    /**
     *
     * @return
     * @throws FileNotFoundException
     */
    public InputStream getRepotStream() throws FileNotFoundException{
        return this.getClass().getClassLoader().getResourceAsStream((ReporteMultasAprobarEnum.MASTER_REPORT.getPathReport()));
    }
}
