/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql;

/**
 *
 * @author Juan
 */
public interface BitacoraDocumentoSQL {
    
    String INSERT_BITACORA_DOCUMENTO = "INSERT INTO sgtb_sgtdocumento "
            + "(NUMEROCONTROL,IDESTADODOCTO,FECHAMOVIMIENTO) "
            + " values(?,?,SYSDATE)";
    
}
