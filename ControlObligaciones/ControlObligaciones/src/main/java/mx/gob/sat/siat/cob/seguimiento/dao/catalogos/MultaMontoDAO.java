package mx.gob.sat.siat.cob.seguimiento.dao.catalogos;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Combo;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.MultaMonto;
import mx.gob.sat.siat.cob.seguimiento.dto.compartidos.CatalogoBase;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;


public interface MultaMontoDAO {

    List<MultaMonto> todosLasOblisanciones();

    void agregarOblisancion(MultaMonto oblisancion, boolean bol) throws SeguimientoDAOException;

    void editaOblisancion(MultaMonto oblisancion,  boolean bol) throws SeguimientoDAOException;

    Integer eliminaOblisancion(MultaMonto oblisancion) throws SeguimientoDAOException;

    List<Combo> obtenerComboObligacion();

    List<CatalogoBase> getOblisancionMotivaciones();
    
    Integer buscarMultaMontoRepetida(Long idObligacionSan, String idTipoMultaSel);
    
}
