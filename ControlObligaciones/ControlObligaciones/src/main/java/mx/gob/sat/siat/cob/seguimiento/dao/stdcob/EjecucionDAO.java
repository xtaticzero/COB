package mx.gob.sat.siat.cob.seguimiento.dao.stdcob;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.BitacoraEjecucionDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ConsultarEjecuionFiltroDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.EjecucionDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Proceso;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;


public interface EjecucionDAO {
    List<EjecucionDTO> consultar(ConsultarEjecuionFiltroDTO filtro) throws SeguimientoDAOException;
    List<BitacoraEjecucionDTO> consultarBitacora(ConsultarEjecuionFiltroDTO filtro) throws SeguimientoDAOException;
    EjecucionDTO consultarUltima(Proceso proceso)throws SeguimientoDAOException;
    void insertar(EjecucionDTO ejecucion) throws SeguimientoDAOException;
    void actualizar(EjecucionDTO ejecucion) throws SeguimientoDAOException;
    /**
     * 
     * @return
     * @throws SeguimientoDAOException 
     */
    List<EjecucionDTO> consultarAnteriores() throws SeguimientoDAOException;
    /**
     * 
     * @param ejecucion
     * @return
     * @throws SeguimientoDAOException 
     */
    int insertarEnHistorico(EjecucionDTO ejecucion) throws SeguimientoDAOException;
    /**
     * 
     * @param ejecucion
     * @return
     * @throws SeguimientoDAOException 
     */
    int borrarRegistro(EjecucionDTO ejecucion) throws SeguimientoDAOException;
}
