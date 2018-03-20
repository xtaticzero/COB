/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl;

import java.sql.Types;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.MultaDocumentoDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.ParametroSgtDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.MultaDocumentoMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.MultaDocumentoSQL;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Documento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.MultaDocumento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ParametrosSgtDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoMultaEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.ParametroSgtEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoMedioEnvioEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoMultaEnum;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import mx.gob.sat.siat.cob.seguimiento.util.Utilerias;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author root
 */
@Repository
public class MultaDocumentoDAOImpl implements MultaDocumentoDAO {

    private final Logger log = Logger.getLogger(MultaDocumentoDAOImpl.class);
    @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG)
    private JdbcTemplate template;
    @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG_NAMED_PARAMETER)
    private NamedParameterJdbcTemplate namedJDBCTemplate;
    @Autowired
    private ParametroSgtDAO parametroSgtDAO;

    /**
     *
     * @param multaDocumento
     * @return
     */
    @Override
    @Propagable(catched = true)
    public Integer insert(MultaDocumento multaDocumento) {
        Integer rs = 0;
        MapSqlParameterSource parameters;
        parameters = new MapSqlParameterSource();

        parameters.addValue("numeroResolucion", multaDocumento.getNumeroResolucion());
        parameters.addValue("numeroControl", multaDocumento.getNumeroControl());
        parameters.addValue("fecha", multaDocumento.getFechaRegistro());
        parameters.addValue("ultimoEstado", multaDocumento.getUltimoEstado());
        parameters.addValue("constanteResolucionMotivo", multaDocumento.getConstanteResolucionMotivo());
        parameters.addValue("rfc", multaDocumento.getRfc());

        rs = namedJDBCTemplate.update(MultaDocumentoSQL.INSERT, parameters);
        if (rs == 0) {
            return null;
        }
        return rs;
    }

    /**
     *
     * @param multaDocumento
     * @return
     */
    @Override
    @Propagable(catched = true)
    public Integer update(MultaDocumento multaDocumento) {
        return template.update(MultaDocumentoSQL.UPDATE,
                new Object[]{multaDocumento.getUltimoEstado(), multaDocumento.getNumeroResolucion()},
                new int[]{Types.NUMERIC, Types.VARCHAR});
    }

    /**
     *
     * @param multaDocumento
     * @return
     */
    @Override
    @Propagable(catched = true)
    public List<MultaDocumento> buscarMultasPorNumControlYTipo(MultaDocumento multaDocumento) {
        return template.query(MultaDocumentoSQL.CONSULTA_NUMCONTROL_TIPO,
                new Object[]{multaDocumento.getNumeroControl(), multaDocumento.getConstanteResolucionMotivo()},
                new int[]{Types.VARCHAR, Types.VARCHAR},
                new MultaDocumentoMapper());
    }

    /**
     *
     * @param numControl
     * @return
     */
    @Override
    @Propagable(catched = true)
    public List<MultaDocumento> buscarMultasPorNumControl(String numControl) {
        return template.query(MultaDocumentoSQL.CONSULTA_NUMCONTROL,
                new Object[]{numControl},
                new int[]{Types.VARCHAR},
                new MultaDocumentoMapper());
    }

    @Override
    @Propagable(catched = true)
    public Integer generarMultaMasivaSeguimiento(List<Documento> documentos, TipoMultaEnum tipoMultaEnum, TipoMedioEnvioEnum idTipoMedio) {
        final int maxParametros = 1000;
        Integer rs = 0;
        int cont = 1;
        int ii = 0;
        List<String> documentosEnviar;
        do {
            try {
                documentosEnviar = new ArrayList();
                for (; (ii < (maxParametros * cont)) && (ii < documentos.size()); ii++) {
                    documentosEnviar.add(documentos.get(ii).getNumeroControl());
                }
                cont++;
                rs = template.update(MultaDocumentoSQL.INSERTAR_MULTAS_MASIVAS.replace("#", Utilerias.formatearParaSQLIn(documentosEnviar.toString().replace(" ", ""))),
                        new Object[]{Utilerias.formatearFechaDDMMAAAAHHMM(Utilerias.formatearFechaDDMMYYYY(new Date())),
                    tipoMultaEnum.toString(),
                    idTipoMedio != null ? (idTipoMedio.getValor() == TipoMedioEnvioEnum.ENTIDAD_FEDERATIVA.getValor() ? 0 : 1) : 1,
                    null},
                        new int[]{Types.DATE, Types.VARCHAR, Types.NUMERIC, Types.NUMERIC});
                if (rs == 0) {
                    return null;
                }
            } catch (ParseException ex) {
                log.error("Error al obtener fecha para generacion de multa " + ex);
            } catch (Exception e) {
                log.error("no se pudo registrar lote de multas " + e);
            }
        } while (ii < documentos.size());
        return rs;
    }

    @Override
    @Propagable(catched = true)
    public Integer obtenerMontoLiquidacion(ParametroSgtEnum idParametro) {

        ParametrosSgtDTO parametro = parametroSgtDAO.obtenerParametroSgtPorIdParametro(idParametro.equals(ParametroSgtEnum.MONTO_PLANTILLA) ? ParametroSgtEnum.MONTO_PLANTILLA.getValor() : ParametroSgtEnum.MONTO_MINIMO.getValor());
        return Integer.valueOf(parametro.getValor());
    }

    @Override
    @Propagable(catched = true)
    public Integer generarResolucionLiquidacion(List<Documento> documentos, TipoMultaEnum tipoMultaEnum, TipoMedioEnvioEnum idTipoMedio, Integer dias) {
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
            parameters.addValue("fecha", Utilerias.setFechaTrunk(new Date()));
            parameters.addValue("tipoMulta", tipoMultaEnum.toString());
            parameters.addValue("idTipoMedio", idTipoMedio != null ? (idTipoMedio.getValor() == TipoMedioEnvioEnum.ENTIDAD_FEDERATIVA.getValor() ? 0 : 1) : 1);
            parameters.addValue("dias", dias);
            parameters.addValue("documentos", documentosEnviar);

            rs = namedJDBCTemplate.update(MultaDocumentoSQL.INSERTAR_RESOLUCION_LIQUIDACION, parameters);
            if (rs == 0) {
                return rs;
            }
        } while (ii < documentos.size());
        return rs;
    }

    @Override
    public void borrarMontoTotalMulta(String numeroResolucion) {
        template.update(MultaDocumentoSQL.BORRAR_MONTO_TOTAL_MULTA, new Object[]{numeroResolucion});
    }

    @Override
    public void actualizarUltimoEstadoMulta(String numeroResol, EstadoMultaEnum estado) {

        template.update(MultaDocumentoSQL.ACTUALIZAR_ULTIMOESTADO_MULTA,
                new Object[]{estado.getValor(), numeroResol});

    }
}
