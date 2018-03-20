package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql;

/**
 *
 * @author root
 */
public interface VigilanciaAlSQL {

    String CONSULTAR_VIGILANCIA = "select \n"
            + "                                 distinct v.fechacargaarchivos as fecha_carga,\n"
            + "                                 dv.descripcion as descripcion_vigilancia ,\n"
            + "                                 td.nombre as tipo_documento,\n"
            + "                                 tm.nombre as tipo_medio,\n"
            + "                                 tm.idtipomedio as id_tipo_medio,\n"
            + "                                 tf.nombre as tipo_firma,\n"
            + "                                 v.idnivelemision as id_nivel_emision,\n"
            + "                                 ne.nombre as nivel_emision,\n"
            + "                                 ca.nombre as cargo_administrativo,\n"
            + "                                 v.fechacorte as fecha_corte\n"
            + "                          from sgtt_vigilancia v\n"
            + "                          join SGTT_VIGILANCIAAL val on val.idvigilancia=v.idvigilancia\n"
            + "                          left join admc_firmatipo tf on tf.idfirmatipo=v.idtipofirma \n"
            + "                          join sgtc_tipomedio tm on tm.idtipomedio=v.idtipomedio \n"
            + "                          join sgtc_tipodocumento td on td.idtipodocumento=v.idtipodocumento \n"
            + "                          join sgtt_documento d on d.idvigilancia=v.idvigilancia and d.idadmonlocal=val.idadmonlocal \n"
            + "                          join sgtc_dscripcionvig dv on dv.iddescripcion=v.iddescripcion \n"
            + "                          left join sgtc_cargoadmtvo ca on ca.idcargoadmtvo=v.idcargoadmtvo\n"
            + "                          left join sgtc_nivelemision ne on ne.idnivelemision=v.idnivelemision\n"
            + "                          where v.idvigilancia = ?";
    String CONSULTAR_DETALLE = "select v.idadmonlocal as id_administracion_local ,\n"
            + "       al.descripcion as administracion_local,\n"
            + "       v.cantidaddocumentos as cantidad_documentos\n"
            + "from sgtt_vigilanciaal v \n"
            + "join sgtc_admonlocalsc al on al.idadmonlocal=v.idadmonlocal\n"
            + "where v.idvigilancia = ? order by v.idadmonlocal";
    String CONSULTAR_DETALLE_EF = "select v.identidadfederativa as id_entidad_federativa ,\n"
            + "                    ef.descripcion as entidad_federativa,\n"
            + "                    v.cantidaddocumentos as cantidad_documentos\n"
            + "             from sgtt_vigilanciaef v \n"
            + "             join rfcc_entidadfedera ef on ef.numeroentidad=v.identidadfederativa\n"
            + "             where v.idvigilancia = ? order by v.identidadfederativa";
    
    String CONSULTAR_REPORTE_VIGILANCIA = "select \n"
            + "            vig.idVigilancia as numeroVigilancia,\n"
            + "            td.nombre as tipoDocumento,\n"
            + "            des.descripcion as descripcionVigilancia,\n"
            + "            listagg (per.descripcionPeriodo, ',') \n"
            + "            WITHIN GROUP \n"
            + "            (ORDER BY per.idPeriodo, per.idPeriodicidad) periodosRequeridos,\n"
            + "            listagg (pvi.ejercicio, ',') \n"
            + "            WITHIN GROUP \n"
            + "            (ORDER BY pvi.ejercicio) ejerciciosRequeridos,\n"
            + "            vig.fechaCargaArchivos as fechaLiberacionVigilancia,\n"
            +"             tipoMedio.idTipoMedio,\n"                                          
            + "            tipoMedio.nombre as tipoMedio"
            + "         from \n"
            + "            (\n"
            + "              select distinct idVigilancia, idPeriodo, idPeriodicidad, ejercicio from SGTT_DetalleDoc det\n"
            + "              inner join SGTT_Documento doc on (det.numeroControl = doc.numeroControl) where idVigilancia = ? \n"
            + "            ) pvi\n"
            + "            inner join SGTT_Vigilancia vig on (pvi.idVigilancia = vig.idVigilancia)\n"
            + "            inner join SGTC_DscripcionVig des on (vig.idDescripcion = des.idDescripcion)\n"
            + "            inner join SGTC_TipoDocumento td on (vig.idTipoDocumento = td.idTipoDocumento)\n"
            + "            inner join SGTA_Periodicidad per on (pvi.idPeriodicidad = per.idPeriodicidad and pvi.idPeriodo = per.idPeriodo)\n"
            + "            inner join SGTC_TipoMedio tipoMedio on tipoMedio.idtipomedio = vig.idtipomedio\n"
            + "            group by \n"
            + "            vig.idVigilancia,\n"
            + "            td.nombre,\n"
            + "            des.descripcion,\n"
            + "            vig.fechaCargaArchivos,\n"
            + "            tipoMedio.idTipoMedio,\n"                              
            +"             tipoMedio.nombre";
    
    String CONSULTAR_ALSC_POR_IDVIGILANCIA = "select \n" +
        "val.idAdmonLocal as cv_alsc,\n" +
        "al.descripcion as alsc,\n" +
        "val.cantidadDocumentos,\n" +
        "imp.cantidadImpresos, \n" +
        "val.fechaDeterminaEmision,\n" +
        "svi.nombre as situacionVigilancia\n" +
        "from (\n" +
        "  select idAdmonLocal, cantidadDocumentos, fechaDeterminaEmision, idSituacionVigilancia  from SGTT_VigilanciaAL where idVigilancia = ?\n" +
        ") val \n" +
        "inner join SGTC_AdmonLocalSC al on (val.idAdmonLocal = al.idAdmonLocal)\n" +
        "left join (\n" +
        "  select idAdmonLocal, count(fechaImpresion) as cantidadImpresos from SGTT_Documento where idVigilancia = ? and fechaImpresion is not null\n" +
        "  group by idAdmonLocal\n" +
        ") imp on (al.idAdmonLocal = imp.idAdmonLocal)\n" +
        "inner join SGTC_SituacionVig svi on (val.idSituacionVigilancia = svi.idSituacionVigilancia)";
 
    
    String CONSULTAR_EF_POR_VIGILANCIA = "select \n" +
        "vef.idEntidadFederativa,\n " +
        "ef.descripcion,\n " +
        "'ENTIDAD FEDERATIVA' tipoEnvio,\n " +
        "vef.cantidadDocumentos,\n " +
        "vef.fechaDescarga,\n " +
        "svi.nombre as situacionVigilancia\n " +
        "from (\n " +
        "  select idEntidadFederativa, cantidadDocumentos, fechaDescarga, idSituacionVigilancia  from SGTT_VigilanciaEF where idVigilancia = ? \n " +
        ") vef\n " +
        "inner join RFCC_EntidadFedera ef on (vef.idEntidadFederativa = ef.numeroEntidad)\n " +
        "inner join SGTC_SituacionVig svi on (vef.idSituacionVigilancia = svi.idSituacionVigilancia) order by idEntidadFederativa ";
        }
