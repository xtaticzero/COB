/*	****************************************************************
	* Nombre de archivo: AfectacionPorNotificionService.java
	* Autores: Emmanuel Estrada Gonzalez 		 	 		
	* Bitácora de modificaciones:
	* CR/Defecto Fecha Autor Descripción del cambio
	* ---------------------------------------------------------------------------
	* N/A 21/10/2013 <Nombre del desarrollador que está modificando el archivo>
	* ---------------------------------------------------------------------------
*/
package mx.gob.sat.siat.cob.background.service.afectaciones;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.SeguimientoARCA;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;

public interface AfectacionPorNotificacionService {
    void actualizaEstadoDocumento(SeguimientoARCA seguimientoARCA)throws SGTServiceException;
}
