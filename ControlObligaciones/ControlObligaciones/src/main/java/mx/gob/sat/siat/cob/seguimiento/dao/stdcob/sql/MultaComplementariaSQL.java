/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql;

/**
 *
 * @author root
 */
public final class MultaComplementariaSQL {

    private MultaComplementariaSQL(){}
    
    private static final String CAMPOS_SELECT_DOCUMENTOS="select documento.numerocontrol, \n" +
                                                "documento.numerocontrolpadre, \n" +
                                                "documento.fechanotificacion, \n" +
                                                "documento.fechaimpresion, \n" +
                                                "documento.rfc, \n" +
                                                "documento.boid, \n" +
                                                "documento.fechavencimientodocto, \n" +
                                                "documento.idetapavigilancia, \n" +
                                                "documento.fechanolocalizadocontribuyente, \n" +
                                                "documento.idvigilancia,  \n" +
                                                "documento.esultimogenerado, \n" +
                                                "documento.ultimoestado, \n" +
                                                "documento.fecharegistro, \n" +
                                                "documento.considerarenuencia, \n" +
                                                "documento.idadmonlocal, \n" +
                                                "vigilancia.idtipodocumento, \n" +
                                                "documento.identidadfederativa, \n" +
                                                "null as  FECHACARGAARCHIVOS,\n" +
                                                "vigilancia.idtipomedio, \n" +
                                                "null as  FECHANOTRABAJADO \n";
    
    private static final String CAMPOS_GROUP_DOCUMENTOS = "group by \n" +
                                                "documento.numerocontrol, \n" +
                                                "documento.numerocontrolpadre, \n" +
                                                "documento.fechanotificacion, \n" +
                                                "documento.fechaimpresion, \n" +
                                                "documento.rfc, \n" +
                                                "documento.boid, \n" +
                                                "documento.fechavencimientodocto, \n" +
                                                "documento.idetapavigilancia, \n" +
                                                "documento.fechanolocalizadocontribuyente, \n" +
                                                "documento.idvigilancia,  \n" +
                                                "documento.esultimogenerado, \n" +
                                                "documento.ultimoestado, \n" +
                                                "documento.fecharegistro, \n" +
                                                "documento.considerarenuencia, \n" +
                                                "documento.idadmonlocal, \n" +
                                                "vigilancia.idtipodocumento, \n" +
                                                "documento.identidadfederativa, \n" +
                                                "vigilancia.idtipomedio";
    
