/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.BitacoraMultaDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.BitacoraMultaSQL;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.BitacoraMulta;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Documento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoMultaEnum;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Rodrigo
 */
@Repository
public class BitacoraMultaDAOImpl implements BitacoraMultaDAO, BitacoraMultaSQL {

    @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG_NAMED_PARAMETER)
    private NamedParameterJdbcTemplate namedJDBCTemplate;

    @Override
    @Propagable(catched = true)
    public Integer insert(BitacoraMulta bitacoraMulta) {

        MapSqlParameterSource parameters = new MapSqlParameterSource();

        parameters.addValue("estado", bitacoraMulta.getIdEstadoResolucion());
        parameters.addValue("fecha", bitacoraMulta.getFechaMovimiento());
        parameters.addValue("resolucion", bitacoraMulta.getNumeroResolucion());
        return this.namedJDBCTemplate.update(INSERT,
                parameters);
    }

    @Override
    @Propagable(catched = true)
    public Integer registraBitacoraMultaMasivo(List<Documento> documentos, EstadoMultaEnum estadoMulta) {
        final int maxParametros = 1000;
        Integer rs = 0;
        int cont = 1;
        int ii = 0;
        List<String> documentosEnviar;
        MapSqlParameterSource parameters;

        do {
            documentosEnviar = new ArrayList();
            for (; (ii < (maxParametros * cont)) && (ii < documentos.size()); ii++) {
                documentosEnviar.add(documentos.get(ii).getNumeroControl());
            }
            cont++;

            parameters = new MapSqlParameterSource();

            parameters.addValue("documentos", documentosEnviar);
            parameters.addValue("estadoresolucion", estadoMulta.getValor());
            parameters.addValue("fecha", new Date());

            rs = namedJDBCTemplate.update(REGISTRA_BITACORA_MULTA_MASIVO, parameters);
            if (rs == 0) {
                return null;
            }
        } while (ii < documentos.size());
        return rs;
    }

}
