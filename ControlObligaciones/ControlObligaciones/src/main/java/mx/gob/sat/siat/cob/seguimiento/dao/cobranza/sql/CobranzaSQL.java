package mx.gob.sat.siat.cob.seguimiento.dao.cobranza.sql;

public interface CobranzaSQL {

    String LISTA_TIPO_FIRMA =
            "select idfirmatipo as id, nombre from ADMC_FIRMATIPO where FECHAFIN is null";

    String LISTA_COMBO_RESOL_MOTIVO =
            "select  o.CONSTANTERESOLMOTIVO as ID, o.NOMBREMULTACOB as DESCRIPCION  from sgtc_multacob o  WHERE O.FECHAFIN IS NULL ORDER BY IDMULTACOB";
}
