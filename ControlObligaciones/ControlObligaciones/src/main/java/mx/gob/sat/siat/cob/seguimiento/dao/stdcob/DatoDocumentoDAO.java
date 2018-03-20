package mx.gob.sat.siat.cob.seguimiento.dao.stdcob;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DatoDocumento;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;


/**
 *
 * @author root
 */
public interface DatoDocumentoDAO {

    /**
     * @param datoDocumento
     * @throws SeguimientoDAOException
     */
    void insert(DatoDocumento datoDocumento) throws SeguimientoDAOException;

    /**
     * @return
     * @throws SeguimientoDAOException
     */
    List<DatoDocumento> consultaDatoDocumento() throws SeguimientoDAOException;
    
    /**
     *
     * @param idVigilancia
     * @param idEtapaVigilancia
     * @return
     * @throws SeguimientoDAOException
     */
    List<DatoDocumento> consultaDatoDocumento(int idVigilancia, int idEtapaVigilancia) throws SeguimientoDAOException;
    
    /**
     *
     * @param idEstadoGeneracion
     * @param numeroControl
     * @return
     * @throws SeguimientoDAOException
     */
    Integer actualizaEstadoDocumento(int idEstadoGeneracion, long numeroControl) throws SeguimientoDAOException;    
}
