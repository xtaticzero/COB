package mx.gob.sat.siat.cob.seguimiento.util.exportador.pdf;


import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;

import org.apache.log4j.Logger;


/**
 *
 * @author root
 */
public abstract class GeneraPdf {
    
    private Logger logErrors = Logger.getLogger(GeneraPdf.class);
    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18 ,Font.BOLD);
   
    private ByteArrayOutputStream baos=null;
    private FileOutputStream ficheroPdf = null;
    private Document document;
    private PdfPTable tb;
    private String rutaImagenFondo = ConstantsCatalogos.RUTA_IMAGEN_FONDO;
    
    
    /**
     *
     * @param modelo
     * @return
     */
    public ByteArrayOutputStream generarPdf(ModeloPDFExcel modelo) {

        try {

            this.setBaos(new ByteArrayOutputStream());
            this.setFicheroPdf(new FileOutputStream("tabla.pdf"));
            this.setDocument(new Document());
            PdfWriter.getInstance(getDocument(), getBaos());
            getDocument().open();
            this.crearTitulo(modelo.getTitulo());
            this.inicializarTabla(modelo.getNombreColumnas().size());
            this.colocarColumnas(modelo.getNombreColumnas());
            this.colocarDatos(modelo.getDatos());
            this.agregarTabla();
            this.cerrar();

        } catch (DocumentException e) {
            getLogErrors().error(e.getMessage(), e);
        } catch (FileNotFoundException e) {
            getLogErrors().error(e.getMessage(), e);
        }

        return getBaos();
    }
    
    /**
     *
     * @param titulo
     */
    public void crearTitulo(String titulo) {
        try {
            Paragraph preface = new Paragraph();
            preface.add(new Paragraph(titulo, getCatFont()));
            getDocument().add(preface); 
            poneImagen(document, rutaImagenFondo);
            this.colocarEspacios(1);
            
            } catch (DocumentException e) {
                getLogErrors().error(e.getMessage());
            }
        
    }
    
    public void inicializarTabla( int numeroCol) {
        setTb(new PdfPTable(numeroCol));        
    }
    
    /**
     *
     * @param columnas
     */
    public void colocarColumnas(List<String> columnas) {
        for(String str: columnas) {
            getTb().addCell(str);    
        }    
    }
    
    /**
     *
     * @param datos
     */
    public abstract void colocarDatos(List<Object> datos);      
    
    /**
     *
     */
    public void agregarTabla() {
       try {
            getDocument().add(getTb());   
       }catch(DocumentException ex) {
           getLogErrors().error(ex.getMessage());
       }
        
        
    }
    
    /**
     *
     */
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
    
    private void poneImagen(Document document, String rutaImagenFondo) {
                 
            try {
                Image watermarkImage = Image.getInstance(rutaImagenFondo);
                
                    float x = document.getPageSize().getWidth() - watermarkImage.getWidth();
                    watermarkImage.setAbsolutePosition(x / 2, 8);
                    document.add(watermarkImage);    
             }
            catch (Exception e) {
                getLogErrors().error( e.getMessage());
            }      
        }

    /**
     *
     * @return
     */
    public ByteArrayOutputStream getBaos() {
        return baos;
    }

    /**
     *
     * @param baos
     */
    public void setBaos(ByteArrayOutputStream baos) {
        this.baos = baos;
    }

    /**
     *
     * @return
     */
    public FileOutputStream getFicheroPdf() {
        return ficheroPdf;
    }

    /**
     *
     * @param ficheroPdf
     */
    public void setFicheroPdf(FileOutputStream ficheroPdf) {
        this.ficheroPdf = ficheroPdf;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

  
    /**
     *
     * @param catFont
     */
    public static void setCatFont(Font catFont) {
        GeneraPdf.catFont = catFont;
    }

    /**
     *
     * @return
     */
    public static Font getCatFont() {
        return catFont;
    }

    /**
     *
     * @param tb
     */
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
