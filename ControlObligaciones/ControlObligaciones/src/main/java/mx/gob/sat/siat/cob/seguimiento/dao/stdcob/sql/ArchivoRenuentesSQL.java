/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql;

/**
 *
 * @author Alex
 */
public interface ArchivoRenuentesSQL {

    /**
     *
     */
    String OBTENER_ID = "select sgtq_cargaRenuents.nextval from dual";

    /**
     *
     */
    String BUSCA_ARCHIVO_POR_ID = "SELECT R.IDCARGARENUENTES, R.USUARIOCARGA, R.NUMEMPLEADOCARGA, \n"
            + " R.NOMBREARCHIVOCARGA, R.TOTALREGISTROSARCHIVOCARGA, R.rutaarchivoerrores,\n"
            + " R.TOTALREGISTROSERRORES, R.FECHACARGA, to_char(r.fechacarga, 'DD/MM/YYYY hh:mi:ss') as FECHACARGASTR \n"
            + " FROM SGTT_CARGARENUENTS R WHERE R.IDCARGARENUENTES = (select max(idCargaRenuentes) as idCarga\n"
            + "from SGTT_CARGARENUENTS)";

    /**
     *
     */
    String AGREGA_ARCHIVO = "insert into SGTT_CARGARENUENTS\n"
            + "(IDCARGARENUENTES, USUARIOCARGA, NUMEMPLEADOCARGA, NOMBREARCHIVOCARGA, \n"
            + "TOTALREGISTROSARCHIVOCARGA, rutaarchivoerrores,\n"
            + "TOTALREGISTROSERRORES, FECHACARGA)\n"
            + "values(?,?,?,?,?,?,?,sysdate)";
}
