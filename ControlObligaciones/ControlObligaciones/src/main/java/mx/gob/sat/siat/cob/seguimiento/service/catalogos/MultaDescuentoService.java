package mx.gob.sat.siat.cob.seguimiento.service.catalogos;

import java.io.ByteArrayOutputStream;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Combo;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.ComboStr;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.MultaDescuento;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.exception.DaoException;
import mx.gob.sat.siat.modelo.dto.SegbMovimientoDTO;


public interface MultaDescuentoService {
    List<MultaDescuento> todasLasMultaMonto();
    void agregarMultaMonto(MultaDescuento multaMonto, SegbMovimientoDTO segMovDto, Boolean existe) throws SeguimientoDAOException, DaoException;
    Integer buscarMultaMontoRepetida(MultaDescuento multaMonto);
    ByteArrayOutputStream generaExcel(List<MultaDescuento> list);
    ByteArrayOutputStream generaPDF(List<MultaDescuento> list);
    List<ComboStr> obtenerComboResolMotivo();
    List<Combo> obtenerComboMultaDescuento();
   
}
