/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoDocumentoEnum;

/**
 *
 * @author root
 */
public interface DocumentoAprobarSQL {

    String VIGILANCIA = " from sgtt_vigilancia vig\n";
    String JOIN_DOCUMENTO = " join sgtt_documento doc on doc.idvigilancia=vig.idvigilancia\n";
    String JOIN_DESCRIPCION_VIGILANCIA = " left join sgtc_dscripcionvig dv on dv.iddescripcion=vig.iddescripcion and dv.fechafin is null \n";
    String JOIN_DETALLE_DOC = " join sgtt_detalledoc det on det.numerocontrol=doc.numerocontrol\n";
    String JOIN_LOCAL = "             join sgtc_admonlocalsc al on al.idadmonlocal=doc.idadmonlocal\n";
    String WHERE_ULTIMO_ESTADO = " where vig.idvigilancia= ? and doc.ultimoestado=";

    String ADMON_LOCAL = " and doc.idadmonlocal= ? ";
    String FILTRO = " and doc.rfc like ? ";

    String PARAMETRO_ADMON_LOCAL = "PARAMETRO_ADMON_LOCAL";
    
    String FILTRO_RFC = "FILTRO_RFC";

    String CONSULTAR_DOCUMENTOS_POR_VIGILANCIA_PAGINADOS = "select numero_carga,\n"
            + "       descripcion_vigilancia,\n"
            + "       numero_control,\n"
            + "       rfc,\n"
            + "       estado_documento,\n"
            + "       id_administracion_local,\n"
            + "       administracion_local\n"
            + "from \n"
            + "(select vig.idvigilancia numero_carga,\n"
            + "       dv.descripcion descripcion_vigilancia,\n"
            + "       doc.numerocontrol numero_control,\n"
            + "       doc.idadmonlocal id_administracion_local,\n"
            + "       al.descripcion administracion_local,\n"
            //+ " case when doc.idtipopersona='F' then (select pf.rfc  from rfcp_personafisica pf where pf.idpersona=doc.boid) \n"
            //+ " else (select pm.rfc  from rfcp_personamoral pm where pm.idpersona=doc.boid) end rfc, \n"
            + "        doc.rfc,\n"
            + "       doc.ultimoestado estado_documento,\n"
            + "       rownum num\n"
            + VIGILANCIA
            + JOIN_DOCUMENTO
            + JOIN_DESCRIPCION_VIGILANCIA
            + JOIN_LOCAL
            + "where vig.idvigilancia= ? and doc.ultimoestado in("
            + EstadoDocumentoEnum.PENDIENTE_DE_PROCESAR.getValor() + ","
            + EstadoDocumentoEnum.CANCELADO.getValor() + ","
            + EstadoDocumentoEnum.CUMPLIDO_SIN_NOTIFICAR.getValor() + ","
            + EstadoDocumentoEnum.ANULADO.getValor() + ","
            + EstadoDocumentoEnum.CUMPLIDO.getValor() + ") " + PARAMETRO_ADMON_LOCAL + FILTRO_RFC
            + "\n order by doc.numerocontrol) documento\n"
            + "where num >= ? and num < ? ";
    
    /**
     * obtener pagina para el procesamiento de firmas en el worker de JS
     */
    String CONSULTAR_DOCUMENTOS_FIRMAR_PAGINADOS = "select precadenaoriginal "+
                " from ( select vig.idvigilancia numero_carga, "+
                "     doc.numerocontrol numero_control, "+
                "      doc.rfc, "+
                "      doc.precadenaoriginal, "+
                "      rownum num "+
                " from sgtt_vigilancia vig "+
                " join sgtt_documento doc on doc.idvigilancia=vig.idvigilancia "+
                " where vig.idvigilancia= ? and doc.idetapavigilancia=1 and doc.ultimoestado in("
            + EstadoDocumentoEnum.PENDIENTE_DE_PROCESAR.getValor() + ") " + PARAMETRO_ADMON_LOCAL
            + " order by doc.numerocontrol) documento "
            + "where num >= ? and num <= ? ";
    
    /**
     * insert para proceso batch de guardado de firmas digitales
     */
    String INSERTA_FIRMAS_DOCUMENTOS = "insert /*+ APPEND_VALUES */ into sgtt_firmadoc("
            + "numerocontrol, cadenaoriginal, firmadigital, fechaEmpleadoFirma, empleadoFirma) "
            + "values (:numControlResolucion, :cadenaOriginal, :firmaDigital , SYSDATE, :empleadoFirma )";
            
    String FILTRO_RFC_DOCUMENTOS_POR_VIGILANCIA = " and rfc like ?"; 

