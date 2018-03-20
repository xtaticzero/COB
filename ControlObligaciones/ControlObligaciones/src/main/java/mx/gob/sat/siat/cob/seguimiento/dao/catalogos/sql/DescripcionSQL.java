package mx.gob.sat.siat.cob.seguimiento.dao.catalogos.sql;

public interface DescripcionSQL {
    
         String OBTEN_TODAS_DESCRIPCIONES = "SELECT D.IDDESCRIPCION, D.DESCRIPCION, D.FECHAINICIO, D.FECHAFIN, D.ORDEN " +
                                                        " FROM SGTC_DSCRIPCIONVIG D " +
                                                        " WHERE D.FECHAFIN IS NULL order by D.IDDESCRIPCION desc  ";
        
         String AGREGA_DESCRIPCION ="INSERT INTO SGTC_DSCRIPCIONVIG (IDDESCRIPCION, DESCRIPCION, FECHAINICIO, FECHAFIN, ORDEN) " +
                                                "VALUES(SGTQ_DESCRIPCION.nextval, ?, ?, ?, ?)";
        
         String EDITA_DESCRIPCION = "UPDATE SGTC_DSCRIPCIONVIG SET DESCRIPCION =? WHERE IDDESCRIPCION = ?";
        
         String ELIMINA_DESCRIPCION = "UPDATE SGTC_DSCRIPCIONVIG SET FECHAFIN = ? WHERE IDDESCRIPCION = ?";
        
         String BUSCA_DESCRIPCION_POR_ID ="SELECT * FROM SGTC_DSCRIPCIONVIG WHERE IDDESCRIPCION =?";
        
         String BUSCA_DESCRIPCION_POR_IDYDES = "SELECT COUNT(*) REGISTROS FROM SGTC_DSCRIPCIONVIG WHERE DESCRIPCION= ? AND FECHAFIN IS NULL" ; 
}
