package mx.gob.sat.siat.cob.seguimiento.dao.catalogos.sql;

public interface MultaMontoSQL {
    
     String OBTEN_TODAS_OBLISANCIONES = "SELECT SGTC_MULTAMONTO.* , SGTC_OBLIGACION.concepto, " +
                                                    "SGTC_MULTACOB.NOMBREMULTACOB as RESOLMOTIVO, obli.deslarga as DESCRIPCIONLARGA, substr(obli.deslarga, 1, 60) as DESCRIPCIONCORTA " + 
                                                    " FROM SGTC_MULTAMONTO " + 
                                                    " INNER JOIN SGTC_OBLIGACION ON SGTC_OBLIGACION.IDOBLIGACION = SGTC_MULTAMONTO.IDOBLIGACION " + 
                                                    " INNER JOIN SGTC_MULTACOB  ON SGTC_MULTACOB.CONSTANTERESOLMOTIVO = SGTC_MULTAMONTO.CONSTANTERESOLMOTIVO " +
                                                    " INNER JOIN rfcc_obligacion obli on obli.IDOBLIGACION = SGTC_MULTAMONTO.IDOBLIGACION \n" + 
                                                    " INNER JOIN rfcc_grupooblig am on obli.idtipocontribucion = am.idgrupooblig\n"+
                                                    " order by SGTC_MULTAMONTO.IDMULTAMONTO desc ";
    
     String AGREGA_OBLISANCION ="INSERT INTO SGTC_MULTAMONTO (SANCION, INFRACCION, FECHAINICIO, FECHAFIN, IDOBLIGACION, ORDEN, IDMULTAMONTO, CONSTANTERESOLMOTIVO, MOTIVACION, MONTO, DESCRIPCIONMONTO)  " +
                                           " VALUES(?, ?, ?, ?, ?, ?,  SGTQ_MULTAMONTO.nextval, ?, ?, ?, ?)";
    
     String EDITA_OBLISANCION = "UPDATE SGTC_MULTAMONTO SET SANCION = ?, INFRACCION =?, CONSTANTERESOLMOTIVO = ?, MOTIVACION = ?, MONTO = ?, DESCRIPCIONMONTO = ?   WHERE IDMULTAMONTO = ?";
    
     String ELIMINA_OBLISANCION = "UPDATE SGTC_MULTAMONTO SET FECHAFIN = ? -1 WHERE IDMULTAMONTO = ?";
    

    
     String LISTA_COMBO_OBLIGACION = "select rfcc.idobligacion as ID, CONCAT(concat(rfcc.idobligacion, '-'),(rfcc.deslarga)) as DESCRIPCION\n" + 
                                         "  from rfcc_obligacion rfcc\n" + 
                                         "  INNER JOIN SGTC_OBLIGACION obli on obli.IDOBLIGACION = rfcc.IDOBLIGACION\n" + 
                                         "  and obli.fechafin is null\n" + 
                                         "  order by obli.idobligacion";
    
     String LISTA_COMBO_OBLIGACION_POR_ID = "select null as ID , r.deslarga as DESCRIPCION from SGTC_OBLIGACION o " +
                                             "WHERE O.FECHAFIN IS NULL  AND o.idobligacion = ?";
 
     String BUSCA_OBLISANCION_MOTIVACION ="SELECT IDOBLIGACION,CONSTANTERESOLMOTIVO,MOTIVACION FROM SGTC_MULTAMONTO WHERE MOTIVACION IS NOT NULL and fechafin is null order by IDOBLIGACION";
     
     String BUSCA_MULTAS_MONTO_REPETIDA = "SELECT COUNT(*) REGISTROS FROM SGTC_MULTAMONTO WHERE idobligacion = ? AND constanteresolmotivo = ? AND FECHAFIN IS NULL"; 
     
     String BUSCA_ID_MULTAS_MONTO_REPETIDA = "SELECT IDMULTAMONTO AS ID FROM SGTC_MULTAMONTO WHERE idobligacion = ? AND constanteresolmotivo = ? AND FECHAFIN IS NULL"; 
     
     String ELIMINA_REPETIDA_MULTAS_MONTO ="UPDATE SGTC_MULTAMONTO set FECHAFIN= :fechaFin where IDMULTAMONTO= :idMultaMonto";  
     
     
}
