package mx.gob.sat.siat.cob.seguimiento.service.extraccion;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DatoDocumento;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;


public interface DatoDocumentoService {

    /**
     * @param datoDocumento
     * @throws SGTServiceException
     */
    void insert(DatoDocumento datoDocumento) throws SGTServiceException;

    /**
     * @return
     * @throws SGTServiceException
     */
    List<DatoDocumento> consultaDatoDocumento() throws SGTServiceException;


    /**
     *
     * @param idVigilancia
     * @param idEtapaVigilancia
     * @return
     * @throws SGTServiceException
     */
    List<DatoDocumento> consultaDatoDocumento(int idVigilancia, int idEtapaVigilancia) throws SGTServiceException;

    /**
     *
     * @param idEstadoGeneracion
     * @param numeroControl
     * @return
     * @throws SGTServiceException
     */
    int actualizaEstadoDocumento(int idEstadoGeneracion, long numeroControl) throws SGTServiceException;

}
