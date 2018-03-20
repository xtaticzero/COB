/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.arca.sql;

/**
 *
 * @author Juan
 */
public interface PeriodoSQL {

    String INSERT = "INSERT INTO SIATControlDeObligaciones.T_PERIODO (\n"
            + "    IDDOCUMENTO,\n"
            + "    FCDESCRIPCIONDEPERIODO,\n"
            + "    FIEJERCICIO,\n"
            + "    FCCONCEPTODEIMPUESTO)";
    /*
     * Select Periodos por idVigilancia y idALSC
     */
    String SELECT_X_ID_VIGILANCIA = "select count(1)\n"
            + "from SIATControlDeObligaciones.T_Periodo per\n"
            + "inner join SIATControlDeObligaciones.T_Documento doc\n"
            + "on doc.id = per.idDocumento\n"
            + "and doc.idVigilancia = ?\n"
            + "and doc.idALSC = ?\n"
            + "and convert(datetime, convert(varchar(20), doc.fdFechaRegistro, 20)) = convert(datetime,?)";
    /*
     * Delete Periodos por idVigilancia y idALSC
     */
    String DELETE = "delete from SIATControlDeObligaciones.T_Periodo where idDocumento in (\n"
            + "select \n"
            + "doc.id\n"
            + "from SIATControlDeObligaciones.T_Documento doc \n"
            + "where doc.idVigilancia = ? and doc.idALSC = ? \n"
            + "and convert(datetime, convert(varchar(20), doc.fdFechaRegistro, 20)) = convert(datetime,?)\n"
            + ")";
}
