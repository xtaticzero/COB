/*	****************************************************************
	* Nombre de archivo: SgtDocumentoSQL.java
	* Autores: Emmanuel Estrada Gonzalez 		 	 		   
	* Bitácora de modificaciones:
	* CR/Defecto Fecha Autor Descripción del cambio
	* ---------------------------------------------------------------------------
	* N/A 25/10/2013 <Nombre del desarrollador que está modificando el archivo>
	* ---------------------------------------------------------------------------
*/
package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql;

public interface SgtDocumentoSQL {    
    String INSERT = "INSERT INTO"
                                 + " SGTB_SGTDOCUMENTO( NUMEROCONTROL, IDESTADODOCTO, FECHAMOVIMIENTO)"
                                 + " VALUES(?, ?, SYSDATE)";    
    
}
