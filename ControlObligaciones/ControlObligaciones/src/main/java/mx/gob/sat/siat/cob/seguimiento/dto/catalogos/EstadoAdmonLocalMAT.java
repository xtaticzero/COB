package mx.gob.sat.siat.cob.seguimiento.dto.catalogos;

import java.io.Serializable;

public class EstadoAdmonLocalMAT implements Serializable {

    private static final long serialVersionUID = -5197691180506547942L;
    private String idAdmonLocal;
    private Integer esOperacionMAT;
    private String entidadDesc;
    private String esOperacionMATDesc;
    
    /**
     *
     * @param idAdmonLocal
     */
    public void setIdAdmonLocal(String idAdmonLocal) {
        this.idAdmonLocal = idAdmonLocal;
    }

    /**
     *
     * @return
     */
    public String getIdAdmonLocal() {
        return idAdmonLocal;
    }

    /**
     *
     * @param esOperacionMAT
     */
    public Integer getEsOperacionMAT() {
        return esOperacionMAT;
    }

    /**
     *
     * @return
     */
    public void setEsOperacionMAT(Integer esOperacionMAT) {
        this.esOperacionMAT = esOperacionMAT;
    }

    /**
     *
     * @param entidadDesc
     */
    public void setEntidadDesc(String entidadDesc) {
        this.entidadDesc = entidadDesc;
    }

    /**
     *
     * @return
     */
    public String getEntidadDesc() {
        return entidadDesc;
    }

    public String getEsOperacionMATDesc() {
        return esOperacionMATDesc;
    }

    public void setEsOperacionMATDesc(String esOperacionMATDesc) {
        this.esOperacionMATDesc = esOperacionMATDesc;
    }
    
}