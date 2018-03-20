package mx.gob.sat.siat.cob.seguimiento.dao.catalogos.impl;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.DescripcionDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.mapper.DescripcionMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.sql.DescripcionSQL;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Descripcion;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

@Repository
public class DescripcionDAOImpl implements DescripcionDAO {

    private static Logger logger = Logger.getLogger(DescripcionDAOImpl.class);
    @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG)
    private JdbcTemplate template;

    /**
     *
     * @return
     */
    @Override
    public List<Descripcion> todasLasDescripciones() {
        List<Descripcion> listDescripcion;
        listDescripcion = template.query(DescripcionSQL.OBTEN_TODAS_DESCRIPCIONES, new DescripcionMapper());
        if (listDescripcion == null || listDescripcion.isEmpty()) {
            Logger.getLogger(DescripcionDAOImpl.class.getName()).log(Level.INFO, DescripcionSQL.OBTEN_TODAS_DESCRIPCIONES);
        }
        return listDescripcion;
    }

    /**
     *
     * @param descripcion
     *
     */
    @Override
    @Propagable
    public void agregaDescripcion(Descripcion descripcion) {
        int resultado = template.update(DescripcionSQL.AGREGA_DESCRIPCION, descripcion.getDescripcion(),
                descripcion.getFechaInicio(), descripcion.getFechaFin(),
                descripcion.getOrden());
        if (resultado == -1) {
            Logger.getLogger(DescripcionDAOImpl.class.getName()).log(Level.INFO, DescripcionSQL.AGREGA_DESCRIPCION);
        }

    }

    /**
     *
     * @param descripcion
     *
     */
    @Override
    @Propagable
    public void editaDescripcion(Descripcion descripcion) {
        int resultado =
                template.update(DescripcionSQL.EDITA_DESCRIPCION, descripcion.getDescripcion(),
                descripcion.getIdDescripcion());
        if (resultado == -1) {
            Logger.getLogger(DescripcionDAOImpl.class.getName()).log(Level.INFO, DescripcionSQL.EDITA_DESCRIPCION);
        }

    }

    /**
     *
     * @param descripcion
     * @return
     *
     */
    @Override
    @Propagable
    public Integer eliminaDescripcion(Descripcion descripcion) {
        Integer reg;
        reg = template.update(DescripcionSQL.ELIMINA_DESCRIPCION, descripcion.getFechaFin(),
                descripcion.getIdDescripcion());
        if (reg == -1) {
            Logger.getLogger(DescripcionDAOImpl.class.getName()).log(Level.INFO, DescripcionSQL.ELIMINA_DESCRIPCION);
        }
        return reg;

    }

    /**
     *
     * @param descripcion
     * @return
     */
    @Override
    public Descripcion buscaDescripcionPorID(Descripcion descripcion) {
        try {
            return (Descripcion) template.queryForObject(DescripcionSQL.BUSCA_DESCRIPCION_POR_ID,
                    new Object[]{descripcion.getIdDescripcion()}, new DescripcionMapper());
        } catch (EmptyResultDataAccessException e) {
            logger.debug("No hay resultados: \n  " + DescripcionSQL.BUSCA_DESCRIPCION_POR_ID );
            return null;
        }
    }

    /**
     *
     * @param descripcion
     * @return
     */
    @Override
    public Integer buscarDescripcionPorIdYDes(Descripcion descripcion) {
        Integer reg = null;
        SqlRowSet srs = template.queryForRowSet(DescripcionSQL.BUSCA_DESCRIPCION_POR_IDYDES,
                descripcion.getDescripcion());

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
