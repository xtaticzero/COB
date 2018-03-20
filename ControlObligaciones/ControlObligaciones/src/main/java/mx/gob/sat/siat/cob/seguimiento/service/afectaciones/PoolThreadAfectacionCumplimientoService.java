/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.service.afectaciones;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.GrupoAfectacionCumpDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoDocumentoEnum;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;

/**
 *
 * @author root
 */
public interface PoolThreadAfectacionCumplimientoService {
    /**
     *Cada vez que hay una excepción en algún hilo, esta variable es incrementada
     * para que al final su valor la pueda leer el proceso principal
     */
    public static Integer erroresHilos = 0;
    void ejecutaCruceAfectacionCumplimientos(GrupoAfectacionCumpDTO grupoAfectacionCumpDTO, List<EstadoDocumentoEnum> estados) throws SGTServiceException;
}
