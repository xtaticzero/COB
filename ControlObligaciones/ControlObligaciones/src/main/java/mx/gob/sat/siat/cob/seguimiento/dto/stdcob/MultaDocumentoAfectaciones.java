package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

import java.io.Serializable;

public class MultaDocumentoAfectaciones implements Serializable {
   
    private static final long serialVersionUID = -5197691180506547942L;
    private String tipoMulta;
    private String numResolucion;
    private Long idObligacion;
    private String descObligacion;
    private Long monto;
    private Long montoTotal;
    private String nombreEstado;
    private String periodo;
    private String ejercicio;
   
        
    public MultaDocumentoAfectaciones() {
        
    }

    public void setTipoMulta(String tipoMulta) {
        this.tipoMulta = tipoMulta;
    }

    public String getTipoMulta() {
        return tipoMulta;
    }

    public void setNumResolucion(String numResolucion) {
        this.numResolucion = numResolucion;
    }

    public String getNumResolucion() {
        return numResolucion;
    }

    public void setIdObligacion(Long idObligacion) {
        this.idObligacion = idObligacion;
    }

    public Long getIdObligacion() {
        return idObligacion;
    }

    public void setDescObligacion(String descObligacion) {
        this.descObligacion = descObligacion;
    }

    public String getDescObligacion() {
        return descObligacion;
    }

    public void setMonto(Long monto) {
        this.monto = monto;
    }

    public Long getMonto() {
        return monto;
    }

    public void setMontoTotal(Long montoTotal) {
        this.montoTotal = montoTotal;
    }

    public Long getMontoTotal() {
        return montoTotal;
    }

    public String getNombreEstado() {
        return nombreEstado;
    }

    public void setNombreEstado(String nonbreEstado) {
        this.nombreEstado = nonbreEstado;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getEjercicio() {
        return ejercicio;
    }

    public void setEjercicio(String ejercicio) {
        this.ejercicio = ejercicio;
    }

}
