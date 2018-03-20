package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

public class DetalleObliga {

    private String numeroControl;
    private String descripcionObligacion;
    private String descripcionPeriodo;
    private String ejercicio;
    private String fundamentoLegal;
    private String fechaVencimiento;

    /**
     *
     */
    public DetalleObliga() {
    }

    /**
     *
     * @param numeroControl
     * @param descripcionObligacion
     * @param descripcionPeriodo
     * @param ejercicio
     * @param fundamentoLegal
     * @param fechaVencimiento
     */
    public DetalleObliga(String numeroControl, String descripcionObligacion, String descripcionPeriodo,
            String ejercicio, String fundamentoLegal, String fechaVencimiento) {
        super();
        this.numeroControl = numeroControl;
        this.descripcionObligacion = descripcionObligacion;
        this.descripcionPeriodo = descripcionPeriodo;
        this.ejercicio = ejercicio;
        this.fundamentoLegal = fundamentoLegal;
        this.fechaVencimiento = fechaVencimiento;
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
     * @param descripcionObligacion
     */
    public void setDescripcionObligacion(String descripcionObligacion) {
        this.descripcionObligacion = descripcionObligacion;
    }

    /**
     *
     * @return
     */
    public String getDescripcionObligacion() {
        return descripcionObligacion;
    }

    /**
     *
     * @param descripcionPeriodo
     */
    public void setDescripcionPeriodo(String descripcionPeriodo) {
        this.descripcionPeriodo = descripcionPeriodo;
    }

    /**
     *
     * @return
     */
    public String getDescripcionPeriodo() {
        return descripcionPeriodo;
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
}
