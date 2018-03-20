package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

import java.io.Serializable;
import java.util.Date;

public class FuncionarioSat implements Serializable {

    private Integer idCargoAdministrativo;
    private String descCargoAdministrativo;
    private String numeroEmpleado;
    private Integer numeroEmpleadoSuperior;
    private String idAdmonLocal;
    private Integer nivelEmision;
    private String descNivelEmision;
    private Date fechaInicio;
    private Date fechaFin;
    private Integer idEventoCarga;
    private String descEventoCarga;
    private String idRegionAlr;
    private String nombreEmpleado;
    private String nombreAlsc;
    private String numeroTabla;

    public FuncionarioSat() {
        super();
    }

    public void setIdCargoAdministrativo(Integer idCargoAdministrativo) {
        this.idCargoAdministrativo = idCargoAdministrativo;
    }

    public Integer getIdCargoAdministrativo() {
        return idCargoAdministrativo;
    }

    public void setNumeroEmpleado(String numeroEmpleado) {
        this.numeroEmpleado = numeroEmpleado;
    }

    public String getNumeroEmpleado() {
        return numeroEmpleado;
    }

    public void setNumeroEmpleadoSuperior(Integer numeroEmpleadoSuperior) {
        this.numeroEmpleadoSuperior = numeroEmpleadoSuperior;
    }

    public Integer getNumeroEmpleadoSuperior() {
        return numeroEmpleadoSuperior;
    }

    public void setIdAdmonLocal(String idAdmonLocal) {
        this.idAdmonLocal = idAdmonLocal;
    }

    public String getIdAdmonLocal() {
        return idAdmonLocal;
    }

    public void setNivelEmision(Integer nivelEmision) {
        this.nivelEmision = nivelEmision;
    }

    public Integer getNivelEmision() {
        return nivelEmision;
    }

    public void setFechaInicio(Date fechaInicio) {
        if (fechaInicio != null) {
            this.fechaInicio = new Date(fechaInicio.getTime());
        }
    }

    public Date getFechaInicio() {
        if (fechaInicio != null) {
            return (Date) fechaInicio.clone();
        } else {
            return null;
        }
    }

    public void setFechaFin(Date fechaFin) {
        if (fechaFin != null) {
            this.fechaFin = (Date) fechaFin.clone();
        }
    }

    public Date getFechaFin() {
        if (fechaFin != null) {
            return (Date) fechaFin.clone();
        } else {
            return null;
        }
    }

    public void setIdEventoCarga(Integer idEventoCarga) {
        this.idEventoCarga = idEventoCarga;
    }

    public Integer getIdEventoCarga() {
        return idEventoCarga;
    }

    public void setDescCargoAdministrativo(String descCargoAdministrativo) {
        this.descCargoAdministrativo = descCargoAdministrativo;
    }

    public String getDescCargoAdministrativo() {
        return descCargoAdministrativo;
    }

    public void setDescNivelEmision(String descNivelEmision) {
        this.descNivelEmision = descNivelEmision;
    }

    public String getDescNivelEmision() {
        return descNivelEmision;
    }

    public void setDescEventoCarga(String descEventoCarga) {
        this.descEventoCarga = descEventoCarga;
    }

    public String getDescEventoCarga() {
        return descEventoCarga;
    }

    public void setIdRegionAlr(String idRegionAlr) {
        this.idRegionAlr = idRegionAlr;
    }

    public String getIdRegionAlr() {
        return idRegionAlr;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreAlsc(String nombreAlsc) {
        this.nombreAlsc = nombreAlsc;
    }

    public String getNombreAlsc() {
        return nombreAlsc;
    }

    public void setNumeroTabla(String numeroTabla) {
        this.numeroTabla = numeroTabla;
    }

    public String getNumeroTabla() {
        return numeroTabla;
    }
}
