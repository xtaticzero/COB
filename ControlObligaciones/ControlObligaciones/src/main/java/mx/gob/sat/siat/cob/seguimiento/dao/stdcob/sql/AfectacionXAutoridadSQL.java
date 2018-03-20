/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql;

import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;

/**
 *
 * @author Marco Murakami
 */
public interface AfectacionXAutoridadSQL {

    /**
     * Query para obtener todos los iceps susceptibles a afectacion por autoridad dado un numero de
     * control
     */
    String CONSULTA_AFECTACION_BY_NUMCONTROL = "SELECT DISTINCT doc.numerocontrol,\n"
            + "  doc.rfc,\n"
            + "  doc.numerocontrolpadre,\n"
            + "  to_char(doc.fecharegistro, "+ConstantsCatalogos.FORMATO_FECHA+") as fecharegistro,\n"
            + "  to_char(doc.fechanotificacion, "+ConstantsCatalogos.FORMATO_FECHA+") as fechanotificacion,\n"
            + "  to_char(doc.fechavencimientodocto, "+ConstantsCatalogos.FORMATO_FECHA+") AS fechavencimiento,\n"
            + "  estado.nombre             AS estado,\n"
            + "  obligacion.idobligacion   AS claveobligacion,\n"
            + "  obligacion.descripcion,\n"
            + "  icep.nombre AS situacionicep,\n"
            + "  multa.numeroresolucion,\n"
            + "  multacob.nombremultacob AS motivo,\n"
            + "  tipo.nombre          AS tipodocumento\n"
            + "FROM sgtt_documento doc\n"
            + "INNER JOIN sgtc_estadodocto estado\n"
            + "ON doc.ultimoestado = estado.idestadodocto\n"
            + "INNER JOIN sgtt_detalledoc detalle\n"
            + "ON doc.numerocontrol = detalle.numerocontrol\n"
            + "INNER JOIN sgtt_vigilancia vig\n"
            + "ON vig.idvigilancia = doc.idvigilancia\n"
            + "INNER JOIN sgtc_tipodocumento tipo\n"
            + "ON vig.idtipodocumento = tipo.idtipodocumento\n"
            + "LEFT JOIN sgtt_resoluciondoc multa\n"
            + "ON multa.numerocontrol = doc.numerocontrol\n"
            + "LEFT JOIN sgtc_obligacion obligacion\n"
            + "ON obligacion.idobligacion = detalle.idobligacion\n"
            + "INNER JOIN sgtc_multacob multacob\n"
            + "on multacob.constanteresolmotivo = multa.constanteresolmotivo\n"
            + "INNER JOIN sgtc_situacionicep icep\n"
            + "ON detalle.idsituacionicep = icep.idsituacionicep\n"
            + "WHERE doc.numerocontrol = ";
    /**
     * Query para obtener todos los iceps susceptibles a afectacion por autoridad dado un numero de
     * control, filtrados por los siguientes tipos de documento: No generado, Pendiente de imprimir,
     * Pendiente de notificar, Cumplido sin notificar, Vencido, Vencido Parcial
     */
    String AFECTACION_BY_NUMCONTROL = "SELECT DISTINCT doc.numerocontrol,\n"
            + "  doc.numerocontrolpadre,\n"
            + "  doc.rfc,\n"
            + "  to_char(doc.fecharegistro, "+ConstantsCatalogos.FORMATO_FECHA+") as fecharegistro,\n"
            + "  to_char(doc.fechanotificacion, "+ConstantsCatalogos.FORMATO_FECHA+") as fechanotificacion,\n"
            + "  to_char(doc.fechavencimientodocto, "+ConstantsCatalogos.FORMATO_FECHA+") AS fechavencimiento,\n"
            + "  estado.nombre             AS estado,\n"
            + "  obligacion.idobligacion   AS claveobligacion,\n"
            + "  obligacion.descripcion,\n"
            + "  icep.nombre AS situacionicep,\n"
            + "  multa.numeroresolucion,\n"
            + "  multacob.nombremultacob AS motivo,\n"
            + "  tipo.nombre             AS tipodocumento\n"
            + "FROM sgtt_documento doc\n"
            + "INNER JOIN sgtc_estadodocto estado\n"
            + "ON doc.ultimoestado = estado.idestadodocto\n"
            + "INNER JOIN sgtt_detalledoc detalle\n"
            + "ON doc.numerocontrol = detalle.numerocontrol\n"
            + "INNER JOIN sgtt_vigilancia vig\n"
            + "ON vig.idvigilancia = doc.idvigilancia\n"
            + "INNER JOIN sgtc_tipodocumento tipo\n"
            + "ON vig.idtipodocumento = tipo.idtipodocumento\n"
            + "LEFT JOIN sgtt_resoluciondoc multa\n"
            + "ON multa.numerocontrol = doc.numerocontrol\n"
            + "LEFT JOIN sgtc_obligacion obligacion\n"
            + "ON obligacion.idobligacion = detalle.idobligacion\n"
            + "LEFT JOIN sgtc_multacob multacob\n"
            + "ON multacob.constanteresolmotivo = multa.constanteresolmotivo\n"
            + "INNER JOIN sgtc_situacionicep icep\n"
            + "ON detalle.idsituacionicep  = icep.idsituacionicep\n"
            + "WHERE estado.idestadodocto IN (2,3,4,9,10,11)\n"
            + "and doc.numerocontrol = ";
    /**
     * Query para obtener todos los iceps susceptibles a afectacion por autoridad dado un RFC,
     * filtrados por los siguientes tipos de documento: No generado, Pendiente de imprimir,
     * Pendiente de notificar, Cumplido sin notificar, Vencido, Vencido Parcial
     */
    String AFECTACION_BY_RFC = "SELECT DISTINCT doc.numerocontrol,\n"
            + "  doc.rfc,\n"
            + "  doc.numerocontrolpadre,\n"
            + "  to_char(doc.fecharegistro, "+ConstantsCatalogos.FORMATO_FECHA+") as fecharegistro,\n"
            + "  to_char(doc.fechanotificacion, "+ConstantsCatalogos.FORMATO_FECHA+") as fechanotificacion,\n"
            + "  to_char(doc.fechavencimientodocto, "+ConstantsCatalogos.FORMATO_FECHA+") AS fechavencimiento,\n"
            + "  estado.nombre             AS estado,\n"
            + "  obligacion.idobligacion   AS claveobligacion,\n"
            + "  obligacion.descripcion,\n"
            + "  icep.nombre AS situacionicep,\n"
            + "  multa.numeroresolucion,\n"
            + "  multacob.nombremultacob AS motivo,\n"
            + "  tipo.nombre          AS tipodocumento\n"
            + "FROM sgtt_documento doc\n"
            + "INNER JOIN sgtc_estadodocto estado\n"
            + "ON doc.ultimoestado = estado.idestadodocto\n"
            + "INNER JOIN sgtt_detalledoc detalle\n"
            + "ON doc.numerocontrol = detalle.numerocontrol\n"
            + "INNER JOIN sgtt_vigilancia vig\n"
            + "ON vig.idvigilancia = doc.idvigilancia\n"
            + "INNER JOIN sgtc_tipodocumento tipo\n"
            + "ON vig.idtipodocumento = tipo.idtipodocumento\n"
            + "LEFT JOIN sgtt_resoluciondoc multa\n"
            + "ON multa.numerocontrol = doc.numerocontrol\n"
            + "LEFT JOIN sgtc_obligacion obligacion\n"
            + "ON obligacion.idobligacion = detalle.idobligacion\n"
            + "INNER JOIN sgtc_multacob multacob\n"
            + "on multacob.constanteresolmotivo = multa.constanteresolmotivo\n"
            + "INNER JOIN sgtc_situacionicep icep\n"
            + "ON detalle.idsituacionicep = icep.idsituacionicep\n"
            + "WHERE doc.rfc = ";
    
