package mx.gob.sat.siat.cob.seguimiento.dao.rfcampliado.impl;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dao.rfcampliado.RfccCatalogoDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.rfcampliado.impl.mapper.RegionAlrMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.rfcampliado.sql.RfccCatalogoSQL;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.ComboStr;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author root
 */
@Repository
public class RfccCatalogoDAOImpl implements RfccCatalogoDAO{
    /**
     *
     */
    public RfccCatalogoDAOImpl() {
        super();
    }
    
    @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_AMPLIADO)
    private JdbcTemplate templateRfca;
    
    private final Logger logger=Logger.getLogger(getClass());
    
    /**
     *
     * @return
     */
    @Override
    @Propagable(catched=true)
    public List<ComboStr> buscarRegionALR() {
        StringBuilder sql=new StringBuilder();
        sql.append(RfccCatalogoSQL.BUSCAR_REGION_ALR);
        logger.debug(sql.toString());
        return templateRfca.query(sql.toString(), new RegionAlrMapper());
    }
}
