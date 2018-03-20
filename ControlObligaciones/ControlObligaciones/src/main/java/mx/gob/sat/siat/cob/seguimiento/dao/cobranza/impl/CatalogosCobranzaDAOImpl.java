package mx.gob.sat.siat.cob.seguimiento.dao.cobranza.impl;


import java.util.List;


import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.mapper.ComboMapperStr;
import mx.gob.sat.siat.cob.seguimiento.dao.cobranza.CatalogosCobranzaDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.cobranza.sql.CobranzaSQL;
import mx.gob.sat.siat.cob.seguimiento.dao.compartidos.mapper.CatalogoMapper;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.ComboStr;
import mx.gob.sat.siat.cob.seguimiento.dto.compartidos.CatalogoBase;

import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 *
 * @author root
 */
@Repository
public class CatalogosCobranzaDAOImpl implements CatalogosCobranzaDAO, CobranzaSQL{
    
    

    @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_COBRANZA)
    private JdbcTemplate template;
    
    @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_ADM_SGT)
    private JdbcTemplate templateAdm;
    
    
    
    /**
     *
     * @return
     */
    @Override
    @Propagable
    public List<ComboStr> buscarTiposMotivo() {
        return  template.query(CobranzaSQL.LISTA_COMBO_RESOL_MOTIVO, new ComboMapperStr());
    }

    /**
     *
     * @return
     */
    @Override
    public List<CatalogoBase> getTipoFirma() {
        return  templateAdm.query(CobranzaSQL.LISTA_TIPO_FIRMA, new CatalogoMapper());
    }

}
