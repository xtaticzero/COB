/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql;

/**
 *
 * @author root
 */
public interface MultaDocumentoSQL {

    String INSERT = "insert into sgtt_resoluciondoc (numeroresolucion,  "
            + "                             idresolucion,  "
            + "                             numerocontrol,  "
            + "                             fecharegistro,  "
            + "                             ultimoestado,  "
            + "                             constanteresolmotivo, "
            + "                             dias,numerolotecbz,numeroprocesocbz,numeroetapacbz,monto, "
            + "                             empleadodeterminaemision, fechadeterminaemision, idadmonlocalactual, idadmonlocalorigen, idcrhactual, rfc)"
            + "                      values(:numeroResolucion,  "
            + "                             null,  "
            + "                             :numeroControl,"
            + "                             :fecha,"
            + "                             :ultimoEstado,"
            + "                             :constanteResolucionMotivo,"
            + "                             null,null,null,null,null,"
            + "                             null,null, SUBSTR(:numeroResolucion,17,2), SUBSTR(:numeroResolucion,17,2), TO_NUMBER(SUBSTR(:numeroResolucion,19,3)), :rfc )";
    String UPDATE = "update sgtt_resoluciondoc set ultimoestado=? where numeroresolucion=?";
    String CONSULTA_NUMCONTROL_TIPO = "select numeroresolucion,"
            + "                                                 idresolucion,numerocontrol,"
            + "                                                 fecharegistro, ultimoestado, constanteresolmotivo "
            + "     from sgtt_resoluciondoc where numerocontrol=? and constanteresolmotivo=?";
    String CONSULTA_NUMCONTROL = "SELECT numeroresolucion,\n"
            + "  idresolucion,\n"
            + "  numerocontrol,\n"
            + "  fecharegistro,\n"
            + "  constanteresolmotivo,\n"
            + "  ultimoestado\n"
            + "FROM sgtt_resoluciondoc\n"
            + "WHERE numerocontrol=?";
    String INSERTAR_MULTAS_MASIVAS = "insert into sgtt_resoluciondoc \n"
            + "(numeroresolucion, idresolucion, numerocontrol,fecharegistro, constanteresolmotivo,ultimoestado,dias,numerolotecbz,numeroprocesocbz,numeroetapacbz,monto,\n"
            + "empleadodeterminaemision, fechadeterminaemision, idadmonlocalactual, idadmonlocalorigen, idcrhactual, rfc)"
            + "select numres.numeroresolucion, null, numres.numerocontrol, ?, ?, ?, ?, null,null,null,null,"
            + "null,null, SUBSTR(numres.numeroresolucion,17,2), SUBSTR(numres.numeroresolucion,17,2), TO_NUMBER(SUBSTR(numres.numeroresolucion,19,3)), nvl(pf.rfc, pm.rfc)\n"
            + "  from SGTT_NUMRESOLUCION numres \n"
            + "   inner join SGTT_DOCUMENTO doc\n"
            + "   on numres.numerocontrol = doc.numerocontrol\n"
            + "join rfcp_persona persona on persona.idPersona=doc.boid \n" 
            + "left join RFCP_PERSONAFISICA pf on persona.idPersona = pf.idPersona \n"
            + "left join RFCP_PERSONAMORAL pm on persona.idPersona = pm.idPersona \n"
            + "  where numres.numerocontrol in #";
    String INSERTAR_RESOLUCION_LIQUIDACION = "insert into sgtt_resoluciondoc \n"
            + "(numeroresolucion, idresolucion, numerocontrol,fecharegistro, constanteresolmotivo,ultimoestado,dias,numerolotecbz,numeroprocesocbz,numeroetapacbz,monto, \n"
            + "empleadodeterminaemision, fechadeterminaemision, idadmonlocalactual, idadmonlocalorigen, idcrhactual)"
            + "select doc.numerocontrol, null, doc.numerocontrolpadre, :fecha, :tipoMulta, :idTipoMedio, :dias, null,null,null,null, \n"
            + "null,null, SUBSTR(doc.numerocontrol,17,2), SUBSTR(doc.numerocontrol,17,2), TO_NUMBER(SUBSTR(doc.numerocontrol,19,3))"
            + "  from SGTT_DOCUMENTO doc\n"
            + "  where doc.numerocontrol in (:documentos)";
    String ACTUALIZAR_MONTO_MULTA_ICEP = "update SGTA_ResolICEP set idmultamonto =:importeDet \n"
            + "where numeroresolucion =:numResolucionDet and claveicep =:claveICEP";
    String ACTUALIZAR_MONTO_RESOLUCIONDOC_BATCH = "update sgtt_resoluciondoc set monto = :monto\n"
            + "where numeroresolucion = :numeroResolucion";
    String ACTUALIZAR_MONTO_CREDITO_BATCH = "update sgtt_resoluciondoc set monto = :ImporteDet\n"
            + "where numeroresolucion = :NumResolucionDet";
    String BORRAR_MONTO_TOTAL_MULTA = "update sgtt_resoluciondoc set monto = null\n"
            + "where numeroresolucion = ?";
    String ACTUALIZAR_ULTIMOESTADO_MULTA = "UPDATE sgtt_ResolucionDoc SET ultimoestado = ? \n"
            + "WHERE numeroresolucion = ? ";
}
