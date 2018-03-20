/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author root
 */
public class VigilanciaAprobar implements Serializable{

    private String numeroCarga;
    private String descripcionVigilancia;
    private String tipoDocumento;
    private Long cantidadDocumentos;
    private String tipoFirma;
    private String idFirma;
    private Date fechaCorte;
    private Date fechaCarga;
    private String nombrePlantilla;
    private Integer idPlantilla;
    private String numeroEmpleado;
    private String tipoMedio;
    private String administracionLocal;
    private String idAdministracionLocal;
    private Date fechaValidacion;
    private boolean procesando;
    private Long cantidadVigilanciasAL;
    private Integer conteoElementos;
    private Long cantidadVigilanciasMostrar;

    public VigilanciaAprobar() {
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

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public Long getCantidadDocumentos() {
        return cantidadDocumentos;
    }

    public void setCantidadDocumentos(Long cantidadDocumentos) {
        this.cantidadDocumentos = cantidadDocumentos;
    }

    public String getTipoFirma() {
        return tipoFirma;
    }

    public void setTipoFirma(String tipoFirma) {
        this.tipoFirma = tipoFirma;
    }

    public Date getFechaCorte() {
        if(fechaCorte==null){
            return null;
        }else{
            return (Date)fechaCorte.clone();
        }
    }

    public void setFechaCorte(Date fechaCorte) {
        if(fechaCorte!=null){
            this.fechaCorte = (Date)fechaCorte.clone();
        }
    }

    public Date getFechaCarga() {
        if(fechaCarga==null){
            return null;
        }else{
            return (Date)fechaCarga.clone();
        }
    }

    public void setFechaCarga(Date fechaCarga) {
        if(fechaCarga!=null){
            this.fechaCarga = (Date)fechaCarga.clone();
        }
    }

    public String getNombrePlantilla() {
        return nombrePlantilla;
    }

    public void setNombrePlantilla(String nombrePlantilla) {
        this.nombrePlantilla = nombrePlantilla;
    }

    public String getNumeroEmpleado() {
        return numeroEmpleado;
    }

    public void setNumeroEmpleado(String numeroEmpleado) {
        this.numeroEmpleado = numeroEmpleado;
    }

    public String getTipoMedio() {
        return tipoMedio;
    }

    public void setTipoMedio(String tipoMedio) {
        this.tipoMedio = tipoMedio;
    }

    public String getAdministracionLocal() {
        return administracionLocal;
    }

    public void setAdministracionLocal(String administracionLocal) {
        this.administracionLocal = administracionLocal;
    }

    public String getIdAdministracionLocal() {
        return idAdministracionLocal;
    }

    public void setIdAdministracionLocal(String idAdministracionLocal) {
        this.idAdministracionLocal = idAdministracionLocal;
    }

    public boolean isProcesando() {
        return procesando;
    }

    public void setProcesando(boolean procesando) {
        this.procesando = procesando;
    }

    public Date getFechaValidacion() {
        return  fechaValidacion==null?fechaValidacion:(Date)fechaValidacion.clone();
    }

    public void setFechaValidacion(Date fechaValidacion) {
        if (fechaValidacion != null) {
            this.fechaValidacion = new Date(fechaValidacion.getTime());
        }
    }

    public Integer getIdPlantilla() {
        return idPlantilla;
    }

    public void setIdPlantilla(Integer idPlantilla) {
        this.idPlantilla = idPlantilla;
    }

    public String getIdFirma() {
        return idFirma;
    }

    public void setIdFirma(String idFirma) {
        this.idFirma = idFirma;
    }
    
    public Long getCantidadVigilanciasAL() {
        return cantidadVigilanciasAL;
    }

    public void setCantidadVigilanciasAL(Long cantidadVigilanciasAL) {
        this.cantidadVigilanciasAL = cantidadVigilanciasAL;
    }

    public Integer getConteoElementos() {
        return conteoElementos;
    }

    public void setConteoElementos(Integer conteoElementos) {
        this.conteoElementos = conteoElementos;
    }

    public Long getCantidadVigilanciasMostrar() {
        return cantidadVigilanciasMostrar;
    }

    public void setCantidadVigilanciasMostrar(Long cantidadVigilanciasMostrar) {
        this.cantidadVigilanciasMostrar = cantidadVigilanciasMostrar;
    }
    
}
