package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.DetalleConsultaVigilanciaDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.VigilanciaDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.ConsultaDocsVigilanciaPorEstadoMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.VigilanciaEtapaMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.VigilanciaMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.EmisionVigilanciaSQL;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.VigilanciaSQL;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.VigilanciaSQL.CONSULTA_VIGILANCIAS_PENDIENTES_EF;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.VigilanciaSQL.CONSULTA_VIGILANCIA_EXISTENTE;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.CargaVigilancia;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ConsultaVigilancia;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DetalleCarga;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.EmisionVigilancia;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Vigilancia;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoDocumentoEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EtapaVigilanciaEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoDocumentoEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoMedioEnvioEnum;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author root
 */
@Repository
public class VigilanciaDAOImpl implements VigilanciaDAO, VigilanciaSQL {

    private final Logger log = Logger.getLogger(VigilanciaDAOImpl.class);
    @Autowired
    private DetalleConsultaVigilanciaDAO detalleConsultaVigilanciaDAO;
    @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG)
    private JdbcTemplate template;
    @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG_NAMED_PARAMETER)
    private NamedParameterJdbcTemplate namedJDBCTemplate;

    /**
     *
     * @return @throws
     * mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException
     */
    @Override
    @Propagable
    public List<ConsultaVigilancia> consultaVigilancia() throws SeguimientoDAOException {
        List<ConsultaVigilancia> listado;
        log.debug("Consultando las vigilancias ... esperando respuesta");
        log.debug(CONSULTA_VIGILANCIAS_PENDIENTES);
        listado = this.template.query(CONSULTA_VIGILANCIAS_PENDIENTES, new VigilanciaMapper());
        for (ConsultaVigilancia c : listado) {
            c.setDetalle(detalleConsultaVigilanciaDAO.consultaDetalleVigilancia(c.getIdVigilancia()));
        }
        return listado;
    }

    /**
     *
     * @return @throws
     * mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException
     */
    @Override
    @Propagable
    public List<ConsultaVigilancia> consultaVigilanciaEF() throws SeguimientoDAOException {
        List<ConsultaVigilancia> listado;
        log.debug("Consultando las vigilancias ... esperando respuesta");
        log.debug(CONSULTA_VIGILANCIAS_PENDIENTES_EF);
        listado = this.template.query(CONSULTA_VIGILANCIAS_PENDIENTES_EF, new VigilanciaMapper());
        for (ConsultaVigilancia c : listado) {
            c.setDetalle(detalleConsultaVigilanciaDAO.consultaDetalleVigilancia(c.getIdVigilancia()));
        }
        return listado;
    }

    /**
     *
     * @param idVigilancia
     * @return
     * @throws SeguimientoDAOException
     */
    @Override
    @Propagable
    public Integer consultaVigilanciasExistente(int idVigilancia) throws SeguimientoDAOException {
        log.debug(CONSULTA_VIGILANCIA_EXISTENTE);
        return this.template.queryForObject(CONSULTA_VIGILANCIA_EXISTENTE,
                new Object[]{idVigilancia},
                new int[]{Types.DECIMAL},
                Integer.class);

    }

    @Override
    @Propagable
    public Integer consultaVigilanciasExistenteEF(int idVigilancia) throws SeguimientoDAOException {
        log.debug(EXISTE_VIGILANCIA_EF);
        return this.template.queryForObject(EXISTE_VIGILANCIA_EF,
                new Object[]{idVigilancia},
                new int[]{Types.DECIMAL},
                Integer.class);

    }

    /**
     *
     * @param idVigilancia
     * @return
     */
    @Override
    @Propagable
    public int actualizaVigilancia(int idVigilancia) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE SGTT_VIGILANCIA SET IDESTADOVIGILANCIA = 2 WHERE IDVIGILANCIA = ").append(idVigilancia);
        log.debug("Actualizando el estado la vigilancia " + idVigilancia + " a PROCESADO ...");
        log.debug(sql.toString());
        return template.update(sql.toString());
    }

    /**
     *
     * @param idVigilancia
     * @param nombreArchivo
     */
    @Override
    public void guardarBitacoraErrores(int idVigilancia, String archivoLocal, String nombreArchivo) {
        log.debug(UPDATE_BITACORA_VIGILANCIA);
        template.update(UPDATE_BITACORA_VIGILANCIA, new Object[]{
            nombreArchivo, idVigilancia, archivoLocal
        },
                new int[]{
            Types.VARCHAR, Types.DECIMAL, Types.VARCHAR
        });
    }

    /**
     *
     * @param vigilancia
     * @throws mx.gob.sat.siat.cob.seguimiento.exception.SeguimientoDAOException
     */
    @Override
    @Propagable
    public void guarda(CargaVigilancia vigilancia) throws SeguimientoDAOException {
        log.debug(INSERT_VIGILANCIA);
        log.debug(vigilancia.getUsuario().getIp());
        template.update(INSERT_VIGILANCIA, new Object[]{
            vigilancia.getTipoDoc().getId(),
            vigilancia.getUsuario().getUsuario(),
            vigilancia.getUsuario().getClaveAdminGral() == null ? "" : vigilancia.getUsuario().getClaveAdminGral(),
            vigilancia.getUsuario().getClaveAdminCentral() == null ? "" : vigilancia.getUsuario().getClaveAdminCentral(),
            vigilancia.getUsuario().getNombreCompleto().split(" ")[0],
            vigilancia.getUsuario().getPrimerApellido(),
            vigilancia.getUsuario().getSegundoApellido(),
            vigilancia.getUsuario().getNumeroEmp(),
            //TODO: mientras se quita, a futuro se pondra otra ves vigilancia.getUsuario().getIp(),
            "127.0.0.1",
            vigilancia.getMedio().getId(),
            vigilancia.getMedio().getId() == TipoMedioEnvioEnum.ENTIDAD_FEDERATIVA.getValor()
            ? null : vigilancia.getFirma().getId(),
            vigilancia.getIdMovimiento(),
            vigilancia.getFechaCorte(),
            vigilancia.getMedio().getId() == TipoMedioEnvioEnum.ENTIDAD_FEDERATIVA.getValor()
            ? null : vigilancia.getIdNivelEmision(),
            vigilancia.getMedio().getId() == TipoMedioEnvioEnum.ENTIDAD_FEDERATIVA.getValor()
            ? null : vigilancia.getIdCargoAdmtvo(),
            vigilancia.getIdDescripcionVigilancia()
        },
                new int[]{
            Types.DECIMAL, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
            Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.DECIMAL,
            Types.DECIMAL, Types.DECIMAL, Types.DATE, Types.DECIMAL, Types.DECIMAL, Types.DECIMAL
        });
    }

    /**
     *
     * @param detalle
     */
    @Override
    @Propagable
    public void guardaDetalle(DetalleCarga detalle) {
        log.debug(INSERT_DETALLE_VIGILANCIA);
        log.debug(detalle);
        template.update(INSERT_DETALLE_VIGILANCIA, new Object[]{
            detalle.getNombreOriginalArchivo(),
            detalle.getNumeroRegistros(),
            detalle.getRutaEnRepositorio(),
            detalle.getRutaEnBitacora()
        },
                new int[]{
            Types.VARCHAR, Types.DECIMAL, Types.VARCHAR, Types.VARCHAR
        });
    }

    /**
     *
     * @return
     */
    @Override
    @Propagable
    public String consultaUltimoIdVigilancia() {
        String query = CONSULTA_ULTIMO_ID_VIGILANCIA;
        log.debug(query);
        return (String) this.template.queryForObject(query, String.class);
    }

    /**
     */
    @Override
    @Propagable
    public List<EmisionVigilancia> conusltaVigilanciaXEtapa() {
        try {
            return (List<EmisionVigilancia>) template.queryForObject(
                    EmisionVigilanciaSQL.SELECT_VIGILANCIA_X_ETAPA,
                    new VigilanciaEtapaMapper());
        } catch (EmptyResultDataAccessException e) {
            log.debug("No hay resultados \n" + EmisionVigilanciaSQL.SELECT_VIGILANCIA_X_ETAPA);
            return null;
        }
    }

    /**
     *
     * @param estadoDoc
     * @param etapaVig
     * @param tipoDocs
     * @return
     */
    @Override
    @Propagable(catched = true)
    public List<Vigilancia> consultarDocsVencidosVigilanciaPorEstado(EstadoDocumentoEnum ultimoEstado,
            EtapaVigilanciaEnum etapaVigilancia,
            TipoDocumentoEnum[] tipoDocs) {

        MapSqlParameterSource parameters = new MapSqlParameterSource();

        parameters.addValue("ultimoEstado", ultimoEstado.getValor());
        parameters.addValue("etapaVigilancia", etapaVigilancia.getValor());
        parameters.addValue("consideraRenuencia", (ultimoEstado == EstadoDocumentoEnum.VENCIDO || ultimoEstado == EstadoDocumentoEnum.VENCIDO_PARCIAL ? 1 : (etapaVigilancia.getValor() == 1 ? 0 : 1)));
        List<String> tipoDocumentos = new ArrayList();
        for (TipoDocumentoEnum tipoDoc : tipoDocs) {
            tipoDocumentos.add(String.valueOf(tipoDoc.getValor()));
        }
        parameters.addValue("tipoDocumentos", tipoDocumentos);

        return namedJDBCTemplate.query(CONSULTAR_DOCS_VIGILANCIA_POR_ESTADO, parameters, new ConsultaDocsVigilanciaPorEstadoMapper());
    }

    @Override
    @Propagable
    public Integer consultaIdTipoFirma(Long idvigilancia) {
        try {
            return (Integer) template.queryForObject(CONSULTA_TIPO_FIRMA, new Object[]{idvigilancia}, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            log.debug(e);
            return null;
        }
    }
}
