/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.dbd2.sql;

/**
 *
 * @author root
 */
public interface HistoricoCumplimientoSQL {
   String CONSULTA_CUMPLIMIENTO = "SELECT    \n" +
"                HISTORICOCUMP.C_IDE_ICDOENN1 AS BOID,\n" +
"                HISTORICOCUMP.C_ICE_ICEP1 AS CLAVEICEP,\n" +
"                HISTORICOCUMP.C_COB_CUMPLIM1 AS IDENTIFICADORCUMPLIMIENTO,\n" +
"                HISTORICOCUMP.I_COB_IPMAPGO1 AS IMPORTEAPAGAR,\n" +
"                HISTORICOCUMP.F_OBL_FPERCEH1 AS FECHAPRESENTACION,\n" +
"                HISTORICOCUMP.C_COB_TDIEPCO1 AS TIPODECLARACION,\n" +
"                DATOSICEP.C_IDE_EISCTPA1 AS EDOICEP,\n" +
"                DATOSICEP.C_IDE_EVSITGA1 AS EDOVIGILABLE,\n" +
"                HISTORICOCUMP.F_OBL_AFMUEAD1 AS FECHAMANTENIMIENTO\n" +
"FROM dm_impin.DD_COB_COUBMLP1 AS HISTORICOCUMP\n" +
"                INNER JOIN dm_impin.DD_ICE_ICCOENP1 AS DATOSICEP on (\n" +
"                            HISTORICOCUMP.C_IDE_ICDOENN1 = DATOSICEP.C_IDC_ICDOENN1\n" +
"                            AND HISTORICOCUMP.C_ICE_ICEP1 = DATOSICEP.C_ICE_ICEP1\n" +
"                            AND HISTORICOCUMP.F_OBL_AFMUEAD1 = date(to_date('#{jobParameters['fecha']}','YYYY-MM-DD'))\n" +
"                ) WITH ur";
    
    String CONSULTA_CUMPLIMIENTO_PAGINADO = "select\n" +
                                        "BOID,\n" +
                                        "CLAVEICEP, \n" +
                                        "IDENTIFICADORCUMPLIMIENTO, \n" +
                                        "IMPORTEAPAGAR, \n" +
                                        "FECHAPRESENTACION, \n" +
                                        "TIPODECLARACION, \n" +
                                        "EDOICEP, \n" +
                                        "EDOVIGILABLE,\n" +
                                        "FECHAMANTENIMIENTO\n" +
                                    "from (\n" +
                                            "SELECT\n" +
                                                "row_number() over (order by HISTORICOCUMP.C_COB_CUMPLIM1) as FILA,\n" +
                                                "HISTORICOCUMP.C_IDE_ICDOENN1 AS BOID, \n" +
                                                "HISTORICOCUMP.C_ICE_ICEP1 AS CLAVEICEP, \n" +
                                                "HISTORICOCUMP.C_COB_CUMPLIM1 AS IDENTIFICADORCUMPLIMIENTO, \n" +
                                                "HISTORICOCUMP.I_COB_IPMAPGO1 AS IMPORTEAPAGAR, \n" +
                                                "HISTORICOCUMP.F_OBL_FPERCEH1 AS FECHAPRESENTACION, \n" +
                                                "HISTORICOCUMP.C_COB_TDIEPCO1 AS TIPODECLARACION, \n" +
                                                "DATOSICEP.C_IDE_EISCTPA1 AS EDOICEP,\n" +
                                                "DATOSICEP.C_IDE_EVSITGA1 AS EDOVIGILABLE,\n" +
                                                "HISTORICOCUMP.F_OBL_AFMUEAD1 AS FECHAMANTENIMIENTO\n" +
                                            "FROM dm_impin.DD_COB_COUBMLP1 AS HISTORICOCUMP \n" +
                                                "INNER JOIN dm_impin.DD_ICE_ICCOENP1 AS DATOSICEP on ( \n" +
                                                    "HISTORICOCUMP.C_IDE_ICDOENN1 = DATOSICEP.C_IDC_ICDOENN1 \n" +
                                                    "AND HISTORICOCUMP.C_ICE_ICEP1 = DATOSICEP.C_ICE_ICEP1 \n" +
                                                    "AND HISTORICOCUMP.F_OBL_AFMUEAD1 = date(to_date('#{jobParameters['fecha']}','YYYY-MM-DD')) \n" +
                                                ")\n" +
                                    ") as HISTORICO_DIA\n" +
                                    "WHERE FILA BETWEEN #{jobParameters['filaInicio']} and #{jobParameters['filaFin']} WITH ur";
 
