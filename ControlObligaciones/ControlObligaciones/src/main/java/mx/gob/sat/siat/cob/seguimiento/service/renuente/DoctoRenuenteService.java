package mx.gob.sat.siat.cob.seguimiento.service.renuente;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Documento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.EstadoDocumento;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;

public interface DoctoRenuenteService {

    /**
     * @param numControl
     * @return
     * @throws mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException
     */
    Documento consultaXNumControl(String numControl) throws SGTServiceException;


    /**
     * @param numControl
     * @return
     * @throws mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException
     */
    Boolean updateConsideraRenuenciaDocto(String numControl) throws SGTServiceException;
    
    /**
     * @param numControl
     * @return
     * @throws mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException
     */
    EstadoDocumento consultaEstadoDoctoXNumControl(String numControl) throws SGTServiceException;
}
