package mx.gob.sat.siat.cob.seguimiento.dao.stdcob;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ParametrosSeguimiento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.TipoDocEtapa;

/**
 *
 * @author root
 */
public interface TipoDocEtapaDAO {

    /**
     *
     * @param id
     * @return
     * @throws mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException
     */
    List<TipoDocEtapa> consultarTipoDocEtapa(String id) throws SeguimientoDAOException;

    /**
     *
     * @param RFC
     * @return
     * @throws mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException
     */
    Integer buscarEstadoSeguimientoActualPorRfc(String rfc) throws SeguimientoDAOException;

    /**
     *
     * @param enEjecucion
     * @param rfc
     * @throws mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException
     */
    void actualizarRegistroEjecucionSeguimiento(Integer enEjecucion, String rfc) throws SeguimientoDAOException;

    /**
     *
     * @param id
     * @throws mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException
     */
    void actualizarParametrosPorTipoDocumento(TipoDocEtapa id) throws SeguimientoDAOException;

    /**
     *
     * @return
     * @throws mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException
     */
    List<ParametrosSeguimiento> buscarParametrosVigentes() throws SeguimientoDAOException;

    /**
     *
     * @param tipoDoc
     * @param etapaVijilancia
     * @return
     * @throws mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException
     */
    Integer getDiasDeVencimineto(Integer tipoDoc, Integer etapaVijilancia) throws SeguimientoDAOException;
}
