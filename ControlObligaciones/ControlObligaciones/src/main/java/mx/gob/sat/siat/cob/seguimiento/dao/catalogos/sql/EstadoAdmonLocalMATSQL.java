package mx.gob.sat.siat.cob.seguimiento.dao.catalogos.sql;

public interface EstadoAdmonLocalMATSQL {

    String OBTEN_TODOS_ESTADOS_ADMONLOCAL_MAT = "select al.idadmonlocal, admon.nombre, al.esoperacionmat from sgtc_alregistrocbz al\n"
            + "inner join sgtc_admonlocalsc admon on al.idadmonlocal = admon.idadmonlocal\n"
            + "order by al.idadmonlocal ";
    String EDITA_ESTADO_ADMONLOCAL_MAT = "update sgtc_alregistrocbz set esoperacionmat = ? where idadmonlocal = ? ";
    String LISTA_COMBO_ESTADO = "select idvalorboolean as ID, nombre as DESCRIPCION from sgtc_valorboolean order by descripcion ";
    /*
     * Consulta que determina si existe la local y saber si es operacion mat.
     */
    String CONSULTA_IDADMONLOCAL = "select al.idadmonlocal, admon.nombre, al.esoperacionmat from sgtc_alregistrocbz al\n"
            + "inner join sgtc_admonlocalsc admon on al.idadmonlocal = admon.idadmonlocal\n"
            + " where al.idAdmonLocal = ?";
}