    public static final String AFECTAR_DETALLES_CON_COMPLEMENTARIA = "merge into sgtt_detalledoc\n"
            + "using\n"
            + "(select numerocontrol, BOID, CLAVEICEP, IDENTIFICADORCUMPLIMIENTO, IMPORTEPAGAR,\n"
            + "FECHAPRESENTACION, TIPODECLARACION  \n"
            + "from\n"
            + "(\n"
                + "select\n"
                + "documento.numerocontrol, cumplimiento.BOID, cumplimiento.CLAVEICEP, cumplimiento.IDENTIFICADORCUMPLIMIENTO,\n"
                + "cumplimiento.IMPORTEPAGAR, cumplimiento.FECHAPRESENTACION, cumplimiento.TIPODECLARACION,\n"
                + "row_number() over(partition by documento.numerocontrol, \n"
                + "cumplimiento.BOID,\n"
                + "cumplimiento.CLAVEICEP order by documento.numerocontrol, \n"
                + "cumplimiento.BOID,\n"
                + "cumplimiento.CLAVEICEP,\n"
                + "cumplimiento.identificadorcumplimiento) as rownumber\n"
                + "from sgtt_documento documento\n"
                    + "join sgtt_detalledoc detalledoc on documento.numerocontrol = detalledoc.numerocontrol\n"            
                        + "and detalledoc.FECHACUMPLIMIENTO is not null\n"
                        + "and detalledoc.IDSITUACIONICEP <> ? \n"
                        + "and detalledoc.TIENEMULTACOMPLEMENTARIA = ? \n"            
                        + "and detalledoc.TIENEMULTAEXTEMPORANEIDAD = ? \n"
                        + "and documento.ultimoEstado != ? \n"
                    + "join sgtt_cumplimiento cumplimiento  on cumplimiento.boid = documento.boid and cumplimiento.claveicep = detalledoc.claveicep\n"
                        + "and months_between(cumplimiento.FECHAPRESENTACION,\n"
                        + "					detalledoc.FECHACUMPLIMIENTO\n"
                        + "				) between 0 and ? \n"
                        + "and nvl(detalledoc.importepagar, 0)<nvl(cumplimiento.IMPORTEPAGAR, 0)\n"
                        + "and (cumplimiento.estadoicepec = 16 "
                            + "or (cumplimiento.estadoicepec >1 and cumplimiento.estadoicepec < 9 )),\n"
                    + "sgtc_tipodec declaracion_normal,  sgtc_tipodec declaracion_complementaria\n"
                   +"where detalledoc.idtipodeclaracion = declaracion_normal.idtipodeclaracion and declaracion_normal.idgrupotipodeclaracion= ? \n"
                        + "and cumplimiento.TIPODECLARACION = declaracion_complementaria.idtipodeclaracion and declaracion_complementaria.idgrupotipodeclaracion= ? "
            + ")\n"
            + "where rownumber=1)\n"
            + "cumplimientos_recibidos on (sgtt_detalledoc.numerocontrol = cumplimientos_recibidos.numerocontrol and sgtt_detalledoc.claveicep = cumplimientos_recibidos.claveicep)\n"
            + "when matched then update\n"
            + "set sgtt_detalledoc.IDCUMPLIMIENTOCOMPL = cumplimientos_recibidos.IDENTIFICADORCUMPLIMIENTO,\n"
            + "sgtt_detalledoc.FECHAPRESENTACIONCOMPL = cumplimientos_recibidos.FECHAPRESENTACION";
    
    public static final String DOCUMENTOS_CON_COMPLEMENTARIA = CAMPOS_SELECT_DOCUMENTOS +
                                            "from sgtt_documento documento  \n" +
                                                "join sgtt_vigilancia vigilancia on vigilancia.idvigilancia=documento.idvigilancia\n" +
                                                    "and vigilancia.idtipomedio <> ?\n" +
                                                "join sgtt_detalledoc detalledoc on  detalledoc.NUMEROCONTROL = documento.NUMEROCONTROL\n" +
                                                    "and detalledoc.FECHAPRESENTACIONCOMPL is not null \n" +
                                                    "and (detalledoc.TIENEMULTACOMPLEMENTARIA is null OR detalledoc.TIENEMULTACOMPLEMENTARIA=0)\n" +
                                                    "and documento.ultimoEstado != ? \n" +
                                            CAMPOS_GROUP_DOCUMENTOS;
    
    public static final String DOCUMENTOS_CON_COMPLEMENTARIA_VIGILANCIA_LOCAL = CAMPOS_SELECT_DOCUMENTOS +
                                            "from sgtt_documento documento  \n" +
                                                "join sgtt_vigilancia vigilancia on vigilancia.idvigilancia=documento.idvigilancia\n" +
                                                     "and vigilancia.idvigilancia= :idVigilancia \n" +
                                                     "and documento.idadmonlocal= :idAdmonlocal \n" +
                                                "join sgtt_detalledoc detalledoc on  detalledoc.NUMEROCONTROL = documento.NUMEROCONTROL\n" +
                                                    "and detalledoc.FECHAPRESENTACIONCOMPL is not null \n" +
                                                    "and (detalledoc.TIENEMULTACOMPLEMENTARIA is null OR detalledoc.TIENEMULTACOMPLEMENTARIA=0)\n" +
                                                    "and documento.ultimoEstado != :ultimoEstado \n" +
                                            CAMPOS_GROUP_DOCUMENTOS;
    
