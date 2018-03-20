package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql;

public interface ProcesosSQL {

    String FIELDS = "IDPROCESO"
            + ",NOMBRE"
            + ",DESCRIPCION"
            + ",LANZADOR"
            + ",PROGRAMACION"
            + ",EXCLUIR"
            + ",PRIORIDAD"
            + ",ESTADO"
            + ",IDMANAGER"
            + ",INTENTOS"
            + ",INTENTOSMAX"
            + ",INICIOEJECUCION"
            + ",FINEJECUCION "
            + ",TIPOPROCESAMIENTO ";
    String FROM_SGTC_PROCESO = " FROM SGTC_PROCESO WHERE (FECHAFIN IS NULL OR FECHAFIN > SYSDATE) ";
    String CONSULTA_PROCESOS
            = "select IDPROCESO,NOMBRE,DESCRIPCION,FECHAINICIO,FECHAFIN,IDMANAGER,ORDEN,ENEJECUCION,"
            + "FECHAINICIOEJECUCION,FECHAFINEJECUCION,INTENTOS,INTENTOSMAX from SGTC_PROCESOS "
            + "WHERE ORDEN <> 0 ORDER BY ORDEN";
    String CONSULTA_CRON_PRINCIPAL
            = "select IDPROCESO,NOMBRE,DESCRIPCION,FECHAINICIO,FECHAFIN,IDMANAGER,ORDEN,ENEJECUCION,"
            + "FECHAINICIOEJECUCION,FECHAFINEJECUCION,INTENTOS,INTENTOSMAX from SGTC_PROCESOS "
            + "WHERE ORDEN = 0 ORDER BY ORDEN";
    String ACTUALIZA_PROCESO
            = "update SGTC_PROCESO set PRIORIDAD = ?, NOMBRE=?,DESCRIPCION=?,LANZADOR=?,EXCLUIR=?,PROGRAMACION=?,"
            + "ESTADO=?,INTENTOS=?,INTENTOSMAX=?,IDMANAGER=?,TIPOPROCESAMIENTO=?,INICIOEJECUCION = ?,FINEJECUCION=? where IDPROCESO=?";
    String ACTUALIZA_INICIOEJECUCION
            = "update SGTC_PROCESO set INICIOEJECUCION=?,FINEJECUCION=NULL,ESTADO=? where IDPROCESO=?";
    String ACTUALIZA_FINEJECUCION
            = "update SGTC_PROCESO set FINEJECUCION=?,INTENTOS=?,ESTADO=?,IDMANAGER=NULL where IDPROCESO=?";
    String ACTUALIZA_MANAGER
            = "update SGTC_PROCESO set IDMANAGER=? where ESTADO=3 AND IDMANAGER IS NULL";
    String SQL_CONSULTA_PROCESOS_POR_FILTRO = "SELECT "
            + FIELDS
            + FROM_SGTC_PROCESO;
    String CONSULTA_PROCESOS_POR_LANZAR = "SELECT "
            + FIELDS
            + FROM_SGTC_PROCESO
            + "AND (LANZADOR like ?) OR (LANZADOR like ? || ',%') OR (LANZADOR like '%,' || ?) OR (LANZADOR like '%,'||?||',%')";
    String SQL_CONSULTA_PROCESOS_POR_IDS = "SELECT "
            + FIELDS
            + FROM_SGTC_PROCESO;
    String SQL_CONSULTAR_DASHBOARD = "SELECT "
            + FIELDS
            + FROM_SGTC_PROCESO;

    String SQL_INSERTAR = " INSERT INTO SGTC_PROCESO (IDPROCESO,NOMBRE,DESCRIPCION,LANZADOR,PROGRAMACION,ESTADO,INTENTOS,INTENTOSMAX,EXCLUIR,PRIORIDAD,TIPOPROCESAMIENTO,FECHAINICIO) "
            + " VALUES                    (SGTQ_PROCESO.NEXTVAL,?,?,?,?,?,0,?,?,?,?,SYSDATE)";
    String SQL_INICIAR_ESTADOS = "UPDATE sgtc_proceso SET IDMANAGER = NULL, ESTADO = CASE WHEN estado IN (2,3,4,5,6) THEN 1 ELSE estado END";

    String SQL_CONSULTA_LANZADOR = "SELECT DISTINCT * \n"
            + "            FROM ( \n"
            + "              SELECT  TRIM(REGEXP_SUBSTR (lanzador, '[^,]+', 1, level)) as lanzador, \n"
            + "                      FECHAFIN,IDPROCESO,NOMBRE,DESCRIPCION,PROGRAMACION,EXCLUIR,PRIORIDAD,\n"
            + "                      ESTADO,IDMANAGER,INTENTOS,INTENTOSMAX,INICIOEJECUCION,FINEJECUCION ,TIPOPROCESAMIENTO\n"
            + "                FROM SGTC_Proceso\n"
            + "                connect by level <= length(regexp_replace(lanzador,'[^,]*'))+1)\n"
            + "            WHERE (FECHAFIN IS NULL OR FECHAFIN > SYSDATE)";
}
