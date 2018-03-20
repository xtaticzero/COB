package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql;

/**
 *
 * @author root
 */
public interface DetalleDocumentoSQL {

    /**
     *
     */
    String INSERT = "INSERT /*+ APPEND_VALUES */ INTO SGTT_DETALLEDOC "
            + "(NUMEROCONTROL,"
            + "CLAVEICEP,"
            + "IDOBLIGACION,"
            + "EJERCICIO,"
            + "IDPERIODO,"
            + "FECHAVENCIMIENTOOBLIGACION,"
            + "FECHACUMPLIMIENTO,"
            + "IDPERIODICIDAD,"
            + "IDSITUACIONICEP,"
            + "IDREGIMEN,"
            + "IMPORTEPAGAR,"
            + "IDTIPODECLARACION,"
            + "TIENEMULTAEXTEMPORANEIDAD,"
            + "TIENEMULTACOMPLEMENTARIA, "
            + "ESTADOICEPEC ) "
            + "VALUES (?,"
            + "?,"
            + "?,"
            + "?,"
            + "?,"
            + "?,"
            + "?,"
            + "?,"
            + "?,"
            + "?,"
            + "?,"
            + "?,"
            + "?,"
            + "?, NULL)";
    /**
     *
     */
    String INSERT_BATCH = "INSERT INTO SGTT_DETALLEDOC ("
            + "NUMEROCONTROL,CLAVEICEP,IDOBLIGACION,EJERCICIO,"
            + "IDPERIODO,FECHAVENCIMIENTOOBLIGACION,FECHACUMPLIMIENTO,"
            + "IDPERIODICIDAD,IDSITUACIONICEP,IDREGIMEN,"
            + "IMPORTEPAGAR,IDTIPODECLARACION,TIENEMULTAEXTEMPORANEIDAD,"
            + "TIENEMULTACOMPLEMENTARIA )"
            + "VALUES (:numeroControl,"
            + ":claveIcep,"
            + ":idObligacion,"
            + ":idEjercicio,"
            + ":idPeriodo,"
            + ":fechaVencimiento,"
            + ":fechaCumplimiento,"
            + ":idPeriodicidad,"
            + ":idSituacionIcep,"
            + ":idRegimen,"
            + ":importeCargo,"
            + ":idTipoDeclaracion,"
            + ":tieneMultaExtemporaneidad,"
            + ":tieneMultaComplementaria)";
    /**
     *
     */
    String CONSULTA_NUMCONTROL = "SELECT NUMEROCONTROL,"
            + "CLAVEICEP,  "
            + "IDOBLIGACION,  "
            + "EJERCICIO,  "
            + "IDPERIODO,  "
            + "FECHAVENCIMIENTOOBLIGACION,  "
            + "FECHACUMPLIMIENTO,  "
            + "IDPERIODICIDAD,  "
            + "IDSITUACIONICEP,  "
            + "IDREGIMEN,  "
            + "IMPORTEPAGAR,  "
            + "IDTIPODECLARACION,  "
            + "TIENEMULTAEXTEMPORANEIDAD,  "
            + "TIENEMULTACOMPLEMENTARIA,  "
            + "IDCUMPLIMIENTO,  "
            + "IDCUMPLIMIENTOCOMPL,  "
            + "FECHAPRESENTACIONCOMPL,  "
            + "FECHABAJA  "                     
            + " FROM SGTT_DETALLEDOC  "
            + " WHERE NUMEROCONTROL = ? ";
    /**
     *
     */
    String ACTUALIZAR_SOLVENTADO = "update sgtt_detalledoc set idSituacionIcep= ? "
            + "                                       where numerocontrol=? and claveicep=?";
    /**
     *
     */
    
    String ACTUALIZAR_SOLVENTADO_ICEPS = "update sgtt_detalledoc set idSituacionIcep= ? "
            + "                                       where numerocontrol=? and idSituacionIcep=?";
    /**
     *
     */
    String MARCAR_MULTA_EXTEMPORANEIDAD = "update sgtt_detalledoc set tienemultaextemporaneidad=1 "
            + "                                       where numerocontrol=? and claveicep=?";
    /**
     *
     */
    String MARCAR_MULTA_COMPLEMENTARIA = "update sgtt_detalledoc set tienemultacomplementaria= 1 "
            + "                                       where numerocontrol=? and claveicep=?";
    String ACTUALIZAR_POR_MOVIENTO = "update sgtt_detalledoc set idSituacionIcep= ? , fechabaja = ? "
            + " where numerocontrol = ? ";
    String ACTUALIZAR_POR_MOVIENTO_ICEPS = "update sgtt_detalledoc set idSituacionIcep= ? , fechabaja = ?  "
            + "where numerocontrol = ? and claveicep = ?";
    String COUNT_IDSITUACION_ICEP = "select count(*) REGISTROS from sgtt_detalledoc where numerocontrol=? and idsituacionicep=0";
}
