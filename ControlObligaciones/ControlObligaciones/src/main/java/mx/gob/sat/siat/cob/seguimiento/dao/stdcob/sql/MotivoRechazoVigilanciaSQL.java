/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql;

/**
 *
 * @author rodrigo
 */
public interface MotivoRechazoVigilanciaSQL {

    String CONSULTAR_MOTIVOS = "select IDMOTIVORECHAZOVIG, \n"
            + "       NOMBRE, \n"
            + "       DESCRIPCION\n"
            + "from SIAT_SGT_ADMIN.SGTC_MOTRECHAZOVIG\n"
            + "where fechafin is null";

}
