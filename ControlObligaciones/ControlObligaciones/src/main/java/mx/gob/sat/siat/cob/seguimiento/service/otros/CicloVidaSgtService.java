package mx.gob.sat.siat.cob.seguimiento.service.otros;

/**
 *
 * @author root
 */
public interface CicloVidaSgtService {

    /**
     *
     * @param idEtapaActual
     * @param idTipoDocumento
     * @param idEtapaRequerida
     * @return
     */
    boolean validaCambioEtapa(Integer idEtapaActual,
            Integer idTipoDocumento,
            Integer idEtapaRequerida);

}
