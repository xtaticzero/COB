/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.service.renuente;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.compartidos.CatalogoBase;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;

/**
 *
 * @author Marco Murakami
 */
public interface PeriodoXPeriodicidadService {
    
    List<CatalogoBase> catalogoPeriodoXPeriodicidad(String periodicidad) throws SGTServiceException;
    List<CatalogoBase> catalogoTodosLosPeriodos() throws SGTServiceException;
    
}
