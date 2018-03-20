package mx.gob.sat.siat.cob.seguimiento.dao.catalogos.impl;

import java.util.Calendar;
import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.MultaMontoDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.mapper.ComboMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.mapper.MultaMontoMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.mapper.OblisancionMotivacionMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.sql.MultaMontoSQL;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Combo;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.MultaMonto;
import mx.gob.sat.siat.cob.seguimiento.dto.compartidos.CatalogoBase;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import mx.gob.sat.siat.cob.seguimiento.util.Utilerias;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

/**
 *
 * @author root
 */
@Repository
public class MultaMontoDAOImpl implements MultaMontoDAO {

    private static Logger logger = Logger.getLogger(MultaMontoDAOImpl.class);
    @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG)
    private JdbcTemplate template;
    
    @Autowired(required = false)
    @Qualifier("namedTemplateSTDCOB")
    private NamedParameterJdbcTemplate namedJDBCTemplate;

    /**
     *
     * @return
     */
    @Override
    public List<MultaMonto> todosLasOblisanciones() {
        List<MultaMonto> listOblisancion;
        listOblisancion = template.query(MultaMontoSQL.OBTEN_TODAS_OBLISANCIONES, new MultaMontoMapper());
        if (listOblisancion == null || listOblisancion.isEmpty()) {
            logger.info(MultaMontoSQL.OBTEN_TODAS_OBLISANCIONES);
        }
        return listOblisancion;
    }

    /**
     *
     * @param oblisancion
     *
     */
    @Override
    @Propagable
    public void agregarOblisancion(MultaMonto oblisancion, boolean bol) {
         if (bol) {
            Long idMonto = buscarIdRepetida(oblisancion);
            guardarMultaMontoRepetida(idMonto);
        }
        int resultado = template.update(MultaMontoSQL.AGREGA_OBLISANCION, oblisancion.getSancion(), oblisancion.getInfraccion(),
                oblisancion.getFechaInicio(), oblisancion.getFechaFin(), oblisancion.getIdObligacion(),
                oblisancion.getOrden(), oblisancion.getResolMotivo(), oblisancion.getMotivacion() , oblisancion.getMonto(), oblisancion.getDescripcionMonto());
        if (resultado == -1) {
            logger.info(MultaMontoSQL.AGREGA_OBLISANCION);
        }
    }
    private void guardarMultaMontoRepetida(Long idMultaMonto) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("fechaFin", Utilerias.setFechaTrunk(Utilerias.getYesterday()));
        parameters.addValue("idMultaMonto", idMultaMonto);
        namedJDBCTemplate.update(MultaMontoSQL.ELIMINA_REPETIDA_MULTAS_MONTO, parameters);
    }

    private Long buscarIdRepetida(MultaMonto oblisancion) {
        Long reg = null;
        SqlRowSet srs = template.queryForRowSet(MultaMontoSQL.BUSCA_ID_MULTAS_MONTO_REPETIDA,
                oblisancion.getIdObligacion(), oblisancion.getResolMotivo());
        while (srs.next()) {
            reg = srs.getLong("ID");
        }
        return reg;
    }
    
    /**
     *
     * @param oblisancion
     *
     */
    @Override
    @Propagable
    public void editaOblisancion(MultaMonto oblisancion, boolean bol) {
        if (bol) {
            Long idMonto = buscarIdRepetida(oblisancion);
            guardarMultaMontoRepetida(idMonto);
        }
        Calendar cal = Calendar.getInstance();
        oblisancion.setFechaInicio(cal.getTime());
        int resultado = template.update(MultaMontoSQL.AGREGA_OBLISANCION, oblisancion.getSancion(), oblisancion.getInfraccion(),
                oblisancion.getFechaInicio(), null, oblisancion.getIdObligacion(),
                oblisancion.getOrden(), oblisancion.getResolMotivo(), oblisancion.getMotivacion() , oblisancion.getMonto(), oblisancion.getDescripcionMonto());
        if (resultado == -1) {
            logger.info(MultaMontoSQL.AGREGA_OBLISANCION);
        }
    }

    /**
     *
     * @param oblisancion
     * @return
     *
     */
    @Override
    @Propagable
    public Integer eliminaOblisancion(MultaMonto oblisancion) {
        Integer reg;
        reg = template.update(MultaMontoSQL.ELIMINA_OBLISANCION, oblisancion.getFechaFin(),
                oblisancion.getIdOblisancion());
        if (reg == -1) {
            logger.info(MultaMontoSQL.ELIMINA_OBLISANCION);
        }
        return reg;
    }


    /**
     *
     * @return
     */
    @Override
    public List<Combo> obtenerComboObligacion() {
        List<Combo> combo;
        combo = template.query(MultaMontoSQL.LISTA_COMBO_OBLIGACION, new ComboMapper());
        return combo;
    }

    /**
     *
     * @return
     */
    @Override
    @Propagable
    public List<CatalogoBase> getOblisancionMotivaciones() {
        List<CatalogoBase> listOblisancion;
        listOblisancion = (List<CatalogoBase>) template.query(MultaMontoSQL.BUSCA_OBLISANCION_MOTIVACION,
                new OblisancionMotivacionMapper());
        if (listOblisancion == null || listOblisancion.isEmpty()) {
            logger.info(MultaMontoSQL.BUSCA_OBLISANCION_MOTIVACION);
        }
        return listOblisancion;
    }

    @Override
    public Integer buscarMultaMontoRepetida(Long idObligacionSan, String idTipoMultaSel) {
         Integer reg = null;
        SqlRowSet srs = template.queryForRowSet(MultaMontoSQL.BUSCA_MULTAS_MONTO_REPETIDA, idObligacionSan, idTipoMultaSel);
        while (srs.next()) {
            reg = srs.getInt("REGISTROS");
        }
        return reg;
    }
}
