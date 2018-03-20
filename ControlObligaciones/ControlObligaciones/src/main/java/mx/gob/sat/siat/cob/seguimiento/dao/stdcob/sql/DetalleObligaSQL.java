package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql;

public interface DetalleObligaSQL {

    String SELECT_DETALLE_OBLIGA = "SELECT "
            + "   NUMEROCONTROL,\n"
            + "   DESCRIPCIONOBLIGACION,\n"
            + "   FUNDAMENTOLEGAL,\n"
            + "   FECHAVENCIMIENTO,\n"
            + "   DESCRIPCIONPERIODO,\n"
            + "   EJERCICIO\n"
            + "FROM "
            + "   SGTT_DETALLEOBLIGA";
    String DETALLE_OBLIGA_X_NUM_CONTROL = "SELECT "
            + "   NUMEROCONTROL,\n"
            + "   DESCRIPCIONOBLIGACION,\n"
            + "   FUNDAMENTOLEGAL,\n"
            + "   FECHAVENCIMIENTO,\n"
            + "   DESCRIPCIONPERIODO,\n"
            + "   EJERCICIO\n"
            + "FROM "
            + "   SGTT_DETALLEOBLIGA "
            + "WHERE"
            + "   NUMEROCONTROL = ";
    String INSERT_DETALLE_OBLIGA = "INSERT INTO "
            + "   SGTT_DETALLEOBLIGA (\n"
            + "       NUMEROCONTROL,\n"
            + "       DESCRIPCIONOBLIGACION,\n"
            + "       FUNDAMENTOLEGAL,\n"
            + "       FECHAVENCIMIENTO,\n"
            + "       DESCRIPCIONPERIODO,\n"
            + "       EJERCICIO)\n"
            + "   VALUES(";
}
