package mx.gob.sat.siat.cob.seguimiento.dao.catalogos.sql;

public interface FundamentoLegalSQL {

    
     String OBTEN_TODOS_FUNDAMENTOS = "SELECT distinct(sgtc_fundamentoleg.idfundamentolegal), sgtc_fundamentoleg.* ,\n" + 
                                     "  SGTC_OBLIGACION.concepto , rfcc_regimen.DESLARGA as DESCRIPCIONREGIMEN, SGTC_EJERCICIOFISCAL.NOMBRE as EJERCICIOFIS, \n" + 
                                     "  SGTA_PERIODICIDAD.DESCRIPCIONPERIODO as PERIODICIDAD, obli.deslarga as DESCRIPCIONLARGA, substr(obli.deslarga, 1, 60) as DESCRIPCIONCORTA \n" + 
                                     "  FROM sgtc_fundamentoleg \n" + 
                                     "  INNER JOIN SGTC_OBLIGACION ON SGTC_OBLIGACION.IDOBLIGACION = sgtc_fundamentoleg.IDOBLIGACION \n" + 
                                     "  INNER JOIN rfcc_regimen ON rfcc_regimen.IDREGIMEN = sgtc_fundamentoleg.IDREGIMEN \n" + 
                                     "  INNER JOIN SGTC_EJERCICIOFISCAL ON SGTC_EJERCICIOFISCAL.IDEJERCICIOFISCAL = sgtc_fundamentoleg.IDEJERCICIOFISCAL \n" + 
                                     "  AND SGTC_EJERCICIOFISCAL.IDEJERCICIOFISCAL = (SELECT MAX(IDEJERCICIOFISCAL) AS EJERCICIO FROM sgtc_ejerciciofiscal WHERE FECHAFIN IS NULL) \n" +
                                     "  INNER JOIN rfcc_obligacion obli on obli.IDOBLIGACION = sgtc_fundamentoleg.IDOBLIGACION \n" + 
                                     "  INNER JOIN rfcc_grupooblig am on obli.idtipocontribucion = am.idgrupooblig\n" +
                                     "  INNER JOIN SGTA_PERIODICIDAD ON (SGTA_PERIODICIDAD.IDPERIODO = sgtc_fundamentoleg.IDPERIODO \n" + 
                                     "  AND SGTA_PERIODICIDAD.IDPERIODICIDAD = sgtc_fundamentoleg.IDPERIODICIDAD ) \n" + 
                                     "  AND SGTC_OBLIGACION.FECHAFIN IS NULL order by sgtc_fundamentoleg.IDFUNDAMENTOLEGAL desc";
                                                                        
     String OBTEN_TODOS_FUNDAMENTOS_EJERCICIO = "SELECT distinct(sgtc_fundamentoleg.idfundamentolegal), sgtc_fundamentoleg.* ,\n" + 
                                     "  SGTC_OBLIGACION.concepto , rfcc_regimen.DESLARGA as DESCRIPCIONREGIMEN, SGTC_EJERCICIOFISCAL.NOMBRE as EJERCICIOFIS, \n" + 
                                     "  SGTA_PERIODICIDAD.DESCRIPCIONPERIODO as PERIODICIDAD, obli.deslarga as DESCRIPCIONLARGA, substr(obli.deslarga, 1, 60) as DESCRIPCIONCORTA \n" + 
                                     "  FROM sgtc_fundamentoleg \n" + 
                                     "  INNER JOIN SGTC_OBLIGACION ON SGTC_OBLIGACION.IDOBLIGACION = sgtc_fundamentoleg.IDOBLIGACION \n" + 
                                     "  INNER JOIN rfcc_regimen ON rfcc_regimen.IDREGIMEN = sgtc_fundamentoleg.IDREGIMEN \n" + 
                                     "  INNER JOIN SGTC_EJERCICIOFISCAL ON SGTC_EJERCICIOFISCAL.IDEJERCICIOFISCAL = sgtc_fundamentoleg.IDEJERCICIOFISCAL \n" + 
                                     "  AND SGTC_EJERCICIOFISCAL.IDEJERCICIOFISCAL = ?\n" +
                                     "  INNER JOIN rfcc_obligacion obli on obli.IDOBLIGACION = sgtc_fundamentoleg.IDOBLIGACION \n" + 
                                     "  INNER JOIN rfcc_grupooblig am on obli.idtipocontribucion = am.idgrupooblig\n" +
                                     "  INNER JOIN SGTA_PERIODICIDAD ON (SGTA_PERIODICIDAD.IDPERIODO = sgtc_fundamentoleg.IDPERIODO \n" + 
                                     "  AND SGTA_PERIODICIDAD.IDPERIODICIDAD = sgtc_fundamentoleg.IDPERIODICIDAD ) \n" + 
                                     "  AND SGTC_OBLIGACION.FECHAFIN IS NULL order by sgtc_fundamentoleg.IDFUNDAMENTOLEGAL desc";
     
