package mx.gob.sat.siat.cob.seguimiento.dto.catalogos;

import java.io.Serializable;
import java.util.Date;


public class FundamentoLegal implements Serializable {
    private static final long serialVersionUID = -5197691180506547942L;
    
    private Long    idFundamentoLegal;
    private Long    idObligacion;
    private String  idObligacionDescr;
    private Long    idRegimen;
    private String  idRegimenDescr;
    private Long    idEjercicioFiscal;
    private String  idEjercicioFiscalDescr;
    private String  idPeriodo;
    private String  idPeriodicidad;
    private String  idPeriodoDescr;
    private String  fundamentoLegal;
    private Date    fechaInicio;
    private String  fechaInicioStr;
    private Date    fechaFin;
    private String  fechaFinStr;
    private String  descripcionCorta;
    private String  descripcionLarga;
    private Date    fechaVencimiento;
    private String  fechaVencimientoStr;
    
    /**
     *
     */
    public FundamentoLegal() {
        super();
    }

    /**
     *
     * @param idFundamentoLegal
     */
    public void setIdFundamentoLegal(Long idFundamentoLegal) {
        this.idFundamentoLegal = idFundamentoLegal;
    }

    /**
     *
     * @return
     */
    public Long getIdFundamentoLegal() {
        return idFundamentoLegal;
    }

    /**
     *
     * @param idObligacion
     */
    public void setIdObligacion(Long idObligacion) {
        this.idObligacion = idObligacion;
    }

    /**
     *
     * @return
     */
    public Long getIdObligacion() {
        return idObligacion;
    }

    /**
     *
     * @param idObligacionDescr
     */
    public void setIdObligacionDescr(String idObligacionDescr) {
        this.idObligacionDescr = idObligacionDescr;
    }

    /**
     *
     * @return
     */
    public String getIdObligacionDescr() {
        return idObligacionDescr;
    }

    /**
     *
     * @param idRegimen
     */
    public void setIdRegimen(Long idRegimen) {
        this.idRegimen = idRegimen;
    }

    /**
     *
     * @return
     */
    public Long getIdRegimen() {
        return idRegimen;
    }

    /**
     *
     * @param idRegimenDescr
     */
    public void setIdRegimenDescr(String idRegimenDescr) {
        this.idRegimenDescr = idRegimenDescr;
    }

    /**
     *
     * @return
     */
    public String getIdRegimenDescr() {
        return idRegimenDescr;
    }

    /**
     *
     * @param idEjercicioFiscal
     */
    public void setIdEjercicioFiscal(Long idEjercicioFiscal) {
        this.idEjercicioFiscal = idEjercicioFiscal;
    }

    /**
     *
     * @return
     */
    public Long getIdEjercicioFiscal() {
        return idEjercicioFiscal;
    }

    /**
     *
     * @param idEjercicioFiscalDescr
     */
    public void setIdEjercicioFiscalDescr(String idEjercicioFiscalDescr) {
        this.idEjercicioFiscalDescr = idEjercicioFiscalDescr;
    }

    /**
     *
     * @return
     */
    public String getIdEjercicioFiscalDescr() {
        return idEjercicioFiscalDescr;
    }

    /**
     *
     * @param idPeriodo
     */
    public void setIdPeriodo(String idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    /**
     *
     * @return
     */
    public String getIdPeriodo() {
        return idPeriodo;
    }

    /**
     *
     * @param idPeriodoDescr
     */
    public void setIdPeriodoDescr(String idPeriodoDescr) {
        this.idPeriodoDescr = idPeriodoDescr;
    }

    /**
     *
     * @return
     */
    public String getIdPeriodoDescr() {
        return idPeriodoDescr;
    }

    /**
     *
     * @param fundamentoLegal
     */
    public void setFundamentoLegal(String fundamentoLegal) {
        this.fundamentoLegal = fundamentoLegal;
    }

    /**
     *
     * @return
     */
    public String getFundamentoLegal() {
        return fundamentoLegal;
    }

    /**
     *
     * @param fechaInicio
     */
    public void setFechaInicio(Date fechaInicio) {
        if (fechaInicio != null) {
            this.fechaInicio = (Date) fechaInicio.clone();
        }
    }

    /**
     *
     * @return
     */
    public Date getFechaInicio() {
        if(fechaInicio!=null){
        return (Date) fechaInicio.clone();
        }
        return null;
    }

    /**
     *
     * @param fechaFin
     */
    public void setFechaFin(Date fechaFin) {
        if (fechaFin != null) {
            this.fechaFin = (Date) fechaFin.clone();
        }
    }

    /**
     *
     * @return
     */
    public Date getFechaFin() {
        if(fechaFin!=null){
        return (Date) fechaFin.clone();
        }
        return null;
    }

    /**
     *
     * @param idPeriodicidad
     */
    public void setIdPeriodicidad(String idPeriodicidad) {
        this.idPeriodicidad = idPeriodicidad;
    }

    /**
     *
     * @return
     */
    public String getIdPeriodicidad() {
        return idPeriodicidad;
    }

    public void setDescripcionCorta(String descripcionCorta) {
        this.descripcionCorta = descripcionCorta;
    }

    public String getDescripcionCorta() {
        return descripcionCorta;
    }

    public void setDescripcionLarga(String descripcionLarga) {
        this.descripcionLarga = descripcionLarga;
    }

    public String getDescripcionLarga() {
        return descripcionLarga;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento != null ? new Date(fechaVencimiento.getTime()): null;
    }

    public Date getFechaVencimiento() {
        if(fechaVencimiento!=null){
        return (Date) fechaVencimiento.clone();
        }
        return null;
    }

    public void setFechaVencimientoStr(String fechaVencimientoStr) {
        this.fechaVencimientoStr = fechaVencimientoStr;
    }

    public String getFechaVencimientoStr() {
        return fechaVencimientoStr;
    }

    public String getFechaInicioStr() {
        return fechaInicioStr;
    }

    public void setFechaInicioStr(String fechaInicioStr) {
        this.fechaInicioStr = fechaInicioStr;
    }

    public String getFechaFinStr() {
        return fechaFinStr;
    }

    public void setFechaFinStr(String fechaFinStr) {
        this.fechaFinStr = fechaFinStr;
    }

    @Override
    public String toString() {
        return "FundamentoLegal{" + "idFundamentoLegal=" + idFundamentoLegal + ", idObligacion=" + idObligacion + ", idRegimen=" + idRegimen + ", idEjercicioFiscal=" + idEjercicioFiscal + ", idPeriodo=" + idPeriodo + ", idPeriodicidad=" + idPeriodicidad + ", fundamentoLegal=" + fundamentoLegal + ", fechaVencimiento=" + fechaVencimiento + '}';
    }
    
}
