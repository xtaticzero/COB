package mx.gob.sat.siat.cob.seguimiento.dao.catalogos;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Regimen;


public interface RegimenDAO {
    
     List<Regimen> todosLosRegimen();
     void agregaRegimen(Regimen regimen) throws SeguimientoDAOException;
     void editaRegimen(Regimen regimen) throws SeguimientoDAOException;
     void reactivaRegimen(Regimen regimen) throws SeguimientoDAOException;
     Integer eliminaRegimen(Regimen regimen) throws SeguimientoDAOException;
     Regimen buscaRegimenPorID(Regimen regimen);
     Integer buscarRegimenPorIdYNom(Regimen regimen);
    
}
