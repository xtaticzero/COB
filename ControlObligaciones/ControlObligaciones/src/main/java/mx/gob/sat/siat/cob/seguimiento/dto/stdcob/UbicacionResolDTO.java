package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

import java.io.Serializable;

/**
 *
 * @author christian.ventura
 */
public class UbicacionResolDTO implements Serializable {
    
    private String clvALR;
    private String clvCRH;
    private String boid;
    private String numeroControl;

    /**
     * @return the clvALR
     */
    public String getClvALR() {
        return clvALR;
    }

    /**
     * @param clvALR the clvALR to set
     */
    public void setClvALR(String clvALR) {
        this.clvALR = clvALR;
    }

    /**
     * @return the clvCRH
     */
    public String getClvCRH() {
        return clvCRH;
    }

    /**
     * @param clvCRH the clvCRH to set
     */
    public void setClvCRH(String clvCRH) {
        this.clvCRH = clvCRH;
    }

    /**
     * @return the boid
     */
    public String getBoid() {
        return boid;
    }

    /**
     * @param boid the boid to set
     */
    public void setBoid(String boid) {
        this.boid = boid;
    }

    /**
     * @return the numeroControl
     */
    public String getNumeroControl() {
        return numeroControl;
    }

    /**
     * @param numeroControl the numeroControl to set
     */
    public void setNumeroControl(String numeroControl) {
        this.numeroControl = numeroControl;
    }
    
}
