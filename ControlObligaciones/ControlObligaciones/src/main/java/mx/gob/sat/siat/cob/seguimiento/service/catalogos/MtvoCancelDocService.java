package mx.gob.sat.siat.cob.seguimiento.service.catalogos;

import java.io.ByteArrayOutputStream;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.MtvoCancelDoc;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.exception.DaoException;
import mx.gob.sat.siat.modelo.dto.SegbMovimientoDTO;


public interface MtvoCancelDocService {
    
     List<MtvoCancelDoc> todosLosMotivos();
     void agregaMotivo(MtvoCancelDoc mtvoCancelDoc, SegbMovimientoDTO segMovDto) throws SeguimientoDAOException, DaoException;
     void editaMotivo(MtvoCancelDoc mtvoCancelDoc, SegbMovimientoDTO segMovDto) throws SeguimientoDAOException, DaoException;
     void eliminaMotivo(MtvoCancelDoc mtvoCancelDoc, SegbMovimientoDTO segMovDto) throws SeguimientoDAOException, DaoException;
     Integer buscaMotivoPorIdYNom(MtvoCancelDoc mtvoCancelDoc);
     ByteArrayOutputStream generaExcel(List<MtvoCancelDoc> list);
     ByteArrayOutputStream generaPDF(List<MtvoCancelDoc> list);

}
