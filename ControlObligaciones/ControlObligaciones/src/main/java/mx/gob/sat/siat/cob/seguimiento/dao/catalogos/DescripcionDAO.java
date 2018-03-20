package mx.gob.sat.siat.cob.seguimiento.dao.catalogos;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Descripcion;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;


public interface DescripcionDAO {
    
    List<Descripcion> todasLasDescripciones();
    void agregaDescripcion(Descripcion descripcion) throws SeguimientoDAOException;
    void editaDescripcion(Descripcion descripcion) throws SeguimientoDAOException;
    Integer eliminaDescripcion(Descripcion descripcion) throws SeguimientoDAOException;
    Descripcion buscaDescripcionPorID(Descripcion descripcion);
    Integer buscarDescripcionPorIdYDes(Descripcion descripcion);
}
