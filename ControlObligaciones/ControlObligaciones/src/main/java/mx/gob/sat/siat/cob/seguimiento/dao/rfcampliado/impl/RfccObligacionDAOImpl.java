package mx.gob.sat.siat.cob.seguimiento.dao.rfcampliado.impl;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.mapper.ComboObligacionMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.sql.ObligacionesSQL;
import mx.gob.sat.siat.cob.seguimiento.dao.rfcampliado.RfccObligacionDAO;
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
public class RfccObligacionDAOImpl implements RfccObligacionDAO {
    @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_AMPLIADO)
    private JdbcTemplate template;
   
    /**
     *
     * @return
     */
    @Override
    public List<Combo> obtenerComboObligacion() {
        List<Combo> combo;
        combo = template.query(ObligacionesSQL.LISTA_COMBO_OBLIGACION, new ComboObligacionMapper());
        return combo;
    }
}
