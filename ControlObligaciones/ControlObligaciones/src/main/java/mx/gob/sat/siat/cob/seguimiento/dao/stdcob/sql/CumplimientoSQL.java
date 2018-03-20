package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.SituacionIcepEnum;

public interface CumplimientoSQL {

    String INSERT = "INSERT INTO SGTT_CUMPLIMIENTO (BOID,"
            + "CLAVEICEP,"
            + "IDENTIFICADORCUMPLIMIENTO,"
            + "IMPORTEPAGAR,"
            + "FECHAPRESENTACION,"
            + "TIPODECLARACION) VALUES(?,?,?,?,?,?)";
    String INSERT_BATCH = " INSERT INTO SGTT_CUMPLIMIENTO (BOID,  "
            + "    CLAVEICEP,   "
            + "    IDENTIFICADORCUMPLIMIENTO,  "
            + "    IMPORTEPAGAR,  "
            + "    FECHAPRESENTACION,   "
            + "    TIPODECLARACION,"
            + "    ESTADOICEPEC,"
            + "    FECHAMANTENIMIENTO) VALUES(:bOID,  "
            + "                            :claveICEP,  "
            + "                            :identificadorCumplimiento,  "
            + "                            :importePagar,  "
            + "                            :fechaPresentacion,  "
            + "                            :tipoDeclaracion,"
            + "                            :estadoIcep,"
            + "                            :fechaMantenimiento)";
    
    String CONSULTA_BOID_PARAMETROS = "";
    String BORRAR_CUMPLIMIENTOS_POR_BOID = " delete from sgtt_cumplimiento where boid = ? ";
    String BORRAR_CUMPLIMIENTOS_NO_OMISOS = " delete from sgtt_cumplimiento where boid not in ("
            + " select distinct documento.boid "
            + " from sgtt_documento documento  "
            + " join sgtt_detalledoc detalledoc on documento.numerocontrol = detalledoc.numerocontrol  "
            + " join sgtt_cumplimiento cumplimiento  on "
            + " cumplimiento.boid = documento.boid and cumplimiento.claveicep = detalledoc.claveicep  "
            + " group by documento.numerocontrol, documento.numerocontrolpadre, "
            + " documento.fechanotificacion, documento.fechaimpresion, documento.rfc, documento.boid,  "
            + " documento.fechavencimientodocto, documento.idetapavigilancia, "
            + " documento.fechanolocalizadocontribuyente, documento.idvigilancia,  "
            + " documento.esultimogenerado, documento.ultimoestado, documento.fecharegistro, documento.considerarenuencia  "
            + ")";
    //+ " where documento.esultimogenerado=1  "
    String AFECTAR_DETALLES_CON_CUMPLIMIENTO = "merge into sgtt_detalledoc\n"
            + "using\n"
            + "(select numerocontrol, BOID, CLAVEICEP, IDENTIFICADORCUMPLIMIENTO, IMPORTEPAGAR,\n"
            + "FECHAPRESENTACION,  TIPODECLARACION,  SITUACION_ICEP, ESTADOICEPEC \n"
            + "from\n"
            + "(\n"
            + "select\n"
            + "documento.numerocontrol, cumplimiento.BOID, cumplimiento.CLAVEICEP, cumplimiento.IDENTIFICADORCUMPLIMIENTO,\n"
            + "cumplimiento.IMPORTEPAGAR, cumplimiento.FECHAPRESENTACION, cumplimiento.TIPODECLARACION,\n"
            + "case when detalledoc.idsituacionicep = 2 then \n"
            + "     detalledoc.idsituacionicep  \n"
            + "else  \n"
            + "     case when documento.ultimoestado in (:estados) then 0 else 1 end  \n"
            + "end  \n"
            + "as SITUACION_ICEP, cumplimiento.ESTADOICEPEC, \n"
            + "row_number() over(partition by documento.numerocontrol, \n"
            + "cumplimiento.BOID,\n"
            + "cumplimiento.CLAVEICEP order by documento.numerocontrol, \n"
            + "cumplimiento.BOID,\n"
            + "cumplimiento.CLAVEICEP,\n"
            + "cumplimiento.identificadorcumplimiento) as rownumber\n"
            + "from sgtt_documento documento\n"
            + "join sgtt_detalledoc detalledoc on documento.numerocontrol = detalledoc.numerocontrol\n"
                + "and detalledoc.fechacumplimiento is null \n"
            + "join sgtt_cumplimiento cumplimiento  on cumplimiento.boid = documento.boid and cumplimiento.claveicep = detalledoc.claveicep\n"
                + "and cumplimiento.FECHAPRESENTACION is not null and (cumplimiento.estadoicepec = 16 "
                    + "or (cumplimiento.estadoicepec >1 and cumplimiento.estadoicepec < 11 ))\n"
            + ")\n"
            + "where rownumber=1) \n"
            + "cumplimientos_recibidos on (sgtt_detalledoc.numerocontrol = cumplimientos_recibidos.numerocontrol and sgtt_detalledoc.claveicep = cumplimientos_recibidos.claveicep)\n"
            + "when matched then update\n"
            + "set sgtt_detalledoc.importepagar = cumplimientos_recibidos.IMPORTEPAGAR,\n"
            + "sgtt_detalledoc.fechacumplimiento = cumplimientos_recibidos.FECHAPRESENTACION,\n"
            + "sgtt_detalledoc.idtipodeclaracion = cumplimientos_recibidos.TIPODECLARACION,\n"
            + "sgtt_detalledoc.idcumplimiento = cumplimientos_recibidos.IDENTIFICADORCUMPLIMIENTO,\n"
            + "sgtt_detalledoc.idsituacionicep = cumplimientos_recibidos.SITUACION_ICEP, \n"
            + "sgtt_detalledoc.estadoicepec = cumplimientos_recibidos.ESTADOICEPEC";
    
