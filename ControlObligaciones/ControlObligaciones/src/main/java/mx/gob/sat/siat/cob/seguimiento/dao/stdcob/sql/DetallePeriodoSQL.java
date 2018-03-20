package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql;

public interface DetallePeriodoSQL {

    String SELECT_DETALLE_PERIODO = "SELECT "
            + "   NUMEROCONTROL,\n"
            + "   DESCRIPCIONPERIODO,\n"
            + "   EJERCICIO,\n"
            + "   CONCEPTOIMPUESTO\n"
            + "FROM "
            + "   SGTT_DETALLEPERIODOS";
    String DETALLE_PERIODO_X_NUM_CONTROL = "SELECT "
            + "   NUMEROCONTROL,\n"
            + "   DESCRIPCIONPERIODO,\n"
            + "   EJERCICIO,\n"
            + "   CONCEPTOIMPUESTO\n"
            + "FROM "
            + "   SGTT_DETALLEPERIODOS"
            + "WHERE"
            + "   NUMEROCONTROL = ";
    String INSERT_DETALLE_PERIODO = "INSERT INTO "
            + "   SGTT_DETALLEPERIODOS (\n"
            + "       NUMEROCONTROL,\n"
            + "       DESCRIPCIONPERIODO,\n"
            + "       EJERCICIO,\n"
            + "       CONCEPTOIMPUESTO)\n"
            + "   VALUES(";
}
