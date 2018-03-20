package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

import java.io.Serializable;

import java.util.Date;
import java.util.List;

public class ReporteVigilancia implements Serializable {

    private static final long serialVersionUID = -5197691180506547942L;
    private String fechaHoraVigilancia;
    private String idVigilancia;
    private String tipoDocumento;
    private String tipoVigilancia;
    private String periodoRequerido;
    private String ejercicioRequerido;
    private Date fechaLiberacion;
    private String fechaLiberacionStr;
    private int idTipoMedio;
    private String tipoMedio;
    private List<AlscDTO> lstALSC;
    private List<AlscDTO> lstEF;

    /**
     * Get the value of lstEF
     *
     * @return the value of lstEF
     */
    public List<AlscDTO> getLstEF() {
        return lstEF;
    }

    /**
     * Set the value of lstEF
     *
     * @param lstEF new value of lstEF
     */
    public void setLstEF(List<AlscDTO> lstEF) {
        this.lstEF = lstEF;
    }

    public void setFechaHoraVigilancia(String fechaHoraVigilancia) {
        this.fechaHoraVigilancia = fechaHoraVigilancia;
    }

    public String getFechaHoraVigilancia() {
        return fechaHoraVigilancia;
    }

    public void setIdVigilancia(String idVigilancia) {
        this.idVigilancia = idVigilancia;
    }

    public String getIdVigilancia() {
        return idVigilancia;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoVigilancia(String tipoVigilancia) {
        this.tipoVigilancia = tipoVigilancia;
    }

    public String getTipoVigilancia() {
        return tipoVigilancia;
    }

    public void setPeriodoRequerido(String periodoRequerido) {
        this.periodoRequerido = periodoRequerido;
    }

    public String getPeriodoRequerido() {
        return periodoRequerido;
    }

    public void setEjercicioRequerido(String ejercicioRequerido) {
        this.ejercicioRequerido = ejercicioRequerido;
    }

    public String getEjercicioRequerido() {
        return ejercicioRequerido;
    }

    public void setFechaLiberacion(Date fechaLiberacion) {
        if (fechaLiberacion != null) {
            this.fechaLiberacion = (Date) fechaLiberacion.clone();
        } else {
            this.fechaLiberacion = null;
        }
    }

    public Date getFechaLiberacion() {
        if (fechaLiberacion != null) {
            return (Date) fechaLiberacion.clone();
        }
        return null;
    }

    /**
     * Get the value of lstALSC
     *
     * @return the value of lstALSC
     */
    public List<AlscDTO> getLstALSC() {
        return lstALSC;
    }

    /**
     * Set the value of lstALSC
     *
     * @param lstALSC new value of lstALSC
     */
    public void setLstALSC(List<AlscDTO> lstALSC) {
        this.lstALSC = lstALSC;
    }

    public String getFechaLiberacionStr() {
        return fechaLiberacionStr;
    }

    public void setFechaLiberacionStr(String fechaLiberacionStr) {
        this.fechaLiberacionStr = fechaLiberacionStr;
    }

    public void setTipoMedio(String tipoMedio) {
        this.tipoMedio = tipoMedio;
    }

    public String getTipoMedio() {
        return tipoMedio;
    }

    public void setIdTipoMedio(int idTipoMedio) {
        this.idTipoMedio = idTipoMedio;
    }

    public int getIdTipoMedio() {
        return idTipoMedio;
    }
}
