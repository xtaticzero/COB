package mx.gob.sat.siat.cob.seguimiento.service.arca;

import java.util.List;
import java.util.Set;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.DocumentoARCA;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.ObligacionPeriodo;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.RequerimientosAnteriores;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;

/**
 *
 * @author Juan
 */
public interface DoctoEnvioArcaService {

    /**
     * @param numControl
     * @return
     * @throws SGTServiceException
     */
    List<ObligacionPeriodo> obtenerObligacionPeriodo(String numControl)
            throws SGTServiceException;

    /**
     * @param numControl
     * @return
     * @throws SGTServiceException
     */
    List<RequerimientosAnteriores> obtenerReqsAnteriores(Set<String> numControl)
            throws SGTServiceException;

    /**
     *
     * @param numControl
     * @param idResolucion
     * @return
     * @throws SGTServiceException
     */
    DocumentoARCA multaDoctoArca(String numControl, int idResolucion)
            throws SGTServiceException;

    /**
     *
     * @param numControl
     * @return
     * @throws SGTServiceException
     */
    List<RequerimientosAnteriores> origenMultaArcaAntecesores(String numControl)
            throws SGTServiceException;

    /**
     *
     * @param numControl
     * @return
     * @throws SGTServiceException
     */
    List<RequerimientosAnteriores> origenMultaArcaPosteriores(String numControl)
            throws SGTServiceException;
}
