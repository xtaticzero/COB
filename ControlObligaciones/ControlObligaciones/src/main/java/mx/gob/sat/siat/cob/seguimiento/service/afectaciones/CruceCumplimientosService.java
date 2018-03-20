/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.service.afectaciones;

import java.util.List;
import java.util.Map;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.GrupoAfectacionCumpDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoDocumentoEnum;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;

/**
 *
 * @author root
 */
public interface CruceCumplimientosService {
    void cruceAfectacionCumplimientos(GrupoAfectacionCumpDTO grupoAfectacionCumpDTO, List<EstadoDocumentoEnum> estados)throws SGTServiceException;
    void cruceAfectacionCumplimientosComplementaria(GrupoAfectacionCumpDTO grupoAfectacionCumpDTO, Map condiciones, List<Integer> tiposDeclaracion)throws SGTServiceException;
}
