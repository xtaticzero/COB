/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.service.afectaciones;

import java.util.Date;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;

/**
 *
 * @author root
 */
public interface CargaCumplimientoService {
    void cargarCumplimientos(Date fechaExtraccion) throws SGTServiceException;
}
