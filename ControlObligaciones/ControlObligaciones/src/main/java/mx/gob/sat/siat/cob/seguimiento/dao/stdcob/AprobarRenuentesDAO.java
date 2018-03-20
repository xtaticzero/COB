/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.stdcob;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.db2.Paginador;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.AdministrativoNivelCargo;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.CadenaOriginal;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VisualizaDetalleRenuente;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VisualizaDocumentoRenuente;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VisualizaVigilanciaRenuentes;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoDocumentoEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.SituacionIcepEnum;

/**
 *
 * @author root
 */
public interface AprobarRenuentesDAO {
    /**
     * Devuelve una lista de vigilancias con estado "Pendiente de procesar" agrupadas
     * por Fecha de emisión, Medio de envío, Tipo de firma, Tipo de documento y Cantidad de documentos
     * @return 
     */
    List<VisualizaVigilanciaRenuentes> listarVigilanciaRenuentes(AdministrativoNivelCargo administrativoNivelCargo);
    /**
     * Devuelve lista de documentos tomando como parametros la Fecha de emisión, Medio de envío, 
     * Tipo de firma, Tipo de documento y Cantidad de documentos
     * @param visualizaVigilanciaRenuentes Objeto donde vienen los parametros a filtrar
     * @param paginador Parámetro que sirve para saber cuales documentos devolver
     * @return Lista de documentos
     */
    List<VisualizaDocumentoRenuente> listarDocumentosRenuentes(VisualizaVigilanciaRenuentes visualizaVigilanciaRenuentes, Paginador paginador,
                                                                AdministrativoNivelCargo administrativoNivelCargo);
    /**
     * Devuelve el número total de documentos de un grupo de vigilancias
     * @param visualizaVigilanciaRenuentes Objeto donde vienen los parametros a filtrar
     * @return Número de documentos
     */
    Integer contarDocumentosRenuentes(VisualizaVigilanciaRenuentes visualizaVigilanciaRenuentes, AdministrativoNivelCargo administrativoNivelCargo);
    /**
     * Devuelve detalles del documento seleccionado
     * @param visualizaDocumentoRenuente Documento del que se quieren ver detalles
     * @param situacionIcepEnum Situacion icep que deben tener el documento
     * @return Detalle del documento
     */
    List<VisualizaDetalleRenuente> listarDetallesRenuentes(VisualizaDocumentoRenuente visualizaDocumentoRenuente, SituacionIcepEnum situacionIcepEnum);
    /**
     * Genera la emisión de los documentos de "Pendiente de procesar" a "No generado"
     * @param visualizaVigilanciaRenuentes Objeto donde vienen los parametros a emitir
     * @param estadoOrigen Estado origen de los documentos a emitir
     * @param estadoDestino Estado al que se que quiere cambiar los documentos
     * @return Documentos que lograron ser emitidos
     */
    Integer emitirDocumentos(VisualizaVigilanciaRenuentes visualizaVigilanciaRenuentes, EstadoDocumentoEnum estadoOrigen, EstadoDocumentoEnum estadoDestino,
                                AdministrativoNivelCargo administrativoNivelCargo);
    /**
     * Genera los registros de bitácora de cambio de estado de documento
     * @param visualizaVigilanciaRenuentes bjeto donde vienen los parametros de los docoumentos a elegir
     * @param estadoOrigen Estado origen de los documentos a emitir
     * @param estadoDestino Estado al que se que quiere cambiar los documentos
     * @return Número de registros generados
     */
    Integer bitacoraCambioDocumentos(VisualizaVigilanciaRenuentes visualizaVigilanciaRenuentes, EstadoDocumentoEnum estadoOrigen,
                                    EstadoDocumentoEnum estadoDestino, AdministrativoNivelCargo administrativoNivelCargo);
    
     List<CadenaOriginal> listarCadenasOriginales(VisualizaVigilanciaRenuentes visualizaVigilanciaRenuentes, AdministrativoNivelCargo administrativoNivelCargo, String nombreFuncionario, 
                                                    Integer rowInicial, Integer rowFinal);
    
    
    Integer eliminaFirmasSinAprobar(VisualizaVigilanciaRenuentes visualizaVigilanciaRenuentes, AdministrativoNivelCargo administrativoNivelCargo);
    /**
     * Cambia el estado de las vigilancias que ya se hayan mandado a ARCA cuando un documento renuente de éstas se haya emitido
     * @param visualizaVigilanciaRenuentes
     * @param estadoOrigen
     * @param estadoDestino
     * @param administrativoNivelCargo
     * @return 
     */
    Integer cambiaEstadoVigilancias(VisualizaVigilanciaRenuentes visualizaVigilanciaRenuentes, EstadoDocumentoEnum estadoOrigen, EstadoDocumentoEnum estadoDestino,
                                AdministrativoNivelCargo administrativoNivelCargo);
    
    Integer bitacoraEmisionFuncionario(VisualizaVigilanciaRenuentes visualizaVigilanciaRenuentes, EstadoDocumentoEnum estadoOrigen,
                                    EstadoDocumentoEnum estadoDestino, AdministrativoNivelCargo administrativoNivelCargo);
    
}