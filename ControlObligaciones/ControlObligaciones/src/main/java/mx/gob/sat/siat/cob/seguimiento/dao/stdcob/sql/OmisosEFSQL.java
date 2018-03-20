package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoMedioEnvioEnum;

/**
 *
 * @author christian.ventura
 */
public interface OmisosEFSQL {

    String OBTENER_RFC_NOMBRE =
            "select /*+ index_ss(detdoc) DRIVING_SITE(pf) DRIVING_SITE(pm) */ \n" +
            " doc.identidadfederativa,doc.idadmonlocal,doc.boid,doc.numerocontrol,\n" +
            " to_char(doc.fecharegistro,'YYYY/MM/DD') as fecha,\n" +
            " trim(pf.rfc) || pm.rfc as rfc, trim(pf.nombre||' '||pf.apellidopaterno||' '||pf.apellidomaterno) || ( pm.razonsocial || " +
            " (select /*+ DRIVING_SITE(cat) */ ', '||cat.descorta from SIAT_SGT.RFCC_CATALOGO cat where cat.IDCATALOGO = pm.idregimencapital ) ) as nombre, \n" +
            " detdoc.idobligacion||'|'||detdoc.ejercicio||'|'||detdoc.idperiodo||'|'||detdoc.idregimen as obligaciones \n" +
            " from SIAT_SGT.sgtt_vigilancia vig \n" +
            " inner join SIAT_SGT.sgtt_documento doc on (doc.idvigilancia=vig.idvigilancia and doc.idVigilancia=? and doc.identidadfederativa = ?) \n" +
            " inner join SIAT_SGT.SGTT_DETALLEDOC detdoc on detdoc.numerocontrol=doc.numerocontrol\n" +
            " left join SIAT_SGT.RFCP_PERSONAFISICA pf on pf.idpersona=doc.boid \n" +
            " left join SIAT_SGT.RFCP_PERSONAMORAL pm on pm.idpersona=doc.boid ";

    String OBTENER_DATOS_UBICACION_PERSONA =
            "SELECT "+
            "     A.CLAVEENTIDADFED, "+
            "     A.IDPERSONA, "+
            "     A.NOMBREVIALIDAD AS Calle, "+
            "     A.NUMEROEXTERIOR AS NumeroExterior, "+
            "     trim(A.NUMEROINTERIOR) AS NumeroInterior, "+
            "     D.CLAVECOLONIA AS ClaveColonia, "+
            "     D.COLONIA AS DescripcionColonia, "+
            "     E.CLAVELOCALIDAD AS ClaveLocalidad, "+
            "     E.LOCALIDAD AS DescripconLocalidad, "+
            "     trim(A.ENTREVIALIDAD1) AS EntreCalle1, "+
            "     trim(A.ENTREVIALIDAD2) AS EntreCalle2, "+
            "     A.REFERENCIAS AS ReferenciaAdicionales, "+
            "     a.CODIGOPOSTAL as CODIGOPOSTAL, "+
            "     F.CLAVEMUNICIPIO AS ClaveMunicipio "+
            " FROM RFCP_DOMICILIO a "+
            " LEFT JOIN RFCC_COLONIA d "+
            "   ON     d.CLAVEENTIDADFED = a.CLAVEENTIDADFED "+
            "   AND d.IDMUNICIPIO = a.IDMUNICIPIO "+
            "   AND d.IDCOLONIA = a.IDCOLONIA "+
            " LEFT JOIN RFCC_LOCALIDAD e "+
            "   ON     e.CLAVEENTIDADFED = a.CLAVEENTIDADFED "+
            "   AND e.IDMUNICIPIO = a.IDMUNICIPIO "+
            "   AND e.IDLOCALIDAD = a.IDLOCALIDAD "+
            " LEFT JOIN RFCC_MUNICIPIO f "+
            "   ON f.CLAVEENTIDADFED = a.CLAVEENTIDADFED "+
            "   AND f.IDMUNICIPIO = a.IDMUNICIPIO "+
            " where "+
            " A.IDPERSONA = ? "+
            " and A.IDTIPODOMICILIO = '32'"+
            " AND (A.FECHAFINVIGENCIA > SYSDATE OR A.FECHAFINVIGENCIA IS NULL) "+
            " AND (A.FECHACREACION =  "+
            " (SELECT MAX (B2S1.FECHACREACION) "+
            " FROM RFCP_DOMICILIO B2S1 "+
            " WHERE B2S1.IDPERSONA = A.IDPERSONA "+
            " AND B2S1.IDTIPODOMICILIO = '32' "+
            " AND B2S1.FECHAINICIOVIGENCIA <= SYSDATE "+
            " AND (B2S1.FECHAFINVIGENCIA > SYSDATE OR B2S1.FECHAFINVIGENCIA IS NULL)) OR A.FECHAMODIFICACION IS NULL)"+
            " AND ROWNUM = 1 ";


