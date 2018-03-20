package mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing;


import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.MultaDocumentoAfectaciones;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ReporteAfectacionXAutoridadDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ReporteJasperAfectacionDTO;

import org.apache.log4j.Logger;


public class GeneraReporteAfectaciones {
    
    private Logger logErrors = Logger.getLogger(GeneraReporteAfectaciones.class);
       
    private ByteArrayOutputStream baos=null;
    private FileOutputStream ficheroPdf = null;
    private Document document;
    private PdfPTable tb;
    
    private List<String> nombreColumnas = new ArrayList<String>();
   
    private File archivoImagen = new File("resources/images/flappy2.jpg");

    public File getArchivoImagen() {
        return archivoImagen;
    }

    public void setArchivoImagen(File archivoImagen) {
        this.archivoImagen = archivoImagen;
    }
   
    public GeneraReporteAfectaciones() {
        super();
    }    
    
    public ByteArrayOutputStream generaReporte(ReporteJasperAfectacionDTO datosAfectacionJasper) {
        
        try{

            this.setBaos(new ByteArrayOutputStream());
            this.setFicheroPdf(new FileOutputStream("Reporte Documento.pdf"));
            this.setDocument(new Document());
            PdfWriter.getInstance(getDocument(), getBaos());
            getDocument().open();
            
            this.crearTitulo("Reporte Documento: "+datosAfectacionJasper.getNumeroControl(), 0);
            this.crearTitulo("Fecha de Registro: "+datosAfectacionJasper.getFechaRegistro(), 0);
            this.crearTitulo("Fecha de Notificacion: "+datosAfectacionJasper.getFechaNotificacion(), 0);
            this.crearTitulo("Fecah de Vencimiento: "+datosAfectacionJasper.getFechaVencimiento(), 0);
            this.crearTitulo("Estado: "+datosAfectacionJasper.getEstado(), 4);
            this.crearTitulo("Datos Obligaciones", 1);
            this.inicializarTabla(4);
            this.nombreColumnas.add("Obligacion");
            this.nombreColumnas.add("Id Obligacion");
            this.nombreColumnas.add("Ejercicio");
            this.nombreColumnas.add("Periodo");
            this.colocarColumnas(this.nombreColumnas);
            this.colocarDatosDocumentos(datosAfectacionJasper.getListaObligaciones());
            this.agregarTabla();
            colocarEspacios(2);
            
            this.crearTitulo("Datos Resoluciones", 1);
            this.inicializarTabla(6);
            this.nombreColumnas = new ArrayList<String>();
            this.nombreColumnas.add("Numero de resolucion");
            this.nombreColumnas.add("Tipo de multa");
            this.nombreColumnas.add("Id Obligacion");
            this.nombreColumnas.add("Obligacion");
            this.nombreColumnas.add("Monto");
            this.nombreColumnas.add("Monto total");
            this.colocarColumnas(this.nombreColumnas);
            this.colocarDatosMultas(datosAfectacionJasper.getListaMultas());
            this.agregarTabla();
            
            
            
            
            
            this.cerrar();
           
            } catch (DocumentException e) {
                getLogErrors().error(e.getMessage(),e);
            } catch (FileNotFoundException e) {
               getLogErrors().error(e.getMessage(),e);
        }
        
        return getBaos();
    }
    
    public void crearTitulo(String titulo, int espacios) {
        try {
            Paragraph preface = new Paragraph();
            preface.add(new Paragraph(titulo));
            getDocument().add(preface);    
            this.colocarEspacios(espacios);
            
            } catch (DocumentException e) {
                getLogErrors().error(e.getMessage());
            }
        
    }
    
    public void inicializarTabla( int numeroCol) {
        setTb(new PdfPTable(numeroCol));        
    }
    
    public void colocarColumnas(List<String> columnas) {
        for(String str: columnas) {
            getTb().addCell(str);    
        }    
    }
    
    public void colocarDatosDocumentos(List<ReporteAfectacionXAutoridadDTO> datos){
        for(ReporteAfectacionXAutoridadDTO obj : datos){
            getTb().addCell(obj.getObDescripcion());
            getTb().addCell(String.valueOf(obj.getClaveObligacion()));
            getTb().addCell(obj.getEjercicio());
            getTb().addCell(obj.getPeriodo());
          }
        
    }      
    
    public void colocarDatosMultas(List<MultaDocumentoAfectaciones> datos){
        for(MultaDocumentoAfectaciones obj : datos){
            getTb().addCell(obj.getNumResolucion());
            getTb().addCell(obj.getTipoMulta());
            getTb().addCell(obj.getIdObligacion().toString());
            getTb().addCell(obj.getDescObligacion());
            getTb().addCell(obj.getMonto().toString());
            getTb().addCell(obj.getMontoTotal().toString());
          }
        
    } 
    
    public void agregarImagen (String url){
        try
        {
            Image foto = Image.getInstance(url);
            foto.scaleToFit(100, 100);
            foto.setAlignment(Chunk.ALIGN_MIDDLE);
            getDocument().add(foto);
        }
        catch ( Exception e ){
            logErrors.error(e);
        }
    }
    
    public void agregarTabla() {
       try {
            getDocument().add(getTb());   
       }catch(DocumentException ex) {
           getLogErrors().error(ex.getMessage());
       }
        
        
    }
    
    public void cerrar() {

        getDocument().close();
    }
    
    public void colocarEspacios(int espacios) {
        try {
            Paragraph preface = new Paragraph();
            for(int c=0; c<= espacios; c++) {
                preface.add(new Paragraph(" "));        
            }

            getDocument().add(preface);
            
        }catch(DocumentException ex) {
           getLogErrors().error( ex.getMessage());
        }
    }

    public ByteArrayOutputStream getBaos() {
        return baos;
    }

    public void setBaos(ByteArrayOutputStream baos) {
        this.baos = baos;
    }

    public FileOutputStream getFicheroPdf() {
        return ficheroPdf;
    }

    public void setFicheroPdf(FileOutputStream ficheroPdf) {
        this.ficheroPdf = ficheroPdf;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public void setTb(PdfPTable tb) {
        this.tb = tb;
    }

    public PdfPTable getTb() {
        return tb;
    }

    public void setLogErrors(Logger logErrors) {
        this.logErrors = logErrors;
    }

    public Logger getLogErrors() {
        return logErrors;
    }


}


