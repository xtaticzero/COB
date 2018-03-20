package mx.gob.sat.siat.cob.seguimiento.service.extraccion;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DetalleObliga;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;


public interface DetalleObligaService {

    /**
     * @param detalleObliga
     * @throws SGTServiceException
     */
    void insert(DetalleObliga detalleObliga) throws SGTServiceException;

    /**
     * @return
     * @throws SGTServiceException
     */
    List<DetalleObliga> consultaDetalleObliga() throws SGTServiceException;

    /**
     * @param numControl
     * @return
     * @throws SGTServiceException
     */
    List<DetalleObliga> detalleObligaXNumControl(String numControl) throws SGTServiceException;
}
