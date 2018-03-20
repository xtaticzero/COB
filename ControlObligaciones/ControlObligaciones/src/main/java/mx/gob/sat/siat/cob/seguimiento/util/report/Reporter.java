/*	****************************************************************
 * Nombre de archivo: Reporter.java
 * Autores: Emmanuel Estrada Gonzalez 		 	 		   
 * Bitácora de modificaciones:
 * CR/Defecto Fecha Autor Descripción del cambio
 * ---------------------------------------------------------------------------
 * N/A 10/02/2014 <Nombre del desarrollador que está modificando el archivo>
 * ---------------------------------------------------------------------------
 */

package mx.gob.sat.siat.cob.seguimiento.util.report;
import java.io.FileNotFoundException;
import java.io.InputStream;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import mx.gob.sat.siat.cob.seguimiento.dto.report.DataMultasBean;
import mx.gob.sat.siat.cob.seguimiento.dto.report.DetalleBean;
import mx.gob.sat.siat.cob.seguimiento.dto.report.enums.ConsultasAfectacionEnum;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.MultaDocumentoAfectaciones;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ReporteAfectacionXAutoridadDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ReporteJasperAfectacionDTO;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class Reporter {
    
    private InputStream inputStreamMasterReport;
    

    public Reporter(){
        super();
    }
              
    private final Logger log = Logger.getLogger(Reporter.class);
    
    public InputStream getInputStreamMasterReport() {
        return inputStreamMasterReport;
    }

    public void setInputStreamMasterReport(InputStream inputStreamMasterReport) {
        this.inputStreamMasterReport = inputStreamMasterReport;
    }

    @SuppressWarnings("unchecked")
    public byte[] makeReport(ReporteJasperAfectacionDTO jasperReportAfectacion) throws FileNotFoundException, JRException {
        
        inputStreamMasterReport     = null;
        
        
                        
        
        try{            
            inputStreamMasterReport = this.getClass().getClassLoader().getResourceAsStream((ConsultasAfectacionEnum.MASTER_REPORT.getParametro()));            
        }catch(Exception ex){
            log.error("No se pueden obtener los reportes del jar ::::::::",ex);  
        }        
        
                
        List<ReporteAfectacionXAutoridadDTO> lstAfectacionXAutoridad;
        List<MultaDocumentoAfectaciones> lstMultaDocAfectaciones;
        List<DataMultasBean> lstMultasBean;
        
        
        DataBeanMaker dataBeanMaker;
        Map parametersReport;
               
        dataBeanMaker = new DataBeanMaker();
        lstAfectacionXAutoridad = dataBeanMaker.getAfectacionXAutoridadList(jasperReportAfectacion);
        lstMultaDocAfectaciones = dataBeanMaker.getMultasLst(jasperReportAfectacion);
        lstMultasBean = dataBeanMaker.getLstDataMultas(lstMultaDocAfectaciones);
                
        parametersReport = dataBeanMaker.getParameters(jasperReportAfectacion);
                
        DetalleBean detalle = new DetalleBean();
        
        detalle.setListdetalleAfectacion(lstAfectacionXAutoridad);
        detalle.setListaMultasBean(lstMultasBean);
        List<DetalleBean> listDetalles = new ArrayList<DetalleBean>();
        listDetalles.add(detalle);
        JRBeanCollectionDataSource beanDataSource = new JRBeanCollectionDataSource(listDetalles);
        
        byte[] archivo;   
        
        archivo = makeReportJASPER(inputStreamMasterReport, parametersReport, beanDataSource);
                
        return archivo;
    }
    
    @SuppressWarnings("unchecked")
    public byte[] makeReportJASPER(InputStream streamFileJASPER,Map parametersReport,JRBeanCollectionDataSource lstBeanDataSource) throws FileNotFoundException, JRException {
        JasperReport jasperReport;
        JasperPrint jasperPrint;
        byte[] archivo;
        archivo=null;
        try{
            jasperReport = (JasperReport) JRLoader.loadObject(streamFileJASPER);
            jasperPrint = JasperFillManager.fillReport(jasperReport, parametersReport, lstBeanDataSource);
            archivo = JasperExportManager.exportReportToPdf(jasperPrint);            
        }catch(JRException jrex){
            log.error("Error al crear reporte JRException ::::::  ",jrex);
            throw new JRException(jrex);
        }catch(Exception fne){
            log.error("Error al crear reporte es Xtatic",fne);
        }
        return archivo;        
    }
    
    @SuppressWarnings("unchecked")
    public void makeReportJASPER(InputStream streamFileJASPER,
            Map parametersReport,
            JRBeanCollectionDataSource lstBeanDataSource,
            String path) throws FileNotFoundException, JRException {
        log.info("Se inicio creacion del reporte........ metodo makeReportJASPER() ::::::::");        
        JasperReport jasperReport;
        JasperPrint jasperPrint;
        jasperReport = (JasperReport) JRLoader.loadObject(streamFileJASPER);
        jasperPrint = JasperFillManager.fillReport(jasperReport, parametersReport, lstBeanDataSource);
        JasperExportManager.exportReportToPdfFile(jasperPrint,path);
    }
    
    @SuppressWarnings("unchecked")
    public void makeLocalReportJASPER(String pathToFile, Map parametersReport,
            Connection connection) throws FileNotFoundException, JRException {
        log.debug("empieza makeLocalReportJASPER");
        JasperReport jasperReport;
        JasperPrint jasperPrint;
        inputStreamMasterReport = this.getClass().getClassLoader().getResourceAsStream(("reports/FacturaEF.jasper"));
        jasperReport = (JasperReport) JRLoader.loadObject(inputStreamMasterReport);
        jasperPrint = JasperFillManager.fillReport(jasperReport, parametersReport, connection);
        JasperExportManager.exportReportToPdfFile(jasperPrint,pathToFile);
        log.debug("termina makeLocalReportJASPER");
    }
    
    @SuppressWarnings("unchecked")
    public byte[] makeReportJRXML(InputStream streamFileJRXML,Map parametersReport,JRBeanCollectionDataSource lstBeanDataSource) throws FileNotFoundException, JRException {
        JasperDesign jasperDesign;
        JasperReport jasperReport;
        JasperPrint jasperPrint;
        byte[] archivo;
        
        jasperDesign = JRXmlLoader.load(streamFileJRXML);
        jasperReport = JasperCompileManager.compileReport(jasperDesign);
        jasperPrint = JasperFillManager.fillReport(jasperReport, parametersReport, lstBeanDataSource);
        
        archivo = JasperExportManager.exportReportToPdf(jasperPrint);
        
        return archivo;    
    }
    
}
