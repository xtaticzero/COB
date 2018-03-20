package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl;

import java.sql.SQLException;
import java.sql.Types;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.ProcesoDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.ProcesoMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.ProcesosSQL;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ConsultarProcesosFiltroDto;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Proceso;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.api.PreparedStatementCreatorFactory;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoProceso;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import org.apache.log4j.Logger;

@Repository
public class ProcesoDAOImpl implements ProcesoDAO {

    private final Logger logger = Logger.getLogger(ProcesoDAOImpl.class);
    @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG)
    private JdbcTemplate template;

    @Override
    @Propagable
    public void crear(final Proceso proceso) throws SeguimientoDAOException {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            PreparedStatementCreator statement
                    = PreparedStatementCreatorFactory.getStatementCreator(PreparedStatementCreatorFactory.PROCESO, proceso);
            template.update(statement, keyHolder);
            proceso.setIdProceso(keyHolder.getKey().intValue());
        } catch (SQLException ex) {
            throw new SeguimientoDAOException(ex);
        }
    }

    @Transactional(readOnly = true)
    @Override
    @Propagable
    public List<Proceso> consultarProcesos(ConsultarProcesosFiltroDto filtro) {
        List<Proceso> resultado = new ArrayList<Proceso>();
        StringBuilder sqlQuery = new StringBuilder(ProcesosSQL.SQL_CONSULTA_PROCESOS_POR_FILTRO);
        if (filtro.getIdProceso() != null) {
            sqlQuery.append(" AND IDPROCESO = ").append(filtro.getIdProceso());
        }
        if (filtro.getListaIdProcesos() != null) {
            String[] ids = filtro.getListaIdProcesos().split(",");
            sqlQuery.append(" AND IDPROCESO IN(");
            for (int i = 0; i < ids.length; i++) {
                sqlQuery.append(ids[i]).append(",");
            }
            sqlQuery.deleteCharAt(sqlQuery.length() - 1);
            sqlQuery.append(")");
        }
        if (filtro.getIdManager() != null) {
            sqlQuery.append(" AND IDMANAGER like '").append(filtro.getIdManager()).append("'");
        }
        if (filtro.getExcluirEstados().size() > 0) {
            sqlQuery.append(" AND ESTADO NOT IN (");
            for (EstadoProceso estado : filtro.getExcluirEstados()) {
                sqlQuery.append(estado.getIdEdoDoc()).append(",");
            }
            sqlQuery.deleteCharAt(sqlQuery.length() - 1);
            sqlQuery.append(") ");
        }

        if (filtro.getIncluirEstados().size() > 0) {
            sqlQuery.append(" AND ESTADO IN (");
            for (EstadoProceso estado : filtro.getIncluirEstados()) {
                sqlQuery.append(estado.getIdEdoDoc()).append(",");
            }
            sqlQuery.deleteCharAt(sqlQuery.length() - 1);
            sqlQuery.append(") ");
        }
        if (filtro.getExcluirProcesos().size() > 0) {
            sqlQuery.append(" AND IDPROCESO NOT IN (");
            for (Proceso p : filtro.getExcluirProcesos()) {
                sqlQuery.append(p.getIdProceso()).append(",");
            }
            sqlQuery.deleteCharAt(sqlQuery.length() - 1);
            sqlQuery.append(") ");
        }
        try {
            logger.debug(sqlQuery.toString());
            resultado = this.template.query(sqlQuery.toString(), new ProcesoMapper());
        } catch (EmptyResultDataAccessException e) {
            logger.debug("No hay resultados");
        }
        return resultado;
    }

    @Transactional(readOnly = true)
    @Override
    @Propagable
    public List<Proceso> consultarProcesosSubsecuentes(Proceso proceso) {
        List<Proceso> subsecuentes = new ArrayList<Proceso>();
        try {
            subsecuentes = (List<Proceso>) this.template.query(ProcesosSQL.CONSULTA_PROCESOS_POR_LANZAR, new Object[]{proceso.getIdProceso().toString(), proceso.getIdProceso().toString(), proceso.getIdProceso().toString(), proceso.getIdProceso().toString()}, new ProcesoMapper());
        } catch (EmptyResultDataAccessException e) {
            logger.debug("No hay subsecuentes");
        }
        return subsecuentes;
    }

    @Override
    @Propagable
    public void actualizarEstado(Proceso proceso, EstadoProceso idEdoProceso) {
        proceso.setEstado(idEdoProceso.getIdEdoDoc());
        logger.debug(proceso.getIdProceso() + " - " + proceso.getNombre() + " --> " + idEdoProceso.getIdEdoDoc());
        actualizar(proceso);
    }

    @Override
    @Propagable
    public void actualizar(Proceso proceso) {
        template.update(ProcesosSQL.ACTUALIZA_PROCESO, new Object[]{
            proceso.getPrioridad(),
            proceso.getNombre(),
            proceso.getDescripcion(),
            proceso.getLanzador(),
            proceso.getExcluir(),
            proceso.getProgramacion(),
            proceso.getEstado(),
            proceso.getIntentos(),
            proceso.getIntentosMax(),
            proceso.getIdManager(),
            proceso.getTipoCadena(),
            proceso.getFechaInicioEjecucion(),
            proceso.getFechaFinEjecucion(),
            proceso.getIdProceso()
        }, new int[]{
            Types.DECIMAL,
            Types.VARCHAR,
            Types.VARCHAR,
            Types.VARCHAR,
            Types.VARCHAR,
            Types.VARCHAR,
            Types.DECIMAL,
            Types.DECIMAL,
            Types.DECIMAL,
            Types.VARCHAR,
            Types.DECIMAL,
            Types.TIMESTAMP,
            Types.TIMESTAMP,
            Types.DECIMAL
        });
    }

    @Override
    @Propagable
    public void actualizarInicioEjecucion(Proceso proceso) {
        template.update(ProcesosSQL.ACTUALIZA_INICIOEJECUCION, new Object[]{
            proceso.getFechaInicioEjecucion(),
            proceso.getEstado(),
            proceso.getIdProceso()
        }, new int[]{
            Types.TIMESTAMP,
            Types.DECIMAL,
            Types.DECIMAL
        });
    }

    @Override
    @Propagable
    public void actualizarFinEjecucion(Proceso proceso) {
        template.update(ProcesosSQL.ACTUALIZA_FINEJECUCION, new Object[]{
            proceso.getFechaFinEjecucion(),
            proceso.getIntentos(),
            proceso.getEstado(),
            proceso.getIdProceso()
        }, new int[]{
            Types.TIMESTAMP,
            Types.DECIMAL,
            Types.DECIMAL,
            Types.DECIMAL
        });
    }

    @Override
    @Propagable
    public List<Proceso> consultarPorId(List<Integer> ids) {
        StringBuilder sqlQuery = new StringBuilder(ProcesosSQL.SQL_CONSULTA_PROCESOS_POR_IDS);
        sqlQuery.append("AND IDPROCESO IN(");
        StringBuilder sIds = new StringBuilder("");
        for (Integer i : ids) {
            sIds.append(i).append(",");
        }
        if (ids.size() > 0) {
            sqlQuery.append(sIds.substring(0, sIds.length() - 1)).append(")");
            return (List<Proceso>) this.template.query(sqlQuery.toString(), new ProcesoMapper());
        } else {
            return new ArrayList<Proceso>();
        }
    }

    @Override
    @Propagable
    public List<Proceso> consultarPorLanzadores(String lanzador) {
        List<Proceso> resultado = null;
        try {
            StringBuilder sqlQuery = new StringBuilder(ProcesosSQL.SQL_CONSULTA_LANZADOR);
            sqlQuery.append("AND LANZADOR IN (").append(lanzador).append(")");
            resultado = this.template.query(sqlQuery.toString(), new ProcesoMapper());
        } catch (EmptyResultDataAccessException e) {
            logger.debug("No hay procesos");
        }
        return resultado;
    }

    @Transactional
    @Override
    @Propagable
    public void actualizarManager(String manager) {
        template.update(ProcesosSQL.ACTUALIZA_MANAGER, new Object[]{
            manager
        }, new int[]{
            Types.VARCHAR
        });
    }

    @Override
    public List<Proceso> consultarPrimerLanzador(Proceso p) {
        List<Proceso> resultado = new ArrayList<Proceso>();
        if (p.getLanzador() == null) {
            resultado.add(p);
            return resultado;
        } else {
            ConsultarProcesosFiltroDto filtro = new ConsultarProcesosFiltroDto();
            filtro.setListaIdProcesos(p.getLanzador());
            resultado = this.consultarProcesos(filtro);
            if (resultado.isEmpty()) {
                resultado.add(p);
                return resultado;
            } else {
                for (Proceso proceso : resultado) {
                    resultado = consultarPrimerLanzador(proceso);
                }
                return resultado;
            }
        }
    }

    @Override
    public void iniciarEstados() throws SeguimientoDAOException {
        template.update(ProcesosSQL.SQL_INICIAR_ESTADOS);
    }

    @Override
    public boolean isPrimeroEnCadena(Integer idProceso) throws SeguimientoDAOException {
        try {
            StringBuilder sqlQuery = new StringBuilder(ProcesosSQL.SQL_CONSULTA_PROCESOS_POR_IDS);
            sqlQuery.append("AND IDPROCESO = ").append(idProceso)
                    .append("AND LANZADOR IS NULL");
            List<Proceso> resultado = this.template.query(sqlQuery.toString(), new ProcesoMapper());
            if (resultado != null && resultado.size() > 0) {
                return true;
            }
        } catch (EmptyResultDataAccessException e) {
            logger.debug("No hay procesos");
        }
        return false;
    }
}
