package mx.gob.sat.siat.cob.seguimiento.dao.stdcob;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VigilanciaAdministracionLocal;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.SituacionVigilanciaEnum;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;

/**
 *
 * @author root
 */
public interface VigilanciaAdministracionLocalDAO {

    /**
     *
     * @param idVigilancia
     * @return
     * @throws SeguimientoDAOException
     */
    int guardaVigAdminLocal(int idVigilancia) throws SeguimientoDAOException;

    /**
     *
     * @param vigilanciaAdministracionLocal
     * @return
     */
    Integer actualizarVigilancias(VigilanciaAdministracionLocal vigilanciaAdministracionLocal);

    /**
     *
     * @return @throws SeguimientoDAOException
     */
    List<VigilanciaAdministracionLocal> obtenerAdministracionLocalXIdVigilanica(Long idVigilancia) throws SeguimientoDAOException;

    /**
     *
     * @param vigilancia
     * @param joinNivelEmision
     * @return
     */
    Integer cuentaRegistrosPorVigilancia(VigilanciaAdministracionLocal vigilancia, String joinNivelEmision);

    /**
     *
     * @param vigilanciaAdministracionLocal
     * @param idSituacionVigilancia
     * @throws SeguimientoDAOException
     */
    void actualizaIdSituacionVigilancia(VigilanciaAdministracionLocal vigilanciaAdministracionLocal, int idSituacionVigilancia) throws SeguimientoDAOException;

    /**
     *
     * @param idVigilancia
     * @param idAdministracionLocal
     * @throws SeguimientoDAOException
     */
    void actualizaFechaVigilancia(Long idVigilancia, String idAdministracionLocal) throws SeguimientoDAOException;

    /**
     *
     * @param numerosControl
     * @return
     */
    List<VigilanciaAdministracionLocal> obtenerIdAdministracionLocal(List<String> numerosControl);

    /**
     *
     * @param vigilanciasAdministracionLocal
     * @param enum1
     * @return
     */
    Integer updateSituacionVigilancia(List<VigilanciaAdministracionLocal> vigilanciasAdministracionLocal, SituacionVigilanciaEnum enum1);

    /**
     *
     * @param numeroCarga
     * @param idAdministracionLocal
     * @return
     */
    Integer updateFechaValidacionCumplimiento(String numeroCarga, String idAdministracionLocal);

    /**
     * Devuelve las vigilancias que ya estan con estado 5 (enviadas a ARCA),
     * pero aun hay documentos pendientes con estado 1(pendiente de envio).
     *
     * @return
     */
    List<VigilanciaAdministracionLocal> obtenerVigilanciasConDocumentos(VigilanciaAdministracionLocal vigilanciaAdminLocal) throws SeguimientoDAOException;

    /**
     *
     * @return @throws SeguimientoDAOException
     */
    VigilanciaAdministracionLocal obtenerVigilanciaMultaBatch() throws SeguimientoDAOException;

    VigilanciaAdministracionLocal obtenerVigilancia(Long idVigilancia, String idAdmonLocal) throws SeguimientoDAOException;

    /**
     * @return @throws SeguimientoDAOException
     */
    Long obtenetNumeroVigilancia() throws SeguimientoDAOException;

    /**
     *
     * @param idVigilancia
     * @return
     * @throws SeguimientoDAOException
     */
    List<VigilanciaAdministracionLocal> obtenerAdministracionLocalMultaXIdVigilanica(Long idVigilancia) throws SeguimientoDAOException;

    /**
     *
     * @param idVigilancia
     * @param idAdmonLocal
     * @return
     * @throws SeguimientoDAOException
     */
    Integer guardaVigAdminLocal(Long idVigilancia, String idAdmonLocal) throws SeguimientoDAOException;

    /**
     *
     * @param idVigilancia
     * @param idAdmonLocal
     * @throws SeguimientoDAOException
     */
    void actualizarFechaUltimoEnvioResol(Long idVigilancia, String idAdmonLocal) throws SeguimientoDAOException;

    /**
     *
     * @param idVigilancia
     * @param idAdmonLocal
     * @throws SeguimientoDAOException
     */
    void actualizarFechaEnvioArca(Long idVigilancia, String idAdmonLocal) throws SeguimientoDAOException;
}
