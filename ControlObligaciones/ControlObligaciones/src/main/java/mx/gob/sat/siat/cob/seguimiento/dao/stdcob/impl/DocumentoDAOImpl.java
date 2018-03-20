package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.DocumentoDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.ParametroSgtDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.BitacoraSeguimientoMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.ConsultarDocumentosActualizadosMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.ConsultarICEPsOmisosMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.DetalleDocumentoMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.DocumentoMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.DocumentoPadronMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.EstadoDocumentoMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.MultaPendienteMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.ResultadoDiligenciaMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.TipoDocumentoFullMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.DocumentoSQL;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.DocumentoSQL.ACTUALIZAR_DOCUMENTOS_ESULTIMOGENERADO;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.DocumentoSQL.CONSULTAR_ICEPS_X_LOTE;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.DocumentoSQL.CONSULTA_DOCUMENTOS_ACTUALIZADOS_X_LOTE;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.DocumentoSQL.CONSULTA_DOCUMENTOS_POR_MOVIMIENTOS_EN_PADRON;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.DocumentoARCA;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.MtvoCancelDoc;
import mx.gob.sat.siat.cob.seguimiento.dto.compartidos.CatalogoBase;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.BitacoraSeguimiento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DetalleDocumento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Documento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.EstadoDocumento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.MultaPendienteDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ParametrosSgtDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.TipoDocumento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VigilanciaEntidadFederativa;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoDocumentoEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EtapaVigilanciaEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.IcepRenuenteEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.ParametroSgtEnum;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import mx.gob.sat.siat.cob.seguimiento.util.Utilerias;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DocumentoDAOImpl implements DocumentoDAO, DocumentoSQL {

    @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG)
    private JdbcTemplate template;
    @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG_NAMED_PARAMETER)
    private NamedParameterJdbcTemplate namedJDBCTemplate;
    @Autowired
    private ParametroSgtDAO parametroSgtDAO;
    private final Logger log = Logger.getLogger(DocumentoDAOImpl.class);

    @Override
    @Propagable(catched = true)
    public Integer actualizarEstatusDocumento(VigilanciaEntidadFederativa vef) {

        return template.update(ACTUALIZAR_ESTATUS_DOCUMENTO_VIGILANCIA,
                new Object[]{vef.getEstadoDocumento(),
            vef.getIdVigilancia(),
            vef.getIdEntidadFederativa()},
                new int[]{Types.INTEGER,
            Types.INTEGER,
            Types.INTEGER});
    }

    @Override
    @Propagable(catched = true)
    public Integer insertarBitacoraDocumentos(VigilanciaEntidadFederativa vef) {

        return template.update(BITACORA_RECHAZO_VIGILANCIA_EF,
                new Object[]{vef.getIdVigilancia(),
            vef.getIdEntidadFederativa()},
                new int[]{Types.NUMERIC,
            Types.NUMERIC});
    }

    /**
     */
    @Override
    @Propagable(catched = true)
    public List<Documento> consultaDocumentos() {
        String query = DocumentoSQL.CONSULTA_SECCIONADA + "where rownum between 1 and 10";
        return template.query(query, new DocumentoMapper());
    }

    /**
     * @param documento
     * @return
     */
    @Override
    @Propagable(catched = true)
    public Integer guardaDocumento(Documento documento) {
        return template.update(DocumentoSQL.INSERT, new Object[]{
            documento.getNumeroControl(),
            documento.getNumeroControlPadre(),
            documento.getFechaNotificacion(),
            documento.getFechaImpresion(),
            documento.getRfc(),
            documento.getBoid(),
            documento.getFechaVencimiento(),
            documento.getIdEtapaVigilancia(),
            documento.getFechaNoLocalizadoContribuyente(),
            documento.getVigilancia().getIdVigilancia(),
            documento.getEsUltimoGenerado(),
            documento.getUltimoEstado(),
            documento.getConsideraRenuencia(),
            documento.getIdAdmonLocal(),
            documento.getCodigoPostal(),
            documento.getIdcrh(),
            documento.getIdentidadFederativa(),
            documento.getIdTipoPersona()},
                new int[]{
            Types.VARCHAR,
            Types.VARCHAR,
            Types.DATE,
            Types.DATE,
            Types.VARCHAR,
            Types.DECIMAL,
            Types.DATE,
            Types.DECIMAL,
            Types.DATE,
            Types.DECIMAL,
            Types.DECIMAL,
            Types.DECIMAL,
            Types.DECIMAL,
            Types.VARCHAR,
            Types.VARCHAR,
            Types.DECIMAL,
            Types.DECIMAL,
            Types.VARCHAR
        });
    }

    /**
     * @param numControl
     * @return
     */
    @Override
    @Propagable(catched = true)
    public Documento consultaXNumControl(String numControl) {
        Documento documento = null;
        log.debug("consultaXNumControl:"+numControl);
        List<Documento> docs = (List<Documento>) this.template.query(
                DocumentoSQL.CONSULTA_X_NUMCONTROL,
                new Object[]{numControl},
                new int[]{Types.VARCHAR},
                new DocumentoMapper());
        if (docs != null && !docs.isEmpty()) {
            documento = docs.get(0);
        }
        return documento;
    }

    @Override
    @Propagable(catched = true)
    public EstadoDocumento consultaEstadoDoctoXNumControl(String numControl) {
        EstadoDocumento estadoDoc = null;
        String query = DocumentoSQL.CONSULTA_ESTADO_DOCTO_X_NUMCONTROL + "'" + numControl + "'";
        List<EstadoDocumento> listaDocs = this.template.query(query, new EstadoDocumentoMapper());
        if (listaDocs != null && !listaDocs.isEmpty()) {
            estadoDoc = listaDocs.get(0);
        }
        return estadoDoc;
    }

    /**
     * @param numControl
     * @return
     */
    @Override
    @Propagable(catched = true)
    public Integer updateConsideraRenuenciaDocto(String numControl) {
        String query = DocumentoSQL.UPDATE_CONSIDERA_RENUENCIA_DOCTO + "'" + numControl + "'";
        return this.template.update(query);
    }

    @Override
    @Propagable(catched = true)
    public Integer actualizarEstadoDocumentoPorLote(int limMenor, int limMayor, Documento tipoDoc, EstadoDocumentoEnum estado) {
        return template.update(ACTUALIZAR_ESTADO_DOCUMENTO_X_LOTE, new Object[]{estado.getValor(),
            tipoDoc.getVigilancia().getIdVigilancia(),
            tipoDoc.getVigilancia().getIdTipoDocumento(),
            tipoDoc.getUltimoEstado(),
            tipoDoc.getEsUltimoGenerado(),
            tipoDoc.getIdEtapaVigilancia(),
            tipoDoc.getConsideraRenuencia(),
            limMayor,
            limMenor});
    }

    @Override
    @Propagable(catched = true)
    public List<Documento> consultarDocumentosActualizadosPorLote(int limMenor, int limMayor, Documento tipoDoc) {

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        List<Integer> aplicaRenuentes = new ArrayList<Integer>();
        parameters.addValue("idVigilancia", tipoDoc.getVigilancia().getIdVigilancia());
        parameters.addValue("idTipoDocumento", tipoDoc.getVigilancia().getIdTipoDocumento());
        parameters.addValue("ultimoEstado", tipoDoc.getUltimoEstado());
        parameters.addValue("esUltimoGenerado", tipoDoc.getEsUltimoGenerado());
        parameters.addValue("idEtapaVigilancia", tipoDoc.getIdEtapaVigilancia());
        parameters.addValue("consideraRenuencia", tipoDoc.getConsideraRenuencia());
        parameters.addValue("limMayor", limMayor);
        parameters.addValue("limMenor", limMenor);
        aplicaRenuentes.add(IcepRenuenteEnum.SI.getValor());
        if (tipoDoc.getEtapaVigilancia() == EtapaVigilanciaEnum.ETAPA_1 && tipoDoc.getConsideraRenuencia() == 0) {
            aplicaRenuentes.add(IcepRenuenteEnum.NO.getValor());
        }
        parameters.addValue("aplicaRenuentes", aplicaRenuentes);

        return namedJDBCTemplate.query(CONSULTA_DOCUMENTOS_ACTUALIZADOS_X_LOTE, parameters, new ConsultarDocumentosActualizadosMapper());

    }

    @Override
    @Propagable(catched = true)
    public List<DetalleDocumento> consultarICEPsOmisos(Documento doc) {
        return this.template.query(
                CONSULTAR_ICEPS_OMISOS,
                new Object[]{doc.getNumeroControl()},
                new int[]{Types.VARCHAR},
                new DetalleDocumentoMapper());
    }

    /**
     *
     * @param documento
     */
    @Override
    @Propagable(catched = true)
    public Integer actualizaSgtDocumento(Documento documento) {
        return template.update(DocumentoSQL.ACTUALIZAR_DOCUMENTO, new Object[]{
            documento.getFechaNotificacion(),
            documento.getFechaImpresion(),
            documento.getFechaVencimiento(),
            documento.getFechaNoLocalizadoContribuyente(),
            documento.getEsUltimoGenerado(),
            documento.getUltimoEstado(),
            documento.getConsideraRenuencia(),
            documento.getDateNoTrabajado(),
            documento.getNumeroControl()},
                new int[]{
            Types.DATE,
            Types.DATE,
            Types.DATE,
            Types.DATE,
            Types.DECIMAL,
            Types.DECIMAL,
            Types.DECIMAL,
            Types.DATE,
            Types.VARCHAR
        });
    }

    @Override
    @Propagable(catched = true)
    public Integer consultaTipoDocumento(String numControl) {
        Integer tipoDoc;

        tipoDoc = template.queryForObject(DocumentoSQL.TIPO_DOCUMENTO_NC,
                new Object[]{numControl},
                Integer.class);

        if (tipoDoc == null) {
            tipoDoc = -1;
        }

        return tipoDoc;

    }

    @Override
    @Propagable(catched = true)
    public Documento consultaDocumento(String numControl) {
        List<Documento> lstDocumentos;
        lstDocumentos = (List<Documento>) template.query(
                DocumentoSQL.DOCUMENTO_NC,
                new Object[]{numControl},
                new DocumentoMapper());

        if (lstDocumentos != null && (lstDocumentos.size() > 0)) {
            return lstDocumentos.get(0);
        }

        return null;
    }

    @Override
    @Propagable(catched = true)
    public List<Documento> documentosAprocesar(Integer[] estados) {
        List<Integer> estadosCollection = Arrays.asList(estados);
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("estados", estadosCollection);
        List<Documento> lstDocumento = this.namedJDBCTemplate.query(
                DOCUMENTOS_A_PROCESAR,
                parameters,
                new DocumentoMapper());
        return lstDocumento;

    }

    @Override
    @Propagable(catched = true)
    public List<Documento> documentosAprocesar(Integer[] estados, String idVigilancia, String idLocal) {
        List<Integer> estadosCollection = Arrays.asList(estados);
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("estados", estadosCollection);
        parameters.addValue("idVigilancia", idVigilancia);
        parameters.addValue("idAdmonlocal", idLocal);
        List<Documento> lstDocumento = this.namedJDBCTemplate.query(
                DOCUMENTOS_A_PROCESAR_VIGILANCIA_LOCAL,
                parameters,
                new DocumentoMapper());
        return lstDocumento;
    }

    @Override
    @Propagable(catched = true)
    public Integer actualizaBitacoraDocumento(Documento documento, EstadoDocumentoEnum estado) {
        Integer resp = -1;

        StringBuilder sqlInsert = new StringBuilder();
        sqlInsert.append("insert into sgtb_sgtdocumento values('").append(documento.getNumeroControl()).append("'");
        sqlInsert.append(",").append(estado.getValor()).append(",SYSDATE)");
        resp = template.update(sqlInsert.toString());
        return resp;
    }

    @Override
    @Propagable(catched = true)
    public List<DetalleDocumento> consultarICEPsOmisosPorLote(int limMenor, int limMayor, Documento tipoDocumento) {
        List<DetalleDocumento> icepsOmisos;
        List<Integer> aplicaRenuentes = new ArrayList<Integer>();
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("idVigilancia", tipoDocumento.getVigilancia().getIdVigilancia());
        parameters.addValue("idTipoDocumento", tipoDocumento.getVigilancia().getIdTipoDocumento());
        parameters.addValue("ultimoEstado", tipoDocumento.getUltimoEstado());
        parameters.addValue("esUltimoGenerado", tipoDocumento.getEsUltimoGenerado());
        parameters.addValue("idEtapaVigilancia", tipoDocumento.getIdEtapaVigilancia());
        parameters.addValue("consideraRenuencia", tipoDocumento.getConsideraRenuencia());
        parameters.addValue("limMayor", limMayor);
        parameters.addValue("limMenor", limMenor);
        aplicaRenuentes.add(IcepRenuenteEnum.SI.getValor());
        if (tipoDocumento.getEtapaVigilancia() == EtapaVigilanciaEnum.ETAPA_1 && tipoDocumento.getConsideraRenuencia() == 0) {
            aplicaRenuentes.add(IcepRenuenteEnum.NO.getValor());
        }
        parameters.addValue("aplicaRenuentes", aplicaRenuentes);

        icepsOmisos = namedJDBCTemplate.query(CONSULTAR_ICEPS_X_LOTE, parameters, new ConsultarICEPsOmisosMapper());

        return icepsOmisos;
    }

    @Override
    @Propagable
    public void insertarEdoDocumentoBitacoraPorLote(int limMenor, int limMayor, Documento tipoDoc,
            EstadoDocumentoEnum estadoDestino) {
        template.update(INSERTAR_ESTADOS_DOCUMENTOS_X_LOTE,
                new Object[]{
            estadoDestino.getValor(),
            new Date(),
            tipoDoc.getVigilancia().getIdVigilancia(),
            tipoDoc.getVigilancia().getIdTipoDocumento(),
            tipoDoc.getUltimoEstado(),
            tipoDoc.getEsUltimoGenerado(),
            tipoDoc.getIdEtapaVigilancia(),
            tipoDoc.getConsideraRenuencia(),
            limMayor,
            limMenor});
    }

    @Override
    @Propagable(catched = true)
    public Integer actualizarEstadoDocumento(List<String> docs, EstadoDocumentoEnum estadoDestino) {
        StringBuilder temp = new StringBuilder("");
        final int maxParametros = 1000;
        final int cont = 1;
        int ii = 0;
        do {
            int longitud;
            for (; (ii < (maxParametros * cont)) && (ii < docs.size()); ii++) {
                temp.append("'").append(docs.get(ii)).append("'");
                if (docs.size() > (ii + 1)) {
                    temp.append(",");
                }
            }
            longitud = temp.length() - 1;
            temp.substring(0, longitud);

            StringBuilder sqlupdate = new StringBuilder();
            sqlupdate.append("UPDATE SGTT_DOCUMENTO SET ULTIMOESTADO =").append(estadoDestino.getValor());
            sqlupdate.append(" WHERE numerocontrol IN (").append(temp).append(")");
            return template.update(sqlupdate.toString());
        } while (ii < docs.size());

    }

    @Override
    @Propagable(catched = true)
    public Integer actualizarEstadoDocumento(int idVigilancia, EstadoDocumentoEnum estadoDestino) {
        return template.update(ACTUALIZA_ESTADO_VIGILANCIA,
                new Object[]{estadoDestino.getValor(), idVigilancia},
                new int[]{Types.NUMERIC, Types.NUMERIC});
    }

    @Override
    @Propagable(catched = true)
    public Integer insertaMotivoCancelacion(List<String> numsControl, MtvoCancelDoc motivoCancelacion) {
        Integer resp = -1;

        for (String numControl : numsControl) {
            StringBuilder query = new StringBuilder("insert into sgtt_doccancelado (numerocontrol, idmotivocancelacion) values ('" + numControl + "', " + motivoCancelacion.getIdMotivoCancelacion() + ")");
            log.log(Level.INFO, query);
            resp = this.template.update(query.toString());
            if (resp == -1) {
                break;
            }
        }
        return resp;
    }

    @Override
    @Propagable(catched = true)
    public TipoDocumento getTipoDocumentoXNumControl(String numControl) {
        TipoDocumento doc = null;
        String query = DocumentoSQL.GET_TIPO_DOCUMENTO_X_NUMCONTROL + "'" + numControl + "'";
        List<TipoDocumento> docs = this.template.query(query, new TipoDocumentoFullMapper());
        if (!docs.isEmpty()) {
            doc = docs.get(0);
        }
        return doc;
    }

    @Override
    @Propagable(catched = true)
    public List<Documento> listarDocumentosConBajaEnPadron() {
        try {
            return (List<Documento>) template.queryForObject(CONSULTA_DOCUMENTOS_POR_MOVIMIENTOS_EN_PADRON, new DocumentoPadronMapper());
        } catch (EmptyResultDataAccessException e) {
            log.debug("No hay resultados \n" + CONSULTA_DOCUMENTOS_POR_MOVIMIENTOS_EN_PADRON);
            return null;
        }
    }

    @Override
    @Propagable(catched = true)
    public List<CatalogoBase> obtenerResultadosDiligenciacion() {
        return template.query(CONSULTA_ESTADO_DOCUMENTO_NOTIFICADO_NO_LOCALIZADO, new ResultadoDiligenciaMapper());
    }

    @Override
    @Propagable
    public List<BitacoraSeguimiento> obtenerRegistrosBitacoraSeguimiento(String numeroControl) {

        return template.query(CONSULTA_REGISTROS_BITACORA,
                new Object[]{numeroControl},
                new int[]{Types.VARCHAR},
                new BitacoraSeguimientoMapper());
    }

    @Override
    @Propagable(catched = true)
    public List<MultaPendienteDTO> obtenerMultasPendientes(String rfc) {
        ParametrosSgtDTO param = parametroSgtDAO.obtenerParametroSgtPorIdParametro(ParametroSgtEnum.CANTIDAD_DIAS_REPORTE_EF.getValor());
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -Integer.parseInt(param.getValor()));

        String fechaInicio = Utilerias.formatearFechaAAAAMMDD(cal.getTime());
        String fechaFin = Utilerias.formatearFechaAAAAMMDD(new Date());

        StringBuilder sql = new StringBuilder();
        sql.append(CONSULTA_MULTAS_PENDIENTES);
        sql.append(" and fechaNotificacion >= to_date('").append(fechaInicio).append(" 00:00:00','YYYY/MM/DD HH24:MI:SS.SSSSS') "
                + "and fechaNotificacion < to_date('").append(fechaFin).append(" 23:59:59','YYYY/MM/DD HH24:MI:SS.SSSSS')");
        log.debug("QueryObtenerMultasPendientes: " + sql.toString());
        return template.query(sql.toString(),
                new Object[]{rfc},
                new int[]{Types.VARCHAR},
                new MultaPendienteMapper());

    }

    @Override
    public void actualizarFechaDocumento(String numeroControl, Date fecha, Date fechaVencimiento, EstadoDocumentoEnum estado) {
        try {
            StringBuilder sql = new StringBuilder();

            sql.append("UPDATE SGTT_DOCUMENTO SET ");
            if (estado == EstadoDocumentoEnum.NOTIFICADO) {
                sql.append("FECHANOLOCALIZADOCONTRIBUYENTE=NULL, FECHANOTIFICACION");
                sql.append("=?, FECHAVENCIMIENTODOCTO =?, ULTIMOESTADO=? WHERE NUMEROCONTROL=?");
                template.update(sql.toString(),
                        new Object[]{fecha,
                    fechaVencimiento,
                    estado.getValor(),
                    numeroControl},
                        new int[]{Types.DATE, Types.DATE, Types.INTEGER, Types.VARCHAR});
            }
            if (estado == EstadoDocumentoEnum.NO_LOCALIZADO) {
                sql.append("FECHANOTIFICACION=NULL, FECHAVENCIMIENTODOCTO=NULL, FECHANOLOCALIZADOCONTRIBUYENTE");
                sql.append("=?, ULTIMOESTADO=? WHERE NUMEROCONTROL=?");
                template.update(sql.toString(),
                        new Object[]{fecha,
                    estado.getValor(),
                    numeroControl},
                        new int[]{Types.DATE, Types.INTEGER, Types.VARCHAR});
            }

        } catch (DataAccessException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void actualizaSoloFecha(String numeroControl, Date fecha, Date fechaVencimiento, EstadoDocumentoEnum estado) {
        try {
            StringBuilder sql = new StringBuilder();

            sql.append("UPDATE SGTT_DOCUMENTO SET ");
            if (estado == EstadoDocumentoEnum.NOTIFICADO) {
                sql.append("FECHANOLOCALIZADOCONTRIBUYENTE=NULL, FECHANOTIFICACION");
                sql.append("=?, FECHAVENCIMIENTODOCTO =? WHERE NUMEROCONTROL=?");
                template.update(sql.toString(),
                        new Object[]{fecha,
                    fechaVencimiento,
                    numeroControl},
                        new int[]{Types.DATE, Types.DATE, Types.VARCHAR});
            }
            if (estado == EstadoDocumentoEnum.NO_LOCALIZADO) {
                sql.append("FECHANOTIFICACION=NULL, FECHAVENCIMIENTODOCTO=NULL, FECHANOLOCALIZADOCONTRIBUYENTE");
                sql.append("=? WHERE NUMEROCONTROL=?");
                template.update(sql.toString(),
                        new Object[]{fecha,
                    numeroControl},
                        new int[]{Types.DATE, Types.VARCHAR});
            }
        } catch (DataAccessException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    @Propagable(catched = true)
    public Integer actualizarDocsUltimoGenerado(List<Documento> documentos, int esUltimoGenerado) {
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
            parameters.addValue("esultimogenerado", esUltimoGenerado);
            parameters.addValue("documentos", documentosEnviar);

            rs = namedJDBCTemplate.update(ACTUALIZAR_DOCUMENTOS_ESULTIMOGENERADO, parameters);
            if (rs == 0) {
                return rs;
            }
        } while (ii < documentos.size());
        return rs;

    }

    @Override
    @Propagable(catched = true)
    public Map<String, String> guardarDocumentos(final List<Documento> documentos) {
        String totales = "ok";
        Map<String, String> resultados = new HashMap<String, String>();
        resultados.put("exception", "");

        try {
            template.batchUpdate(INSERTAR_DOCUMENTOS, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    Documento doc = documentos.get(i);
                    ps.setString(ConstantsCatalogos.UNO, doc.getNumeroControl());
                    ps.setString(ConstantsCatalogos.DOS, doc.getNumeroControlPadre());
                    ps.setDate(ConstantsCatalogos.TRES, doc.getFechaNotificacion() == null
                            ? null : new java.sql.Date(doc.getFechaNotificacion().getTime()));
                    ps.setDate(ConstantsCatalogos.CUATRO, doc.getFechaImpresion() == null
                            ? null : new java.sql.Date(doc.getFechaImpresion().getTime()));
                    ps.setString(ConstantsCatalogos.CINCO, doc.getRfc());
                    ps.setString(ConstantsCatalogos.SEIS, doc.getBoid());
                    ps.setDate(ConstantsCatalogos.SIETE, doc.getFechaVencimiento() == null
                            ? null : new java.sql.Date(doc.getFechaVencimiento().getTime()));
                    ps.setInt(ConstantsCatalogos.OCHO, doc.getEtapaVigilancia().getValor());
                    ps.setDate(ConstantsCatalogos.NUEVE, doc.getFechaNoLocalizadoContribuyente() == null
                            ? null : new java.sql.Date(doc.getFechaNoLocalizadoContribuyente().getTime()));
                    ps.setLong(ConstantsCatalogos.DIEZ, doc.getVigilancia().getIdVigilancia());
                    ps.setInt(ConstantsCatalogos.ONCE, doc.getEsUltimoGenerado());
                    ps.setInt(ConstantsCatalogos.DOCE, doc.getUltimoEstado());
                    ps.setDate(ConstantsCatalogos.TRECE, doc.getFechaRegistro() == null
                            ? null : new java.sql.Date(doc.getFechaRegistro().getTime()));
                    ps.setInt(ConstantsCatalogos.CATORCE, doc.getConsideraRenuencia());
                    ps.setDate(ConstantsCatalogos.QUINCE, doc.getDateNoTrabajado() == null
                            ? null : new java.sql.Date(doc.getDateNoTrabajado().getTime()));
                    ps.setString(ConstantsCatalogos.DIECISEIS, doc.getIdAdmonLocal());
                    ps.setString(ConstantsCatalogos.DIECISIETE, doc.getCodigoPostal());
                    ps.setInt(ConstantsCatalogos.DIECIOCHO, doc.getIdcrh());
                    ps.setInt(ConstantsCatalogos.DIECINUEVE, doc.getIdentidadFederativa());
                    ps.setString(ConstantsCatalogos.VEINTE, doc.getIdTipoPersona());

                }

                @Override
                public int getBatchSize() {
                    return documentos.size();
                }
            });
        } catch (DataAccessException ex) {
            log.error(ex.getMessage(), ex);
            return null;
        }

        resultados.put("estado", totales);
        return resultados;
    }

    @Override
    @Propagable(catched = true)
    public Integer numeroDetallesXNumeroControl(DocumentoARCA documento) {
        return template.queryForObject(NUMERO_DETALLES_X_NUM_CONTROL,
                new Object[]{
            documento.getId()},
                new int[]{
            Types.VARCHAR}, Integer.class);
    }

    @Propagable
    @Override
    public Integer actualizarEstadoDoc(Integer ultimoEstado, Long idVigilancia, String idAlsc, String fechaMonitor) {
        return template.update(DocumentoSQL.ACTUALIZA_ESTADO_DOC, new Object[]{
            ultimoEstado,
            idVigilancia,
            idAlsc,
            fechaMonitor},
                new int[]{
            Types.INTEGER,
            Types.INTEGER,
            Types.VARCHAR,
            Types.VARCHAR
        });
    }
}
