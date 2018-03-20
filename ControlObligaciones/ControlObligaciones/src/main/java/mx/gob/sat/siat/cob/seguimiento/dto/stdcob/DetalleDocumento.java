package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

import java.io.Serializable;
import java.math.BigInteger;

import java.util.Date;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.SituacionIcepEnum;


public class DetalleDocumento implements Serializable {

    private String numeroControl;
    private Long claveIcep;
    private Integer idObligacion;
    private int idEjercicio;
    private int idPeriodo;
    private String fechaVencimiento;
    private Date fechaCumplimiento;
    private String idPeriodicidad;
    private int idSituacionIcep;
    private int idRegimen;
    private Double importeCargo;
    private Integer idTipoDeclaracion;
    private int tieneMultaExtemporaneidad;
    private int tieneMultaComplementaria;
    private String rutaArchivo;
    private String boid;
    private BigInteger idCumplimiento;
    private BigInteger idCumplimientoCompl;
    private Date fechaPresentacionCompl;
    private long estadoIcepEC;
    private Date fechaBaja;
    private Date fechaMantenimiento;
    

    /**
     *
     */
    public DetalleDocumento() {
        super();
    }

    /**
     *
     * @param numeroControl
     */
    public void setNumeroControl(String numeroControl) {
        this.numeroControl = numeroControl;
    }

    /**
     *
     * @return
     */
    public String getNumeroControl() {
        return numeroControl;
    }

    /**
     *
     * @param claveIcep
     */
    public void setClaveIcep(Long claveIcep) {
        this.claveIcep = claveIcep;
    }

    /**
     *
     * @return
     */
    public Long getClaveIcep() {
        return claveIcep;
    }

    /**
     *
     * @param idEjercicio
     */
    public void setIdEjercicio(String idEjercicio) {
        this.idEjercicio = Integer.valueOf(idEjercicio);
    }

    /**
     *
     * @return
     */
    public int getIdEjercicio() {
        return idEjercicio;
    }

    /**
     *
     * @param idPeriodo
     */
    public void setIdPeriodo(String idPeriodo) {
        this.idPeriodo = Integer.valueOf(idPeriodo);
    }

    /**
     *
     * @return
     */
    public int getIdPeriodo() {
        return idPeriodo;
    }

    /**
     *
     * @param fechaVencimiento
     */
    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    /**
     *
     * @return
     */
    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    /**
     *
     * @param fechaCumplimiento
     */
    public void setFechaCumplimiento(Date fechaCumplimiento) {
        if (fechaCumplimiento != null) {
            this.fechaCumplimiento = new Date(fechaCumplimiento.getTime());
        }
    }

