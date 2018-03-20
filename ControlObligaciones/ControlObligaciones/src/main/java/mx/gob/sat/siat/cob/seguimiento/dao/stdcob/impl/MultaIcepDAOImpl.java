/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl;

import java.sql.Types;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.MultaIcepDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.MultaIcepMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.ResolucionIcepSQL;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.ResolucionIcepSQL.ACTUALIZAR_MONTO_RESOLICEP;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Documento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.MultaIcep;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.IcepRenuenteEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.SituacionIcepEnum;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 *
 * @author Rodrigo
 */
@Repository
public class MultaIcepDAOImpl implements MultaIcepDAO, ResolucionIcepSQL {

    @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG)
    private JdbcTemplate template;
    @Autowired(required = false)
    @Qualifier("namedTemplateSTDCOB")
    private NamedParameterJdbcTemplate namedJDBCTemplate;

    @Override
    @Propagable(catched = true)
    public Integer insert(MultaIcep multaIcep) {
        return template.update(INSERT, new Object[]{multaIcep.getNumeroResolucion(),
            multaIcep.getNumeroControl(),
            multaIcep.getClaveIcep()},
                new int[]{Types.VARCHAR,
                    Types.VARCHAR,
                    Types.NUMERIC});
    }

    @Override
    @Propagable(catched = true)
    public List<MultaIcep> selectPorNumeroResolucion(String numeroResolucion) {
        return template.query(SELECT_POR_NUMERO_RESOLUCION, 
                new Object[]{numeroResolucion},
                new int[]{Types.VARCHAR},
                new MultaIcepMapper());
    }

    @Override
    @Propagable(catched = true)
    public Integer insertarIcepsPorMultasIncumplimiento(List<Documento> documentos, int esIcepRenuente) {
        int maxParametros = 1000;
        Integer rs = 0;
        int cont = 1;
        int ii = 0;
        List<String> documentosEnviar;
        MapSqlParameterSource parameters;
        do {
            documentosEnviar = new ArrayList<String>();
            for (; (ii < (maxParametros * cont)) && (ii < documentos.size()); ii++) {
                documentosEnviar.add(documentos.get(ii).getNumeroControl());
            }
            cont++;
            parameters = new MapSqlParameterSource();
            parameters.addValue("documentos", documentosEnviar);
            parameters.addValue("idSituacionIcep", SituacionIcepEnum.INCUMPLIDO.getValor());
            List<Integer> aplicaRenuentes = new ArrayList<Integer>();
            aplicaRenuentes.add(IcepRenuenteEnum.SI.getValor());
            if (esIcepRenuente == IcepRenuenteEnum.NO.getValor()){
            aplicaRenuentes.add(IcepRenuenteEnum.NO.getValor());
            }
            parameters.addValue("aplicaRenuentes", aplicaRenuentes);
            rs = namedJDBCTemplate.update(INSERTAR_ICEPS_POR_MULTAS_INCUMPLIMIENTO, parameters);
            if (rs == 0) {
                return null;
            }
        } while (ii < documentos.size());
        return rs;
    }
    
    
    @Override
    @Propagable(catched = true)
    public Integer insertarIcepsPorMultasComplementarias(List<Documento> documentos) {
            
            int maxParametros = 1000;
            Integer rs =0;
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
                rs = this.namedJDBCTemplate.update(INSERTA_ICEP_POR_MULTA_COMPLEMENTARIA, parameters);
                 if (rs ==0){
                     return rs;
                     }                
            } while (ii < documentos.size());
            return rs;
    }

    @Override
    @Propagable(catched = true)
    public void actualizarMontoResolucionIcep(Long monto, String numeroResolucion, String claveIcep) {
        
        template.update(ACTUALIZAR_MONTO_RESOLICEP, new Object[]{monto, numeroResolucion, claveIcep});
    }

    @Override
    @Propagable(catched = true)
    public void borrarMontosResolucionIcep(String numeroResolucion) {
        template.update(BORRAR_MONTOS_RESOLICEP, new Object[]{numeroResolucion});
    }
}
