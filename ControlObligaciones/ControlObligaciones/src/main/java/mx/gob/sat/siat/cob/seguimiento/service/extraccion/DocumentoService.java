package mx.gob.sat.siat.cob.seguimiento.service.extraccion;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.DocumentoARCA;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.MtvoCancelDoc;
import mx.gob.sat.siat.cob.seguimiento.dto.compartidos.CatalogoBase;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Documento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.MultaPendienteDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ResultadoDiligencia;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.TipoDocumento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VigilanciaAdministracionLocal;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoDocumentoEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.OpcionResultadoDiligenciaEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.SituacionVigilanciaEnum;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;

public interface DocumentoService {

    List<MultaPendienteDTO> obtenerMultasPendientes(String rfc) throws SGTServiceException;

    /**
     * @return @throws
     * mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException
     */
    List<Documento> consultaDocumentos() throws SGTServiceException;

    /**
     * Metodo para obtener toda la informacion de un documento dado el numero de
     * control
     *
     * @param numControl
     * @return
     * @throws mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException
     */
    Documento consultaXNumControl(String numControl) throws SGTServiceException;

    void guardaDocumento(Documento documento) throws SGTServiceException;

    /**
     * actualiza estado en SGTT_DOCUMENTO y bitacora en SGTB_SGTDOCUMENTO
     *
     * @param documento
     * @param estado
     * @throws mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException
     */
    boolean actualizaEstadoBitacoraDocumento(Documento documento, EstadoDocumentoEnum estado) throws SGTServiceException;

    /**
     * Metodo para actualizar el estado en SGTT_DOCUMENTO y bitacora en
     * SGTB_SGTDOCUMENTO
     *
     * @param docs
     * @param motivoCancelacion
     * @param estado
     * @throws SGTServiceException
     */
    boolean cancelaDocumentoXAutoridad(List<Documento> docs, MtvoCancelDoc motivoCancelacion, EstadoDocumentoEnum estado) throws SGTServiceException;

    void actualizaSgtDocumento(Documento documento) throws SGTServiceException;

    /**
     *
     * @param docs
     * @param estadoDestino
     * @throws SGTServiceException
     */
    void actualizarEstadoDocumento(List<String> docs, EstadoDocumentoEnum estadoDestino) throws SGTServiceException;

    /**
     * actualiza documentos por id de vigilancia
     *
     * @param idVigilancia
     * @param estadoDestino
     * @return
     * @throws SGTServiceException
     */
    void actualizarEstadoDocumento(int idVigilancia, EstadoDocumentoEnum estadoDestino) throws SGTServiceException;

    /**
     *
     * @param numControl
     * @return
     * @throws SGTServiceException
     */
    TipoDocumento getTipoDocumentoXNumControl(String numControl) throws SGTServiceException;

    /**
     * @param input
     * @param tipoOperacion
     * @param usuario
     * @param isEstadoValido
     * @param formatoFechaIncorrecta
     * @param isCargaPorArchivo
     * @return
     * @throws SGTServiceException
     */
    ResultadoDiligencia actualizarBitacoraDocumento(ResultadoDiligencia input, OpcionResultadoDiligenciaEnum tipoOperacion, String usuario, boolean isCargaPorArchivo, boolean formatoFechaIncorrecta, boolean isEstadoValido) throws SGTServiceException;

    /**
     *
     * @param input
     * @return
     * @throws SGTServiceException
     */
    ResultadoDiligencia modificarBitacoraDocumento(ResultadoDiligencia input) throws SGTServiceException;

    /**
     *
     * @return @throws SGTServiceException
     */
    List<CatalogoBase> obtenerResultadosDiligenciacion() throws SGTServiceException;

    /**
     *
     * @param doctos
     * @param vigilanciaAdministracionLocal
     * @param estado
     * @throws SGTServiceException
     */
    void actualizarVigilanciaAdministracionLocal(List<DocumentoARCA> doctos, VigilanciaAdministracionLocal vigilanciaAdministracionLocal, SituacionVigilanciaEnum estado) throws SGTServiceException;

    /**
     *
     * @param documento
     * @return
     * @throws SGTServiceException
     */
    Integer numeroDetallesXNumeroControl(DocumentoARCA documento) throws SGTServiceException;

    /**
     *
     * @param id
     * @throws SGTServiceException
     */
    void eliminarDocumento(String id) throws SGTServiceException;

    /**
     * registra el cambio de estado del documento en sgtdocumento
     *
     * @param doc
     * @throws SeguimientoDAOException
     */
    void guardaSgtDocumento(Documento doc) throws SeguimientoDAOException;

    /**
     * registra el cambio de estado del documento en sgtdocumento en batch
     *
     * @param docs
     * @throws SeguimientoDAOException
     */
    void guardaSgtDocumentoBatch(List<Documento> docs) throws SeguimientoDAOException;
}
