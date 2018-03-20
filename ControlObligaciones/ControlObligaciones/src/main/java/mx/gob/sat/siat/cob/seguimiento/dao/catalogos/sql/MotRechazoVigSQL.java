package mx.gob.sat.siat.cob.seguimiento.dao.catalogos.sql;

public interface MotRechazoVigSQL {
    
        String OBTEN_TODOS_MOTIVOS = "SELECT M.IDMOTIVORECHAZOVIG, M.NOMBRE, M.DESCRIPCION, M.FECHAINICIO, M.FECHAFIN, M.ORDEN " +
                                                       " FROM SGTC_MOTRECHAZOVIG M " +
                                                       " WHERE M.FECHAFIN IS NULL order by M.IDMOTIVORECHAZOVIG desc ";
       
        String AGREGA_MOTIVO ="INSERT INTO SGTC_MOTRECHAZOVIG (IDMOTIVORECHAZOVIG, NOMBRE, DESCRIPCION, FECHAINICIO, FECHAFIN, ORDEN) " +
                                       "VALUES(SGTQ_MOTRECHAZOVIG.nextval, ?, ?, ?, ?, ?)";
       
        String EDITA_MOTIVO = "UPDATE SGTC_MOTRECHAZOVIG SET NOMBRE = ?, DESCRIPCION =? WHERE IDMOTIVORECHAZOVIG = ?";
       
        String ELIMINA_MOTIVO = "UPDATE SGTC_MOTRECHAZOVIG SET FECHAFIN = ? WHERE IDMOTIVORECHAZOVIG = ?";
       
        String BUSCA_MOTIVO_POR_ID ="SELECT * FROM SGTC_MOTRECHAZOVIG WHERE IDMOTIVORECHAZOVIG =?";
       
        String BUSCA_MOTIVO_POR_IDYNOM = "SELECT COUNT(*) REGISTROS FROM SGTC_MOTRECHAZOVIG WHERE IDMOTIVORECHAZOVIG= ? " ; 
}
