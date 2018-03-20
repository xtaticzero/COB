/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.arca;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.OrigenMulta;
import mx.gob.sat.siat.cob.seguimiento.exception.ARCADAOExcepcion;

/**
 *
 * @author Agustin Romero - Softtek
 */
public interface ReqOrigenMultaDAO {

    /**
     *
     * @param numControl
     * @param idDocumento
     * @return
     */
    OrigenMulta origenMultaArca(String numControl, String idDocumento);

    /**
     *
     * @return
     */
    List<OrigenMulta> origenMultaArca();

    /**
     *
     * @param id
     */
    Integer eliminarOrigenMulta(String id);

    /**
     *
     * @param idVigilancia
     * @param idAlsc
     * @return
     */
    Integer consultarOrigenesMultaPorIdVigilancia(Long idVigilancia, Integer idAlsc, String fechaRecepcion) throws ARCADAOExcepcion;

    /**
     *
     * @param idVigilancia
     * @param idAlsc
     * @return
     */
    Integer deleteOrigenesMultaPorIdVigilancia(Long idVigilancia, Integer idAlsc, String fechaMonitor) throws ARCADAOExcepcion;
}