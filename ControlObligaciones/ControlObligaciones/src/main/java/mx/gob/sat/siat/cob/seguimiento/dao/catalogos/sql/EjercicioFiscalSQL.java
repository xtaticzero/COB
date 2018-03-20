package mx.gob.sat.siat.cob.seguimiento.dao.catalogos.sql;

public interface EjercicioFiscalSQL {
    
     String OBTEN_TODOS_EJERCICIOS = "SELECT E.IDEJERCICIOFISCAL, E.NOMBRE, E.DESCRIPCION, E.FECHAINICIO, E.FECHAFIN, E.ORDEN " +
                                                    " FROM SGTC_EJERCICIOFISCAL E order by E.FECHAINICIO desc " ;
    
     String AGREGA_EJERCICIO ="INSERT INTO SGTC_EJERCICIOFISCAL (IDEJERCICIOFISCAL, NOMBRE, DESCRIPCION, FECHAINICIO, FECHAFIN, ORDEN) " +
                                    "VALUES(?, ?, ?, ?, ?, ?)";
    
     String EDITA_EJERCICIO = "UPDATE SGTC_EJERCICIOFISCAL SET NOMBRE = ?, DESCRIPCION =? WHERE IDEJERCICIOFISCAL = ?";
    
     String ELIMINA_EJERCICIO = "UPDATE SGTC_EJERCICIOFISCAL SET FECHAFIN = ? WHERE IDEJERCICIOFISCAL = ?";
    
     String BUSCA_EJERCICIO_POR_ID ="SELECT * FROM SGTC_EJERCICIOFISCAL WHERE IDEJERCICIOFISCAL =?";
    
     String BUSCA_EJERCICIO_POR_NOMYDESC = "SELECT COUNT(*) REGISTROS FROM SGTC_EJERCICIOFISCAL WHERE IDEJERCICIOFISCAL= ? " ; 
    
     String REACTIVA_EJERCICIO = "UPDATE SGTC_EJERCICIOFISCAL SET FECHAFIN = NULL WHERE IDEJERCICIOFISCAL = ?";
                                            
    
    
}
