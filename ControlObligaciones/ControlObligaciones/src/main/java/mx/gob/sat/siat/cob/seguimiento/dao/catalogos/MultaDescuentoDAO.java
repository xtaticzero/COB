package mx.gob.sat.siat.cob.seguimiento.dao.catalogos;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Combo;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.MultaDescuento;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;


public interface MultaDescuentoDAO {
    List<MultaDescuento> todasLasMultaMonto();
    void agregarMultaMonto(MultaDescuento multaMonto, Boolean existe) throws SeguimientoDAOException;
    Integer buscarMultaMontoRepetida(MultaDescuento multaMonto);
    List<Combo> obtenerComboMultaDescuento();
}
