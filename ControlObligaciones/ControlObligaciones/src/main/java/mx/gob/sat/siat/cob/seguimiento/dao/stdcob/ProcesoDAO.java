package mx.gob.sat.siat.cob.seguimiento.dao.stdcob;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ConsultarProcesosFiltroDto;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Proceso;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoProceso;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;

public interface ProcesoDAO {

    /**
     * obtiene la lista de procesos filtrados por los campos especificados
     *
     * @param filtro
     * @return Lista de procesos
     * @throws mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException
     */
    List<Proceso> consultarProcesos(ConsultarProcesosFiltroDto filtro) throws SeguimientoDAOException;

    /**
     * obtiene la lista de procesos cuyo trigger es el proceso dado
     *
     * @param proceso
     * @return Lista de procesos
     * @throws SeguimientoDAOException
     */
    List<Proceso> consultarProcesosSubsecuentes(Proceso proceso) throws SeguimientoDAOException;

    /**
     * Actualiza el estado de un proceso
     *
     * @param proceso
     * @param idEdoProceso
     * @throws SeguimientoDAOException
     */
    void actualizarEstado(Proceso proceso, EstadoProceso idEdoProceso) throws SeguimientoDAOException;

    /**
     * Actualiza el proceso
     *
     * @param proceso
     * @throws SeguimientoDAOException
     */
    void actualizar(Proceso proceso) throws SeguimientoDAOException;

    /**
     *
     * @param proceso
     * @throws SeguimientoDAOException
     */
    void crear(Proceso proceso) throws SeguimientoDAOException;

    /**
     *
     * @param proceso
     */
    void actualizarInicioEjecucion(Proceso proceso);

    /**
     *
     * @param proceso
     */
    void actualizarFinEjecucion(Proceso proceso);

    /**
     *
     * @param manager
     */
    void actualizarManager(String manager);

    /**
     * Consultar una lista de procesos por id
     *
     * @param ids
     * @return
     * @throws SeguimientoDAOException
     */
    List<Proceso> consultarPorId(List<Integer> ids) throws SeguimientoDAOException;

    /**
     *
     * @param lanzador
     * @return
     * @throws SeguimientoDAOException
     */
    List<Proceso> consultarPorLanzadores(String lanzador) throws SeguimientoDAOException;

    /**
     *
     * @param p
     * @return
     * @throws SeguimientoDAOException
     */
    List<Proceso> consultarPrimerLanzador(Proceso p) throws SeguimientoDAOException;

    /**
     *
     * @throws SeguimientoDAOException
     */
    void iniciarEstados() throws SeguimientoDAOException;

    /**
     *
     * @param idProceso
     * @return
     * @throws SeguimientoDAOException
     */
    boolean isPrimeroEnCadena(Integer idProceso) throws SeguimientoDAOException;
}
