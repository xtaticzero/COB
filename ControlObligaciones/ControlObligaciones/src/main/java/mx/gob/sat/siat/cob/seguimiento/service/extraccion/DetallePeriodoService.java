package mx.gob.sat.siat.cob.seguimiento.service.extraccion;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DetallePeriodos;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;


public interface DetallePeriodoService {

    /**
     * @param detallePeriodo
     * @throws mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException
     */
    void insert(DetallePeriodos detallePeriodo) throws SGTServiceException;

    /**
     * @return
     * @throws mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException
     */
    List<DetallePeriodos> consultaDetallePeriodos() throws SGTServiceException;


    /**
     * @param numControl
     * @return
     * @throws mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException
     */
    List<DetallePeriodos> detallePeriodosXNumControl(String numControl) throws SGTServiceException;

}
