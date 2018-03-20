package mx.gob.sat.siat.cob.seguimiento.dao.catalogos.impl;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.ObligacionDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.mapper.ObligacionCatalogoBaseMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.mapper.ObligacionMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.mapper.ValorBooleanoMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.sql.ObligacionesSQL;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Obligacion;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.ValorBooleano;
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
public class ObligacionDAOImpl implements ObligacionDAO {

    private static Logger logger = Logger.getLogger(ObligacionDAOImpl.class);
    @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG)
    private JdbcTemplate template;

    /**
     *
     * @return
     */
    @Override
    public List<Obligacion> todosLasObligaciones() {

        List<Obligacion> lista = template.query(ObligacionesSQL.OBTEN_TODAS_OBLIGACIONES, new ObligacionMapper());
        if (lista == null || lista.isEmpty()) {
            logger.info(ObligacionesSQL.OBTEN_TODAS_OBLIGACIONES);
        }
        return lista;
    }

    /**
     *
     * @param obligacion
     */
    @Override
    @Propagable
    public void agregaObligacion(Obligacion obligacion) {

        int resultado = template.update(ObligacionesSQL.AGREGA_OBLIGACION, obligacion.getValorBooleano().getIdValorBooleano(),
                obligacion.getIdObligacion(), obligacion.getConcepto(),
                obligacion.getDescripcion(), obligacion.getFechaInicio(),
                obligacion.getFechaFin(), obligacion.getOrden());
        if (resultado == -1) {
            logger.info(ObligacionesSQL.AGREGA_OBLIGACION);
        }
    }

    /**
     *
     * @param obligacion
     */
    @Override
    @Propagable
    public void editaObligacion(Obligacion obligacion) {

        int resultado = template.update(ObligacionesSQL.EDITA_OBLIGACION, obligacion.getConcepto(),
                obligacion.getDescripcion(), obligacion.getValorBooleano().getIdValorBooleano(),
                obligacion.getIdObligacion());
        if (resultado == -1) {
            logger.info(ObligacionesSQL.EDITA_OBLIGACION);
        }
    }

    /**
     *
     * @param obligacion
     *
     */
    @Override
    @Propagable
    public void reactivaObligacion(Obligacion obligacion) {
        int resultado = template.update(ObligacionesSQL.REACTIVA_OBLIGACION,
                obligacion.getIdObligacion());
        if (resultado == -1) {
            logger.info(ObligacionesSQL.REACTIVA_OBLIGACION);
        }
    }

    /**
     *
     * @param obligacion
     * @return
     */
    @Override
    @Propagable
    public Integer eliminaObligacion(Obligacion obligacion) {
        int resultado = template.update(ObligacionesSQL.ELIMINA_OBLIGACION, obligacion.getFechaFin(),
                obligacion.getIdObligacion());
        if (resultado == -1) {
            logger.info(ObligacionesSQL.ELIMINA_OBLIGACION);
        }
        return resultado;
    }

    /**
     *
     * @param obligacion
     * @return
     */
    @Override
    public Obligacion buscaObligacionPorID(Obligacion obligacion) {

        try {
            return template.queryForObject(ObligacionesSQL.BUSCA_OBLIGACION_POR_ID,
                    new Object[]{obligacion.getIdObligacion()}, new ObligacionMapper());
        } catch (EmptyResultDataAccessException e) {
            logger.debug("No hay resultados: \n  " + ObligacionesSQL.BUSCA_OBLIGACION_POR_ID);
            return null;
        }
    }

    /**
     *
     * @param obligacion
     * @return
     */
    @Override
    public Integer buscarObligacionPorConcYDesc(Obligacion obligacion) {
        Integer reg = null;
        SqlRowSet srs = template.queryForRowSet(ObligacionesSQL.BUSCA_OBLIGACION_POR_CONCYDESC,
                obligacion.getIdObligacion());

        while (srs.next()) {
            reg = srs.getInt("REGISTROS");
        }

        return reg;
    }

    @Override
    public String obtenerConceptoImpuesto(Long obligacion) {
        String reg = null;
        SqlRowSet srs = template.queryForRowSet(ObligacionesSQL.OBTENER_CONCEPTO_IMP,
                obligacion);

        while (srs.next()) {
            reg = srs.getString("CONCEPTO");
        }

        return reg;
    }

    /**
     *
     * @return
     */
    @Override
    @Propagable
    public List<Obligacion> getDistinctObligacion() {
        return template.queryForObject(ObligacionesSQL.GET_DISTINCT_OBLIGACION, new ObligacionCatalogoBaseMapper());
    }

    /**
     *
     * @return
     */
    @Override
    public List<ValorBooleano> obtenerTodosLosValores() {
        return template.query(ObligacionesSQL.OBTEN_TODOS_VALORES_BOOL, new ValorBooleanoMapper());
    }
}
