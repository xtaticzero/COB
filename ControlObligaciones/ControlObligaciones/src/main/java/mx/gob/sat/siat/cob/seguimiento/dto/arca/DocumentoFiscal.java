/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dto.arca;

/**
 *
 * @author Juan
 */
public class DocumentoFiscal {

    private String idAutoridad;
    private String idDocumento;
    private String fechaDocumento;
    private String claveALR;
    private String idReferencia;
    private String fechaRecepcion;

    public String getIdAutoridad() {
        return idAutoridad;
    }

    public void setIdAutoridad(String idAutoridad) {
        this.idAutoridad = idAutoridad;
    }

    public String getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(String idDocumento) {
        this.idDocumento = idDocumento;
    }

    public String getFechaDocumento() {
        return fechaDocumento;
    }

    public void setFechaDocumento(String fechaDocumento) {
        this.fechaDocumento = fechaDocumento;
    }

    public String getClaveALR() {
        return claveALR;
    }

    public void setClaveALR(String claveALR) {
        this.claveALR = claveALR;
    }

    public String getIdReferencia() {
        return idReferencia;
    }

    public void setIdReferencia(String idReferencia) {
        this.idReferencia = idReferencia;
    }

    public String getFechaRecepcion() {
        return fechaRecepcion;
    }

    public void setFechaRecepcion(String fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }
}
