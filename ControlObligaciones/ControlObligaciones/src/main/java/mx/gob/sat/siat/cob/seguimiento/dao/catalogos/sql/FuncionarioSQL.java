package mx.gob.sat.siat.cob.seguimiento.dao.catalogos.sql;

public interface FuncionarioSQL {

    String OBTEN_TODOS_FUNCIONARIOS = "SELECT F.NUMEROEMPLEADO, F.NOMBREFUNCIONARIO, F.CORREOELECTRONICO, F.CORREOELECTRONICOALTERNO, "
            + " F.FECHAINICIO, F.FECHAFIN, F.IDAREAADSCRIPCION, AREA.NOMBRE, F.DESCRIPCIONCARGO "
            + " FROM SGTP_FUNCIONARIO F "
            + " INNER JOIN SGTC_AREAADSCRIP AREA on AREA.IDAREAADSCRIPCION = F.IDAREAADSCRIPCION"
            + " order by F.FECHAINICIO desc ";
    String AGREGA_FUNCIONARIO = "INSERT INTO SGTP_FUNCIONARIO (NUMEROEMPLEADO, NOMBREFUNCIONARIO, "
            + "CORREOELECTRONICO, CORREOELECTRONICOALTERNO, FECHAINICIO, FECHAFIN, IDAREAADSCRIPCION, DESCRIPCIONCARGO) "
            + "VALUES(LPAD(?,11,'0'), ?, ?, ?, ?, ?, ?, ?)";
    String EDITA_FUNCIONARIO = "UPDATE SGTP_FUNCIONARIO SET NOMBREFUNCIONARIO =? , CORREOELECTRONICO =? ,"
            + "CORREOELECTRONICOALTERNO =?, IDAREAADSCRIPCION =?, DESCRIPCIONCARGO =? WHERE NUMEROEMPLEADO = ?";
    String ELIMINA_FUNCIONARIO = "UPDATE SGTP_FUNCIONARIO SET FECHAFIN = ? WHERE NUMEROEMPLEADO = ?";
    String BUSCA_FUNCIONARIO_POR_ID = "SELECT f.numeroempleado,"
            + "f.correoelectronico,"
            + "f.correoelectronicoalterno,"
            + "f.fechainicio,"
            + "f.fechafin,"
            + "f.nombrefuncionario,"
            + "F.IDAREAADSCRIPCION,"
            + "AREA.NOMBRE "
            + "FROM SGTP_FUNCIONARIO F "
            + "JOIN SGTC_AREAADSCRIP AREA on AREA.IDAREAADSCRIPCION = F.IDAREAADSCRIPCION "
            + "WHERE NUMEROEMPLEADO =?";
    String BUSCA_FUNCIONARIO_POR_IDYNOM = "SELECT COUNT(*) REGISTROS FROM SGTP_FUNCIONARIO WHERE NUMEROEMPLEADO= ? ";
    String REACTIVA_FUNCIONARIO = "UPDATE SGTP_FUNCIONARIO SET FECHAFIN = NULL WHERE NUMEROEMPLEADO = ?";
    String LISTA_COMBO_AREA = " select idareaadscripcion as ID, nombre as DESCRIPCION from SGTC_AREAADSCRIP order by descripcion ";
    String INSERT_ADMTVONIVLCGO = "INSERT INTO SGTA_AdmtvoNivlBK \n"
            + "( select * from sgta_admtvonivlcgo\n"
            + "  where ideventoCarga = 1\n"
            + "    and fechaFin is null)";
    String INSERT_FUNCIONARIO = "INSERT INTO SGTP_FuncionarioBK \n"
            + "( select * from sgtp_funcionario\n"
            + "  where fechaFin is null) ";
    String DELETE_ADMTVONIVLCGO = "delete SGTA_AdmtvoNivlBK";
    String DELETE_FUNCIONARIO = "delete SGTP_FuncionarioBK";
}
