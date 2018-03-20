/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.service.aprobacion;

import java.io.InputStream;
import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.db2.Paginador;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.AdministrativoNivelCargo;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.CadenaOriginal;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VisualizaDetalleRenuente;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VisualizaDocumentoRenuente;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VisualizaVigilanciaRenuentes;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.modelo.dto.SegbMovimientoDTO;

/**
 *
 * @author root
 */
public interface AprobarRenuentesService {
    /**
     * Devuelve una lista de vigilancias con estado "Pendiente de procesar" agrupadas
     * por Fecha de emisión, Medio de envío, Tipo de firma, Tipo de documento y Cantidad de documentos
     * @return Lista de vigilancias
     * @throws SGTServiceException por error en ejecución
     */
    List<VisualizaVigilanciaRenuentes> listarVigilanciaRenuentes(AdministrativoNivelCargo administrativoNivelCargo)throws SGTServiceException;
    /**
     * Devuelve lista de documentos tomando como parametros la Fecha de emisión, Medio de envío, 
     * Tipo de firma, Tipo de documento y Cantidad de documentos
     * @param visualizaVigilanciaRenuentes Objeto donde vienen los parametros a filtrar
     * @param paginador Parámetro que sirve para saber cuales documentos devolver
     * @return Lista de documentos
     * @throws SGTServiceException por error en ejecución
     */
    List<VisualizaDocumentoRenuente> listarDocumentosRenuentes(VisualizaVigilanciaRenuentes visualizaVigilanciaRenuentes, Paginador paginador, AdministrativoNivelCargo administrativoNivelCargo) throws SGTServiceException;
    /**
     * Devuelve un paginador que a la postre se usará para obtener el listado de documentos
     * @param visualizaVigilanciaRenuentes Objeto donde vienen los parametros a filtrar
     * @param numeroFilas Número de filas que se desean por página
     * @return paginador
     * @throws SGTServiceException por error en ejecución
     */
    Paginador crearPaginador(VisualizaVigilanciaRenuentes visualizaVigilanciaRenuentes, int numeroFilas, AdministrativoNivelCargo administrativoNivelCargo) throws SGTServiceException;
    /**
     * Devuelve detalles del documento seleccionado
     * @param visualizaDocumentoRenuente Documento del que se quieren ver detalles
     * @return Detalle del documento
     * @throws SGTServiceException por error en ejecución
     */
    List<VisualizaDetalleRenuente> listarDetallesRenuentes(VisualizaDocumentoRenuente visualizaDocumentoRenuente) throws SGTServiceException;
    /**
     * Genera la emisión de los documentos de "Pendiente de procesar" a "No generado"
     * @param visualizaVigilanciaRenuentes Objeto donde vienen los parametros a emitir
     * @param segMovDto Contiene propiedades para poder generar la bitácora
     * @return Documentos realmente emitidos
     * @throws SGTServiceException por error en ejecución
     */
    Integer emitirDocumentos(VisualizaVigilanciaRenuentes visualizaVigilanciaRenuentes, SegbMovimientoDTO segMovDto, AdministrativoNivelCargo administrativoNivelCargo) throws SGTServiceException;
    /**
     * Genera archivo del resumen de la emisión de los documentos
     * @param visualizaVigilanciaRenuentes Parámetros para la generación del archivo
     * @return Stream del archivo
     * @throws SGTServiceException por error en ejecución
     */
    InputStream generarArchivoEmision(VisualizaVigilanciaRenuentes visualizaVigilanciaRenuentes) throws SGTServiceException;
    
     List<CadenaOriginal> listarCadenasOriginales(VisualizaVigilanciaRenuentes visualizaVigilanciaRenuentes, AdministrativoNivelCargo administrativoNivelCargo, 
                                                    Integer rowInicial, Integer rowFinal) throws SGTServiceException;
    
    Integer contarDocumentosAprobar(VisualizaVigilanciaRenuentes visualizaVigilanciaRenuentes, AdministrativoNivelCargo administrativoNivelCargo) throws SGTServiceException;
    
    void eliminaFirmasSinAprobar(VisualizaVigilanciaRenuentes visualizaVigilanciaRenuentes, AdministrativoNivelCargo administrativoNivelCargo) throws SGTServiceException;
    
    String generaFirma(VisualizaVigilanciaRenuentes visualizaVigilanciaRenuentes, AdministrativoNivelCargo administrativo);
}
