/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.service.renuente;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ArchivoRenuente;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;

/**
 *
 * @author Alex
 */
public interface ArchivoRenuentesService {

    /**
     *
     * @return @throws SGTServiceException
     */
    Long obtenerIdCargaRenuents() throws SGTServiceException;

    /**
     *
     * @param archivoRenuente
     */
    void agregaArchivoRenuente(ArchivoRenuente archivoRenuente);

    /**
     *
     * @return
     */
    ArchivoRenuente obtenerArchivoRenuente();
}
