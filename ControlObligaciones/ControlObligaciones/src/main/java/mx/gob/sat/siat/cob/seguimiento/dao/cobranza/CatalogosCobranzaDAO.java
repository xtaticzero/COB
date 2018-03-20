package mx.gob.sat.siat.cob.seguimiento.dao.cobranza;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.ComboStr;
import mx.gob.sat.siat.cob.seguimiento.dto.compartidos.CatalogoBase;

public interface CatalogosCobranzaDAO {
    
    List<ComboStr> buscarTiposMotivo();

    List<CatalogoBase> getTipoFirma();
}
