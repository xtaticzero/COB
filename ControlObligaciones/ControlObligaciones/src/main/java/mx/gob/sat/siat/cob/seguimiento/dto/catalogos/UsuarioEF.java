package mx.gob.sat.siat.cob.seguimiento.dto.catalogos;

import java.io.Serializable;

import java.util.Date;

public class UsuarioEF implements Serializable{
    private static final long serialVersionUID = -5197691180506547942L;
    
    private String rfcCorto;
    private Long idEntidadFederativa;
    private String nombreUsuario;
    private Date fechaInicio;
    private Date fechaFin;
    private String correoElectronico;
    private String entidadDesc;
    private String fechaFinStr;
    private String situacion;
    

    public UsuarioEF() {
        super();
    }

    public void setRfcCorto(String rfcCorto) {
        this.rfcCorto = rfcCorto;
    }

    public String getRfcCorto() {
        return rfcCorto;
    }

    public void setIdEntidadFederativa(Long idEntidadFederativa) {
        this.idEntidadFederativa = idEntidadFederativa;
    }

    public Long getIdEntidadFederativa() {
        return idEntidadFederativa;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
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

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setEntidadDesc(String entidadDesc) {
        this.entidadDesc = entidadDesc;
    }

    public String getEntidadDesc() {
        return entidadDesc;
    }
    
    public void setFechaFinStr(String fechaFinStr) {
        this.fechaFinStr = fechaFinStr;
    }

    public String getFechaFinStr() {
        return fechaFinStr;
    }

    public void setSituacion(String situacion) {
        this.situacion = situacion;
    }

    public String getSituacion() {
        return situacion;
    }
}
