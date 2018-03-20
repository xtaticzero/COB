package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl;

import java.sql.Types;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.BuscaRenuentesDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.ArchivoBuscaRenuentesMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.BuscaRenuentesMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.RenuentesMapper;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.BuscaRenuentesSQL.*;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.BuscaRenuentes;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.PeriodicidadHelper;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Renuentes;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import mx.gob.sat.siat.cob.seguimiento.util.Utilerias;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BuscaRenuentesDAOImpl implements BuscaRenuentesDAO {

    private static Logger logger = Logger.getLogger(BuscaRenuentesDAOImpl.class);
    @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG_NAMED_PARAMETER)
    private NamedParameterJdbcTemplate namedJDBCTemplate;
    private final static int CIEN = 100;

    @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG)
    private JdbcTemplate template;

    @Override
    @Propagable(catched = true)
    public List<Renuentes> buscaRenuentes(BuscaRenuentes buscaRenuentes) {
        List<Renuentes> listaRenuentes;
        StringBuilder select = new StringBuilder(BUSCA_RENUENTES);

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("listaTipoDocumentos", buscaRenuentes.getSelectedTipoDocumento());
        parameters.addValue("listaEdosDoc", buscaRenuentes.getEstadoDocumento());
        parameters.addValue("listaObligaciones", buscaRenuentes.getSelectedObligaciones());
        parameters.addValue("listaPeriodos", obtenerListaPeriodos(buscaRenuentes));
        select.append(obtenerFechaPeriodo(buscaRenuentes));
        listaRenuentes = this.namedJDBCTemplate.query(select.toString(), parameters, new RenuentesMapper());

        return listaRenuentes;
    }

    @Override
    public Long obtenerIdBusquedaRenuentes() throws SeguimientoDAOException {
        try {
            return template.queryForObject(OBTENER_ID, Long.class);
        } catch (EmptyResultDataAccessException e) {
            logger.debug(e);
            return null;
        }
    }

    @Override
    public Integer insertBuscaRenuentes(BuscaRenuentes buscaRenuentes) {
        return template.update(INSERT_RENUENTES,
                new Object[]{
                    buscaRenuentes.getIdBusquedaRenuente(),
                    Utilerias.separadoPorComas(buscaRenuentes.getSelectedTipoDocumento().toString()),
                    buscaRenuentes.getEstadoDocumento(),
                    buscaRenuentes.getEsFinalizada(),
                    Utilerias.separadoPorComas(buscaRenuentes.getSelectedObligaciones().toString()),
                    buscaRenuentes.getRutaArchivo(),
                    buscaRenuentes.getFechaBusquedaStr()
                }, new int[]{Types.NUMERIC,
                    Types.VARCHAR,
                    Types.NUMERIC,
                    Types.NUMERIC,
                    Types.VARCHAR,
                    Types.VARCHAR,
                    Types.VARCHAR});
    }

    @Override
    @Propagable
    public BuscaRenuentes obtenerArchivoBusquedaRenuente() {
        try {
            return template.queryForObject(BUSCA_ARCHIVO_POR_ID, new ArchivoBuscaRenuentesMapper());
        } catch (EmptyResultDataAccessException e) {
            logger.info(BUSCA_ARCHIVO_POR_ID);
            logger.error(e);
            return null;
        }
    }

    @Override
    @Propagable
    public List<BuscaRenuentes> registroSinFinalizar() throws SeguimientoDAOException {
        try {
            return template.query(REGISTROS_SIN_FINALIZAR, new BuscaRenuentesMapper());
        } catch (EmptyResultDataAccessException e) {
            logger.info(REGISTROS_SIN_FINALIZAR);
            logger.error(e);
            return null;
        }
    }

    private List<String> obtenerListaPeriodos(BuscaRenuentes buscaRenuentes) {
        List<String> listaPeriodos = new LinkedList<String>();
        for (PeriodicidadHelper periodo : buscaRenuentes.getSelectedPeriodicidadHelper()) {
            listaPeriodos.add(periodo.getPeriodicidad().getIdString());
        }
        return listaPeriodos;
    }

    private String obtenerFechaPeriodo(BuscaRenuentes buscaRenuentes) {
        StringBuilder stringBuilder = new StringBuilder();

        Iterator itr = buscaRenuentes.getSelectedPeriodicidadHelper().iterator();

        while (itr.hasNext()) {
            PeriodicidadHelper periodo = (PeriodicidadHelper) itr.next();

            String mesInicial = periodo.getPeriodoInicialSelected().trim().split("\\|")[0];
            String mesFinal = periodo.getPeriodoFinalSelected().trim().split("\\|")[0];
            String anioInicial = periodo.getEjercicioInicialSelected();
            String anioFinal = periodo.getEjercicioFinalSelected();

            Integer valorInicial = Integer.valueOf(anioInicial) * CIEN + Integer.valueOf(mesInicial);
            Integer valorFinal = Integer.valueOf(anioFinal) * CIEN + Integer.valueOf(mesFinal);

            stringBuilder.append("(detalle.IdPeriodicidad = '").
                    append(periodo.getPeriodicidad().getIdString()).
                    append("' AND(detalle.ejercicio*100+detalle.idPeriodo>=(").
                    append(valorInicial).
                    append(") AND (detalle.ejercicio*100+detalle.idPeriodo<=").
                    append(valorFinal).
                    append(")))");
            if (itr.hasNext()) {
                stringBuilder.append("OR");
            } else {
                stringBuilder.append(")");
            }
        }
        return stringBuilder.toString();
    }
}
