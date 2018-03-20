package mx.gob.sat.siat.cob.seguimiento.service.catalogos;

import java.io.ByteArrayOutputStream;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Descripcion;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.exception.DaoException;
import mx.gob.sat.siat.modelo.dto.SegbMovimientoDTO;


public interface DescripcionService {
    
    List<Descripcion> todasLasDescripciones();
    
    void agregaDescripcion(Descripcion descripcion, SegbMovimientoDTO segMovDto) throws SeguimientoDAOException, DaoException;
    void editaDescripcion(Descripcion descripcion, SegbMovimientoDTO segMovDto)throws SeguimientoDAOException, DaoException;
    void eliminaDescripcion(Descripcion descripcion, SegbMovimientoDTO segMovDto) throws SeguimientoDAOException, DaoException;
    Integer buscaDescripcionPorIdYDes(Descripcion descripcion);
    ByteArrayOutputStream generaExcel(List<Descripcion> list);
    ByteArrayOutputStream generaPDF(List<Descripcion> list);
}