    String COMPLEMENT_BY_TIPO_PERSONA = " inner join sgtt_vigilancia vig  on(doc.idvigilancia= vig.idvigilancia)\n" + 
    "  inner join sgtc_tipodocumento tipo  ON (vig.idtipodocumento = tipo.idtipodocumento )\n" + 
    "  inner join sgtt_detalledoc detalle on(detalle.numerocontrol = doc.numerocontrol)\n" + 
    "  inner join sgta_periodicidad periodo on (periodo.idperiodo = detalle.idperiodo AND periodo.idperiodicidad = detalle.idperiodicidad)\n"  ;
    
    
    String OBTENER_NUMEROS_CONTROL = "select \n" + 
                                    "  distinct doc.numerocontrol as id, \n" + 
                                    "  doc.numerocontrol as descripcion, \n" + 
                                    "  doc.rfc as rfc, \n" + 
                                    "  tipo.nombre as tipodoc,\n" + 
                                    "  detalle.ejercicio,\n" + 
                                    "  periodo.descripcionperiodo,";
    
    String CONSULTA_AFECTACION_BY_NUMCONTROL_INI = "SELECT \n" + 
                                                    "    admon.descripcion as admonlocal, doc.numerocontrol,  \n" + 
                                                    "  doc.rfc, \n" + 
                                                    "  to_char(doc.fecharegistro, "+ConstantsCatalogos.FORMATO_FECHA+") as fecharegistro,\n" + 
                                                    "  to_char(doc.fechanotificacion, "+ConstantsCatalogos.FORMATO_FECHA+") as fechanotificacion, \n" + 
                                                    "  to_char(doc.fechavencimientodocto, "+ConstantsCatalogos.FORMATO_FECHA+") AS fechavencimiento,\n" + 
                                                    "  to_char(doc.FECHANOTRABAJADO, "+ConstantsCatalogos.FORMATO_FECHA+") AS FECHANOTRABAJADO,\n" + 
                                                    "  to_char(doc.FECHANOLOCALIZADOCONTRIBUYENTE, "+ConstantsCatalogos.FORMATO_FECHA+") AS FECHANOLOCALIZADOCONTRIBUYENTE,\n" + 
                                                    "  estado.nombre             AS estado, \n" + 
                                                    "  obl.idobligacion   AS claveobligacion,\n" + 
                                                    "  obl.descripcion,  \n" + 
                                                    "  detalle.ejercicio, \n" +
                                                    "  per.descripcionperiodo, \n" +
                                                    "  icep.nombre              AS situacionicep, \n" + 
                                                    "  tipo.nombre          AS tipodocumento,\n" + 
                                                    "  med.nombre           AS tipoMedio,\n " +
                                                    "  to_char(cit.FECHACITATORIO, "+ConstantsCatalogos.FORMATO_FECHA+") AS FECHACITATORIO ";
    
