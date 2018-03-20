package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

public class DatoDoctoALSC {

    private String domicilioAlsc;
    private String fraccionAlsc;
    private String localidadAlsc;
    private String nombreAlsc;
    private String nombreAdminAlsc;
    private String nombreContribuyenteAlsc;

    /**
     *
     */
    public DatoDoctoALSC() {
        super();
    }

    /**
     *
     * @param domicilioAlsc
     * @param fraccionAlsc
     * @param localidadAlsc
     * @param nombreAlsc
     * @param nombreAdminAlsc
     * @param nombreContribuyenteAlsc
     */
    public DatoDoctoALSC(String domicilioAlsc, String fraccionAlsc, String localidadAlsc, String nombreAlsc,
            String nombreAdminAlsc, String nombreContribuyenteAlsc) {
        super();
        this.domicilioAlsc = domicilioAlsc;
        this.fraccionAlsc = fraccionAlsc;
        this.localidadAlsc = localidadAlsc;
        this.nombreAlsc = nombreAlsc;
        this.nombreAdminAlsc = nombreAdminAlsc;
        this.nombreContribuyenteAlsc = nombreContribuyenteAlsc;
    }

    /**
     *
     * @param domicilioALSC
     */
    public void setDomicilioAlsc(String domicilioAlsc) {
        this.domicilioAlsc = domicilioAlsc;
    }

    /**
     *
     * @return
     */
    public String getDomicilioAlsc() {
        return domicilioAlsc;
    }

    /**
     *
     * @param fraccionAlsc
     */
    public void setFraccionAlsc(String fraccionAlsc) {
        this.fraccionAlsc = fraccionAlsc;
    }

    /**
     *
     * @return
     */
    public String getFraccionAlsc() {
        return fraccionAlsc;
    }

    /**
     *
     * @param localidadALSC
     */
    public void setLocalidadAlsc(String localidadAlsc) {
        this.localidadAlsc = localidadAlsc;
    }

    /**
     *
     * @return
     */
    public String getLocalidadAlsc() {
        return localidadAlsc;
    }

    /**
     *
     * @param nombreALSC
     */
    public void setNombreAlsc(String nombreAlsc) {
        this.nombreAlsc = nombreAlsc;
    }

    /**
     *
     * @return
     */
    public String getNombreAlsc() {
        return nombreAlsc;
    }

    /**
     *
     * @param nombreAdminALSC
     */
    public void setNombreAdminAlsc(String nombreAdminAlsc) {
        this.nombreAdminAlsc = nombreAdminAlsc;
    }

    /**
     *
     * @return
     */
    public String getNombreAdminAlsc() {
        return nombreAdminAlsc;
    }

    /**
     *
     * @param nombreContribuyenteAlsc
     */
    public void setNombreContribuyenteAlsc(String nombreContribuyenteAlsc) {
        this.nombreContribuyenteAlsc = nombreContribuyenteAlsc;
    }

    /**
     *
     * @return
     */
    public String getNombreContribuyenteAlsc() {
        return nombreContribuyenteAlsc;
    }
}
