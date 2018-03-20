package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql;

/**
 *
 * @author root
 */
public interface IcepsAprobarSQL {

    String LOCAL = "and doc.idadmonlocal=?";
    String FRAGMENTO_LOCAL = "fragmento_local";

    String CONTAR_ICEPS = "select count(1)\n"
            + "from sgtt_vigilancia vig\n"
            + "join sgtt_documento doc on doc.idvigilancia=vig.idvigilancia and vig.idvigilancia=?\n"
            + "join sgtt_detalledoc det on det.numerocontrol=doc.numerocontrol \n"
//            + "                            and doc.ultimoestado=" + EstadoDocumentoEnum.PENDIENTE_DE_PROCESAR.getValor() + " \n"
            //+" and det.idsituacionicep= "+SituacionIcepEnum.INCUMPLIDO.getValor()+ " \n "
            + "                            " + FRAGMENTO_LOCAL + "  ";

    String ICEPS_POR_PAGINA = "select fecha_corte,\n"
            + "       rfc,\n"
            + "       numero_control,\n"
            + "       clave_icep,\n"
            + "       descripcion_obligacion,\n"
            + "       ejercicio,\n"
            + "       periodo,\n"
            + "       estado_obligacion\n"
            + "FROM\n"
            + "(select /*+ FIRST_ROWS(20) index_ss(sit) index_ss(per)*/\n"
            + " vig.fechacorte fecha_corte,\n"
            //+ "       nvl(pm.rfc,pf.rfc) rfc,\n"
            + "        doc.rfc,\n"
            + "       doc.numerocontrol numero_control,\n"
            + "       det.idobligacion clave_icep,\n"
            + "       det.ejercicio ejercicio,\n"
            + "       per.descripcionperiodo periodo,\n"
            + "       obli.descripcion descripcion_obligacion,\n"
            + "       sit.nombre estado_obligacion,\n"
            + "       ROW_NUMBER() over (order by doc.numerocontrol,det.claveicep) num\n"
            + "from sgtt_vigilancia vig\n"
            + "join sgtt_documento doc on doc.idvigilancia=vig.idvigilancia and vig.idvigilancia= ? \n"
            //+ "left join rfcp_personamoral pm on pm.idpersona=doc.boid\n"
            //+ "left join rfcp_personafisica pf on pf.idpersona=doc.boid\n"
            + "join sgtt_detalledoc det on det.numerocontrol=doc.numerocontrol \n"
//            + "                            and doc.ultimoestado=" + EstadoDocumentoEnum.PENDIENTE_DE_PROCESAR.getValor() + " \n"
            //+" and det.idsituacionicep= "+SituacionIcepEnum.INCUMPLIDO.getValor()+ " \n "
            + "                           " + FRAGMENTO_LOCAL + "   \n"
            + "left join sgtc_obligacion obli on obli.idobligacion=det.idobligacion \n"
            //+ "                                  and obli.fechafin is null\n"
            + "left join sgtc_situacionicep sit on sit.idsituacionicep=det.idsituacionicep \n"
            //+ "                                    and sit.fechafin is null\n"
            +"join sgta_periodicidad per on per.idperiodicidad=det.idperiodicidad and per.idperiodo=det.idperiodo) \n"
            + " ICEP\n"
            + "where num >= ? and num < ? ";

    String PARAMETRO_ICEPS = "PARAMETRO_ICEPS";

    String ACTUALIZAR_ICEPS = "update  sgtt_detalledoc set\n"
            + "       fechacumplimiento= ? ,\n"
            + "       idsituacionicep= ? ,\n"
            + "       importepagar= ? ,\n"
            + "       idtipodeclaracion= ? ,\n"
            + "       idcumplimiento = ? , \n"
            + "       estadoicepec = ?  \n"
            + "       where numerocontrol= ? \n"
            + "     and claveicep= ?";
    
    String ACTUALIZAR_ICEPS_PADRON = "update  sgtt_detalledoc set\n"
            + "       idsituacionicep= ? \n"
            +" where numerocontrol= ? \n"
            + PARAMETRO_ICEPS;
}
