/*	****************************************************************
 * Nombre de archivo: DataBeanMaker.java
 * Autores: Emmanuel Estrada Gonzalez 		 	 		   
 * Bitácora de modificaciones:
 * CR/Defecto Fecha Autor Descripción del cambio
 * ---------------------------------------------------------------------------
 * N/A 10/02/2014 <Nombre del desarrollador que está modificando el archivo>
 * ---------------------------------------------------------------------------
 */

package mx.gob.sat.siat.cob.seguimiento.util.report;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.MultaDocumentoAfectaciones;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ReporteAfectacionXAutoridadDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ReporteJasperAfectacionDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.report.DataMultasBean;
import mx.gob.sat.siat.cob.seguimiento.dto.report.MultaDetalleBean;


/**
 *
 * @author Emmanuel Estrada Gonzalez
 */
public class DataBeanMaker {
    
    private DecimalFormat formateadorNumeric;
    private DecimalFormatSymbols simbolos;
    private static final String PATTERN_NUMER_FORMAT = "$ ###,###.##";
    /**
     *
     */
    public DataBeanMaker() {
        super();
    }   
    
    /**
     *
     * @param jasperReportAfectacion
     * @param subRepAfectaciones
     * @param subRepMultas
     * @param subRepMultasDetalleStream
     * @return
     */
    public Map getParameters(ReporteJasperAfectacionDTO jasperReportAfectacion){
        Map parameters = new HashMap();
        
        
        parameters.put("numRequerimiento", jasperReportAfectacion.getNumeroControl());  
        parameters.put("rfc", jasperReportAfectacion.getRfc());
        parameters.put("nombre", jasperReportAfectacion.getNombre());
        parameters.put("alsc", jasperReportAfectacion.getAdmonLocal());
        parameters.put("tipoDocumento", jasperReportAfectacion.getTipoDocumento());
        parameters.put("stdRequerimiento", jasperReportAfectacion.getEstado());
        parameters.put("dateEmision", jasperReportAfectacion.getFechaRegistro());
        parameters.put("dateNotificacion", jasperReportAfectacion.getFechaNotificacion());
        parameters.put("dateVencimiento",  jasperReportAfectacion.getFechaVencimiento());
        parameters.put("medioEnvio", jasperReportAfectacion.getTipoMedio());
        parameters.put("fechaNoTrabajado", jasperReportAfectacion.getFechaNoTrabajado());
        parameters.put("fechaNoLocalizado", jasperReportAfectacion.getFechaNoLocalizado());
        parameters.put("fechaCitatorio", jasperReportAfectacion.getFechaCitatorio());
        
        return parameters;    
    }
    
    /**
     *
     * @param jasperReportAfectacion
     * @return
     */
    public List<ReporteAfectacionXAutoridadDTO> getAfectacionXAutoridadList(
            ReporteJasperAfectacionDTO jasperReportAfectacion){
        List<ReporteAfectacionXAutoridadDTO> lstAfectacionXAutoridad;        
        lstAfectacionXAutoridad = null;        
        lstAfectacionXAutoridad = jasperReportAfectacion.getListaObligaciones();        
        return lstAfectacionXAutoridad;                    
    }
    
    /**
     *
     * @param jasperReportAfectacion
     * @return
     */
    public List<MultaDocumentoAfectaciones> getMultasLst(ReporteJasperAfectacionDTO jasperReportAfectacion){
        List<MultaDocumentoAfectaciones> lstMultaDocAfectaciones;
        lstMultaDocAfectaciones = null;
        lstMultaDocAfectaciones = jasperReportAfectacion.getListaMultas();
        return lstMultaDocAfectaciones;
    }
    
