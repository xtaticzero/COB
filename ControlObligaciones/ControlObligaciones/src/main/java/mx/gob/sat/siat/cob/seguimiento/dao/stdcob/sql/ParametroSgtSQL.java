package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql;

public interface ParametroSgtSQL {

    String ACTUALIZAR_PARAMETROS_LIQUIDACION = "INSERT INTO SGTC_ParametroLiq values (SGTQ_ParametroLiq.NEXTVAL,?,?,?,?,?) ";
    String ACTUALIZAR_VIGENCIA_LIQUIDACION = "UPDATE SGTC_ParametroLiq set fechafin=sysdate where idparametroliq=(SELECT MAX(idparametroliq) FROM SGTC_ParametroLiq) ";
    String OBTENER_PARAMETROS_SGT = "SELECT * FROM SGTC_Parametro WHERE FECHAFIN IS NULL";
    String OBTENER_CAT_PARAMETRO_POR_CLAVE = "select  \n"
            + "  param.*, cat.valor\n"
            + "from SGTC_Parametro param\n"
            + "inner join SGTT_ParametroSGT cat \n"
            + "    on cat.idparametro=param.idparametro\n"
            + "where param.idparametro=? \n"
            + "and param.fechafin is null\n"
            + "and cat.fechaFin is null ";
    String OBTENER_PARAMETRO_SGT_POR_ID = "select * from SGTT_ParametroSGT where idparametro=? and fechafin is null";
    String ACTUALIZAR_PARAMETROS_SGT = "INSERT INTO SGTT_ParametroSGT values (?,?,?,?)";
    String ACTUALIZAR_VALOR_PARAMETRO_SGT = "UPDATE SGTT_ParametroSGT SET valor = ? WHERE idparametro=? and fechafin is null";
    String ACTUALIZAR_VIGENCIA_PARAMETROS_SGT = "UPDATE SGTT_ParametroSGT set fechafin=sysdate where idparametro=? and fechafin is null";
    String OBTENER_PARAMETROS_SGT_VIGENTES = "select \n"
            + "  cat.nombre, \n"
            + "  param.valor, \n"
            + "  param.idparametro,\n"
            + "  cat.tipoDato\n"
            + "from SGTT_ParametroSGT param \n"
            + "  inner join sgtc_parametro cat \n"
            + "    on cat.idparametro=param.idparametro \n"
            + "where param.fechafin is null\n"
            + "and cat.fechafin is null";
    int PARAMETRO_ORDEN = 1;
}
