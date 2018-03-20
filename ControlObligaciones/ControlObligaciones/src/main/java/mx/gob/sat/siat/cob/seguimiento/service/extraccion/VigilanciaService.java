package mx.gob.sat.siat.cob.seguimiento.service.extraccion;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.EmisionVigilancia;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;

public interface VigilanciaService {

    /**
     * @return @throws SGTServiceException
     */
    List<EmisionVigilancia> conusltaVigilanciaXEtapa() throws SGTServiceException;

    /**
     *
     * @param idvigilancia
     * @return
     * @throws SGTServiceException
     */
    Integer consultaIdTipoFirma(Long idvigilancia) throws SGTServiceException;
}
