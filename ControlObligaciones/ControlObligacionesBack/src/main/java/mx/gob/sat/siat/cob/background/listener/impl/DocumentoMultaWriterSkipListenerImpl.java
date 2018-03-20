/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.listener.impl;

import java.util.Date;
import mx.gob.sat.siat.cob.background.holder.CargaArchivosHolder;
import mx.gob.sat.siat.cob.background.listener.DocumentoMultaWriterSkipListener;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.MultaDocumentoDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.MultaIcepDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.DocumentoARCA;
import mx.gob.sat.siat.cobranza.bajas.api.BajasFacade;
import mx.gob.sat.siat.cobranza.negocio.util.excepcion.FacadeNegocioException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author Daniel Morales
 */
@Service("documentoMultaWriterSkipListener")
@Scope("step")
public class DocumentoMultaWriterSkipListenerImpl implements DocumentoMultaWriterSkipListener {

    private Logger log = Logger.getLogger(DocumentoMultaWriterSkipListenerImpl.class);
    @Autowired
    private CargaArchivosHolder holder;
    @Qualifier("bajasFacade")
    private BajasFacade bajaResolucion;
    @Autowired
    private MultaIcepDAO multaIcepDAO;
    @Autowired
    private MultaDocumentoDAO multaDocumentoDAO;

    @Override
    public void onSkipInRead(Throwable thrwbl) {
    }

    @Override
    public void onSkipInWrite(DocumentoARCA documento, Throwable thrwbl) {
        try {
            log.error("no se pudo procesar documento arca con numero resolucion " + documento.getId());

            log.error(thrwbl);
            multaIcepDAO.borrarMontosResolucionIcep(documento.getId());
            multaDocumentoDAO.borrarMontoTotalMulta(documento.getId());

            holder.getDocumentosARCA().remove(documento);
            bajaResolucion.bajasPorImprocedencia(new Long(documento.getId()), new Date(), 1213L);

        } catch (FacadeNegocioException ex) {
            log.error("No se pudo dar de baja el id Resolucion " + documento.getId());
            log.error(ex);
        }
    }

    @Override
    public void onSkipInProcess(Object t, Throwable thrwbl) {
    }
}
