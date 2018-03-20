package mx.gob.sat.siat.cob.seguimiento.dao.stdcob;

import java.util.Date;
import java.util.List;
import java.util.Map;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.DocumentoARCA;

import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.MtvoCancelDoc;
import mx.gob.sat.siat.cob.seguimiento.dto.compartidos.CatalogoBase;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.BitacoraSeguimiento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DetalleDocumento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Documento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.EstadoDocumento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.MultaPendienteDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.TipoDocumento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VigilanciaEntidadFederativa;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoDocumentoEnum;

/**
 *
 * @author root
 */
public interface DocumentoDAO {
    /**
     * @param vef
     * @return
     */
    
    Integer insertarBitacoraDocumentos(VigilanciaEntidadFederativa vef);
    /**
     * @param vef
     * @return
     */
    Integer actualizarEstatusDocumento(VigilanciaEntidadFederativa vef);
    
    /**
     * @param rfc
     * @return
     */
    List<MultaPendienteDTO> obtenerMultasPendientes(String rfc);

    /**
     * @return
     */
    List<Documento> consultaDocumentos();

    /**
     * @param documento
     * @return
     */
    Integer guardaDocumento(Documento documento);

    /**
     * Metodo batchUpdate para guardar una lista de documentos
     *
     * @param documentos
     * @return
     */
    Map<String, String> guardarDocumentos(List<Documento> documentos);

    /**
     * @param numControl
     * @return
     */
    Documento consultaXNumControl(String numControl);

    /**
     * @param numControl
     * @return
     */
    EstadoDocumento consultaEstadoDoctoXNumControl(String numControl);

    /**
     * @param numControl
     * @return
     */
    Integer updateConsideraRenuenciaDocto(String numControl);

    /**
     * Metodo que obtiene los iceps omisos de un documento
     *
     * @param doc necesita el numero de control
     * @return
     */
    List<DetalleDocumento> consultarICEPsOmisos(Documento doc);

    /**
     * Metodo que obtiene los iceps omisos de un conjunto de documentos
     *
     * @param limMenor
     * @param limMayor
     * @param tipoDoc define el idvigilancia, idtipodocumento, si
     * considerarenuencia el doc idetapavigilancia y ultimoestado
     * @return
     */
    List<DetalleDocumento> consultarICEPsOmisosPorLote(int limMenor, int limMayor, Documento tipoDoc);

    /**
     * Metodo que actualiza el estado de un documento
     *
     * @param docs
     * @param estado
     * @throws mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException
     */
    Integer actualizarEstadoDocumento(List<String> docs, EstadoDocumentoEnum estado);

    /**
     * actualiza documentos por id de vigilancia
     *
     * @param idVigilancia
     * @param estadoDestino
     * @return
     */
    Integer actualizarEstadoDocumento(int idVigilancia, EstadoDocumentoEnum estadoDestino);

    /**
     * metodo que actualiza el campo ultimo generado para un lote de documentos
     * de afectacion por tiempo
     *
     * @param limMenor
     * @param limMayor
     * @param tipoDoc
     * @param ultimoGenerado
     * @return
     */
    Integer actualizarDocsUltimoGenerado(List<Documento> documentos, int ultimoGenerado);

    /**
     * Inserta el motivo de cancelacion dado ciertos numeros de control
     *
     * @param numsControl
     * @param motivoCancelacion
     */
    Integer insertaMotivoCancelacion(List<String> numsControl, MtvoCancelDoc motivoCancelacion);

    /**
     * Metodo que actualiza el estado de un conjunto de documentos
     *
     * @param limMenor
     * @param limMayor
     * @param tipoDoc define el idvigilancia, idtipodocumento,
     * idetapavigilancia, si considerarenuencia el doc y ultimoestado
     * @param estado a actualizar para todos los docs
     * @return
     */
    Integer actualizarEstadoDocumentoPorLote(int limMenor, int limMayor, Documento tipoDoc, EstadoDocumentoEnum estado);

    /**
     * Metodo que actualiza a 0 los ultimos estados del doc en bitacora
     * (sgtb_sgtdocumento) e inserta el ultimo estado activo
     *
     * @param documento
     * @param estado a actualizar
     */
    Integer actualizaBitacoraDocumento(Documento documento, EstadoDocumentoEnum estado);

    /**
     * Metodo que inserta el ultimo estado activo para cada documento
     *
     * @param limMenor
     * @param limMayor
     * @param tipoDoc define el idvigilancia, idtipodocumento,
     * idetapavigilancia, si considerarenuencia el doc y ultimoestado
     * @param estado a actualizar
     */
    void insertarEdoDocumentoBitacoraPorLote(int limMenor, int limMayor, Documento tipoDoc, EstadoDocumentoEnum estado);

    /**
     * Metodo que obtiene la lista de numeros de control de documentos
     * actualizados
     *
     * @param limMenor
     * @param limMayor
     * @param tipoDoc define el idvigilancia, idtipodocumento, idetapavigilancia
     * y ultimoestado
     * @return
     */
    List<Documento> consultarDocumentosActualizadosPorLote(int limMenor, int limMayor, Documento tipoDoc);

    /**
     *
     * @param documento
     * @return
     */
    Integer actualizaSgtDocumento(Documento documento);

    /**
     * Metodo que obtiene tipo de documento mediante numero de control
     *
     * @param numControl
     * @return Integer tipoDocumento
     */
    Integer consultaTipoDocumento(String numControl);

    /**
     * Metodo que obtiene documento mediante numero de control
     *
     * @param numeroControl
     * @return Documento
     */
    Documento consultaDocumento(String numeroControl);

    /**
     * Obtiene documentos que tienen un cumplimiento relacionado
     *
     * @param estados
     * @return Lista de Documentos con uno o mas cumplimientos realizados
     */
    List<Documento> documentosAprocesar(Integer[] estados);
    
    List<Documento> documentosAprocesar(Integer[] estados, String idVigilancia, String idLocal);

    /**
     *
     * @param numControl
     * @return
     */
    TipoDocumento getTipoDocumentoXNumControl(String numControl);

    /**
     *
     * @return Regresa lista de documentos que tuvieron baja en el padron
     */
    List<Documento> listarDocumentosConBajaEnPadron();

    /**
     *
     * @return
     */
    List<CatalogoBase> obtenerResultadosDiligenciacion();

    /**
     *
     * @param string
     * @param fecha
     * @param estadoDocumentoEnum
     */
    void actualizarFechaDocumento(String string, Date fecha,Date fechaVencimiento, EstadoDocumentoEnum estadoDocumentoEnum);
    
        /**
     *
     * @param string
     * @param fecha
     * @param estadoDocumentoEnum
     */
    void actualizaSoloFecha(String string, Date fecha,Date fechaVencimiento, EstadoDocumentoEnum estadoDocumentoEnum);

    /**
     *
     * @param numeroControl
     * @return
     */
    List<BitacoraSeguimiento> obtenerRegistrosBitacoraSeguimiento(String numeroControl);

    /**
     *
     * @param documento
     * @return
     */
    Integer numeroDetallesXNumeroControl(DocumentoARCA documento);

    /**
     *
     * @param ultimoEstado
     * @param idVigilancia
     * @param idAlsc
     * @param fechaMonitor
     * @return
     */
    Integer actualizarEstadoDoc(Integer ultimoEstado, Long idVigilancia, String idAlsc, String fechaMonitor);
}
