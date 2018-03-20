/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.listener.impl;

import java.util.ArrayList;
import java.util.List;
import mx.gob.sat.siat.cob.background.holder.CargaArchivosHolder;
import mx.gob.sat.siat.cob.background.holder.HolderCargaMasivaMap;
import mx.gob.sat.siat.cob.background.listener.DocumentoArcaWriterSkipListener;
import mx.gob.sat.siat.cob.seguimiento.dao.arca.DocumentoARCADAO;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.DocumentoARCA;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoDocumentoEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.SituacionVigilanciaEnum;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.service.afectaciones.BitacoraDocumentoService;
import mx.gob.sat.siat.cob.seguimiento.service.extraccion.DocumentoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author Juan
 */
@Service("documentoArcaWriterSkipListener")
@Scope("step")
public class DocumentoArcaWriterSkipListenerImpl implements DocumentoArcaWriterSkipListener {

    private Logger log = Logger.getLogger(DocumentoArcaWriterSkipListenerImpl.class);
    @Autowired
    private DocumentoService documentoService;
    @Autowired
    private DocumentoARCADAO documentoARCADAO;
    @Autowired
    private BitacoraDocumentoService bitacoraDocumentoService;
    @Autowired
    private HolderCargaMasivaMap holderCargaMasiva;
    private String idVigilanciaAdministracionLocal;
    private Long idVigilancia;

    /**
     * Ocurrio un error a la hora de hacer el insert en ARCA
     */
    @Override
    public void onSkipInWrite(DocumentoARCA docto, Throwable thrwbl) {
        log.error("### ERROR documentoArcaWriterSkipListener : --> " + docto.getId());
        log.error(thrwbl.getMessage());
        try {
            List<DocumentoARCA> doctosFallidos = new ArrayList<DocumentoARCA>();
            DocumentoARCA darca = documentoARCADAO.consultarDocumentoXId(docto.getId());

            if (darca != null) {
                log.error("### Se eliminaran los detalles del documento " + darca.getId());
                documentoARCADAO.eliminarDocumentoEnCascada(darca.getId());
                doctosFallidos.add(docto);
            }
            if (!doctosFallidos.isEmpty()) {
                documentoService.actualizarVigilanciaAdministracionLocal(doctosFallidos,
                        ((CargaArchivosHolder) holderCargaMasiva.getHolderCargaMasiva().
                        get(idVigilancia + idVigilanciaAdministracionLocal)).getVigilanciaAdministracionLocal(),
                        SituacionVigilanciaEnum.ERRONEA);
            }
            regresaUltimoEstadoDocumento(docto.getId());
            bitacoraDocumentoService.registraBitacoraMulta(docto.getId(), EstadoDocumentoEnum.NO_GENERADO.getValor());
        } catch (SGTServiceException ex) {
            log.error(ex);
        }
    }

    @Override
    public void onSkipInProcess(Object t, Throwable thrwbl) {
    }

    @Override
    public void onSkipInRead(Throwable thrwbl) {
    }

    @Value("#{jobParameters['idAdmonLocal']}")
    public void setIdVigilanciaAdministracionLocal(String idVigilanciaAdministracionLocal) {
        this.idVigilanciaAdministracionLocal = idVigilanciaAdministracionLocal;
    }

    @Value("#{jobParameters['idVigilancia']}")
    public void setIdVigilancia(Long idVigilancia) {
        this.idVigilancia = idVigilancia;
    }

    /**
     * Regresa el estado del documento a NO_GENERADO.
     */
    private void regresaUltimoEstadoDocumento(String id) throws SGTServiceException {
        List numerosControl = new ArrayList<String>();
        numerosControl.add(id);
        documentoService.actualizarEstadoDocumento(numerosControl, EstadoDocumentoEnum.NO_GENERADO);
    }
}
