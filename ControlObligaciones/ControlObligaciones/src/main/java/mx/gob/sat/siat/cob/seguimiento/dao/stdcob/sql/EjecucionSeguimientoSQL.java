package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql;

public interface EjecucionSeguimientoSQL {

    String CONSULTA_ESTATUS_EJECUCION = "select enejecucion from sgtb_ejecucionseg where idejecucion= "
            + "(select max(idejecucion) from sgtb_ejecucionseg)";
    String ENEJECUCION = "enejecucion";
    String CONSULTA_EJECUCION_PROCESO = "SELECT IDEJECUCION AS ID,ENEJECUCION AS EJECUCION FROM SGTB_EJECUCIONSEG";
}
