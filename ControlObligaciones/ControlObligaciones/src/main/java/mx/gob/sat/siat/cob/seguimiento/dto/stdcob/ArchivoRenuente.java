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
 * @author juan
 */
public class ArchivoRenuente implements Serializable {

    private static final long serialVersionUID = -4197691180506547943L;

    private Long idCargaRenunetes;
    private String usuarioCarga;
    private String numEmpleadoCarga;
    private String nombreArchivoCarga;
    private Integer totalRegistrosArchivoCarga;
    private String rutaArchivoResultado;
    private Integer totalRegistrosErrores;
    private Date fechaCarga;
    private String fechaCargaStr;

    public ArchivoRenuente() {
    }

    public Long getIdCargaRenunetes() {
        return idCargaRenunetes;
    }

    public void setIdCargaRenunetes(Long idCargaRenunetes) {
        this.idCargaRenunetes = idCargaRenunetes;
    }

    public String getUsuarioCarga() {
        return usuarioCarga;
    }

    public void setUsuarioCarga(String usuarioCarga) {
        this.usuarioCarga = usuarioCarga;
    }

    public String getNumEmpleadoCarga() {
        return numEmpleadoCarga;
    }

    public void setNumEmpleadoCarga(String numEmpleadoCarga) {
        this.numEmpleadoCarga = numEmpleadoCarga;
    }

    public String getNombreArchivoCarga() {
        return nombreArchivoCarga;
    }

    public void setNombreArchivoCarga(String nombreArchivoCarga) {
        this.nombreArchivoCarga = nombreArchivoCarga;
    }

    public Integer getTotalRegistrosArchivoCarga() {
        return totalRegistrosArchivoCarga;
    }

    public void setTotalRegistrosArchivoCarga(Integer totalRegistrosArchivoCarga) {
        this.totalRegistrosArchivoCarga = totalRegistrosArchivoCarga;
    }

    public String getRutaArchivoResultado() {
        return rutaArchivoResultado;
    }

    public void setRutaArchivoResultado(String rutaArchivoResultado) {
        this.rutaArchivoResultado = rutaArchivoResultado;
    }

    public Integer getTotalRegistrosErrores() {
        return totalRegistrosErrores;
    }

    public void setTotalRegistrosErrores(Integer totalRegistrosErrores) {
        this.totalRegistrosErrores = totalRegistrosErrores;
    }

    public Date getFechaCarga() {
        if (fechaCarga != null) {
            return (Date) fechaCarga.clone();
        }
        return null;
    }

    public void setFechaCarga(Date fechaCarga) {
        if (fechaCarga != null) {
            this.fechaCarga = (Date) fechaCarga.clone();
        } else {
            this.fechaCarga = null;
        }
    }

    public String getFechaCargaStr() {
        return fechaCargaStr;
    }

    public void setFechaCargaStr(String fechaCargaStr) {
        this.fechaCargaStr = fechaCargaStr;
    }

}
