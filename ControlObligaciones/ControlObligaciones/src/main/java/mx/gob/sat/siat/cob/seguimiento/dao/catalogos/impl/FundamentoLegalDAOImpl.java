package mx.gob.sat.siat.cob.seguimiento.dao.catalogos.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.FundamentoLegalDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.mapper.BusquedaFundamentoLegalMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.mapper.ComboMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.mapper.ComboMapperStr;
import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.mapper.FundamentoLegalMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.sql.FundamentoLegalSQL;
import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.sql.ObligacionesSQL;
import mx.gob.sat.siat.cob.seguimiento.dao.compartidos.mapper.CatalogoMapper;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Combo;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.FundamentoLegal;
import mx.gob.sat.siat.cob.seguimiento.dto.compartidos.CatalogoBase;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

/**
 *
 * @author root
 */
@Repository
public class FundamentoLegalDAOImpl implements FundamentoLegalDAO {

    private static Logger logger = Logger.getLogger(FundamentoLegalDAOImpl.class);
    
    @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG)
    private JdbcTemplate template;

    /**
     *
     * @return
     */
    @Override
    public List<FundamentoLegal> todosLosFundamentos() {
        List<FundamentoLegal> listFundamentoLegal;
        listFundamentoLegal = template.query(FundamentoLegalSQL.OBTEN_TODOS_FUNDAMENTOS, new FundamentoLegalMapper());
        if (listFundamentoLegal == null || listFundamentoLegal.isEmpty()) {
            logger.info(FundamentoLegalSQL.OBTEN_TODOS_FUNDAMENTOS);
        }
        return listFundamentoLegal;
    }
    
    /**
     *
     * @return
     */
    @Override
    public List<FundamentoLegal> todosLosFundamentosPorEjercicioFiscal(Long idEjercicio) {
        List<FundamentoLegal> listFundamentoLegal;
        listFundamentoLegal = template.query(FundamentoLegalSQL.OBTEN_TODOS_FUNDAMENTOS_EJERCICIO, 
                new Object[]{idEjercicio},
                new int[]{Types.INTEGER},
                new FundamentoLegalMapper());
        if (listFundamentoLegal == null || listFundamentoLegal.isEmpty()) {
            logger.info(FundamentoLegalSQL.OBTEN_TODOS_FUNDAMENTOS_EJERCICIO);
        }
        return listFundamentoLegal;
    }

    /**
     *
     * @param fundamento
     */
    @Override
    @Propagable
    public void agregarFundamentoLegal(FundamentoLegal fundamento) {
        int resultado = template.update(FundamentoLegalSQL.AGREGA_FUNDAMENTO, fundamento.getIdObligacion(),
                fundamento.getIdRegimen(), fundamento.getIdPeriodo(), fundamento.getIdPeriodicidad(),
                fundamento.getIdEjercicioFiscal(), fundamento.getFundamentoLegal(), fundamento.getFechaInicio(),
                fundamento.getFechaFin(), fundamento.getFechaVencimiento());
        if (resultado == -1) {
            logger.info(FundamentoLegalSQL.AGREGA_FUNDAMENTO);
        }

    }

    /**
     *
     * @param fundamento
     */
    @Override
    @Propagable
    public void editaFundamentoLegal(FundamentoLegal fundamento) {
        int resultado = template.update(FundamentoLegalSQL.EDITA_FUNDAMENTO, fundamento.getFundamentoLegal(), fundamento.getFechaVencimiento(), fundamento.getIdFundamentoLegal());
        if (resultado == -1) {
            logger.info(FundamentoLegalSQL.EDITA_FUNDAMENTO);
        }
    }

    /**
     *
     * @param fundamento
     * @return
     */
    @Override
    @Propagable
    public Integer eliminaFundamentoLegal(FundamentoLegal fundamento) {
        Integer reg;
        reg = template.update(FundamentoLegalSQL.ELIMINA_FUNDAMENTO, fundamento.getFechaFin(),
                fundamento.getIdFundamentoLegal());
        if (reg == -1) {
            logger.info(FundamentoLegalSQL.ELIMINA_FUNDAMENTO);
        }
        return reg;
    }

    /**
     *
     * @param fundamento
     * @return
     */
    @Override
    public FundamentoLegal buscaFundamentoLegalPorID(FundamentoLegal fundamento) {
        try {
            return template.queryForObject(FundamentoLegalSQL.BUSCA_FUNDAMENTO_POR_ID,
                    new Object[]{fundamento.getIdFundamentoLegal()}, new FundamentoLegalMapper());
        } catch (EmptyResultDataAccessException e) {
            logger.debug("No hay resultados: \n  " + FundamentoLegalSQL.BUSCA_FUNDAMENTO_POR_ID);
            return null;
        }
    }

    /**
     *
     * @param fundamento
     * @return
     */
    @Override
    public Integer buscarFundamentoLegalPorIdObligYIdReg(FundamentoLegal fundamento) {
        Integer reg = null;
        SqlRowSet srs = template.queryForRowSet(FundamentoLegalSQL.BUSCA_FUNDAMENTO_POR_IDFUNDAMENTO, fundamento.getIdObligacion(), 
                fundamento.getIdRegimen(), fundamento.getIdPeriodo(), fundamento.getIdPeriodicidad(),
                fundamento.getIdEjercicioFiscal());
        while (srs.next()) {
            reg = srs.getInt("REGISTROS");
        }

        return reg;
    }

    /**
     *
     * @return
     */
    @Override
    @Propagable
    public List<Combo> obtenerListRegimenPorIdObligacion() {
        List<Combo> obligacion;
        obligacion = template.query(ObligacionesSQL.LISTA_COMBO_REGIMEN,
                new ComboMapper());
        return obligacion;
    }

    /**
     *
     * @return
     */
    @Override
    public List<Combo> obtenerComboEjercicioFiscal() {
        List<Combo> combo;
        combo = template.query(FundamentoLegalSQL.LISTA_COMBO_EJERCICIO_FISCAL, new ComboMapper());
        return combo;
    }

    /**
     *
     * @return
     */
    @Override
    public List<Combo> obtenerComboPeriodicidad() {
        List<Combo> combo;
        combo = template.query(FundamentoLegalSQL.LISTA_COMBO_PERIODO, new ComboMapperStr());
        return combo;
    }

    /**
     *
     * @param fundamento
     * @return
     */
    @Override
    @Propagable
    public FundamentoLegal buscarFundamentoLegalParaExportacion(FundamentoLegal fundamento) {
        List<FundamentoLegal> fundamentoLegal = null;
        FundamentoLegal fnd = null;
        String[] arr = fundamento.getIdPeriodo().split("\\|");
        String id = arr[0];
        String[] aux = id.split("-");
        String[] arrEjFiscal = fundamento.getIdEjercicioFiscalDescr().split("-");
        StringBuilder sql = new StringBuilder();

        sql.append(FundamentoLegalSQL.BUSCAR_FUNDAMENTO_PARA_EXPORTAR_ARCHIVO
                .replace("{0}", aux[0])
                .replace("{1}", aux[1])
                .replace("{2}", arrEjFiscal[0]));
        logger.debug("querybuscarFundamentoLegalParaExportacion:" + sql.toString());
        fundamentoLegal = template.query(sql.toString(), new BusquedaFundamentoLegalMapper());

        if (fundamentoLegal != null && !fundamentoLegal.isEmpty()) {
            fnd = fundamentoLegal.get(0);
            fnd.setIdEjercicioFiscalDescr(arrEjFiscal[1]);
            fnd.setIdPeriodoDescr(arr[1]);
        }

        return fnd;
    }

    /**
     *
     * @return
     */
    @Override
    public List<Combo> obtenerComboObligacion() {
        List<Combo> combo;
        combo = template.query(FundamentoLegalSQL.LISTA_COMBO_OBLIGACION_FUNDAMENTO, new ComboMapper());
        return combo;
    }
    
    
    /**
     *
     * @param detalles
     * @return
     * @throws SeguimientoDAOException
     */
    @Override
    public Map<String,String> guardaFundamentoLegalBatch(final List<FundamentoLegal> detalles)
            throws SeguimientoDAOException {
        logger.debug(FundamentoLegalSQL.INSERTA_FUNDAMENTO_BATCH);
        String estatus="ok";
        Map<String,String> resultados = new HashMap<String,String>();
        resultados.put("exception", "");
        try{
            template.batchUpdate(FundamentoLegalSQL.INSERTA_FUNDAMENTO_BATCH, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    FundamentoLegal detalle = detalles.get(i);
                    ps.setInt(1, detalle.getIdObligacion().intValue());
                    ps.setInt(2, detalle.getIdRegimen().intValue());
                    ps.setInt(3, Integer.parseInt(detalle.getIdPeriodo()));
                    ps.setString(4, detalle.getIdPeriodicidad());
                    ps.setInt(5, detalle.getIdEjercicioFiscal().intValue());
                    ps.setString(6, detalle.getFundamentoLegal());
                    ps.setDate(7, detalle.getFechaVencimiento()==null?
                        null:new java.sql.Date(detalle.getFechaVencimiento().getTime()));
                }
                @Override
                public int getBatchSize() {
                    return detalles.size();
                }
            });
        }catch(DataAccessException ex){
            logger.error(ex);
            resultados.put("exception", ex.getCause().getMessage().trim());
            estatus="error";
        }
        resultados.put("estado", estatus);
        return resultados;
    }
    
    /**
     *
     * @return
     */
    @Override
    public List<CatalogoBase> getFundamentosExistentes() {
        List<CatalogoBase> combo;
        combo = template.query(FundamentoLegalSQL.FUNDAMENTOS_EXISTENTES, new CatalogoMapper());
        return combo;
    }
    

    /**
     *
     * @param template
     */
    public void setJdbcTemplate(JdbcTemplate template) {
        this.template = template;
    }
}