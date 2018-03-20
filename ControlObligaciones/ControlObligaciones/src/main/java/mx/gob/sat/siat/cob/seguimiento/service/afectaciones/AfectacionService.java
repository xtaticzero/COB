/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.service.afectaciones;

import java.math.BigInteger;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dto.arca.RequerimientosAnteriores;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.ComboStr;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.MtvoCancelDoc;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.AfectacionXAutoridad;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ConsultasRenuentes;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DetalleDocumento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Documento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.MultaDocumentoAfectaciones;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ReporteAfectacionXAutoridadDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.AfectacionEnum;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.modelo.dto.SegbMovimientoDTO;


/**
 *
 * @author root
 */
public interface AfectacionService {
    /**
     * Genera un documento a partir de un padre aplicando las reglas de negocio
     * dependiendo del tipo de afectación.
     *
     * @param documentoPadre Documento del cual generaremos un segundo.
     * @param afectacionEnum tipo de afectacion
     * @throws SGTServiceException
     */
    void generaDocumentosAfectacion(Documento documentoPadre, AfectacionEnum afectacionEnum) throws SGTServiceException;

    /**
     * Cancela un documento y avisa a ARCA de la cancelación
     *
     * @param doc Documento a cancelar
     * @throws SGTServiceException
     */
    void cancelarDocumento(Documento doc) throws SGTServiceException;
    
    /**
     * Cancela un documento por autoridad y avisa a ARCA de la cancelacion
     * @param docs
     * @param motivoCancelacion
     * @return true si la transaccin fue exitosa. False en caso contrario
     * @throws SGTServiceException 
     */
    boolean cancelarDocumentoXAutoridad(List<Documento> docs, MtvoCancelDoc motivoCancelacion) throws SGTServiceException;

    /**
     * Avisa a ARCA de la cancelación sin cancelar el documento en COB
     *
     * @param doc Documento a cancelar
     * @throws SGTServiceException
     */
    void cancelarDocumentoARCA(Documento doc) throws SGTServiceException;
/**
     *
     * @param doc con numero de control
     * @return lista de iceps omisos para 1 documento
     * @throws SGTServiceException
     */
    List<DetalleDocumento> consultarICEPsOmisos(Documento doc);
    
    /**
     * Servicio para la busqueda de ICEPS por afectacion dado un numero de control
     * @param numeroControl
     * @return
     * @throws SGTServiceException 
     */
    List<AfectacionXAutoridad> searchDocsAfectacionByNumeroControl(String numeroControl) throws SGTServiceException;
    
    /**
     * Servicio para buscar documentos asociados a un numero de control
     * @param numeroControl
     * @return
     * @throws SGTServiceException 
     */
    List<AfectacionXAutoridad> searchAfectacionXAutoridadByNumeroControl (String numeroControl) throws SGTServiceException;
    
    /**
     * Servicio para buscar documentos asociados a un RFC
     * @param rfc
     * @return
     * @throws SGTServiceException 
     */
    List<AfectacionXAutoridad> searchAfectacionXAutoridadByRFC (String rfc) throws SGTServiceException;
    
    /**
     * Servicio para auditoria
     * @param segMovDto
     * @return
     * @throws SGTServiceException 
     */
    BigInteger registraBitacora(SegbMovimientoDTO segMovDto) throws SGTServiceException;
    
    List<ComboStr> obtenerBoId(String rfc, String tipoPersona) throws SGTServiceException;
    
    List<ConsultasRenuentes> searchAfectacionXAutoridadByBoId(String boId, String tipoPersona) throws SGTServiceException;
    
    List<AfectacionXAutoridad> searchDocsAfectacionByNumeroControl(String numeroControl, String tipoPersona) throws SGTServiceException;
    
    List<MultaDocumentoAfectaciones> obtenerMultasPorNumControl(String numControl) throws SGTServiceException;
   
    List<RequerimientosAnteriores> origenMultaArcaPosteriores(String numControl) throws SGTServiceException;
    
    List<RequerimientosAnteriores> origenMultaArcaAnteriores(String numControl) throws SGTServiceException;
    
    List<ReporteAfectacionXAutoridadDTO> obtenerDatosReporte(String numControl) throws SGTServiceException;
    
    List<MultaDocumentoAfectaciones> obtenerMultasPorNumControlTipoMulta(String numRes, String tipoMulta) throws SGTServiceException;
    
    List<AfectacionXAutoridad> searchDocsAfectacionByNumeroControlCancelacion(String numeroControl) throws SGTServiceException;
    
    List<AfectacionXAutoridad>  searchDocsAfectacionNumControl(String numControl) throws SGTServiceException;
}
