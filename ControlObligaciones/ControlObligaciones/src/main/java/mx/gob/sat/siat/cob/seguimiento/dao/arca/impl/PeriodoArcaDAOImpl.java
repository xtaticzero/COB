/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.arca.impl;

import java.sql.Types;
import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dao.arca.PeriodoArcaDAO;
import static mx.gob.sat.siat.cob.seguimiento.dao.arca.sql.PeriodoSQL.SELECT_X_ID_VIGILANCIA;
import static mx.gob.sat.siat.cob.seguimiento.dao.arca.sql.PeriodoSQL.DELETE;
import static mx.gob.sat.siat.cob.seguimiento.dao.arca.sql.PeriodoSQL.INSERT;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.Periodo;
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
 * @author Juan
 */
@Repository("periodoArcaDAO")
public class PeriodoArcaDAOImpl implements PeriodoArcaDAO {

    private Logger log = Logger.getLogger(PeriodoArcaDAOImpl.class);
    private static final int TAMBLOCK = 100;
    @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_ARCA)
    private JdbcTemplate template;

    @Propagable(catched = true)
    @Override
    public Integer insert(List<Periodo> periodos) {
        Integer resultado;
        StringBuilder query = new StringBuilder(INSERT);
        StringBuilder queryItem = new StringBuilder(" SELECT ");
        int ii = 0;
        do {
            int jj = ii + TAMBLOCK;
            for (; ((ii < jj) && (ii < periodos.size())); ii++) {
                queryItem.append("\'").append(periodos.get(ii).getIdDocumento()).append("\'").append(",");
                queryItem.append("\'").append(periodos.get(ii).getDescripcionPeriodo()).append("\'").append(",");
                queryItem.append(periodos.get(ii).getEjercicio()).append(",");
                queryItem.append("\'").append(periodos.get(ii).getConceptoImpuesto()).append("\'");

                if (!((ii + 1 == jj) || (ii + 1 == periodos.size()))) {
                    queryItem.append("\n UNION ALL ");
                }
                query.append(queryItem);
                queryItem = new StringBuilder(" SELECT ");
            }
            log.info("### --> " + query.toString());
            resultado = template.update(query.toString());
            query = new StringBuilder(INSERT);
            queryItem = new StringBuilder(" SELECT ");
        } while (ii < periodos.size());

        return resultado;
    }

    @Propagable
    @Override
    public Integer consultarPeriodosPorIdVigilancia(Long idVigilancia, Integer idAlsc, String fechaRecepcion) throws ARCADAOExcepcion {
        try {
            return (Integer) template.queryForObject(SELECT_X_ID_VIGILANCIA,
                    new Object[]{idVigilancia, idAlsc, fechaRecepcion}, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            log.debug(e);
            return null;
        }
    }

    @Override
    public Integer deletePeriodosPorIdVigilancia(Long idVigilancia, Integer idAlsc, String fechaMonitor) throws ARCADAOExcepcion {
        return template.update(DELETE, new Object[]{
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