    public static final String ACTUALIZA_DETALLE_DOCUMENTOS = "update sgtt_detalledoc set\n" +
                                            "tienemultacomplementaria  = 1\n" +
                                            "where numeroControl in ( :documentos )\n" +
                                            "and TIENEMULTACOMPLEMENTARIA = 0\n" +
                                            "and FECHAPRESENTACIONCOMPL is not null";
    public static final String CONTEO_AGRUPADO_DETALLES_COMPLEMENTARIA = "select count(1) as conteo, documento.IDADMONLOCAL as admonLocal, documento.IDVIGILANCIA as vigilancia\n" +
                                                    "from sgtt_documento documento \n"
                                                        + "join sgtt_detalledoc detalledoc on documento.NUMEROCONTROL = detalledoc.numerocontrol\n" +
                                                       "join sgtc_tipodec declaracion_normal  on detalledoc.idtipodeclaracion = declaracion_normal.idtipodeclaracion and declaracion_normal.idgrupotipodeclaracion= :declaracionNormal\n" +
                                                    "where detalledoc.FECHACUMPLIMIENTO is not null\n" +
                                                        "and detalledoc.IDSITUACIONICEP <> :situacionIcep \n" +
                                                        "and detalledoc.TIENEMULTACOMPLEMENTARIA = :tieneMultaComplementaria \n" +
                                                        "and detalledoc.TIENEMULTAEXTEMPORANEIDAD = :tieneMultaExtemporaneidad \n" +
                                                        "and documento.ultimoEstado != :ultimoEstado\n" +
                                                    "group by documento.IDADMONLOCAL, documento.IDVIGILANCIA";
    
    public static final  String DETALLES_PARA_COMPLEMENTARIA = "select numerocontrol as numero_control, boid, claveicep as CLAVE_ICEP, ultimoestado, " +
                                        "importepagar, fechacumplimiento,\n" +
                                        "null as descripcion_vigilancia,\n" +
                                        "null as numero_carga,\n" +
                                        "null as rfc,\n" +
                                        "null as id_tipo_documento\n" +
                                    "from \n" +
                                        "(select /*+ FIRST_ROWS(1500) */ documento.numerocontrol, documento.boid, detalledoc.claveicep, documento.ultimoestado, \n" +
                                            "detalledoc.importepagar, detalledoc.fechacumplimiento, \n" +
                                        "ROW_NUMBER() over (order by documento.numerocontrol,detalledoc.claveicep) num\n" +
                                        "from sgtt_documento documento join sgtt_detalledoc detalledoc on documento.NUMEROCONTROL = detalledoc.numerocontrol \n" +
                                            "join sgtc_tipodec declaracion_normal  on detalledoc.idtipodeclaracion = declaracion_normal.idtipodeclaracion and declaracion_normal.idgrupotipodeclaracion= :declaracionNormal\n" +
                                        "where documento.idvigilancia=:vigilancia and documento.idadmonlocal=:admonlocal\n" +
                                                 "and detalledoc.FECHACUMPLIMIENTO is not null\n" +
                                                 "and detalledoc.IDSITUACIONICEP <> :situacionIcep \n" +
                                                 "and detalledoc.TIENEMULTACOMPLEMENTARIA = :tieneMultaComplementaria \n" +
                                                 "and detalledoc.TIENEMULTAEXTEMPORANEIDAD = :tieneMultaExtemporaneidad \n" +
                                                 "and documento.ultimoEstado != :ultimoEstado\n" +
                                    ")\n" +
                                    "where num >= :inicio and num < :fin";
    
    public static final String ACTUALIZAR_DETALLES_COMPLEMENTARIA ="update  sgtt_detalledoc set\n" +
            "FECHAPRESENTACIONCOMPL= ?,\n" +
            "IDCUMPLIMIENTOCOMPL = ? \n" +
            "where numerocontrol= ? \n" +
            "and claveicep= ?";
}
