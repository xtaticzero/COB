package mx.gob.sat.siat.cob.seguimiento.dao.arca.impl;

import java.sql.Types;
import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dao.arca.ObligacionArcaDAO;
import static mx.gob.sat.siat.cob.seguimiento.dao.arca.sql.ObligacionSQL.SELECT_X_ID_VIGILANCIA;
import static mx.gob.sat.siat.cob.seguimiento.dao.arca.sql.ObligacionSQL.DELETE;
import static mx.gob.sat.siat.cob.seguimiento.dao.arca.sql.ObligacionSQL.INSERT;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.Obligacion;
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
@Repository
public class ObligacionArcaDAOImpl implements ObligacionArcaDAO {

    private static final int TAMBLOCK = 100;
    private Logger log = Logger.getLogger(ObligacionArcaDAOImpl.class);
    @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_ARCA)
    private JdbcTemplate template;

    @Propagable(catched = true)
    @Override
    public Integer insert(List<Obligacion> obligaciones) {
        Integer resultado;
        String query = INSERT;
        String queryItem = " SELECT ";
        String comilla = "\'";
        String comillaComa = "\',";
        int ii = 0;
        do {
            int jj = ii + TAMBLOCK;
            for (; ((ii < jj) && (ii < obligaciones.size())); ii++) {
                queryItem += comilla + obligaciones.get(ii).getIdDocumento() + comillaComa;
                queryItem += comilla + obligaciones.get(ii).getDescripcionDeObligacion() + comillaComa;
                queryItem += comilla + obligaciones.get(ii).getDescripcionDePeriodo() + comillaComa;
                queryItem += comilla + obligaciones.get(ii).getEjercicio() + comillaComa;
                queryItem += comilla + obligaciones.get(ii).getFundamentoLegal() + comillaComa;
                queryItem += comilla + obligaciones.get(ii).getFechaDeVencimiento() + comillaComa;
                queryItem += comilla + obligaciones.get(ii).getSancion() + comillaComa;
                queryItem += comilla + obligaciones.get(ii).getInfraccion() + comillaComa;
                queryItem += obligaciones.get(ii).getImporte();

                if (!((ii + 1 == jj) || (ii + 1 == obligaciones.size()))) {
                    queryItem += "\n UNION ALL ";
                }
                query += queryItem;
                queryItem = " SELECT ";
            }
            log.info("### QUERY -->" + query);
            resultado = template.update(query);
            query = "INSERT ALL \n";
            queryItem = INSERT;
        } while (ii < obligaciones.size());
        return resultado;
    }

    @Propagable
    @Override
    public Integer consultarObligacionesPorIdVigilancia(Long idVigilancia, Integer idAlsc, String fechaRecepcion) throws ARCADAOExcepcion {
        try {
            return (Integer) template.queryForObject(SELECT_X_ID_VIGILANCIA,
                    new Object[]{idVigilancia, idAlsc, fechaRecepcion}, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            log.debug(e);
            return null;
        }
    }

    @Propagable
    @Override
    public Integer deleteObligacionesPorIdVigilancia(Long idVigilancia, Integer idAlsc, String fechaMonitor) throws ARCADAOExcepcion {
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
