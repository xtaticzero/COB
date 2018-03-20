package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

public class Renuentes {

    private String numeroControl;
    private String rfc;
    private String estado;
    private String clave;
    private String concepto;
    private String ejercicio;
    private String periodo;
    private String periodicidad;
    private String fechaCumplimiento;
    private String claveIcep;
    private String mes;
    private String regimen;
    private String regimenDesc;

    /**
     *
     */
    public Renuentes() {
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
     * @param rfc
     */
    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    /**
     *
     * @return
     */
    public String getRfc() {
        return rfc;
    }

    /**
     *
     * @param estado
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     *
     * @return
     */
    public String getEstado() {
        return estado;
    }

    /**
     *
     * @param clave
     */
    public void setClave(String clave) {
        this.clave = clave;
    }

    /**
     *
     * @return
     */
    public String getClave() {
        return clave;
    }

    /**
     *
     * @param concepto
     */
    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    /**
     *
     * @return
     */
    public String getConcepto() {
        return concepto;
    }

    /**
     *
     * @param ejercicio
     */
    public void setEjercicio(String ejercicio) {
        this.ejercicio = ejercicio;
    }

    /**
     *
     * @return
     */
    public String getEjercicio() {
        return ejercicio;
    }

    /**
     *
     * @param periodo
     */
    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    /**
     *
     * @return
     */
    public String getPeriodo() {
        return periodo;
    }

    /**
     *
     * @param periodicidad
     */
    public void setPeriodicidad(String periodicidad) {
        this.periodicidad = periodicidad;
    }

    /**
     *
     * @return
     */
    public String getPeriodicidad() {
        return periodicidad;
    }

    /**
     *
     * @param fechaCumplimiento
     */
    public void setFechaCumplimiento(String fechaCumplimiento) {
        this.fechaCumplimiento = fechaCumplimiento;
    }

    /**
     *
     * @return
     */
    public String getFechaCumplimiento() {
        return fechaCumplimiento;
    }

    /**
     *
     * @return
     */
    public String getClaveIcep() {
        return claveIcep;
    }

    /**
     *
     * @param claveIcep
     */
    public void setClaveIcep(String claveIcep) {
        this.claveIcep = claveIcep;
    }

    /**
     *
     * @return
     */
    public String getMes() {
        return mes;
    }

    /**
     *
     * @param mes
     */
    public void setMes(String mes) {
        this.mes = mes;
    }

    public void setRegimen(String regimen) {
        this.regimen = regimen;
    }

    public String getRegimen() {
        return regimen;
    }

    public void setRegimenDesc(String regimenDesc) {
        this.regimenDesc = regimenDesc;
    }

    public String getRegimenDesc() {
        return regimenDesc;
    }
}
