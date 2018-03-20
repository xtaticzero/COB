/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql;

/**
 *
 * @author emmanuel
 */
public interface TipoDeclaracionSQL {
    String SELECT_TIPO_DECLARACION = "select IDTIPODECLARACION from SGTC_TIPODEC where IDGRUPOTIPODECLARACION = ?";
}