    String CONTAR_REGISTROS = "select count(1)\n"
            + VIGILANCIA
            + JOIN_DOCUMENTO
            + "where vig.idvigilancia= ? and doc.ultimoestado in("
            + EstadoDocumentoEnum.PENDIENTE_DE_PROCESAR.getValor() + ","
            + EstadoDocumentoEnum.CANCELADO.getValor() + ","
            + EstadoDocumentoEnum.CUMPLIDO_SIN_NOTIFICAR.getValor() + ","
            + EstadoDocumentoEnum.ANULADO.getValor() + ","
            + EstadoDocumentoEnum.CUMPLIDO.getValor() + ") "
            + PARAMETRO_ADMON_LOCAL;
    
    /**
     * regresa el total de registros a firmar
     */
    String CONTAR_REGISTROS_FIRMA = "select count(1)\n"
            + VIGILANCIA
            + JOIN_DOCUMENTO
            + "where vig.idvigilancia= ? and doc.idetapavigilancia=1 and doc.ultimoestado in("
            + EstadoDocumentoEnum.PENDIENTE_DE_PROCESAR.getValor() + ") "
            + PARAMETRO_ADMON_LOCAL;
    
    String CONTAR_REGISTROS_FILTRO = "select count(1)\n"
            + VIGILANCIA
            + JOIN_DOCUMENTO
            + "where vig.idvigilancia= ? and doc.ultimoestado in("
            + EstadoDocumentoEnum.PENDIENTE_DE_PROCESAR.getValor() + ","
            + EstadoDocumentoEnum.CANCELADO.getValor() + ","
            + EstadoDocumentoEnum.CUMPLIDO_SIN_NOTIFICAR.getValor() + ","
            + EstadoDocumentoEnum.ANULADO.getValor() + ","
            + EstadoDocumentoEnum.CUMPLIDO.getValor() + ") "
            + PARAMETRO_ADMON_LOCAL
            + " and doc.rfc like ? ";

    String DOCUMENTOS_CON_ICEP_POR_LOCALIDAD = "select numero_carga,\n"
            + "                    descripcion_vigilancia,\n"
            + "                    numero_control,\n"
            + "                    rfc,\n"
            + "                    boid,\n"
            + "                    idtipodocumento id_tipo_documento,\n"
            + "       id_administracion_local,\n"
            + "       administracion_local,\n"
            + "                    clave_icep,\n"
            + "                    num\n"
            + "             from \n"
            + "             (select /*+ FIRST_ROWS(1500) */ vig.idvigilancia numero_carga,\n"
            + "                    dv.descripcion descripcion_vigilancia,\n"
            + "                    doc.numerocontrol numero_control,\n"
            + "                    doc.rfc rfc,\n"
            + "       doc.idadmonlocal id_administracion_local,\n"
            + "       al.descripcion administracion_local,\n"
            + "                    doc.boid boid,\n"
            + "                    det.claveicep clave_icep,\n"
            + "                    vig.idtipodocumento,\n"
            + "                    ROW_NUMBER() over (order by doc.numerocontrol,det.claveicep) num"
            + VIGILANCIA
            + JOIN_DOCUMENTO
            + JOIN_LOCAL
            + JOIN_DETALLE_DOC
            + JOIN_DESCRIPCION_VIGILANCIA
            + WHERE_ULTIMO_ESTADO
            + EstadoDocumentoEnum.PENDIENTE_DE_PROCESAR.getValor() + "\n"
            + "            and doc.idadmonlocal= ? )\n"
            + "            documento\n"
            + "            where num >= ? and num < ? ";

    String DOCUMENTOS_CON_ICEP_POR_VIGILANCIA = "select numero_carga,\n"
            + "                    descripcion_vigilancia,\n"
            + "                    numero_control,\n"
            + "                    rfc,\n"
            + "                    boid,\n"
            + "                    idtipodocumento id_tipo_documento,\n"
            + "                    clave_icep,\n"
            + "                    num\n"
            + "             from \n"
            + "             (select /*+ FIRST_ROWS(1500) */ vig.idvigilancia numero_carga,\n"
            + "                    dv.descripcion descripcion_vigilancia,\n"
            + "                    doc.numerocontrol numero_control,\n"
            + "                    doc.rfc rfc,\n"
            + "                    doc.boid boid,\n"
            + "                    det.claveicep clave_icep,\n"
            + "                    vig.idtipodocumento,\n"
            + "                    ROW_NUMBER() over (order by doc.numerocontrol,det.claveicep) num"
            + VIGILANCIA
            + JOIN_DOCUMENTO
            + JOIN_DETALLE_DOC
            + JOIN_DESCRIPCION_VIGILANCIA
            + WHERE_ULTIMO_ESTADO
            + EstadoDocumentoEnum.PENDIENTE_DE_PROCESAR.getValor() + ") \n"
            + "            documento\n"
            + "            where num >= ? and num < ? ";

    String CONTAR_POR_VIGILANCIA = "select count(1)\n"
            + VIGILANCIA
            + JOIN_DOCUMENTO
            + JOIN_DETALLE_DOC
            + WHERE_ULTIMO_ESTADO + EstadoDocumentoEnum.PENDIENTE_DE_PROCESAR.getValor();

