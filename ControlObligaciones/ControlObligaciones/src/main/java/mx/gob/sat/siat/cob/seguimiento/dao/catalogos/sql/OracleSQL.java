package mx.gob.sat.siat.cob.seguimiento.dao.catalogos.sql;

public interface OracleSQL {
    String CONSULTAR_FECHA_ACTUAL = "SELECT SYSDATE AS FECHA_ACTUAL FROM DUAL";
}
