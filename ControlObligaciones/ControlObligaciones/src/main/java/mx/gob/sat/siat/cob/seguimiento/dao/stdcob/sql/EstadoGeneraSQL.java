package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql;

public interface EstadoGeneraSQL {

    String SELECT_ESTADO_GENERA = "SELECT IDESTADOGENERACION,\n"
            + "  NOMBRE,\n"
            + "  DESCRIPCION,\n"
            + "  FECHAINICIO,\n"
            + "  FECHAFIN,\n"
            + "  ORDEN\n"
            + "FROM SGTC_ESTADOGENERA";
    String INSERT_ESTADO_GENERA = "NSERT INTO SGTC_ESTADOGENERA ( \n"
            + "    IDESTADOGENERACION,\n"
            + "    NOMBRE,\n"
            + "    DESCRIPCION,\n"
            + "    FECHAINICIO,\n"
            + "    FECHAFIN,\n"
            + "    ORDEN ) \n"
            + "  VALUES (";
}
