package mx.gob.sat.siat.cob.background.service.multa;

import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;

/**
 *
 * @author Juan
 */
public interface GenerarMulta {

    /**
     * Genera las multas, e envia la inofrmacion a ARCA.
     */
    void generarMulta() throws SGTServiceException;
}
