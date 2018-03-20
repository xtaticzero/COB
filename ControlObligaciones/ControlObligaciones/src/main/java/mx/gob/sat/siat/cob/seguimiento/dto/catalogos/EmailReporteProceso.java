package mx.gob.sat.siat.cob.seguimiento.dto.catalogos;

import java.io.Serializable;

import java.util.Date;

public class EmailReporteProceso implements Serializable{
    private static final long serialVersionUID = -5197691180506547942L;
    
    private Long idEmailReporteProceso;
    private String nombreCompleto;
    private String correoElectronico;
    private String correoElectronicoAlterno;
    private Date fechaInicio;
    private Date fechaFin;
    private Boolean isEc;

    public Boolean isIsEc() {
        return isEc;
    }

    public void setIsEc(Boolean isEc) {
        this.isEc = isEc;
    }
    
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronicoAlterno(String correoElectronicoAlterno) {
        this.correoElectronicoAlterno = correoElectronicoAlterno;
    }

    public String getCorreoElectronicoAlterno() {
        return correoElectronicoAlterno;
    }

    public void setFechaInicio(Date fechaInicio) {
        if (fechaInicio != null) {
            this.fechaInicio = (Date) fechaInicio.clone();
        }else{
            this.fechaInicio = null;
        }
    }

    public Date getFechaInicio() {
        if(fechaInicio!=null){
        return (Date) fechaInicio.clone();
        }
        return null;
    }

    public void setFechaFin(Date fechaFin) {
        if (fechaFin != null) {
            this.fechaFin = (Date) fechaFin.clone();
        }else{
            this.fechaFin = null;
        }
    }

    public Date getFechaFin() {
        if(fechaFin!=null){
        return (Date) fechaFin.clone();
        }
        return null;
    }

    public void setIdEmailReporteProceso(Long idEmailReporteProceso) {
        this.idEmailReporteProceso = idEmailReporteProceso;
    }

    public Long getIdEmailReporteProceso() {
        return idEmailReporteProceso;
    }
}
