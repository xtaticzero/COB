package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

import java.util.Date;

public class MultaPendienteDTO {
    
    private String numeroControl;
    private String descripcionVigilancia;
    private String nombreALSC;
    private String rfc;
    private String nombre;
    private String razonSocial;
    private String periodoVigilado;
    private String obligacionRequerida;
    private Date notificacionReq;
    private Date vencimientoReq;
    private boolean pendienteNotificar;
    private boolean reqProximoVencer;
    private Date presentacionObligacion;
    private String periodoDeclarado;
    private boolean cumplioAntesEfectosNotificacion;
    private boolean cumplioDentroPlazo15Dias;
    private boolean cumplioFueraPlazo15Dias;
    private boolean requerimientoVencido;
    private boolean canceladoPorCambioDomicilio;


    public void setDescripcionVigilancia(String descripcionVigilancia) {
        this.descripcionVigilancia = descripcionVigilancia;
    }

    public String getDescripcionVigilancia() {
        return descripcionVigilancia;
    }

    public void setNombreALSC(String nombreALSC) {
        this.nombreALSC = nombreALSC;
    }

    public String getNombreALSC() {
        return nombreALSC;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getRfc() {
        return rfc;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setPeriodoVigilado(String periodoVigilado) {
        this.periodoVigilado = periodoVigilado;
    }

    public String getPeriodoVigilado() {
        return periodoVigilado;
    }

    public void setObligacionRequerida(String obligacionRequerida) {
        this.obligacionRequerida = obligacionRequerida;
    }

    public String getObligacionRequerida() {
        return obligacionRequerida;
    }

    public void setNotificacionReq(Date notificacionReq) {
        if(notificacionReq!=null){
            this.notificacionReq = (Date)notificacionReq.clone();
        }
    }

    public Date getNotificacionReq() {
        if(notificacionReq==null){
            return null;
        }else{
            return (Date)notificacionReq.clone();
        }
    }

    public void setVencimientoReq(Date vencimientoReq) {
        if(vencimientoReq!=null){
            this.vencimientoReq = (Date)vencimientoReq.clone();
        }
    }

    public Date getVencimientoReq() {
        if(vencimientoReq==null){
            return null;
        }else{
            return (Date)vencimientoReq.clone();
        }
    }

    public void setPendienteNotificar(boolean pendienteNotificar) {
        this.pendienteNotificar = pendienteNotificar;
    }

    public boolean isPendienteNotificar() {
        return pendienteNotificar;
    }

    public void setReqProximoVencer(boolean reqProximoVencer) {
        this.reqProximoVencer = reqProximoVencer;
    }

    public boolean isReqProximoVencer() {
        return reqProximoVencer;
    }

    public void setPresentacionObligacion(Date presentacionObligacion) {
        if(presentacionObligacion!=null){
            this.presentacionObligacion = (Date)presentacionObligacion.clone();
        }
    }

    public Date getPresentacionObligacion() {
        if(presentacionObligacion==null){
            return null;
        }else{
            return (Date)presentacionObligacion.clone();
        }
    }

    public void setPeriodoDeclarado(String periodoDeclarado) {
        this.periodoDeclarado = periodoDeclarado;
    }

    public String getPeriodoDeclarado() {
        return periodoDeclarado;
    }

    public void setCumplioAntesEfectosNotificacion(boolean cumplioAntesEfectosNotificacion) {
        this.cumplioAntesEfectosNotificacion = cumplioAntesEfectosNotificacion;
    }

    public boolean isCumplioAntesEfectosNotificacion() {
        return cumplioAntesEfectosNotificacion;
    }

    public void setCumplioDentroPlazo15Dias(boolean cumplioDentroPlazo15Dias) {
        this.cumplioDentroPlazo15Dias = cumplioDentroPlazo15Dias;
    }

    public boolean isCumplioDentroPlazo15Dias() {
        return cumplioDentroPlazo15Dias;
    }

    public void setCumplioFueraPlazo15Dias(boolean cumplioFueraPlazo15Dias) {
        this.cumplioFueraPlazo15Dias = cumplioFueraPlazo15Dias;
    }

    public boolean isCumplioFueraPlazo15Dias() {
        return cumplioFueraPlazo15Dias;
    }

    public void setRequerimientoVencido(boolean requerimientoVencido) {
        this.requerimientoVencido = requerimientoVencido;
    }

    public boolean isRequerimientoVencido() {
        return requerimientoVencido;
    }

    public void setCanceladoPorCambioDomicilio(boolean canceladoPorCambioDomicilio) {
        this.canceladoPorCambioDomicilio = canceladoPorCambioDomicilio;
    }

    public boolean isCanceladoPorCambioDomicilio() {
        return canceladoPorCambioDomicilio;
    }

    public void setNumeroControl(String numeroControl) {
        this.numeroControl = numeroControl;
    }

    public String getNumeroControl() {
        return numeroControl;
    }
}
