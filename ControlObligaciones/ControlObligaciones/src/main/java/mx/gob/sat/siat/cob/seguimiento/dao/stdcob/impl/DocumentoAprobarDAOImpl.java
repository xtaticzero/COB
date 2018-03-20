/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl;

import java.sql.Types;
import java.util.List;
import java.util.Set;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.DocumentoAprobarDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.DocumentoAprobarIcepMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.DocumentoAprobarMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.FirmaDocumentoAprobarMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.DocumentoAprobarSQL;
import mx.gob.sat.siat.cob.seguimiento.dto.db2.Paginador;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.CadenaOriginal;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DocumentoAprobar;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoDocumentoEnum;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import mx.gob.sat.siat.cob.seguimiento.util.Utilerias;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author root
 */
@Repository
public class DocumentoAprobarDAOImpl implements DocumentoAprobarDAO, DocumentoAprobarSQL {

    @Autowired
    @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG)
    private JdbcTemplate template;

    @Override
    @Propagable(catched = true)
    public List<DocumentoAprobar> listarDocumentos(Paginador paginador, String numeroCarga, String idLocalidad, String filtroRFC) {
        if (filtroRFC == null || filtroRFC.equals("")) {
            if (idLocalidad == null) {
                return template.query(CONSULTAR_DOCUMENTOS_POR_VIGILANCIA_PAGINADOS.
                        replace(PARAMETRO_ADMON_LOCAL, "").replace(FILTRO_RFC, ""),
                        new Object[]{numeroCarga,
                    paginador.getRowInicial(),
                    paginador.getRowFinal()},
                        new int[]{Types.NUMERIC,
                    Types.NUMERIC,
                    Types.NUMERIC},
                        new DocumentoAprobarMapper());
            } else {
                return template.query(CONSULTAR_DOCUMENTOS_POR_VIGILANCIA_PAGINADOS.
                        replace(PARAMETRO_ADMON_LOCAL, ADMON_LOCAL).replace(FILTRO_RFC, ""),
                        new Object[]{numeroCarga,
                    idLocalidad,
                    paginador.getRowInicial(),
                    paginador.getRowFinal()},
                        new int[]{Types.NUMERIC,
                    Types.VARCHAR,
                    Types.NUMERIC,
                    Types.NUMERIC},
                        new DocumentoAprobarMapper());
            }

        } else {
            String filter;

            filter = "%" + filtroRFC + "%";

            if (idLocalidad == null) {
                return template.query(CONSULTAR_DOCUMENTOS_POR_VIGILANCIA_PAGINADOS.
                        replace(PARAMETRO_ADMON_LOCAL, "").replace(FILTRO_RFC, FILTRO),
                        new Object[]{numeroCarga,
                    filter,
                    paginador.getRowInicial(),
                    paginador.getRowFinal()},
                        new int[]{Types.NUMERIC,
                    Types.VARCHAR,
                    Types.NUMERIC,
                    Types.NUMERIC},
                        new DocumentoAprobarMapper());
            } else {
                return template.query(CONSULTAR_DOCUMENTOS_POR_VIGILANCIA_PAGINADOS.
                        replace(PARAMETRO_ADMON_LOCAL, ADMON_LOCAL).replace(FILTRO_RFC, FILTRO),
                        new Object[]{numeroCarga,
                    idLocalidad,
                    filter,
                    paginador.getRowInicial(),
                    paginador.getRowFinal()},
                        new int[]{Types.NUMERIC,
                    Types.VARCHAR,
                    Types.VARCHAR,
                    Types.NUMERIC,
                    Types.NUMERIC},
                        new DocumentoAprobarMapper());
            }

        }
    }
    
    /**
     * obtiene los documentos para el proceso de firmado digital en en el navegador
     * @param paginador
     * @param vigilancia
     * @param idLocalidad
     * @param nombre 
     * @return 
     */
    @Override
    @Propagable(catched = true)
    public List<CadenaOriginal> listarDocumentosFirmar(int rowInicial, int rowFinal, 
    String vigilancia, String idLocalidad, String nombre) {
        if (idLocalidad == null) {
            return template.query(CONSULTAR_DOCUMENTOS_FIRMAR_PAGINADOS.replace(PARAMETRO_ADMON_LOCAL, ""),
                new Object[]{
            vigilancia,
            rowInicial,
            rowFinal},
                new int[]{
            Types.NUMERIC,
            Types.NUMERIC,
            Types.NUMERIC},
                new FirmaDocumentoAprobarMapper(nombre));
        }else{
            return template.query(CONSULTAR_DOCUMENTOS_FIRMAR_PAGINADOS.replace(PARAMETRO_ADMON_LOCAL, ADMON_LOCAL),
                    new Object[]{
                vigilancia,
                idLocalidad,
                rowInicial,
                rowFinal},
                    new int[]{
                Types.NUMERIC,
                Types.VARCHAR,
                Types.NUMERIC,
                Types.NUMERIC},
                    new FirmaDocumentoAprobarMapper(nombre));
        }
    }
    
    /**
     *
     * @param numeroCarga
     * @param idLocalidad
     * @return
     */
    @Override
    @Propagable(catched = true)
    public Long contarRegistrosFirma(String numeroCarga, String idLocalidad) {
        if (idLocalidad == null) {
            return template.queryForObject(CONTAR_REGISTROS_FIRMA.replace(PARAMETRO_ADMON_LOCAL, ""),
                    new Object[]{numeroCarga},
                    new int[]{Types.NUMERIC}, Long.class);
        }else{
            return template.queryForObject(CONTAR_REGISTROS_FIRMA.replace(PARAMETRO_ADMON_LOCAL, ADMON_LOCAL),
                    new Object[]{numeroCarga,
                idLocalidad},
                    new int[]{Types.NUMERIC, Types.VARCHAR}, Long.class);
        }
    }

    @Override
    @Propagable(catched = true)
    public Long contarRegistros(String numeroCarga, String idLocalidad) {
        if (idLocalidad == null) {
            return template.queryForObject(CONTAR_REGISTROS.replace(PARAMETRO_ADMON_LOCAL, ""),
                    new Object[]{numeroCarga},
                    new int[]{Types.NUMERIC}, Long.class);
        } else {
            return template.queryForObject(CONTAR_REGISTROS.replace(PARAMETRO_ADMON_LOCAL, ADMON_LOCAL),
                    new Object[]{numeroCarga,
                idLocalidad},
                    new int[]{Types.NUMERIC, Types.VARCHAR}, Long.class);
        }
    }

    @Override
    @Propagable(catched = true)
    public Long contarRegistros(String numeroCarga, String idLocalidad, String filtroRFC) {
        String filtro = "%" + filtroRFC + "%";
        if (idLocalidad == null) {
            return template.queryForObject(CONTAR_REGISTROS_FILTRO.replace(PARAMETRO_ADMON_LOCAL, ""),
                    new Object[]{numeroCarga, filtro},
                    new int[]{Types.NUMERIC, Types.VARCHAR}, Long.class);
        } else {
            return template.queryForObject(CONTAR_REGISTROS_FILTRO.replace(PARAMETRO_ADMON_LOCAL, ADMON_LOCAL),
                    new Object[]{numeroCarga,
                idLocalidad, filtro},
                    new int[]{Types.NUMERIC, Types.VARCHAR, Types.VARCHAR}, Long.class);
        }
    }

    @Override
    @Propagable(catched = false)
    public List<DocumentoAprobar> listarDocumentosIcep(Paginador paginador, String numeroCarga, String idLocalidad) {
        List<List<DocumentoAprobar>> lst;
        if (idLocalidad == null) {
            lst = (List<List<DocumentoAprobar>>) template.query(DOCUMENTOS_CON_ICEP_POR_VIGILANCIA,
                    new Object[]{numeroCarga,
                paginador.getRowInicial(),
                paginador.getRowFinal()},
                    new int[]{Types.NUMERIC,
                Types.NUMERIC,
                Types.NUMERIC},
                    new DocumentoAprobarIcepMapper());
        } else {
            lst = (List<List<DocumentoAprobar>>) template.query(DOCUMENTOS_CON_ICEP_POR_LOCALIDAD,
                    new Object[]{numeroCarga,
                idLocalidad,
                paginador.getRowInicial(),
                paginador.getRowFinal()},
                    new int[]{Types.NUMERIC,
                Types.NUMERIC,
                Types.NUMERIC,
                Types.NUMERIC},
                    new DocumentoAprobarIcepMapper());
        }
        if (lst != null && lst.size() > 0) {
            return (List<DocumentoAprobar>) lst.get(0);
        } else {
            return null;
        }
    }

    @Override
    @Propagable(catched = true)
    public Long contarRegistrosIcep(String numeroCarga, String idLocalidad) {
        if (idLocalidad == null) {
            return template.queryForObject(CONTAR_POR_VIGILANCIA,
                    new Object[]{numeroCarga},
                    new int[]{Types.NUMERIC}, Long.class);
        } else {
            return template.queryForObject(CONTAR_POR_LOCALIDAD,
                    new Object[]{numeroCarga,
                idLocalidad},
                    new int[]{Types.NUMERIC, Types.VARCHAR}, Long.class);
        }
    }

    @Override
    @Propagable(catched = true)
    public Integer actualizarDocumentos(String numeroCarga, String idLocalidad, EstadoDocumentoEnum estado) {
        if (idLocalidad == null) {
            return template.update(RECHAZAR_DOCUMENTOS_VIGILANCIA,
                    new Object[]{
                estado.getValor(),
                numeroCarga},
                    new int[]{Types.NUMERIC, Types.NUMERIC});
        } else {
            return template.update(RECHAZAR_DOCUMENTOS_LOCALIDAD,
                    new Object[]{
                estado.getValor(),
                numeroCarga,
                idLocalidad},
                    new int[]{Types.NUMERIC, Types.NUMERIC, Types.VARCHAR});
        }
    }

    @Override
    @Propagable(catched = true)
    public Integer bitacoraDocumentos(String numeroCarga, String idLocalidad, EstadoDocumentoEnum estado) {
        if (idLocalidad == null) {
            return template.update(BITACORA_RECHAZO_VIGILANCIA,
                    new Object[]{estado.getValor(), numeroCarga},
                    new int[]{Types.NUMERIC, Types.NUMERIC});
        } else {
            return template.update(BITACORA_RECHAZO_LOCALIDAD,
                    new Object[]{estado.getValor(),
                numeroCarga,
                idLocalidad},
                    new int[]{Types.NUMERIC, Types.NUMERIC, Types.VARCHAR});
        }
    }

    @Override
    @Propagable(catched = true)
    public Integer rechazarDocumentosPorNumeroControl(Set<String> numerosControl) {
        String query = CAMBIAR_ESTADO_DOCUMENTO_POR_NUMERO_CONTROL.
                replace(CAMPO_NUEVO_ESTADO, EstadoDocumentoEnum.NO_PROCESADO.getValor() + "").
                replace(NUMEROS_CONTROL, Utilerias.inSQLMasDeMil(numerosControl, "numerocontrol"));
        return template.update(query);
    }

    @Override
    @Propagable(catched = true)
    public Integer bitacoraRechazarDocumentosPorNumeroControl(Set<String> numerosControl) {
        StringBuilder builder = new StringBuilder();
        builder.append(INSERT_ALL).append("\n");
        for (String numeroControl : numerosControl) {
            String into = INTO_BITACORA_DOCUMENTO.
                    replace(CAMPO_NUMERO_CONTROL, numeroControl).
                    replace(CAMPO_ESTADO_DOCTO,
                    EstadoDocumentoEnum.NO_PROCESADO.getValor() + "");
            builder.append(into).append("\n");
        }
        builder.append(DUAL);
        return template.update(builder.toString());
    }

    @Override
    @Propagable(catched = true)
    public Integer actualizarEstatusDocumento(String numeroControl, EstadoDocumentoEnum estadoNuevo) {
        template.update(ACTUALIZAR_ESTATUS_DOCUMENTO_VIGILANCIA,
                new Object[]{estadoNuevo.getValor(), numeroControl},
                new int[]{Types.NUMERIC, Types.VARCHAR});
        return template.update(INSERT_BITACORA_DOCUMENTO,
                new Object[]{numeroControl, estadoNuevo.getValor()},
                new int[]{Types.VARCHAR, Types.NUMERIC});
    }
}
