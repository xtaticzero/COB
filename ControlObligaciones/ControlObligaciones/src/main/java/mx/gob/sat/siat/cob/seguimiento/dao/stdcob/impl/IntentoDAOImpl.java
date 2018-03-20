package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl;

import java.sql.SQLException;
import java.sql.Types;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.IntentoDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.IntentoMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.IntentoSQL;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ConsultarIntentosFiltroDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.EjecucionDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.IntentoDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.api.PreparedStatementCreatorFactory;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
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
public class IntentoDAOImpl implements IntentoDAO {

    private static Logger logger = Logger.getLogger(IntentoDAOImpl.class);
    @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG)
    private JdbcTemplate template;

    /**
     * @param filtro
     * @return
     * @throws SeguimientoDAOException
     */
    @Override
    @Propagable
    public List<IntentoDTO> consultar(ConsultarIntentosFiltroDTO filtro) throws SeguimientoDAOException {
        logger.debug("Inicia consultar");
        List<IntentoDTO> resultado = new ArrayList<IntentoDTO>();
        StringBuilder sqlQuery = new StringBuilder(IntentoSQL.CONSULTAR_INTENTOS);
        List params = new ArrayList();
        Object[] sendParams = null;
        if (filtro != null) {
            if (filtro.getId() != null) {
                sqlQuery.append(" AND IDINTENTOJOB = ?");
                params.add(filtro.getId());
            }
            if (filtro.getIdEjecucion() != null) {
                sqlQuery.append(" AND IDEJECUCION = ?");
                params.add(filtro.getIdEjecucion());
            }
        }
        if (params.size() > 0) {
            sendParams = new Object[params.size()];
            int i = 0;
            for (Object o : params) {
                sendParams[i++] = o;
            }
        }
        logger.debug("Fin consultar");
        try {
            resultado = (List<IntentoDTO>) this.template.queryForObject(sqlQuery.toString(), sendParams, new IntentoMapper());
        } catch (EmptyResultDataAccessException e) {
            logger.error("No hay resultados IntentoDTO");
        }
        return resultado;

    }

    /**
     * @param ejecucion
     * @return
     * @throws SeguimientoDAOException
     */
    @Override
    @Propagable
    public IntentoDTO consultarUltimo(EjecucionDTO ejecucion) throws SeguimientoDAOException {
        StringBuilder sqlQuery = new StringBuilder(IntentoSQL.CONSULTAR_ULTIMO);
        Object[] sendParams = {ejecucion.getId(), ejecucion.getId()};
        try {
            List<IntentoDTO> recordset = this.template.query(sqlQuery.toString(), sendParams, new IntentoMapper());
            if (recordset.size() > 0) {
                return recordset.get(0);
            } else {
                return null;
            }
        } catch (EmptyResultDataAccessException e) {
            logger.error(e);
            return null;
        }
    }

    /**
     * @param ejecucion
     * @return
     * @throws SeguimientoDAOException
     */
    @Override
    @Propagable
    public IntentoDTO consultarPrimer(EjecucionDTO ejecucion) throws SeguimientoDAOException {
        StringBuilder sqlQuery = new StringBuilder(IntentoSQL.CONSULTAR_PRIMERO);
        Object[] sendParams = {ejecucion.getId()};
        List<IntentoDTO> recordset = new ArrayList<IntentoDTO>();
        try {
            recordset = (List<IntentoDTO>) this.template.query(sqlQuery.toString(), sendParams, new IntentoMapper());
        } catch (EmptyResultDataAccessException e) {
            logger.error("No hay resultados");
        }
        if (recordset.size() > 0) {
            return recordset.get(0);
        } else {
            return null;
        }
    }

    /**
     * @param intento
     * @throws SeguimientoDAOException
     */
    @Override
    @Propagable
    @Transactional
    public void insertar(final IntentoDTO intento) throws SeguimientoDAOException {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try{
            PreparedStatementCreator statement = 
                PreparedStatementCreatorFactory.getStatementCreator(PreparedStatementCreatorFactory.INTENTO_JOB
                                                                    ,intento);
            template.update(statement, keyHolder);
            intento.setId(keyHolder.getKey().intValue());
        }
        catch(SQLException ex){
            throw new SeguimientoDAOException(ex);
        }
    }

    /**
     * @param intento
     * @throws SeguimientoDAOException
     */
    @Override
    @Propagable
    @Transactional
    public void actualizar(IntentoDTO intento) throws SeguimientoDAOException {
        template.update(IntentoSQL.ACTUALIZAR, new Object[]{
            intento.getFin(),
            intento.getEstado(),
            intento.getObservaciones(),
            intento.getId()
        }, new int[]{
            Types.TIMESTAMP,
            Types.DECIMAL,
            Types.VARCHAR,
            Types.DECIMAL
        });
    }
    
    /**
     * 
     * @return
     * @throws SeguimientoDAOException 
     */
    @Override
    @Propagable(catched = true)
    public List<IntentoDTO> consultarPorIdEjecucion(Integer idEjecucion) throws SeguimientoDAOException {
        logger.debug("entra consultarPorIdEjecucion");
        return template.query(IntentoSQL.CONSULTAR_X_IDEJECUCION,new Object[]{
            idEjecucion
        },new int[]{
            Types.DECIMAL
        }, new IntentoMapper());
    }
    
    /**
     * 
     * @param intento
     * @return
     * @throws SeguimientoDAOException 
     */
    @Override
    @Propagable(catched = true)
    public int insertarEnHistorico(IntentoDTO intento) throws SeguimientoDAOException {
        logger.debug("entra insertarEnHistorico");
        return template.update(IntentoSQL.INSERTAR_HISTORICO,new Object[]{
            intento.getId(),
            intento.getIdEjecucion(),
            intento.getIntento(),
            intento.getInicio(),
            intento.getFin(),
            intento.getObservaciones(),
            intento.getEstado()
        },new int[]{
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
     * @param intento
     * @return
     * @throws SeguimientoDAOException 
     */
    @Override
    @Propagable(catched = true)
    public int borrarRegistro(IntentoDTO intento) throws SeguimientoDAOException {
        logger.debug("entra borrarRegistro");
        return template.update(IntentoSQL.BORRAR_REGISTRO,new Object[]{
            intento.getId()
        },new int[]{
            Types.DECIMAL
        });
    }
    
    
}