    /**
     *
     * @return
     */
    public Date getFechaCumplimiento() {
        if (fechaCumplimiento != null) {
            return (Date) fechaCumplimiento.clone();
        } else {
            return null;
        }
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

    /**
     *
     * @return
     */
    public int getIdSituacionIcep() {
        return idSituacionIcep;
    }

    /**
     *
     * @param idSituacionIcep
     */
    public void setIdSituacionIcep(int idSituacionIcep) {
        this.idSituacionIcep = idSituacionIcep;
    }

    /**
     *
     * @param idRegimen
     */
    public void setIdRegimen(String idRegimen) {
        this.idRegimen = Integer.valueOf(idRegimen);
    }

    /**
     *
     * @return
     */
    public int getIdRegimen() {
        return idRegimen;
    }

    /**
     *
     * @param idObligacion
     */
    public void setIdObligacion(Integer idObligacion) {
        this.idObligacion = idObligacion;
    }

    /**
     *
     * @return
     */
    public Integer getIdObligacion() {
        return idObligacion;
    }

    /**
     *
     * @return
     */
    public Double getImporteCargo() {
        return importeCargo;
    }

    /**
     *
     * @param importeCargo
     */
    public void setImporteCargo(Double importeCargo) {
        this.importeCargo = importeCargo;
    }

    /**
     *
     * @return
     */
    public Integer getIdTipoDeclaracion() {
        return idTipoDeclaracion;
    }

    /**
     *
     * @param idTipoDeclaracion
     */
    public void setIdTipoDeclaracion(Integer idTipoDeclaracion) {
        this.idTipoDeclaracion = idTipoDeclaracion;
    }

    /**
     *
     * @return
     */
    public int getTieneMultaExtemporaneidad() {
        return tieneMultaExtemporaneidad;
    }

    /**
     *
     * @param tieneMultaExtemporaneidad
     */
    public void setTieneMultaExtemporaneidad(int tieneMultaExtemporaneidad) {
        this.tieneMultaExtemporaneidad = tieneMultaExtemporaneidad;
    }

    /**
     *
     * @return
     */
    public int getTieneMultaComplementaria() {
        return tieneMultaComplementaria;
    }

    /**
     *
     * @param tieneMultaComplementaria
     */
    public void setTieneMultaComplementaria(int tieneMultaComplementaria) {
        this.tieneMultaComplementaria = tieneMultaComplementaria;
    }

    /**
     *
     * @return
     */
    public SituacionIcepEnum getSituacionIcep() {
        for (SituacionIcepEnum situacion : SituacionIcepEnum.values()) {
            if (situacion.getValor() == idSituacionIcep) {
                return situacion;
            }
        }
        return null;
    }

    /**
     *
     * @return
     */
    public String getRutaArchivo() {
        return rutaArchivo;
    }

    /**
     *
     * @param rutaArchivo
     */
    public void setRutaArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    public String getBoid() {
        return boid;
    }

    public void setBoid(String boid) {
        this.boid = boid;
    }

    public BigInteger getIdCumplimiento() {
        return idCumplimiento;
    }

    public void setIdCumplimiento(BigInteger idCumplimiento) {
        this.idCumplimiento = idCumplimiento;
    }

    public BigInteger getIdCumplimientoCompl() {
        return idCumplimientoCompl;
    }

    public void setIdCumplimientoCompl(BigInteger idCumplimientoCompl) {
        this.idCumplimientoCompl = idCumplimientoCompl;
    }

   

    public long getEstadoIcepEC() {
        return estadoIcepEC;
    }

    public void setEstadoIcepEC(long estadoIcepEC) {
        this.estadoIcepEC = estadoIcepEC;
    }

    
    
    public Date getFechaPresentacionCompl() {
        if (fechaPresentacionCompl != null) {
            return (Date) fechaPresentacionCompl.clone();
        } else {
            return null;
        }
    }

    public void setFechaPresentacionCompl(Date fechaPresentacionCompl) {
        if (fechaPresentacionCompl != null) {
            this.fechaPresentacionCompl = (Date) fechaPresentacionCompl.clone();
        }
    }
    
    /**
     *
     * @param fechaCumplimiento
     */
    public void setFechaBaja(Date fechaBaja) {
        if (fechaBaja != null) {
            this.fechaBaja = new Date(fechaBaja.getTime());
        }
    }

    /**
     *
     * @return
     */
    public Date getFechaBaja() {
        if (fechaBaja != null) {
            return (Date) fechaBaja.clone();
        } else {
            return null;
        }
    }
    
    /**
     *
     * @param fechaMantenimiento
     */
    public void setFechaMantenimiento(Date fechaMantenimiento) {
        if (fechaMantenimiento != null) {
            this.fechaMantenimiento = new Date(fechaMantenimiento.getTime());
        }
    }

    /**
     *
     * @return
     */
    public Date getFechaMantenimiento() {
        if (fechaMantenimiento != null) {
            return (Date) fechaMantenimiento.clone();
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        return  claveIcep + "," + idObligacion + 
                "," + idEjercicio + "," + idPeriodo + 
                "," + fechaVencimiento + 
                "," + fechaCumplimiento + 
                "," + idPeriodicidad + 
                "," + idSituacionIcep + "," + idRegimen + 
                "," + importeCargo + "," + idTipoDeclaracion + 
                "," + tieneMultaExtemporaneidad + 
                "," + tieneMultaComplementaria;
    }


   
}
