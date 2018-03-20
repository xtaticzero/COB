package mx.gob.sat.siat.cob.seguimiento.dao.catalogos.impl;

import java.util.Date;
import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.MultaDescuentoDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.mapper.ComboMapperStr;
import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.mapper.MultaDescuentoMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.catalogos.sql.MultaDescuentoSQL;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.Combo;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.MultaDescuento;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import mx.gob.sat.siat.cob.seguimiento.util.Utilerias;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;


@Repository
public class MultaDescuentoDAOImpl implements MultaDescuentoDAO {

    @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG)
    private JdbcTemplate template;

    @Autowired(required = false)
    @Qualifier("namedTemplateSTDCOB")
    private NamedParameterJdbcTemplate namedJDBCTemplate;
   
    
    @Override
    @Propagable
    public List<MultaDescuento> todasLasMultaMonto() {
        return (List<MultaDescuento>) template.query(MultaDescuentoSQL.OBTEN_TODAS_MULTAS_MONTO, new MultaDescuentoMapper());
    }

    @Override
    @Propagable
    public void agregarMultaMonto(MultaDescuento multaMonto, Boolean existe) {
        if (existe) {
            Long idMonto = buscarIdRepetida(multaMonto);
            editaMultaMonto(idMonto);
        }
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("idResolMotivo", multaMonto.getIdResolMotivo());
        parameters.addValue("fechaInicio", Utilerias.setFechaTrunk(new Date()));
        parameters.addValue("fechaFin", multaMonto.getFechaFin());
        parameters.addValue("multaDes", multaMonto.getIdMultaDescuento());
        namedJDBCTemplate.update(MultaDescuentoSQL.AGREGA_MULTAS_MONTO, parameters);
    }

    private void editaMultaMonto(Long idMultaMonto) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("fechaFin", Utilerias.setFechaTrunk(Utilerias.getYesterday()));
        parameters.addValue("idMultaMonto", idMultaMonto);
        namedJDBCTemplate.update(MultaDescuentoSQL.UPDATE_MULTAS_MONTO, parameters);
    }

    private Long buscarIdRepetida(MultaDescuento multaMonto) {
        Long reg = null;
        SqlRowSet srs = template.queryForRowSet(MultaDescuentoSQL.BUSCA_ID_MONTO_REPETIDA,
                multaMonto.getIdResolMotivo());
        while (srs.next()) {
            reg = srs.getLong("ID");
        }
        return reg;
    }

    @Override
    public Integer buscarMultaMontoRepetida(MultaDescuento multaMonto) {
        Integer reg = null;
        SqlRowSet srs = template.queryForRowSet(MultaDescuentoSQL.BUSCA_MULTAS_MONTO_REPETIDA,
                multaMonto.getIdResolMotivo());
        while (srs.next()) {
            reg = srs.getInt("REGISTROS");
        }
        return reg;
    }

    @Override
    public List<Combo> obtenerComboMultaDescuento() {
        return template.query(MultaDescuentoSQL.LISTA_COMBO_MULTA_DESCUENTO, new ComboMapperStr());
    }

}
