package mx.gob.sat.siat.cob.seguimiento.dao.cobranza;

import java.util.Date;
import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.DiaInhabil;
import mx.gob.sat.siat.cob.seguimiento.exception.CobranzaDAOException;

/**
 *
 * @author root
 */
public interface DiaInhabilDAO {
    /**
     *
     * @param fechaNotificacion
     * @return
     * @throws SGTDAOException
     */
    List<DiaInhabil> todosDiaInhabil(Date fechaNotificacion)throws CobranzaDAOException;
}