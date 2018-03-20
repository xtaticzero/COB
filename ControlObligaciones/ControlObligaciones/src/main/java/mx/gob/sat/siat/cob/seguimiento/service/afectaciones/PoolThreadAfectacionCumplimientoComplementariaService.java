/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.service.afectaciones;

import java.util.List;
import java.util.Map;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.GrupoAfectacionCumpDTO;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;

/**
 *
 * @author root
 */
public interface PoolThreadAfectacionCumplimientoComplementariaService {

    /**
     *Cada vez que hay una excepción en algún hilo, esta variable es incrementada
     * para que al final su valor la pueda leer el proceso principal
     */    
    void ejecutaCruceAfectacionCumplimientosComplementaria(GrupoAfectacionCumpDTO grupoAfectacionCumpDTO, Map condiciones, List<Integer> tiposDeclaracion) throws SGTServiceException;
}
