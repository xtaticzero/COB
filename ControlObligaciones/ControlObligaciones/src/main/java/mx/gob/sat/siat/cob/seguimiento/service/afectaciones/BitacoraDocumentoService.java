/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.service.afectaciones;

import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;

/**
 *
 * @author Juan
 */
public interface BitacoraDocumentoService {
    
    void registraBitacoraMulta(String numeroControl, int idEstadoDocto) throws SGTServiceException;
    
}
