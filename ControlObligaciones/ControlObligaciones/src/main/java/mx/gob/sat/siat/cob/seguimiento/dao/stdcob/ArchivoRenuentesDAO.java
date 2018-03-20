/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.stdcob;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ArchivoRenuente;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;

/**
 *
 * @author Alex
 */
public interface ArchivoRenuentesDAO {

    /**
     *
     * @return @throws SeguimientoDAOException
     */
    Long obtenerIdCargaRenuents() throws SeguimientoDAOException;

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
