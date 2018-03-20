package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql;

public interface DatoDoctoVigilanciasSQL {

    String CONSULTA_DATO_DOCTO_X_VIG = "SELECT\n"
            + "      idvigilancia,\n"
            + "      idetapavigilancia,\n"
            + "      idtipodocumento,\n"
            + "      numerocontrol,\n"
            + "      rfc,\n"
            + "      boid\n"
            + "    FROM(\n"
            + "      SELECT\n"
            + "        b.idvigilancia,\n"
            + "        a.idetapavigilancia,\n"
            + "        b.idtipodocumento,\n"
            + "        a.numerocontrol,\n"
            + "        a.rfc,\n"
            + "        a.boid, rownum numfila\n"
            + "      FROM\n"
            + "        SGTT_DOCUMENTO a\n"
            + "        INNER JOIN SGTT_VIGILANCIA b ON a.idvigilancia = b.idvigilancia\n"
            + "      WHERE\n"
            + "        a.ultimoestado = 0";
}
