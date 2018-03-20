/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.arca.sql;

/**
 *
 * @author Juan
 */
public interface DocumentoARCASQL {

    String INSERT_T_DOCUMENTO_ALL = "INSERT INTO SIATControlDeObligaciones.T_DOCUMENTO (IdAlsc,"
            + "IdResolucion,fcContribuyente,fcRFC,fcCURP,fcDireccion,"
            + "fcCP,fiCrh,fcResolucion,fImporte,IdDocumentoPlantilla,"
            + "IdEstadoDeDocumento,IdMedioDeEnvio,IdTipoDocumento,Id,"
            + "fcTipoPersona,fcInformacionQR ) "
            + "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    /*
     * Insert T_Documento
     */
    String INSERT_T_DOCUMENTO = "insert into SIATControlDeObligaciones.T_DOCUMENTO (IdAlsc,    "
            + "IdResolucion,fcContribuyente,fcRFC,fcCURP,fcDireccion,fcCP,"
            + "fiCrh,fcResolucion,ffImporte,IdDocumentoPlantilla,IdEstadoDeDocumento,"
            + "IdMedioDeEnvio,IdTipoDocumento,Id,fcTipoPersona,fcInformacionQR,"
            + "IdFirmaDigitalAplicable,fcNombreFirmante,fcCargoFirmante,"
            + "idVigilancia, fdFecharegistro, firmaDigital, cadenaOriginal,idProceso,idLote,idSubproceso) values (:idAlsc, "
            + ":idResolucion,:descripcionContribuyente,:rfc,:curp,:descripcionDireccion,"
            + ":codPostal,:crh,:resolucion,:importe,:idDocumentoPlantilla,:idEstadoDocumento,"
            + ":idMedioEnvio,:idTipoDocumento,:id,:idTipoPersona,:infromacionQR,"
            + ":idFirmaDigitalAplicable,:nombreFuncionario,:descripcionFuncionario,"
            + ":idVigilancia, convert(datetime,:fechaRegistroARCA ,20), :firmaDigital, :cadenaOriginal,"
            + ":idProceso, :idLote, :idSubProceso) ";
    String UPDATE = "update SIATControlDeObligaciones.T_Documento set    "
            + "IdTipoDocumento =?,   "
            + "IdMedioDeEnvio = ?,   "
            + "IdEstadoDeDocumento = ?,   "
            + "IdDocumentoPlantilla = ?,   "
            + "IdAlsc = ?,   "
            + "fcContribuyente = ?,   "
            + "fcRFC = ?,   "
            + "fcCURP = ?,   "
            + "fcDireccion = ?,   "
            + "fiCrh = ?,   "
            + "fcResolucion = ?,   "
            + "ffImporte = ?   "
            + "where Id =?";
    String ACTUALIZA_ESTADO = "UPDATE SIATControlDeObligaciones.T_Documento \n"
            + "    SET IdEstadoDeDocumento = ?\n"
            + " WHERE Id = ?";
    String ACTUALIZA_ULTIMO_ESTADO_DOCUMENTO_MULTAS_MASIVAS = "UPDATE sgtt_documento \n"
            + "SET ultimoEstado = 2, fechaenvioArca = to_date(:fechaRegistroARCA, 'yyyy/mm/dd HH24:mi:ss') \n"
            + " WHERE numerocontrol = :id \n"
            + " AND ultimoEstado = 1";
    /*
     * Insert SGTB_SGTDOCUMENTO proceso job
     */
    String INSERT_BITACORA_ARCHIVOS_MASIVAS = "INSERT INTO sgtb_sgtDocumento "
            + "  (numeroControl, idEstadoDocto, fechaMovimiento) "
            + "VALUES(:id,2,SYSDATE)";
    /*
     * Insert T_Obligacion
     */
    String INSERT_OBLIGACION_ARCA = "INSERT INTO SIATControlDeObligaciones.T_Obligacion \n"
            + "(idDocumento, fcDescripcionDeObligacion,"
            + " fcDescripcionDePeriodo,fiEjercicio, fcFundamentoLegal,\n"
            + " fdFechaDeVencimiento, fcSancion, fcInfraccion, fcImporte)\n"
            + "VALUES (:obligacion.idDocumento,:obligacion.descripcionDeObligacion,"
            + ":obligacion.descripcionDePeriodo,:obligacion.ejercicio,:obligacion.fundamentoLegal,"
            + ":obligacion.fechaDeVencimiento,:obligacion.sancion,:obligacion.infraccion,:obligacion.importe)";
    String INSERT_OBLIGACION_CREDITO_ARCA = "INSERT INTO SIATControlDeObligaciones.T_Obligacion \n"
            + "(idDocumento, fcDescripcionDeObligacion,"
            + " fcDescripcionDePeriodo,fiEjercicio, fcFundamentoLegal,\n"
            + " fdFechaDeVencimiento, fcSancion, fcInfraccion, fcImporte, fcMotivacion)\n"
            + "VALUES (:obligacion.idDocumento,:obligacion.descripcionDeObligacion,"
            + ":obligacion.descripcionDePeriodo,:obligacion.ejercicio,:obligacion.fundamentoLegal,"
            + ":obligacion.fechaDeVencimiento,:obligacion.sancion,:obligacion.infraccion,:obligacion.importe, :obligacion.motivacion)";
    /*
     * Insert T_Periodo
     */
    String INSERT_PERIODO_ARCA = "INSERT INTO SIATControlDeObligaciones.T_Periodo\n"
            + "(idDocumento, fcDescripcionDePeriodo,\n"
            + "fiEjercicio, fcConceptoDeImpuesto)\n"
            + "VALUES (:periodo.idDocumento,:periodo.descripcionPeriodo,"
            + ":periodo.ejercicio,:periodo.conceptoImpuesto)";
    String GET_ESTADO_DOCUMENTO = "SELECT IdEstadoDeDocumento FROM SIATControlDeObligaciones.T_Documento WHERE id = ?";
    /*
     * Insert T_OrigenMulta
     */
    String INSERT_ORIGEN_MULTA_ARCA = "INSERT INTO SIATControlDeObligaciones.T_OrigenMulta\n"
            + "(IdDocumento, fcNumeroDeControlOrigen,fdFechaDeNotificacionOrigen, "
            + "fcNumeroDeControlPrimero,fdFechaDeNotificacionPrimero, fcNumeroDeControlSegundo, "
            + "fdFechaDeNotificacionSegundo )\n"
            + "VALUES (:idDocumento, :numControlOrigen, :fechaNotificacionOrigen,"
            + ":numControlPrimero, :fechaNotificacionPrimero, :numControlSegundo,"
            + " :fechaNotificacionSegundo)";
    /*
     * Insert T_DocumentoResolucion
     */
    String INSERT_T_DOCUMENTO_RESOLUCION = "insert into SIATControlDeObligaciones.T_DocumentoResolucion \n"
            + "(fcDocumento,fdFechaDocumento,fiClaveAlr,fdFechaRecepcion,\n"
            + "fiIdEstatus,fcMotivo,fmImporte,fiCreditoSIR,fcRFC,fcNombreRazon,fcApellidoPaterno,\n"
            + "fcApellidoMaterno,fcCalle,fcNumeroExterior,fcNumeroInterior,fcColonia,fcLocalidad,\n"
            + "fiCodigoPostal,fiEntidadFederativa,fiDelegacionMunicipio,fcCRH,fcBOID)\n"
            + "values(:id, :fechaDocumento, :idAlsc, :fechaEnvioARCA, :idEstatus, :idMotivo,\n"
            + ":importe, :creditoSIR, :rfc, :contribuyente.nombre, :contribuyente.apPaterno,\n"
            + ":contribuyente.apMaterno, :direccion.calle, :direccion.numeroExterior, :direccion.numeroInterior,\n"
            + ":direccion.descripcionColonia, :direccion.descripcionLocalidad, :direccion.codigoPostal,\n"
            + ":idEntidadFederativa, :idDelegacionMunicipio,\n"
            + ":crh, :boId)";
    /*
     * Select Documento por idDocumento
     */
    String SELECT_X_ID_DOCUMENTO = "SELECT * FROM SIATControlDeObligaciones.T_Documento WHERE id = ?";
    /*
     * Delete T_Obligacion x id
     */
    String DELETE_T_OBLIGACION_X_ID = "DELETE FROM SIATControlDeObligaciones.T_Obligacion WHERE idDocumento = ?";
    /*
     * Delete T_Periodo x id
     */
    String DELETE_T_PERIODO_X_ID = "delete from SIATControlDeObligaciones.T_Periodo where idDocumento = ? ";
    /*
     * Delete T_RequerimientoAnterior x id
     */
    String DELETE_T_REQ_ANTERIOR_X_ID = "delete from SIATControlDeObligaciones.T_RequerimientoAnterior where idDocumento = ? ";
    /*
     * Delete T_Documento x id
     */
    String DELETE_T_DOCUMENTO_X_ID = "delete from SIATControlDeObligaciones.T_Documento where id = ? ";
    String CANCELAR_MULTA_SIR = "update SIATControlDeObligaciones.T_DocumentoResolucion set fiIdEstatus = ?\n"
            + "where fcDocumento = ? and fiIdEstatus not in (?, ?)";
    /*
     * Delete T_DocumentoResolucion x id
     */
    String DELETE_T_DOCUMENTO_RESOLUCION_X_ID = "delete from SIATControlDeObligaciones.T_DocumentoResolucion where fcDocumento = ? ";
    /*
     * Delete T_OrigenMulta x id
     */
    String DELETE_T_ORIGEN_MULTA_X_ID = "delete from SIATControlDeObligaciones.T_OrigenMulta where idDocumento = ? ";
    /*
     * Select Documento por idVigilancia SIATControlDeObligaciones.
     */
    String SELECT_X_ID_VIGILANCIA = "select count(1)\n"
            + "from SIATControlDeObligaciones.T_Documento doc\n"
            + "where doc.idVigilancia = ?\n"
            + "and doc.idALSC = ?\n"
            + "and convert(datetime, convert(varchar(20), doc.fdFechaRegistro, 20)) = convert(datetime,?)";
    /*
     * Select Documento Resolucion por idVigilancia
     */
    String SELECT_RESOLUCIONES_X_ID_VIGILANCIA = "select count(1)\n"
            + "from SIATControlDeObligaciones.T_DocumentoResolucion res\n"
            + "inner join SIATControlDeObligaciones.T_Documento doc\n"
            + "on doc.id = res.fcDocumento\n"
            + "and doc.idVigilancia = ?\n"
            + "and doc.idALSC = ?\n"
            + "and convert(datetime, convert(varchar(20), doc.fdFechaRegistro, 20)) = convert(datetime,?)";
    String DELETE_RESOLUCION = "delete from SIATControlDeObligaciones.T_DocumentoResolucion where fcDocumento in (\n"
            + "select \n"
            + "doc.id\n"
            + "from SIATControlDeObligaciones.T_Documento doc \n"
            + "where doc.idVigilancia = ? and doc.idALSC = ? \n"
            + "and convert(datetime, convert(varchar(20), doc.fdFechaRegistro, 20)) = convert(datetime,?))";
    String DELETE_DOCUMENTO = "delete "
            + "from SIATControlDeObligaciones.T_Documento "
            + "where idVigilancia = ? and idALSC = ? \n"
            + "and convert(datetime, convert(varchar(20), fdFechaRegistro, 20)) = convert(datetime,?)\n";
}
