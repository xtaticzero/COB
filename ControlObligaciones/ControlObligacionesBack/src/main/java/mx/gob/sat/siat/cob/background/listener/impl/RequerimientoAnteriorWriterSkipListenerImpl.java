/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.listener.impl;

import java.util.ArrayList;
import java.util.List;
import mx.gob.sat.siat.cob.background.holder.CargaArchivosHolder;
import mx.gob.sat.siat.cob.background.holder.HolderCargaMasivaMap;
import mx.gob.sat.siat.cob.background.listener.RequerimientoAnteriorWriterSkipListener;
import mx.gob.sat.siat.cob.seguimiento.dao.arca.DocumentoARCADAO;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.DocumentoARCA;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.RequerimientosAnteriores;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoDocumentoEnum;
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
@Service("requerimientoAnteriorWriterSkipListener")
@Scope("step")
public class RequerimientoAnteriorWriterSkipListenerImpl implements RequerimientoAnteriorWriterSkipListener {

    private Logger log = Logger.getLogger(ObligacionPeriodoArcaWriterSkipListenerImpl.class);
    @Autowired
    private DocumentoService documentoService;
    @Autowired
    private DocumentoARCADAO documentoARCADAO;
    @Autowired
    private HolderCargaMasivaMap holderCargaMasiva;
    private String idVigilanciaAdministracionLocal;
    private Long idVigilancia;

    public RequerimientoAnteriorWriterSkipListenerImpl() {
        log.info("### Inicia RequerimientoAnteriorWriterSkipListener ... ");
    }

    @Override
    public void onSkipInWrite(RequerimientosAnteriores requerimiento, Throwable thrwbl) {

        try {
            log.error("### ERROR RequerimientoAnteriorWriterSkipListener : --> " + requerimiento.getIdDocumento());
            log.error(thrwbl.getMessage());
            DocumentoARCA darca = documentoARCADAO.consultarDocumentoXId(requerimiento.getIdDocumento());

            List<DocumentoARCA> doctosFallidos = new ArrayList<DocumentoARCA>();
            if (darca != null) {
                documentoARCADAO.eliminarRequerimientoAnterior(darca.getId());
                documentoARCADAO.eliminarDocumentoEnCascada(darca.getId());
                log.error("### Documento Asociado : --> " + darca.getId());

                doctosFallidos.add(darca);
                documentoService.actualizarVigilanciaAdministracionLocal(doctosFallidos,
                        ((CargaArchivosHolder) holderCargaMasiva.getHolderCargaMasiva().get(idVigilancia + idVigilanciaAdministracionLocal)).getVigilanciaAdministracionLocal(),
                        SituacionVigilanciaEnum.ERRONEA);
            } else {
                log.error("### Se elimina por que no hay documento asociado ");
                darca = new DocumentoARCA();
                darca.setId(requerimiento.getIdDocumento());
                darca.setIdAlsc(((CargaArchivosHolder) holderCargaMasiva.getHolderCargaMasiva().get(idVigilancia + idVigilanciaAdministracionLocal)).getVigilanciaAdministracionLocal().getIdAdministracionLocal());
                doctosFallidos.add(darca);
                log.error("### Vigilancia AdministracionLocal enviada como erronea");
                documentoService.actualizarVigilanciaAdministracionLocal(doctosFallidos,
                        ((CargaArchivosHolder) holderCargaMasiva.getHolderCargaMasiva().get(idVigilancia + idVigilanciaAdministracionLocal)).getVigilanciaAdministracionLocal(),
                        SituacionVigilanciaEnum.ERRONEA);
            }
            regresaUltimoEstadoDocumento(requerimiento.getIdDocumento());
        } catch (SGTServiceException ex) {
            log.error(ex);
        }
    }

    @Override
    public void onSkipInRead(Throwable thrwbl) {
    }

    @Override
    public void onSkipInProcess(Object t, Throwable thrwbl) {
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
