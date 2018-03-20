/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.arca;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.PlantillaArcaSQL;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.dto.compartidos.CatalogoBase;

/**
 *
 * @author eduardo.romero
 */
public interface PlantillaArcaDAO extends PlantillaArcaSQL{

    /**
     *
     * @return @throws SeguimientoDAOException
     */
    List<CatalogoBase> buscarPlantillasDBArca() throws SeguimientoDAOException;
    
    List<byte[]> buscarArchivoPlantilla(int idPlantilla);
}
