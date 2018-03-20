/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.stdcob;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.PeriodicidadHelper;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;

/**
 *
 * @author juan
 */
public interface PeriodoBusquedaDAO {

    /**
     *
     * @param periodoBusqueda
     * @return
     */
    Integer insertPeriodoBusqueda(PeriodicidadHelper periodoBusqueda);

    /**
     *
     * @param idBusquedaRenuentes
     * @return
     * @throws SeguimientoDAOException
     */
    List<PeriodicidadHelper> selectPorIdBusqueda(Long idBusquedaRenuentes) throws SeguimientoDAOException;
}
