/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.stdcob;

import java.util.List;
import java.util.Map;
import mx.gob.sat.siat.cob.seguimiento.dto.db2.Paginador;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Documento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DocumentoAprobar;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.GrupoAfectacionCumpDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoMedioEnvioEnum;

/**
 *
 * @author root
 */
public interface MultaComplementariaDAO {
    /**
     * Método para actualizar los detalles de documento que apliquen para una multa complementaria.
     * @return El número de registros afectados. 
     */
    Integer afectarDetallesConComplementaria(Map condiciones);
    /**
     * Método para obtener una lista de los documentos cuyos detalles apliquen para multa complementaria
     * @return Lista de los documentos cuyos detalles apliquen para multa complementaria
     */
    List<Documento> documentosMultaComplementaria(Integer canceladoAutoridad, TipoMedioEnvioEnum medioEnvioDescartar);
    
    List<Documento> documentosMultaComplementaria(Integer canceladoAutoridad, String idVigilancia, String idLocal);
    
    /**
     * Método para marcar los detalles de documento con la bandera de que
     * ya tienen una multa complementaria generada.
     * @param documentos Lista de documentos a los que sus detalles (los que apliquen)
     *                  seran marcados con multa complementaria.
     * @return El número de registros afectados. 
     */
    Integer marcarIcepsMultaComplementaria(List<Documento> documentos);
    
    Integer actualizarDetalleConComplementaria(Documento documento);
    
    List<GrupoAfectacionCumpDTO> obtenerDetallesComplementariaGrupo(Map condiciones);
    
    List<DocumentoAprobar> listarDocumentosIcep(GrupoAfectacionCumpDTO grupoAfectacionCumpDTO, Paginador paginador, Map condiciones);
}
