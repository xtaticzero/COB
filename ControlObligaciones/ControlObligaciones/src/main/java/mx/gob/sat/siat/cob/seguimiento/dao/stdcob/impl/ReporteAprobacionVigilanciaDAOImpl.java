/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl;

import java.sql.Types;
import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.ReporteAprobacionVigilanciaDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.DetalleReporteVigilanciaAprobadaMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.ReporteAprobacionVigilanciaMapper;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DetalleReporteVigilanciaAprobada;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ReporteAprobacionVigilancia;
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
public class ReporteAprobacionVigilanciaDAOImpl implements ReporteAprobacionVigilanciaDAO {

    @Autowired
    @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG)
    private JdbcTemplate template;
    private static Logger logger = Logger.getLogger(ReporteAprobacionVigilanciaDAOImpl.class);

    @Override
    @Propagable(catched = true)
    public ReporteAprobacionVigilancia consultarCifrasDeVigilancia(long idVigilancia, String idLocalidad) {
        String query = "";
        try {
            if (idLocalidad == null || idLocalidad.isEmpty()) {
                query = CONSULTA_CIFRAS.replace(PARAMETRO_ADMON_LOCAL, "");
                return template.queryForObject(query,
                        new Object[]{idVigilancia},
                        new int[]{Types.NUMERIC},
                        new ReporteAprobacionVigilanciaMapper());
            } else {
                query = CONSULTA_CIFRAS.replace(PARAMETRO_ADMON_LOCAL, ADMON_LOCAL);
                return template.queryForObject(query,
                        new Object[]{idVigilancia, idLocalidad},
                        new int[]{Types.NUMERIC, Types.VARCHAR},
                        new ReporteAprobacionVigilanciaMapper());
            }
        } catch (EmptyResultDataAccessException e) {
            logger.debug("No hay resultados: \n  " + query);
            return null;
        }
    }

    @Override
    @Propagable(catched = true)
    public List<DetalleReporteVigilanciaAprobada> detallesReporte(long idVigilancia, String idLocalidad) {
        if (idLocalidad == null || idLocalidad.isEmpty()) {
            String query = CONSULTA_DETALLE.replace(PARAMETRO_ADMON_LOCAL, "");
            return template.query(query,
                    new Object[]{idVigilancia},
                    new int[]{Types.NUMERIC},
                    new DetalleReporteVigilanciaAprobadaMapper());
        } else {
            String query = CONSULTA_DETALLE.replace(PARAMETRO_ADMON_LOCAL, ADMON_LOCAL);
            return template.query(query,
                    new Object[]{idVigilancia, idLocalidad},
                    new int[]{Types.NUMERIC, Types.VARCHAR},
                    new DetalleReporteVigilanciaAprobadaMapper());
        }
    }

    @Override
    public List<DetalleReporteVigilanciaAprobada> detallesExcluidosReporte(long idVigilancia, String idLocalidad) {
        if (idLocalidad == null || idLocalidad.isEmpty()) {
            String query = CONSULTA_DETALLE_EXCLUIDOS.replace(PARAMETRO_ADMON_LOCAL, "");
            return template.query(query,
                    new Object[]{idVigilancia},
                    new int[]{Types.NUMERIC},
                    new DetalleReporteVigilanciaAprobadaMapper());
        } else {
            String query = CONSULTA_DETALLE_EXCLUIDOS.replace(PARAMETRO_ADMON_LOCAL, ADMON_LOCAL);
            return template.query(query,
                    new Object[]{idVigilancia, idLocalidad},
                    new int[]{Types.NUMERIC, Types.VARCHAR},
                    new DetalleReporteVigilanciaAprobadaMapper());
        }
    }

    @Override
    public List<DetalleReporteVigilanciaAprobada> detallesCanceladosReporte(long idVigilancia, String idLocalidad) {
        if (idLocalidad == null || idLocalidad.isEmpty()) {
            String query = CONSULTA_DETALLE_CANCELADOS.replace(PARAMETRO_ADMON_LOCAL, "");
            return template.query(query,
                    new Object[]{idVigilancia},
                    new int[]{Types.NUMERIC},
                    new DetalleReporteVigilanciaAprobadaMapper());
        } else {
            String query = CONSULTA_DETALLE_CANCELADOS.replace(PARAMETRO_ADMON_LOCAL, ADMON_LOCAL);
            return template.query(query,
                    new Object[]{idVigilancia, idLocalidad},
                    new int[]{Types.NUMERIC, Types.VARCHAR},
                    new DetalleReporteVigilanciaAprobadaMapper());
        }
    }

    @Override
    public List<DetalleReporteVigilanciaAprobada> detallesCumplidosReporte(long idVigilancia, String idLocalidad) {
        if (idLocalidad == null || idLocalidad.isEmpty()) {
            String query = CONSULTA_DETALLE_CUMPLIDOS.replace(PARAMETRO_ADMON_LOCAL, "");
            return template.query(query,
                    new Object[]{idVigilancia},
                    new int[]{Types.NUMERIC},
                    new DetalleReporteVigilanciaAprobadaMapper());
        } else {
            String query = CONSULTA_DETALLE_CUMPLIDOS.replace(PARAMETRO_ADMON_LOCAL, ADMON_LOCAL);
            return template.query(query,
                    new Object[]{idVigilancia, idLocalidad},
                    new int[]{Types.NUMERIC, Types.VARCHAR},
                    new DetalleReporteVigilanciaAprobadaMapper());
        }
    }
    
    
}
