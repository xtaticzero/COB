package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql;

/**
 * Interface SQL consultas Control de obligaciones - Seguimiento
 *
 * @author
 *
 */
public interface SQLOracleSeguimiento {

    // Querys
    String CONSULTAR_TIPODOCETAPA_BY_IDTIPODOC= "SELECT T.IDTIPODOCUMENTO, T.IDETAPAVIGILANCIA, T.DIASVENCIMIENTO, T.NOMBREDOCUMENTOETAPA"
            + " FROM SGTA_TIPODOCETAPA T"
            + " WHERE  FECHAFIN is null and IDTIPODOCUMENTO = {0}";
    String CONSULTAR_ESTADO_SEGUIMIENTO_ACTUAL_POR_RFC = "SELECT enejecucion FROM sgtb_ejecucionseg WHERE idejecucion=(SELECT max(idejecucion) FROM sgtb_ejecucionseg)";
    String ACTUALIZAR_REGISTRO_EJECUCION_SEGUIMIENTO= "INSERT INTO sgtb_ejecucionseg "
            + "(IDEJECUCION,"
            + "FECHAAFECTACION,"
            + "ENEJECUCION,"
            + "RFCUSUARIO)"
            + " VALUES (SGTQ_EJECUCIONSEG.NEXTVAL,sysdate, {0} ,'{1}')";
    String  INSERTAR_REGISTRO_PLANTILLA_ARCA = "INSERT INTO SGTT_PLANTILLAARCA "
            + "(IDPLANTILLA,"
            + "IDPLANTILLAARCA,"
            + "FECHAINICIO,"
            + "FECHAFIN,"
            + "DESCRIPCION,"
            + "ESMULTA,"
            + "IDTIPODOCUMENTO,"
            + "IDETAPAVIGILANCIA,"
            + "IDTIPOMEDIO,"
            + "IDTIPOFIRMA,"
            + "CONSTANTERESOLMOTIVO, DIAS, IDNIVELEMISION, " 
            + "IDCARGOADMTVO)"
            + " VALUES (SGTQ_PLANTILLAARCA.NEXTVAL,?, sysdate, null, ?, ?, ?, ?, ?, ?, ?,?,?,?)";
    String ACTUALIZAR_ESTADO_PLANTILLA_ARCA= "UPDATE SGTT_PLANTILLAARCA SET FECHAFIN=sysdate WHERE IDPLANTILLA={0}";
    
    String ELIMINAR_PLANTILLAS_REPETIDAS= "UPDATE SGTT_PLANTILLAARCA SET FECHAFIN=sysdate WHERE IDTIPODOCUMENTO=? " +
                                            "AND IDNIVELEMISION=? " +
                                            "AND IDCARGOADMTVO=? "+
                                            "AND IDETAPAVIGILANCIA=? " +
                                            "AND IDTIPOFIRMA=? " +
                                            "AND IDTIPOMEDIO=? " +
                                            "AND ESMULTA = ? " +
                                            "AND nvl(CONSTANTERESOLMOTIVO, '-') = nvl(?, '-') " +
                                            "AND nvl(DIAS, 0) = nvl(?, 0) " +
                                            "AND FECHAFIN IS NULL ";
    
    
    String BUSCAR_PLANTILLA_REQARCA_POR_PARAMETROS= "select * from SGTT_PLANTILLAARCA where idtipodocumento={0} and idetapavigilancia={1} and esplantilladiot={2} and idtipomedio={3} and idtipofirma={4} and idmodalidaddocumento={5}";
    String BUSCAR_PLANTILLA_LIQ_ARCA_POR_PARAMETROS= "select * from SGTT_PLANTILLAARCA where idtipodocumento={0} and idetapavigilancia={1} and esplantilladiot={2} and idtipomedio={3} and idtipofirma={4} and idmodalidaddocumento={5} and dias={6}";
    String BUSCAR_PLANTILLA_MULTA_ARCA_POR_PARAMETROS= "select * from SGTT_PLANTILLAARCA where idtipodocumento={0} and idetapavigilancia={1} and esplantilladiot={2} and idtipomedio={3} and idtipofirma={4} and idmodalidaddocumento={5} and constanteresolmotivo='{6}' ";
    
