package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

import java.util.Date;

public class ResultadoDiligencia {

    private String fechaAfectacion;
    private String numeroControl;
    private String estatus;
    private String resultado;
    private boolean cargaMasiva;
    private Date fechaDiligencia;
    private Long claveEf;
    private Date fechaCitatorio;

    public ResultadoDiligencia() {
        super();
    }

    public void setFechaAfectacion(String fechaAfectacion) {
        this.fechaAfectacion = fechaAfectacion;
    }

    public String getFechaAfectacion() {
        return fechaAfectacion;
    }

    public void setNumeroControl(String numeroControl) {
        this.numeroControl = numeroControl;
    }

    public String getNumeroControl() {
        return numeroControl;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getResultado() {
        return resultado;
    }

    public void setCargaMasiva(boolean cargaMasiva) {
        this.cargaMasiva = cargaMasiva;
    }

    public boolean isCargaMasiva() {
        return cargaMasiva;
    }

    public void setFechaDiligencia(Date fechaDiligencia) {
        if (fechaDiligencia != null) {
            this.fechaDiligencia = (Date) fechaDiligencia.clone();
        } else {
            this.fechaDiligencia = null;
        }
    }

    public Date getFechaDiligencia() {
        if (fechaDiligencia != null) {
            return (Date) fechaDiligencia.clone();
        }
        return null;
    }

    public void setClaveEf(Long claveEf) {
        this.claveEf = claveEf;
    }

    public Long getClaveEf() {
        return claveEf;
    }

    public Date getFechaCitatorio() {
        if (fechaCitatorio != null) {
            return (Date) fechaCitatorio.clone();
        }
        return null;
    }

    public void setFechaCitatorio(Date fechaCitatorio) {
        if (fechaCitatorio != null) {
            this.fechaCitatorio = (Date) fechaCitatorio.clone();
        } else {
            this.fechaCitatorio = null;
        }
    }

}
