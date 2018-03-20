/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.service.afectaciones;

import java.math.BigInteger;
import java.util.Date;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Documento;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;

/**
 *Servicio para procesar los cumplimientos obtenidos de Estructura de Cumplimiento.
 * @author Gabriel García
 */
public interface CumplimientoService {

    /**
     * Método para obtener el número máximo de identificador de cumplimiento.
     * @return El número máximo de identificador de cumplimiento.
     * @throws mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException 
     */
    BigInteger obtenerMaximoIdentificadorCumplimiento() throws SGTServiceException;
    
    /**
     * Método para eliminar los cumplimientos ya procesados
     * @throws mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException en caso que los cumplimientos no puedan ser eliminados.
     */
    void eliminarCumplimientosProcesados() throws SGTServiceException;
    
    void eliminarCumplimientosPorFechaMto(Date fechaMantenimiento)  throws SGTServiceException;
    
     Integer actualizarDetalleConCumplimiento(Documento documento, Integer idSituacionIcep) throws SGTServiceException;
}
