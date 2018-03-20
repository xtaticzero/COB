package mx.gob.sat.siat.cob.background.service.multa;

import mx.gob.sat.siat.cob.seguimiento.dto.arca.MonitorArchivoArca;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;

/**
 *
 * @author Juan
 */
public interface ValidaDatosService {

    /**
     * @param monitorArca
     * @throws SGTServiceException
     */
    void eliminaDatosARCA(MonitorArchivoArca monitorArca) throws SGTServiceException;
}
