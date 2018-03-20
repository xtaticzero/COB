package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl;

import java.sql.SQLException;
import java.sql.Types;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.EjecucionDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.BitacoraEjecucionMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.EjecucionMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.EjecucionJobSQL;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.BitacoraEjecucionDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ConsultarEjecuionFiltroDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.EjecucionDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Proceso;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.api.PreparedStatementCreatorFactory;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import mx.gob.sat.siat.cob.seguimiento.util.Utilerias;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class EjecucionDAOImpl implements EjecucionDAO {

    private static Logger logger = Logger.getLogger(EjecucionDAOImpl.class);
    @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG)
    private JdbcTemplate template;

    private static final String ORACLE_STR_FECHA = "YYYY-MM-DD HH24:MI:SS";
    private static final String ORACLE_STR_HORA_CERO = " 00:00:00";

    @Override
    @Propagable
    public List<EjecucionDTO> consultar(ConsultarEjecuionFiltroDTO filtro) throws SeguimientoDAOException {
        logger.debug("Inicia consultar");
        List<EjecucionDTO> resultado = new ArrayList<EjecucionDTO>();
        StringBuilder sqlQuery = new StringBuilder(EjecucionJobSQL.CONSULTAR);
        List params = new ArrayList();
        Object[] sendParams = null;
        if (filtro != null) {
            if (filtro.getIdProceso() != null) {
                sqlQuery.append(" AND IDPROCESO = ").append(filtro.getIdProceso());
            }
            if (filtro.getInicioDe() != null) {
                String sFecha = Utilerias.formatearFechaAAAAMMDD(filtro.getInicioDe()) + ORACLE_STR_HORA_CERO;
                sqlQuery.append(" AND FECHAINICIO >= TO_DATE('").append(sFecha).append("','").append(ORACLE_STR_FECHA).append("')");
            }
            if (filtro.getInicioA() != null) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(filtro.getInicioA());
                calendar.add(Calendar.DAY_OF_YEAR, 1);
                String sFecha = Utilerias.formatearFechaAAAAMMDD(calendar.getTime()) + ORACLE_STR_HORA_CERO;
                sqlQuery.append(" AND A.FECHAINICIO < TO_DATE('").append(sFecha).append("','").append(ORACLE_STR_FECHA).append("')");
            }
        }
        if (params.size() > 0) {
            sendParams = new Object[params.size()];
            int i = 0;
            for (Object o : params) {
                sendParams[i++] = o;
            }
        }
        try {
            resultado = (List<EjecucionDTO>) this.template.queryForObject(sqlQuery.toString(), sendParams, new EjecucionMapper());
        } catch (EmptyResultDataAccessException e) {
            logger.debug("No hay resultados con el filtro especificado.");
        }
        logger.debug("Fin consultar");
        return resultado;

    }

    @Override
    @Propagable
    public EjecucionDTO consultarUltima(Proceso proceso) throws SeguimientoDAOException {
        String sqlQuery = EjecucionJobSQL.CONSULTAR_ULTIMA;
        Object[] sendParams = {proceso.getIdProceso(), proceso.getIdProceso()};
        try {
            return this.template.queryForObject(sqlQuery, sendParams, new EjecucionMapper());

        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    @Propagable
    @Transactional
    public void insertar(EjecucionDTO ejecucion) throws SeguimientoDAOException {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            PreparedStatementCreator statement
                    = PreparedStatementCreatorFactory.getStatementCreator(PreparedStatementCreatorFactory.EJECUCION_JOB, ejecucion);
            template.update(statement, keyHolder);
            ejecucion.setId(keyHolder.getKey().intValue());
        } catch (SQLException e) {
            throw new SeguimientoDAOException(e);
        }
    }

    @Override
    @Propagable
    @Transactional
    public void actualizar(EjecucionDTO ejecucion) throws SeguimientoDAOException {
        template.update(EjecucionJobSQL.ACTUALIZAR, new Object[]{
            ejecucion.getFin(),
            ejecucion.getEstado(),
            ejecucion.getIntento(),
            ejecucion.getObservaciones(),
            ejecucion.getId()
        }, new int[]{
            Types.TIMESTAMP,
            Types.DECIMAL,
            Types.DECIMAL,
            Types.VARCHAR,
            Types.DECIMAL
        });
    }

    @Override
    public List<BitacoraEjecucionDTO> consultarBitacora(ConsultarEjecuionFiltroDTO filtro) throws SeguimientoDAOException {
        logger.debug("Inicia consultar");
        List<BitacoraEjecucionDTO> resultado = new ArrayList<BitacoraEjecucionDTO>();
        StringBuilder sqlQuery = new StringBuilder(EjecucionJobSQL.CONSULTAR_JOIN_INTENTO);
        List params = new ArrayList();
        Object[] sendParams = null;
        if (filtro != null) {
            if (filtro.getIdProceso() != null) {
                sqlQuery.append(" AND IDPROCESO = ").append(filtro.getIdProceso());
            }
            if (filtro.getInicioDe() != null) {
                String sFecha = Utilerias.formatearFechaAAAAMMDD(filtro.getInicioDe()) + ORACLE_STR_HORA_CERO;
                sqlQuery.append(" AND A.FECHAINICIO >= TO_DATE('").append(sFecha).append("','").append(ORACLE_STR_FECHA).append("')");
            }
            if (filtro.getInicioA() != null) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(filtro.getInicioA());
                calendar.add(Calendar.DAY_OF_YEAR, 1);
                String sFecha = Utilerias.formatearFechaAAAAMMDD(calendar.getTime()) + ORACLE_STR_HORA_CERO;
                sqlQuery.append(" AND A.FECHAINICIO < TO_DATE('").append(sFecha).append("','").append(ORACLE_STR_FECHA).append("')");
            }
        }
        if (params.size() > 0) {
            sendParams = new Object[params.size()];
            int i = 0;
            for (Object o : params) {
                sendParams[i++] = o;
            }
        }
        try {
            sqlQuery.append(" ORDER BY B.IDINTENTOJOB DESC ");
            logger.debug(sqlQuery.toString());
            resultado = (List<BitacoraEjecucionDTO>) this.template.queryForObject(sqlQuery.toString(), sendParams, new BitacoraEjecucionMapper());
        } catch (EmptyResultDataAccessException e) {
            logger.debug("No hay resultados con el filtro especificado.");
        }
        logger.debug("Fin consultar");
        return resultado;
    }

    /**
     *
     * @return @throws SeguimientoDAOException
     */
    @Override
    @Propagable(catched = true)
    public List<EjecucionDTO> consultarAnteriores() throws SeguimientoDAOException {
        logger.debug("entra consultarAnteriores");
        return template.query(EjecucionJobSQL.CONSULTAR_FECHA_ANTERIOR, new EjecucionMapper());
    }

    /**
     *
     * @param ejecucion
     * @return
     * @throws SeguimientoDAOException
     */
    @Override
    @Propagable(catched = true)
    public int insertarEnHistorico(EjecucionDTO ejecucion) throws SeguimientoDAOException {
        logger.debug("entra insertarEnHistorico");
        return template.update(EjecucionJobSQL.INSERTAR_HISTORICO, new Object[]{
            ejecucion.getId(),
            ejecucion.getIdProceso(),
            ejecucion.getIntento(),
            ejecucion.getInicio(),
            ejecucion.getFin(),
            ejecucion.getObservaciones(),
            ejecucion.getEstado()
        }, new int[]{
            Types.DECIMAL,
            Types.DECIMAL,
            Types.DECIMAL,
            Types.TIMESTAMP,
            Types.TIMESTAMP,
            Types.VARCHAR,
            Types.DECIMAL
        });
    }

    /**
     *
     * @param ejecucion
     * @return
     * @throws SeguimientoDAOException
     */
    @Override
    @Propagable(catched = true)
    public int borrarRegistro(EjecucionDTO ejecucion) throws SeguimientoDAOException {
        logger.debug("entra borrarRegistro");
        return template.update(EjecucionJobSQL.BORRAR_REGISTRO, new Object[]{
            ejecucion.getId()
        }, new int[]{
            Types.DECIMAL
        });
    }
}
