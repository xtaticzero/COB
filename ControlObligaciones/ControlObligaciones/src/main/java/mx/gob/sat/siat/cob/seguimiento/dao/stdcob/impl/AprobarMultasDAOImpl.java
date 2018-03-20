package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.AprobarMultasDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.MultaAprobarGrupoMapper;
import mx.gob.sat.siat.cob.seguimiento.dto.db2.Paginador;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.AdministrativoNivelCargo;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.MultaAprobar;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.MultaAprobarDetalle;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.MultaAprobarGrupo;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoMultaEnum;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import java.util.Arrays;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.CadenaOriginalMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.MultaAprobarDetalleMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.MultaAprobarMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.MultaAprobarReporteMapper;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.AprobarMultasSQL.MULTA_GRUPOS;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.AprobarMultasSQL.MULTA_DOCUMENTOS_PAGINADOS;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.AprobarMultasSQL.MULTA_DOCUMENTOS_NO_APROBAR;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.AprobarMultasSQL.MULTA_DETALLE;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.AprobarMultasSQL.CONSULTA_NUMERO_RESOLUCIONES;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.AprobarMultasSQL.EMITIR_RESOLUCIONES;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.AprobarMultasSQL.BITACORA_CAMBIO_ESTADO;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.AprobarMultasSQL.CONDICION_ADMON_LOCAL;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.AprobarMultasSQL.CONDICION_LOCAL_IGUAL;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.AprobarMultasSQL.CONDICION_LOCAL_DIFERENTE;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.AprobarMultasSQL.EMITIDOS_REPORTE;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.AprobarMultasSQL.CAMBIA_LOCAL_ORIGEN;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.AprobarMultasSQL.MULTAS_TRASLADADAS;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.AprobarMultasSQL.SUMA_MONTO;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.AprobarMultasSQL.CADENAS_ORIGINALES;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.AprobarMultasSQL.ELIMINA_FIRMAS;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.AprobarMultasSQL.SELECT_CON_MONTO;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.AprobarMultasSQL.SELECT_GENERAL_PARA_FIRMA;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.AprobarMultasSQL.ACTUALIZA_LOCAL_ACTUAL_ORIGEN;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.AprobarMultasSQL.ACTUALIZA_ORIGEN;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.CadenaOriginal;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.MultaAprobarReporteDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.NivelEmisionEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoFirmaEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoMedioEnvioEnum;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author root
 */
@Repository
public class AprobarMultasDAOImpl implements AprobarMultasDAO {