    String CONSULTA_AFECTACION_BY_NUMCONTROL_MED =  "   inner join SGTA_Periodicidad per on (detalle.idPeriodo = per.idPeriodo and detalle.idPeriodicidad = per.idPeriodicidad )\n" + 
                                                    "  INNER JOIN sgtt_documento doc   ON doc.numerocontrol = detalle.numerocontrol\n" + 
                                                    "  INNER JOIN sgtc_estadodocto estado ON (doc.ultimoestado = estado.idestadodocto)\n" + 
                                                    "  INNER JOIN sgtt_vigilancia vig  ON vig.idvigilancia = doc.idvigilancia\n" + 
                                                    "  INNER JOIN sgtc_tipodocumento tipo  ON (vig.idtipodocumento = tipo.idtipodocumento)\n" + 
                                                    "  inner JOIN sgtc_situacionicep icep ON (detalle.idsituacionicep = icep.idsituacionicep)\n" + 
                                                    "  inner join sgtc_tipomedio  med on (med.idtipomedio =  vig.idtipomedio)\n " +
                                                    "  inner join sgtc_admonlocalSC admon on (admon.idadmonlocal = doc.idadmonlocal)\n " +
                                                    "  left join sgtt_citatoriodoc cit on (cit.numerocontrol = doc.numerocontrol) ";
                                                    
    String CONSULTA_NOMBRE_PERSONA_MORAL=           "  JOIN rfcp_persona persona\n" + 
                                                    "  ON persona.idPersona=doc.boid\n" + 
                                                    "  join (SELECT pm.idPersona, pm.rfc, pm.razonSocial || ' ' || catalogo.descorta  AS compania, \n" + 
                                                    "  catalogo.fechaFinVigencia\n" + 
                                                    "  FROM RFCP_PERSONAMORAL pm\n" + 
                                                    "  JOIN rfcc_catalogo catalogo\n" + 
                                                    "  ON pm.idregimencapital = catalogo.idcatalogo) pm\n" + 
                                                    "  on persona.idPersona = pm.idPersona\n" + 
                                                    "  where (pm.fechafinvigencia > SYSDATE OR pm.fechafinvigencia IS NULL) ";                                                   
            

   
   String OBTENER_MULTAS_POR_NUMCONTROL_TIPOMULTA = "inner join SGTT_DetalleDoc det on (ric.numeroControl = det.numeroControl and ric.claveIcep = det.claveIcep)\n" + 
                            " inner join sgta_periodicidad per on (per.idperiodo = det.idperiodo and per.idperiodicidad = det.idperiodicidad)\n" +
                            " inner join SGTC_MultaMonto mmo on (det.IdObligacion = mmo.IdObligacion and mdo.constanteResolMotivo = mmo.constanteResolMotivo)\n" + 
                            " inner join SGTT_Documento doc on (det.numeroControl = doc.numeroControl)\n" + 
                            " inner join sgtc_obligacion obl on (det.idObligacion = obl.idObligacion )\n" + 
                            " inner join sgtc_multacob mul  on (mul.constanteResolMotivo = mmo.constanteResolMotivo)\n" + 
                            " where ";  
                           
                                               
   
   String OBTENER_REPORTE = "  inner join SGTA_Periodicidad per on (detalle.idPeriodo = per.idPeriodo and detalle.idPeriodicidad = per.idPeriodicidad )\n" + 
   "  INNER JOIN sgtt_documento doc   ON doc.numerocontrol = detalle.numerocontrol\n" + 
   "  INNER JOIN sgtc_estadodocto estado ON (doc.ultimoestado = estado.idestadodocto )\n" + 
   "  INNER JOIN sgtt_vigilancia vig  ON vig.idvigilancia = doc.idvigilancia\n" + 
   "  INNER JOIN sgtc_tipodocumento tipo  ON (vig.idtipodocumento = tipo.idtipodocumento )\n" + 
   "  inner JOIN sgtc_situacionicep icep ON (detalle.idsituacionicep = icep.idsituacionicep )\n" + 
   "  inner join sgtc_tipomedio  med on (med.idtipomedio =  vig.idtipomedio )\n" + 
   "  order by obl.idObligacion";
   
   String OBTENER_TIPO_PERSONA = " select * from sgtt_documento where numeroControl = ?";
   
}