    String CONTAR_CUMPLIMIENTOS = "SELECT    \n" +
                                    "count(1)\n" +            
                                  "FROM dm_impin.DD_COB_COUBMLP1 AS HISTORICOCUMP\n" +
                                    "INNER JOIN dm_impin.DD_ICE_ICCOENP1 AS DATOSICEP on (\n" +
                                    "HISTORICOCUMP.C_IDE_ICDOENN1 = DATOSICEP.C_IDC_ICDOENN1\n" +
                                    "AND HISTORICOCUMP.C_ICE_ICEP1 = DATOSICEP.C_ICE_ICEP1\n" +
                                    "AND HISTORICOCUMP.F_OBL_AFMUEAD1 = ?\n" +
                                    ") WITH ur";

    String CONSULTAR_PAGOS_ICEPS ="SELECT * FROM (\n" +
        "SELECT ROW_NUMBER() OVER (ORDER BY CUMPLIMIENTO.FECHAPRESENTACION desc) AS ROWMIN,\n" +
        "       PADRON.C_IDC_ICDOENN1 AS BOID,\n" +
        "       PADRON.C_ICE_ICEP1 AS CLAVEICEPHISTORICOPAGO,\n" +
        "       PADRON.D_ICE_EFJIESR1 AS EJERCICIO,\n" +
        "       PADRON.C_ICE_IPDEERN1 AS PERIODO,\n" +
        "       PADRON.C_ICE_IPDEERN2 AS PERIODICIDAD,\n" +
        "       CUMPLIMIENTO.IDENTIFICADORCUMPLIMIENTO,\n" +
        "       CUMPLIMIENTO.IMPORTEACARGO,\n" +
        "       CUMPLIMIENTO.FECHAPRESENTACION,\n" +
        "       CUMPLIMIENTO.TIPODECLARACION,\n" +
        "       DATOSDECLARACION.N_NUMERO_OPERACION AS NUMOPERACION\n" +
        "FROM DM_IMPIN.DD_ICE_ICCOENP1 AS PADRON\n" +
        "INNER JOIN (SELECT HISTORICOCUMP.C_IDE_ICDOENN1 AS BOID,\n" +
        "           HISTORICOCUMP.C_ICE_ICEP1 AS CLAVEICEP,\n" +
        "           HISTORICOCUMP.C_COB_CUMPLIM1 AS IDENTIFICADORCUMPLIMIENTO,    \n" +
        "           HISTORICOCUMP.I_COB_ICMAPRO1 AS IMPORTEACARGO,   \n" +
        "           HISTORICOCUMP.F_OBL_FPERCEH1 AS FECHAPRESENTACION,      \n" +
        "           HISTORICOCUMP.C_COB_TDIEPCO1 AS TIPODECLARACION,   \n" +
        "           ROW_NUMBER() OVER (PARTITION BY HISTORICOCUMP.C_IDE_ICDOENN1,\n" +
        "                              HISTORICOCUMP.C_ICE_ICEP1 ORDER BY HISTORICOCUMP.C_COB_CUMPLIM1) as numero\n" +
        "FROM dm_impin.DD_COB_COUBMLP1 AS HISTORICOCUMP \n" +
        "  WHERE HISTORICOCUMP.C_IDE_ICDOENN1 = ? -- PARAMETRO VARIABLE: Boid, SE CONSULTA PUNTUALMENTE POR UN CONTRIBUYENTE EN ESPECIFICO\n" +
        "    AND HISTORICOCUMP.C_COB_TDIEPCO1 IN (1,2,3,41,42,43,51,52,53,61,62,63,65,72,73,80)\n" +
        "    AND HISTORICOCUMP.N_MATERIA IN (1,2)\n" +
        "    AND (HISTORICOCUMP.F_OBL_FPERCEH1 IS NOT NULL OR HISTORICOCUMP.I_COB_ICMAPRO1 IS NOT NULL OR DATOSDECLARACION.N_NUMERO_OPERACION IS NOT NULL)\n"+    
        "    AND HISTORICOCUMP.F_OBL_FPERCEH1 > (SELECT DATE(CURRENT TIMESTAMP - 5 YEAR) FROM SYSIBM.SYSDUMMY1)\n" +
        ") AS CUMPLIMIENTO\n" +
        "ON cumplimiento.boid=padron.C_IDC_ICDOENN1 and PADRON.C_ICE_ICEP1=cumplimiento.claveicep and numero=1 \n" +
        "  AND PADRON.C_OBL_COLBALV1 = ?	-- PARAMETRO VARIABLE: \"IdObligacion\", SE CONSULTA PUNTUALMENTE POR UN CONTRIBUYENTE EN ESPECIFICO\n" +
        "  AND (PADRON.C_IDE_EISCTPA1 BETWEEN 2 AND 10 OR PADRON.C_IDE_EISCTPA1 = 16)\n" +
        "INNER JOIN dm_impin.DD_ICE_ECECOSE1 AS DATOSDECLARACION\n" +
        "  ON  CUMPLIMIENTO.BOID = DATOSDECLARACION.C_IDE_ICDOENN1 \n" +
        "  AND CUMPLIMIENTO.CLAVEICEP = DATOSDECLARACION.C_ICE_ICEP1 \n" +
        "  AND CUMPLIMIENTO.IDENTIFICADORCUMPLIMIENTO = DATOSDECLARACION.C_COB_CUMPLIM1\n" +
        "  AND PADRON.C_IDE_EISCTPA1 = DATOSDECLARACION.C_ICE_ETSRTAA1\n" +
        ") AS PAGOS\n" +
        "WHERE ROWMIN BETWEEN 1 and 6 \n" +
        "WITH ur"; 