    String CONTAR_POR_LOCALIDAD = "select count(1)\n"
            + VIGILANCIA
            + JOIN_DOCUMENTO
            + JOIN_DETALLE_DOC
            + WHERE_ULTIMO_ESTADO + EstadoDocumentoEnum.PENDIENTE_DE_PROCESAR.getValor()
            + "              and doc.idadmonlocal= ? ";

    String RECHAZAR_DOCUMENTOS_VIGILANCIA = "update sgtt_documento set ultimoestado= ? \n"
            + "where numerocontrol in(\n"
            + "select \n"
            + "                    doc.numerocontrol numero_control \n"
            + VIGILANCIA
            + JOIN_DOCUMENTO
            + JOIN_DESCRIPCION_VIGILANCIA
            + WHERE_ULTIMO_ESTADO + EstadoDocumentoEnum.PENDIENTE_DE_PROCESAR.getValor() + ")";

    String RECHAZAR_DOCUMENTOS_LOCALIDAD = "update sgtt_documento set ultimoestado= ? \n"
            + "where numerocontrol in(\n"
            + "select \n"
            + "                    doc.numerocontrol numero_control \n"
            + VIGILANCIA
            + JOIN_DOCUMENTO
            + JOIN_DESCRIPCION_VIGILANCIA
            + WHERE_ULTIMO_ESTADO + EstadoDocumentoEnum.PENDIENTE_DE_PROCESAR.getValor() + ""
            + "             and doc.idadmonlocal= ? )";

    String BITACORA_RECHAZO_VIGILANCIA = "insert into sgtb_sgtdocumento (NUMEROCONTROL,IDESTADODOCTO,FECHAMOVIMIENTO ) \n"
            + "            (select doc.numerocontrol numero_control,\n"
            + "                    ? ,\n"
            + "                   SYSDATE\n"
            + VIGILANCIA
            + JOIN_DOCUMENTO
            + JOIN_DESCRIPCION_VIGILANCIA
            + WHERE_ULTIMO_ESTADO + EstadoDocumentoEnum.PENDIENTE_DE_PROCESAR.getValor() + ")";
    String BITACORA_RECHAZO_LOCALIDAD = "insert into sgtb_sgtdocumento (NUMEROCONTROL,IDESTADODOCTO,FECHAMOVIMIENTO ) \n"
            + "            (select doc.numerocontrol numero_control,\n"
            + "                    ? ,\n"
            + "                   SYSDATE\n"
            + VIGILANCIA
            + JOIN_DOCUMENTO
            + JOIN_DESCRIPCION_VIGILANCIA
            + WHERE_ULTIMO_ESTADO + EstadoDocumentoEnum.PENDIENTE_DE_PROCESAR.getValor() + ""
            + "             and doc.idadmonlocal= ? )";

    String DUAL = " select * from dual ";

    String INSERT_ALL = "insert all \n";

    String INTO_BITACORA_DOCUMENTO = "into sgtb_sgtdocumento "
            + "(NUMEROCONTROL,IDESTADODOCTO,FECHAMOVIMIENTO ) "
            + "values ( '"
            + DocumentoAprobarSQL.CAMPO_NUMERO_CONTROL + "' , "
            + DocumentoAprobarSQL.CAMPO_ESTADO_DOCTO + " ,SYSDATE)\n";

    String CAMPO_ESTADO_DOCTO = "CampoEstadoDocto";

    String CAMPO_NUMERO_CONTROL = "CampoNumeroControl";

    String CAMPO_NUEVO_ESTADO = "NuevoEstado";

    String NUMEROS_CONTROL = "NumerosControl";

    String CAMBIAR_ESTADO_DOCUMENTO_POR_NUMERO_CONTROL = "update sgtt_documento set ultimoestado="
            + DocumentoAprobarSQL.CAMPO_NUEVO_ESTADO + "\n"
            + "where " + NUMEROS_CONTROL;

    String PARAMETRO_ICEPS = "PARAMETRO_ICEPS";

    String BUSCAR_DOCUMENTO_VIG_BOID_ICEPS = "select distinct d.numerocontrol numero_control,\n"
            + "       (select count(1) from sgtt_detalledoc det where det.numerocontrol=d.numerocontrol) numero_iceps\n"
            + "from sgtt_documento d \n"
            + "join sgtt_detalledoc dd on dd.numerocontrol=d.numerocontrol\n"
            + "where idvigilancia= ? and \n"
            + "      boid= ? and \n"
            + PARAMETRO_ICEPS;

    String ACTUALIZAR_ESTATUS_DOCUMENTO_VIGILANCIA = "update sgtt_documento set ultimoestado= ? \n"
            + "where numerocontrol = ? ";

    String INSERT_BITACORA_DOCUMENTO = "insert into sgtb_sgtdocumento "
            + "(NUMEROCONTROL,IDESTADODOCTO,FECHAMOVIMIENTO ) "
            + "values (? , ? , SYSDATE )";
}
