/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql;

/**
 *
 * @author root
 */
public final class AprobarMultasSQL {    
    
    private AprobarMultasSQL(){}   
    

    public static final String  CONDICION_ADMON_LOCAL="and multa.idadmonlocalorigen = :admonLocal\n";
    
    public static final String CONDICION_LOCAL_IGUAL="and multa.idadmonlocalorigen = multa.idadmonlocalactual\n";
    public static final String CONDICION_LOCAL_DIFERENTE="and multa.idadmonlocalorigen <> multa.idadmonlocalactual\n";
    public static final String SUMA_MONTO ="sum(select_general.monto) as sumaMonto,";
    public static final String JOIN_MONTO="left join SGTC_MultaMonto monto on (det.IdObligacion = monto.IdObligacion and multa.constanteResolMotivo = monto.constanteResolMotivo)";
    
    public static final String ACTUALIZA_ORIGEN = "sgtt_resoluciondoc.idadmonlocalorigen = nueva_local.ClaveALR, \n";
    
    
    private static final String CONDICIONES_MULTA = "and multa.ULTIMOESTADO = :estadoMulta\n" +
                                                        "and multa.FECHAREGISTRO = :fechaRegistro\n" +
                                                        CONDICION_ADMON_LOCAL +
                                                        CONDICION_LOCAL_IGUAL+
                                                        "and multa.CONSTANTERESOLMOTIVO = :idmulta \n" ;
    
    private static final String CONDICIONES_VIGILANCIA = "and vigilancia.IDNIVELEMISION = :nivelEmision \n" +
                                                        "and vigilancia.IDCARGOADMTVO = :cargoAdmtvo \n" +                                                        
                                                        "and vigilancia.IDTIPOMEDIO = :idmedio \n" +
                                                        "and vigilancia.IDTIPOFIRMA = :idfirma \n";
    
    
    private static final String CONDICIONES_MONTO = "(\n" +
                                                            "(monto.constanteResolMotivo in ('RESOLMOTIVO_INCUMPLIMIENTO','RESOLMOTIVO_AMBOS') and (documento.fechaNotificacion >= monto.fechaInicio)\n" +
                                                                   "and (documento.fechaNotificacion <= monto.fechaFin or monto.fechaFin is null))\n" +
                                                            "or\n" +
                                                             "(monto.constanteResolMotivo = 'RESOLMOTIVO_EXTEMPORANEIDAD' and (det.fechaCumplimiento >= monto.fechaInicio)\n" +
                                                                   "and (det.fechaCumplimiento <= monto.fechaFin or monto.fechaFin is null))\n" +
                                                            "or\n" +
                                                            "(monto.constanteResolMotivo = 'RESOLMOTIVO_COMPLEMENTARIA' and (det.fechaPresentacionCompl >= monto.fechaInicio)\n" +
                                                                   "and (det.fechaPresentacionCompl <= monto.fechaFin or monto.fechaFin is null))\n" +
                                                         ")\n";
    private static final String FROM_GENERAL = "sgtt_resoluciondoc multa\n" +
                                                        "join sgtt_documento documento on multa.NUMEROCONTROL = documento.NUMEROCONTROL\n" + CONDICIONES_MULTA +
                                                        "join sgtt_vigilancia vigilancia on documento.idvigilancia = vigilancia.idvigilancia\n" + CONDICIONES_VIGILANCIA +                                                    
                                                        "join SGTA_ResolICEP ric on (multa.numeroResolucion = ric.numeroResolucion)\n" +
                                                        "join SGTT_DetalleDoc det on ric.numeroControl = det.numeroControl and ric.claveIcep = det.claveIcep and det.numeroControl = documento.numeroControl\n" +
                                                        JOIN_MONTO +
                                                        " and " +CONDICIONES_MONTO;
    
