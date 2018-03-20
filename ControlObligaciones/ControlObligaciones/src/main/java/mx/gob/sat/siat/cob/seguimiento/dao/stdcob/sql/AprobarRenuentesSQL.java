/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoDocumentoEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EtapaVigilanciaEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.SituacionVigilanciaEnum;

/**
 *
 * @author root
 */
public final class AprobarRenuentesSQL {
    
    private AprobarRenuentesSQL(){}    
    
    public static final String  CONDICION_ADMON_LOCAL="and d.IDADMONLOCAL = :admonLocal";
    public static final String JOIN_FIRMA="join sgtt_firmadoc firma on firma.NUMEROCONTROL = d.NUMEROCONTROL \n";
    public static final String SIN_FIRMA="and 1 = 1 \n";
    
    private static final String CONDICIONES_ADMINISTRATIVO ="d.ultimoestado = " + EstadoDocumentoEnum.PENDIENTE_DE_PROCESAR.getValor() + "\n" +
                                                            "and d.idEtapaVigilancia > " + EtapaVigilanciaEnum.ETAPA_1.getValor() + "\n" +                                                            
                                                            "and trunc(d.fecharegistro) = :fechaCarga\n"+
                                                            "and v.IDNIVELEMISION = :nivelEmision\n" +
                                                            "and v.IDCARGOADMTVO = :cargoAdmtvo \n" +
                                                            CONDICION_ADMON_LOCAL;
    
    private static final String FROM_GENERAL = "from sgtt_documento d \n" +
                                                "join sgtt_vigilancia v on d.idvigilancia=v.idvigilancia\n" +
                                                  "and v.IDTIPOFIRMA = :idfirma\n" +
                                                  "and v.IDTIPODOCUMENTO = :idTipoDocumento\n" +
                                                  "and v.IDTIPOMEDIO = :idmedio\n";
    private static final String JOIN_CICLO_ETAPA="join sgta_ciclodocetapa etapa on etapa.IDTIPODOCUMENTO= v.IDTIPODOCUMENTO \n" +
                                                                "and etapa.IDETAPAVIGILANCIA  = d.IDETAPAVIGILANCIA\n" +
                                                                "and etapa.fechafin is  null\n" +
                                                                "and etapa.IDESTADOORIGEN = :estadoOrigen\n" +
                                                                "and etapa.IDESTADODESTINO = :estadoDestino\n";
    
    
    
    
    public static final String VIGILANCIA_RENUENTES = "select fecha_registro, cantidad_documentos,\n" +
                                                            "td.nombre as tipo_documento, tf.nombre as tipo_firma, tm.nombre as medio_envio, \n" +
                                                            "td.idtipodocumento as id_tipo_documento, tf.idfirmatipo as id_tipo_firma, tm.idtipomedio as id_tipo_medio\n" +
                                                       "from \n" +
                                                                "(\n" +
                                                                "select fecha_registro,\n" +
                                                                    "idtipofirma,\n" +
                                                                    "idtipodocumento,\n" +
                                                                    "idtipomedio,\n" +
                                                                    "count(1) as cantidad_documentos\n" +
                                                                "from\n" +
                                                                    "(select  trunc(d.fecharegistro) as fecha_registro, \n" +
                                                                        "v.idtipofirma,\n" +
                                                                        "v.idtipodocumento,\n" +
                                                                        "v.idtipomedio\n" +
                                                                    "from sgtt_vigilancia v\n" +
                                                                        "join sgtt_documento d on d.idvigilancia=v.idvigilancia\n" +
                                                                        "and d.ultimoestado = 0\n" +
                                                                        "and d.idEtapaVigilancia > " + EtapaVigilanciaEnum.ETAPA_1.getValor()  +"\n" +
                                                                        "where v.IDNIVELEMISION = :nivelEmision\n" +
                                                                        "and v.IDCARGOADMTVO = :cargoAdmtvo \n"+
                                                                        CONDICION_ADMON_LOCAL + "  \n" +
                                                                    ")\n" +
                                                                "having count(1)>0\n" +
                                                                "group by fecha_registro,\n" +
                                                                    "idtipofirma,\n" +
                                                                    "idtipodocumento,\n" +
                                                                    "idtipomedio\n" +
                                                                "order by fecha_registro,\n" +
                                                                    "idtipofirma,\n" +
                                                                    "idtipodocumento,\n" +
                                                                    "idtipomedio" +
                                                               ") vigilancia_renuentes\n" +
                                                            "join admc_firmatipo tf on tf.idfirmatipo =vigilancia_renuentes.idtipofirma\n" +
                                                            "join sgtc_tipodocumento td on td.idtipodocumento =vigilancia_renuentes.idtipodocumento\n" +
                                                            "join sgtc_tipomedio tm on tm.idtipomedio = vigilancia_renuentes.idtipomedio";
    
    
    public static final String CONSULTA_DOCUMENTOS_PAGINADOS = "select numerocontrol, numerocontrolpadre, rfc\n" +
                                                                "from (\n" +
                                                                        "select d.numerocontrol, d.numerocontrolpadre, \n" +
                                                                            "d.rfc,\n" +
                                                                            "row_number() over(order by d.numerocontrol, d.numerocontrolpadre, d.rfc) num\n" +
                                                                        FROM_GENERAL +
                                                                       "where " + CONDICIONES_ADMINISTRATIVO  +
                                                                ")" +
                                                                "where num >= :rowInicial and num < :rowFinal";
    
