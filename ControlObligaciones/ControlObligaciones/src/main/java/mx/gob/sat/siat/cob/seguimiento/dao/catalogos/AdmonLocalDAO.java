package mx.gob.sat.siat.cob.seguimiento.dao.catalogos;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.AdmonLocal;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;

public interface AdmonLocalDAO {
    
    List<AdmonLocal> todasLasAdmon();
    void agregaAdmon(AdmonLocal admonLocal) throws SeguimientoDAOException;
    void editaAdmon(AdmonLocal admonLocal) throws SeguimientoDAOException;
    void reactivaAdmon(AdmonLocal admonLocal) throws SeguimientoDAOException;
    Integer eliminaAdmon(AdmonLocal admonLocal) throws SeguimientoDAOException;
    Integer buscarAdmonPorId(AdmonLocal admonLocal);
}
