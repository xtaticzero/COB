package mx.gob.sat.siat.cob.seguimiento.service.aprobacion;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.AlscDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ReporteVigilancia;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VigilanciaAl;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;

/**
 *
 * @author root
 */
public interface ConsultarVigilanciaAlService {

    /**
     *
     * @param vigilancia
     * @return
     * @throws SGTServiceException
     */
    VigilanciaAl consultarVigilancia(VigilanciaAl vigilancia) throws SGTServiceException;
    /**
     *
     * @param idVigilancia
     * @return
     * @throws SGTServiceException
     */
    ReporteVigilancia consultaReporteVigilancia(String idVigilancia) throws SGTServiceException;
    /**
     *
     * @param idVigilancia
     * @return
     * @throws SGTServiceException
     */
    List<AlscDTO> consultaAlscXVigilancia(Integer idVigilancia)throws SGTServiceException;
    /**
     *
     * @param idVigilancia
     * @return
     * @throws SGTServiceException
     */
    List<AlscDTO> consultaEFXVigilancia(Integer idVigilancia)throws SGTServiceException;
}
