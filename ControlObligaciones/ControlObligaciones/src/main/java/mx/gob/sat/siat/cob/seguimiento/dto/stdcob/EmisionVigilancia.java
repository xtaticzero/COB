package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

public class EmisionVigilancia {

    private int idVigilancia;
    private int idTipoDocumento;
    private int idEtapaVigilancia;
    private int esPlantillaDIOT;
    private int idEstadoEmision;

    /**
     *
     */
    public EmisionVigilancia() {
    }

    /**
     *
     * @param idVigilancia
     * @param idTipoDocumento
     * @param idEtapaVigilancia
     * @param esPlantillaDIOT
     * @param idEstadoEmision
     */
    public EmisionVigilancia(int idVigilancia, int idTipoDocumento, int idEtapaVigilancia, int esPlantillaDIOT,
            int idEstadoEmision) {
        this.idVigilancia = idVigilancia;
        this.idTipoDocumento = idTipoDocumento;
        this.idEtapaVigilancia = idEtapaVigilancia;
        this.esPlantillaDIOT = esPlantillaDIOT;
        this.idEstadoEmision = idEstadoEmision;
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
     * @param idTipoDocumento
     */
    public void setIdTipoDocumento(int idTipoDocumento) {
        this.idTipoDocumento = idTipoDocumento;
    }

    /**
     *
     * @return
     */
    public int getIdTipoDocumento() {
        return idTipoDocumento;
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

    /**
     *
     * @param esPlantillaDIOT
     */
    public void setEsPlantillaDIOT(int esPlantillaDIOT) {
        this.esPlantillaDIOT = esPlantillaDIOT;
    }

    /**
     *
     * @return
     */
    public int getEsPlantillaDIOT() {
        return esPlantillaDIOT;
    }

    /**
     *
     * @param idEstadoEmision
     */
    public void setIdEstadoEmision(int idEstadoEmision) {
        this.idEstadoEmision = idEstadoEmision;
    }

    /**
     *
     * @return
     */
    public int getIdEstadoEmision() {
        return idEstadoEmision;
    }
}
