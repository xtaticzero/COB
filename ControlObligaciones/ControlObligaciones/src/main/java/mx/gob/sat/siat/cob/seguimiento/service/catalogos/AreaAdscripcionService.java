package mx.gob.sat.siat.cob.seguimiento.service.catalogos;

import java.io.ByteArrayOutputStream;
import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.AreaAdscripcion;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.exception.DaoException;
import mx.gob.sat.siat.modelo.dto.SegbMovimientoDTO;

public interface AreaAdscripcionService {
    
    List<AreaAdscripcion> todasLasAreas();
    void agregaArea(AreaAdscripcion areaAdscripcion, SegbMovimientoDTO segMovDto)throws SeguimientoDAOException, DaoException;
    void editaArea(AreaAdscripcion areaAdscripcion, SegbMovimientoDTO segMovDto)throws SeguimientoDAOException, DaoException;
    void reactivaArea(AreaAdscripcion areaAdscripcion)throws SeguimientoDAOException;
    void eliminaArea(AreaAdscripcion areaAdscripcion, SegbMovimientoDTO segMovDto) throws SeguimientoDAOException, DaoException;
    Integer buscaAreaPorId(AreaAdscripcion areaAdscripcion);
    ByteArrayOutputStream generaExcel(List<AreaAdscripcion> list);
    ByteArrayOutputStream generaPDF(List<AreaAdscripcion> list);
}
