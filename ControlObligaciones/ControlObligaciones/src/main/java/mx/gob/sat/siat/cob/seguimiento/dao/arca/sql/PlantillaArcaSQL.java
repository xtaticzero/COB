/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.arca.sql;

/**
 *
 * @author root
 */
public interface PlantillaArcaSQL {

    String OBTENER_DOCUMENTO = "select cp.ftContenido64 contenido from\n"
            + "SIATControlDeObligaciones.T_Cat_DocumentoPlantilla cat\n"
            + "join SIATControlDeObligaciones.T_Plantilla_DocumentoPlantilla pdp\n"
            + "on (cat.id=pdp.iddocumento)\n"
            + "join SIATControlDeObligaciones.T_Cat_Plantilla cp\n"
            + "on pdp.IdPlantilla=cp.id\n"
            + "where cat.id= ? \n"
            + "order by fiOrdenDePlantilla";
    
    String BUSCAR_PLANTILLAS_DB_ARCA= "select id, fcDescripcionCorta from SIATControlDeObligaciones.T_CAT_DOCUMENTOPLANTILLA";
    
}
