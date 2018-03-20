/*	****************************************************************
 * Nombre de archivo: MultaDetalleBean.java
 * Autores: Emmanuel Estrada Gonzalez
 * Bitácora de modificaciones:
 * CR/Defecto Fecha Autor Descripción del cambio
 * ---------------------------------------------------------------------------
 * N/A 10/02/2014 <Nombre del desarrollador que está modificando el archivo>
 * ---------------------------------------------------------------------------
 */
package mx.gob.sat.siat.cob.seguimiento.dto.report;

/**
 *
 * @author root
 */
public class MultaDetalleBean {
    private Long idObligacion;
    private String descObligacion;
    private String monto;
    private String ejercicio;
    private String periodo;

    public Long getIdObligacion() {
        return idObligacion;
    }

    public void setIdObligacion(Long idObligacion) {
        this.idObligacion = idObligacion;
    }

    public String getDescObligacion() {
        return descObligacion;
    }

    public void setDescObligacion(String descObligacion) {
        this.descObligacion = descObligacion;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    /**
     * @return the ejercicio
     */
    public String getEjercicio() {
        return ejercicio;
    }

    /**
     * @param ejercicio the ejercicio to set
     */
    public void setEjercicio(String ejercicio) {
        this.ejercicio = ejercicio;
    }

    /**
     * @return the periodo
     */
    public String getPeriodo() {
        return periodo;
    }

    /**
     * @param periodo the periodo to set
     */
    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

}
