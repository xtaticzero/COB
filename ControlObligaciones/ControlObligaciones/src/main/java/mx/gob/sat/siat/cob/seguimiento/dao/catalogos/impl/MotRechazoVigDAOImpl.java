package mx.gob.sat.siat.cob.seguimiento.dao.catalogos.impl;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.MotRechazoVigDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.mapper.MotRechazoVigMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.sql.MotRechazoVigSQL;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.MotRechazoVig;
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
public class MotRechazoVigDAOImpl implements MotRechazoVigDAO {

    private static Logger logger = Logger.getLogger(MotRechazoVigDAOImpl.class);
    @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG)
    private JdbcTemplate template;

    /**
     *
     * @return
     */
    @Override
    public List<MotRechazoVig> todosLosMotivos() {
        List<MotRechazoVig> listMotRechazoVig;
        listMotRechazoVig = template.query(MotRechazoVigSQL.OBTEN_TODOS_MOTIVOS, new MotRechazoVigMapper());
        if (listMotRechazoVig == null || listMotRechazoVig.isEmpty()) {
            logger.info(MotRechazoVigSQL.OBTEN_TODOS_MOTIVOS);
        }
        return listMotRechazoVig;
    }

    /**
     *
     * @param motRechazoVig
     *
     */
    @Override
    @Propagable
    public void agregaMotivo(MotRechazoVig motRechazoVig) {
        int resultado = template.update(MotRechazoVigSQL.AGREGA_MOTIVO, motRechazoVig.getNombre(),
                motRechazoVig.getDescripcion(), motRechazoVig.getFechaInicio(), motRechazoVig.getFechaFin(),
                motRechazoVig.getOrden());
        if (resultado == -1) {
            logger.info(MotRechazoVigSQL.AGREGA_MOTIVO);
        }

    }

    /**
     *
     * @param motRechazoVig
     *
     */
    @Override
    @Propagable
    public void editaMotivo(MotRechazoVig motRechazoVig) {
        int resultado = template.update(MotRechazoVigSQL.EDITA_MOTIVO, motRechazoVig.getNombre(),
                motRechazoVig.getDescripcion(), motRechazoVig.getIdMotivoRechazoVig());
        if (resultado == -1) {
            logger.info(MotRechazoVigSQL.EDITA_MOTIVO);
        }

    }

    /**
     *
     * @param motRechazoVig
     * @return
     *
     */
    @Override
    @Propagable
    public Integer eliminaMotivo(MotRechazoVig motRechazoVig) {
        Integer reg;
        reg = template.update(MotRechazoVigSQL.ELIMINA_MOTIVO, motRechazoVig.getFechaFin(),
                motRechazoVig.getIdMotivoRechazoVig());
        if (reg == -1) {
            logger.info(MotRechazoVigSQL.ELIMINA_MOTIVO);
        }
        return reg;

    }

    /**
     *
     * @param motRechazoVig
     * @return
     */
    @Override
    public MotRechazoVig buscaMotivoPorID(MotRechazoVig motRechazoVig) {
        try {
            return template.queryForObject(MotRechazoVigSQL.BUSCA_MOTIVO_POR_ID,
                    new Object[]{motRechazoVig.getIdMotivoRechazoVig()}, new MotRechazoVigMapper());
        } catch (EmptyResultDataAccessException e) {
            logger.debug("No hay resultados: \n  " + MotRechazoVigSQL.BUSCA_MOTIVO_POR_ID);
            return null;
        }

    }

    /**
     *
     * @param motRechazoVig
     * @return
     */
    @Override
    public Integer buscarMotivoPorIdYNom(MotRechazoVig motRechazoVig) {
        Integer reg = null;
        SqlRowSet srs = template.queryForRowSet(MotRechazoVigSQL.BUSCA_MOTIVO_POR_IDYNOM,
                motRechazoVig.getIdMotivoRechazoVig());
        while (srs.next()) {
            reg = srs.getInt("REGISTROS");
        }
        return reg;
    }

    /**
     *
     * @param template
     */
    public void setJdbcTemplate(JdbcTemplate template) {
        this.template = template;
    }

    /**
     *
     * @return
     */
    public JdbcTemplate getJdbcTemplate() {
        return template;
    }
}
