package mx.gob.sat.siat.cob.seguimiento.dao.rfcampliado;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Combo;


/**
 *
 * @author root
 */
public interface RfccObligacionDAO {
    /**
     *
     * @return
     */
    List<Combo> obtenerComboObligacion();
}
