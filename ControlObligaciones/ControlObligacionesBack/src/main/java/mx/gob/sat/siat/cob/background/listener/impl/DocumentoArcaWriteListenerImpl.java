/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.listener.impl;

import java.util.ArrayList;
import java.util.List;
import mx.gob.sat.siat.cob.background.holder.CargaArchivosHolder;
import mx.gob.sat.siat.cob.background.holder.HolderCargaMasivaMap;
import mx.gob.sat.siat.cob.background.listener.DocumentoArcaWriteListener;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.DocumentoARCA;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.SituacionVigilanciaEnum;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
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
@Service("documentoArcaWriteListener")
@Scope("step")
public class DocumentoArcaWriteListenerImpl implements DocumentoArcaWriteListener {

    private Logger log = Logger.getLogger(DocumentoArcaWriteListenerImpl.class);
    @Autowired
    private DocumentoService documentoService;
    @Autowired
    private HolderCargaMasivaMap holderCargaMasiva;
    private String idVigilanciaAdministracionLocal;
    private Long idVigilancia;

    @Override
    public void beforeWrite(List<? extends DocumentoARCA> list) {
    }

    @Override
    public void afterWrite(List<? extends DocumentoARCA> doctos) {
        try {
            documentoService.actualizarVigilanciaAdministracionLocal(
                    new ArrayList<DocumentoARCA>(doctos),
                    ((CargaArchivosHolder) holderCargaMasiva.getHolderCargaMasiva().get(idVigilancia + idVigilanciaAdministracionLocal)).getVigilanciaAdministracionLocal(),
                    SituacionVigilanciaEnum.ENVIADA_ARCA);
        } catch (SGTServiceException ex) {
            log.error(ex);
        }
    }

    @Override
    public void onWriteError(Exception excptn, List<? extends DocumentoARCA> doctos) {
    }

    @Value("#{jobParameters['idAdmonLocal']}")
    public void setIdVigilanciaAdministracionLocal(String idVigilanciaAdministracionLocal) {
        this.idVigilanciaAdministracionLocal = idVigilanciaAdministracionLocal;
    }

    @Value("#{jobParameters['idVigilancia']}")
    public void setIdVigilancia(Long idVigilancia) {
        this.idVigilancia = idVigilancia;
    }
}
