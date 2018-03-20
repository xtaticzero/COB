package mx.gob.sat.siat.cob.seguimiento.service.catalogos;

import java.io.ByteArrayOutputStream;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Combo;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.EstadoAdmonLocalMAT;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.exception.DaoException;
import mx.gob.sat.siat.modelo.dto.SegbMovimientoDTO;

public interface EstadoAdmonLocalMATService {
    
    List<EstadoAdmonLocalMAT> todosLosEstadosAdmonLocalMAT();
    void editaEstadoAdmonLocalMAT(EstadoAdmonLocalMAT estadoAdmonLocalMAT, SegbMovimientoDTO segMovDto) throws SeguimientoDAOException, DaoException;
    ByteArrayOutputStream generaExcel(List<EstadoAdmonLocalMAT> list);
    ByteArrayOutputStream generaPDF(List<EstadoAdmonLocalMAT> list);
    List<Combo> obtenerComboEstado();
}
