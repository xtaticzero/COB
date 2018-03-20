package mx.gob.sat.siat.cob.seguimiento.service.otros;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dto.compartidos.CatalogoBase;


public interface ServiceCatalogos {
    List<CatalogoBase> consultar(String aliasTabla) ;
}
