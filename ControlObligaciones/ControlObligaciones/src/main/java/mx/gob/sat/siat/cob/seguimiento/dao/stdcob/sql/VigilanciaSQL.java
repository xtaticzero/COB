package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql;

public interface VigilanciaSQL {

    String CONSULTAR_DOCS_VIGILANCIA_POR_ESTADO =
            "select vig.idvigilancia, vig.idtipodocumento, vig.idtipomedio, count(*) as totaldocs\n"
            + "from SGTT_VIGILANCIA vig\n"
            + "  inner join SGTT_DOCUMENTO doc\n"
            + "  on (vig.idVigilancia = doc.idVigilancia)\n"
            + "  where ultimoEstado = :ultimoEstado and doc.idetapavigilancia = :etapaVigilancia\n"
            + "  and doc.fechavencimientodocto < TRUNC(sysdate) and  doc.esultimogenerado = 1 and doc.considerarenuencia = :consideraRenuencia\n"
            + "  and vig.idtipodocumento in (:tipoDocumentos)\n"
            + "group by vig.IdVigilancia, vig.idtipodocumento, vig.idtipomedio,doc.idetapavigilancia\n"
            + "order by vig.idvigilancia";
    String INSERT_VIGILANCIA = "INSERT INTO SGTT_VIGILANCIA  (FECHACARGAARCHIVOS, IDTIPODOCUMENTO, RFCUSUARIO,"
            + "IDADMONGRALUSUARIO, IDADMONLOCALUSUARIO, NOMBREUSUARIO, APELLIDOPATERNOUSUARIO,"
            + "APELLIDOMATERNOUSUARIO, NUMEMPLEADOUSUARIO, IPUSUARIO,"
            + "IDTIPOMEDIO,IDTIPOFIRMA, IDESTADOVIGILANCIA, IDVIGILANCIA,IDMOVIMIENTO,"
            + "FECHACORTE,IDNIVELEMISION,IDCARGOADMTVO,IDDESCRIPCION) VALUES ("
            + "to_date(to_char(sysdate,'dd/MM/yyyy'),'dd/MM/yyyy'),"
            + "?,?,?,?,?,?,?,?,?,?,?,1,SGTQ_VIGILANCIAS.NEXTVAL,?,?,?,?,?)";
    String INSERT_DETALLE_VIGILANCIA = "INSERT INTO SGTT_DETALLECARGA ( NOMBREARCHIVO, NUMEROREGISTROSARCHIVO,"
            + "RUTALOCALARCHIVO, IDVIGILANCIA, RUTABITACORAERRORES) VALUES ("
            + "?,?,?,SGTQ_VIGILANCIAS.CURRVAL,?)";
    String CONSULTA_ULTIMO_ID_VIGILANCIA = "select SGTQ_VIGILANCIAS.CURRVAL from dual";
    String UPDATE_BITACORA_VIGILANCIA =
            "UPDATE sgtt_detallecarga SET RUTABITACORAERRORES = ? WHERE IDVIGILANCIA =? and NOMBREARCHIVO = ?";
    String CONSULTA_VIGILANCIAS_PENDIENTES =
            "SELECT IDVIGILANCIA AS ID, IDTIPODOCUMENTO AS MODALIDAD_DOC, "
            + "IDNIVELEMISION, IDTIPOMEDIO, d.DESCRIPCION,FECHACARGAARCHIVOS "
            + "FROM SGTT_VIGILANCIA v, SGTC_DSCRIPCIONVIG d "
            + "WHERE IDESTADOVIGILANCIA = 1 "
            + "and v.iddescripcion = d.iddescripcion";
    //para pruebas rapidas cambiar IDESTADOVIGILANCIA a 2 y IDVIGILANCIA a 395
    String CONSULTA_VIGILANCIAS_PENDIENTES_EF =
            "SELECT IDVIGILANCIA AS ID, IDTIPODOCUMENTO AS MODALIDAD_DOC, "
            + "IDNIVELEMISION, IDTIPOMEDIO, d.DESCRIPCION,FECHACARGAARCHIVOS,numempleadousuario "
            + "FROM SGTT_VIGILANCIA v, SGTC_DSCRIPCIONVIG d "
            + "WHERE IDESTADOVIGILANCIA = 1 "
            + "and v.iddescripcion = d.iddescripcion "
            + "and v.IDVIGILANCIA in ( "
            + "select doc.IDVIGILANCIA from SGTT_DOCUMENTO doc where doc.IDVIGILANCIA = v.IDVIGILANCIA)";
    String CONSULTA_VIGILANCIA_EXISTENTE =
            "SELECT count(1) "
            + "FROM SGTT_VIGILANCIA v "
            + "WHERE v.IDVIGILANCIA = ? and v.IDVIGILANCIA in ( "
            + "select doc.IDVIGILANCIA from SGTT_DOCUMENTO doc where doc.IDVIGILANCIA = v.IDVIGILANCIA) ";
    String EXISTE_VIGILANCIA_EF = "select count(1) from SGTT_VIGILANCIAEF where idvigilancia=?";
    String CONSULTA_TIPO_FIRMA = "Select idTipoFirma \n"
            + "from SGTT_Vigilancia \n"
            + "where idVigilancia = ?";
}
