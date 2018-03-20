package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

public class TipoDocEtapa {

    private int id;
    private int idEtapaVigilancia;
    private int diasVencimiento;
    private String nombreDocumentoEtapa;

    /**
     *
     */
    public TipoDocEtapa() {
        super();
    }

    /**
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public int getId() {
        return id;
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
     * @param diasVencimiento
     */
    public void setDiasVencimiento(int diasVencimiento) {
        this.diasVencimiento = diasVencimiento;
    }

    /**
     *
     * @return
     */
    public int getDiasVencimiento() {
        return diasVencimiento;
    }

    /**
     *
     * @param nombreDocumentoEtapa
     */
    public void setNombreDocumentoEtapa(String nombreDocumentoEtapa) {
        this.nombreDocumentoEtapa = nombreDocumentoEtapa;
    }

    /**
     *
     * @return
     */
    public String getNombreDocumentoEtapa() {
        return nombreDocumentoEtapa;
    }
}
