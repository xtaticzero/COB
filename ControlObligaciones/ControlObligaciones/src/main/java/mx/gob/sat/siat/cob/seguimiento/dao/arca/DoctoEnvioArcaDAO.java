/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.arca;

import java.util.List;
import java.util.Set;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.DocumentoARCA;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.ObligacionPeriodo;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.RequerimientosAnteriores;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DetalleDocumento;

/**
 *
 * @author Juan
 */
public interface DoctoEnvioArcaDAO {

    /**
     *
     * @return
     */
    List<ObligacionPeriodo> obtenerObligacionPeriodo(String numControl);

    /**
     *
     * @return
     */
    List<RequerimientosAnteriores> obtenerReqAnteriores(Set<String> numControles);

    /**
     *
     * @param numControl
     * @param detallesDocumento
     * @return
     */
    List<ObligacionPeriodo> multaObligacionPeriodoArca(String numControl,
            List<DetalleDocumento> detallesDocumento);

    /**
     *
     * @param numControl
     * @param idResolucion
     * @return
     */
    DocumentoARCA multaDoctoArca(String numControl, long idResolucion);

    /**
     *
     * @param numControl
     * @return
     */
    List<RequerimientosAnteriores> origenMultaArca(String numControl);

    /**
     *
     * @param numControl
     * @return
     */
    List<RequerimientosAnteriores> origenMultaArcaAntecesores(String numControl);

    /**
     *
     * @param numControl
     * @return
     */
    List<RequerimientosAnteriores> origenMultaArcaPosteriores(String numControl);
}
