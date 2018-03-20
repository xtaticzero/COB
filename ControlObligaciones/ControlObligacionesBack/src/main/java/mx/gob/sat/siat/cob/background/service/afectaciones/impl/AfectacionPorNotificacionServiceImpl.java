/*	****************************************************************
 * Nombre de archivo: AfectacionPorNotificacionServiceImpl.java
 * Autores: Emmanuel Estrada Gonzalez 		 	 		
 * Bitácora de modificaciones:
 * CR/Defecto Fecha Autor Descripción del cambio
 * ---------------------------------------------------------------------------
 * N/A 21/10/2013 <Nombre del desarrollador que está modificando el archivo>
 * ---------------------------------------------------------------------------
 */
package mx.gob.sat.siat.cob.background.service.afectaciones.impl;

import mx.gob.sat.siat.cob.seguimiento.br.AfectacionPorNotificacionBR;
import mx.gob.sat.siat.cob.background.service.afectaciones.AfectacionPorNotificacionService;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.DocumentoDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.SGTBRetroARCADAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.SgtDocumentoDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.SeguimientoARCA;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Documento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoDocumentoEnum;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author root
 */
@Service("afectacion-por-notificacion-bean")
public class AfectacionPorNotificacionServiceImpl implements AfectacionPorNotificacionService {

    private final Logger log = Logger.getLogger(AfectacionPorNotificacionServiceImpl.class);
    @Autowired
    private DocumentoDAO documentoDAO;
    @Autowired
    private SgtDocumentoDAO sgtDocumentoDAO;
    @Autowired
    private AfectacionPorNotificacionBR afectacionPorNotificacionBR;
    @Autowired
    private SGTBRetroARCADAO sGTBRetroARCADAO;

    /**
     *
     */
    public AfectacionPorNotificacionServiceImpl() {
        super();
    }

    /**
     *
     * @param seguimientoARCA
     * @throws SGTServiceException
     */
    @Override
    public void actualizaEstadoDocumento(SeguimientoARCA seguimientoARCA) throws SGTServiceException {
        Documento doc = documentoDAO.consultaDocumento(seguimientoARCA.getIdDocumento());
        if (doc != null) {
            int ultimoEstadoActual = doc.getUltimoEstado();
            try {
                doc = afectacionPorNotificacionBR.obtenEdoSigDoc(seguimientoARCA);
                if (ultimoEstadoActual != doc.getUltimoEstado()) {
                    documentoDAO.actualizaSgtDocumento(doc);
                    sgtDocumentoDAO.actualizarBitacoraEdoDocumento(doc);
                    sGTBRetroARCADAO.updateUltimoActualizado(seguimientoARCA.getId());
                }/**
                 * Este caso es cuando el documento se encuentra en un estado
                 * cancelado pero uno de sus valores se va a actualizar y
                 * avanzar el id de sGTBRetroARCA.
                 */
                else if (isCancelado(ultimoEstadoActual, doc)) {
                    documentoDAO.actualizaSgtDocumento(doc);
                    sGTBRetroARCADAO.updateUltimoActualizado(seguimientoARCA.getId());
                }
            } catch (SeguimientoDAOException cISgtEx) {
                log.error("Error en AfectacionPorNotificacionServiceImpl.actualizaEstadoDocumento() :");
                log.error(cISgtEx.getCause());
                throw new SGTServiceException(cISgtEx);
            }
        }
    }

    /**
     * Regresa true si el documento se encuentra en un estado "cancelado" y el
     * valor al que se quiere modificar es "cancelado".
     */
    private boolean isCancelado(int ultimoEstadoActual, Documento doc) {
        return ultimoEstadoActual == EstadoDocumentoEnum.CANCELADO.getValor()
                && doc.getUltimoEstado() == EstadoDocumentoEnum.CANCELADO.getValor();
    }
}
