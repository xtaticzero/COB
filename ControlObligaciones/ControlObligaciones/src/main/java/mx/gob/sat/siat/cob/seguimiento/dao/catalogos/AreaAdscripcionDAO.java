package mx.gob.sat.siat.cob.seguimiento.dao.catalogos;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.AreaAdscripcion;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;

public interface AreaAdscripcionDAO {
    
    List<AreaAdscripcion> todasLasAreas();
    void agregaArea(AreaAdscripcion areaAdscripcion) throws SeguimientoDAOException;
    void editaArea(AreaAdscripcion areaAdscripcion) throws SeguimientoDAOException;
    void reactivaArea(AreaAdscripcion areaAdscripcion) throws SeguimientoDAOException;
    Integer eliminaArea(AreaAdscripcion areaAdscripcion) throws SeguimientoDAOException;
    Integer buscarAreaPorId(AreaAdscripcion areaAdscripcion);
}
