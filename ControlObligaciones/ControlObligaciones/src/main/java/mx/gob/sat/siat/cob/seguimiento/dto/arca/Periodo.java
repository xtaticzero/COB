/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dto.arca;

/**
 *
 * @author Juan
 */
public class Periodo {

    private Long id;
    private String idDocumento;
    private String descripcionPeriodo;
    private int ejercicio;
    private String conceptoImpuesto;

    /**
     *
     */
    public Periodo() {
    }

    /**
     *
     * @param id
     * @param idDocumento
     * @param descripcionPeriodo
     * @param ejercicio
     * @param conceptoImpuesto
     */
    public Periodo(Long id, String idDocumento, String descripcionPeriodo, int ejercicio, String conceptoImpuesto) {
        this.id = id;
        this.idDocumento = idDocumento;
        this.descripcionPeriodo = descripcionPeriodo;
        this.ejercicio = ejercicio;
        this.conceptoImpuesto = conceptoImpuesto;
    }

    /**
     *
     * @return
     */
    public Long getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
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
     * @param idDocumento
     */
    public void setIdDocumento(String idDocumento) {
        this.idDocumento = idDocumento;
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
     * @param descripcionPeriodo
     */
    public void setDescripcionPeriodo(String descripcionPeriodo) {
        this.descripcionPeriodo = descripcionPeriodo;
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
     * @param ejercicio
     */
    public void setEjercicio(int ejercicio) {
        this.ejercicio = ejercicio;
    }

    /**
     *
     * @return
     */
    public String getConceptoImpuesto() {
        return conceptoImpuesto;
    }

    /**
     *
     * @param conceptoImpuesto
     */
    public void setConceptoImpuesto(String conceptoImpuesto) {
        this.conceptoImpuesto = conceptoImpuesto;
    }
}
