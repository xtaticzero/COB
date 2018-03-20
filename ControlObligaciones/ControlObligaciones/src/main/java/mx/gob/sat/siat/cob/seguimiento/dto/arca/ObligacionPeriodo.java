/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dto.arca;

/**
 *
 * @author root
 */
public class ObligacionPeriodo {

    private Obligacion obligacion;
    private Periodo periodo;

    /**
     *
     */
    public ObligacionPeriodo() {
    }

    /**
     *
     * @param obligacion
     * @param periodo
     */
    public ObligacionPeriodo(Obligacion obligacion, Periodo periodo) {
        this.obligacion = obligacion;
        this.periodo = periodo;
    }

    /**
     *
     * @return
     */
    public Obligacion getObligacion() {
        return obligacion;
    }

    /**
     *
     * @param obligacion
     */
    public void setObligacion(Obligacion obligacion) {
        this.obligacion = obligacion;
    }

    /**
     *
     * @return
     */
    public Periodo getPeriodo() {
        return periodo;
    }

    /**
     *
     * @param periodo
     */
    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }
}
