package mx.gob.sat.siat.cob.seguimiento.dao.rfcampliado;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.ComboStr;

/**
 *
 * @author root
 */
public interface RfccCatalogoDAO {
    
    /**
     * 
     * @return
     */
    List<ComboStr> buscarRegionALR();
}
