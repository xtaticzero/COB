/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.processor.impl;

import mx.gob.sat.siat.cob.background.processor.DatosReqsAnterioresProcessor;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.RequerimientosAnteriores;
import mx.gob.sat.siat.cob.seguimiento.util.Utilerias;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author Juan
 */
@Service("datosReqsAnterioresProcessor")
@Scope("step")
public class DatosReqsAnterioresProcessorImpl
        implements DatosReqsAnterioresProcessor {

    @Override
    public RequerimientosAnteriores process(RequerimientosAnteriores reqAnterior) {
        if (reqAnterior != null) {
            descripcionesVacias(reqAnterior);
            fechasVacias(reqAnterior);
            if (!Utilerias.isVacio(reqAnterior.getEdoObligacionVencimiento())) {
                reqAnterior.setEdoObligacionVencimiento(null);
            }
            idsVacios(reqAnterior);
        }
        return reqAnterior;
    }

    private void descripcionesVacias(RequerimientosAnteriores reqAnterior) {
        if (!Utilerias.isVacio(reqAnterior.getDescrObligacion())) {
            reqAnterior.setDescrObligacion(null);
        }
        if (!Utilerias.isVacio(reqAnterior.getDescrPeriodo())) {
            reqAnterior.setDescrPeriodo(null);
        }
        if (!Utilerias.isVacio(reqAnterior.getDescrTipoRequ())) {
            reqAnterior.setDescrTipoRequ(null);
        }
    }

    private void fechasVacias(RequerimientosAnteriores reqAnterior) {

        if (!Utilerias.isVacio(reqAnterior.getFechaNotificacion())) {
            reqAnterior.setFechaNotificacion(null);
        }
        if (!Utilerias.isVacio(reqAnterior.getFechaPresentacionObligacion())) {
            reqAnterior.setFechaPresentacionObligacion(null);
        }
        if (!Utilerias.isVacio(reqAnterior.getFechaVencimientoReq())) {
            reqAnterior.setFechaVencimientoReq(null);
        }
    }

    private void idsVacios(RequerimientosAnteriores reqAnterior) {
        if (!Utilerias.isVacio(reqAnterior.getIdDocumento())) {
            reqAnterior.setIdDocumento(null);
        }
        if (!Utilerias.isVacio(reqAnterior.getIdDocumentoPadre())) {
            reqAnterior.setIdDocumentoPadre(null);
        }
        if (!Utilerias.isVacio(reqAnterior.getTipoDocumento())) {
            reqAnterior.setTipoDocumento(null);
        }
    }
}
