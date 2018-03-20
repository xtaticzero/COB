package mx.gob.sat.siat.cob.seguimiento.service.renuente;

import java.math.BigInteger;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.BuscaRenuentes;
import mx.gob.sat.siat.modelo.dto.SegbMovimientoDTO;

public interface BuscaRenuentesService {

    /**
     * Servicio para la busqueda de renuentes
     *
     * @param buscaRenuentes
     * @return
     * @throws SGTServiceException
     */
    String buscaRenuentes(BuscaRenuentes buscaRenuentes) throws SGTServiceException;

    /**
     * Servicio para auditoria
     *
     * @param segMovDto
     * @return
     * @throws SGTServiceException
     */
    BigInteger registraBitacora(SegbMovimientoDTO segMovDto) throws SGTServiceException;

    /**
     *
     * @return @throws SGTServiceException
     */
    Long obtenerIdBusquedaRenuentes() throws SGTServiceException;

    /**
     *
     * @param buscaRenuentes
     * @return
     * @throws SGTServiceException
     */
    Integer insertBuscaRenuentes(BuscaRenuentes buscaRenuentes) throws SGTServiceException;

    /**
     *
     * @return
     */
    BuscaRenuentes obtenerArchivoBusquedaRenuente();

    /**
     * Servicio para envio de correo
     *
     * @param buscaRenuentes
     */
    void enviaCorreo(BuscaRenuentes buscaRenuentes);
}
