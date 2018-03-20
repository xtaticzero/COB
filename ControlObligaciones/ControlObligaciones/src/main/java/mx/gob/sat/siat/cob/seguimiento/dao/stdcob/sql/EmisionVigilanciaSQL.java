package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql;

public interface EmisionVigilanciaSQL {

    String SELECT_EMISION_VIGILANCIA = "SELECT IDVIGILANCIA,\n"
            + "  IDTIPODOCUMENTO,\n"
            + "  IDETAPAVIGILANCIA,\n"
            + "  ESPLANTILLADIOT,\n"
            + "  IDESTADOEMISION \n"
            + "FROM \n"
            + "  SGTT_EMISIONVIG";
    String SELECT_EMISION_VIG_X_EDO_EMISION = "SELECT IDVIGILANCIA,\n"
            + "  IDTIPODOCUMENTO,\n"
            + "  IDETAPAVIGILANCIA,\n"
            + "  ESPLANTILLADIOT,\n"
            + "  IDESTADOEMISION \n"
            + "FROM \n"
            + "  SGTT_EMISIONVIG \n"
            + "WHERE \n"
            + "  IDESTADOEMISION = 0";
    String INSERT_EMISION_VIGILANCIA = "INSERT INTO SGTT_EMISIONVIG (\n"
            + "    IDVIGILANCIA,\n"
            + "    IDTIPODOCUMENTO,\n"
            + "    IDETAPAVIGILANCIA,\n"
            + "    ESPLANTILLADIOT, IDESTADOEMISION)\n"
            + "  VALUES (";
    String SELECT_VIGILANCIA_X_ETAPA = "SELECT \n"
            + "  idvigilancia,\n"
            + "  idetapavigilancia, \n"
            + "  idtipodocumento,\n"
            + "  esobligaciondiot as esplantilladiot \n"
            + "FROM(\n"
            + "  SELECT \n"
            + "    b.idvigilancia, \n"
            + "    b.esobligaciondiot,\n"
            + "    a.idetapavigilancia,\n"
            + "    b.idtipodocumento\n"
            + "  FROM \n"
            + "    SGTT_DOCUMENTO a\n"
            + "    INNER JOIN SGTT_VIGILANCIA b ON a.idvigilancia = b.idvigilancia\n"
            + "  WHERE\n"
            + "    a.ultimoestado = 0\n"
            + ")\n"
            + "GROUP by \n"
            + "  idvigilancia,\n"
            + "  idetapavigilancia, \n"
            + "  idtipodocumento,\n"
            + "  esobligaciondiot";
}
