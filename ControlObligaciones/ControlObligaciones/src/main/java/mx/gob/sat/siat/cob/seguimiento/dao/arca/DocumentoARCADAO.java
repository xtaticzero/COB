/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.arca;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.DocumentoARCA;
import mx.gob.sat.siat.cob.seguimiento.exception.ARCADAOExcepcion;

/**
 *
 * @author root
 */
public interface DocumentoARCADAO {

    /**
     *
     * @param doctos
     */
    Integer insert(List<DocumentoARCA> doctos);

    /**
     *
     * @param documento
     */
    void update(DocumentoARCA documento);

    /**
     *
     * @param docto
     */
    Integer actualizaEstadoDocto(DocumentoARCA docto);

    /**
     *
     * @param id
     * @return
     */
    Integer cancelaDocto(String id);

    /**
     *
     * @param id
     * @return
     */
    Integer getEstadoDocumento(String id);

    /**
     *
     * @param id
     * @return
     */
    DocumentoARCA consultarDocumentoXId(String id);

    /**
     *
     * @param id
     */
    Integer eliminarDocumentoEnCascada(String id);

    /**
     *
     * @param id
     */
    Integer eliminarRequerimientoAnterior(String id);

    /**
     *
     * @param id
     * @return
     */
    Integer eliminarDocumento(String id);

    Integer cancelarMultaSIR(String numeroResolucion);

    /**
     *
     * @param id
     */
    Integer eliminarDocumentoResolucion(String id);

    /**
     *
     * @param idVigilancia
     * @param idAlsc
     * @return
     */
    Integer consultarDocumentoPorIdVigilancia(Long idVigilancia, Integer idAlsc, String fechaenvio) throws ARCADAOExcepcion;

    /**
     *
     * @param idVigilancia
     * @param idAlsc
     * @return
     */
    Integer deleteDocumentoPorIdVigilancia(Long idVigilancia, Integer idAlsc, String fechaMonitorI) throws ARCADAOExcepcion;

    /**
     *
     * @param idVigilancia
     * @param idAlsc
     * @return
     */
    Integer consultarDocumentoResolucionPorIdVigilancia(Long idVigilancia, Integer idAlsc, String fechaenvio) throws ARCADAOExcepcion;

    /**
     *
     * @param idVigilancia
     * @param idAlsc
     * @return
     */
    Integer deleteDocumentoResolucionPorIdVigilancia(Long idVigilancia, Integer idAlsc, String fehaMonitor) throws ARCADAOExcepcion;
}
