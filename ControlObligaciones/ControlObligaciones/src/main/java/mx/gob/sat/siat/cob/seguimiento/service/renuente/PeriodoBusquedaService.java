/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.service.renuente;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.PeriodicidadHelper;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;

/**
 *
 * @author juan
 */
public interface PeriodoBusquedaService {

    /**
     *
     * @param periodoBusqueda
     * @return
     */
    Integer insertPeriodoBusqueda(PeriodicidadHelper periodoBusqueda) throws SGTServiceException;

}
