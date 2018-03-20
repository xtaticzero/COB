package mx.gob.sat.siat.cob.seguimiento.dao.catalogos;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.EjercicioFiscal;


public interface EjercicioFiscalDAO {
    
     List<EjercicioFiscal> todosLosEjercicios();
     void agregaEjercicio(EjercicioFiscal ejercicioFiscal) throws SeguimientoDAOException;
     void editaEjercicio(EjercicioFiscal ejercicioFiscal) throws SeguimientoDAOException;
     void reactivaEjercicio(EjercicioFiscal ejercicioFiscal) throws SeguimientoDAOException;
     Integer eliminaEjercicio(EjercicioFiscal ejercicioFiscal) throws SeguimientoDAOException;
     EjercicioFiscal buscaEjercicioPorID(EjercicioFiscal ejercicioFiscal);
     Integer buscarEjercicioPorNomYDesc(EjercicioFiscal ejercicioFiscal);
}
