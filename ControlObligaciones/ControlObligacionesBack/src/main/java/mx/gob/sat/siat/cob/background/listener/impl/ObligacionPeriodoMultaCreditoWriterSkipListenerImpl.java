/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.listener.impl;

import mx.gob.sat.siat.cob.background.holder.CargaArchivosHolder;
import mx.gob.sat.siat.cob.background.listener.ObligacionPeriodoMultaCreditoWriterSkipListener;
import mx.gob.sat.siat.cob.seguimiento.dao.arca.DocumentoARCADAO;
import mx.gob.sat.siat.cob.seguimiento.dao.arca.ReqOrigenMultaDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.MultaDocumentoDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.MultaIcepDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.DocumentoARCA;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.ObligacionPeriodo;
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
@Service("obligacionPeriodoMultaCreditoWriterSkipListener")
@Scope("step")
public class ObligacionPeriodoMultaCreditoWriterSkipListenerImpl implements ObligacionPeriodoMultaCreditoWriterSkipListener {

    private Logger log = Logger.getLogger(ObligacionPeriodoMultaCreditoWriterSkipListenerImpl.class);
    @Autowired
    private DocumentoARCADAO documentoARCADAO;
    @Autowired
    private CargaArchivosHolder holder;
    @Autowired
    private MultaService multaService;
    @Autowired
    private MultaIcepDAO multaIcepDAO;
    @Autowired
    private MultaDocumentoDAO multaDocumentoDAO;
    @Autowired
    private ReqOrigenMultaDAO origenARCADAO;

    @Override
    public void onSkipInRead(Throwable thrwbl) {
    }

    @Override
    public void onSkipInWrite(ObligacionPeriodo resolucion, Throwable thrwbl) {

        try {
            log.error("### T_Obligacion y T_Periodo, no se pudo procesar documento multado con numero resolucion " + resolucion.getObligacion().getIdDocumento());
            log.error(thrwbl);
            multaIcepDAO.borrarMontosResolucionIcep(resolucion.getObligacion().getIdDocumento());
            multaDocumentoDAO.borrarMontoTotalMulta(resolucion.getObligacion().getIdDocumento());
            multaService.actualizarUltimoEstadoMulta(resolucion.getObligacion().getIdDocumento(), EstadoMultaEnum.AUTORIZADA);
            DocumentoARCA darca = documentoARCADAO.consultarDocumentoXId(resolucion.getObligacion().getIdDocumento());
            if (darca != null) {
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
