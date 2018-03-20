package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl;

import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.VigilanciaAlDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.AlscEFMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.AlscMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.DetalleVigilanciaAlMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.DetalleVigilanciaEFMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.ReporteVigilanciaMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.VigilanciaAlMapper;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.AlscDTO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.VigilanciaAlSQL;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.VigilanciaAlSQL.CONSULTAR_ALSC_POR_IDVIGILANCIA;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.VigilanciaAlSQL.CONSULTAR_VIGILANCIA;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ReporteVigilancia;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VigilanciaAdministracionLocal;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VigilanciaAl;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VigilanciaEntidadFederativa;
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
public class VigilanciaAlDAOImpl implements VigilanciaAlDAO {

    private final Logger log = Logger.getLogger(VigilanciaAlDAOImpl.class);
    @Autowired
    @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG)
    private JdbcTemplate template;

    /**
     *
     * @param numeroCarga
     * @return
     */
    @Override
    @Propagable(catched = true)
    public VigilanciaAl buscarVigilancia(String numeroCarga) {
        List<VigilanciaAl> vigilancias = template.query(CONSULTAR_VIGILANCIA,
                new Object[]{numeroCarga},
                new int[]{Types.NUMERIC},
                new VigilanciaAlMapper());
        if (vigilancias.size() > 0) {
            return vigilancias.get(0);
        }
        return null;
    }

    /**
     *
     * @param idVigilancia
     * @return
     */
    @Override
    @Propagable(catched = true)
    public ReporteVigilancia consultaReporteVigilancia(String idVigilancia) {
        try {
            return template.queryForObject(VigilanciaAlSQL.CONSULTAR_REPORTE_VIGILANCIA,
                    new Object[]{idVigilancia}, new ReporteVigilanciaMapper());
        } catch (EmptyResultDataAccessException e) {
            log.debug("No hay resultados: \n  " + VigilanciaAlSQL.CONSULTAR_REPORTE_VIGILANCIA);
            return null;
        }
    }

    /**
     *
     * @param numeroCarga
     * @return
     */
    @Override
    @Propagable(catched = true)
    public List<VigilanciaAdministracionLocal> buscarDetalleVigilancia(String numeroCarga) {
        return template.query(CONSULTAR_DETALLE,
                new Object[]{numeroCarga},
                new int[]{Types.NUMERIC},
                new DetalleVigilanciaAlMapper());
    }

    /**
     *
     * @param numeroCarga
     * @return
     */
    @Override
    public List<VigilanciaEntidadFederativa> buscarDetalleVigilanciaEF(String numeroCarga) {
        return template.query(CONSULTAR_DETALLE_EF,
                new Object[]{numeroCarga},
                new int[]{Types.NUMERIC},
                new DetalleVigilanciaEFMapper());
    }

    /**
     *
     * @param idVigilancia
     * @return
     * @throws SQLException
     */
    @Override
    public List<AlscDTO> buscarALSCVigilancia(Integer idVigilancia) throws SQLException {
        List<AlscDTO> lstAlsc;
        lstAlsc = template.query(CONSULTAR_ALSC_POR_IDVIGILANCIA,
                new Object[]{idVigilancia, idVigilancia},
                new AlscMapper());
        return lstAlsc;
    }

    /**
     *
     * @param idVigilancia
     * @return
     * @throws SQLException
     */
    @Override
    public List<AlscDTO> buscarEFVigilancia(Integer idVigilancia) throws SQLException {
        List<AlscDTO> lstEf;
        lstEf = template.query(CONSULTAR_EF_POR_VIGILANCIA,
                new Object[]{idVigilancia},
                new AlscEFMapper());
        return lstEf;
    }
}
