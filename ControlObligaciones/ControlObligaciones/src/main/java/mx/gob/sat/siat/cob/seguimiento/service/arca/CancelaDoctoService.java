package mx.gob.sat.siat.cob.seguimiento.service.arca;

import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;

public interface CancelaDoctoService {
     /**
     *
     * @param id
     * @throws SGTServiceException
     */
    void cancelaDoctoArca(String id) throws SGTServiceException;

    /**
     *
     * @param numeroResolucion
     * @throws SGTServiceException
     */
    void cancelarMultaSIR(String numeroResolucion) throws SGTServiceException;
}
