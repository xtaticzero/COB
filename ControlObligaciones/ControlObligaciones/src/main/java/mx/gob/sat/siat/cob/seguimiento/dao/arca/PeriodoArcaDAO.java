/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.arca;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.Periodo;
import mx.gob.sat.siat.cob.seguimiento.exception.ARCADAOExcepcion;

/**
 *
 * @author Juan
 */
public interface PeriodoArcaDAO {

    /**
     *
     * @param periodos
     */
    Integer insert(List<Periodo> periodos);

    /**
     *
     * @param idVigilancia
     * @return
     */
    Integer consultarPeriodosPorIdVigilancia(Long idVigilancia, Integer idAlsc, String fechaRecepcion) throws ARCADAOExcepcion;

    /**
     *
     * @param idVigilancia
     * @param idAlsc
     * @return
     */
    Integer deletePeriodosPorIdVigilancia(Long idVigilancia, Integer idAlsc, String fechaMonitor) throws ARCADAOExcepcion;
}
