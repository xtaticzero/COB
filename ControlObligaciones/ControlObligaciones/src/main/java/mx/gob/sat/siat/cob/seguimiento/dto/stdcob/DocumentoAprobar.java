/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author root
 */
public class DocumentoAprobar implements Serializable {

    private String numeroCarga;
    private String descripcionVigilancia;
    private String numeroControl;
    private String rfc;
    private String boid;
    private String idTipoDocumento;
    private String idAdministracionLocal;
    private String administracionLocal;
    private boolean excluir;
    private boolean estadoValido;
    private int ultimoEstado;

    private List<DetalleDocumento> detalles;

    public DocumentoAprobar() {
        detalles = new ArrayList<DetalleDocumento>();
    }

    public String getNumeroCarga() {
        return numeroCarga;
    }

    public void setNumeroCarga(String numeroCarga) {
        this.numeroCarga = numeroCarga;
    }

    public String getDescripcionVigilancia() {
        return descripcionVigilancia;
    }

    public void setDescripcionVigilancia(String descripcionVigilancia) {
        this.descripcionVigilancia = descripcionVigilancia;
    }

    public String getNumeroControl() {
        return numeroControl;
    }

    public void setNumeroControl(String numeroControl) {
        this.numeroControl = numeroControl;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public boolean isExcluir() {
        return excluir;
    }

    public void setExcluir(boolean excluir) {
        this.excluir = excluir;
    }

    public List<DetalleDocumento> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleDocumento> detalles) {
        this.detalles = detalles;
    }

    public String getBoid() {
        return boid;
    }

    public void setBoid(String boid) {
        this.boid = boid;
    }

    public boolean isEstadoValido() {
        return estadoValido;
    }

    public void setEstadoValido(boolean estadoValido) {
        this.estadoValido = estadoValido;
    }

    public String getIdTipoDocumento() {
        return idTipoDocumento;
    }

    public void setIdTipoDocumento(String idTipoDocumento) {
        this.idTipoDocumento = idTipoDocumento;
    }

    public String getIdAdministracionLocal() {
        return idAdministracionLocal;
    }

    public void setIdAdministracionLocal(String idAdministracionLocal) {
        this.idAdministracionLocal = idAdministracionLocal;
    }

    public String getAdministracionLocal() {
        return administracionLocal;
    }

    public void setAdministracionLocal(String administracionLocal) {
        this.administracionLocal = administracionLocal;
    }

    public int getUltimoEstado() {
        return ultimoEstado;
    }

    public void setUltimoEstado(int ultimoEstado) {
        this.ultimoEstado = ultimoEstado;
    }
    
    
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (this.numeroCarga != null ? this.numeroCarga.hashCode() : 0);
        hash = 79 * hash + (this.descripcionVigilancia != null ? this.descripcionVigilancia.hashCode() : 0);
        hash = 79 * hash + (this.numeroControl != null ? this.numeroControl.hashCode() : 0);
        hash = 79 * hash + (this.rfc != null ? this.rfc.hashCode() : 0);
        hash = 79 * hash + (this.boid != null ? this.boid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DocumentoAprobar other = (DocumentoAprobar) obj;
        boolean buffer=validar(other);
        buffer&=validar(other.descripcionVigilancia);
        buffer&=validarNumeroControl(other);
        buffer&=validarRFC(other);
        buffer&=validarBOID(other);
        return buffer;
    }

    private boolean validar(DocumentoAprobar other){
        return !((this.numeroCarga == null) ? (other.numeroCarga != null) : !this.numeroCarga.equals(other.numeroCarga));
    }
    private boolean validar(String descripcionVigilancia){
        return !((this.descripcionVigilancia == null) ? (descripcionVigilancia != null) : !this.descripcionVigilancia.equals(descripcionVigilancia));
    }
    private boolean validarNumeroControl(DocumentoAprobar other){
        return !((this.numeroControl == null) ? (other.numeroControl != null) : !this.numeroControl.equals(other.numeroControl));
    }
    private boolean validarRFC(DocumentoAprobar other){
        return !((this.rfc == null) ? (other.rfc != null) : !this.rfc.equals(other.rfc));
    }
    private boolean validarBOID(DocumentoAprobar other){
        return !((this.boid == null) ? (other.boid != null) : !this.boid.equals(other.boid));
    }

    @Override
    public String toString() {
        return "DocumentoAprobar{" + "numeroCarga=" + numeroCarga + ", descripcionVigilancia=" + descripcionVigilancia + ", numeroControl=" + numeroControl + ", rfc=" + rfc + ", boid=" + boid + ", idTipoDocumento=" + idTipoDocumento + ", idAdministracionLocal=" + idAdministracionLocal + ", administracionLocal=" + administracionLocal + ", excluir=" + excluir + ", estadoValido=" + estadoValido + ", ultimoEstado=" + ultimoEstado + ", detalles=" + detalles + '}';
    }
    
}
