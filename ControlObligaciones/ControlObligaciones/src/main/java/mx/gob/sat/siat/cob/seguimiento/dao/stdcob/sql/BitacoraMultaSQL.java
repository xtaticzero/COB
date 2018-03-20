package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql;

/**
 *
 * @author root
 */
public interface BitacoraMultaSQL {

    String INSERT = "insert into sgtb_sgtresolucion (IDESTADORESOLUCION,FECHAMOVIMIENTO,NUMERORESOLUCION) values(:estado,:fecha,:resolucion)";
    String REGISTRA_BITACORA_MULTA_MASIVO = "insert into sgtb_sgtresolucion(idestadoresolucion, fechamovimiento, numeroresolucion)\n" +
                                                                "select :estadoresolucion, :fecha, sgtt_resoluciondoc.numeroresolucion\n" +
                                                                "from sgtt_resoluciondoc join sgtt_numresolucion on sgtt_resoluciondoc.numeroresolucion = sgtt_numresolucion.numeroresolucion\n" +
                                                                "where sgtt_resoluciondoc.numerocontrol in (:documentos)";
}
