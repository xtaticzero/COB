package mx.gob.sat.siat.cob.seguimiento.service.extraccion;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.EstadoGenera;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;


public interface EstadoGeneraService {

    /**
     * @param estadoGenera
     * @throws mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException
     */
    void insert(EstadoGenera estadoGenera) throws SGTServiceException;

    /**
     * @return
     * @throws mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException
     */
    List<EstadoGenera> consultaEstadoGenera() throws SGTServiceException;

}
