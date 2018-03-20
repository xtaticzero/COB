package mx.gob.sat.siat.cob.seguimiento.dao.arca.impl;

import java.sql.Types;
import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dao.arca.ReqsAnterioresDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.arca.mapper.RequerimientoAnteriorArcaMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.arca.sql.ReqsAnterioresSQL;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.RequerimientosAnteriores;
import mx.gob.sat.siat.cob.seguimiento.exception.ARCADAOExcepcion;
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
 * @author Agustin
 */
@Repository("reqsAnterioresArcaDAO")
public class ReqsAnterioresDAOImpl implements ReqsAnterioresDAO, ReqsAnterioresSQL {

    private final Logger log = Logger.getLogger(ReqsAnterioresDAOImpl.class);
    @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_ARCA)
    private JdbcTemplate template;

    /**
     *
     * @param reqsAnteriores
     * @return
     */
    @Override
    @Propagable(catched = true)
    public Integer guardaReqsAnteriores(List<RequerimientosAnteriores> reqsAnteriores) {
        int respuesta = 0;
        String queryItem = INSERT_REQS_ANTERIORES_ITEM;

        for (int ii = 0; ii < reqsAnteriores.size(); ii++) {
            queryItem += "('" + reqsAnteriores.get(ii).getIdDocumento() + "',";
            queryItem += "'" + reqsAnteriores.get(ii).getIdDocumentoPadre() + "',";
            queryItem += "'" + reqsAnteriores.get(ii).getDescrTipoRequ() + "',";
            queryItem += "'" + reqsAnteriores.get(ii).getFechaNotificacion() + "',";
            queryItem += "'" + reqsAnteriores.get(ii).getDescrObligacion() + "',";
            queryItem += "'" + reqsAnteriores.get(ii).getDescrPeriodo() + "',";
            queryItem += "'" + reqsAnteriores.get(ii).getEjercicio() + "',";
            queryItem += reqsAnteriores.get(ii).getFechaPresentacionObligacion() + ",";
            queryItem += reqsAnteriores.get(ii).getFechaVencimientoReq() + ",";
            if (ii != reqsAnteriores.size() - 1) {
                queryItem += "'" + reqsAnteriores.get(ii).getEdoObligacionVencimiento() + "'),\n";
            } else {
                queryItem += "'" + reqsAnteriores.get(ii).getEdoObligacionVencimiento() + "')\n";
            }

        }
        log.info("### SQL : \n " + queryItem);
        respuesta = template.update(queryItem);

        return respuesta;
    }

    @Propagable
    @Override
    public List<RequerimientosAnteriores> consultarRequerimientosPorIdVigilancia(Long idVigilancia, Integer idAlsc) throws ARCADAOExcepcion {
        try {
            return (List<RequerimientosAnteriores>) template.query(SELECT_X_ID_VIGILANCIA,
                    new Object[]{idVigilancia, idAlsc}, new RequerimientoAnteriorArcaMapper());
        } catch (EmptyResultDataAccessException e) {
            log.debug(e);
            return null;
        }
    }

    @Propagable
    @Override
    public Integer deleteReqsAnteriores(Long idVigilancia, Integer idAlsc) throws ARCADAOExcepcion {
        return template.update(DELETE, new Object[]{
            idVigilancia,
            idAlsc},
                new int[]{
            Types.INTEGER,
            Types.INTEGER
        });
    }

    @Propagable
    @Override
    public Integer consultarReqsAnterioresPorIdVigilancia(Long idVigilancia, Integer idAlsc, String fechaenvio) throws ARCADAOExcepcion {
        try {
            return (Integer) template.queryForObject(SELECT_REQS_ANTERIORES_X_ID_VIGILANCIA,
                    new Object[]{idVigilancia, idAlsc, fechaenvio}, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            log.debug(e);
            return null;
        }
    }

    @Override
    public Integer deleteReqsAnterioresPorIdVigilancia(Long idVigilancia, Integer idAlsc, String fechaMonitor) throws ARCADAOExcepcion {
        return template.update(DELETE_REQS_ANTERIORES_X_ID_VIGILANCIA, new Object[]{
            idVigilancia,
            idAlsc,
            fechaMonitor},
                new int[]{
            Types.INTEGER,
            Types.INTEGER,
            Types.VARCHAR
        });
    }
}