   private static final String SELECT_GENERAL = "select select_general.NUMERORESOLUCION, select_general.NUMEROCONTROL, select_general.boid, " + 
                                                        SUMA_MONTO + " select_general.crhActual, select_general.destinoLocal, select_general.precadenaoriginal, select_general.rfc\n" +
                                                                            "from (\n" +
                                                                                    "select \n" +
                                                                                        "/*+INDEX (multa IDX_SGTTRESOLDOC_IDADMONLOCAL IDX_SGTTRESOLDOC_IDADMONLOCALA) */ \n" +
                                                                                        "multa.NUMERORESOLUCION, multa.NUMEROCONTROL, \n" +
                                                                                        "multa.idcrhactual as crhActual, documento.boid, multa.idadmonlocalactual as destinoLocal, multa.precadenaoriginal,\n" +
                                                                                        "nvl(monto.monto,0) as monto, case when monto.monto is null then 0 else 1 end as monto_disponible, multa.rfc \n" +
                                                                                        "from " + FROM_GENERAL +
                                                                                ") select_general \n" +                                                                                
                                                            "group by select_general.NUMERORESOLUCION, select_general.NUMEROCONTROL, select_general.crhActual, " +
                                                                      "select_general.boid, select_general.destinoLocal, select_general.precadenaoriginal, " +
                                                                        "select_general.rfc";
   
   public static final String SELECT_GENERAL_PARA_FIRMA ="select \n" +
                                                              "multa.NUMERORESOLUCION, multa.NUMEROCONTROL, multa.idcrhactual as crhActual, documento.boid, multa.rfc\n" +
                                                        "from sgtt_resoluciondoc multa\n" +
                                                        "join sgtt_documento documento on multa.NUMEROCONTROL = documento.NUMEROCONTROL\n" + CONDICIONES_MULTA +
                                                        "join sgtt_vigilancia vigilancia on documento.idvigilancia = vigilancia.idvigilancia\n" + CONDICIONES_VIGILANCIA +
                                                        "join sgtt_firmaresol firma on firma.NUMERORESOLUCION = multa.NUMERORESOLUCION";
   
  
    
  
   
   private static final String CONDICION_CON_MONTO = "\nhaving count(monto) = sum(monto_disponible)";
   private static final String CONDICION_SIN_MONTO = "\nhaving count(monto) <> sum(monto_disponible)";
   
   public static final String SELECT_CON_MONTO = SELECT_GENERAL + CONDICION_CON_MONTO;
   
   
   
    
    public static final String MULTA_GRUPOS = "select multa_grupo.fecha_registro, medio.NOMBRE as tipo_medio,\n" +
                                                    "tipo_multa.NOMBREMULTACOB as tipo_multa, multa_grupo.numero_multas,\n" +
                                                    "medio.idtipomedio as id_tipo_medio, tipo_multa.CONSTANTERESOLMOTIVO as id_tipo_multa, \n"  +
                                                    "firma.NOMBRE as tipo_firma, firma.IDFIRMATIPO as id_tipo_firma \n"+            
            " from (select multa.FECHAREGISTRO as fecha_registro,\n" +
                                                    "count(multa.NUMERORESOLUCION) as numero_multas,\n" +
                                                    "vigilancia.idtipomedio, multa.CONSTANTERESOLMOTIVO, vigilancia.IDTIPOFIRMA \n"  +
                                            "from sgtt_resoluciondoc multa\n" + 
                                                    "join sgtt_documento documento on multa.NUMEROCONTROL = documento.NUMEROCONTROL\n" +
                                                        "and multa.ULTIMOESTADO = :estadoMulta\n" +                                                       
                                                        "and multa.CONSTANTERESOLMOTIVO in (:tiposMulta)\n" +
                                                        CONDICION_ADMON_LOCAL + 
                                                    " join sgtt_vigilancia vigilancia on documento.idvigilancia = vigilancia.idvigilancia\n" +
                                                        "and vigilancia.IDNIVELEMISION = :nivelEmision \n" +
                                                        "and vigilancia.IDCARGOADMTVO = :cargoAdmtvo \n" +
                                                        "and vigilancia.IDTIPOMEDIO <> :medioNoConsiderar \n" +
                                            "group by  multa.FECHAREGISTRO, vigilancia.idtipomedio, multa.CONSTANTERESOLMOTIVO, \n" +
                                                    "vigilancia.IDTIPOFIRMA) multa_grupo \n" + 
                                                     "join SGTC_TIPOMEDIO medio on  medio.IDTIPOMEDIO = multa_grupo.IDTIPOMEDIO \n" +
                                                    "join admc_firmatipo firma on firma.IDFIRMATIPO = multa_grupo.IDTIPOFIRMA \n" +
                                                    "join sgtc_multacob tipo_multa on tipo_multa.CONSTANTERESOLMOTIVO = multa_grupo.CONSTANTERESOLMOTIVO";
    
