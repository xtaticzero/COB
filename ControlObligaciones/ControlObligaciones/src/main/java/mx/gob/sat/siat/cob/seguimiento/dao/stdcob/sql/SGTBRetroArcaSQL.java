    /*	****************************************************************
 * Nombre de archivo: SGTBRetroArcaSQL.java
 * Autores: Emmanuel Estrada Gonzalez 		 	 		
 * Bitácora de modificaciones:
 * CR/Defecto Fecha Autor Descripción del cambio
 * ---------------------------------------------------------------------------
 * N/A 30/10/2013 <Nombre del desarrollador que está modificando el archivo>
 * ---------------------------------------------------------------------------
 */
package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql;

public interface SGTBRetroArcaSQL {

    String INSERT_ITEM = "INSERT INTO SGTB_RETROARCA ("
            + "    IDPROCESADO,"
            + "    FECHAPROCESADO"
            + "    VALUES(?,SYSDATE)";
    String SELECT = "SELECT RA.IDPROCESADO,RA.FECHAPROCESADO"
            + " FROM SGTB_RETROARCA RA";
    String SELECT_ID = "SELECT RA.IDPROCESADO,RA.FECHAPROCESADO"
            + " FROM SGTB_RETROARCA RA  WHERE RA.IDPROCESADO = ?";
    String UPDATE_ID = "UPDATE SGTB_RETROARCA RA SET RA.IDPROCESADO = ? ,RA.FECHAPROCESADO = SYSDATE";
}
