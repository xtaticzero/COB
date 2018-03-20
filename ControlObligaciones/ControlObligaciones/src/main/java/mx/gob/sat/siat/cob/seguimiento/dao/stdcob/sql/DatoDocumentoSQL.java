package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql;

public interface DatoDocumentoSQL {

    String SELECT_DATO_DOCUMENTO = "SELECT NUMEROCONTROL,\n"
            + "  NOMBREALSC,\n"
            + "  RFC,\n"
            + "  CURP,\n"
            + "  NOMBRECONTRIBUYENTE,\n"
            + "  DOMICILIOCONTRIBUYENTE,\n"
            + "  IDVIGILANCIA,\n"
            + "  FRACCCIONALSC,\n"
            + "  LOCALIDADALSC,\n"
            + "  NOMBREADMINISTRADORALSC,\n"
            + "  IDESTADOGENERACION,\n"
            + "  DOMICILIOALSC,\n"
            + "  IDETAPAVIGILANCIA\n"
            + "FROM SGTT_DATODOCUMENTO";
    String INSERT_DATO_DOCUMENTO = "INSERT INTO SGTT_DATODOCUMENTO (\n"
            + "    NUMEROCONTROL,\n"
            + "    NOMBREALSC,\n"
            + "    RFC,\n"
            + "    CURP,\n"
            + "    NOMBRECONTRIBUYENTE,\n"
            + "    DOMICILIOCONTRIBUYENTE,\n"
            + "    IDVIGILANCIA,\n"
            + "    FRACCCIONALSC,\n"
            + "    LOCALIDADALSC,\n"
            + "    NOMBREADMINISTRADORALSC,\n"
            + "    IDESTADOGENERACION,\n"
            + "    DOMICILIOALSC) VALUES(";
}
