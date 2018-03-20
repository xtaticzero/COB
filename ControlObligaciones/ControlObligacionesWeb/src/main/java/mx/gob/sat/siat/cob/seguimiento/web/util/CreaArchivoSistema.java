package mx.gob.sat.siat.cob.seguimiento.web.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.FundamentoLegal;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.MultaPendienteDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ParametrosSgtDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ResultadoDiligencia;

import mx.gob.sat.siat.cob.seguimiento.util.Utilerias;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.primefaces.model.UploadedFile;


/**
 *
 * @author root
 */
public final class CreaArchivoSistema {
    
    private static Logger log = Logger.getLogger(CreaArchivoSistema.class);

    private CreaArchivoSistema() {
    }
    

    /**
     *
     * @param file
     * @param idPlantillaMax
     * @param rutaArchivo  
     */
    public static void crearArchivoEnSistema(UploadedFile file, int idPlantillaMax, String rutaArchivo){
        FileOutputStream fileOutputStream=null;
        InputStream inputStream=null;
        String nombreArchivo=FilenameUtils.removeExtension(file.getFileName());
        String extensionArchivo=FilenameUtils.getExtension(file.getFileName());
        File dirTmp=new File(rutaArchivo);
        boolean flgCrea=dirTmp.mkdirs();
        log.debug(flgCrea);
        String nombreArchivoEnSistema=rutaArchivo+nombreArchivo+"-"+idPlantillaMax+"."+extensionArchivo;
        
        try {
            fileOutputStream = new FileOutputStream(new File(nombreArchivoEnSistema));
            byte[] buffer=file.getContents();
            int bulk;
             inputStream = file.getInputstream();
              while (true) {
                bulk = inputStream.read(buffer);
                if (bulk < 0) {
                    break;
                 }
               fileOutputStream.write(buffer, 0, bulk);
               fileOutputStream.flush();
            }
        } catch (FileNotFoundException e) {
            
            log.debug(e.getMessage());    
        } catch (IOException e) {
            
            log.debug(e.getMessage());    
        }finally{
                IOUtils.closeQuietly(fileOutputStream);
                IOUtils.closeQuietly(inputStream);
        }
    }
    
    /**
     *
     * @param file
     * @param archivoDiligencia
     */
    public static void cargarResultadoDiligencia(UploadedFile file,String archivoDiligencia){
        FileOutputStream fileOutputStream=null;
        InputStream inputStream=null;
        File dirTmp=new File(archivoDiligencia);
        StringBuilder ruta=new StringBuilder();
        boolean flgCrea=dirTmp.mkdirs();
        log.debug(flgCrea);
        ruta.append(archivoDiligencia).append(file.getFileName());
        try {
            fileOutputStream = new FileOutputStream(new File(ruta.toString()));
            byte[] buffer=file.getContents();
            int bulk;
             inputStream = file.getInputstream();
              while (true) {
                bulk = inputStream.read(buffer);
                if (bulk < 0) {
                    break;
                 }
               fileOutputStream.write(buffer, 0, bulk);
                  
               
                  
               fileOutputStream.flush();
            }
        } catch (FileNotFoundException e) {
            log.debug(e.getMessage());    
        } catch (IOException e) {
            log.debug(e.getMessage());    
        }finally{
                IOUtils.closeQuietly(fileOutputStream);
                IOUtils.closeQuietly(inputStream);
        }
    }
    
    /**
     *
     * @param fundamentoLegal
     * @param ruta  
     */
    public static void generateCsvFile(FundamentoLegal fundamentoLegal,String ruta){
            FileWriter writer=null;
            try
            {
                writer = new FileWriter(ruta);
     
                writer.append(fundamentoLegal.getIdObligacionDescr());
                writer.append("|");
                writer.append(fundamentoLegal.getIdEjercicioFiscalDescr());
                writer.append("|");
                writer.append(fundamentoLegal.getIdPeriodoDescr());
                writer.append("|");
                writer.append(fundamentoLegal.getIdFundamentoLegal()+"");
                writer.append("|");
                writer.append(fundamentoLegal.getIdObligacion().toString());
                writer.append("|");
                writer.append(fundamentoLegal.getIdRegimen()+"");
                writer.append("|");
                writer.append(fundamentoLegal.getIdEjercicioFiscal()+"");
                writer.append("|");
                writer.append(fundamentoLegal.getFundamentoLegal());
         
     
                writer.flush();
               
            }
            catch(IOException e)
            {
                log.debug(e);
            } finally{
                    IOUtils.closeQuietly(writer);
            }
        }


