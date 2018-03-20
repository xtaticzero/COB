package mx.gob.sat.siat.cob.seguimiento.service.jobmanager;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Proceso;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ProcesoDetalle;
import mx.gob.sat.siat.cob.seguimiento.exception.BusinessException;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;

public interface ProcesoDetalleService {

    /**
     *
     * @param idProceso
     * @return
     * @throws SGTServiceException
     */
    ProcesoDetalle consultarProcesoDetalle(Integer idProceso) throws SGTServiceException;

    /**
     *
     * @param procesoDetalle
     * @throws BusinessException
     * @throws SGTServiceException
     */
    void guardar(ProcesoDetalle procesoDetalle) throws BusinessException, SGTServiceException;

    /**
     *
     * @param actuales
     * @return
     * @throws SGTServiceException
     */
    List<Proceso> consultarPorAgregar(List<Proceso> actuales) throws SGTServiceException;

    /**
     *
     * @param proceso
     * @return
     * @throws SGTServiceException
     */
    List<Proceso> cadenaLanzadores(Proceso proceso) throws SGTServiceException;

    /**
     *
     * @param proceso
     * @return
     * @throws SGTServiceException
     */
    List<Proceso> procesosHijos(Proceso proceso) throws SGTServiceException;

    /**
     *
     * @param proceso
     * @return
     * @throws SGTServiceException
     */
    List<Proceso> procesosPadres(Proceso proceso) throws SGTServiceException;

    /**
     *
     * @param proceso
     * @return
     * @throws SGTServiceException
     */
    boolean isUltimoEnCadena(Proceso proceso) throws SGTServiceException;

    /**
     *
     * @param proceso
     * @return
     * @throws SGTServiceException
     */
    boolean isPrimeroEnCadena(Proceso proceso) throws SGTServiceException;

    /**
     *
     * @param lanzador
     * @return
     * @throws SGTServiceException
     */
    List<Proceso> consultarPorId(String lanzador) throws SGTServiceException;

    /**
     *
     * @param lanzador
     * @return
     * @throws SGTServiceException
     */
    List<Proceso> consultarPorLanzadores(String lanzador) throws SGTServiceException;
}
