package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.DetalleDocumentoDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.DetalleDocumentoMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.DetalleDocumentoSQL;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.DetalleDocumentoSQL.COUNT_IDSITUACION_ICEP;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DetalleDocumento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Documento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.SituacionIcepEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoMultaEnum;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

@Repository
public class DetalleDocumentoDAOImpl implements DetalleDocumentoDAO {

    private final Logger log = Logger.getLogger(DetalleDocumentoDAOImpl.class);

    @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG)
    private JdbcTemplate template;

    /**
     *
     * @param detalle
     * @return
     */
    @Override
    @Propagable
    public int guardaDetalleDocumento(DetalleDocumento detalle) {
        log.debug("Guardando detalle ...");
        log.debug(DetalleDocumentoSQL.INSERT);
        log.debug(detalle);
        int respuesta = template.update(DetalleDocumentoSQL.INSERT, new Object[]{
            detalle.getNumeroControl(),
            detalle.getClaveIcep(),
            detalle.getIdObligacion(),
            detalle.getIdEjercicio(),
            detalle.getIdPeriodo(),
            detalle.getFechaVencimiento(),
            detalle.getFechaCumplimiento(),
            detalle.getIdPeriodicidad(),
            detalle.getIdSituacionIcep(),
            detalle.getIdRegimen(),
            detalle.getImporteCargo(),
            detalle.getIdTipoDeclaracion(),
            detalle.getTieneMultaExtemporaneidad(),
            detalle.getTieneMultaComplementaria()},
                new int[]{
                    Types.VARCHAR,
                    Types.DECIMAL,
                    Types.VARCHAR,
                    Types.DECIMAL,
                    Types.DECIMAL,
                    Types.VARCHAR,
                    Types.DATE,
                    Types.CHAR,
                    Types.DECIMAL,
                    Types.DECIMAL,
                    Types.DECIMAL,
                    Types.DECIMAL,
                    Types.DECIMAL,
                    Types.DECIMAL
                });
        return respuesta;
    }

    @Override
    public Map<String, String> guardaDetalleDocumentoBatch(final List<DetalleDocumento> detalles)
            throws SeguimientoDAOException {
        log.debug(DetalleDocumentoSQL.INSERT);
        String estatus = "ok";
        Map<String, String> resultados = new HashMap<String, String>();
        resultados.put("exception", "");
        try {
            template.batchUpdate(DetalleDocumentoSQL.INSERT, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    DetalleDocumento detalle = detalles.get(i);
                    ps.setString(ConstantsCatalogos.UNO, detalle.getNumeroControl());
                    ps.setLong(ConstantsCatalogos.DOS, detalle.getClaveIcep());
                    ps.setLong(ConstantsCatalogos.TRES, detalle.getIdObligacion());
                    ps.setInt(ConstantsCatalogos.CUATRO, detalle.getIdEjercicio());
                    ps.setInt(ConstantsCatalogos.CINCO, detalle.getIdPeriodo());
                    ps.setString(ConstantsCatalogos.SEIS, detalle.getFechaVencimiento());
                    ps.setDate(ConstantsCatalogos.SIETE, detalle.getFechaCumplimiento() == null
                            ? null : new java.sql.Date(detalle.getFechaCumplimiento().getTime()));
                    ps.setString(ConstantsCatalogos.OCHO, detalle.getIdPeriodicidad());
                    ps.setInt(ConstantsCatalogos.NUEVE, detalle.getIdSituacionIcep());
                    ps.setInt(ConstantsCatalogos.DIEZ, detalle.getIdRegimen());
                    if (detalle.getImporteCargo() != null) {
                        ps.setDouble(ConstantsCatalogos.ONCE, detalle.getImporteCargo());
                    } else {
                        ps.setObject(ConstantsCatalogos.ONCE, null);
                    }
                    if (detalle.getIdTipoDeclaracion() != null) {
                        ps.setInt(ConstantsCatalogos.DOCE, detalle.getIdTipoDeclaracion());
                    } else {
                        ps.setObject(ConstantsCatalogos.DOCE, null);
                    }
                    ps.setInt(ConstantsCatalogos.TRECE, detalle.getTieneMultaExtemporaneidad());
                    ps.setInt(ConstantsCatalogos.CATORCE, detalle.getTieneMultaComplementaria());
                }

                @Override
                public int getBatchSize() {
                    return detalles.size();
                }
            });
        } catch (DataAccessException ex) {
            log.error(ex.getCause().getMessage());
            resultados.put("exception", ex.getCause().getMessage().trim());
            estatus = "error";
        }
        resultados.put("estado", estatus);
        return resultados;
    }

    @Override
    @Propagable(catched = true)
    public List<DetalleDocumento> consultaXNumControl(String numControl) {
        log.debug("-->numControl:" + numControl);
        return (List<DetalleDocumento>) this.template.query(
                DetalleDocumentoSQL.CONSULTA_NUMCONTROL,
                new Object[]{numControl},
                new int[]{Types.VARCHAR},
                new DetalleDocumentoMapper());
    }

    @Override
    @Propagable(catched = true)
    public List<DetalleDocumento> consultaXNumControl(String numControl, SituacionIcepEnum situacionIcep) {
        log.debug("-->numControl:" + numControl);
        StringBuilder sQuery = new StringBuilder(DetalleDocumentoSQL.CONSULTA_NUMCONTROL);
        sQuery.append(" AND IDSITUACIONICEP = ?");
        return (List<DetalleDocumento>) this.template.query(
                sQuery.toString(),
                new Object[]{numControl, situacionIcep.getValor()},
                new int[]{Types.VARCHAR, Types.NUMERIC},
                new DetalleDocumentoMapper());
    }

    @Override
    @Propagable
    public void cambiarEstatusICEP(DetalleDocumento detalleDocumento, SituacionIcepEnum situacion) {
        template.update(DetalleDocumentoSQL.ACTUALIZAR_SOLVENTADO,
                new Object[]{situacion.getValor(),
                    detalleDocumento.getNumeroControl(),
                    detalleDocumento.getClaveIcep()},
                new int[]{Types.NUMERIC, Types.VARCHAR, Types.VARCHAR});
    }

    @Override
    @Propagable
    public void cambiarEstadoICEPsPorNumControl(Documento documento,
            SituacionIcepEnum valorActual,SituacionIcepEnum valorNuevo) {
        template.update(DetalleDocumentoSQL.ACTUALIZAR_SOLVENTADO_ICEPS,
                new Object[]{valorNuevo.getValor(),
                    documento.getNumeroControl(),
                    valorActual.getValor(),},
                new int[]{Types.NUMERIC, Types.VARCHAR, Types.NUMERIC,});
    }

    @Override
    @Propagable(catched = true)
    public Integer solventarPorMovimientoAlPadron(String numeroControl) {
        return template.update(DetalleDocumentoSQL.ACTUALIZAR_POR_MOVIENTO,
                new Object[]{SituacionIcepEnum.CANCELADO_POR_MOVIMIENTOS_PADRON.getValor(),
                    new Date(),
                    numeroControl},
                new int[]{Types.NUMERIC, Types.DATE, Types.VARCHAR});
    }

    @Override
    @Propagable(catched = true)
    public Integer solventarPorMovimientoAlPadron(Documento documento) {
        int counter = 0;
        if (!documento.getDetalles().isEmpty()) {
            for (DetalleDocumento detalleDocumento : documento.getDetalles()) {
                if (detalleDocumento.getSituacionIcep().getValor() == SituacionIcepEnum.INCUMPLIDO.getValor()
                        || detalleDocumento.getSituacionIcep().getValor() == SituacionIcepEnum.CUMPLIDO.getValor()) {
                    counter += template.update(DetalleDocumentoSQL.ACTUALIZAR_POR_MOVIENTO_ICEPS,
                            new Object[]{SituacionIcepEnum.CANCELADO_POR_MOVIMIENTOS_PADRON.getValor(),
                                detalleDocumento.getFechaMantenimiento(),
                                documento.getNumeroControl(), detalleDocumento.getClaveIcep()},
                            new int[]{Types.NUMERIC, Types.DATE, Types.VARCHAR, Types.NUMERIC});
                }
            }
        }
        log.debug(counter + " Rows Affected!");
        return counter;
    }

    @Override
    @Propagable
    public void marcarConMulta(DetalleDocumento detalleDocumento, TipoMultaEnum tipoMulta) {
        if (TipoMultaEnum.EXTEMPORANEIDAD.equals(tipoMulta)
                || TipoMultaEnum.INCUMPLIMIENTO_EXTEMPORANEIDAD.equals(tipoMulta)) {
            template.update(DetalleDocumentoSQL.MARCAR_MULTA_EXTEMPORANEIDAD,
                    new Object[]{detalleDocumento.getNumeroControl(),
                        detalleDocumento.getClaveIcep()},
                    new int[]{Types.VARCHAR, Types.VARCHAR});
        }
        if (TipoMultaEnum.COMPLEMENTARIA.equals(tipoMulta)) {
            template.update(DetalleDocumentoSQL.MARCAR_MULTA_COMPLEMENTARIA,
                    new Object[]{detalleDocumento.getNumeroControl(),
                        detalleDocumento.getClaveIcep()},
                    new int[]{Types.VARCHAR, Types.VARCHAR});
        }
    }

    @Override
    public Integer getNumeroVigilables(String numeroControl) {
        Integer reg = null;
        SqlRowSet srs;

        srs = template.queryForRowSet(COUNT_IDSITUACION_ICEP, numeroControl);

        while (srs.next()) {
            reg = srs.getInt("REGISTROS");
        }

        return reg;
    }
}