    public static final String MULTA_DOCUMENTOS_PAGINADOS = "select NUMERORESOLUCION, NUMEROCONTROL, rfc, sumaMonto, boid\n" +
                                                                "from\n" +
                                                                "(\n" +
                                                                    "select NUMERORESOLUCION, NUMEROCONTROL, boid, sumaMonto, rfc, " +
                                                                            "row_number() over(order by NUMERORESOLUCION, NUMEROCONTROL, boid) num\n" +
                                                                    "from\n" +
                                                                        "(\n" +
                                                                            SELECT_CON_MONTO +
                                                                        ")\n" +
                                                                    ")select_interno \n" +
                                                                "where num >= :rowInicial and num < :rowFinal";
    
    
    public static final String MULTA_DOCUMENTOS_NO_APROBAR ="select NUMERORESOLUCION, NUMEROCONTROL, rfc, crhActual\n" +
                                                                "from\n" +
                                                                "(\n" +
                                                                        SELECT_GENERAL + CONDICION_SIN_MONTO +
                                                                 ")select_interno";
    
    public static final String EMITIDOS_REPORTE = "select NUMERORESOLUCION, rfc, crhActual \n" +
                                                  "from\n" +
                                                  "(\n" +
                                                     SELECT_CON_MONTO +
                                                   ") select_interno \n" +
                                                   "order by crhActual"; 
    public static final String MULTAS_TRASLADADAS = "select NUMERORESOLUCION, rfc, crhActual, \n" +
                                                    "admonLocal.idadmonlocal || ' ' || admonLocal.descripcion as destinoLocal \n" +
                                                  "from\n" +
                                                  "(\n" +
                                                     SELECT_CON_MONTO +
                                                   ") select_interno \n" +
                                                   "join sgtc_admonlocalsc admonLocal on admonLocal.idadmonlocal=select_interno.destinoLocal \n" +
                                                        "and admonLocal.fechafin is null";    
    
    
    
    public static final String MULTA_DETALLE="select obl.IDOBLIGACION, obl.DESCRIPCION, monto.monto,per.descripcionperiodo as PERIODO,det.ejercicio as EJERCICIO \n" +
        "from sgtt_resoluciondoc multa\n" +
            "join sgtt_documento documento on multa.NUMEROCONTROL = documento.NUMEROCONTROL\n" +
            "join SGTA_ResolICEP ric on multa.numeroResolucion = ric.numeroResolucion\n" +
            "join SGTT_DetalleDoc det on ric.numeroControl = det.numeroControl and ric.claveIcep = det.claveIcep and det.numeroControl = documento.numeroControl\n" +
            "inner join sgta_periodicidad per on (per.idperiodo = det.idperiodo and per.idperiodicidad = det.idperiodicidad) "+
            "join sgtc_obligacion obl on det.idObligacion = obl.idObligacion\n" +
            "left join SGTC_MultaMonto monto on det.IdObligacion = monto.IdObligacion and multa.constanteResolMotivo = monto.constanteResolMotivo and \n" +
             CONDICIONES_MONTO +
        "where ric.NUMEROCONTROL = :numeroControl and ric.NUMERORESOLUCION = :numeroResolucion";
    
    public static final String  CONSULTA_NUMERO_RESOLUCIONES = "select count (1)\n" +
                                                            "from(\n" +
                                                                 SELECT_CON_MONTO +
                                                                ")"; 
    public static final String EMITIR_RESOLUCIONES ="update sgtt_resoluciondoc set ULTIMOESTADO = :estadoDestino, EMPLEADODETERMINAEMISION = :numEmpleado , FECHADETERMINAEMISION = sysdate\n" +
                                                        "where NUMERORESOLUCION in\n" +
                                                        "(\n" +
                                                            "select NUMERORESOLUCION from (" + SELECT_CON_MONTO +  ")\n" +
                                                        ")";
    public static final String BITACORA_CAMBIO_ESTADO = "insert into sgtb_sgtresolucion(NUMERORESOLUCION, IDESTADORESOLUCION, FECHAMOVIMIENTO) \n" +
                                                        "select NUMERORESOLUCION, :estadoDestino, sysdate from (" + SELECT_CON_MONTO +  ")\n";
    
