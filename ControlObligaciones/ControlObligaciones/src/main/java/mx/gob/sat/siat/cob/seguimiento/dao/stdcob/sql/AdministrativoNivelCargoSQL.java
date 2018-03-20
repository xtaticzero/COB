package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql;

/**
 *
 * @author root
 */
public interface AdministrativoNivelCargoSQL {

    String BUSCAR_POR_NUM_EMPLEADO = "select idcargoadmtvo,\n"
            + "       numeroempleado,\n"
            + "       idadmonlocal,\n"
            + "       idnivelemision,\n"
            + "       ideventocarga \n"
            + "from sgta_admtvonivlcgo \n"
            + "where numeroempleado=? \n"
            + "and ideventocarga=? \n"
            + "and fechafin is null";
    String BUSCAR_POR_EVENTO_CARGA = "select idcargoadmtvo,\n"
            + "       numeroempleado,\n"
            + "       idadmonlocal,\n"
            + "       idnivelemision,\n"
            + "       ideventocarga \n"
            + "from sgta_admtvonivlcgo \n"
            + "where ideventocarga=? \n"
            + "and fechafin is null";
    String BUSCAR_POR_EVENTO_CARGA_EMISION = "select adm.idcargoadmtvo,\n"
            + "       adm.numeroempleado,\n"
            + "       adm.idadmonlocal,\n"
            + "       adm.idnivelemision,\n"
            + "       adm.ideventocarga,\n"
            + "       fun.nombrefuncionario,\n"
            + "       fun.correoelectronico,\n"
            + "       fun.correoelectronicoalterno\n"
            + "from sgta_admtvonivlcgo adm\n"
            + "join sgtp_funcionario fun on (adm.numeroempleado = fun.numeroempleado)\n"
            + "where adm.ideventocarga=?\n"
            + "and adm.fechafin is null\n"
            + "and adm.idnivelemision = 2";
    String EMPLEADOS_CENTRALES = "SELECT * FROM SGTA_ADMTVONIVLCGO "
            + "where IDEVENTOCARGA=1 and fechafin is null and IDNIVELEMISION=2 and idcargoadmtvo in "
            + "(select idcargoadmtvo from sgtt_vigilancia where idvigilancia=? )";
    String EMPLEADOS_LOCALES = "SELECT * FROM SGTA_ADMTVONIVLCGO "
            + "where IDEVENTOCARGA=1 and fechafin is null and IDNIVELEMISION=1 "
            + "and IDADMONLOCAL in (SELECT unique IDADMONLOCAL FROM SGTT_VIGILANCIAAL where idvigilancia = ? ) "
            + "and idcargoadmtvo in (select idcargoadmtvo from sgtt_vigilancia where idvigilancia=? )";
    String CONSULTA_EMPLEADOS = "SELECT ad.*, ca.nombre as cadesc, em.nombre as emdesc , ev.nombre as evdesc , func.NOMBREFUNCIONARIO as nombre, adm.DESCRIPCION as descripcionalsc FROM sgta_admtvonivlcgo ad "
            + " inner join sgtc_cargoadmtvo ca on ca.idcargoadmtvo=ad.idcargoadmtvo"
            + " left join SGTC_ADMONLOCALSC adm on adm.IDADMONLOCAL=ad.IDADMONLOCAL "
            + " inner join sgtp_funcionario func on func.NUMEROEMPLEADO=ad.NUMEROEMPLEADO "
            + " inner join sgtc_nivelemision em on em.idnivelemision=ad.idnivelemision"
            + " inner join sgtc_eventocarga ev on ev.ideventocarga=ad.ideventocarga order by ad.numeroempleado";
    String ACTUALIZAR_FUNCIONARIO = "update sgta_admtvonivlcgo set IDCARGOADMTVO=?, IDNIVELEMISION=?, IDADMONLOCAL=? where numeroempleado=? and ideventocarga=?";
    String HABILITAR_FUNCIONARIO = "update sgta_admtvonivlcgo set fechafin=null where numeroempleado= ? and ideventocarga= ?";
    String ELIMINAR_FUNCIONARIO = "update sgta_admtvonivlcgo set fechafin=sysdate where numeroempleado='{0}'and ideventocarga='{1}'";
    String INSERTAR_FUNCIONARIO = "INSERT INTO sgta_admtvonivlcgo values ({0},'{1}','{2}',{3},{4},{5},{6})";
    String BUSCAR_CARGOS_ADMINISTRATIVOS = "select idcargoadmtvo as ID,nombre as DESCRIPCION from sgtc_cargoadmtvo";
    String BUSCAR_NIVELES_EMISION = "select idnivelemision as ID,nombre as DESCRIPCION from sgtc_nivelemision";
    String BUSCAR_EVENTO_CARGA = "select ideventocarga as ID, nombre as DESCRIPCION from sgtc_eventocarga";
    String BUSCAR_REGION_ALR = "select IDADMONLOCAL, descripcion from SGTC_ADMONLOCALSC where fechaFin is null";
    String BUSCAR_CARGO_POR_NIVEL_EMISION = "select cargo.idcargoadmtvo as ID,cargo.nombre as DESCRIPCION from "
            + "sgtc_cargoadmtvo cargo inner join sgtc_nivelemision nivel on cargo.idnivelemision=nivel.idnivelemision where "
            + "nivel.idnivelemision=?";
    String OBTENER_EMPLEADOS_ADMTVO = "select numeroempleado as id, nombrefuncionario as descripcion from SGTP_Funcionario";
    String BUSCAR_FUNCIONARIOS_EXISTENTES = "select * from sgta_admtvonivlcgo where idnivelemision=? and idcargoadmtvo=? and idadmonlocal=?";
    String BUSCAR_FUNCIONARIOS_EXISTENTES_NULL = "select * from sgta_admtvonivlcgo where idnivelemision=? and idcargoadmtvo=? and idadmonlocal IS NULL and fechafin is null";
    String BUSCAR_FUNCIONARIOS_NUMEVENTO = "select * from sgta_admtvonivlcgo where numeroempleado=? and ideventocarga=? and idnivelemision=?";
    String BAJA_EMPLEADOS_EXISTENTES = "UPDATE sgta_admtvonivlcgo set fechafin=sysdate where numeroempleado"
            + " in(select numeroempleado from sgta_admtvonivlcgo where idnivelemision=? and idcargoadmtvo=? and idadmonlocal=? and numeroempleado!=?) and ideventocarga=1";
    String BAJA_EMPLEADOS_EXISTENTES_NULL = "UPDATE sgta_admtvonivlcgo set fechafin=sysdate where numeroempleado"
            + " in(select numeroempleado from sgta_admtvonivlcgo where idnivelemision=? and idcargoadmtvo=? and idadmonlocal IS NULL and numeroempleado!=?) and ideventocarga=1";
}
