package mx.gob.sat.siat.cob.seguimiento.dao.stdcob;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ConsultarIntentosFiltroDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.EjecucionDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.IntentoDTO;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;


public interface IntentoDAO {
    List<IntentoDTO> consultar(ConsultarIntentosFiltroDTO filtro) throws SeguimientoDAOException;
    IntentoDTO consultarUltimo(EjecucionDTO ejecucion)throws SeguimientoDAOException;
    IntentoDTO consultarPrimer(EjecucionDTO ejecucion)throws SeguimientoDAOException;
    void insertar(IntentoDTO intento) throws SeguimientoDAOException;
    void actualizar(IntentoDTO intento) throws SeguimientoDAOException;
    /**
     * 
     * @param idEjecucion
     * @return
     * @throws SeguimientoDAOException 
     */
    List<IntentoDTO> consultarPorIdEjecucion(Integer idEjecucion) throws SeguimientoDAOException;
    /**
     * 
     * @param intento
     * @return
     * @throws SeguimientoDAOException 
     */
    int insertarEnHistorico(IntentoDTO intento) throws SeguimientoDAOException;
    /**
     * 
     * @param intento
     * @return
     * @throws SeguimientoDAOException 
     */
    int borrarRegistro(IntentoDTO intento) throws SeguimientoDAOException;
}
