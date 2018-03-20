package mx.gob.sat.siat.cob.seguimiento.dao.arca.sql;

public interface ObligacionSQL {

    String INSERT = "INSERT INTO SIATControlDeObligaciones.T_OBLIGACION (\n"
            + "    IDDOCUMENTO,\n"
            + "    FCDESCRIPCIONDEOBLIGACION,\n"
            + "    FCDESCRIPCIONDEPERIODO,\n"
            + "    FIEJERCICIO,\n"
            + "    FCFUNDAMENTOLEGAL,\n"
            + "    FDFECHADEVENCIMIENTO,\n"
            + "    FCSANCION,\n"
            + "    FCINFRACCION,\n"
            + "    FCIMPORTE) ";
    /*
     * Select Obligaciones por idVigilancia y idALSC
     */
    String SELECT_X_ID_VIGILANCIA = "select count(1)\n"
            + "from SIATControlDeObligaciones.T_Obligacion obli\n"
            + "inner join SIATControlDeObligaciones.T_Documento doc\n"
            + "on doc.id = obli.idDocumento\n"
            + "and doc.idVigilancia = ?\n"
            + "and doc.idALSC = ?\n"
            + "and convert(datetime, convert(varchar(20), doc.fdFechaRegistro, 20)) = convert(datetime,?)";
    /*
     * Delete Obligaciones por idVigilancia y idALSC
     */
    String DELETE = "delete from SIATControlDeObligaciones.T_Obligacion where idDocumento in (\n"
            + "select \n"
            + "doc.id\n"
            + "from SIATControlDeObligaciones.T_Documento doc \n"
            + "where doc.idVigilancia = ? and doc.idALSC = ? and \n"
            + " convert(datetime, convert(varchar(20), doc.fdFechaRegistro, 20)) = convert(datetime,?)\n"
            + ")";
}
