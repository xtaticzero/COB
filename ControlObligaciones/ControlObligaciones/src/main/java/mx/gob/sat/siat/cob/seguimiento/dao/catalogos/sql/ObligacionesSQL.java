package mx.gob.sat.siat.cob.seguimiento.dao.catalogos.sql;

public interface ObligacionesSQL {

    String OBTEN_TODAS_OBLIGACIONES = "  SELECT o.idobligacion as IDOBLIGACION, o.concepto as Concepto, o.DESCRIPCION, obli.deslarga AS DESCROBLICOMPLETA," +
                                        "  o.fechainicio, o.fechafin, o.orden, CB.NOMBRE AS NOMBRE,  CB.IDVALORBOOLEAN AS IDVALORBOOLEAN, obli.descorta AS DESCROBLIG\n" + 
                                        "  FROM SGTC_OBLIGACION O \n" + 
                                        "  left join SGTC_VALORBOOLEAN CB on O.APLICARENUENTES = CB.IDVALORBOOLEAN \n" + 
                                        "  INNER JOIN rfcc_obligacion obli on obli.IDOBLIGACION = o.IDOBLIGACION \n" + 
                                        "  INNER JOIN rfcc_grupooblig am on obli.idtipocontribucion = am.idgrupooblig\n" + 
                                        //"  WHERE O.FECHAFIN IS NULL AND CB.FECHAFIN IS NULL \n" + 
                                        "  order by O.FECHAINICIO desc";
    
    String AGREGA_OBLIGACION = "INSERT INTO SGTC_OBLIGACION (APLICARENUENTES, IDOBLIGACION, CONCEPTO, DESCRIPCION, FECHAINICIO, FECHAFIN, ORDEN) "
            + "VALUES(?, ?, ?, ?, ?, ?, ?)";
    String EDITA_OBLIGACION = "UPDATE SGTC_OBLIGACION SET CONCEPTO = ?, DESCRIPCION =?, APLICARENUENTES = ? WHERE IDOBLIGACION = ?";
    String ELIMINA_OBLIGACION = "UPDATE SGTC_OBLIGACION SET FECHAFIN = ? WHERE IDOBLIGACION = ?";
    String BUSCA_OBLIGACION_POR_ID = "SELECT * FROM SGTC_OBLIGACION WHERE IDOBLIGACION =?";
    String BUSCA_OBLIGACION_POR_CONCYDESC = "SELECT count(*) as REGISTROS FROM SGTC_OBLIGACION WHERE  IDOBLIGACION = ? "
            + " AND FECHAINICIO <= sysdate "
            + " AND ((FECHAFIN is not null) or (FECHAFIN is null))  ";
    String OBTEN_TODOS_VALORES_BOOL = "SELECT * FROM SGTC_VALORBOOLEAN WHERE FECHAFIN IS NULL";
                       
    String LISTA_COMBO_OBLIGACION = "select idobligacion as ID, CONCAT(concat(idobligacion, '-'),substr(deslarga, 1, 60)) as DESCRIPCIONCORTA, CONCAT(concat(idobligacion, '-'),(deslarga)) as DESCRIPCIONLARGA from rfcc_obligacion order by idobligacion";
    String LISTA_COMBO_REGIMEN = " select idregimen as ID, CONCAT(concat(idregimen, '-'),(deslarga)) as DESCRIPCION from rfcc_regimen where idregimen != -1 order by idregimen ";
    String GET_DISTINCT_OBLIGACION = "SELECT DISTINCT obligacion.idobligacion,\n"
            + "  CASE\n"
            + "    WHEN obligacion.aplicarenuentes = 1\n"
            + "    THEN TO_CHAR(CONCAT(concat(obligacion.idobligacion, '-'),(obligacion.descripcion)))\n"
            + "      ||' (Aplica Renuente)'\n"
            + "    ELSE CONCAT(concat(obligacion.idobligacion, '-'),(obligacion.descripcion))\n"
            + "  END AS concepto,\n"
            + "  obligacion.aplicarenuentes\n"
            + "FROM sgtc_obligacion obligacion\n"
            + "WHERE obligacion.fechafin IS NULL\n"
            + "ORDER BY obligacion.idobligacion";
    
    String OBTENER_CONCEPTO_IMP = "  select gpo.deslarga AS CONCEPTO\n" + 
                                        " from rfcc_obligacion obli\n" + 
                                        " inner join RFCC_GRUPOOBLIG gpo on (obli.idtipocontribucion = gpo.idgrupooblig)\n" + 
                                        " where idobligacion = ?";
    
    String REACTIVA_OBLIGACION = "UPDATE SGTC_OBLIGACION SET FECHAFIN = NULL WHERE IDOBLIGACION = ?";
    
}
