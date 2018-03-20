package mx.gob.sat.siat.cob.seguimiento.service.catalogos;

import java.io.ByteArrayOutputStream;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Regimen;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.exception.DaoException;


public interface RegimenService {
    
     List<Regimen> todosLosRegimen();
     void agregaRegimen(Regimen regimen)throws SeguimientoDAOException, DaoException;
     void editaRegimen(Regimen regimen)throws SeguimientoDAOException, DaoException;
     void reactivaRegimen(Regimen regimen)throws SGTServiceException, SeguimientoDAOException;
     void eliminaRegimen(Regimen regimen) throws SeguimientoDAOException, DaoException;
     Integer buscaRegimenPorIdYNom(Regimen regimen);
     ByteArrayOutputStream generaExcel(List<Regimen> list);
     ByteArrayOutputStream generaPDF(List<Regimen> list);

}
