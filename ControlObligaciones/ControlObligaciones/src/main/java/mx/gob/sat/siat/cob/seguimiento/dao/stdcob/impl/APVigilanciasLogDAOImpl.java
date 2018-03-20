/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.APVigilanciasLogDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.VigilanciasLogMapper;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.APVigilanciasLogSQL.BODY_1_SELECT_IDVIGILANCIA;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.APVigilanciasLogSQL.BODY_2_SELECT_IDVIGILANCIA;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.APVigilanciasLogSQL.CLEAN_REGISTRO;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.APVigilanciasLogSQL.COUNT_NUM_ERR;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.APVigilanciasLogSQL.FOOTER_SELECT;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.APVigilanciasLogSQL.HEDER_SELECT;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.APVigilanciasLogSQL.INSERT_ERROR;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.APVigilanciasLogSQL.PARAMETRO_IDADMONLOCAL_1;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.APVigilanciasLogSQL.PARAMETRO_IDADMONLOCAL_2;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.APVigilanciasLogSQL.SELECT_ERROR;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.APVigilanciasLogSQL.UPDATE_ERROR;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VigilanciasLogDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.NivelEmisionEnum;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

/**
 *
 * @author emmanuel
 */
@Repository
public class APVigilanciasLogDAOImpl implements APVigilanciasLogDAO {

    @Autowired
    @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG)
    private JdbcTemplate template;
    private final static String CENTRAL = "central";
    private final static String CENTRAL_PARAM = "and IDADMONLOCAL = ?";
    private final static String CENTRAL_NULL = "and IDADMONLOCAL = 'central'";
    private final Logger logger = Logger.getLogger(APVigilanciasLogDAOImpl.class);

    @Override
    @Propagable(catched = true)
    public Integer insertarError(Long idVigilancia, String idAdmonLocal, String descripcion) {
        Object[] params;

        // define query arguments
        if (idAdmonLocal == null) {
            params = new Object[]{idVigilancia, CENTRAL, descripcion};
        } else {
            params = new Object[]{idVigilancia, idAdmonLocal, descripcion};
        }
        // define SQL types of the arguments
        int[] types = new int[]{Types.NUMERIC, Types.VARCHAR, Types.VARCHAR};

        // execute insert query to insert the data
        // return number of row / rows processed by the executed query
        return template.update(INSERT_ERROR, params, types);
    }

    @Override
    @Propagable(catched = true)
    public Integer updateError(Long idVigilancia, String idAdmonLocal, String descripcion) {
        Object[] params;
        // define query arguments
        if (idAdmonLocal == null) {
            params = new Object[]{descripcion, idVigilancia, CENTRAL};
        } else {
            params = new Object[]{descripcion, idVigilancia, idAdmonLocal};
        }
        // execute insert query to insert the data
        // return number of row / rows processed by the executed query
        return template.update(UPDATE_ERROR, params);

    }

    @Override
    @Propagable(catched = true)
    public VigilanciasLogDTO getEerrorVigilancias(Long idVigilancia, String idAdmonLocal) {
        return template.queryForObject(
                SELECT_ERROR, new Object[]{idVigilancia, idAdmonLocal}, new VigilanciasLogMapper());
    }

    @Override
    @Propagable(catched = true)
    public Integer cleanError(Long idVigilancia, String idAdmonLocal) {
        // define query arguments
        Object[] params = new Object[]{idVigilancia, idAdmonLocal};
        // execute insert query to insert the data
        // return number of row / rows processed by the executed query
        return template.update(CLEAN_REGISTRO, params);
    }

    @Override
    @Propagable(catched = true)
    public Boolean vigilanciaConError(Long idVigilancia, String idAdmonLocal) {
        Integer reg = null;
        SqlRowSet srs;
        String query = COUNT_NUM_ERR;

        if (idAdmonLocal == null || (idAdmonLocal.equals(CENTRAL))) {
            srs = template.queryForRowSet((query.replace(CENTRAL, CENTRAL_NULL)), idVigilancia);
        } else {
            srs = template.queryForRowSet((query.replace(CENTRAL, CENTRAL_PARAM)), idVigilancia, idAdmonLocal);
        }

        while (srs.next()) {
            reg = srs.getInt("REGISTROS");
        }

        return (reg != 0);
    }

    @Override
    @Propagable(catched = true)
    public List<VigilanciasLogDTO> getVigilanciasConError(NivelEmisionEnum nivelEmi, Long[] arrIdVigilancia, String idAdmonLocal) {
        StringBuffer query;
        if ((arrIdVigilancia == null) || (arrIdVigilancia.length == 0)) {
            return new ArrayList<VigilanciasLogDTO>();
        }
        query = new StringBuffer(HEDER_SELECT);
        if (arrIdVigilancia.length > 0) {
            query.append(BODY_1_SELECT_IDVIGILANCIA);
            for (int i = 0; i < arrIdVigilancia.length; i++) {
                if (i == 0) {
                    query.append(arrIdVigilancia[i].toString());
                } else {
                    query.append(",").append(arrIdVigilancia[i].toString());
                }
            }
            query.append(BODY_2_SELECT_IDVIGILANCIA);
        }
        logger.debug(nivelEmi);
        query.append(PARAMETRO_IDADMONLOCAL_1);
        switch (nivelEmi) {

            case CENTRAL:
                if (idAdmonLocal == null) {
                    query.append(CENTRAL);
                }
                break;
            case LOCAL:
                if (idAdmonLocal != null) {
                    query.append(idAdmonLocal);
                }
                break;
        }
        query.append(PARAMETRO_IDADMONLOCAL_2);

        query.append(FOOTER_SELECT);
        return template.query(query.toString(), new VigilanciasLogMapper());
    }
}
