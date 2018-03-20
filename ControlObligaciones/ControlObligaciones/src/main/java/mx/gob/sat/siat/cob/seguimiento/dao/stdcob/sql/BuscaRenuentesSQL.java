/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql;

/**
 *
 * @author root
 */
public interface BuscaRenuentesSQL {

    /**
     *
     */
    String GET_NUMEROSCONTROL_BY_RFC = "SELECT doc.numerocontrol,\n"
            + "  doc.rfc,\n"
            + "  tipo.nombre\n"
            + "FROM sgtt_documento doc\n"
            + "INNER JOIN sgtt_vigilancia vig\n"
            + "ON vig.idvigilancia = doc.idvigilancia\n"
            + "INNER JOIN sgtc_tipodocumento tipo\n"
            + "ON vig.idtipodocumento  = tipo.idtipodocumento\n"
            + "WHERE rfc LIKE '?%'\n"
            + "AND (lower(tipo.nombre) = 'requerimiento normal'\n"
            + "OR lower(tipo.nombre)   = 'carta exhorto'\n"
            + "OR lower(tipo.nombre)   = 'requerimiento renuente'\n"
            + "OR lower(tipo.nombre)   = 'requerimiento acumulado'\n"
            + "OR lower(tipo.nombre)   = 'requerimiento diot'\n"
            + "OR lower(tipo.nombre)   = 'requerimiento multa')\n"
            + "ORDER BY numerocontrol";
    /**
     *
     */
    String BUSCA_RENUENTES = "SELECT  doc.NumeroControl AS NUMEROCONTROL, doc.RFC AS RFC,\n"
            + "        estado.Nombre AS ESTADO, obligacion.IdObligacion AS CLAVEOBLIGACION,\n"
            + "        obligacion.Descripcion AS DESCRIPCIONOBLIGACION, \n"
            + "        detalle.IdRegimen AS CLAVEREGIMEN,  RFCREG.DESCORTA AS DESCRIPCIONREGIMEN,\n"
            + "        detalle.Ejercicio AS EJERCICIO,\n"
            + "        detalle.IdPeriodo AS PERIODO, detalle.IdPeriodicidad AS PERIODICIDAD,\n"
            + "        detalle.FechaCumplimiento AS FECHACUMPLIMIENTO\n"
            + "FROM sgtt_documento doc \n"
            + "        INNER JOIN sgtt_vigilancia vigilancia ON doc.IDVIGILANCIA = vigilancia.IDVIGILANCIA\n"
            + "                   AND vigilancia.IdTipoDocumento IN (:listaTipoDocumentos) --- Variable de seleccion\n"
            + "                   AND vigilancia.IdTipoMedio != 2\n"
            + "                   AND doc.considerarenuencia = 0 \n"
            + "                   AND doc.UltimoEstado IN (:listaEdosDoc) --- Variable de seleccion\n"
            + "        INNER JOIN sgtc_estadodocto estado ON estado.IDESTADODOCTO = doc.ULTIMOESTADO\n"
            + "        INNER JOIN sgtt_detalledoc detalle ON detalle.NUMEROCONTROL = doc.NUMEROCONTROL\n"
            + "                   AND detalle.idobligacion IN (:listaObligaciones) --- Variable de seleccion\n"
            + "        INNER JOIN sgtc_obligacion obligacion ON obligacion.IDOBLIGACION = detalle.IDOBLIGACION "
            + "        left join rfcc_regimen  rfcreg on rfcreg.idregimen =  detalle.idregimen where( ";
    /**
     *
     */
    String OBTENER_ID = "select sgtq_busqRenuentes.nextval from dual";
    String INSERT_RENUENTES = "insert into sgtt_busqRenuentes\n"
            + "            (IDBUSQUEDARENUENTES, LISTAIDTIPODOCUMENTO, IDESTADODOCTO, ESFINALIZADA, \n"
            + "            LISTAIDOBLIGACION, RUTAARCHIVOGENERADO,FECHAGUARDABUSQUEDA)\n"
            + "            values(?,?,?,?,?,?,to_date(?, 'dd/mm/yyyy HH24:mi:ss'))";
    String BUSCA_ARCHIVO_POR_ID = "SELECT B.IDBUSQUEDARENUENTES, B.RUTAARCHIVOGENERADO, B.ESFINALIZADA,\n"
            + "             B.FECHAGUARDABUSQUEDA, to_char(b.fechaguardabusqueda, 'DD/MM/YYYY hh:mi:ss') as FECHAGUARDABUSQUEDASTR \n"
            + "             FROM SGTT_BUSQRENUENTES B WHERE B.IDBUSQUEDARENUENTES = (select max(idBusquedaRenuentes) as idBusqueda\n"
            + "            from SGTT_BUSQRENUENTES where esfinalizada = 1)";

