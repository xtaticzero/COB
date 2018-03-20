package mx.gob.sat.siat.cob.seguimiento.dao.idc.sql;

public interface PersonaDomicilioSQL {
    String PERDOM_SQL =
        "select perD.*, dom.*, medioc.* from cbzd_per_domicilio perD left join cbzp_domicilio dom on perD.iddomicilio = dom.iddomicilio " +
        "left join cbzc_mediocontacto medioC on perD.idmediocontacto = medioc.idmediocontacto where perD.idpersona = ?";
    String ID_PERDOM_SQL = "select cbzq_perdomicilio.nextval from dual";
    String INSERT_PERDOM_SQL =
        "insert into cbzd_per_domicilio (idperdomicilio, idpersona, iddomicilio, idmediocontacto , fechainicio, fechafin ) values(?,?,?,?,sysdate,null)";
    String UPDATE_PERDOM_SQL =
        "update cbzd_per_domicilio set  iddomicilio=?, fechafin=?, idmediocontacto=? where idperdomicilio = ?";

}
