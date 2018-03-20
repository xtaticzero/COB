package mx.gob.sat.siat.cob.seguimiento.dao.catalogos.sql;

public interface AreaAdscripcionSQL {
    
    String OBTEN_TODAS_AREAS = "SELECT IDAREAADSCRIPCION, NOMBRE, DESCRIPCION, FECHAINICIO, FECHAFIN, ORDEN " +
                                                   " FROM SGTC_AREAADSCRIP  order by IDAREAADSCRIPCION desc " ;
    
    String AGREGA_AREA ="INSERT INTO SGTC_AREAADSCRIP (IDAREAADSCRIPCION, NOMBRE, DESCRIPCION, FECHAINICIO, FECHAFIN, ORDEN) " +
                                   "VALUES(SGTQ_AREAADSCRIP.nextval, ?, ?, ?, ?, ?)";
    
    String EDITA_AREA = "UPDATE SGTC_AREAADSCRIP SET NOMBRE = ?, DESCRIPCION =? WHERE IDAREAADSCRIPCION = ?";
    
    String ELIMINA_AREA = "UPDATE SGTC_AREAADSCRIP SET FECHAFIN = ? WHERE IDAREAADSCRIPCION = ?";
    
    String BUSCA_AREA_POR_ID = "SELECT COUNT(*) REGISTROS FROM SGTC_AREAADSCRIP WHERE IDAREAADSCRIPCION= ? " ; 
    
    String REACTIVA_AREA = "UPDATE SGTC_AREAADSCRIP SET FECHAFIN = NULL WHERE IDAREAADSCRIPCION = ?";
}
