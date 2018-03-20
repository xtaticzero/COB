package mx.gob.sat.siat.cob.seguimiento.dao.catalogos.sql;

public interface EmailReporteProcesoSQL {
    
    String OBTEN_TODOS_EMAILREPORTEPROCESO = "select {1} AS ID, NOMBRECOMPLETO, CORREOELECTRONICO, "
            + "CORREOELECTRONICOALTERNO, FECHAINICIO, FECHAFIN  FROM {2} where fechafin is null order by FECHAINICIO desc ";
    
    String AGREGA_EMAILREPORTEPROCESO ="INSERT INTO {1} ({2}, NOMBRECOMPLETO, CORREOELECTRONICO, "
            + "CORREOELECTRONICOALTERNO, FECHAINICIO, FECHAFIN) " +
                                   "VALUES({3}, ?, ?, ?, ?, ?)";
    
    String EDITA_EMAILREPORTEPROCESO = "UPDATE {1} SET NOMBRECOMPLETO =? , CORREOELECTRONICO =? , "
            + " CORREOELECTRONICOALTERNO =? , FECHAFIN = ? WHERE {2} = ? ";
    
    String ELIMINA_EMAILREPORTEPROCESO = "UPDATE {1} SET FECHAFIN = ? WHERE {2} = ?";
}
