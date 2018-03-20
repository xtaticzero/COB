/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.AprobarRenuentesDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.VisualizaVigilanciaRenuentesMapper;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VisualizaDocumentoRenuente;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VisualizaVigilanciaRenuentes;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.AprobarRenuentesSQL.VIGILANCIA_RENUENTES;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.AprobarRenuentesSQL.CONSULTA_DOCUMENTOS_PAGINADOS;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.AprobarRenuentesSQL.CONSULTA_NUMERO_DOCUMENTOS;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.AprobarRenuentesSQL.CONSULTA_DETALLE_RENUENTE;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.AprobarRenuentesSQL.EMITIR_DOCUMENTOS;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.AprobarRenuentesSQL.BITACORA_CAMBIO_ESTADO;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.AprobarRenuentesSQL.CONDICION_ADMON_LOCAL;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.AprobarRenuentesSQL.ELIMINA_FIRMAS;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.AprobarRenuentesSQL.CADENAS_ORIGINALES;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.AprobarRenuentesSQL.JOIN_FIRMA;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.AprobarRenuentesSQL.SIN_FIRMA;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.AprobarRenuentesSQL.ACTUALIZAR_VIGILANCIA_AL;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.AprobarRenuentesSQL.BITACORA_EMISION_FUNCIONARIO;
import mx.gob.sat.siat.cob.seguimiento.dto.db2.Paginador;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import org.springframework.stereotype.Repository;
import java.sql.Types;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.CadenaOriginalMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.VisualizaDetalleRenuenteMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.VisualizaDocumentoRenuenteMapper;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.AdministrativoNivelCargo;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.CadenaOriginal;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VisualizaDetalleRenuente;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoDocumentoEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.NivelEmisionEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.SituacionIcepEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoFirmaEnum;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;


/**
 *
 * @author root
 */
