/*	****************************************************************
 * Nombre de archivo: SGTBRetroARCADAOImpl.java
 * Autores: Emmanuel Estrada Gonzalez 		 	 		
 * Bitácora de modificaciones:
 * CR/Defecto Fecha Autor Descripción del cambio
 * ---------------------------------------------------------------------------
 * N/A 30/10/2013 <Nombre del desarrollador que está modificando el archivo>
 * ---------------------------------------------------------------------------
 */
package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl;

import java.sql.Types;
import mx.gob.sat.siat.cob.seguimiento.dao.arca.mapper.SGTBRetroArcaMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.SGTBRetroARCADAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.SGTBRetroArcaSQL;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.SGTBRetroARCA;
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
@Repository("sgtbRetroArcaDAO")
public class SGTBRetroARCADAOImpl implements SGTBRetroARCADAO {

    private static Logger logger = Logger.getLogger(SGTBRetroARCADAOImpl.class);
    @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG)
    private JdbcTemplate template;

    /**
     *
     * @param sgtbRetroARCA
     * @return
     */
    @Override
    @Propagable
    public int updateUltimoActualizado(SGTBRetroARCA sgtbRetroARCA) {
        return template.update(SGTBRetroArcaSQL.UPDATE_ID, new Object[]{sgtbRetroARCA.getIdProcesado()}, new int[]{Types.INTEGER});
    }

    /**
     *
     * @param idProcesado
     * @return
     */
    @Override
    @Propagable
    public int updateUltimoActualizado(int idProcesado) {
        return template.update(SGTBRetroArcaSQL.UPDATE_ID, new Object[]{idProcesado}, new int[]{Types.INTEGER});
    }

    /**
     *
     * @param sgtbRetroARCA
     * @return
     */
    @Override
    @Propagable
    public int insertUltimoActualizado(SGTBRetroARCA sgtbRetroARCA) {
        return template.update(SGTBRetroArcaSQL.INSERT_ITEM,
                new Object[]{sgtbRetroARCA.getIdProcesado()},
                new int[]{Types.INTEGER});
    }

    /**
     *
     * @return
     */
    @Override
    @Propagable
    public SGTBRetroARCA getUltimoActualizado() {
        try {
            return (SGTBRetroARCA) template.queryForObject(SGTBRetroArcaSQL.SELECT, new SGTBRetroArcaMapper());
        } catch (EmptyResultDataAccessException e) {
            logger.debug("No hay resultados: \n  " + SGTBRetroArcaSQL.SELECT);
            return null;
        }
    }
}
