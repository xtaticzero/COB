package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

import java.io.Serializable;

public class DatoDoctoVigilancia implements Serializable {

    @SuppressWarnings("compatibility:-1169986979954039357")
    private static final long serialVersionUID = 1L;
    private String numeroControl;
    private int idVigilancia;
    private String rfc;
    private String boId;
    private int idEtapaVigilancia;

    /**
     *
     */
    public DatoDoctoVigilancia() {
        super();
    }

    /**
     *
     * @param numeroControl
     * @param idVigilancia
     * @param rfc
     * @param boId
     * @param idEtapaVigilancia
     */
    public DatoDoctoVigilancia(String numeroControl, int idVigilancia, String rfc, String boId,
            int idEtapaVigilancia) {
        super();
        this.numeroControl = numeroControl;
        this.idVigilancia = idVigilancia;
        this.rfc = rfc;
        this.boId = boId;
        this.idEtapaVigilancia = idEtapaVigilancia;
    }

    /**
     *
     * @param numeroControl
     */
    public void setNumeroControl(String numeroControl) {
        this.numeroControl = numeroControl;
    }

    /**
     *
     * @return
     */
    public String getNumeroControl() {
        return numeroControl;
    }

    /**
     *
     * @param idVigilancia
     */
    public void setIdVigilancia(int idVigilancia) {
        this.idVigilancia = idVigilancia;
    }

    /**
     *
     * @return
     */
    public int getIdVigilancia() {
        return idVigilancia;
    }

    /**
     *
     * @param rfc
     */
    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    /**
     *
     * @return
     */
    public String getRfc() {
        return rfc;
    }

    /**
     *
     * @param boId
     */
    public void setBoId(String boId) {
        this.boId = boId;
    }

    /**
     *
     * @return
     */
    public String getBoId() {
        return boId;
    }

    /**
     *
     * @param idEtapaVigilancia
     */
    public void setIdEtapaVigilancia(int idEtapaVigilancia) {
        this.idEtapaVigilancia = idEtapaVigilancia;
    }

    /**
     *
     * @return
     */
    public int getIdEtapaVigilancia() {
        return idEtapaVigilancia;
    }
}
