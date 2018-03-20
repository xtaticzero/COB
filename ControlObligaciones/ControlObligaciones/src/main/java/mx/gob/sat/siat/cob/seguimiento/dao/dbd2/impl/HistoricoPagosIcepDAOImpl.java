package mx.gob.sat.siat.cob.seguimiento.dao.dbd2.impl;

import java.math.BigDecimal;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.gob.sat.siat.cob.seguimiento.dao.dbd2.HistoricoPagosIcepDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.dbd2.mapper.HistoricoPagosICEPsOmisosMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.dbd2.sql.HistoricoCumplimientoSQL;
import mx.gob.sat.siat.cob.seguimiento.dto.db2.PagoIcepOmiso;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class HistoricoPagosIcepDAOImpl implements HistoricoPagosIcepDAO {

    private final Logger log = Logger.getLogger(HistoricoPagosIcepDAOImpl.class);
    
    @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_DB2)
    private JdbcTemplate templateDB2;

    @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG)
    private JdbcTemplate templateCOB;

    /**
     *
     * @param boid
     * @param claveIcep
     * @return
     */
    @Override
    @Propagable(catched = true)
    public List<PagoIcepOmiso> consultarHistoricoPagosICEPsOmisos(BigDecimal boid, int claveIcep) {
        return templateDB2.query(HistoricoCumplimientoSQL.CONSULTAR_PAGOS_ICEPS,
                new Object[]{boid, claveIcep},
                new HistoricoPagosICEPsOmisosMapper());
    }

    @Override
    public Map<String,String> guardarHistoricoPagosLiquidacion(final List<PagoIcepOmiso> pagos) throws SeguimientoDAOException {

        String totales = "ok";
        Map<String, String> resultados = new HashMap<String, String>();
        resultados.put("exception", "");
        try {
            templateCOB.batchUpdate(HistoricoCumplimientoSQL.GUARDAR_HISTORICO_PAGOS_LIQ, new BatchPreparedStatementSetter() {

                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                            PagoIcepOmiso pago = pagos.get(i);
                            ps.setString(ConstantsCatalogos.UNO, pago.getNumeroControl());
                            ps.setLong(ConstantsCatalogos.DOS, pago.getClaveIcep());
                            ps.setLong(ConstantsCatalogos.TRES, pago.getIdentificadorCumplimiento());
                            ps.setLong(ConstantsCatalogos.CUATRO, pago.getImporteaCargo());
                            ps.setDate(ConstantsCatalogos.CINCO, (pago.getFechaPresentacion() == null ? null : new java.sql.Date(pago.getFechaPresentacion().getTime())));
                            ps.setInt(ConstantsCatalogos.SEIS, pago.getTipoDeclaracion());
                            ps.setLong(ConstantsCatalogos.SIETE, pago.getNumOperacion());
                            ps.setInt(ConstantsCatalogos.OCHO, pago.getEsCantidadDetMayor());
                            ps.setLong(ConstantsCatalogos.NUEVE, pago.getClaveIcepHistPago());
                            ps.setInt(ConstantsCatalogos.DIEZ,pago.getEjercicio());
                            ps.setInt(ConstantsCatalogos.ONCE,pago.getPeriodo());
                            ps.setString(ConstantsCatalogos.DOCE,pago.getPeriodicidad());
                            log.debug("registro numero :  " + i);
                        }
                    @Override
                    public int getBatchSize() {
                        log.debug("total de registros :  " + pagos.size());
                        return pagos.size();
                    }
                });

        } catch (DataAccessException ex) {
            log.error(ex.getCause().getMessage());
            totales = "error";
            throw new SeguimientoDAOException(ex);
        }
        resultados.put("estado", totales);
        return resultados;
    }
}
