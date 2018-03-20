package mx.gob.sat.siat.cob.seguimiento.service.extraccion;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.EmisionVigilancia;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;


public interface EmisionVigilanciaService {

    /**
     * @param emisionVigilancia
     * @throws SGTServiceException
     */
    void insert(EmisionVigilancia emisionVigilancia) throws SGTServiceException;

    /**
     * @return
     * @throws SGTServiceException
     */
    List<EmisionVigilancia> consultaEmisionVigilancia() throws SGTServiceException;

    /**
     * @return
     * @throws SGTServiceException
     */
    List<EmisionVigilancia> emisionVigXEdoEmision() throws SGTServiceException;


}