    String REGISTROS_SIN_FINALIZAR = "select \n"
            + "  IDBUSQUEDARENUENTES,\n"
            + "  LISTAIDTIPODOCUMENTO,\n"
            + "  IDESTADODOCTO,\n"
            + "  FECHAGUARDABUSQUEDA,\n"
            + "  ESFINALIZADA,\n"
            + "  LISTAIDOBLIGACION,\n"
            + "  RUTAARCHIVOGENERADO,\n"
            + "  to_char(fechaguardabusqueda, 'DD/MM/YYYY hh:mi:ss') as FECHAGUARDABUSQUEDASTR\n"
            + "from sgtt_busqRenuentes\n"
            + "  where esFinalizada = 0";

    String BUSCA_RENUENTES_JOB = "SELECT  doc.NumeroControl AS NUMEROCONTROL, doc.RFC AS RFC,\n"
            + "   estado.Nombre AS ESTADO, obligacion.IdObligacion AS CLAVEOBLIGACION,\n"
            + "   obligacion.Descripcion AS DESCRIPCIONOBLIGACION, \n"
            + "   detalle.IdRegimen AS CLAVEREGIMEN,  RFCREG.DESCORTA AS DESCRIPCIONREGIMEN,\n"
            + "   detalle.Ejercicio AS EJERCICIO,\n"
            + "   detalle.IdPeriodo AS PERIODO, detalle.IdPeriodicidad AS PERIODICIDAD,\n"
            + "   detalle.FechaCumplimiento AS FECHACUMPLIMIENTO\n"
            + "FROM sgtt_documento doc \n"
            + "   INNER JOIN sgtt_vigilancia vigilancia ON doc.IDVIGILANCIA = vigilancia.IDVIGILANCIA\n"
            + "     AND vigilancia.IdTipoDocumento IN (#{jobParameters['idTipoDocumento']}) \n"
            + "     AND vigilancia.IdTipoMedio != 2\n"
            + "     AND doc.considerarenuencia = 0 \n"
            + "     AND doc.UltimoEstado IN (#{jobParameters['ultimoEstado']}) \n"
            + "   INNER JOIN sgtc_estadodocto estado ON estado.IDESTADODOCTO = doc.ULTIMOESTADO\n"
            + "   INNER JOIN sgtt_detalledoc detalle ON detalle.NUMEROCONTROL = doc.NUMEROCONTROL\n"
            + "     AND detalle.idobligacion IN (#{jobParameters['idObligaciones']}) \n"
            + "   INNER JOIN sgtc_obligacion obligacion ON obligacion.IDOBLIGACION = detalle.IDOBLIGACION "
            + "   left join rfcc_regimen  rfcreg on rfcreg.idregimen =  detalle.idregimen where( #{jobParameters['wherePB']}";

    String REGISTROS_A_FINALIZAR = "select \n"
            + "  IDBUSQUEDARENUENTES,\n"
            + "  LISTAIDTIPODOCUMENTO,\n"
            + "  IDESTADODOCTO,\n"
            + "  FECHAGUARDABUSQUEDA,\n"
            + "  ESFINALIZADA,\n"
            + "  LISTAIDOBLIGACION,\n"
            + "  RUTAARCHIVOGENERADO,\n"
            + "  to_char(fechaguardabusqueda, 'DD/MM/YYYY hh:mi:ss') as FECHAGUARDABUSQUEDASTR\n"
            + "from sgtt_busqRenuentes\n"
            + "  where idBusquedaRenuentes in (#{jobParameters['idsBusqRen']})";

    String UPDATE_FINALIZADA = "update sgtt_busqRenuentes set esFinalizada= 1\n"
            + "where idbusquedarenuentes = :idBusquedaRenuente";
}
