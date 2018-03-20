/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

import java.util.List;

/**
 *
 * @author root
 */
public class FirmaAprobarVigilanciaDTO {
    
    private String vigilancia;
    private String empleadoFirma;
    private List<FirmaDigitalDTO> lista;

    public String getVigilancia() {
        return vigilancia;
    }

    public void setVigilancia(String vigilancia) {
        this.vigilancia = vigilancia;
    }

    public String getEmpleadoFirma() {
        return empleadoFirma;
    }

    public void setEmpleadoFirma(String empleadoFirma) {
        this.empleadoFirma = empleadoFirma;
    }

    public List<FirmaDigitalDTO> getLista() {
        return lista;
    }

    public void setLista(List<FirmaDigitalDTO> lista) {
        this.lista = lista;
    }

    @Override
    public String toString() {
        return "FirmaAprobarVigilanciaDTO{" + "vigilancia=" + vigilancia + ", empleadoFirma=" + empleadoFirma + ", lista=" + lista + '}';
    }
    
}
