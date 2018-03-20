/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.service.afectaciones;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Documento;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;

/**
 *
 * @author root
 */
public interface CambiaEstadosCumplimientoService {
    void procesarCumplidoSinNotificacionTotal(Documento documento) throws SGTServiceException;
    void procesarCumplidoSinNotificacionParcial(Documento documento) throws SGTServiceException;
    
    void procesarCumplidoAntesDeNotificacionTotal(Documento documento, Documento documentoOriginal) throws SGTServiceException;
    void procesarCumplidoAntesDeNotificacionParcial(Documento documento, Documento documentoOriginal) throws SGTServiceException;
    void procesarCumplidoDentroDelPlazoDeNotificacionTotal(Documento documento, Documento documentoOriginal) throws SGTServiceException;
    void procesarCumplidoDentroDelPlazoDeNotificacionParcial(Documento documento, Documento documentoOriginal) throws SGTServiceException;
    
    void procesarCumplidoFueraDelPlazoDeNotificacionTotal(Documento documento) throws SGTServiceException;
    void procesarCumplidoFueraDelPlazoDeNotificacionParcial(Documento documento) throws SGTServiceException;
    void procesaCartaExhortoTotal(Documento documento) throws SGTServiceException;
    void procesaCartaExhortoParcial(Documento documento) throws SGTServiceException;
}
