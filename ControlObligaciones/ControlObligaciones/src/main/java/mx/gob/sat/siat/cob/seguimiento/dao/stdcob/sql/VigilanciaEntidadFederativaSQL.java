package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.SituacionVigilanciaEnum;

public interface VigilanciaEntidadFederativaSQL {

    String CONSULTA_VIGILANCIAS_EF_POR_IDVIG_CLAVEEF = " select * from SGTT_VIGILANCIAEF where idvigilancia=? and identidadfederativa=? and idsituacionvigilancia=2";
    String CONSULTA_VIGILANCIAS_EF = "select entidad.descripcion as nombreentidad, vef.identidadfederativa,vef.idvigilancia, dvig.iddescripcion, dvig.descripcion, tip.idtipodocumento,tip.nombre, \n"
            + "vig.fechacorte, vig.fechacargaarchivos,vef.idsituacionarchivo,sit.nombre as situacion, vef.fechadescarga, vef.rutaarchivoomisos, vef.idsituacionvigilancia, vef.rutaarchivofactura, vef.rutaarchivofundamentos  \n"
            + "from SGTT_VIGILANCIAEF vef \n"
            + "inner join rfcc_entidadfedera entidad on vef.identidadfederativa=entidad.numeroEntidad \n"
            + "inner join sgtt_vigilancia vig on vig.idvigilancia=vef.idvigilancia \n"
            + "inner join SGTC_DSCRIPCIONVIG dvig on dvig.IDDESCRIPCION=vig.iddescripcion\n"
            + "inner join SGTC_TIPODOCUMENTO tip on vig.idtipodocumento=tip.idtipodocumento\n"
            + "inner join sgtc_situacionarch sit on sit.idsituacionarchivo=vef.idsituacionarchivo\n"
            + "where vef.idsituacionvigilancia!=" + SituacionVigilanciaEnum.RECHAZADA.getIdSituacion() + " \n"
            + "order by vef.idvigilancia asc, entidad.descripcion asc ";
    String CONSULTA_VIGILANCIA_PAGINADA = "select * from (\n"
            + "select entidad.descripcion as nombreentidad, vef.identidadfederativa,vef.idvigilancia, dvig.iddescripcion, dvig.descripcion, tip.idtipodocumento,tip.nombre,  \n"
            + "    vig.fechacorte, vig.fechacargaarchivos,vef.idsituacionarchivo,sit.nombre as situacion, vef.fechadescarga, vef.rutaarchivoomisos, vef.idsituacionvigilancia, \n"
            + "    vef.rutaarchivofactura, vef.rutaarchivofundamentos , vig.rfcusuario as rfcusuario \n"
            + "    ,row_number() over(order by vef.idvigilancia, entidad.descripcion) num\n"
            + "    from SGTT_VIGILANCIAEF vef  \n"
            + "    inner join rfcc_entidadfedera entidad on vef.identidadfederativa=entidad.numeroEntidad \n"
            + "    inner join sgtt_vigilancia vig on vig.idvigilancia=vef.idvigilancia  \n"
            + "    inner join SGTC_DSCRIPCIONVIG dvig on dvig.IDDESCRIPCION=vig.iddescripcion \n"
            + "    inner join SGTC_TIPODOCUMENTO tip on vig.idtipodocumento=tip.idtipodocumento \n"
            + "    inner join sgtc_situacionarch sit on sit.idsituacionarchivo=vef.idsituacionarchivo\n"
            + "    where vef.idsituacionvigilancia!=" + SituacionVigilanciaEnum.RECHAZADA.getIdSituacion() + " \n"
            + "    order by vef.idvigilancia asc, entidad.descripcion asc\n"
            + "    )\n"
            + "    where num>= ? and num< ?";
    String CONSULTA_CANTIDAD_VIGILANCIAS_EF = "select count (1) from SGTT_VIGILANCIAEF vef \n"
            + "inner join rfcc_entidadfedera entidad on vef.identidadfederativa=entidad.numeroEntidad \n"
            + "inner join sgtt_vigilancia vig on vig.idvigilancia=vef.idvigilancia \n"
            + "inner join SGTC_DSCRIPCIONVIG dvig on dvig.IDDESCRIPCION=vig.iddescripcion\n"
            + "inner join SGTC_TIPODOCUMENTO tip on vig.idtipodocumento=tip.idtipodocumento\n"
            + "inner join sgtc_situacionarch sit on sit.idsituacionarchivo=vef.idsituacionarchivo\n"
            + "where vef.idsituacionvigilancia!=" + SituacionVigilanciaEnum.RECHAZADA.getIdSituacion() + " \n"
            + "order by vef.idvigilancia asc, entidad.descripcion asc ";
    String CONSULTA_VIGILANCIAS_EF_X_ENTIDAD_FEDERATIVA = "select * from (select entidad.descripcion as nombreentidad, vef.identidadfederativa,vef.idvigilancia, dvig.iddescripcion, dvig.descripcion, tip.idtipodocumento,tip.nombre, \n"
            + "vig.fechacorte, vig.fechacargaarchivos,vef.idsituacionarchivo,sit.nombre as situacion, vef.fechadescarga, vef.rutaarchivoomisos, vef.idsituacionvigilancia, vef.rutaarchivofactura, vef.rutaarchivofundamentos  \n"
            + "from SGTT_VIGILANCIAEF vef \n"
            + "inner join rfcc_entidadfedera entidad on vef.identidadfederativa=entidad.numeroEntidad \n"
            + "inner join sgtt_vigilancia vig on vig.idvigilancia=vef.idvigilancia \n"
            + "inner join SGTC_DSCRIPCIONVIG dvig on dvig.IDDESCRIPCION=vig.iddescripcion\n"
            + "inner join SGTC_TIPODOCUMENTO tip on vig.idtipodocumento=tip.idtipodocumento\n"
            + "inner join sgtc_situacionarch sit on sit.idsituacionarchivo=vef.idsituacionarchivo\n"
            + "where vef.idsituacionvigilancia!=" + SituacionVigilanciaEnum.RECHAZADA.getIdSituacion() + " \n"
            + " and vef.IDENTIDADFEDERATIVA=?  "
            + " order by vef.idvigilancia desc) where rownum <=10 ";
    String RECHAZAR_VIGILANCIA_EF = "UPDATE SGTT_VIGILANCIAEF SET IDMOTIVORECHAZOVIG=?,IDSITUACIONVIGILANCIA=? WHERE IDVIGILANCIA=? AND IDENTIDADFEDERATIVA=?";
    String ACEPTAR_VIGILANCIA_EF = "UPDATE SGTT_VIGILANCIAEF SET IDSITUACIONVIGILANCIA=?,IDSITUACIONARCHIVO=?,FECHADESCARGA=? WHERE IDVIGILANCIA=? AND IDENTIDADFEDERATIVA=?";
    String CONSULTA_VIGILANCIAS_EF_X_USUARIO = "select\n"
            + "vef.FECHADESCARGA from SGTT_VigilanciaEF vef\n"
            + "inner join sgtp_usuarioef us on (vef.idEntidadFederativa = us.idEntidadFederativa)\n"
            + "where us.usuario = ? and idVigilancia = ?";
}
