/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dto.arca;

import java.io.Serializable;

/**
 *
 * @author Ulises
 */
public class MonitorArchivoArca implements Serializable {

    private static final long serialVersionUID = -5197691180506547943L;
    private Long idVigilancia;
    private String idAdmonLocal;
    private Integer esEnvioResolucion;
    private Integer cantidadDocumentos;
    private Integer cantidadObligacionPeriodo;
    private Integer cantidadOrigenMulta;
    private Integer cantidadReqAnteriores;
    private String fechaEnvioArca;

    /**
     *
     */
    public MonitorArchivoArca() {
        super();
    }

    public Long getIdVigilancia() {
        return idVigilancia;
    }

    public void setIdVigilancia(Long idVigilancia) {
        this.idVigilancia = idVigilancia;
    }

    public String getIdAdmonLocal() {
        return idAdmonLocal;
    }

    public void setIdAdmonLocal(String idAdmonLocal) {
        this.idAdmonLocal = idAdmonLocal;
    }

    public Integer getEsEnvioResolucion() {
        return esEnvioResolucion;
    }

    public void setEsEnvioResolucion(Integer esEnvioResolucion) {
        this.esEnvioResolucion = esEnvioResolucion;
    }

    public Integer getCantidadDocumentos() {
        return cantidadDocumentos;
    }

    public void setCantidadDocumentos(Integer cantidadDocumentos) {
        this.cantidadDocumentos = cantidadDocumentos;
    }

    public Integer getCantidadObligacionPeriodo() {
        return cantidadObligacionPeriodo;
    }

    public void setCantidadObligacionPeriodo(Integer cantidadObligacionPeriodo) {
        this.cantidadObligacionPeriodo = cantidadObligacionPeriodo;
    }

    public Integer getCantidadOrigenMulta() {
        return cantidadOrigenMulta;
    }

    public void setCantidadOrigenMulta(Integer cantidadOrigenMulta) {
        this.cantidadOrigenMulta = cantidadOrigenMulta;
    }

    public String getFechaEnvioArca() {
        return fechaEnvioArca;
    }

    public void setFechaEnvioArca(String fechaEnvioArca) {
        this.fechaEnvioArca = fechaEnvioArca;
    }

    public Integer getCantidadReqAnteriores() {
        return cantidadReqAnteriores;
    }

    public void setCantidadReqAnteriores(Integer cantidadReqAnteriores) {
        this.cantidadReqAnteriores = cantidadReqAnteriores;
    }
}
