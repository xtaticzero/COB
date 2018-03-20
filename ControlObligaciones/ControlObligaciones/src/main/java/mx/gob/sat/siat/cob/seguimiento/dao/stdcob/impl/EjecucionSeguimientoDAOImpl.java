package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl;

import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.EjecucionSeguimientoDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.EjecucionSeguimientoMapper;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.EjecucionSeguimientoSQL.*;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.EjecucionSeguimiento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EjecucionSeguimientoEnum;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EjecucionSeguimientoDAOImpl implements EjecucionSeguimientoDAO {

    @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG)
    private JdbcTemplate template;
    private static Logger logger = Logger.getLogger(EjecucionSeguimientoDAOImpl.class);

    @Override
    @Propagable
    public EjecucionSeguimiento consultaEjecucionProceso() {
        try {
            return (EjecucionSeguimiento) this.template.queryForObject(CONSULTA_EJECUCION_PROCESO,
                    new EjecucionSeguimientoMapper());
        } catch (EmptyResultDataAccessException e) {
            logger.debug("No hay resultados: \n  " + CONSULTA_EJECUCION_PROCESO);
            return null;
        }
    }

    @Override
    @Propagable
    public EjecucionSeguimientoEnum enEjecucion() {
        int valor = template.queryForObject(CONSULTA_ESTATUS_EJECUCION, Integer.class);
        for (EjecucionSeguimientoEnum e : EjecucionSeguimientoEnum.values()) {
            if (e.ordinal() == valor) {
                return e;
            }
        }
        return null;
    }
}
