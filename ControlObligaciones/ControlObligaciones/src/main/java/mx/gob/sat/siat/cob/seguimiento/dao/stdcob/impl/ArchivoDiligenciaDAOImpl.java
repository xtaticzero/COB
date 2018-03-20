/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl;

import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.ArchivoDiligenciaDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.ArchivoDiligenciaMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.ArchivoDiligenciaSQL;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ArchivoDiligencia;
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
 * @author root
 */
@Repository
public class ArchivoDiligenciaDAOImpl implements ArchivoDiligenciaDAO{
    
    private static Logger logger = Logger.getLogger(ArchivoDiligenciaDAOImpl.class);
    @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG)
    private JdbcTemplate template;
    
    @Override
    @Propagable
    public ArchivoDiligencia obtenerArchivoDiligencia(Long idEntidadFederativa) {
        ArchivoDiligencia archivoDiligencia = template.queryForObject(ArchivoDiligenciaSQL.BUSCA_ARCHIVO_POR_NOMYENTIDAD,
                    new Object[]{idEntidadFederativa}, new ArchivoDiligenciaMapper());
        if (archivoDiligencia == null) {
            logger.info(ArchivoDiligenciaSQL.BUSCA_ARCHIVO_POR_NOMYENTIDAD);
        }
        return archivoDiligencia;
    }

    @Override
    @Propagable
    public void agregaArchivoDiligencia(ArchivoDiligencia archivoDiligencia) throws SeguimientoDAOException {
        ArchivoDiligencia archivo = null;
        try {
             archivo =  template.queryForObject(ArchivoDiligenciaSQL.BUSCA_ARCHIVO_POR_NOMYENTIDAD,
                    new Object[]{archivoDiligencia.getIdEntidadFederativa()}, new ArchivoDiligenciaMapper());
        } catch (EmptyResultDataAccessException e) {
            logger.debug("No hay resultados: \n  " + ArchivoDiligenciaSQL.BUSCA_ARCHIVO_POR_NOMYENTIDAD);
        }
        if (archivo == null) {
            
            template.update(ArchivoDiligenciaSQL.AGREGA_ARCHIVO, archivoDiligencia.getIdEntidadFederativa(), 
                    archivoDiligencia.getNombreArchivoCarga(),
                    archivoDiligencia.getTotalRegistrosCarga(), 
                    archivoDiligencia.getTotalRegistrosProcesados());
        }else{
              template.update(ArchivoDiligenciaSQL.EDITA_ARCHIVO, archivoDiligencia.getNombreArchivoCarga(),
                    archivoDiligencia.getTotalRegistrosCarga(), archivoDiligencia.getTotalRegistrosProcesados(), 
                    archivoDiligencia.getIdEntidadFederativa());
        }
    }
    
    @Override
    @Propagable
    public void agregaRutaArchivoDiligencia(ArchivoDiligencia archivoDiligencia) throws SeguimientoDAOException {
        ArchivoDiligencia archivo = null;
        try {
             archivo =  template.queryForObject(ArchivoDiligenciaSQL.BUSCA_ARCHIVO_POR_NOMYENTIDAD,
                    new Object[]{archivoDiligencia.getIdEntidadFederativa()}, new ArchivoDiligenciaMapper());
        } catch (EmptyResultDataAccessException e) {
            logger.debug("No hay resultados: \n  " + ArchivoDiligenciaSQL.BUSCA_ARCHIVO_POR_NOMYENTIDAD);
        }
        if (archivo != null) {
              template.update(ArchivoDiligenciaSQL.EDITA_RUTA_ARCHIVO, archivoDiligencia.getRutaArchivoResultado(), archivoDiligencia.getIdEntidadFederativa());
        }else{
              logger.info(ArchivoDiligenciaSQL.EDITA_RUTA_ARCHIVO);
        }
    }
    
}
