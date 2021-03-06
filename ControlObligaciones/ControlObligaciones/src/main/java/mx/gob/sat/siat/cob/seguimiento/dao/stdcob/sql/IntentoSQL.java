package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql;

public interface IntentoSQL {
    String CONSULTAR_INTENTOS =
            "SELECT IDINTENTOJOB,IDEJECUCION,INTENTO,FECHAINICIO,FECHAFIN,IDESTADOJOB,OBSERVACIONES FROM SGTB_INTENTOJOB WHERE 1 = 1 ORDER BY idintentojob desc";
    
    String CONSULTAR_ULTIMO = "SELECT IDINTENTOJOB,IDEJECUCION,INTENTO,FECHAINICIO,FECHAFIN,IDESTADOJOB,OBSERVACIONES FROM SGTB_INTENTOJOB " +
                              " WHERE IDEJECUCION = ? AND INTENTO = (SELECT MAX(INTENTO) FROM SGTB_INTENTOJOB WHERE IDEJECUCION = ?)";
    
    String CONSULTAR_PRIMERO = "SELECT IDINTENTOJOB,IDEJECUCION,INTENTO,FECHAINICIO,FECHAFIN,IDESTADOJOB,OBSERVACIONES FROM SGTB_INTENTOJOB " +
                              " WHERE IDEJECUCION = ? AND INTENTO = 1";
    
    String INSERTAR   = "INSERT INTO SGTB_INTENTOJOB (IDINTENTOJOB,IDEJECUCION,INTENTO,FECHAINICIO,IDESTADOJOB) " +
                        "VALUES (SGTQ_INTENTOJOB.NEXTVAL,?,?,SYSDATE,?)";
    String ACTUALIZAR = "UPDATE SGTB_INTENTOJOB SET FECHAFIN = ?, IDESTADOJOB = ?, OBSERVACIONES=? WHERE IDINTENTOJOB = ?";
    
    String CONSULTAR_X_IDEJECUCION = "select ij.* from SGTB_IntentoJob ij where IDEJECUCION = ?";
    String INSERTAR_HISTORICO = "INSERT INTO SGTH_INTENTOJOB (IDINTENTOJOB,IDEJECUCION,INTENTO,FECHAINICIO,FECHAFIN,OBSERVACIONES,IDESTADOJOB) " +
                        "VALUES (?,?,?,?,?,?,?)";
    String BORRAR_REGISTRO="delete from SGTB_INTENTOJOB where IDINTENTOJOB = ?";
}
