package mx.gob.sat.siat.cob.seguimiento.dao.arca.sql;

public interface ReqsAnterioresSQL {

    String INSERT_REQS_ANTERIORES_ITEM = "INSERT INTO SIATControlDeObligaciones.T_RequerimientoAnterior (\n"
            + "    IdDocumento,\n"
            + "    IdDocumentoPadre,\n"
            + "    FcDescripcionTipoDeRequerimiento,\n"
            + "    FdFechaDeNotificacion,\n"
            + "    FcDescripcionDeObligacion,\n"
            + "    FcDescripcionDePeriodo,\n"
            + "    FiEjercicio,\n"
            + "    FdFechaDePresentacionDeObligacion,\n"
            + "    FdFechaDeVenciomientoDeRequerimiento,\n"
            + "    FcEstadoDeObligacionDeVencimiento) VALUES";
    String INSERT_REQS_ANTERIORES = "INSERT INTO SIATControlDeObligaciones.T_RequerimientoAnterior (\n"
            + "    IdDocumento,\n"
            + "    IdDocumentoPadre,\n"
            + "    FcDescripcionTipoDeRequerimiento,\n"
            + "    FdFechaDeNotificacion,\n"
            + "    FcDescripcionDeObligacion,\n"
            + "    FcDescripcionDePeriodo,\n"
            + "    FiEjercicio,\n"
            + "    FdFechaDePresentacionDeObligacion,\n"
            + "    FdFechaDeVenciomientoDeRequerimiento,\n"
            + "    FcEstadoDeObligacionDeVencimiento) \n "
            + " VALUES (:idDocumento, :idDocumentoPadre, :descrTipoRequ, :fechaNotificacion, :descrObligacion,"
            + " :descrPeriodo, :ejercicio, :fechaPresentacionObligacion, :fechaVencimientoReq, :edoObligacionVencimiento )";
    String DELETE = "delete from SIATControlDeObligaciones.T_RequerimientoAnterior where idDocumento in (\n"
            + "select \n"
            + "doc.id\n"
            + "from T_Documento doc \n"
            + "where doc.idVigilancia = ? and doc.idALSC = ? and doc.idEstadoDeDocumento = 0\n"
            + ")";
    String SELECT_X_ID_VIGILANCIA = "select \n"
            + "req.IdDocumento,\n"
            + "req.IdDocumentoPadre,\n"
            + "req.fcDescripcionTipoDeRequerimiento,\n"
            + "req.fdFechaDeNotificacion,\n"
            + "req.fcDescripcionDeObligacion,\n"
            + "req.fcDescripcionDePeriodo,\n"
            + "req.fiEjercicio,\n"
            + "req.fdFechaDePresentacionDeObligacion,\n"
            + "req.fdFechaDeVenciomientoDeRequerimiento,\n"
            + "req.fcEstadoDeObligacionDeVencimiento\n"
            + "from SIATControlDeObligaciones.T_RequerimientoAnterior req\n"
            + "inner join T_Documento doc\n"
            + "  on doc.id = req.idDocumento\n"
            + "    and doc.idVigilancia = ? \n"
            + "    and doc.idALSC = ?\n"
            + "    and doc.idEstadoDeDocumento = 0";
    
    String SELECT_REQS_ANTERIORES_X_ID_VIGILANCIA = "select count(1)\n"
            + "from SIATControlDeObligaciones.T_RequerimientoAnterior req\n"
            + "inner join SIATControlDeObligaciones.T_Documento doc\n"
            + "on doc.id = req.idDocumento\n"
            + "and doc.idVigilancia = ?\n"
            + "and doc.idALSC = ?\n"
            + "and convert(datetime, convert(varchar(20), doc.fdFechaRegistro, 20)) = convert(datetime,?)";
        
    String DELETE_REQS_ANTERIORES_X_ID_VIGILANCIA = "delete from SIATControlDeObligaciones.T_RequerimientoAnterior where idDocumento in (\n"
            + "select \n"
            + "doc.id\n"
            + "from SIATControlDeObligaciones.T_Documento doc \n"
            + "where doc.idVigilancia = ? and doc.idALSC = ? \n"
            + "and convert(datetime, convert(varchar(20), doc.fdFechaRegistro, 20)) = convert(datetime,?))";
}