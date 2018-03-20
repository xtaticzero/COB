package mx.gob.sat.siat.cob.seguimiento.service.catalogos;

import java.io.ByteArrayOutputStream;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.EjercicioFiscal;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.exception.DaoException;
import mx.gob.sat.siat.modelo.dto.SegbMovimientoDTO;


public interface EjercicioFiscalService {
    
     List<EjercicioFiscal> todosLosEjercicios();
     void agregaEjercicio(EjercicioFiscal ejercicioFiscal, SegbMovimientoDTO segMovDto)throws SeguimientoDAOException, DaoException;
     void editaEjercicio(EjercicioFiscal ejercicioFiscal, SegbMovimientoDTO segMovDto)throws SeguimientoDAOException, DaoException;
     void reactivaEjercicio(EjercicioFiscal ejercicioFiscal)throws SeguimientoDAOException;
     void eliminaEjercicio(EjercicioFiscal ejercicioFiscal, SegbMovimientoDTO segMovDto) throws SeguimientoDAOException, DaoException;
     Integer buscaEjercicioPorNomYDesc(EjercicioFiscal ejercicioFiscal);
     ByteArrayOutputStream generaExcel(List<EjercicioFiscal> list);
     ByteArrayOutputStream generaPDF(List<EjercicioFiscal> list);
    
}