@Repository
public class AprobarRenuentesDAOImpl implements AprobarRenuentesDAO {
    @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG_NAMED_PARAMETER)
    private NamedParameterJdbcTemplate namedJDBCTemplate;
    
     @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG)
    private JdbcTemplate template;

    @Override
    @Propagable(catched = true)
    public List<VisualizaVigilanciaRenuentes> listarVigilanciaRenuentes(AdministrativoNivelCargo administrativoNivelCargo) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();        
        
        parameters.addValue("nivelEmision", administrativoNivelCargo.getNivelEmision().getIdNivelEmision());
        parameters.addValue("cargoAdmtvo", administrativoNivelCargo.getIdCargoAdministrativo());
        parameters.addValue("admonLocal", administrativoNivelCargo.getIdAdministacionLocal());        
        return this.namedJDBCTemplate.query(
                administrativoNivelCargo.getNivelEmision()== NivelEmisionEnum.LOCAL? 
                            VIGILANCIA_RENUENTES : VIGILANCIA_RENUENTES.replaceAll(CONDICION_ADMON_LOCAL, ""),
                parameters,
                new VisualizaVigilanciaRenuentesMapper());
    }

    @Override
    @Propagable(catched = true)
    public List<VisualizaDocumentoRenuente> listarDocumentosRenuentes(VisualizaVigilanciaRenuentes visualizaVigilanciaRenuentes, Paginador paginador,
                                                                        AdministrativoNivelCargo administrativoNivelCargo) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        agregarParametros(parameters, visualizaVigilanciaRenuentes, administrativoNivelCargo);       
        parameters.addValue("rowInicial", paginador.getRowInicial());
        parameters.addValue("rowFinal", paginador.getRowFinal());

        
        return this.namedJDBCTemplate.query(
                administrativoNivelCargo.getNivelEmision()== NivelEmisionEnum.LOCAL? 
                            CONSULTA_DOCUMENTOS_PAGINADOS : CONSULTA_DOCUMENTOS_PAGINADOS.replaceAll(CONDICION_ADMON_LOCAL, ""),
                parameters,
                new VisualizaDocumentoRenuenteMapper());     
   
    }

   @Override
   @Propagable(catched = true)
    public Integer contarDocumentosRenuentes(VisualizaVigilanciaRenuentes visualizaVigilanciaRenuentes, AdministrativoNivelCargo administrativoNivelCargo) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        agregarParametros(parameters, visualizaVigilanciaRenuentes, administrativoNivelCargo);
        
        return this.namedJDBCTemplate.queryForObject(
                administrativoNivelCargo.getNivelEmision()== NivelEmisionEnum.LOCAL? 
                            CONSULTA_NUMERO_DOCUMENTOS : CONSULTA_NUMERO_DOCUMENTOS.replaceAll(CONDICION_ADMON_LOCAL, ""),                
                parameters, Integer.class);
    }

    @Override
    @Propagable(catched = true)
    public List<VisualizaDetalleRenuente> listarDetallesRenuentes(VisualizaDocumentoRenuente visualizaDocumentoRenuente, SituacionIcepEnum situacionIcepEnum) {
        return template.query(CONSULTA_DETALLE_RENUENTE,
                    new Object[]{
                        situacionIcepEnum.getValor(),
                        visualizaDocumentoRenuente.getNumeroControl()
                        },
                    new int[]{
                        Types.NUMERIC,
                        Types.VARCHAR},
                    new VisualizaDetalleRenuenteMapper());
    }
    @Override
    @Propagable(catched = true)
    public Integer emitirDocumentos(VisualizaVigilanciaRenuentes visualizaVigilanciaRenuentes, EstadoDocumentoEnum estadoOrigen,
                                    EstadoDocumentoEnum estadoDestino, AdministrativoNivelCargo administrativoNivelCargo) {

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        agregarParametros(parameters, visualizaVigilanciaRenuentes, administrativoNivelCargo);        
        parameters.addValue("estadoDestino", estadoDestino.getValor());
        parameters.addValue("estadoOrigen", estadoOrigen.getValor());
        String query=administrativoNivelCargo.getNivelEmision()== NivelEmisionEnum.LOCAL? 
                            EMITIR_DOCUMENTOS : EMITIR_DOCUMENTOS.replaceAll(CONDICION_ADMON_LOCAL, "");
        if (visualizaVigilanciaRenuentes.getIdTipoFirma().equals(TipoFirmaEnum.ELECTRONICA.getValor())){
            query = query.replace(SIN_FIRMA, JOIN_FIRMA);
        }
        return this.namedJDBCTemplate.update(
                query,
                parameters);
       
    }

    @Override
    @Propagable(catched = true)
    public Integer bitacoraCambioDocumentos(VisualizaVigilanciaRenuentes visualizaVigilanciaRenuentes, EstadoDocumentoEnum estadoOrigen,
                                             EstadoDocumentoEnum estadoDestino, AdministrativoNivelCargo administrativoNivelCargo) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        agregarParametros(parameters, visualizaVigilanciaRenuentes, administrativoNivelCargo);
        parameters.addValue("estadoDestino", estadoDestino.getValor());        
        parameters.addValue("estadoOrigen", estadoOrigen.getValor());      
        String query=administrativoNivelCargo.getNivelEmision()== NivelEmisionEnum.LOCAL? 
                            BITACORA_CAMBIO_ESTADO : BITACORA_CAMBIO_ESTADO.replaceAll(CONDICION_ADMON_LOCAL, "");
        if (visualizaVigilanciaRenuentes.getIdTipoFirma().equals(TipoFirmaEnum.ELECTRONICA.getValor())){
            query = query.replace(SIN_FIRMA, JOIN_FIRMA);
        }
        return this.namedJDBCTemplate.update(
                query,
                parameters);
    }    
    private void agregarParametros(MapSqlParameterSource parameters, VisualizaVigilanciaRenuentes visualizaVigilanciaRenuentes, AdministrativoNivelCargo administrativoNivelCargo){
        parameters.addValue("idfirma", visualizaVigilanciaRenuentes.getIdTipoFirma());
        parameters.addValue("idTipoDocumento", visualizaVigilanciaRenuentes.getIdTipoDocumento());
        parameters.addValue("idmedio", visualizaVigilanciaRenuentes.getIdMedioEnvio());
        parameters.addValue("fechaCarga", visualizaVigilanciaRenuentes.getFechaEmision());
        parameters.addValue("nivelEmision", administrativoNivelCargo.getNivelEmision().getIdNivelEmision());
        parameters.addValue("cargoAdmtvo", administrativoNivelCargo.getIdCargoAdministrativo());
        parameters.addValue("admonLocal", administrativoNivelCargo.getIdAdministacionLocal());
        
    }

    @Override
    public List<CadenaOriginal> listarCadenasOriginales(VisualizaVigilanciaRenuentes visualizaVigilanciaRenuentes,  AdministrativoNivelCargo administrativoNivelCargo, String nombreFuncionario, Integer rowInicial, Integer rowFinal) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        agregarParametros(parameters, visualizaVigilanciaRenuentes, administrativoNivelCargo);
        parameters.addValue("rowInicial", rowInicial);
        parameters.addValue("rowFinal", rowFinal);
        parameters.addValue("nombreFuncionario", nombreFuncionario);
        return this.namedJDBCTemplate.query(
                administrativoNivelCargo.getNivelEmision()== NivelEmisionEnum.LOCAL? 
                            CADENAS_ORIGINALES : CADENAS_ORIGINALES.replaceAll(CONDICION_ADMON_LOCAL, ""),
                parameters,
                new CadenaOriginalMapper());     
    }

    @Override
    @Propagable(catched = true)
    public Integer eliminaFirmasSinAprobar(VisualizaVigilanciaRenuentes visualizaVigilanciaRenuentes,  AdministrativoNivelCargo administrativoNivelCargo) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        agregarParametros(parameters, visualizaVigilanciaRenuentes, administrativoNivelCargo);     
        
        return this.namedJDBCTemplate.update(
                administrativoNivelCargo.getNivelEmision()== NivelEmisionEnum.LOCAL? 
                            ELIMINA_FIRMAS : ELIMINA_FIRMAS.replaceAll(CONDICION_ADMON_LOCAL, ""),
                parameters);
    }

    @Override
    @Propagable(catched = true)
    public Integer cambiaEstadoVigilancias(VisualizaVigilanciaRenuentes visualizaVigilanciaRenuentes, EstadoDocumentoEnum estadoOrigen, EstadoDocumentoEnum estadoDestino, AdministrativoNivelCargo administrativoNivelCargo) {
         MapSqlParameterSource parameters = new MapSqlParameterSource();
        agregarParametros(parameters, visualizaVigilanciaRenuentes, administrativoNivelCargo);        
        parameters.addValue("estadoDestino", estadoDestino.getValor());
        parameters.addValue("estadoOrigen", estadoOrigen.getValor());
        String query=administrativoNivelCargo.getNivelEmision()== NivelEmisionEnum.LOCAL? 
                            ACTUALIZAR_VIGILANCIA_AL : ACTUALIZAR_VIGILANCIA_AL.replaceAll(CONDICION_ADMON_LOCAL, "");
        if (visualizaVigilanciaRenuentes.getIdTipoFirma().equals(TipoFirmaEnum.ELECTRONICA.getValor())){
            query = query.replace(SIN_FIRMA, JOIN_FIRMA);
        }
        return this.namedJDBCTemplate.update(
                query,
                parameters);
    }

    @Override
    @Propagable(catched = true)
    public Integer bitacoraEmisionFuncionario(VisualizaVigilanciaRenuentes visualizaVigilanciaRenuentes, EstadoDocumentoEnum estadoOrigen, EstadoDocumentoEnum estadoDestino, AdministrativoNivelCargo administrativoNivelCargo) {
         MapSqlParameterSource parameters = new MapSqlParameterSource();
        agregarParametros(parameters, visualizaVigilanciaRenuentes, administrativoNivelCargo);
        parameters.addValue("estadoDestino", estadoDestino.getValor());        
        parameters.addValue("estadoOrigen", estadoOrigen.getValor()); 
        parameters.addValue("numeroEmpleado", administrativoNivelCargo.getNumeroEmpleado()); 
        String query=administrativoNivelCargo.getNivelEmision()== NivelEmisionEnum.LOCAL? 
                            BITACORA_EMISION_FUNCIONARIO : BITACORA_EMISION_FUNCIONARIO.replaceAll(CONDICION_ADMON_LOCAL, "");
        if (visualizaVigilanciaRenuentes.getIdTipoFirma().equals(TipoFirmaEnum.ELECTRONICA.getValor())){
            query = query.replace(SIN_FIRMA, JOIN_FIRMA);
        }
        return this.namedJDBCTemplate.update(
                query,
                parameters);
    }
    
}