     String AGREGA_FUNDAMENTO ="INSERT INTO sgtc_fundamentoleg (IDOBLIGACION, IDREGIMEN, IDPERIODO, IDPERIODICIDAD, IDEJERCICIOFISCAL, " +
                                                 "FUNDAMENTOLEGAL, FECHAINICIO, FECHAFIN, FECHAVENCIMIENTOOBL, IDFUNDAMENTOLEGAL) " +
                                                 "VALUES(?,?,?,?,?,?,?,?,?,sgtq_fundamentoleg.nextval)";
     
     String INSERTA_FUNDAMENTO_BATCH ="insert /*+ APPEND_VALUES */ into sgtc_fundamentoleg ("
                                + "IDOBLIGACION,IDREGIMEN,IDPERIODO,IDPERIODICIDAD,IDEJERCICIOFISCAL,FUNDAMENTOLEGAL,FECHAINICIO,IDFUNDAMENTOLEGAL,FECHAVENCIMIENTOOBL) "+
                                "values(?,?,?,?,?,?,sysdate,sgtq_fundamentoleg.nextVAL,?)";
    
     String EDITA_FUNDAMENTO = "UPDATE sgtc_fundamentoleg SET FUNDAMENTOLEGAL = ?,  FECHAVENCIMIENTOOBL= ? WHERE IDFUNDAMENTOLEGAL = ?";
    
     String ELIMINA_FUNDAMENTO = "UPDATE sgtc_fundamentoleg SET FECHAFIN = ? WHERE IDFUNDAMENTOLEGAL = ?";
    
     String BUSCA_FUNDAMENTO_POR_ID ="SELECT * FROM sgtc_fundamentoleg WHERE IDFUNDAMENTOLEGAL =? AND FECHAFIN IS NULL";
    
     String BUSCA_FUNDAMENTO_POR_IDFUNDAMENTO = "SELECT COUNT(*) REGISTROS FROM sgtc_fundamentoleg WHERE " +
                                            "IDOBLIGACION = ? " + 
                                            "AND IDREGIMEN = ? " +
                                            "AND IDPERIODO = ? " +
                                            "AND IDPERIODICIDAD = ? " +
                                            "AND IDEJERCICIOFISCAL = ? " +
                                            "AND FECHAFIN IS NULL "; 
    
     String BUSCAR_FUNDAMENTO_PARA_EXPORTAR_ARCHIVO=" SELECT * FROM sgtc_fundamentoleg " +
                                                         " fl inner join sgtc_obligacion ob on  fl.idobligacion=ob.IDOBLIGACION " +
                                                        " WHERE IDPERIODO={0} AND IDPERIODICIDAD='{1}' AND IDEJERCICIOFISCAL={2} AND fl.FECHAFIN is null";
    
     
    String LISTA_COMBO_OBLIGACION_FUNDAMENTO = "select rfcc.idobligacion as ID, CONCAT(concat(rfcc.idobligacion, '-'),(rfcc.deslarga)) as DESCRIPCION\n" + 
                                        "  from rfcc_obligacion rfcc\n" + 
                                        "  INNER JOIN SGTC_OBLIGACION obli on obli.IDOBLIGACION = rfcc.IDOBLIGACION\n" + 
                                        "  and obli.fechafin is null\n" + 
                                        "  order by obli.idobligacion";
    
    
     String LISTA_COMBO_PERIODO = "select CONCAT(o.IDPERIODO,CONCAT('|',o.IDPERIODICIDAD)) as ID, o.DESCRIPCIONPERIODO as DESCRIPCION from SGTA_PERIODICIDAD o "+
                                            "WHERE O.FECHAFIN IS NULL";
    
     String LISTA_COMBO_EJERCICIO_FISCAL = "select o.IDEJERCICIOFISCAL as ID, o.NOMBRE as DESCRIPCION from SGTC_EJERCICIOFISCAL o  "+ 
                                               "WHERE O.FECHAFIN IS NULL order by o.IDEJERCICIOFISCAL desc";
  
     String FUNDAMENTOS_EXISTENTES = "select IDFUNDAMENTOLEGAL AS ID, IDOBLIGACION||','||IDREGIMEN||','||IDPERIODO||','||IDPERIODICIDAD||','||IDEJERCICIOFISCAL as NOMBRE\n" +
                                     "from sgtc_fundamentoleg ";
     
}