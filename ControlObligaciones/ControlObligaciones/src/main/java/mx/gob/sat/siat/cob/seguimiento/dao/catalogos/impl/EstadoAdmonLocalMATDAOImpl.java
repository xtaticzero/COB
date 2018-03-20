package mx.gob.sat.siat.cob.seguimiento.dao.catalogos.impl;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.EstadoAdmonLocalMATDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.mapper.ComboMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.mapper.EstadoAdmonLocalMATMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.sql.EstadoAdmonLocalMATSQL;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Combo;

import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.EstadoAdmonLocalMAT;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EstadoAdmonLocalMATDAOImpl implements EstadoAdmonLocalMATDAO {

    private static Logger logger = Logger.getLogger(EstadoAdmonLocalMATDAOImpl.class);
    @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG)
    private JdbcTemplate template;

    /**
     *
     * @return
     */
    @Override
    public List<EstadoAdmonLocalMAT> todosLosEstadosAdmonLocalMAT() {
        List<EstadoAdmonLocalMAT> listEstadoAdmonLocalMAT;
        listEstadoAdmonLocalMAT = template.query(EstadoAdmonLocalMATSQL.OBTEN_TODOS_ESTADOS_ADMONLOCAL_MAT, new EstadoAdmonLocalMATMapper());
        if (listEstadoAdmonLocalMAT == null || listEstadoAdmonLocalMAT.isEmpty()) {
            logger.info(EstadoAdmonLocalMATSQL.OBTEN_TODOS_ESTADOS_ADMONLOCAL_MAT);
        }
        return listEstadoAdmonLocalMAT;
    }

    /**
     *
     * @param estadoAdmonLocalMAT
     *
     */
    @Override
    @Propagable
    public void editaEstadoAdmonLocalMAT(EstadoAdmonLocalMAT estadoAdmonLocalMAT) {
        int resultado = template.update(EstadoAdmonLocalMATSQL.EDITA_ESTADO_ADMONLOCAL_MAT, estadoAdmonLocalMAT.getEsOperacionMAT(), estadoAdmonLocalMAT.getIdAdmonLocal());
        if (resultado == -1) {
            logger.info(EstadoAdmonLocalMATSQL.EDITA_ESTADO_ADMONLOCAL_MAT);
        }
    }

    /**
     *
     * @return
     */
    @Override
    public List<Combo> obtenerComboEstado() {
        List<Combo> combo;
        combo = template.query(EstadoAdmonLocalMATSQL.LISTA_COMBO_ESTADO, new ComboMapper());
        return combo;
    }

    @Propagable
    @Override
    public EstadoAdmonLocalMAT consultaPorIdAdmonLocal(String idAdmonLocal) throws SeguimientoDAOException {
        try {
            return (EstadoAdmonLocalMAT) template.queryForObject(
                    EstadoAdmonLocalMATSQL.CONSULTA_IDADMONLOCAL,
                    new Object[]{idAdmonLocal},
                    new EstadoAdmonLocalMATMapper());
        } catch (EmptyResultDataAccessException e) {
            logger.error("### No hay resultados : \n" + EstadoAdmonLocalMATSQL.CONSULTA_IDADMONLOCAL);
            return null;
        }
    }
}
