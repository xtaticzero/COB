/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

/**
 *
 * @author root
 */
public class MultaAprobarReporteDTO extends MultaAprobar {
    private String crh;
    private String destino;

    public String getCrh() {
        return crh;
    }

    public void setCrh(String crh) {
        this.crh = crh;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    @Override
    public String toString() {
        return "MultaAprobarReporteDTO{" + "crh=" + crh + ", destino=" + destino + '}';
    }
    
    
}
