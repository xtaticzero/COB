package mx.gob.sat.siat.cob.seguimiento.dao.stdcob;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.BuscaRenuentes;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Renuentes;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;

/**
 *
 * @author root
 */
public interface BuscaRenuentesDAO {

    /**
     *
     * @param buscaRenuentes
     * @return
     */
    List<Renuentes> buscaRenuentes(BuscaRenuentes buscaRenuentes);

    /**
     *
     * @return @throws SeguimientoDAOException
     */
    Long obtenerIdBusquedaRenuentes() throws SeguimientoDAOException;

    /**
     *
     * @param buscaRenuentes
     * @return
     */
    Integer insertBuscaRenuentes(BuscaRenuentes buscaRenuentes);

    /**
     *
     * @return
     */
    BuscaRenuentes obtenerArchivoBusquedaRenuente();

    /**
     *
     * @return @throws
     * mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException
     */
    List<BuscaRenuentes> registroSinFinalizar() throws SeguimientoDAOException;
}
