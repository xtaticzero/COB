package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql;

public interface DetalleObligaPeriodosSQL {

    String SELECT_OBLIGA_PERIODOS = "SELECT \n"
            + "  doc.numerocontrol, \n"
            + "  perio.descripcionperiodo as descripcionperiodo,\n"
            + "  detalle.ejercicio, \n"
            + "  obli.concepto as conceptoimpuesto, \n"
            + "  obli.descripcion as descripcionobligacion,\n"
            + "  obli.fundamentolegal,\n"
            + "  detalle.fechavencimientoobligacion as fechavencimiento\n"
            + "FROM \n"
            + "  sgtt_documento doc\n"
            + "  INNER JOIN \n"
            + "    sgtt_detalledoc detalle \n"
            + "    ON (doc.numerocontrol = detalle.numerocontrol)\n"
            + "  INNER JOIN sgtc_obligacion obli \n"
            + "    ON (obli.idobligacion = detalle.idobligacion \n"
            + "      )\n"
            + "  INNER JOIN sgta_periodicidad perio\n"
            + "    ON (perio.idperiodo = detalle.idperiodo \n"
            + "      and perio.idperiodicidad = detalle.idperiodicidad)\n"
            + "WHERE obli.fechafin is null";
}
