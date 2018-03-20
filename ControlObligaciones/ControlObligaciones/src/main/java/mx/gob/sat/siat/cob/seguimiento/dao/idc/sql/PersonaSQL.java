package mx.gob.sat.siat.cob.seguimiento.dao.idc.sql;

public interface PersonaSQL {
    String STORE_PROC_BUSCA_POR_BO_ID = "WSID.PKG_INT_IDENT.SP_INT_IDENT";
    String STORE_PROC_BUSCA_POR_RFC = "WSID.PKG_INT_RFCS.SP_INT_RFCS";
    String BUSQUEDA_POR_RFC ="SELECT P.*, PT.NOMBRE FROM CBZP_PERSONA P LEFT JOIN ADMC_PERTIPO PT ON P.IDPERTIPO = PT.IDPERTIPO WHERE RFC=?";
    String BUSQUEDA_POR_ID = "SELECT * FROM CBZP_PERSONA P LEFT JOIN ADMC_PERTIPO PT ON P.IDPERTIPO = PT.IDPERTIPO WHERE IDPERSONA=?";
    String BUSQUEDA_PERSONAFISICA_POR_ID="SELECT * FROM CBZP_PERFISICA WHERE IDPERSONA=?";
    String BUSQUEDA_PERSONAMORAL_POR_ID="SELECT * FROM CBZP_PERMORAL WHERE IDPERSONA=?";
    String INSERT_PERSONA="insert into cbzp_persona (idpersona, idpertipo, rfc, bo_id, idestatus) values(?,?,?,?,?)";
    String UPDATE_PERSONA="update cbzp_persona set idpertipo=?, rfc=?, bo_id=?, idestatus=? where idpersona=?";
    String BUSCAR_BY_RFC="SELECT P.*, PT.NOMBRE TIPO_PERSONA FROM CBZP_PERSONA P INNER JOIN ADMC_PERTIPO PT ON P.IDPERTIPO = PT.IDPERTIPO ";
    String SQS_PER_SQL="select cbzq_persona.nextval from dual";
}
