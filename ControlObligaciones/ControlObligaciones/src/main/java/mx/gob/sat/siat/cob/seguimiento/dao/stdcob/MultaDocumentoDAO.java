/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.stdcob;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Documento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.MultaDocumento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoMultaEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.ParametroSgtEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoMedioEnvioEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoMultaEnum;

/**
 *
 * @author root
 */
public interface MultaDocumentoDAO {

    /**
     *
     * @param multaDocumento
     * @return
     */
    Integer insert(MultaDocumento multaDocumento);

    /**
     *
     * @param multaDocumento
     * @return
     */
    Integer update(MultaDocumento multaDocumento);

    /**
     *
     * @param multaDocumento
     * @return
     */
    List<MultaDocumento> buscarMultasPorNumControlYTipo(MultaDocumento multaDocumento);

    /**
     *
     * @param numControl
     * @return
     */
    List<MultaDocumento> buscarMultasPorNumControl(String numControl);

    /**
     * @param docs
     * @param tipoMultaEnum
     * @param idTipoMedio
     * @return Metodo que guarda las multas en seguimiento sgtt_resoluciondoc
     */
    Integer generarMultaMasivaSeguimiento(List<Documento> docs, TipoMultaEnum tipoMultaEnum, TipoMedioEnvioEnum idTipoMedio);

    /**
     *
     * @param docs
     * @param tipoMultaEnum
     * @param idTipoMedio
     * @param dias
     * @return
     */
    Integer generarResolucionLiquidacion(List<Documento> docs, TipoMultaEnum tipoMultaEnum, TipoMedioEnvioEnum idTipoMedio, Integer dias);

    /**
     *
     * @param idParametro
     * @return
     */
    Integer obtenerMontoLiquidacion(ParametroSgtEnum idParametro);

    /**
     *
     * @param numeroResolucion
     */
    void borrarMontoTotalMulta(String numeroResolucion);

    /**
     *
     * @param numeroResol
     * @param estado
     */
    void actualizarUltimoEstadoMulta(String numeroResol, EstadoMultaEnum estado);

}