    String IN_BOID_ICEP_CUMPLIMIENTO = "InBoidIcepCumplimiento";

    String IN_BOID_ICEP_PADRON = "InBoidIcepPadron";
    
    String TIPO_DECLARACION = "tipoDeclaracion";
    
    String VALIDAR_CUMPLIMIENTOS = "SELECT       PADRON.C_IDC_ICDOENN1 BOID,\n"
            + "             PADRON.C_ICE_ICEP1 CLAVEICEP,\n"
            + "             PADRON.C_IDE_EVSITGA1 EDOVIGILABLE,\n"
            + "             PADRON.C_IDE_EISCTPA1 AS EDOICEP,\n"
            + "             IDENTIFICADORCUMPLIMIENTO,    \n"
            + "             IMPORTEAPAGAR,   \n"
            + "             FECHAPRESENTACION,      \n"
            + "             TIPODECLARACION   \n"
            + "FROM DM_IMPIN.DD_ICE_ICCOENP1 AS PADRON\n"
            + "LEFT JOIN (SELECT    HISTORICOCUMP.C_IDE_ICDOENN1 AS BOID,\n"
            + "           HISTORICOCUMP.C_ICE_ICEP1 AS CLAVEICEP,\n"
            + "           HISTORICOCUMP.C_COB_CUMPLIM1 AS IDENTIFICADORCUMPLIMIENTO,    \n"
            + "           HISTORICOCUMP.I_COB_IPMAPGO1 AS IMPORTEAPAGAR,   \n"
            + "           HISTORICOCUMP.F_OBL_FPERCEH1 AS FECHAPRESENTACION,      \n"
            + "           HISTORICOCUMP.C_COB_TDIEPCO1 AS TIPODECLARACION,   \n"
            + "           ROW_NUMBER() OVER (PARTITION BY HISTORICOCUMP.C_IDE_ICDOENN1,\n"
            + "                              HISTORICOCUMP.C_ICE_ICEP1 ORDER BY HISTORICOCUMP.C_COB_CUMPLIM1) as numero\n"
            + "FROM dm_impin.DD_COB_COUBMLP1 AS HISTORICOCUMP \n"
            + "    WHERE "+IN_BOID_ICEP_CUMPLIMIENTO+" ) cumplimiento\n"
            + "ON cumplimiento.boid=padron.C_IDC_ICDOENN1 and PADRON.C_ICE_ICEP1=cumplimiento.claveicep and numero=1 \n"
            + "WHERE "+IN_BOID_ICEP_PADRON
            + " WITH ur";
    
