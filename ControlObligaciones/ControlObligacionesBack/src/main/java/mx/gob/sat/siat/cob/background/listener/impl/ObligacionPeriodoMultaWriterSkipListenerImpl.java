/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.listener.impl;

import java.util.Date;
import mx.gob.sat.siat.cob.background.holder.CargaArchivosHolder;
import mx.gob.sat.siat.cob.background.listener.ObligacionPeriodoMultaWriterSkipListener;
import mx.gob.sat.siat.cob.seguimiento.dao.arca.DocumentoARCADAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.MultaDocumentoDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.MultaIcepDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.DocumentoARCA;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.ObligacionPeriodo;
import mx.gob.sat.siat.cobranza.bajas.api.BajasFacade;
import mx.gob.sat.siat.cobranza.negocio.util.excepcion.FacadeNegocioException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author Daniel
 */
@Service("obligacionPeriodoMultaWriterSkipListener")
@Scope("step")
public class ObligacionPeriodoMultaWriterSkipListenerImpl implements ObligacionPeriodoMultaWriterSkipListener {

    private Logger log = Logger.getLogger(ObligacionPeriodoMultaWriterSkipListenerImpl.class);
    @Autowired
    private DocumentoARCADAO documentoARCADAO;
    @Autowired
    private CargaArchivosHolder holder;
    @Autowired
    @Qualifier("bajasFacade")
    private BajasFacade bajaResolucion;
    @Autowired
    private MultaIcepDAO multaIcepDAO;
    @Autowired
    private MultaDocumentoDAO multaDocumentoDAO;

    @Override
    public void onSkipInWrite(ObligacionPeriodo obligacionPeriodo, Throwable thrwbl) {
        log.error("### ERROR ObligacionPeriodoArcaWriterSkipListener : --> " + obligacionPeriodo.getObligacion().getIdDocumento());
        log.error(thrwbl.getMessage());


        DocumentoARCA darca = documentoARCADAO.consultarDocumentoXId(obligacionPeriodo.getObligacion().getIdDocumento());
        if (darca != null) {
            documentoARCADAO.eliminarDocumentoEnCascada(darca.getId());
            log.error("### Documento Asociado : --> " + darca.getId());
            holder.getDocumentosARCA().remove(darca);
            try {
                bajaResolucion.bajasPorImprocedencia(new Long(darca.getId()), new Date(), 1213L);
            } catch (FacadeNegocioException ex) {
                log.error(ex);
            }
            multaIcepDAO.borrarMontosResolucionIcep(darca.getId());
            multaDocumentoDAO.borrarMontoTotalMulta(darca.getId());
        }
    }

    @Override
    public void onSkipInProcess(Object t, Throwable thrwbl) {
    }

    @Override
    public void onSkipInRead(Throwable thrwbl) {
    }
}
