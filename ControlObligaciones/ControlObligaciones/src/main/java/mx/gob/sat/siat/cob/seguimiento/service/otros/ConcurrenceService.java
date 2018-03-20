/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.service.otros;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ElementoConcurrente;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoServiciosConcurrentesEnum;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.modelo.dto.SegbMovimientoDTO;

/**
 *
 * @author emmanuel
 */
public interface ConcurrenceService {
    boolean lockServices(TipoServiciosConcurrentesEnum idServicio,String firmaProceso)throws SGTServiceException;
    boolean unlockServices(TipoServiciosConcurrentesEnum idServicio,String firmaProceso);
    boolean isLockedServices(TipoServiciosConcurrentesEnum idServicio,String firmaProceso);
    void desbloqueoManual(List<ElementoConcurrente> elementosConcurrentes, SegbMovimientoDTO dto)throws SGTServiceException;
    List<ElementoConcurrente> obtenerElementosBloqueados()throws SGTServiceException;
}
