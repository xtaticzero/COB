package mx.gob.sat.siat.cob.seguimiento.dao.idc.sql;

public interface PersonaTipoSQL {
    String OBTENER_TODOS_PERSONATIPO = "SELECT * FROM ADMC_PERTIPO WHERE ESSELECCIONABLE = 1 AND FECHAFIN IS NULL";
    String OBTENER_PERSONATIPO_POR_ID = "SELECT * FROM ADMC_PERTIPO WHERE IDPERTIPO=?";
}
