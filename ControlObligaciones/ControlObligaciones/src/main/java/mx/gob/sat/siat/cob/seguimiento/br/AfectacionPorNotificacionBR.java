/*****************************************************************
* Nombre de archivo: AfectacionPorNotificacionBR.java
* Autores: Emmanuel Estrada Gonzalez
* Bitácora de modificaciones:
* CR/Defecto Fecha Autor Descripción del cambio
* ---------------------------------------------------------------------------
* N/A 21/10/2013 <Nombre del desarrollador que está modificando el archivo>
* ---------------------------------------------------------------------------
*/
package mx.gob.sat.siat.cob.seguimiento.br;

import java.util.Date;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.SeguimientoARCA;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Documento;

public interface AfectacionPorNotificacionBR {
    Documento obtenEdoSigDoc(SeguimientoARCA seguimintoARCA)throws SGTServiceException;
    Date calculaFechaValidezDoc(Integer tipoDoc,Integer etapaVigilancia,Date fechaNotificacion)throws SGTServiceException;
}