    String VALIDAR_CUMPLIMIENTOS_TIPOSDECLARACION = " "+"SELECT       PADRON.C_IDC_ICDOENN1 BOID,\n"
            + "             PADRON.C_ICE_ICEP1 CLAVEICEP,\n"
            + "             PADRON.C_IDE_EVSITGA1 EDOVIGILABLE,\n"
            + "             PADRON.C_IDE_EISCTPA1 AS EDOICEP,\n"
            + "             IDENTIFICADORCUMPLIMIENTO,    \n"
            + "             IMPORTEAPAGAR,   \n"
            + "             FECHAPRESENTACION,      \n"
            + "             TIPODECLARACION   \n"
            + "FROM DM_IMPIN.DD_ICE_ICCOENP1 AS PADRON\n"
            + "JOIN (SELECT    HISTORICOCUMP.C_IDE_ICDOENN1 AS BOID,\n"
            + "           HISTORICOCUMP.C_ICE_ICEP1 AS CLAVEICEP,\n"
            + "           HISTORICOCUMP.C_COB_CUMPLIM1 AS IDENTIFICADORCUMPLIMIENTO,    \n"
            + "           HISTORICOCUMP.I_COB_IPMAPGO1 AS IMPORTEAPAGAR,   \n"
            + "           HISTORICOCUMP.F_OBL_FPERCEH1 AS FECHAPRESENTACION,      \n"
            + "           HISTORICOCUMP.C_COB_TDIEPCO1 AS TIPODECLARACION,   \n"
            + "           ROW_NUMBER() OVER (PARTITION BY HISTORICOCUMP.C_IDE_ICDOENN1,\n"
            + "                              HISTORICOCUMP.C_ICE_ICEP1 ORDER BY HISTORICOCUMP.C_COB_CUMPLIM1) as numero\n"
            + "FROM dm_impin.DD_COB_COUBMLP1 AS HISTORICOCUMP \n"
            + "    WHERE "+TIPO_DECLARACION+" "+IN_BOID_ICEP_CUMPLIMIENTO+" ) cumplimiento\n"
            + "ON cumplimiento.boid=padron.C_IDC_ICDOENN1 and PADRON.C_ICE_ICEP1=cumplimiento.claveicep and numero=1 \n"
            + "WHERE "+IN_BOID_ICEP_PADRON
            + " WITH ur";
    
    String GUARDAR_HISTORICO_PAGOS_LIQ = "insert into sgtt_detalleliq (numerocontrol, \n" + 
        "    claveicep,\n" + 
        "    identificadorcumplimiento,\n" + 
        "    importecargo,\n" + 
        "    fechapresentacion,\n" + 
        "    idtipodeclaracion,\n" + 
        "    numerooperacion,\n" + 
        "    escantidaddetmayor," +
        "    claveicephistpago,\n" +
        "    ejercicio,\n" +
        "    idperiodo,\n" +
        "    idperiodicidad) "+
        "    values( ?, ?, ?, ?,\n" + 
        "    to_date (?,'dd-mm-yyyy'),\n" + 
        "    ?, ?, ?, ?, ?, ?, ?)";
}

