package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql;

public interface BaseDocumentoSQL {

    String GENERAR_RESOLUCIONES = "PKG_COB.COBS_GENAGRNUMRES";
    String GENERAR_CONTROLES = "PKG_COB.COBS_GENNUMCTRLLT";
    String GENERAR_DOCUMENTOS_DETALLES = "PKG_COB.COBS_GENDOCUMDET";
    String GENERAR_NUMCONTROL = "COBF_GENCONTROL";
    String GENERAR_NUMRESOLUCION = "COBF_GENRESOLUCION";
    String TRUNCATE_TABLA_NUMRESOLUCION = "PKG_COB.COBS_TRUNCATETABLERESOL";
    String BORRAR_TABLA_NUMRESOLUCION = "delete from SGTT_NUMRESOLUCION";
    String OBTENER_TOTAL_NUM_CONTROL=
            "SELECT count(1) FROM sgtt_numresolucion";
    String OBTENER_DETALLE_NUM_RESOLUCION=
            "SELECT * FROM sgtt_numresolucion";
    String OBTENER_NUMERO_CONTROL_POR_BOID=
            "SELECT /*+INDEX_SS(nr) FIRST_ROWS(1) */ nr.numerocontrol FROM sgtt_numresolucion nr WHERE nr.numeroresolucion = ?";
    String GET_IDENTIFICADOR_NUMERO_CONTROL =
            "select generanumerocontrol(?,?,?) from dual";
    String GET_IDENTIFICADOR_NUMERO_RESOLUCION =
            "select generaresoluciones(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) from dual";
    String CONSULTAR_IDENTIFICADOR_DOCUMENTO =
            "select trim(to_char(SGTQ_NUMEROCONTROL.NEXTVAL,'0000000')) as identificador from dual";
    String CONSULTAR_TIPO_ONLY_ETAPA_SEQ =
                "select nvl(idtipodocetapa,0) "
                + "from sgta_tipodocetapa te "
                + "where te.idtipodocumento = ? "
                + "and te.idetapavigilancia = ? ";
    String CONSULTAR_TIPO_ETAPA_SEQ =
            "select nvl(idtipodocetapa,0)||'-'||trim(to_char(SGTQ_NUMEROCONTROL.NEXTVAL,'0000000')) "
            + "from sgta_tipodocetapa te "
            + "where te.idtipodocumento = ? "
            + "and te.idetapavigilancia = ? ";
    String CONSULTAR_TIPO_ETAPA_RESOL_MOTIVO =
            "select nvl(idmultacob,0)  ||'-'||trim(to_char(SGTQ_NUMEROCONTROL.NEXTVAL,'0000000')) "
            + "from sgtc_multacob "
            + "where constanteresolmotivo = ?";
    String CONSULTAR_DATOS_UBICACION =
            "SELECT"
            + "  BB.BO_ID,Z.SAT_REGION_CD ClaveALR,A.POSTAL CodigoPostal,"
            + "  Y.DESCR ClaveCRH,st.numeric_cd "
            + "FROM PS_BO_CM_USE BB "
            + "  INNER JOIN PS_BO_CM B "
            + "    ON (BB.BO_ID =? "
            + "      AND BB.PRIMARY_IND = 'Y' "
            + "      AND BB.CM_USE_START_DT <= SYSDATE "
            + "      AND (BB.CM_USE_END_DT > SYSDATE OR BB.CM_USE_END_DT IS NULL) "
            + "      AND (BB.ROLE_TYPE_ID = 2 OR BB.ROLE_TYPE_ID = 9) "
            + "      AND B.BO_ID = BB.BO_ID "
            + "      AND B.PROFILE_CM_SEQ = BB.PROFILE_CM_SEQ "
            + "      AND B.CM_PURPOSE_TYPE_ID = '32' "
            + "      AND B.CM_TYPE_ID        = '1' "
            + "      AND (B.BO_CM_END_DT > SYSDATE OR B.BO_CM_END_DT IS NULL) "
            + "      AND B.BO_CM_START_DT <= SYSDATE) "
            + "  INNER JOIN  PS_CM A "
            + "    ON (A.CM_ID = B.CM_ID AND A.CM_TYPE_ID = B.CM_TYPE_ID) "
            + "  inner join PS_STATE_TBL ST "
            + "    on (a.state = ST.state and st.country = 'MEX') "
            + "  INNER JOIN PS_SAT_RB_REGION X "
            + "    ON (X.REGION_ID = A.REGION_ID) "
            + "  INNER JOIN PS_RB_REGION Y "
            + "    ON (Y.REGION_ID = X.REGION_ID "
            + "      AND Y.REGION_CATEGORY = X.REGION_CATEGORY AND Y.REGION_TYPE = 'CRH') "
            + "  INNER JOIN (SELECT "
            + "      A.REGION_ID, "
            + "      A.SAT_REGION_CD "
            + "    FROM "
            + "      PS_SAT_RB_REGION A, "
            + "      PS_RB_REGION     B "
            + "    WHERE "
            + "      A.REGION_ID = B.REGION_ID "
            + "      AND A.REGION_CATEGORY = B.REGION_CATEGORY "
            + "      AND    B.REGION_TYPE  = 'ALR')Z "
            + "    ON (Z.REGION_ID = Y.PARENT_REGION_ID) ";
}
