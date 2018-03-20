package mx.gob.sat.siat.cob.seguimiento.dao.catalogos.impl;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.EjercicioFiscalDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.mapper.EjercicioFiscalMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.sql.EjercicioFiscalSQL;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.EjercicioFiscal;
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
public class EjercicioFiscalDAOImpl implements EjercicioFiscalDAO {

    private static Logger logger = Logger.getLogger(EjercicioFiscalDAOImpl.class);
    @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG)
    private JdbcTemplate template;

    /**
     *
     * @return
     */
    @Override
    public List<EjercicioFiscal> todosLosEjercicios() {
        List<EjercicioFiscal> listEjercicioFiscal;
        listEjercicioFiscal = template.query(EjercicioFiscalSQL.OBTEN_TODOS_EJERCICIOS, new EjercicioFiscalMapper());
        if (listEjercicioFiscal == null || listEjercicioFiscal.isEmpty()) {
            logger.info(EjercicioFiscalSQL.OBTEN_TODOS_EJERCICIOS);
        }
        return listEjercicioFiscal;
    }

    /**
     *
     * @param ejercicioFiscal
     *
     */
    @Override
    @Propagable
    public void agregaEjercicio(EjercicioFiscal ejercicioFiscal) {
        int resultado = template.update(EjercicioFiscalSQL.AGREGA_EJERCICIO, ejercicioFiscal.getIdEjercicioFiscal(), ejercicioFiscal.getNombre(),
                ejercicioFiscal.getDescripcion(), ejercicioFiscal.getFechaInicio(), ejercicioFiscal.getFechaFin(),
                ejercicioFiscal.getOrden());
        if (resultado == -1) {
            logger.info(EjercicioFiscalSQL.AGREGA_EJERCICIO);
        }
    }

    /**
     *
     * @param ejercicioFiscal
     *
     */
    @Override
    @Propagable
    public void editaEjercicio(EjercicioFiscal ejercicioFiscal) {
        int resultado = template.update(EjercicioFiscalSQL.EDITA_EJERCICIO, ejercicioFiscal.getNombre(),
                ejercicioFiscal.getDescripcion(), ejercicioFiscal.getIdEjercicioFiscal());
        if (resultado == -1) {
            logger.info(EjercicioFiscalSQL.EDITA_EJERCICIO);
        }
    }

    /**
     *
     * @param ejercicioFiscal
     *
     */
    @Override
    @Propagable
    public void reactivaEjercicio(EjercicioFiscal ejercicioFiscal) {
        int resultado = template.update(EjercicioFiscalSQL.REACTIVA_EJERCICIO,
                ejercicioFiscal.getIdEjercicioFiscal());
        if (resultado == -1) {
            logger.info(EjercicioFiscalSQL.REACTIVA_EJERCICIO);
        }
    }

    /**
     *
     * @param ejercicioFiscal
     * @return
     *
     */
    @Override
    @Propagable
    public Integer eliminaEjercicio(EjercicioFiscal ejercicioFiscal) {
        Integer reg;
        reg = template.update(EjercicioFiscalSQL.ELIMINA_EJERCICIO, ejercicioFiscal.getFechaFin(),
                ejercicioFiscal.getIdEjercicioFiscal());
        if (reg == -1) {
            logger.info(EjercicioFiscalSQL.ELIMINA_EJERCICIO);
        }
        return reg;
    }

    /**
     *
     * @param ejercicioFiscal
     * @return
     */
    @Override
    public EjercicioFiscal buscaEjercicioPorID(EjercicioFiscal ejercicioFiscal) {
        try {
            return template.queryForObject(EjercicioFiscalSQL.BUSCA_EJERCICIO_POR_ID,
                    new Object[]{ejercicioFiscal.getIdEjercicioFiscal()}, new EjercicioFiscalMapper());
        } catch (EmptyResultDataAccessException e) {
            logger.debug("No hay resultados: \n  " + EjercicioFiscalSQL.BUSCA_EJERCICIO_POR_ID);
            return null;
        }
    }

    /**
     *
     * @param ejercicioFiscal
     * @return
     */
    @Override
    public Integer buscarEjercicioPorNomYDesc(EjercicioFiscal ejercicioFiscal) {
        Integer reg = null;
        SqlRowSet srs;

        srs = template.queryForRowSet(EjercicioFiscalSQL.BUSCA_EJERCICIO_POR_NOMYDESC,
                ejercicioFiscal.getIdEjercicioFiscal());

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