    /**
     *
     * @param lstResultados
     * @param rutaArchivoResultados
     */
    public static void crearArchivoResultadoDiligencias(List<ResultadoDiligencia> lstResultados,String rutaArchivoResultados) {
        FileWriter writer=null;
        try{
            writer = new FileWriter(rutaArchivoResultados);
            for(ResultadoDiligencia aux:lstResultados){
                writer.append(aux.getNumeroControl());
                writer.append("|");
                writer.append(aux.getFechaAfectacion());
                writer.append("|");
                writer.append(aux.getResultado());
                writer.append("| \n");
            }
            
            writer.flush();
        
        }catch(IOException e){
            log.debug(e);
        } finally{
            IOUtils.closeQuietly(writer);
        }
    }

    /**
     *
     * @param multas
     * @param rutaArchivoMultas
     * @param param
     */
    public static void crearArchivoMultasPendientes(List<MultaPendienteDTO> multas, String rutaArchivoMultas, ParametrosSgtDTO param) {
        FileWriter writer=null;
        try{
            Calendar cal=Calendar.getInstance();
            cal.add(Calendar.DAY_OF_MONTH, -Integer.parseInt(param.getValor()));
            
            writer = new FileWriter(rutaArchivoMultas);
            writer.append("Reporte para Generaci\u00f3n de Multas con corte a: " + Utilerias.formatearFechaDDMMYYYY(new Date()) );
            writer.append("\n");
            writer.append("Periodo: "+ Utilerias.formatearFechaDDMMYYYY(cal.getTime())+ " a "+ Utilerias.formatearFechaDDMMYYYY(new Date()) );
            writer.append("\n");
            writer.append("\n");
            writer.append("N\u00famero de Control");
            writer.append("|");
            writer.append("RFC");
            writer.append("|");
            writer.append("Obligaci\u00f3n Requerida");
            writer.append("|");
            writer.append("Periodo Vigilado");
            writer.append("|");
            writer.append("Descripci\u00f3n Vigilancia");
            writer.append("|");
            writer.append("Fecha Notificaci\u00f3n Requerimiento");
            writer.append("|");
            writer.append("Fecha Vencimiento Requerimiento");
            writer.append("|");
            writer.append("Fecha Presentacion Obligaci\u00f3n");
            writer.append("|");
            writer.append("Pendiente Notificar");
            writer.append("|");
            writer.append("Proximo a Vencer");
            writer.append("|");
            writer.append("Cumpli\u00f3 Antes Efectos Notificacion");
            writer.append("|");
            writer.append("Cumpli\u00f3 Dentro Plazo 15 Dias");
            writer.append("|");
            writer.append("Cumpli\u00f3 Fuera Plazo 15 Dias");
            writer.append("|");
            writer.append("Requerimiento Vencido");
            writer.append("| \n");
            writer.append("\n");
            for(MultaPendienteDTO aux:multas){
                writer.append(aux.getNumeroControl());
                writer.append("|");
                writer.append(aux.getRfc());
                writer.append("|");
                writer.append(aux.getObligacionRequerida());
                writer.append("|");
                writer.append(aux.getPeriodoVigilado());
                writer.append("|");
                writer.append(aux.getDescripcionVigilancia());
                writer.append("|");
                writer.append(aux.getNotificacionReq()!=null?Utilerias.formatearFechaDDMMYYYY(aux.getNotificacionReq()):"");
                writer.append("|");
                writer.append(aux.getVencimientoReq()!=null?Utilerias.formatearFechaDDMMYYYY(aux.getVencimientoReq()):"");
                writer.append("|");
                writer.append(aux.getPresentacionObligacion()!=null?Utilerias.formatearFechaDDMMYYYY(aux.getPresentacionObligacion()):"");
                writer.append("|");
                writer.append(aux.isPendienteNotificar()?"X":"NA");
                writer.append("|");
                writer.append(aux.isReqProximoVencer()?"X":"NA");
                writer.append("|");
                writer.append(aux.isCumplioAntesEfectosNotificacion()?"X":"NA");
                writer.append("|");
                writer.append(aux.isCumplioDentroPlazo15Dias()?"X":"NA");
                writer.append("|");
                writer.append(aux.isCumplioFueraPlazo15Dias()?"X":"NA");
                writer.append("|");
                writer.append(aux.isRequerimientoVencido()?"X":"NA");
                writer.append("| \n");
            }
            
            writer.flush();
        
        }catch(IOException e){
            log.debug(e);
        } finally{
            IOUtils.closeQuietly(writer);
        }
    }
}
