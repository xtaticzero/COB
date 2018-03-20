/*	****************************************************************
 * Nombre de archivo: DocumentoReporteBean.java
 * Autores: Emmanuel Estrada Gonzalez 		 	 		   
 * Bitácora de modificaciones:
 * CR/Defecto Fecha Autor Descripción del cambio
 * ---------------------------------------------------------------------------
 * N/A 10/02/2014 <Nombre del desarrollador que está modificando el archivo>
 * ---------------------------------------------------------------------------
 */
package mx.gob.sat.siat.cob.seguimiento.dto.report;

public class DocumentoReporteBean {
    private String claveObligacion;
    private String obDescripcion;
    private String ejercicio;
    private String periodo;
    
    public DocumentoReporteBean() {
        super();
    }

    public String getClaveObligacion() {
        return claveObligacion;
    }

    public void setClaveObligacion(String claveObligacion) {
        this.claveObligacion = claveObligacion;
    }

    public String getObDescripcion() {
        return obDescripcion;
    }

    public void setObDescripcion(String obDescripcion) {
        this.obDescripcion = obDescripcion;
    }

    public String getEjercicio() {
        return ejercicio;
    }

    public void setEjercicio(String ejercicio) {
        this.ejercicio = ejercicio;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }
}
