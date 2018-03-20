package mx.gob.sat.siat.cob.seguimiento.service.extraccion;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DetalleObligaPeriodo;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;


public interface DetalleObligaPeriodoService {

    /**
     * @return
     * @throws mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException
     */
    List<DetalleObligaPeriodo> buscaDetalleOblicaPeriodos() throws SGTServiceException;

}
