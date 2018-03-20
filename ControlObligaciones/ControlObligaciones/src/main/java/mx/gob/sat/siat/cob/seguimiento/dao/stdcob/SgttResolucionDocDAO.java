package mx.gob.sat.siat.cob.seguimiento.dao.stdcob;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.AdministrativoNivelCargo;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.MultaAprobarGrupo;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoMultaEnum;
import mx.gob.sat.siat.cob.seguimiento.exception.ARCADAOExcepcion;

/**
 *
 * @author christian.ventura
 */
public interface SgttResolucionDocDAO {

    Integer actualizarAdmonLocal(MultaAprobarGrupo multaAprobarGrupo,
            EstadoMultaEnum estadoMultaEnum, AdministrativoNivelCargo administrativoNivelCargo);

    Integer actualizarEstadoResolucion(Integer ultimoEstado, Long idVigilancia, String idAlsc, String fechaMonitor) throws ARCADAOExcepcion;
}
