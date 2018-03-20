/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

/**
 *
 * @author juan
 */
public class CargaMotivoRenuente {

    private Long idCargaRenuente;
    private String lineaArchivo;
    private Integer idMotivoNoRenuente;

    public CargaMotivoRenuente() {
    }

    public Long getIdCargaRenuente() {
        return idCargaRenuente;
    }

    public void setIdCargaRenuente(Long idCargaRenuente) {
        this.idCargaRenuente = idCargaRenuente;
    }

    public String getLineaArchivo() {
        return lineaArchivo;
    }

    public void setLineaArchivo(String lineaArchivo) {
        this.lineaArchivo = lineaArchivo;
    }

    public Integer getIdMotivoNoRenuente() {
        return idMotivoNoRenuente;
    }

    public void setIdMotivoNoRenuente(Integer idMotivoNoRenuente) {
        this.idMotivoNoRenuente = idMotivoNoRenuente;
    }

}
