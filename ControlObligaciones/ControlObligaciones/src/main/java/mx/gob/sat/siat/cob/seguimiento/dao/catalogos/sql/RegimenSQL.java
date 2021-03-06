package mx.gob.sat.siat.cob.seguimiento.dao.catalogos.sql;

public interface RegimenSQL {
    
     String OBTEN_TODOS_REGIMEN = "SELECT R.IDREGIMEN, R.NOMBRE, R.DESCRIPCION, R.FECHAINICIO, R.FECHAFIN, R.ORDEN " +
                                                    " FROM SGTC_REGIMEN R order by R.IDREGIMEN desc " ;
    
     String AGREGA_REGIMEN ="INSERT INTO SGTC_REGIMEN (IDREGIMEN, NOMBRE, DESCRIPCION, FECHAINICIO, FECHAFIN, ORDEN) " +
                                    "VALUES(?, ?, ?, ?, ?, ?)";
    
     String EDITA_REGIMEN = "UPDATE SGTC_REGIMEN SET NOMBRE = ?, DESCRIPCION =? WHERE IDREGIMEN = ?";
    
     String ELIMINA_REGIMEN = "UPDATE SGTC_REGIMEN SET FECHAFIN = ? WHERE IDREGIMEN = ?";
    
     String BUSCA_REGIMEN_POR_ID ="SELECT * FROM SGTC_REGIMEN WHERE IDREGIMEN =?";
    
     String BUSCA_REGIMEN_POR_IDYNOM = "SELECT COUNT(*) REGISTROS FROM SGTC_REGIMEN WHERE IDREGIMEN= ? " ; 
    
     String REACTIVA_REGIMEN = "UPDATE SGTC_REGIMEN SET FECHAFIN = NULL WHERE IDREGIMEN = ?";
}