    /**
     *
     * @param lstMultaDocAfectaciones
     * @return
     */
    public List<DataMultasBean> getLstDataMultas(List<MultaDocumentoAfectaciones> lstMultaDocAfectaciones){
        List<DataMultasBean> lstDataMultas;
        
        if(lstMultaDocAfectaciones!=null){
            lstDataMultas = new ArrayList<DataMultasBean>();
            
            
            for(int i=0;i<lstMultaDocAfectaciones.size();i++){
                String tipoMulta1;
                String tipoMulta2;
                    
                String numResolucion1;
                String numResolucion2;
                
                DataMultasBean   dataMulta;
                dataMulta        = new DataMultasBean();
                
                tipoMulta1=lstMultaDocAfectaciones.get(i).getTipoMulta();
                numResolucion1=lstMultaDocAfectaciones.get(i).getNumResolucion();
                
                dataMulta.setTipoMulta(lstMultaDocAfectaciones.get(i).getTipoMulta());
                dataMulta.setNumResolucion(lstMultaDocAfectaciones.get(i).getNumResolucion());
                dataMulta.setNombreEstado(lstMultaDocAfectaciones.get(i).getNombreEstado());
                dataMulta.setMontoTotal(getNumberFormat(lstMultaDocAfectaciones.get(i).getMontoTotal()));
                dataMulta.setMonto(getNumberFormat(lstMultaDocAfectaciones.get(i).getMonto()));
                
                                
                if(!containElement(lstDataMultas,dataMulta)){
                            lstDataMultas.add(dataMulta);
                        }   
                
                for(int j=i+1;j<lstMultaDocAfectaciones.size();j++){
                    tipoMulta2=lstMultaDocAfectaciones.get(j).getTipoMulta();
                    numResolucion2=lstMultaDocAfectaciones.get(j).getNumResolucion();
                    
                    if((!tipoMulta1.equals(tipoMulta2))||(!numResolucion1.equals(numResolucion2))){
                        DataMultasBean dataMulta2  = new DataMultasBean();
                        dataMulta2.setTipoMulta(lstMultaDocAfectaciones.get(j).getTipoMulta());
                        dataMulta2.setNumResolucion(lstMultaDocAfectaciones.get(j).getNumResolucion());
                        dataMulta2.setNombreEstado(lstMultaDocAfectaciones.get(j).getNombreEstado());
                        dataMulta2.setMontoTotal(getNumberFormat(lstMultaDocAfectaciones.get(j).getMontoTotal()));
                        dataMulta2.setMonto(getNumberFormat(lstMultaDocAfectaciones.get(j).getMonto()));
                        if(!containElement(lstDataMultas,dataMulta2)){
                            lstDataMultas.add(dataMulta2);
                        }                       
                    }        
                    
                }                
                return makeDetalleMulta(lstDataMultas,lstMultaDocAfectaciones);                
            }            
        }
        return null;
    }
    
    /**
     *
     * @param lstDataMultas
     * @param element
     * @return
     */
    public boolean containElement(List<DataMultasBean> lstDataMultas,DataMultasBean element){
        if(lstDataMultas!=null){
            for(DataMultasBean item:lstDataMultas){
                if(item.getNumResolucion().equals(element.getNumResolucion())){
                    if(item.getTipoMulta().equals(element.getTipoMulta())){
                        return true;                    
                    }                
                }
            }            
        }
        return false;
    }
    
    /**
     *
     * @param lstDataMultas
     * @param lstMultaDocAfectaciones
     * @return
     */
    public List<DataMultasBean> makeDetalleMulta(List<DataMultasBean> lstDataMultas,
            List<MultaDocumentoAfectaciones> lstMultaDocAfectaciones){
        List<MultaDetalleBean> lstDetalleMulta;
        List<DataMultasBean> lstMultasDetalle;
        
        lstMultasDetalle = new ArrayList<DataMultasBean>();
               
        for(DataMultasBean multa:lstDataMultas){
            lstDetalleMulta = new ArrayList<MultaDetalleBean>();
             Long montoTotal = new Long("0");
            for(MultaDocumentoAfectaciones itemDoc:lstMultaDocAfectaciones){
               
                if(itemDoc.getTipoMulta().equals(multa.getTipoMulta())){
                    if(itemDoc.getNumResolucion().equals(multa.getNumResolucion())){
                        MultaDetalleBean detalle = new MultaDetalleBean();
                        detalle.setIdObligacion(itemDoc.getIdObligacion());
                        detalle.setDescObligacion(itemDoc.getDescObligacion());
                        detalle.setPeriodo(itemDoc.getPeriodo());
                        detalle.setEjercicio(itemDoc.getEjercicio());
                        detalle.setMonto(getNumberFormat(itemDoc.getMonto()));
                        montoTotal = montoTotal+itemDoc.getMonto();
                        lstDetalleMulta.add(detalle);
                    }
                }
            }
            multa.setMontoTotal(getNumberFormat(montoTotal));
            multa.setLstMultaDetalle(lstDetalleMulta);
            lstMultasDetalle.add(multa);
        }       
        return lstMultasDetalle;
    }   
    
    /**
     *
     * @param number
     * @return
     */
    public String getNumberFormat(Double number){
        simbolos = new DecimalFormatSymbols();
        simbolos.setDecimalSeparator('.');
        simbolos.setGroupingSeparator(',');
        formateadorNumeric = new DecimalFormat(PATTERN_NUMER_FORMAT,simbolos);
        return formateadorNumeric.format(number);
    }
    
    /**
     *
     * @param number
     * @return
     */
    public String getNumberFormat(Long number){
        simbolos = new DecimalFormatSymbols();
        simbolos.setDecimalSeparator('.');
        simbolos.setGroupingSeparator(',');
        formateadorNumeric = new DecimalFormat(PATTERN_NUMER_FORMAT,simbolos);
        return formateadorNumeric.format(number);
    }
}
