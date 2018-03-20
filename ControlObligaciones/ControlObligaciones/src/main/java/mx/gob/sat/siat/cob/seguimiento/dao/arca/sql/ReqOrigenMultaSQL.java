/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.arca.sql;

/**
 *
 * @author root
 */
public interface ReqOrigenMultaSQL {

    String INSERT = "INTO SIATControlDeObligaciones.T_ORIGENMULTA (\n"
            + "    IDDOCUMENTO,\n"
            + "    FCNUMEROCONTROLORIGEN,\n"
            + "    FDFECHANOTIFICACIONORIGEN,\n"
            + "    FCNUMEROCONTROLPRIMERO,\n"
            + "    FDFECHANOTIFICACIONPRIMERO,\n"
            + "    FCNUMEROCONTROLSEGUNDO,\n"
            + "    FDFECHANOTIFICACIONSEGUNDO,\n"
            + "VALUES(";
    String SELECT_X_ID_VIGILANCIA = "select count(1)\n"
            + "from SIATControlDeObligaciones.T_OrigenMulta ori\n"
            + "inner join SIATControlDeObligaciones.T_Documento doc\n"
            + "on doc.id = ori.idDocumento\n"
            + "and doc.idVigilancia = ?\n"
            + "and doc.idALSC = ?\n"
            + "and convert(datetime, convert(varchar(20), doc.fdFechaRegistro, 20)) = convert(datetime,?)";
    String DELETE = "delete from SIATControlDeObligaciones.T_OrigenMulta where idDocumento in (\n"
            + "select \n"
            + "doc.id\n"
            + "from SIATControlDeObligaciones.T_Documento doc \n"
            + "where doc.idVigilancia = ? and doc.idALSC = ? "
            + "and convert(datetime, convert(varchar(20), doc.fdFechaRegistro, 20)) = convert(datetime,?))";
}