    public static final String  CONSULTA_NUMERO_DOCUMENTOS = "select count(1)\n" +
                                                                FROM_GENERAL +
                                                                "where " + CONDICIONES_ADMINISTRATIVO;
    
    public static final String CONSULTA_DETALLE_RENUENTE = "select detalle.idobligacion as idobligacion, obligacion.descripcion as obligacion_descripcion, \n" +
                                                                "detalle.ejercicio as ejercicio, periodo.descripcionperiodo as periodo_descripcion\n" +
                                                            "from sgtt_detalledoc detalle\n" +
                                                                "left join sgtc_obligacion obligacion on detalle.IDOBLIGACION = obligacion.IDOBLIGACION\n" +
                                                                    "and obligacion.FECHAFIN is null\n" +
                                                                "left join sgta_periodicidad periodo on periodo.idperiodo = detalle.idperiodo\n" +
                                                                    "and periodo.idperiodicidad = detalle.idperiodicidad\n" +
                                                                    "and periodo.FECHAFIN is null\n" +
                                                            "where detalle.idsituacionicep = ?\n" +
                                                                "and detalle.numerocontrol = ?";
    
    public static final String EMITIR_DOCUMENTOS= "update sgtt_documento set ultimoestado=:estadoDestino\n" +
                                                    "where numerocontrol in(\n" +
                                                        "select d.numerocontrol\n" +
                                                        FROM_GENERAL +
                                                            JOIN_CICLO_ETAPA + 
                                                            SIN_FIRMA +
                                                        "where " + CONDICIONES_ADMINISTRATIVO + ")";
    
    public static final String BITACORA_CAMBIO_ESTADO = "insert into sgtb_sgtdocumento(numerocontrol, idestadodocto, fechamovimiento)\n" +
                                                            "select d.numerocontrol, :estadoDestino, sysdate \n" +
                                                        FROM_GENERAL +
                                                            JOIN_CICLO_ETAPA +
                                                            SIN_FIRMA +
                                                        "where " + CONDICIONES_ADMINISTRATIVO;
    
    public static final String INSERTA_FIRMA_INDIVIDUAL = "insert into sgtt_firmadoc (numerocontrol, cadenaoriginal, firmadigital, fechaempleadofirma, empleadofirma)\n" +
                                                            "values(:numControlResolucion, :cadenaOriginal, :firmaDigital, sysdate, :empleadoFirma)";
    
    public static final String ELIMINA_FIRMAS="delete from sgtt_firmadoc where numerocontrol in " +
                                                            "(\n" +
                                                                "select d.numerocontrol\n" +
                                                                    FROM_GENERAL +
                                                                "where " + CONDICIONES_ADMINISTRATIVO +
                                                            "\n)";
    
     public static final String CADENAS_ORIGINALES = "select precadenaoriginal || :nombreFuncionario as cadenaOriginal from \n"+
                                                    "(\n" +
                                                        "select precadenaoriginal, \n"+
                                                        "row_number() over(order by d.numerocontrol, d.numerocontrolpadre, d.rfc) num\n"+
                                                        FROM_GENERAL +
                                                        "where " + CONDICIONES_ADMINISTRATIVO +
                                                    "\n)"+
                                                    "where num >= :rowInicial and num <= :rowFinal";
     
     public static final String ACTUALIZAR_VIGILANCIA_AL = "merge into sgtt_vigilanciaal\n" +
                                                    "using(\n" +
                                                        "select distinct v.idvigilancia, d.IDADMONLOCAL\n" +
                                                        FROM_GENERAL +
                                                           JOIN_CICLO_ETAPA+ 
                                                           SIN_FIRMA +
                                                           "join sgtt_vigilanciaal vigilanciaal on vigilanciaal.idvigilancia = v.idvigilancia \n" +
                                                                        "and vigilanciaal.IDADMONLOCAL = d.IDADMONLOCAL \n" +
                                                                        "and vigilanciaal.idsituacionvigilancia =" + SituacionVigilanciaEnum.ENVIADA_ARCA.getIdSituacion()  + "\n" +
                                                        "where " + CONDICIONES_ADMINISTRATIVO + ") vigilancias_cambiar \n" +
                                                      "on (sgtt_vigilanciaal.idvigilancia = vigilancias_cambiar.idvigilancia and sgtt_vigilanciaal.IDADMONLOCAL = vigilancias_cambiar.IDADMONLOCAL) \n" +
                                                      "when matched then update set sgtt_vigilanciaal.idsituacionvigilancia =" + SituacionVigilanciaEnum.ERRONEA.getIdSituacion();
     
     public static final String BITACORA_EMISION_FUNCIONARIO = "insert into sgtt_emidocrenuent(numerocontrol, empleadodeterminaemision, fechadeterminaemision)\n" +
                                                            "select d.numerocontrol, :numeroEmpleado, sysdate \n" +
                                                        FROM_GENERAL +
                                                            JOIN_CICLO_ETAPA +
                                                            SIN_FIRMA +
                                                        "where " + CONDICIONES_ADMINISTRATIVO;
    
}
