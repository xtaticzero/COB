/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.service.afectaciones;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Documento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.MultaDocumento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoMultaEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoMultaEnum;

/**
 *
 * @author root
 */
public interface MultaService {

    /**
     *
     * @param documento
     * @param tipoMultaEnum
     * @throws SGTServiceException
     */
    void generarMulta(Documento documento, TipoMultaEnum tipoMultaEnum) throws SGTServiceException;

    /**
     *
     * @param multaDocumento
     * @throws SGTServiceException
     */
    void cancelarMulta(MultaDocumento multaDocumento) throws SGTServiceException;

    /**
     *
     * @param multaDocumento
     * @return
     * @throws SGTServiceException
     */
    List<MultaDocumento> buscarMultasPorTipoYNumeroControl(MultaDocumento multaDocumento) throws SGTServiceException;

    /**
     *
     * @param numControl
     * @return
     * @throws SGTServiceException
     */
    List<MultaDocumento> buscarMultasPorNumeroControl(String numControl) throws SGTServiceException;

    /**
     *
     * @param numeroResol
     * @param estado
     * @throws SGTServiceException
     */
    void actualizarUltimoEstadoMulta(String numeroResol, EstadoMultaEnum estado) throws SGTServiceException;
}
