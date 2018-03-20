package mx.gob.sat.siat.cob.seguimiento.dao.catalogos;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.dto.compartidos.CatalogoBase;

/**
 *
 * @author root
 */
public interface PeriodoDAO {
    
    List<CatalogoBase> periodoXPeriodicidad(String periodicidad) throws SeguimientoDAOException;

}
