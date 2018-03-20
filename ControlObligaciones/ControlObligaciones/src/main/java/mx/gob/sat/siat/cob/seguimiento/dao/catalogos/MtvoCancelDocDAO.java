package mx.gob.sat.siat.cob.seguimiento.dao.catalogos;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.MtvoCancelDoc;


public interface MtvoCancelDocDAO {
    
     List<MtvoCancelDoc> todosLosMotivos();
     void agregaMotivo(MtvoCancelDoc mtvoCancelDoc) throws SeguimientoDAOException;
     void editaMotivo(MtvoCancelDoc mtvoCancelDoc) throws SeguimientoDAOException;
     Integer eliminaMotivo(MtvoCancelDoc mtvoCancelDoc) throws SeguimientoDAOException;
     MtvoCancelDoc buscaMotivoPorID(MtvoCancelDoc mtvoCancelDoc);
     Integer buscarMotivoPorIdYNom(MtvoCancelDoc mtvoCancelDoc);
}
