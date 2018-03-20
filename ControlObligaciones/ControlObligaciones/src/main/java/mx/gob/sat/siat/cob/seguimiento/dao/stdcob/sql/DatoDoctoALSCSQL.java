package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql;

public interface DatoDoctoALSCSQL {

    String CONSULTA_ALSC_X_CLV_SIR = "SELECT * \n"
            + "FROM \n"
            + "  CBZC_UnidadAdmva uni\n"
            + "  inner join \n"
            + "    CBZP_Domicilio dom \n"
            + "  on \n"
            + "    (uni.idDomicilio = dom.idDomicilio)\n"
            + "WHERE \n"
            + "  uni.idUnidadmvaTipo = 15 \n"
            + "  and uni.fechafin is null\n"
            + "  and uni.clave_sir = ";
}