    String BUSCAR_REGISTRO_PLANTILLA_ARCA_INDIVIDUAL="select IDCARGOADMTVO, null as descripcionadmtvo, null as idnivelemision, null as nombre,"
                                            + "idplantilla, descripcion, dias, constanteresolmotivo, "
                                            + "null as descresolmotivo, null as nombretipodocumento,"
                                            + "null as idtipoDocumento,null as nombredocumentoetapa,"
                                            + "null as idetapavigilancia, null as nombretipomedio,"
                                            + "null as idTipoMedio, null as nombrefirma, null as idfirmatipo " +
                                       "from SGTT_PLANTILLAARCA " +
                                        "WHERE IDTIPODOCUMENTO=? " +
                                            "AND IDNIVELEMISION=? " +
                                            "AND IDCARGOADMTVO=? "+
                                            "AND IDETAPAVIGILANCIA=? " +
                                            "AND IDTIPOFIRMA=? " +
                                            "AND IDTIPOMEDIO=? " +
                                            "AND ESMULTA = ? " +
                                            "AND nvl(CONSTANTERESOLMOTIVO, '-') = nvl(?, '-') " +
                                            "AND nvl(DIAS, 0) = nvl(?, 0) " +
                                            "AND FECHAFIN IS NULL ";
    
    String BUSCAR_REGISTROS_PLANTILLA_ARCA_COB= "select pl.IDCARGOADMTVO, cg.descripcion as descripcionadmtvo, ne.idnivelemision,ne.nombre,pl.idplantilla, pl.descripcion, pl.dias, pl.constanteresolmotivo, rm.nombremultacob as descresolmotivo, tp.nombre as nombretipodocumento,tp.idtipoDocumento,etapa.nombredocumentoetapa as nombredocumentoetapa,etapa.idetapavigilancia, tm.nombre as nombretipomedio,tm.idTipoMedio, ft.nombre as nombrefirma, ft.idfirmatipo\n" + 
    "                 from sgtt_plantillaarca pl \n" + 
    "                 inner join sgtc_cargoadmtvo cg on cg.IDCARGOADMTVO=pl.IDCARGOADMTVO\n" + 
    "                 inner join sgtc_tipodocumento tp on pl.idtipodocumento = tp.idtipodocumento\n" + 
    "                inner join sgtc_nivelemision ne on ne.idnivelemision=pl.idnivelemision\n" + 
    "                left join sgtc_multacob rm on rm.constanteresolmotivo=pl.constanteresolmotivo\n" + 
    "                inner join sgta_tipodocetapa etapa on (tp.idtipodocumento = etapa.idtipodocumento and pl.idetapavigilancia = etapa.idetapavigilancia)\n" + 
    "                inner join SGTC_TIPOMEDIO tm on pl.idTipoMedio=tm.idTipoMedio\n" + 
    "                left join ADMC_FIRMATIPO ft on ft.idfirmatipo=pl.idtipofirma \n" + 
    "                where pl.fechafin is null order by pl.idplantilla desc";
    
    String BUSCAR_PLANTILLAS_DB_ARCA= "select id, fcDescripcionCorta from SIATControlDeObligaciones.T_CAT_DOCUMENTOPLANTILLA";
    String BUSCAR_REGISTROS_PLANTILLA= "select pl.idplantilla,pl.rutaarchivo,pl.descripcion, etapa.nombredocumentoetapa,tp.nombre from sgtt_plantilla pl INNER JOIN sgtc_tipodocumento tp ON pl.idtipodocumento = tp.idtipodocumento INNER JOIN sgta_tipodocetapa etapa ON (tp.idtipodocumento = etapa.idtipodocumento and pl.idetapavigilancia = etapa.idetapavigilancia) where pl.fechafin is null order by pl.idplantilla desc";
    String BUSCAR_PARAMETROS_VIGENTES = "select tp.nombre as tipoDocumento,etapa.nombredocumentoetapa as etapa, pl.diasvencimiento as diasVencimiento from SGTA_TipoDocEtapa pl "
            + "INNER JOIN sgtc_tipodocumento tp ON pl.idtipodocumento = tp.idtipodocumento "
            + "INNER JOIN sgta_tipodocetapa etapa ON (tp.idtipodocumento = etapa.idtipodocumento "
            + "and pl.idetapavigilancia = etapa.idetapavigilancia) where pl.fechafin is null";
    String ACTUALIZAR_PARAMETROS_POR_TIPO_DOCUMENTO= "update SGTA_TipoDocEtapa set diasvencimiento={0} where idtipodocumento={1} and idetapavigilancia={2}";
    String OBTEN_DIAS_VENCIMIENTO = "SELECT T.IDTIPODOCUMENTO, T.IDETAPAVIGILANCIA, T.DIASVENCIMIENTO, T.NOMBREDOCUMENTOETAPA FROM"
            + " SGTA_TIPODOCETAPA T"
            + " WHERE T.IDTIPODOCUMENTO = {0} AND T.IDETAPAVIGILANCIA = {1}";
    //Llamadas a Store Procedure
}