package mx.gob.sat.siat.cob.seguimiento.dao.catalogos.impl;

import java.sql.Types;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.UsuarioEFDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.mapper.UsuarioEFMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.mapper.UsuarioEFSingleMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.sql.UsuarioEFSQL;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.UsuarioEF;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioEFDAOImpl implements UsuarioEFDAO {

    private static Logger logger = Logger.getLogger(UsuarioEFDAOImpl.class);
    @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG)
    private JdbcTemplate template;

    /**
     *
     * @return
     */
    @Override
    public List<UsuarioEF> todosLosUsuariosEFXVigilancia(int idVigilancia) {
        List<UsuarioEF> listUsuarioEF;
        listUsuarioEF = template.query(UsuarioEFSQL.OBTEN_TODOS_USUARIOSEF_POR_VIGILANCIA,
                new Object[]{idVigilancia},
                new int[]{Types.INTEGER},
                new UsuarioEFMapper());
        if (listUsuarioEF == null || listUsuarioEF.isEmpty()) {
            logger.info(UsuarioEFSQL.OBTEN_TODOS_USUARIOSEF_POR_VIGILANCIA);
        }
        return listUsuarioEF;
    }

    /**
     *
     * @return
     */
    @Override
    public List<UsuarioEF> todosLosUsuariosEF() {
        List<UsuarioEF> listUsuarioEF;
        listUsuarioEF = template.query(UsuarioEFSQL.OBTEN_TODOS_USUARIOSEF, new UsuarioEFMapper());
        if (listUsuarioEF == null || listUsuarioEF.isEmpty()) {
            logger.info(UsuarioEFSQL.OBTEN_TODOS_USUARIOSEF);
        }
        return listUsuarioEF;
    }

    @Override
    @Propagable
    public List<UsuarioEF> obtenerUsuarioPorRfcCorto(String rfc) {

        return template.query(UsuarioEFSQL.BUSCA_USUARIOEF_POR_RFCCORTO,
                new Object[]{rfc},
                new int[]{Types.VARCHAR},
                new UsuarioEFSingleMapper());
    }

    /**
     *
     * @param usuarioEF
     *
     */
    @Override
    @Propagable
    public void agregaUsuarioEF(UsuarioEF usuarioEF) {
        int resultado = template.update(UsuarioEFSQL.AGREGA_USUARIOEF, usuarioEF.getRfcCorto(),
                usuarioEF.getIdEntidadFederativa(), usuarioEF.getNombreUsuario(), usuarioEF.getFechaInicio(), usuarioEF.getFechaFin(),
                usuarioEF.getCorreoElectronico());
        if (resultado == -1) {
            logger.info(UsuarioEFSQL.AGREGA_USUARIOEF);
        }

    }

    /**
     *
     * @param usuarioEF
     *
     */
    @Override
    @Propagable
    public void editaUsuarioEF(UsuarioEF usuarioEF) {
        int resultado = template.update(UsuarioEFSQL.EDITA_USUARIOEF, usuarioEF.getIdEntidadFederativa(), usuarioEF.getNombreUsuario(),
                usuarioEF.getCorreoElectronico(), usuarioEF.getRfcCorto());
        if (resultado == -1) {
            logger.info(UsuarioEFSQL.EDITA_USUARIOEF);
        }

    }

    @Override
    @Propagable
    public void reactivaUsuarioEF(UsuarioEF usuarioEF) {

        int resultado = template.update(UsuarioEFSQL.REACTIVA_USUARIOEF,
                usuarioEF.getRfcCorto());
        if (resultado == -1) {
            logger.info(UsuarioEFSQL.REACTIVA_USUARIOEF);
        }
    }

    /**
     *
     * @param usuarioEF
     * @return
     *
     */
    @Override
    @Propagable
    public Integer eliminaUsuarioEF(UsuarioEF usuarioEF) {
        Integer reg;
        reg = template.update(UsuarioEFSQL.ELIMINA_USUARIOEF, usuarioEF.getFechaFin(),
                usuarioEF.getRfcCorto());
        if (reg == -1) {
            logger.info(UsuarioEFSQL.ELIMINA_USUARIOEF);
        }
        return reg;

    }

    /**
     *
     * @param usuarioEF
     * @return
     */
    @Override
    public UsuarioEF buscaUsuarioEFPorID(UsuarioEF usuarioEF) {
        try {
            return template.queryForObject(UsuarioEFSQL.BUSCA_USUARIOEF_POR_ID,
                    new Object[]{usuarioEF.getRfcCorto()}, new UsuarioEFMapper());
        } catch (EmptyResultDataAccessException e) {
            logger.debug("No hay resultados: \n  " + UsuarioEFSQL.BUSCA_USUARIOEF_POR_ID);
            return null;
        }
    }

    /**
     *
     * @param usuarioEF
     * @return
     */
    @Override
    public Integer buscarUsuarioEFPorIdYNom(UsuarioEF usuarioEF) {
        Integer reg = null;
        SqlRowSet srs = template.queryForRowSet(UsuarioEFSQL.BUSCA_USUARIOEF_POR_IDYNOM,
                usuarioEF.getRfcCorto().toUpperCase());

        while (srs.next()) {
            reg = srs.getInt("REGISTROS");
        }

        return reg;
    }
    
    @Override
    @Propagable
    public List<UsuarioEF> obtenerUsuariosPorEntidad(Long entidad) {

        return template.query(UsuarioEFSQL.BUSCA_USUARIOS_POR_ENTIDAD,
                new Object[]{entidad},
                new int[]{Types.NUMERIC},
                new UsuarioEFSingleMapper());
    }
}
