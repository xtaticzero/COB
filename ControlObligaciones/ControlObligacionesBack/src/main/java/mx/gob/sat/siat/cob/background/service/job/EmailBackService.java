/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.service.job;

import mx.gob.sat.siat.cob.seguimiento.dto.arca.MonitorArchivoArca;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;

/**
 *
 * @author Juan
 */
public interface EmailBackService {

    /**
     * @param asuntoProceso
     * @param monitor
     * @param vigilancia
     */
    void enviarCorreo(String asuntoProceso, MonitorArchivoArca monitor) throws SGTServiceException;
}
