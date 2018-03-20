/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.dbd2.sql;

/**
 *
 * @author root
 */
public interface PadronSQL {
    String CONSULTA_BAJA_PADRON = "select C_IDC_ICDOENN1 boid,"
            + "C_ICE_ICEP1 icep, "
            + "F_OBL_AFMUEAD1 fecha_mantenimiento "
            + "from DM_IMPIN.DD_ICE_ICCOENP1 "
            + "where C_IDE_EVSITGA1=0 and F_OBL_AFMUEAD1='#{jobParameters['fecha']}' ";

    String INSERT_BAJA_ICEP = "insert into sgtt_bajaicep (BOID,"
            + "CLAVEICEP,"
            + "FECHAMANTENIMIENTO)values(:boid,"
            + "    :icep,"
            + "    :fechaMantenimiento)";

}