    @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG_NAMED_PARAMETER)
    private NamedParameterJdbcTemplate namedJDBCTemplate;

    @Override
    @Propagable(catched = true)
    public List<MultaAprobarGrupo> listarMultasAgrupadas(AdministrativoNivelCargo administrativoNivelCargo, EstadoMultaEnum estadoMultaEnum, TipoMedioEnvioEnum medioNoConsiderar, String[] tipoMultasConsiderar) {

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("estadoMulta", estadoMultaEnum.getValor());
        parameters.addValue("medioNoConsiderar", medioNoConsiderar.getValor());
        parameters.addValue("tiposMulta", Arrays.asList(tipoMultasConsiderar));
        parameters.addValue("nivelEmision", administrativoNivelCargo.getNivelEmision().getIdNivelEmision());
        parameters.addValue("cargoAdmtvo", administrativoNivelCargo.getIdCargoAdministrativo());
        parameters.addValue("admonLocal", administrativoNivelCargo.getIdAdministacionLocal());
        return this.namedJDBCTemplate.query(condicionAdmonLocal(administrativoNivelCargo, MULTA_GRUPOS),
                parameters,
                new MultaAprobarGrupoMapper());
    }

    @Override
    @Propagable(catched = true)
    public List<MultaAprobar> listarMultasPorAprobar(MultaAprobarGrupo multaAprobarGrupo, Paginador paginador,
            AdministrativoNivelCargo administrativoNivelCargo, EstadoMultaEnum estadoMultaEnum) {

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        agregarParametros(parameters, multaAprobarGrupo, estadoMultaEnum, administrativoNivelCargo);
        parameters.addValue("rowInicial", paginador.getRowInicial());
        parameters.addValue("rowFinal", paginador.getRowFinal());
        return this.namedJDBCTemplate.query(condicionAdmonLocal(administrativoNivelCargo, MULTA_DOCUMENTOS_PAGINADOS),
                parameters,
                new MultaAprobarMapper());
    }

    @Override
    @Propagable(catched = true)
    public List<MultaAprobar> listarMultasNoAprobar(MultaAprobarGrupo multaAprobarGrupo, AdministrativoNivelCargo administrativoNivelCargo,
            EstadoMultaEnum estadoMultaEnum) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        agregarParametros(parameters, multaAprobarGrupo, estadoMultaEnum, administrativoNivelCargo);
        String query = MULTA_DOCUMENTOS_NO_APROBAR.replace(SUMA_MONTO, "");
        return this.namedJDBCTemplate.query(condicionAdmonLocal(administrativoNivelCargo, query),
                parameters,
                new MultaAprobarMapper());
    }

    /**
     *
     * @param multaAprobarGrupo
     * @param estadoMultaEnum
     * @param administrativoNivelCargo
     * @return
     */
    @Override
    @Propagable(catched = true)
    public List<MultaAprobarReporteDTO> listaMultasNoGeneradas(MultaAprobarGrupo multaAprobarGrupo, EstadoMultaEnum estadoMultaEnum, AdministrativoNivelCargo administrativoNivelCargo) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        agregarParametros(parameters, multaAprobarGrupo, estadoMultaEnum, administrativoNivelCargo);
        String query = condicionAdmonLocal(administrativoNivelCargo, MULTA_DOCUMENTOS_NO_APROBAR);
        query = query.replace(SUMA_MONTO, "");
        return this.namedJDBCTemplate.query(query,
                parameters,
                new MultaAprobarReporteMapper());
    }

    /**
     *
     * @param multaAprobar
     * @return
     */
    @Override
    public List<MultaAprobarDetalle> listarDetallesMulta(MultaAprobar multaAprobar) {

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("numeroControl", multaAprobar.getNumeroControl());
        parameters.addValue("numeroResolucion", multaAprobar.getNumeroResolucion());
        return this.namedJDBCTemplate.query(MULTA_DETALLE,
                parameters,
                new MultaAprobarDetalleMapper());
    }

    @Override
    @Propagable(catched = true)
    public Integer emitirMultas(MultaAprobarGrupo multaAprobarGrupo, EstadoMultaEnum estadoOrigen, EstadoMultaEnum estadoDestino, AdministrativoNivelCargo administrativoNivelCargo) {

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        String query = EMITIR_RESOLUCIONES;
        if (multaAprobarGrupo.getIdTipoFirma().equals(TipoFirmaEnum.ELECTRONICA.getValor())) {
            query = query.replace(SELECT_CON_MONTO, SELECT_GENERAL_PARA_FIRMA);
        }
        query = condicionAdmonLocal(administrativoNivelCargo, query);
        
        agregarParametros(parameters, multaAprobarGrupo, estadoOrigen, administrativoNivelCargo);
        parameters.addValue("estadoDestino", estadoDestino.getValor());
        parameters.addValue("numEmpleado", administrativoNivelCargo.getNumeroEmpleado());
        return this.namedJDBCTemplate.update(query,
                parameters);
    }

    @Override
    @Propagable(catched = true)
    public Integer bitacoraCambioMultas(MultaAprobarGrupo multaAprobarGrupo, EstadoMultaEnum estadoOrigen, EstadoMultaEnum estadoDestino, AdministrativoNivelCargo administrativoNivelCargo) {

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        agregarParametros(parameters, multaAprobarGrupo, estadoOrigen, administrativoNivelCargo);
        String query = BITACORA_CAMBIO_ESTADO;

        if (multaAprobarGrupo.getIdTipoFirma().equals(TipoFirmaEnum.ELECTRONICA.getValor())) {
            query = query.replace(SELECT_CON_MONTO, SELECT_GENERAL_PARA_FIRMA);
        }
        query = condicionAdmonLocal(administrativoNivelCargo, query);
        
        parameters.addValue("estadoDestino", estadoDestino.getValor());
        return this.namedJDBCTemplate.update(query,
                parameters);
    }

    @Override
    @Propagable(catched = true)
    public Integer contarResolucionesAprobar(MultaAprobarGrupo multaAprobarGrupo, EstadoMultaEnum estadoMultaEnum, AdministrativoNivelCargo administrativoNivelCargo) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        agregarParametros(parameters, multaAprobarGrupo, estadoMultaEnum, administrativoNivelCargo);
        String query = CONSULTA_NUMERO_RESOLUCIONES.replace(SUMA_MONTO, "");
        return this.namedJDBCTemplate.queryForObject(condicionAdmonLocal(administrativoNivelCargo, query),
                parameters, Integer.class);
    }

    /**
     *
     * @param multaAprobarGrupo
     * @param estadoMultaEnum
     * @param administrativoNivelCargo
     * @return
     */
    @Override
    @Propagable(catched = true)
    public List<MultaAprobarReporteDTO> listaMultasAemitir(MultaAprobarGrupo multaAprobarGrupo, EstadoMultaEnum estadoMultaEnum, AdministrativoNivelCargo administrativoNivelCargo) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        agregarParametros(parameters, multaAprobarGrupo, estadoMultaEnum, administrativoNivelCargo);
        String query = EMITIDOS_REPORTE;
        if (multaAprobarGrupo.getIdTipoFirma().equals(TipoFirmaEnum.ELECTRONICA.getValor())) {
            query = query.replace(SELECT_CON_MONTO, SELECT_GENERAL_PARA_FIRMA);
        }else{
            query = query.replace(SUMA_MONTO, "");
        }
        query = condicionAdmonLocal(administrativoNivelCargo, query);   
        return this.namedJDBCTemplate.query(query,
                parameters,
                new MultaAprobarReporteMapper());
    }

    /**
     *
     * @param multaAprobarGrupo
     * @param estadoMultaEnum
     * @param administrativoNivelCargo
     * @return
     */
    @Override
    public List<MultaAprobarReporteDTO> listaMultasTrasladadas(MultaAprobarGrupo multaAprobarGrupo, EstadoMultaEnum estadoMultaEnum, AdministrativoNivelCargo administrativoNivelCargo) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        agregarParametros(parameters, multaAprobarGrupo, estadoMultaEnum, administrativoNivelCargo);
        String query = MULTAS_TRASLADADAS.replaceAll(CONDICION_LOCAL_IGUAL, CONDICION_LOCAL_DIFERENTE);
        return this.namedJDBCTemplate.query(condicionAdmonLocal(administrativoNivelCargo, query),
                parameters,
                new MultaAprobarReporteMapper());
    }

    /**
     *
     * @param multaAprobarGrupo
     * @param estadoMultaEnum
     * @param administrativoNivelCargo
     * @return
     */
    @Override
    @Propagable(catched = true)
    public Integer actualizarLocalesOrigen(MultaAprobarGrupo multaAprobarGrupo, EstadoMultaEnum estadoMultaEnum, AdministrativoNivelCargo administrativoNivelCargo) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        String query = CAMBIA_LOCAL_ORIGEN.replaceAll(CONDICION_LOCAL_IGUAL, CONDICION_LOCAL_DIFERENTE);       
        query = query.replace(SUMA_MONTO, "");
        agregarParametros(parameters, multaAprobarGrupo, estadoMultaEnum, administrativoNivelCargo);
        return this.namedJDBCTemplate.update(condicionAdmonLocal(administrativoNivelCargo, query),
                parameters);
    }

    private void agregarParametros(MapSqlParameterSource parameters, MultaAprobarGrupo multaAprobarGrupo, EstadoMultaEnum estadoMultaEnum, AdministrativoNivelCargo administrativoNivelCargo) {
        parameters.addValue("idmedio", multaAprobarGrupo.getIdMedioEnvio());
        parameters.addValue("idfirma", multaAprobarGrupo.getIdTipoFirma());
        parameters.addValue("idmulta", multaAprobarGrupo.getIdTipoMulta());
        parameters.addValue("estadoMulta", estadoMultaEnum.getValor());
        parameters.addValue("fechaRegistro", multaAprobarGrupo.getFechaEmision());
        parameters.addValue("nivelEmision", administrativoNivelCargo.getNivelEmision().getIdNivelEmision());
        parameters.addValue("cargoAdmtvo", administrativoNivelCargo.getIdCargoAdministrativo());
        parameters.addValue("admonLocal", administrativoNivelCargo.getIdAdministacionLocal());
    }

    /*@Propagable(catched = true)*/
    /**
     *
     * @param multaAprobarGrupo
     * @param estadoMultaEnum
     * @param administrativoNivelCargo
     * @return
     */
    @Override
    public Integer actualizarLocalesActuales(MultaAprobarGrupo multaAprobarGrupo, EstadoMultaEnum estadoMultaEnum, AdministrativoNivelCargo administrativoNivelCargo) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        String query = ACTUALIZA_LOCAL_ACTUAL_ORIGEN;
        if (multaAprobarGrupo.getIdTipoFirma().equals(TipoFirmaEnum.ELECTRONICA.getValor())) {
            query = query.replace(MULTA_DOCUMENTOS_PAGINADOS, SELECT_GENERAL_PARA_FIRMA);
        }else{
            query = query.replace(MULTA_DOCUMENTOS_PAGINADOS, SELECT_CON_MONTO);
            query = query.replace(SUMA_MONTO, "");
        }
        query = query.replace(ACTUALIZA_ORIGEN, "");
        agregarParametros(parameters, multaAprobarGrupo, estadoMultaEnum, administrativoNivelCargo);
        return this.namedJDBCTemplate.update(condicionAdmonLocal(administrativoNivelCargo, query),
                parameters);
    }

    @Override
    @Propagable(catched = true)
    public List<CadenaOriginal> listarCadenasOriginales(MultaAprobarGrupo multaAprobarGrupo, EstadoMultaEnum estadoMultaEnum,
            AdministrativoNivelCargo administrativoNivelCargo, String nombreFuncionario,
            Integer rowInicial, Integer rowFinal) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("nombreFuncionario", nombreFuncionario);
        agregarParametros(parameters, multaAprobarGrupo, estadoMultaEnum, administrativoNivelCargo);
        parameters.addValue("rowInicial", rowInicial);
        parameters.addValue("rowFinal", rowFinal);
        return this.namedJDBCTemplate.query(
                condicionAdmonLocal(administrativoNivelCargo, CADENAS_ORIGINALES),
                parameters,
                new CadenaOriginalMapper());
    }

    @Override
    @Propagable(catched = true)
    public Integer eliminaFirmasTrasladadas(MultaAprobarGrupo multaAprobarGrupo, EstadoMultaEnum estadoMultaEnum, AdministrativoNivelCargo administrativoNivelCargo) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        String query = ELIMINA_FIRMAS.replaceAll(CONDICION_LOCAL_IGUAL, CONDICION_LOCAL_DIFERENTE);
        agregarParametros(parameters, multaAprobarGrupo, estadoMultaEnum, administrativoNivelCargo);
        return this.namedJDBCTemplate.update(condicionAdmonLocal(administrativoNivelCargo, query),
                parameters);
    }

    @Override
    @Propagable(catched = true)
    public Integer eliminaFirmasSinAprobar(MultaAprobarGrupo multaAprobarGrupo, EstadoMultaEnum estadoMultaEnum, AdministrativoNivelCargo administrativoNivelCargo) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        String query = ELIMINA_FIRMAS;
        agregarParametros(parameters, multaAprobarGrupo, estadoMultaEnum, administrativoNivelCargo);
        return this.namedJDBCTemplate.update(condicionAdmonLocal(administrativoNivelCargo, query),
                parameters);
    }

    private String condicionAdmonLocal(AdministrativoNivelCargo administrativoNivelCargo, String consulta) {
        return administrativoNivelCargo.getNivelEmision() == NivelEmisionEnum.LOCAL
                ? consulta : consulta.replaceAll(CONDICION_ADMON_LOCAL, "");
    }

    @Override
    public Integer actualizarLocalesActualesOrigen(MultaAprobarGrupo multaAprobarGrupo, 
                EstadoMultaEnum estadoMultaEnum, AdministrativoNivelCargo administrativoNivelCargo,
                Integer rowInicial, Integer rowFinal) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        String query = ACTUALIZA_LOCAL_ACTUAL_ORIGEN;
        agregarParametros(parameters, multaAprobarGrupo, estadoMultaEnum, administrativoNivelCargo);
        parameters.addValue("rowInicial", rowInicial);
        parameters.addValue("rowFinal", rowFinal);
        return this.namedJDBCTemplate.update(condicionAdmonLocal(administrativoNivelCargo, query),
                parameters);
    }

    @Override
    public Integer actualizarLocalesActualesOrigen(MultaAprobarGrupo multaAprobarGrupo, EstadoMultaEnum estadoMultaEnum, AdministrativoNivelCargo administrativoNivelCargo) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        String query = ACTUALIZA_LOCAL_ACTUAL_ORIGEN;
        query = query.replace(MULTA_DOCUMENTOS_PAGINADOS, SELECT_CON_MONTO);
        query = query.replace(SUMA_MONTO, "");
        agregarParametros(parameters, multaAprobarGrupo, estadoMultaEnum, administrativoNivelCargo);
        return this.namedJDBCTemplate.update(condicionAdmonLocal(administrativoNivelCargo, query),
                parameters);
    }

}
