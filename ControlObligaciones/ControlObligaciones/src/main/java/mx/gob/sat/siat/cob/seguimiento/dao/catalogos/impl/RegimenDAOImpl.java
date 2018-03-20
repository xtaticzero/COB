package mx.gob.sat.siat.cob.seguimiento.dao.catalogos.impl;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.RegimenDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.mapper.RegimenMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.sql.RegimenSQL;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Regimen;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

@Repository
public class RegimenDAOImpl implements RegimenDAO {

    private static Logger logger = Logger.getLogger(RegimenDAOImpl.class);
    @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG)
    private JdbcTemplate template;

    /**
     *
     * @return
     */
    @Override
    public List<Regimen> todosLosRegimen() {
        List<Regimen> listRegimen;
        listRegimen = template.query(RegimenSQL.OBTEN_TODOS_REGIMEN, new RegimenMapper());
        return listRegimen;
    }

    /**
     *
     * @param regimen
     *
     */
    @Override
    @Propagable
    public void agregaRegimen(Regimen regimen) {
        template.update(RegimenSQL.AGREGA_REGIMEN, regimen.getIdRegimen(), regimen.getNombre(),
                regimen.getDescripcion(), regimen.getFechaInicio(), regimen.getFechaFin(),
                regimen.getOrden());
    }

    /**
     *
     * @param regimen
     *
     */
    @Override
    @Propagable
    public void editaRegimen(Regimen regimen) {
        template.update(RegimenSQL.EDITA_REGIMEN, regimen.getNombre(),
                regimen.getDescripcion(), regimen.getIdRegimen());
    }

    /**
     *
     * @param regimen
     *
     */
    @Override
    @Propagable
    public void reactivaRegimen(Regimen regimen) {
        template.update(RegimenSQL.REACTIVA_REGIMEN,
                regimen.getIdRegimen());
    }

    /**
     *
     * @param regimen
     * @return
     *
     */
    @Override
    @Propagable
    public Integer eliminaRegimen(Regimen regimen) {
        Integer reg = null;
        reg = template.update(RegimenSQL.ELIMINA_REGIMEN, regimen.getFechaFin(),
                regimen.getIdRegimen());
        return reg;

    }

    /**
     *
     * @param regimen
     * @return
     */
    @Override
    public Regimen buscaRegimenPorID(Regimen regimen) {
        try {
            return template.queryForObject(RegimenSQL.BUSCA_REGIMEN_POR_ID,
                    new Object[]{regimen.getIdRegimen()}, new RegimenMapper());
        } catch (EmptyResultDataAccessException e) {
            logger.debug("No hay resultados: \n  " + RegimenSQL.BUSCA_REGIMEN_POR_ID);
            return null;
        }

    }

    /**
     *
     * @param regimen
     * @return
     */
    @Override
    public Integer buscarRegimenPorIdYNom(Regimen regimen) {
        Integer reg = null;
        SqlRowSet srs = template.queryForRowSet(RegimenSQL.BUSCA_REGIMEN_POR_IDYNOM,
                regimen.getIdRegimen());

        while (srs.next()) {
            reg = srs.getInt("REGISTROS");
        }

        return reg;
    }
}
