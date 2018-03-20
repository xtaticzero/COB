/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.listener.impl;

import java.util.Date;
import mx.gob.sat.siat.cob.background.holder.CargaArchivosHolder;
import mx.gob.sat.siat.cob.background.listener.RegresaUltimoEstadoMultaCreditoSkipListener;
import mx.gob.sat.siat.cob.seguimiento.dao.arca.DocumentoARCADAO;
import mx.gob.sat.siat.cob.seguimiento.dao.arca.ReqOrigenMultaDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.MultaDocumentoDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.MultaIcepDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.DocumentoARCA;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoMultaEnum;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.service.afectaciones.MultaService;
import mx.gob.sat.siat.cobranza.bajas.api.BajasFacade;
import mx.gob.sat.siat.cobranza.negocio.util.excepcion.FacadeNegocioException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author Daniel
 */
@Service("regresaUltimoEstadoMultaCreditoSkipListener")
@Scope("step")
public class RegresaUltimoEstadoMultaCreditoSkipListenerImpl implements RegresaUltimoEstadoMultaCreditoSkipListener {

    private Logger log = Logger.getLogger(RegresaUltimoEstadoMultaCreditoSkipListenerImpl.class);
    @Autowired
    private MultaService multaService;
    @Autowired
    private MultaIcepDAO multaIcepDAO;
    @Autowired
    private MultaDocumentoDAO multaDocumentoDAO;
    @Autowired
    private CargaArchivosHolder holder;
    @Autowired
    private ReqOrigenMultaDAO origenARCADAO;
    @Autowired
    private DocumentoARCADAO documentoARCADAO;
    @Autowired
    @Qualifier("bajasFacade")
    private BajasFacade bajaResolucion;
    private String esOperacionMat;

    @Override
    public void onSkipInRead(Throwable thrwbl) {
    }

    @Override
    public void onSkipInWrite(DocumentoARCA resolucion, Throwable thrwbl) {
        try {
            log.error("### No se pudo procesar documento multado con numero resolucion " + resolucion.getId());
            log.error(thrwbl);
            /**
             * Regresa los montos anteriormente actualizados, esto para que la
             * siguiente vuelta en que se ejecute el proceso, los vuelva a
             * actualizar y considere los datos.
             */
            multaIcepDAO.borrarMontosResolucionIcep(resolucion.getId());
            multaDocumentoDAO.borrarMontoTotalMulta(resolucion.getId());
            multaService.actualizarUltimoEstadoMulta(resolucion.getId(), EstadoMultaEnum.AUTORIZADA);
            DocumentoARCA darca = documentoARCADAO.consultarDocumentoXId(resolucion.getId());
            if (darca != null) {
                origenARCADAO.eliminarOrigenMulta(darca.getId());
                documentoARCADAO.eliminarDocumentoEnCascada(darca.getId());
                log.error("### Rollback en ARCA terminado para el documento --> " + darca.getId());

            }
            /**
             * Solo se llevara a cabo los siguientes actualizaciones,
             * eliminaciones cuando la local sea del tipo OperacionMAT.
             */
            if (esOperacionMat.equals("1")) {
                try {
                    /**
                     * Se manda a eliminar el registro en cobranza para ese
                     * numero de Resolucion.
                     */
                    log.error("### Elimina registro de Cobranza: " + resolucion.getId());

                    bajaResolucion.bajasPorImprocedencia(Long.valueOf(resolucion.getIdResolucion()), new Date(), 1213L);
                } catch (FacadeNegocioException ex) {
                    log.error(ex);
                }
            }
            holder.getDocumentosARCA().remove(resolucion);
        } catch (SGTServiceException ex) {
            log.error("No se pudo regresar el ultimo estado de la multa a Autorizada");
            log.error(ex);
        }
    }

    @Override
    public void onSkipInProcess(Object t, Throwable thrwbl) {
    }

    @Value("#{jobParameters['esOperacionMat']}")
    public void setEsOperacionMat(String esOperacionMat) {
        this.esOperacionMat = esOperacionMat;
    }
}
