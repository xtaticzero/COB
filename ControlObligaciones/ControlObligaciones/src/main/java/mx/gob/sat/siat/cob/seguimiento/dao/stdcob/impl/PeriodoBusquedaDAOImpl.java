/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl;

import java.sql.Types;
import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.PeriodoBusquedaDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.PeriodoBusquedaMapper;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.PeriodoBusquedaSQL.INSERT_PERIODO_BUSQUEDA;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.PeriodoBusquedaSQL.SELECT_X_ID_BUSQUEDA;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.PeriodicidadHelper;
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
 * @author juan
 */
@Repository
public class PeriodoBusquedaDAOImpl implements PeriodoBusquedaDAO {

    private static Logger logger = Logger.getLogger(PeriodoBusquedaDAOImpl.class);
    @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG)
    private JdbcTemplate template;

    @Override
    public Integer insertPeriodoBusqueda(PeriodicidadHelper periodoBusqueda) {
        return template.update(INSERT_PERIODO_BUSQUEDA,
                new Object[]{
                    periodoBusqueda.getIdBusquedaRenuentes(),
                    periodoBusqueda.getPeriodicidad().getIdString(),
                    periodoBusqueda.getPeriodoInicialSelected(),
                    periodoBusqueda.getPeriodoFinalSelected(),
                    periodoBusqueda.getEjercicioInicialSelected(),
                    periodoBusqueda.getEjercicioFinalSelected()
                }, new int[]{Types.NUMERIC,
                    Types.CHAR,
                    Types.NUMERIC,
                    Types.NUMERIC,
                    Types.NUMERIC,
                    Types.NUMERIC});
    }

    @Override
    @Propagable
    public List<PeriodicidadHelper> selectPorIdBusqueda(Long idBusquedaRenuentes) throws SeguimientoDAOException {
        try {
            return template.query(SELECT_X_ID_BUSQUEDA,
                    new Object[]{idBusquedaRenuentes},
                    new int[]{Types.NUMERIC},
                    new PeriodoBusquedaMapper());
        } catch (EmptyResultDataAccessException e) {
            logger.info(SELECT_X_ID_BUSQUEDA);
            logger.error(e);
            return null;
        }
    }
}
