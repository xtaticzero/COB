package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

import java.io.Serializable;

import java.util.Date;

public class ParametrosSgtDTO implements Serializable {

    private int idParametro;
    private String nombre;
    private String descripcion;
    private Date fechaInicio;
    private Date fechaFin;
    private int orden;
    private String tipoDato;
    private String valor;
    private String precision;

    public void setIdParametro(int idParametro) {
        this.idParametro = idParametro;
    }

    public int getIdParametro() {
        return idParametro;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setFechaInicio(Date fechaInicio) {
        if (fechaInicio != null) {
            this.fechaInicio = (Date) fechaInicio.clone();
        }
    }

    public Date getFechaInicio() {
        if (fechaInicio == null) {
            return null;
        } else {
            return (Date) fechaInicio.clone();
        }
    }

    public void setFechaFin(Date fechaFin) {
        if (fechaFin != null) {
            this.fechaFin = (Date) fechaFin.clone();
        }
    }

    public Date getFechaFin() {
        if (fechaFin == null) {
            return null;
        } else {
            return (Date) fechaFin.clone();
        }
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public int getOrden() {
        return orden;
    }

    public void setTipoDato(String tipoDato) {
        this.tipoDato = tipoDato;
    }

    public String getTipoDato() {
        return tipoDato;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

    public String getPrecision() {
        return precision;
    }

    public void setPrecision(String precision) {
        this.precision = precision;
    }
}
