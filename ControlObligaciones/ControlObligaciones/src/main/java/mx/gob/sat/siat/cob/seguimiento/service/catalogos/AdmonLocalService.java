package mx.gob.sat.siat.cob.seguimiento.service.catalogos;

import java.io.ByteArrayOutputStream;
import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.AdmonLocal;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.exception.DaoException;
import mx.gob.sat.siat.modelo.dto.SegbMovimientoDTO;

public interface AdmonLocalService {
    
    List<AdmonLocal> todasLasAdmon();
    void agregaAdmon(AdmonLocal admonLocal, SegbMovimientoDTO segMovDto)throws SeguimientoDAOException, DaoException;
    void editaAdmon(AdmonLocal admonLocal, SegbMovimientoDTO segMovDto)throws SeguimientoDAOException, DaoException;
    void reactivaAdmon(AdmonLocal admonLocal)throws SeguimientoDAOException;
    void eliminaAdmon(AdmonLocal admonLocal, SegbMovimientoDTO segMovDto) throws SeguimientoDAOException, DaoException;
    Integer buscaAdmonPorId(AdmonLocal admonLocal);
    ByteArrayOutputStream generaExcel(List<AdmonLocal> list);
    ByteArrayOutputStream generaPDF(List<AdmonLocal> list);
}
