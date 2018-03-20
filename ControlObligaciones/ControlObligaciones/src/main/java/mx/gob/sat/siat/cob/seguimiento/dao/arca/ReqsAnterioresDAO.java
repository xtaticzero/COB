package mx.gob.sat.siat.cob.seguimiento.dao.arca;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.RequerimientosAnteriores;
import mx.gob.sat.siat.cob.seguimiento.exception.ARCADAOExcepcion;

/**
 *
 * @author Agustin
 */
public interface ReqsAnterioresDAO {

    /**
     *
     * @param reqsAnteriores
     * @return
     */
    Integer guardaReqsAnteriores(List<RequerimientosAnteriores> reqsAnteriores);

    /**
     *
     * @param idVigilancia
     * @param idAlsc
     * @return
     */
    List<RequerimientosAnteriores> consultarRequerimientosPorIdVigilancia(Long idVigilancia, Integer idAlsc) throws ARCADAOExcepcion;

    /**
     *
     * @param idVigilancia
     * @param idAlsc
     * @return
     * @throws ARCADAOExcepcion
     */
    Integer deleteReqsAnteriores(Long idVigilancia, Integer idAlsc) throws ARCADAOExcepcion;

    /**
     *
     * @param idVigilancia
     * @param idAlsc
     * @return
     */
    Integer consultarReqsAnterioresPorIdVigilancia(Long idVigilancia, Integer idAlsc, String fechaenvio) throws ARCADAOExcepcion;

    /**
     *
     * @param idVigilancia
     * @param idAlsc
     * @return
     */
    Integer deleteReqsAnterioresPorIdVigilancia(Long idVigilancia, Integer idAlsc, String fechaMonitor) throws ARCADAOExcepcion;
}
