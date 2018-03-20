package mx.gob.sat.siat.cob.seguimiento.dao.rfcampliado.impl;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.mapper.ComboMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.rfcampliado.RfccEntidadDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.rfcampliado.sql.RfccEntidadSQL;
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
public class RfccEntidadDAOImpl implements RfccEntidadDAO {
    @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_AMPLIADO)
    private JdbcTemplate template;
   
    /**
     *
     * @return
     */
    @Override
    public List<Combo> obtenerComboEntidad() {
        List<Combo> combo;
        combo = template.query(RfccEntidadSQL.LISTA_COMBO_ENTIDAD, new ComboMapper());
        return combo;
    }
}
