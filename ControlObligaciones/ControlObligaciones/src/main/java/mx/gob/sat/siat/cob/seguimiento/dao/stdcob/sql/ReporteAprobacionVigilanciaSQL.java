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
public interface ReporteAprobacionVigilanciaSQL {

    String ADMON_LOCAL = " and doc.idadmonlocal= ? ";
    String PARAMETRO_ADMON_LOCAL = "PARAMETRO_ADMON_LOCAL";

    String CONSULTA_CIFRAS = "select NO_PROCESADO,ANULADO,CANCELADO,CUMPLIDO,CUMPLIDO_SIN_NOTIFICAR from (\n"
            + "select doc.ultimoestado estatus,count(1) counter from sgtt_vigilancia vig\n"
            + "join sgtt_documento doc on doc.idvigilancia=vig.idvigilancia \n"
            + "where vig.idvigilancia=? " + PARAMETRO_ADMON_LOCAL + " \n"
            + "group by doc.ultimoestado) x\n"
            + "PIVOT(\n"
            + "    SUM(counter) \n"
            + "    FOR estatus in (" + EstadoDocumentoEnum.NO_PROCESADO.getValor() + " AS NO_PROCESADO,"
            + EstadoDocumentoEnum.ANULADO.getValor() + " AS ANULADO,"
            + EstadoDocumentoEnum.CANCELADO.getValor() + " AS CANCELADO,"
            + EstadoDocumentoEnum.CUMPLIDO.getValor() + " AS CUMPLIDO,"
            + EstadoDocumentoEnum.CUMPLIDO_SIN_NOTIFICAR.getValor() +" AS CUMPLIDO_SIN_NOTIFICAR)\n"
            + ")";
    String CONSULTA_DETALLE = "select doc.rfc,doc.numerocontrol from sgtt_vigilancia vig\n"
            + "join sgtt_documento doc on doc.idvigilancia=vig.idvigilancia\n"
            + "    and vig.idvigilancia= ? and doc.ultimoestado="
            + EstadoDocumentoEnum.NO_GENERADO.getValor()
            + " " + PARAMETRO_ADMON_LOCAL;
    String CONSULTA_DETALLE_EXCLUIDOS = "select doc.rfc,doc.numerocontrol from sgtt_vigilancia vig\n"
            + "join sgtt_documento doc on doc.idvigilancia=vig.idvigilancia\n"
            + "    and vig.idvigilancia= ? and doc.ultimoestado="
            + EstadoDocumentoEnum.NO_PROCESADO.getValor()
            + " " + PARAMETRO_ADMON_LOCAL;
    String CONSULTA_DETALLE_CANCELADOS = "select doc.rfc,doc.numerocontrol from sgtt_vigilancia vig\n"
            + "join sgtt_documento doc on doc.idvigilancia=vig.idvigilancia\n"
            + "    and vig.idvigilancia= ? and doc.ultimoestado in ("
            + EstadoDocumentoEnum.CANCELADO.getValor()+","+EstadoDocumentoEnum.ANULADO.getValor()+") "
            + " " + PARAMETRO_ADMON_LOCAL;
    String CONSULTA_DETALLE_CUMPLIDOS = "select doc.rfc,doc.numerocontrol from sgtt_vigilancia vig\n"
            + "join sgtt_documento doc on doc.idvigilancia=vig.idvigilancia\n"
            + "    and vig.idvigilancia= ? and doc.ultimoestado in ("
            + EstadoDocumentoEnum.CUMPLIDO_SIN_NOTIFICAR.getValor()+","+EstadoDocumentoEnum.CUMPLIDO.getValor()+") "
            + " " + PARAMETRO_ADMON_LOCAL;
}
