package mx.gob.sat.siat.cob.seguimiento.service.extraccion;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DatoDoctoVigilancia;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;

public interface DatoDoctoVigilanciaService {
    
    /**
     * @return
     * @throws SGTServiceException
     */
    List<DatoDoctoVigilancia> consultaDatosDoctosVigilancia() throws SGTServiceException;
}
