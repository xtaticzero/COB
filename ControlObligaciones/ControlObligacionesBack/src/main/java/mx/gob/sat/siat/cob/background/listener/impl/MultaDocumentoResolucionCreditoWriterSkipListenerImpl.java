/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.listener.impl;

import mx.gob.sat.siat.cob.background.holder.CargaArchivosHolder;
import mx.gob.sat.siat.cob.background.listener.MultaDocumentoResolucionCreditoWriterSkipListener;
import mx.gob.sat.siat.cob.seguimiento.dao.arca.DocumentoARCADAO;
import mx.gob.sat.siat.cob.seguimiento.dao.arca.ReqOrigenMultaDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.MultaDocumentoDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.MultaIcepDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.DocumentoARCA;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoMultaEnum;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.service.afectaciones.MultaService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author Daniel
 */
@Service("multaDocumentoResolucionCreditoWriterSkipListener")
@Scope("step")
public class MultaDocumentoResolucionCreditoWriterSkipListenerImpl implements MultaDocumentoResolucionCreditoWriterSkipListener {

    private Logger log = Logger.getLogger(MultaDocumentoResolucionCreditoWriterSkipListenerImpl.class);
    @Autowired
    private MultaService multaService;
    @Autowired
    private MultaIcepDAO multaIcepDAO;
    @Autowired
    private MultaDocumentoDAO multaDocumentoDAO;
    @Autowired
    private CargaArchivosHolder holder;
    @Autowired
    private DocumentoARCADAO documentoARCADAO;
    @Autowired
    private ReqOrigenMultaDAO origenARCADAO;

    @Override
    public void onSkipInRead(Throwable thrwbl) {
    }

    @Override
    public void onSkipInWrite(DocumentoARCA resolucion, Throwable thrwbl) {
        try {
            log.error("### T_DocumentoResolucion, no se pudo procesar documento multado con numero resolucion " + resolucion.getId());
            log.error(thrwbl);
            multaIcepDAO.borrarMontosResolucionIcep(resolucion.getId());
            multaDocumentoDAO.borrarMontoTotalMulta(resolucion.getId());
            multaService.actualizarUltimoEstadoMulta(resolucion.getId(), EstadoMultaEnum.AUTORIZADA);
            DocumentoARCA darca = documentoARCADAO.consultarDocumentoXId(resolucion.getId());
            if (darca != null) {
                documentoARCADAO.eliminarDocumentoResolucion(darca.getId());
                origenARCADAO.eliminarOrigenMulta(darca.getId());
                documentoARCADAO.eliminarDocumentoEnCascada(darca.getId());
                log.error("### Rollback en ARCA terminado para el documento --> " + darca.getId());
                holder.getDocumentosARCA().remove(darca);
            }
        } catch (SGTServiceException ex) {
            log.error("No se pudo regresar el ultimo estado de la multa a Autorizada");
            log.error(ex);
        }
    }

    @Override
    public void onSkipInProcess(Object t, Throwable thrwbl) {
    }
}
