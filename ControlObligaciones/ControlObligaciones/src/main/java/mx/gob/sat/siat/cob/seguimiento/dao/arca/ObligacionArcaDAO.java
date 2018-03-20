package mx.gob.sat.siat.cob.seguimiento.dao.arca;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.Obligacion;
import mx.gob.sat.siat.cob.seguimiento.exception.ARCADAOExcepcion;

/**
 *
 * @author Juan
 */
public interface ObligacionArcaDAO {

    /**
     *
     * @param obligaciones
     */
    Integer insert(List<Obligacion> obligaciones);

    /**
     * @param idVigilancia
     * @param idAlsc
     * @return
     */
    Integer consultarObligacionesPorIdVigilancia(Long idVigilancia, Integer idAlsc, String fechaRecepcion) throws ARCADAOExcepcion;

    /**
     *
     * @param idVigilancia
     * @param idAlsc
     * @return
     */
    Integer deleteObligacionesPorIdVigilancia(Long idVigilancia, Integer idAlsc, String fechaMonitor) throws ARCADAOExcepcion;
}
