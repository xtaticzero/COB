package mx.gob.sat.siat.cob.seguimiento.dao.catalogos;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.MotRechazoVig;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;

public interface MotRechazoVigDAO {
    
    List<MotRechazoVig> todosLosMotivos();
    void agregaMotivo(MotRechazoVig motRechazoVig) throws SeguimientoDAOException;
    void editaMotivo(MotRechazoVig motRechazoVig) throws SeguimientoDAOException;
    Integer eliminaMotivo(MotRechazoVig motRechazoVig) throws SeguimientoDAOException;
    MotRechazoVig buscaMotivoPorID(MotRechazoVig motRechazoVig);
    Integer buscarMotivoPorIdYNom(MotRechazoVig motRechazoVig);
}
