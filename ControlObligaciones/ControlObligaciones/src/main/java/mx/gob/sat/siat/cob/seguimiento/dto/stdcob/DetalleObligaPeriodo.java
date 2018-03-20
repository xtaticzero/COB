package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

public class DetalleObligaPeriodo {

    private DetalleObliga detalleObliga;
    private DetallePeriodos detallePeriodo;

    /**
     *
     */
    public DetalleObligaPeriodo() {
        super();
    }

    /**
     * @param detalleObliga
     * @param detallePeriodo
     */
    public DetalleObligaPeriodo(DetalleObliga detalleObliga, DetallePeriodos detallePeriodo) {
        super();
        this.detalleObliga = detalleObliga;
        this.detallePeriodo = detallePeriodo;
    }

    /**
     * @param detalleObliga
     */
    public void setDetalleObliga(DetalleObliga detalleObliga) {
        this.detalleObliga = detalleObliga;
    }

    /**
     * @return
     */
    public DetalleObliga getDetalleObliga() {
        return detalleObliga;
    }

    /**
     * @param detallePeriodo
     */
    public void setDetallePeriodo(DetallePeriodos detallePeriodo) {
        this.detallePeriodo = detallePeriodo;
    }

    /**
     * @return
     */
    public DetallePeriodos getDetallePeriodo() {
        return detallePeriodo;
    }
}
