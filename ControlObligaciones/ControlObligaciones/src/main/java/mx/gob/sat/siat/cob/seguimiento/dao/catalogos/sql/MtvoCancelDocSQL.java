package mx.gob.sat.siat.cob.seguimiento.dao.catalogos.sql;

public interface MtvoCancelDocSQL {
    
     String OBTEN_TODOS_MOTIVOS = "SELECT M.IDMOTIVOCANCELACION, M.NOMBRE, M.DESCRIPCION, M.FECHAINICIO, M.FECHAFIN, M.ORDEN " +
                                                        " FROM SGTC_MTVOCANCELDOC M " +
                                                        " WHERE M.FECHAFIN IS NULL order by M.IDMOTIVOCANCELACION desc ";
        
         String AGREGA_MOTIVO ="INSERT INTO SGTC_MTVOCANCELDOC (IDMOTIVOCANCELACION, NOMBRE, DESCRIPCION, FECHAINICIO, FECHAFIN, ORDEN) " +
                                        "VALUES(SGTQ_MTVOCANCELDOC.nextval, ?, ?, ?, ?, ?)";
        
         String EDITA_MOTIVO = "UPDATE SGTC_MTVOCANCELDOC SET NOMBRE = ?, DESCRIPCION =? WHERE IDMOTIVOCANCELACION = ?";
        
         String ELIMINA_MOTIVO = "UPDATE SGTC_MTVOCANCELDOC SET FECHAFIN = ? WHERE IDMOTIVOCANCELACION = ?";
        
         String BUSCA_MOTIVO_POR_ID ="SELECT * FROM SGTC_MTVOCANCELDOC WHERE IDMOTIVOCANCELACION =?";
        
         String BUSCA_MOTIVO_POR_IDYNOM = "SELECT COUNT(*) REGISTROS FROM SGTC_MTVOCANCELDOC WHERE IDMOTIVOCANCELACION= ? " ; 

}
