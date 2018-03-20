package mx.gob.sat.siat.cob.seguimiento.dao.catalogos.impl;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.MtvoCancelDocDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.mapper.MtvoCancelDocMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.sql.MtvoCancelDocSQL;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.MtvoCancelDoc;
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
public class MtvoCancelDocDAOImpl implements MtvoCancelDocDAO {

    private static Logger logger = Logger.getLogger(MtvoCancelDocDAOImpl.class);
    @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG)
    private JdbcTemplate template;

    /**
     *
     * @return
     */
    @Override
    public List<MtvoCancelDoc> todosLosMotivos() {
        List<MtvoCancelDoc> listMtvoCancelDoc;
        listMtvoCancelDoc = template.query(MtvoCancelDocSQL.OBTEN_TODOS_MOTIVOS, new MtvoCancelDocMapper());
        if (listMtvoCancelDoc == null || listMtvoCancelDoc.isEmpty()) {
            logger.info(MtvoCancelDocSQL.OBTEN_TODOS_MOTIVOS);
        }
        return listMtvoCancelDoc;
    }

    /**
     *
     * @param mtvoCancelDoc
     *
     */
    @Override
    @Propagable
    public void agregaMotivo(MtvoCancelDoc mtvoCancelDoc) {
        int resultado = template.update(MtvoCancelDocSQL.AGREGA_MOTIVO, mtvoCancelDoc.getNombre(),
                mtvoCancelDoc.getDescripcion(), mtvoCancelDoc.getFechaInicio(), mtvoCancelDoc.getFechaFin(),
                mtvoCancelDoc.getOrden());
        if (resultado == -1) {
            logger.info(MtvoCancelDocSQL.AGREGA_MOTIVO);
        }

    }

    /**
     *
     * @param mtvoCancelDoc
     *
     */
    @Override
    @Propagable
    public void editaMotivo(MtvoCancelDoc mtvoCancelDoc) {
        int resultado = template.update(MtvoCancelDocSQL.EDITA_MOTIVO, mtvoCancelDoc.getNombre(),
                mtvoCancelDoc.getDescripcion(), mtvoCancelDoc.getIdMotivoCancelacion());
        if (resultado == -1) {
            logger.info(MtvoCancelDocSQL.EDITA_MOTIVO);
        }

    }

    /**
     *
     * @param mtvoCancelDoc
     * @return
     *
     */
    @Override
    @Propagable
    public Integer eliminaMotivo(MtvoCancelDoc mtvoCancelDoc) {
        Integer reg;
        reg = template.update(MtvoCancelDocSQL.ELIMINA_MOTIVO, mtvoCancelDoc.getFechaFin(),
                mtvoCancelDoc.getIdMotivoCancelacion());
        if (reg == -1) {
            logger.info(MtvoCancelDocSQL.ELIMINA_MOTIVO);
        }
        return reg;

    }

    /**
     *
     * @param mtvoCancelDoc
     * @return
     */
    @Override
    public MtvoCancelDoc buscaMotivoPorID(MtvoCancelDoc mtvoCancelDoc) {
        try {
            return template.queryForObject(MtvoCancelDocSQL.BUSCA_MOTIVO_POR_ID,
                    new Object[]{mtvoCancelDoc.getIdMotivoCancelacion()}, new MtvoCancelDocMapper());
        } catch (EmptyResultDataAccessException e) {
            logger.debug("No hay resultados: \n  " + MtvoCancelDocSQL.BUSCA_MOTIVO_POR_ID);
            return null;
        }
    }

    /**
     *
     * @param mtvoCancelDoc
     * @return
     */
    @Override
    public Integer buscarMotivoPorIdYNom(MtvoCancelDoc mtvoCancelDoc) {
        Integer reg = null;
        SqlRowSet srs = template.queryForRowSet(MtvoCancelDocSQL.BUSCA_MOTIVO_POR_IDYNOM,
                mtvoCancelDoc.getIdMotivoCancelacion());

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
