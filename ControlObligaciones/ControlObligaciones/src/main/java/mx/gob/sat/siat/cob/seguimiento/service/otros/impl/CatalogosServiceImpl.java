package mx.gob.sat.siat.cob.seguimiento.service.otros.impl;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dao.compartidos.CatalogosDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.compartidos.CatalogoBase;
import mx.gob.sat.siat.cob.seguimiento.service.otros.ServiceCatalogos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author root
 */
@Service(value = "catalogos")
@Transactional
public class CatalogosServiceImpl implements ServiceCatalogos {
    
    @Autowired
    private CatalogosDAO dao;
    
    /**
     *
     * @param aliasTabla
     * @return
     */
    @Override
    @Transactional(readOnly = true,
                   isolation = Isolation.DEFAULT,
                   propagation = Propagation.NOT_SUPPORTED)
    public List<CatalogoBase> consultar(String aliasTabla) {        
        return dao.consultar(aliasTabla);
    }
}
