package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl;

import java.sql.Types;

import java.util.Date;
import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.ParametroSgtDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.CatParametroMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.ParametroSgtMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.ParametrosVigentesSgtMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.ParametroSgtSQL;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ParametrosSgtDTO;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 *
 * @author root
 */
@Repository
public class ParametroSgtDAOImpl implements ParametroSgtDAO, ParametroSgtSQL {

    @Autowired
    @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG)
    private JdbcTemplate template;
    private static Logger logger = Logger.getLogger(ParametroSgtDAOImpl.class);

    /**
     *
     */
    @Propagable
    @Override
    public void actualizarVigenciaLiquidacion() {
        template.update(ACTUALIZAR_VIGENCIA_LIQUIDACION);
    }

    /**
     *
     * @return
     */
    @Override
    @Propagable
    public List<ParametrosSgtDTO> obtenerParametrosSgt() {
        return template.query(OBTENER_PARAMETROS_SGT, new CatParametroMapper());

    }

    /**
     *
     * @param montoMinimo
     * @param montoTotal
     */
    @Propagable
    @Override
    public void guardarParametrosLiquidacion(String montoMinimo, String montoTotal) {

        template.update(ACTUALIZAR_PARAMETROS_LIQUIDACION,
                new Object[]{Integer.valueOf(montoMinimo),
            Integer.valueOf(montoTotal),
            new Date(),
            null,
            PARAMETRO_ORDEN},
                new int[]{Types.INTEGER,
            Types.INTEGER,
            Types.DATE,
            Types.DATE,
            Types.INTEGER});

    }

    /**
     *
     * @param param
     */
    @Propagable
    @Override
    public void guardarParametroSgt(ParametrosSgtDTO param) {
        template.update(ACTUALIZAR_PARAMETROS_SGT,
                new Object[]{param.getIdParametro(),
            param.getValor(),
            new Date(),
            null},
                new int[]{Types.INTEGER,
            Types.VARCHAR,
            Types.DATE,
            Types.DATE});
    }
    
    /**
     *
     * @param idParametro
     */
    @Override
    @Propagable
    public void actualizarVigenciaParametroSgt(int idParametro) {
        template.update(ACTUALIZAR_VIGENCIA_PARAMETROS_SGT,
                new Object[]{idParametro},
                new int[]{Types.INTEGER});
    }

    /**
     *
     * @param idParametro
     * @return
     */
    @Override
    @Propagable
    public List<ParametrosSgtDTO> obtenerParametrosSgt(String idParametro) {

        return template.query(OBTENER_CAT_PARAMETRO_POR_CLAVE,
                new Object[]{Integer.parseInt(idParametro)},
                new int[]{Types.INTEGER},
                new CatParametroMapper());
    }

    @Override
    @Propagable
    public List<ParametrosSgtDTO> obtenerParametrosVigentesSgt() {
        return template.query(OBTENER_PARAMETROS_SGT_VIGENTES, new ParametrosVigentesSgtMapper());
    }

    /**
     *
     * @param idParametro
     * @return
     */
    @Override
    @Propagable(catched = true)
    public ParametrosSgtDTO obtenerParametroSgtPorIdParametro(int idParametro) {
        try {
            return template.queryForObject(OBTENER_PARAMETRO_SGT_POR_ID,
                    new Object[]{idParametro},
                    new int[]{Types.INTEGER},
                    new ParametroSgtMapper());
        } catch (EmptyResultDataAccessException e) {
            logger.debug("No hay resultados: \n  " + OBTENER_PARAMETRO_SGT_POR_ID);
            return null;
        }
    }

    @Override
    public void actualizarParametroSgt(ParametrosSgtDTO param) throws SeguimientoDAOException {
        template.update(ACTUALIZAR_VALOR_PARAMETRO_SGT,
                new Object[]{param.getValor(),
            param.getIdParametro()},
                new int[]{Types.VARCHAR,
            Types.INTEGER});
    }
}
