/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.arca.impl;

import java.sql.Types;
import java.util.List;
import java.util.Map;

import mx.gob.sat.siat.cob.seguimiento.dao.arca.ReqOrigenMultaDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.arca.mapper.OrigenMultaMapper;
import static mx.gob.sat.siat.cob.seguimiento.dao.arca.sql.DoctoEnviaArcaSQL.SELECT_ORIGEN_MULTA;
import static mx.gob.sat.siat.cob.seguimiento.dao.arca.sql.DoctoEnviaArcaSQL.SELECT_ORIGEN_MULTA_ANTECESOR;
import static mx.gob.sat.siat.cob.seguimiento.dao.arca.sql.DocumentoARCASQL.DELETE_T_ORIGEN_MULTA_X_ID;
import static mx.gob.sat.siat.cob.seguimiento.dao.arca.sql.ReqOrigenMultaSQL.SELECT_X_ID_VIGILANCIA;
import static mx.gob.sat.siat.cob.seguimiento.dao.arca.sql.ReqOrigenMultaSQL.DELETE;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.OrigenMulta;
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
 * @author root
 */
@Repository("origenMultaDAO")
public class OrigenMultaDAOImpl implements ReqOrigenMultaDAO {

    private Logger log = Logger.getLogger(OrigenMultaDAOImpl.class);
    @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_ARCA)
    private JdbcTemplate template;

    @Propagable(catched = true)
    @Override
    public OrigenMulta origenMultaArca(String numControl, String idDocumento) {
        OrigenMulta reqsAnteriores = new OrigenMulta();
        List<Map<String, Object>> lista = this.template.queryForList(SELECT_ORIGEN_MULTA_ANTECESOR,
                new Object[]{numControl});
        reqsAnteriores.setIdDocumento(idDocumento);
        int i = 0;
        for (Map m : lista) {
            if (i == 0) {
                reqsAnteriores.setNumControlOrigen((String) m.get("NumeroControl"));
                reqsAnteriores.setFechaNotificacionOrigen((String) m.get("FechaNotificacion"));
            } else if (i == 1) {
                reqsAnteriores.setNumControlPrimero((String) m.get("NumeroControl"));
                reqsAnteriores.setFechaNotificacionPrimero((String) m.get("FechaNotificacion"));
            } else if (i == 2) {
                reqsAnteriores.setNumControlSegundo((String) m.get("NumeroControl"));
                reqsAnteriores.setFechaNotificacionSegundo((String) m.get("FechaNotificacion"));
            }
            i++;
        }
        return reqsAnteriores;
    }

    @Propagable(catched = true)
    @Override
    public List<OrigenMulta> origenMultaArca() {
        return (List<OrigenMulta>) this.template.query(SELECT_ORIGEN_MULTA, new OrigenMultaMapper());
    }

    @Propagable(catched = true)
    @Override
    public Integer eliminarOrigenMulta(String id) {
        return template.update(DELETE_T_ORIGEN_MULTA_X_ID, new Object[]{id});
    }

    @Propagable
    @Override
    public Integer consultarOrigenesMultaPorIdVigilancia(Long idVigilancia, Integer idAlsc, String fechaRecepcion) throws ARCADAOExcepcion {
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
    public Integer deleteOrigenesMultaPorIdVigilancia(Long idVigilancia, Integer idAlsc, String fechaMonitor) throws ARCADAOExcepcion {
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
