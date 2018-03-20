/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.arca.sql;

/**
 *
 * @author Ulises
 */
public interface MonitorArchivoArcaSQL {

    String INSERT = "INSERT INTO SGTT_ENVIODOCSARCA (\n"
            + "    IDVIGILANCIA,\n"
            + "    IDADMONLOCAL,\n"
            + "    CANTIDADDOCUMENTOS,\n"
            + "    CANTIDADOBLIGACIONPERIODO,\n"
            + "    CANTIDADORIGENMULTA,\n"
            + "    cantidadReqsAnteriores,\n"
            + "    FECHAENVIOARCA,\n"
            + "    ESENVIORESOLUCIONES) VALUES(?, ?, ?, ?, ?, ?, to_date(?,'yyyy/MM/dd HH24:MI:SS'), ?) ";
    String CONSULTAMONITORARCHIVO = "SELECT \n"
            + "    IDVIGILANCIA,\n"
            + "    IDADMONLOCAL,\n"
            + "    CANTIDADDOCUMENTOS,\n"
            + "    CANTIDADOBLIGACIONPERIODO,\n"
            + "    CANTIDADORIGENMULTA,\n"
            + "    cantidadReqsAnteriores,\n"
            + "    FECHAENVIOARCA,\n"
            + "    ESENVIORESOLUCIONES FROM SGTT_ENVIODOCSARCA "
            + "WHERE IDVIGILANCIA = ? AND IDADMONLOCAL = ? AND ESENVIORESOLUCIONES = ?";
    String CONSULTALISTAMONITORARCHIVO = "SELECT \n"
            + "    IDVIGILANCIA,\n"
            + "    IDADMONLOCAL,\n"
            + "    CANTIDADDOCUMENTOS,\n"
            + "    CANTIDADOBLIGACIONPERIODO,\n"
            + "    CANTIDADORIGENMULTA,\n"
            + "    cantidadReqsAnteriores,\n"
            + "    to_char(fechaEnvioArca,'yyyy/mm/dd hh24:mi:ss') as FECHAENVIOARCA,\n"
            + "    ESENVIORESOLUCIONES FROM SGTT_ENVIODOCSARCA "
            + "WHERE ESENVIORESOLUCIONES = ?";
    String ACTUALIZAMONITORARCHIVO = "UPDATE SGTT_ENVIODOCSARCA SET \n"
            + "    CANTIDADDOCUMENTOS= ?, \n"
            + "    CANTIDADOBLIGACIONPERIODO=?, \n"
            + "    CANTIDADORIGENMULTA=?, \n"
            + "    cantidadReqsAnteriores=? \n"
            + "    WHERE IDVIGILANCIA =? AND IDADMONLOCAL=? ";
    String DELETE = "DELETE FROM SGTT_ENVIODOCSARCA WHERE IDVIGILANCIA=? AND IDADMONLOCAL= ? AND ESENVIORESOLUCIONES = ?";
}
