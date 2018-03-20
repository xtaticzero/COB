package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

public class DetallePeriodos {

    private String numeroControl;
    private String descripcionPeriodo;
    private String ejercicio;
    private String conceptoImpuesto;

    /**
     *
     */
    public DetallePeriodos() {
        super();
    }

    /**
     *
     * @param numeroControl
     * @param descripcionPeriodo
     * @param ejercicio
     * @param conceptoImpuesto
     */
    public DetallePeriodos(String numeroControl, String descripcionPeriodo, String ejercicio,
            String conceptoImpuesto) {
        super();
        this.numeroControl = numeroControl;
        this.descripcionPeriodo = descripcionPeriodo;
        this.ejercicio = ejercicio;
        this.conceptoImpuesto = conceptoImpuesto;
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
     * @param conceptoImpuesto
     */
    public void setConceptoImpuesto(String conceptoImpuesto) {
        this.conceptoImpuesto = conceptoImpuesto;
    }

    /**
     *
     * @return
     */
    public String getConceptoImpuesto() {
        return conceptoImpuesto;
    }
}
