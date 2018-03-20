/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.arca.impl;

import java.sql.Types;
import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dao.arca.MonitorArchivoArcaDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.arca.mapper.MonitorArchivoArcaMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.arca.sql.MonitorArchivoArcaSQL;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.MonitorArchivoArca;
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
 * @author Ulises
 */
@Repository("monitorArchivoArcaDAO")
public class MonitorArchivoArcaDAOImpl implements MonitorArchivoArcaDAO {

    private Logger log = Logger.getLogger(MonitorArchivoArcaDAOImpl.class);
    @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG)
    private JdbcTemplate template;

    @Propagable
    @Override
    public Integer insert(List<MonitorArchivoArca> monitoresArchivoArca) throws SeguimientoDAOException {
        Integer registro = null;
        for (MonitorArchivoArca monitor : monitoresArchivoArca) {
            registro = template.update(MonitorArchivoArcaSQL.INSERT, new Object[]{
                monitor.getIdVigilancia(),
                monitor.getIdAdmonLocal(),
                monitor.getCantidadDocumentos(),
                monitor.getCantidadObligacionPeriodo(),
                monitor.getCantidadOrigenMulta(),
                monitor.getCantidadReqAnteriores(),
                monitor.getFechaEnvioArca(),
                monitor.getEsEnvioResolucion()},
                    new int[]{
                Types.INTEGER,
                Types.VARCHAR,
                Types.INTEGER,
                Types.INTEGER,
                Types.INTEGER,
                Types.INTEGER,
                Types.VARCHAR,
                Types.INTEGER
            });
        }
        return registro;
    }

    @Propagable
    @Override
    public MonitorArchivoArca consultarMonitorArchivoArca(Long idVigilancia, String idAlsc, Integer esEnvioResolucion)
            throws SeguimientoDAOException {
        try {
            return (MonitorArchivoArca) template.queryForObject(MonitorArchivoArcaSQL.CONSULTAMONITORARCHIVO,
                    new Object[]{idVigilancia, idAlsc, esEnvioResolucion}, new MonitorArchivoArcaMapper());
        } catch (EmptyResultDataAccessException e) {
            log.debug(e);
            return null;
        }
    }

    @Propagable
    @Override
    public List<MonitorArchivoArca> consultarListaMonitorArchivoArca(Integer esEnvioResolucion)
            throws SeguimientoDAOException {
        try {
            return (List<MonitorArchivoArca>) template.query(MonitorArchivoArcaSQL.CONSULTALISTAMONITORARCHIVO,
                    new Object[]{esEnvioResolucion},
                    new MonitorArchivoArcaMapper());
        } catch (EmptyResultDataAccessException e) {
            log.debug(e);
            return null;
        }
    }

    @Propagable
    @Override
    public Integer actualizaMonitorArchivoArca(MonitorArchivoArca monitorArchivoArca) throws SeguimientoDAOException {
        return template.update(MonitorArchivoArcaSQL.ACTUALIZAMONITORARCHIVO, new Object[]{
            monitorArchivoArca.getCantidadDocumentos(),
            monitorArchivoArca.getCantidadObligacionPeriodo(),
            monitorArchivoArca.getCantidadOrigenMulta(),
            monitorArchivoArca.getCantidadReqAnteriores(),
            monitorArchivoArca.getIdVigilancia(),
            monitorArchivoArca.getIdAdmonLocal(),},
                new int[]{
            Types.INTEGER,
            Types.INTEGER,
            Types.INTEGER,
            Types.INTEGER,
            Types.INTEGER,
            Types.VARCHAR});

    }

    @Propagable
    @Override
    public Integer deleteMonitorArchivoArca(Long idVigilancia, String idAlsc, Integer esEnvioResolucion) throws SeguimientoDAOException {
        return template.update(MonitorArchivoArcaSQL.DELETE, new Object[]{
            idVigilancia,
            idAlsc,
            esEnvioResolucion},
                new int[]{
            Types.INTEGER,
            Types.INTEGER,
            Types.INTEGER
        });
    }
}
