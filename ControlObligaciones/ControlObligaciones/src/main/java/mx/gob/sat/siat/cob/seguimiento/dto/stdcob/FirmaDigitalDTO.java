package mx.gob.sat.siat.cob.seguimiento.dto.stdcob;

/**
 *
 * @author root
 */
public class FirmaDigitalDTO {

    private String cadenaOriginal;
    private String cadenaOriginalGenerada;
    private String firmaDigital;

    /**
     *
     * @return
     */
    public String getCadenaOriginal() {
        return cadenaOriginal;
    }

    /**
     *
     * @param cadenaOriginal
     */
    public void setCadenaOriginal(String cadenaOriginal) {
        this.cadenaOriginal = cadenaOriginal;
    }

    /**
     *
     * @return
     */
    public String getCadenaOriginalGenerada() {
        return cadenaOriginalGenerada;
    }

    /**
     *
     * @param cadenaOriginalGenerada
     */
    public void setCadenaOriginalGenerada(String cadenaOriginalGenerada) {
        this.cadenaOriginalGenerada = cadenaOriginalGenerada;
    }

    /**
     *
     * @return
     */
    public String getFirmaDigital() {
        return firmaDigital;
    }

    /**
     *
     * @param firmaDigital
     */
    public void setFirmaDigital(String firmaDigital) {
        this.firmaDigital = firmaDigital;
    }

    @Override
    public String toString() {
        return "FirmaDigitalDTO{" + "cadenaOriginal=" + cadenaOriginal + ", cadenaOriginalGenerada=" + cadenaOriginalGenerada + ", firmaDigital=" + firmaDigital + '}';
    }
    
}
