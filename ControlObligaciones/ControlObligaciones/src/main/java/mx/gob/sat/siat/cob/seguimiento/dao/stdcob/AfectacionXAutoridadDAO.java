/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.stdcob;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dto.arca.RequerimientosAnteriores;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.ComboStr;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.AfectacionXAutoridad;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ConsultasRenuentes;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.MultaDocumentoAfectaciones;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ReporteAfectacionXAutoridadDTO;


public interface AfectacionXAutoridadDAO {
    
    /**
     * Servicio para buscar documentos asociados a un numero de control
     * @param numeroControl
     * @return
     */
    List<AfectacionXAutoridad> searchAfectacionXAutoridadByNumeroControl (String numeroControl);
    
    /**
     * 
     * @param numeroControl
     * @return
     */
    List<AfectacionXAutoridad> searchDocsAfectacionByNumeroControl (String numeroControl);
       
    /**
     * Servicio para buscar documentos asociados a un RFC
     * @param rfc
     * @return
     */
    List<AfectacionXAutoridad> searchAfectacionXAutoridadByRFC (String rfc);
    
    List<ComboStr> obtenerBoId(String rfc, String tipoPersona);
    
    List<ConsultasRenuentes> searchAfectacionXAutoridadByBoId(String boId, String tipoPersona);
        
    List<AfectacionXAutoridad> searchDocsAfectacionByNumeroControl(String numeroControl, String tipoPersona);
    
    List<MultaDocumentoAfectaciones> obtenerMultasPorNumControl(String numControl); 
    
    List<RequerimientosAnteriores> origenMultaArcaPosteriores(String numControl);
    
    List<RequerimientosAnteriores> origenMultaArcaAnteriores(String numControl);
    
    List<ReporteAfectacionXAutoridadDTO> obtenerReporte(String nc);
    
    /**
     * 
     * @param numRes
     * @param tipoMulta
     * @return 
     */
    List<MultaDocumentoAfectaciones> obtenerMultasPorNumControlTipoMulta(String numRes, String tipoMulta); 
    
    List<AfectacionXAutoridad> searchDocsAfectacionByNumeroControlCancelacion (String numeroControl);
    
    List<AfectacionXAutoridad>  searchDocsAfectacionNumControl(String numControl);
}
