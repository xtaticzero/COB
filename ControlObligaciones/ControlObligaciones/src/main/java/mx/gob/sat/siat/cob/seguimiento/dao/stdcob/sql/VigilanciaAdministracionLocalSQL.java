package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql;

/**
 *
 * @author root
 */
public interface VigilanciaAdministracionLocalSQL {

    String INSERT_VIGILANCIA_ADMIN_LOCAL = "INSERT INTO SGTT_VIGILANCIAAL "
            + "select IDVIGILANCIA,nvl(idadmonlocal,0),count(*), 1, null, null, sysdate, null, null, null, null "
            + "FROM SGTT_DOCUMENTO "
            + "WHERE IDVIGILANCIA = ? "
            + "GROUP BY IDVIGILANCIA, IDADMONLOCAL";
    String ACTUALIZAR_POR_ADMINISTRACION = "update sgtt_vigilanciaal \n"
            + "set idmotivorechazovig=? , idsituacionvigilancia=?   \n"
            + " ,fechadeterminaemision= SYSDATE,empleadodeterminaemision= ? \n"
            + "where idadmonlocal=? and idvigilancia=?";
    String ACTUALIZAR_POR_VIGILANCIA = "update sgtt_vigilanciaal \n"
            + "set idmotivorechazovig=? , idsituacionvigilancia=?   \n"
            + " ,fechadeterminaemision= SYSDATE,empleadodeterminaemision= ? \n"
            + "where idvigilancia=?";
    String OBTENER_VIGILANCIA_A_PROCESAR = "SELECT distinct\n"
            + "      vigAdmon.idVigilancia,\n"
            + "      vigAdmon.idAdmonLocal,\n"
            + "      vigAdmon.idSituacionVigilancia,\n"
            + "      vigadmon.fechaenvioarca,\n"
            + "      vigilancia.idNivelEmision\n"
            + "    FROM SGTT_Vigilancia vigilancia\n"
            + "      JOIN SGTT_VigilanciaAL vigAdmon\n"
            + "        ON vigAdmon.idVigilancia = vigilancia.idVigilancia\n"
            + "        AND vigAdmon.idVigilancia = ? \n"
            + "      JOIN SGTT_ParametroSGT param\n"
            + "        ON param.idParametro = 4\n"
            + "        AND param.fechaFin is null\n"
            + "    WHERE vigAdmon.idSituacionVigilancia IN (2,4)\n"
            + "      AND (vigadmon.fechaEnvioArca IS NULL \n"
            + "          OR vigadmon.CantidadDocumentos <= param.valor \n"
            + "          OR to_date(vigadmon.fechaEnvioArca) < to_date(SYSDATE))\n"
            + "    ORDER BY \n"
            + "      vigadmon.idSituacionVigilancia ASC, \n"
            + "      vigadmon.fechaEnvioArca ASC, \n"
            + "      vigadmon.idVigilancia ASC, \n"
            + "      vigadmon.IdAdmonLocal";
    String CUENTA_REGISTRO_X_VIGILANCIA = "SELECT count(1) as filas \n"
            + "FROM  sgtt_vigilancia vigilancia \n"
            + "  JOIN sgtt_documento docto\n"
            + "    ON vigilancia.idvigilancia = docto.idvigilancia \n"
            + "    AND docto.ultimoestado = 1\n"
            + "    AND docto.idEtapaVigilancia !=4\n"
            + "    AND vigilancia.idVigilancia = ?  \n"
            + "     AND docto.idAdmonLocal = ? \n"
            + "    AND vigilancia.idTipoDocumento !=4\n"
            + "  JOIN sgtc_tipomedio tipoMedio\n"
            + "    ON tipoMedio.idTipoMedio = vigilancia.idTipoMedio\n"
            + "    AND tipoMedio.fechaFin IS NULL\n"
            + "  JOIN sgtc_tipodocumento tipoDocto\n"
            + "    ON tipoDocto.idTipoDocumento = vigilancia.idTipoDocumento\n"
            + "    AND tipoDocto.fechaFIN IS NULL\n"
            + "  JOIN SGTT_PlantillaArca plantilla\n"
            + "    ON plantilla.idTipoDocumento = vigilancia.idTipoDocumento\n"
            + "    AND plantilla.idEtapaVigilancia = docto.idEtapaVigilancia\n"
            + "    AND plantilla.idTipoMEdio = vigilancia.idTipoMedio\n"
            + "    AND plantilla.idTipoFirma = vigilancia.idTipoFirma\n"
            + "    AND plantilla.idNivelEmision = vigilancia.idNivelEmision\n"
            + "    AND plantilla.idCargoAdmtvo = vigilancia.idCargoAdmtvo\n"
            + "    AND plantilla.dias IS NULL\n"
            + "    AND plantilla.idCargoAdmtvo = vigilancia.idCargoAdmtvo\n"
            + "    AND plantilla.constanteResolMotivo IS NULL\n"
            + "    AND plantilla.fechafin IS NULL \n"
            + "    AND plantilla.esMulta = 0\n";
    String ACTUALIZAR_ID_SITUACION_VIGILANCIA = "UPDATE SGTT_VigilanciaAL SET idSituacionVigilancia = ?, \n"
            + "  fechaenvioarca = SYSDATE\n"
            + "WHERE idVigilancia = ? \n"
            + "  AND idAdmonLocal = ? ";
    String ACTUALIZAR_FECHA_VIGILANCIA = "UPDATE SGTT_VigilanciaAL SET fechaultimoenvioresol = SYSDATE\n"
            + "WHERE idVigilancia = ? \n"
            + "  AND idAdmonLocal = ? ";
    String OBTENER_VIGILANCIA_ADMIN_LOCAL_UPDATE = "SELECT DISTINCT vigilancia.idVigilancia, docto.idadmonlocal,"
            + " vigAdmon.idSituacionVigilancia, vigAdmon.fechaEnvioARCA, vigilancia.idNivelEmision as idNivelEmision\n"
            + "FROM SGTT_VigilanciaAL vigAdmon \n"
            + "  JOIN sgtt_vigilancia vigilancia \n"
            + "    on vigAdmon.idVigilancia = vigilancia.idVigilancia\n"
            + "  join sgtt_documento docto\n"
            + "    ON vigilancia.idvigilancia = docto.idvigilancia\n"
            + "    AND vigilancia.idTipoDocumento != 4\n"
            + "    AND docto.idEtapaVigilancia != 4\n"
            + "WHERE docto.numerocontrol IN  # ";
    String ACTUALIZA_ESTADOEJECUCION_PROCESO_ARCHIVOS = "update SGTT_VigilanciaAL "
            + "set idsituacionvigilancia = ?, fechaEnvioArca = ? \n"
            + "where idVigilancia = ?\n"
            + "and idAdmonLocal IN # ";
    String UPDATE_FECHA = "update SGTT_VigilanciaAL set fechaultimovalcumpl=? where idvigilancia=? ";
    String LOCAL = "and idadmonlocal=?";
    String OBTENER_VIGILANCIAS_CON_DOCUMENTOS = "select distinct \n"
            + "docto.idAdmonLocal as idAdmonLocal,\n"
            + "docto.idvigilancia as idVigilancia,\n"
            + "vigAdmon.IdSituacionVigilancia as idSituacionVigilancia,\n"
            + "vigAdmon.fechaEnvioArca as fechaEnvioARCA,\n"
            + "vigilancia.idNivelEmision as idNivelEmision \n"
            + " from sgtt_documento docto\n"
            + "  inner join sgtt_vigilancia  vigilancia\n"
            + "      ON vigilancia.idvigilancia = docto.idvigilancia \n"
            + "      AND docto.ultimoestado = 1\n"
            + "      AND docto.idEtapaVigilancia != 4\n"
            + "      AND vigilancia.idTipoDocumento !=4\n"
            + "      and vigilancia.idvigilancia = ? \n"
            + "      and docto.idAdmonLocal = ? \n"
            + "  inner join SGTT_VigilanciaAL vigAdmon\n"
            + "    ON vigAdmon.idVigilancia = vigilancia.idVigilancia\n"
            + "      AND vigAdmon.idAdmonLocal = docto.idAdmonLocal";
    String OBTENER_VIGILANCIA_MULTA_A_PROCESAR = "SELECT * FROM (  \n"
            + "  select \n"
            + "    a.idVigilancia, \n"
            + "    null as idAdmonLocal, \n"
            + "    a.idNivelEmision,\n"
            + "    null as idSituacionVigilancia,\n"
            + "    val.fechaUltimoEnvioResol as fechaenvioarca\n"
            + "  from (\n"
            + "    select distinct\n"
            + "      docto.idVigilancia,\n"
            + "      resol.idAdmonLocalOrigen as idAdmonLocal,\n"
            + "      vig.idNivelEmision\n"
            + "    from sgtt_ResolucionDoc resol\n"
            + "      inner join SGTT_Documento docto \n"
            + "        on resol.numeroControl = docto.numeroControl\n"
            + "        and resol.ultimoestado = 2 \n"
            + "        and resol.idresolucion is null\n"
            + "      inner join SGTT_Vigilancia vig on (docto.idVigilancia = vig.idVigilancia and vig.idNivelEmision is not null)\n"
            + "  ) a \n"
            + "    left join SGTT_VigilanciaAl val \n"
            + "      on a.idVigilancia = val.idVigilancia \n"
            + "      and a.idAdmonLocal = val.idAdmonLocal\n"
            + "  order by val.fechaUltimoEnvioResol asc nulls first)\n"
            + "WHERE ROWNUM = 1";
    String OBTENER_VIGILANCIA = "select idVigilancia,\n"
            + "idAdmonLocal,\n"
            + "idSituacionVigilancia,\n"
            + "fechaEnvioARCA,\n"
            + "null as idNivelEmision\n"
            + "from sgtt_vigilanciaal\n"
            + "where idvigilancia = ? and IDADMONLOCAL= ?";
    String ID_VIGILANCIA_ENVIA_ARCHIVOS = "SELECT idVigilancia FROM (\n"
            + "  SELECT distinct\n"
            + "      vigadmon.idSituacionVigilancia, \n"
            + "      vigadmon.fechaEnvioArca, \n"
            + "      vigadmon.idVigilancia, \n"
            + "      vigadmon.IdAdmonLocal\n"
            + "    FROM SGTT_Vigilancia vigilancia\n"
            + "      JOIN SGTT_VigilanciaAL vigAdmon\n"
            + "        ON vigAdmon.idVigilancia = vigilancia.idVigilancia\n"
            + "      JOIN SGTT_ParametroSGT param\n"
            + "        ON param.idParametro = 4\n"
            + "    WHERE vigAdmon.idSituacionVigilancia IN (2,4)\n"
            + "      AND (vigadmon.fechaEnvioArca IS NULL \n"
            + "          OR vigadmon.CantidadDocumentos <= param.valor \n"
            + "          OR to_date(vigadmon.fechaEnvioArca) < to_date(SYSDATE))\n"
            + "    ORDER BY \n"
            + "      vigadmon.idSituacionVigilancia ASC, \n"
            + "      vigadmon.fechaEnvioArca ASC nulls first, \n"
            + "      vigadmon.idVigilancia ASC, \n"
            + "      vigadmon.IdAdmonLocal\n"
            + "  )WHERE ROWNUM = 1";
    String VIGILANCIA_CREDITOS = "select \n"
            + "  a.idVigilancia, \n"
            + "  a.idAdmonLocal, \n"
            + "  a.idNivelEmision,\n"
            + "  null as idSituacionVigilancia,\n"
            + "  val.fechaUltimoEnvioResol as fechaenvioarca\n"
            + " from (\n"
            + "  select distinct\n"
            + "   docto.idVigilancia,\n"
            + "   resol.idadmonlocalorigen as idAdmonLocal,\n"
            + "   vig.idNivelEmision\n"
            + "  from sgtt_ResolucionDoc resol\n"
            + "   inner join SGTT_Documento docto \n"
            + "    on resol.numeroControl = docto.numeroControl\n"
            + "    and docto.idVigilancia = ?\n"
            + "    and resol.ultimoestado = 2 \n"
            + "    and resol.idresolucion is null\n"
            + "   inner join SGTT_Vigilancia vig on (docto.idVigilancia = vig.idVigilancia)\n"
            + " ) a \n"
            + " left join SGTT_VigilanciaAl val \n"
            + "  on a.idVigilancia = val.idVigilancia \n"
            + "  and a.idAdmonLocal = val.idAdmonLocal";
    String INSERT_VIGILANCIA_ADMIN_LOCAL_VIGILANCIA = "INSERT INTO SGTT_VigilanciaAl\n"
            + "(idVigilancia,idAdmonLocal,cantidadDocumentos,idSituacionVigilancia,idMotivoRechazoVig,fechaEnvioArca,\n"
            + "fechaRegistro,fechaUltimoEnvioResol,fechaUltimoValCumpl,fechaDeterminaEmision,empleadoDeterminaEmision)\n"
            + "values(?,?,0,6,null,null,sysdate,sysdate,null,null,null)";
    String UPDATE_FECHA_ULTIMO_ENVIO_RESOL = "update SGTT_VigilanciaAL \n"
            + "set fechaultimoenvioresol = null\n"
            + "where idVigilancia = ? and idAdmonLocal = ?";
    String UPDATE_FECHA_ENVIO_ARCA = "update SGTT_VigilanciaAL set fechaEnvioArca = null, idsituacionVigilancia=2\n"
            + "where idVigilancia = ? and idAdmonLocal = ?";
}
