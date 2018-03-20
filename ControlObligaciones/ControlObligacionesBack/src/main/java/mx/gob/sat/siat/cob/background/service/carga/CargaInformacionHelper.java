package mx.gob.sat.siat.cob.background.service.carga;

import java.sql.SQLException;
import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ConsultaVigilancia;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Documento;

/**
 *
 * @author christian.ventura
 */
public interface CargaInformacionHelper {

    /**
     *
     * @return
     * @throws SeguimientoDAOException
     */
    boolean validaEjecucion() throws SeguimientoDAOException;

    /**
     *
     * @return
     * @throws SeguimientoDAOException
     */
    List<ConsultaVigilancia> cargaListadoVigilancias() throws SeguimientoDAOException;

    /**
     *
     * @param documento
     * @throws SeguimientoDAOException
     */
    boolean guardarDocumentos(Documento documento);
    
    int guardarDocumentosHilos(List<Documento> documentos) throws SeguimientoDAOException;

    /**
     * 
     * @param idVigilancia
     * @throws SeguimientoDAOException
     */
    void actualizarVigilancia(int idVigilancia) throws SeguimientoDAOException;

    /**
     *
     * @param idVigilancia
     * @throws SeguimientoDAOException
     */
    void guardaVigAdminLocal(int idVigilancia) throws SeguimientoDAOException;

    /**
     *
     * @param vigilancia
     * @throws SQLException
     */
    void envioCorreoPorNivelEmision(ConsultaVigilancia vigilancia)
            throws SQLException;

    /**
     * envia correo al usuario que cargo la vigilancia
     * @param vigilancia
     * @throws SQLException 
     */
    void enviaCorreoErrorXVigilancia(ConsultaVigilancia vigilancia) throws SQLException;
    /**
     * 
     * @param idVigilancia
     * @return
     * @throws SeguimientoDAOException 
     */
    Integer consultaVigilanciasExistente(int idVigilancia) throws SeguimientoDAOException;
    /**
     * 
     * @param idVigilancia
     * @return
     * @throws SeguimientoDAOException 
     */
    Integer consultaVigilanciasExistenteEF(int idVigilancia) throws SeguimientoDAOException;
    
}
