package mx.gob.sat.siat.cob.seguimiento.dao.compartidos;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dto.compartidos.CatalogoBase;

public interface CatalogosDAO<T> {

    List<CatalogoBase> consultar(String identificador);
}
