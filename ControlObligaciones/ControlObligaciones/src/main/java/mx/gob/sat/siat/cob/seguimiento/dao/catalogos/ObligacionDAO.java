package mx.gob.sat.siat.cob.seguimiento.dao.catalogos;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Obligacion;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.ValorBooleano;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;


public interface ObligacionDAO {

    List<Obligacion> todosLasObligaciones();

    void agregaObligacion(Obligacion obligacion) throws SeguimientoDAOException;

    void editaObligacion(Obligacion obligacion) throws SeguimientoDAOException;
    
    void reactivaObligacion(Obligacion obligacion) throws SeguimientoDAOException;

    Integer eliminaObligacion(Obligacion obligacion) throws SeguimientoDAOException;

    Obligacion buscaObligacionPorID(Obligacion obligacion);

    Integer buscarObligacionPorConcYDesc(Obligacion obligacion);
    
    String obtenerConceptoImpuesto(Long idObli);

    List<ValorBooleano> obtenerTodosLosValores();

    List<Obligacion> getDistinctObligacion() throws SeguimientoDAOException;
}