    String OBTENER_MAXIMO_IDENTIFICADOR_CUMPLIMIENTO = "select nvl(max(identificadorcumplimiento), 0) from sgtt_cumplimiento";
    
    String ELIMINAR_CUMPLIMIENTOS = "delete from sgtt_cumplimiento";
    
    String TRUNCATE_TABLA_CUMPLIMIENTOS = "PKG_COB.SGTSP_BORRACUMPLIMIENTOS";
    
    String ELIMINAR_CUMPLIMIENTOS_FECHA_MTO = "delete from sgtt_cumplimiento where fechamantenimiento = ?";
    
    String DETALLES_INCUMPLIDOS = "select numerocontrol as numero_control, boid, claveicep as CLAVE_ICEP, ultimoestado,\n" +
                                        "null as descripcion_vigilancia,\n" +
                                        "null as numero_carga,\n" +
                                        "null as rfc,\n" +
                                        "null as id_tipo_documento\n" +
                                    "from \n" +
                                        "(select /*+ FIRST_ROWS(1500) */ documento.numerocontrol, documento.boid, detalledoc.claveicep, documento.ultimoestado, \n" +
                                        "ROW_NUMBER() over (order by documento.numerocontrol,detalledoc.claveicep) num\n" +
                                        "from sgtt_documento documento join sgtt_detalledoc detalledoc on documento.NUMEROCONTROL = detalledoc.numerocontrol \n" +
                                        "where detalledoc.fechacumplimiento is null and documento.idvigilancia=:vigilancia and documento.idadmonlocal=:admonlocal)\n" +
                                    "where num >= :inicio and num < :fin";
    
    String CONTEO_AGRUPADO_DETALLES_INCUMPLIDOS = "select count(1) as conteo, documento.IDADMONLOCAL as admonLocal, documento.IDVIGILANCIA as vigilancia\n" +
                                                    "from sgtt_documento documento join sgtt_detalledoc detalledoc on documento.NUMEROCONTROL = detalledoc.numerocontrol\n" +
                                                     "where detalledoc.fechacumplimiento is null \n" +
                                                    "group by documento.IDADMONLOCAL, documento.IDVIGILANCIA";
    String ACTUALIZAR_DETALLES_CUMPLIDOS ="update  sgtt_detalledoc set\n" +
            "fechacumplimiento= ?,\n" +
            "idsituacionicep= case when idsituacionicep = " + SituacionIcepEnum.CANCELADO_POR_MOVIMIENTOS_PADRON.getValor() + " then idsituacionicep else ? end,\n" + 
            "importepagar= ?, \n" +
            "idtipodeclaracion= ?, \n" +
            "idcumplimiento = ?, \n" +
             "estadoicepec = ?  \n" +
            "where numerocontrol= ? \n" +
            "and claveicep= ?";
}
