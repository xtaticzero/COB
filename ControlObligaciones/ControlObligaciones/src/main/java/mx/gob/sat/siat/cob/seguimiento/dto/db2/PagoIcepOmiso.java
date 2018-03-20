package mx.gob.sat.siat.cob.seguimiento.dto.db2;

import java.io.Serializable;

import java.math.BigInteger;

import java.util.Date;


public class PagoIcepOmiso implements Serializable {
    
    private BigInteger boid;
    private Long claveIcep;
    private Long claveIcepHistPago;
    private int ejercicio;
    private int periodo;
    private String periodicidad;
    private Long identificadorCumplimiento;
    private Long importeaCargo;
    private Date fechaPresentacion;
    private Long numOperacion;
    private int tipoDeclaracion;
    private String numeroControl;
    private int esCantidadDetMayor;
        
    /**
     *
     */
    public PagoIcepOmiso() {
        super();
    }
    /**
     *
     * @param boid
     */
    public void setBoid(String boid) {
        this.boid = new BigInteger(boid);
    }

    public int getEjercicio() {
        return ejercicio;
    }

    public void setEjercicio(int ejercicio) {
        this.ejercicio = ejercicio;
    }

    public int getPeriodo() {
        return periodo;
    }

    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }

    public String getPeriodicidad() {
        return periodicidad;
    }

    public void setPeriodicidad(String periodicidad) {
        this.periodicidad = periodicidad;
    }
    
    /**
     *
     * @return
     */
    public String getBoid() {
        return boid.toString();
    }

    /**
     *
     * @param claveIcep
     */
    public void setClaveIcep(Long claveIcep) {
        this.claveIcep = claveIcep;
    }

    
    public Long getClaveIcepHistPago() {
        return claveIcepHistPago;
    }

    public void setClaveIcepHistPago(Long claveIcepHistPago) {
        this.claveIcepHistPago = claveIcepHistPago;
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
     * @param identificadorCumplimiento
     */
    public void setIdentificadorCumplimiento(Long identificadorCumplimiento) {
        this.identificadorCumplimiento = identificadorCumplimiento;
    }

    /**
     *
     * @return
     */
    public Long getIdentificadorCumplimiento() {
        return identificadorCumplimiento;
    }

    /**
     *
     * @param importeaCargo
     */
    public void setImporteaCargo(Long importeaCargo) {
        this.importeaCargo = importeaCargo;
    }

    /**
     *
     * @return
     */
    public Long getImporteaCargo() {
        return importeaCargo;
    }

    /**
     *
     * @param fechaPresentacion
     */
    public void setFechaPresentacion(Date fechaPresentacion) {
        if(fechaPresentacion!=null){
            this.fechaPresentacion = new Date(fechaPresentacion.getTime());
        }
    }

    /**
     *
     * @return
     */
    public Date getFechaPresentacion() {
        if(fechaPresentacion!=null){
            return (Date) fechaPresentacion.clone();
        }else{
            return null;
        }
    }

    /**
     *
     * @param numOperacion
     */
    public void setNumOperacion(Long numOperacion) {
        this.numOperacion = numOperacion;
    }

    /**
     *
     * @return
     */
    public Long getNumOperacion() {
        return numOperacion;
    }

    /**
     *
     * @param tipoDeclaracion
     */
    public void setTipoDeclaracion(int tipoDeclaracion) {
        this.tipoDeclaracion = tipoDeclaracion;
    }

    /**
     *
     * @return
     */
    public int getTipoDeclaracion() {
        return tipoDeclaracion;
    }

    public void setEsCantidadDetMayor(int esCantidadDetMayor) {
        this.esCantidadDetMayor = esCantidadDetMayor;
    }

    public int getEsCantidadDetMayor() {
        return esCantidadDetMayor;
    }

    public void setNumeroControl(String numeroControl) {
        this.numeroControl = numeroControl;
    }

    public String getNumeroControl() {
        return numeroControl;
    }
}
