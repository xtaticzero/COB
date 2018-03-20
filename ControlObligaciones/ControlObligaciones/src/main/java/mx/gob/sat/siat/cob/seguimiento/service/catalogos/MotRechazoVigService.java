package mx.gob.sat.siat.cob.seguimiento.service.catalogos;

import java.io.ByteArrayOutputStream;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.MotRechazoVig;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.exception.DaoException;
import mx.gob.sat.siat.modelo.dto.SegbMovimientoDTO;

public interface MotRechazoVigService {
    
    List<MotRechazoVig> todosLosMotivos();
    void agregaMotivo(MotRechazoVig motRechazoVig, SegbMovimientoDTO segMovDto) throws SeguimientoDAOException, DaoException;
    void editaMotivo(MotRechazoVig motRechazoVig, SegbMovimientoDTO segMovDto) throws SeguimientoDAOException, DaoException;
    void eliminaMotivo(MotRechazoVig motRechazoVig, SegbMovimientoDTO segMovDto) throws SeguimientoDAOException, DaoException;
    Integer buscaMotivoPorIdYNom(MotRechazoVig motRechazoVig);
    ByteArrayOutputStream generaExcel(List<MotRechazoVig> list);
    ByteArrayOutputStream generaPDF(List<MotRechazoVig> list);
}
