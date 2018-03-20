package mx.gob.sat.siat.cob.seguimiento.dto.arca;

/**
 * Clase Obligacion
 *
 *
 *
 * @author Agustin Romero Mata - Softtek
 */
public class Obligacion {

    private String idObligacion;
    private String constanteResolMotivo;
    private String idPeriodo;
    private String idPeriodicidad;
    private String fechaNotificacion;
    private String motivacion;
    private String idDocumento;
    private String descripcionDeObligacion;
    private String descripcionDePeriodo;
    private int ejercicio;
    private String fundamentoLegal;
    private String fechaDeVencimiento;
    private String sancion;
    private String infraccion;
    private String importe;
    private String numControl;

    /**
     *
     */
    public Obligacion() {
        super();
    }

    /**
     *
     * @param idDocumento
     */
    public void setIdDocumento(String idDocumento) {
        this.idDocumento = idDocumento;
    }

    /**
     *
     * @return
     */
    public String getIdDocumento() {
        return idDocumento;
    }

    /**
     *
     * @param descripcionDeObligacion
     */
    public void setDescripcionDeObligacion(String descripcionDeObligacion) {
        this.descripcionDeObligacion = descripcionDeObligacion;
    }

    /**
     *
     * @return
     */
    public String getDescripcionDeObligacion() {
        return descripcionDeObligacion;
    }

    /**
     *
     * @param descripcionDePeriodo
     */
    public void setDescripcionDePeriodo(String descripcionDePeriodo) {
        this.descripcionDePeriodo = descripcionDePeriodo;
    }

    /**
     *
     * @return
     */
    public String getDescripcionDePeriodo() {
        return descripcionDePeriodo;
    }

    /**
     *
     * @param ejercicio
     */
    public void setEjercicio(int ejercicio) {
        this.ejercicio = ejercicio;
    }

    /**
     *
     * @return
     */
    public int getEjercicio() {
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
     * @param fechaDeVencimiento
     */
    public void setFechaDeVencimiento(String fechaDeVencimiento) {
        this.fechaDeVencimiento = fechaDeVencimiento;
    }

    /**
     *
     * @return
     */
    public String getFechaDeVencimiento() {
        return fechaDeVencimiento;
    }

    /**
     *
     * @param sancion
     */
    public void setSancion(String sancion) {
        this.sancion = sancion;
    }

    /**
     *
     * @return
     */
    public String getSancion() {
        return sancion;
    }

    /**
     *
     * @param infraccion
     */
    public void setInfraccion(String infraccion) {
        this.infraccion = infraccion;
    }

    /**
     *
     * @return
     */
    public String getInfraccion() {
        return infraccion;
    }

    /**
     *
     * @param importe
     */
    public void setImporte(String importe) {
        this.importe = importe;
    }

    /**
     *
     * @return
     */
    public String getImporte() {
        return importe;
    }

    public String getIdObligacion() {
        return idObligacion;
    }

    public void setIdObligacion(String idObligacion) {
        this.idObligacion = idObligacion;
    }

    public String getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(String idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public String getIdPeriodicidad() {
        return idPeriodicidad;
    }

    public void setIdPeriodicidad(String idPeriodicidad) {
        this.idPeriodicidad = idPeriodicidad;
    }

    public String getFechaNotificacion() {
        return fechaNotificacion;
    }

    public void setFechaNotificacion(String fechaNotificacion) {
        this.fechaNotificacion = fechaNotificacion;
    }

    public String getConstanteResolMotivo() {
        return constanteResolMotivo;
    }

    public void setConstanteResolMotivo(String constanteResolMotivo) {
        this.constanteResolMotivo = constanteResolMotivo;
    }

    public String getMotivacion() {
        return motivacion;
    }

    public void setMotivacion(String motivacion) {
        this.motivacion = motivacion;
    }

    public String getNumControl() {
        return numControl;
    }

    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }
}