    String OBTENER_FUNDAMENTOS_LEGALES =
            "select /*+DRIVING_SITE(d) DRIVING_SITE(dd) DRIVING_SITE(ob) " +
            "DRIVING_SITE(fl) DRIVING_SITE(per) index_ss(ob) index_ss(dd) */ " +
            "distinct "+
            "d.IDVIGILANCIA," +
            "OB.IDOBLIGACION," +
            "DD.EJERCICIO," +
            "DD.IDPERIODO," +
            "DD.IDREGIMEN," +
            "RR.DESCORTA," +
            "OB.DESCRIPCION," +
            "per.DESCRIPCIONPERIODO," +
            "FL.FUNDAMENTOLEGAL, "+
            "to_char(FL.FECHAVENCIMIENTOOBL,'YYYY-MM-DD') as CHARFECHAVENCIMIENTO " +
            "from "+
            "sgtt_documento d, "+
            "SGTT_DETALLEDOC dd, "+
            "SGTC_OBLIGACION ob, "+
            "RFCC_REGIMEN rr, "+
            "sgtc_fundamentoleg fl, "+
            "SGTA_PERIODICIDAD per "+
            "where "+
            "d.idvigilancia = ? "+
            "and d.identidadfederativa = ? "+
            "and d.numerocontrol = dd.numerocontrol "+
            "and ob.idobligacion = dd.idobligacion "+
            "and rr.idregimen = dd.idregimen "+
            "and fl.idobligacion = dd.idobligacion "+
            "and fl.idregimen = dd.idregimen "+
            "and fl.idperiodo = dd.idperiodo "+
            "and fl.idperiodicidad = dd.idperiodicidad "+
            "and fl.idejerciciofiscal=dd.ejercicio "+
            "and fl.fechafin is null "+
            "and fl.idperiodo = per.idperiodo "+
            "and fl.idperiodicidad = per.idperiodicidad";

    String INSERT_VIGILANCIA_EF =
            "INSERT INTO SGTT_VIGILANCIAEF " +
            "select IDVIGILANCIA,nvl(identidadfederativa,0),count(*), " +
            "?, ?, 1, null, 1, null, ?, sysdate " +
            "FROM SGTT_DOCUMENTO " +
            "WHERE IDVIGILANCIA in ( " +
            " select IDVIGILANCIA from sgtt_vigilancia where idtipomedio= " +
            TipoMedioEnvioEnum.ENTIDAD_FEDERATIVA.getValor()+
            " and idestadovigilancia=1 and idvigilancia=? " +
            ") and identidadfederativa = ? " +
            "GROUP BY idvigilancia, nvl(identidadfederativa,0), ?, " +
            " ?, 1, null, 1, null, ?, sysdate ";

    String OBTENER_ENCABEZADO_FACTURA =
            "select ' '||count(doc.numerocontrol) as pCantidadDocs, d."
            + "descripcion as pDescVigilancia,e.descripcion as pDescEntidadFed " +
            "from " +
            "sgtt_documento doc, " +
            "sgtt_vigilancia v, " +
            "SGTC_DSCRIPCIONVIG d, " +
            "RFCC_ENTIDADFEDERA e " +
            "where v.idvigilancia = ? " +
            "and doc.idvigilancia=v.idvigilancia " +
            "and v.iddescripcion=d.iddescripcion " +
            "and doc.identidadfederativa = e.numeroentidad " +
            "and doc.identidadfederativa = ? " +
            "group by d.descripcion, e.descripcion";

    String OBTENER_ENTIDADES_FEDERATIVAS=
            "select distinct d.identidadfederativa from sgtt_documento d, sgtt_vigilancia vig "+
            "where d.idvigilancia=? and vig.idtipomedio="+TipoMedioEnvioEnum.ENTIDAD_FEDERATIVA.getValor()+
            " and d.idvigilancia=vig.idvigilancia order by d.identidadfederativa";

    String OBTENER_DETALLE_FACTURA =
            "select rownum,b.* "+
            "from ( "+
            "select /*+ DRIVING_SITE(pf) DRIVING_SITE(pm) */ d.numerocontrol,trim(pf.rfc) || pm.rfc as rfc,d.codigopostal "+
            "from sgtt_documento d left join RFCP_PERSONAFISICA pf on pf.idpersona=d.boid "+
             "left join RFCP_PERSONAMORAL pm on pm.idpersona=d.boid "+
            "where d.idvigilancia in (?) and d.identidadfederativa in (?) "+
            "order by d.codigopostal,d.rfc "+
            ") b ";
}
