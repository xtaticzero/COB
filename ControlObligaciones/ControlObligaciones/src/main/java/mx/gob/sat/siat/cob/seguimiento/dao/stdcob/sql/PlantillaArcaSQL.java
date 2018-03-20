/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql;

/**
 *
 * @author Juan
 */
public interface PlantillaArcaSQL {

    String INSERT_PLANTILLA_ARCA_BATCH = "INSERT INTO SGTT_PlantillaBACK \n"
            + "(\n"
            + "  SELECT * FROM SGTT_PlantillaArca plantilla\n"
            + "    WHERE plantilla.dias IS NULL\n"
            + "    AND plantilla.constanteResolMotivo IS NULL\n"
            + "    AND plantilla.fechafin IS NULL \n"
            + "    AND plantilla.esMulta = ? \n"
            + ")";
    String INSERT_PLANTILLA_MULTA_CREDITO = "INSERT INTO SGTT_PlantillaBACK \n"
            + "(\n"
            + "  SELECT * FROM SGTT_PlantillaArca plantilla\n"
            + "    WHERE plantilla.dias IS NULL\n"
            + "    AND plantilla.constanteResolMotivo IS NOT NULL\n"
            + "    AND plantilla.fechafin IS NULL \n"
            + "    AND plantilla.esMulta = ? \n"
            + ")";
    String DELETE_PLANTILLA_ARCA_BATCH = "DELETE FROM SGTT_PlantillaBACK";
}
