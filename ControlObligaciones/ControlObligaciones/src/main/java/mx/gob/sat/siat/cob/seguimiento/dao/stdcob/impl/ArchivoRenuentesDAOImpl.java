/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl;

import java.sql.Types;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.ArchivoRenuentesDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.ArchivoRenuentesMapper;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.ArchivoRenuentesSQL.*;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ArchivoRenuente;
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
 * @author Alex
 */
@Repository
public class ArchivoRenuentesDAOImpl implements ArchivoRenuentesDAO {

    private static Logger logger = Logger.getLogger(ArchivoRenuentesDAOImpl.class);
    @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG)
    private JdbcTemplate template;

    @Override
    @Propagable
    public Long obtenerIdCargaRenuents() throws SeguimientoDAOException {
        try {
            return template.queryForObject(OBTENER_ID, Long.class);
        } catch (EmptyResultDataAccessException e) {
            logger.debug(e);
            return null;
        }
    }

    @Override
    @Propagable
    public void agregaArchivoRenuente(ArchivoRenuente archivoRenuente) {
        template.update(AGREGA_ARCHIVO,
                new Object[]{
                    archivoRenuente.getIdCargaRenunetes(),
                    archivoRenuente.getUsuarioCarga(),
                    archivoRenuente.getNumEmpleadoCarga(),
                    archivoRenuente.getNombreArchivoCarga(),
                    archivoRenuente.getTotalRegistrosArchivoCarga(),
                    archivoRenuente.getRutaArchivoResultado(),
                    archivoRenuente.getTotalRegistrosErrores()
                }, new int[]{Types.NUMERIC, Types.VARCHAR, Types.VARCHAR,
                    Types.VARCHAR, Types.NUMERIC, Types.VARCHAR,
                    Types.NUMERIC});
    }

    @Override
    @Propagable
    public ArchivoRenuente obtenerArchivoRenuente() {
        try {
            return template.queryForObject(BUSCA_ARCHIVO_POR_ID, new ArchivoRenuentesMapper());
        } catch (EmptyResultDataAccessException e) {
            logger.info(BUSCA_ARCHIVO_POR_ID);
            logger.error(e);
            return null;
        }
    }
}
