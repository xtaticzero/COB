package mx.gob.sat.siat.cob.seguimiento.br;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.PlantillaDocumento;

/**
 *
 * @author root
 */
public interface CargarAsignarPlantillaBR {
    
    /**
     *
     * @param plantilla
     * @param listaPlantillas
     * @return
     */
    int existePlantillaRepetida(PlantillaDocumento plantilla, List<PlantillaDocumento> listaPlantillas);
   /**
     *
     * @param fileName
     * @return
     */
    boolean validarArchivo(final String fileName);
}
