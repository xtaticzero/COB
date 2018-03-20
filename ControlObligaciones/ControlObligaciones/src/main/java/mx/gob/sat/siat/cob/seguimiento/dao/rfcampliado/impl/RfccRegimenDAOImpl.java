package mx.gob.sat.siat.cob.seguimiento.dao.rfcampliado.impl;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.mapper.ComboMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.sql.ObligacionesSQL;
import mx.gob.sat.siat.cob.seguimiento.dao.rfcampliado.RfccRegimenDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Combo;
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
public class RfccRegimenDAOImpl implements RfccRegimenDAO{
    @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_AMPLIADO)
    private JdbcTemplate template;
   
   
    /**
     *
     * @return
     */
    @Override
    public List<Combo> obtenerComboRegimen() {
        List<Combo> regimen;
        regimen = template.query(ObligacionesSQL.LISTA_COMBO_REGIMEN, new ComboMapper());
        return regimen;
    }
}
