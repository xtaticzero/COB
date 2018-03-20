/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author root
 */
public class ArchivoDiligencia implements Serializable{
    private static final long serialVersionUID = -5197691180506547942L;
    
    private Long idEntidadFederativa;
    private String nombreArchivoCarga;
    private Integer totalRegistrosCarga;
    private Integer totalRegistrosProcesados;
    private String rutaArchivoResultado;
    private Date fechaCarga;
    private String  fechaCargaStr;

    public Long getIdEntidadFederativa() {
        return idEntidadFederativa;
    }

    public void setIdEntidadFederativa(Long idEntidadFederativa) {
        this.idEntidadFederativa = idEntidadFederativa;
    }

    public String getNombreArchivoCarga() {
        return nombreArchivoCarga;
    }

    public void setNombreArchivoCarga(String nombreArchivoCarga) {
        this.nombreArchivoCarga = nombreArchivoCarga;
    }

    public Integer getTotalRegistrosCarga() {
        return totalRegistrosCarga;
    }

    public void setTotalRegistrosCarga(Integer totalRegistrosCarga) {
        this.totalRegistrosCarga = totalRegistrosCarga;
    }

    public Integer getTotalRegistrosProcesados() {
        return totalRegistrosProcesados;
    }

    public void setTotalRegistrosProcesados(Integer totalRegistrosProcesados) {
        this.totalRegistrosProcesados = totalRegistrosProcesados;
    }

    public String getRutaArchivoResultado() {
        return rutaArchivoResultado;
    }

    public void setRutaArchivoResultado(String rutaArchivoResultado) {
        this.rutaArchivoResultado = rutaArchivoResultado;
    }

  
    public void setFechaCarga(Date fechaCarga) {
        if (fechaCarga != null) {
            this.fechaCarga = (Date) fechaCarga.clone();
        }
    }

    public Date getFechaCarga() {
        if(fechaCarga!=null){
        return (Date) fechaCarga.clone();
        }
        return null;
    }

    public String getFechaCargaStr() {
        return fechaCargaStr;
    }

    public void setFechaCargaStr(String fechaCargaStr) {
        this.fechaCargaStr = fechaCargaStr;
    }
    
   
    
}
