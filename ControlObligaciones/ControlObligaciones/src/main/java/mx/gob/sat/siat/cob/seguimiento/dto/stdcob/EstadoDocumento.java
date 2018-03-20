package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

public class EstadoDocumento {

    private String numeroControl;
    private int idEstadoDocto;
    private String nombreEstado;

    /**
     *
     */
    public EstadoDocumento() {
        super();
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
     * @param idEstadoDocto
     */
    public void setIdEstadoDocto(int idEstadoDocto) {
        this.idEstadoDocto = idEstadoDocto;
    }

    /**
     *
     * @return
     */
    public int getIdEstadoDocto() {
        return idEstadoDocto;
    }

    /**
     *
     * @param nombreEstado
     */
    public void setNombreEstado(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }

    /**
     *
     * @return
     */
    public String getNombreEstado() {
        return nombreEstado;
    }
}
