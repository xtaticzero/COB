/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

/**
 *
 * @author root
 */
public class GrupoAfectacionCumpDTO {
    private String vigilancia;
    private String idAdmonLocal;
    private Integer conteo;

    public String getVigilancia() {
        return vigilancia;
    }

    public void setVigilancia(String vigilancia) {
        this.vigilancia = vigilancia;
    }

    public String getIdAdmonLocal() {
        return idAdmonLocal;
    }

    public void setIdAdmonLocal(String idAdmonLocal) {
        this.idAdmonLocal = idAdmonLocal;
    }

    public Integer getConteo() {
        return conteo;
    }

    public void setConteo(Integer conteo) {
        this.conteo = conteo;
    }

    @Override
    public String toString() {
        return "GrupoAfectacionCumpDTO{" + "vigilancia=" + vigilancia + ", idAdmonLocal=" + idAdmonLocal + ", conteo=" + conteo + '}';
    }

       
}
