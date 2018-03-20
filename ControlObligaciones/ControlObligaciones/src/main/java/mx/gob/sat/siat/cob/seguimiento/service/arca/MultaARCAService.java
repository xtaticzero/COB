/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.service.arca;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Documento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoMultaEnum;
import mx.gob.sat.siat.cobranza.domain.Resolucion;

/**
 *
 * @author root
 */
public interface MultaARCAService {

    /**
     *
     * @param documento
     * @param resolucion
     * @param constanteResolMotivo
     * @throws SGTServiceException
     */
    void generarMulta(Documento documento, Resolucion resolucion,
            TipoMultaEnum constanteResolMotivo) throws SGTServiceException;

    /**
     * @param documentos
     * @param resoluciones
     * @param constanteResolMotivo
     * @throws SGTServiceException
     */
    void generarMultasMasiva(List<Documento> documentos, List<Resolucion> resoluciones,
            TipoMultaEnum constanteResolMotivo) throws SGTServiceException;
}
