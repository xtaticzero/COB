package mx.gob.sat.siat.cob.seguimiento.dto.catalogos;

import java.io.Serializable;

import java.util.Date;


public class MultaDescuento implements Serializable {
    private static final long serialVersionUID = -5197691180506547942L;
    
    public MultaDescuento() {
        super();
    }
    
    private Long    idMultaMonto;
    private Date    fechaInicio;
    private Date    fechaFin;
    private String  idResolMotivo;
    private Long    idMultaDescuento;
    
    private String  resolMotivoDescr;
    private String  multaDescuentoDescr;


    public void setIdMultaMonto(Long idMultaMonto) {
        this.idMultaMonto = idMultaMonto;
    }

    public Long getIdMultaMonto() {
        return idMultaMonto;
    }

    public void setIdResolMotivo(String idResolMotivo) {
        this.idResolMotivo = idResolMotivo;
    }

    public String getIdResolMotivo() {
        return idResolMotivo;
    }

    public void setIdMultaDescuento(Long idMultaDescuento) {
        this.idMultaDescuento = idMultaDescuento;
    }

    public Long getIdMultaDescuento() {
        return idMultaDescuento;
    }

    public void setResolMotivoDescr(String resolMotivoDescr) {
        this.resolMotivoDescr = resolMotivoDescr;
    }

    public String getResolMotivoDescr() {
        return resolMotivoDescr;
    }

    public void setMultaDescuentoDescr(String multaDescuentoDescr) {
        this.multaDescuentoDescr = multaDescuentoDescr;
    }

    public String getMultaDescuentoDescr() {
        return multaDescuentoDescr;
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
            this.fechaInicio = null;
        }
    }
    public Date getFechaFin() {
        if(fechaFin!=null){
        return (Date) fechaFin.clone();
        }
        return null;
    }
}
