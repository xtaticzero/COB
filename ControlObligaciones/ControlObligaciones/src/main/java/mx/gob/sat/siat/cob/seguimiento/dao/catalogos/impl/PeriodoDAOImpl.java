/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.catalogos.impl;

import java.sql.Types;
import java.util.List;


import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.PeriodoDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.mapper.PeriodoMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.sql.PeriodoSQL;
import mx.gob.sat.siat.cob.seguimiento.dto.compartidos.CatalogoBase;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
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
public class PeriodoDAOImpl implements PeriodoDAO {

    @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG)
    private JdbcTemplate template;
    /**
     *
     * @param periodicidad
     * @return
      
     */
    @Override
    @Propagable
    public List<CatalogoBase> periodoXPeriodicidad(String periodicidad) throws SeguimientoDAOException {
        return template.query(PeriodoSQL.PERIODOS_X_PERIODICIDAD,
                new Object[]{periodicidad},
                new int[]{Types.VARCHAR},
                new PeriodoMapper());
    }
    
}