    public static final String CAMBIA_LOCAL_ORIGEN ="update sgtt_resoluciondoc set idadmonlocalorigen = idadmonlocalactual, \n" +
                                                        "fecharegistro = to_date(to_char(sysdate,'dd/MM/yyyy'),'dd/MM/yyyy')\n" +
                                                        " where NUMERORESOLUCION in\n" +
                                                        "(\n" +
                                                            "select NUMERORESOLUCION from (" + SELECT_CON_MONTO +  ")\n" +
                                                        ")";
    
    public static final String INSERTA_FIRMA_INDIVIDUAL = "insert into sgtt_firmaresol (numeroresolucion, cadenaoriginal, firmadigital, fechaempleadofirma, empleadofirma)\n" +
                                                            "values(:numControlResolucion, :cadenaOriginal, :firmaDigital, sysdate, :empleadoFirma)";
    
    public static final String CADENAS_ORIGINALES = "select cadenaOriginal from \n"+
                                                    "(\n" +
                                                        "select precadenaoriginal || :nombreFuncionario as cadenaOriginal, \n"+
                                                        "row_number() over(order by NUMERORESOLUCION, NUMEROCONTROL, boid) num\n"+
                                                        "from\n" +
                                                              "(\n" +
                                                                    SELECT_CON_MONTO +
                                                               "\n)" +
                                                    "\n)"+
                                                    "where num >= :rowInicial and num <= :rowFinal";
    
    public static final String ELIMINA_FIRMAS="delete from sgtt_firmaresol where numeroresolucion in " +
                                                            "(\n" +
                                                                "select numeroresolucion from " +
                                                                    "(\n" +
                                                                        SELECT_GENERAL_PARA_FIRMA +
                                                                    "\n)" +
                                                            "\n)";
    
    public static final String ACTUALIZA_LOCAL_ACTUAL_ORIGEN;
    
    static{
    ACTUALIZA_LOCAL_ACTUAL_ORIGEN="merge into sgtt_resoluciondoc\n" +
                                   "using(\n" +
                                        "SELECT doc.NUMERORESOLUCION, region.CODIGOREGION AS ClaveALR\n" +
                                        "FROM (" + MULTA_DOCUMENTOS_PAGINADOS + ") doc\n" +
                                        "join rfcp_domicilio dom on dom.IdPersona = doc.BoId\n" +
                                        "  and dom.IDTIPODOMICILIO= '32'\n" +
                                        "  AND (dom.FECHAFINVIGENCIA > SYSDATE OR dom.FECHAFINVIGENCIA IS NULL)\n" +
                                        "  AND dom.FECHAINICIOVIGENCIA <= SYSDATE\n" +
                                        "  AND (dom.FECHACREACION = \n" +
                                        "        (SELECT MAX (B2S1.FECHACREACION)\n" +
                                        "          FROM RFCP_DOMICILIO B2S1\n" +
                                        "          WHERE B2S1.IDPERSONA = dom.IDPERSONA\n" +
                                        "          AND B2S1.IDTIPODOMICILIO = '32'\n" +
                                        "          AND B2S1.FECHAINICIOVIGENCIA <= SYSDATE\n" +
                                        "          AND (B2S1.FECHAFINVIGENCIA > SYSDATE OR B2S1.FECHAFINVIGENCIA IS NULL)) \n" +
                                        "        OR dom.FECHAMODIFICACION IS NULL)\n" +
                                        "join RFCC_REGION crh on crh.IDREGION = dom.IDCRH\n" +
                                        "join (select CLAVEREGION,CODIGOREGION\n" +
                                        "  FROM RFCC_REGION@RFCD_DBLINK \n" +
                                        "   WHERE IDTIPOREGION IN\n" +
                                        "  (select IDCATALOGO\n" +
                                        "  from RFCC_CATALOGO@RFCD_DBLINK\n" +
                                        "  WHERE TIPO='TIPOREGION' and CLAVEELEMENTO='ALR' and \n" +
                                        "   FECHAINICIOVIGENCIA<=SYSDATE AND (FECHAFINVIGENCIA>SYSDATE OR FECHAFINVIGENCIA IS NULL))\n" +
                                        "  ) region on region.CLAVEREGION = crh.CLAVEPADRE"
                                + ") nueva_local \n" +
                                  "on (sgtt_resoluciondoc.NUMERORESOLUCION = nueva_local.NUMERORESOLUCION) \n" +
                                  "when matched then update set " + ACTUALIZA_ORIGEN +
                                         "idadmonlocalactual = nueva_local.ClaveALR";
    }
   
    
}
