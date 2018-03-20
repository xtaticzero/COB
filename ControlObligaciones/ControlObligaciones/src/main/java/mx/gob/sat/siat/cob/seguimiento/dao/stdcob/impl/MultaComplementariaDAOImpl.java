/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl;

import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.MultaComplementariaDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Documento;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import org.springframework.stereotype.Repository;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Map;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.DocumentoAprobarIcepMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.DocumentoMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper.GrupoAfectacionCumpMapper;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.MultaComplementariaSQL.AFECTAR_DETALLES_CON_COMPLEMENTARIA;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.MultaComplementariaSQL.DOCUMENTOS_CON_COMPLEMENTARIA;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.MultaComplementariaSQL.DOCUMENTOS_CON_COMPLEMENTARIA_VIGILANCIA_LOCAL;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.MultaComplementariaSQL.ACTUALIZA_DETALLE_DOCUMENTOS;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.MultaComplementariaSQL.ACTUALIZAR_DETALLES_COMPLEMENTARIA;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.MultaComplementariaSQL.CONTEO_AGRUPADO_DETALLES_COMPLEMENTARIA;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.MultaComplementariaSQL.DETALLES_PARA_COMPLEMENTARIA;
import mx.gob.sat.siat.cob.seguimiento.dto.db2.Paginador;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DetalleDocumento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DocumentoAprobar;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.GrupoAfectacionCumpDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoMedioEnvioEnum;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author root
 */
@Repository
public class MultaComplementariaDAOImpl implements MultaComplementariaDAO {
    
    private final Logger log = Logger.getLogger(MultaComplementariaDAOImpl.class);

    @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG)
    private JdbcTemplate template;
    @Autowired(required = false)
    @Qualifier("namedTemplateSTDCOB")
    private NamedParameterJdbcTemplate namedJDBCTemplate;

    @Override
    @Propagable(catched = true)
    public Integer afectarDetallesConComplementaria(Map condiciones) {
        return template.update(AFECTAR_DETALLES_CON_COMPLEMENTARIA,
                new Object[]{
            condiciones.get("anuladoPadron"),
            condiciones.get("multacomplementaria"),
            condiciones.get("multaExtemporaneidad"),
            condiciones.get("canceladoAutoridad"),
            condiciones.get("mesesMaximo"),
            condiciones.get("tipoDeclaracionPrevia"),
            condiciones.get("tipoDeclaracionCumplimiento")},
                new int[]{Types.DECIMAL,
            Types.DECIMAL,
            Types.DECIMAL,
            Types.DECIMAL,
            Types.DECIMAL,
            Types.DECIMAL,
            Types.DECIMAL});
    }

    @Override
    @Propagable(catched = true)
    public List<Documento> documentosMultaComplementaria(Integer canceladoAutoridad, TipoMedioEnvioEnum medioEnvioDescartar) {
        return (List<Documento>) template.query(
                DOCUMENTOS_CON_COMPLEMENTARIA,
                new Object[]{medioEnvioDescartar.getValor(),canceladoAutoridad},
                new int[]{Types.DECIMAL, Types.DECIMAL},
                new DocumentoMapper());

    }
    @Override
    @Propagable(catched = true)
    public List<Documento> documentosMultaComplementaria(Integer canceladoAutoridad, String idVigilancia, String idLocal) {
         MapSqlParameterSource parameters = new MapSqlParameterSource();
         parameters.addValue("idVigilancia", idVigilancia);
         parameters.addValue("idAdmonlocal", idLocal);
         parameters.addValue("ultimoEstado", canceladoAutoridad);
        List<Documento> lstDocumento = this.namedJDBCTemplate.query(
                DOCUMENTOS_CON_COMPLEMENTARIA_VIGILANCIA_LOCAL,
                parameters,
                new DocumentoMapper());
        return lstDocumento;
    }

    @Override
    @Propagable
    public Integer marcarIcepsMultaComplementaria(List<Documento> documentos) {

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
            rs = this.namedJDBCTemplate.update(ACTUALIZA_DETALLE_DOCUMENTOS, parameters);
            if (rs == 0) {
                return rs;
            }
        } while (ii < documentos.size());
        return rs;
    }

    @Override
    public Integer actualizarDetalleConComplementaria(Documento documento) {
        int i = 0;
        for (DetalleDocumento det : documento.getDetalles()) {       
             log.info("Se actualizara detalle complementaria con los siguientes datos \n" +
                                     "Fecha de cumplimiento "  + det.getFechaCumplimiento() + "\n" +
                                     "Id cumplimiento "  +   det.getIdCumplimiento() + "\n" +
                                     "Numero de control "  +   documento.getNumeroControl() + "\n" +
                                     "Clave icep "  +   det.getClaveIcep() + "\n" );
            i += template.update(ACTUALIZAR_DETALLES_COMPLEMENTARIA, new Object[]{
            det.getFechaCumplimiento(),
            (det.getIdCumplimiento()!=null ? det.getIdCumplimiento().toString():null),
            documento.getNumeroControl(),
            det.getClaveIcep()},
                new int[]{Types.DATE,
            Types.VARCHAR,
            Types.VARCHAR,
            Types.DECIMAL});
        }
        return i;
    }

    @Override
    @Propagable(catched = true)
    @Transactional(readOnly = true)
    public List<GrupoAfectacionCumpDTO> obtenerDetallesComplementariaGrupo(Map condiciones) {
         MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
         
         mapSqlParameterSource.addValue("situacionIcep", condiciones.get("anuladoPadron"));
         mapSqlParameterSource.addValue("tieneMultaComplementaria", condiciones.get("multacomplementaria"));
         mapSqlParameterSource.addValue("tieneMultaExtemporaneidad", condiciones.get("multaExtemporaneidad"));
         mapSqlParameterSource.addValue("ultimoEstado", condiciones.get("canceladoAutoridad"));
         mapSqlParameterSource.addValue("declaracionNormal", condiciones.get("tipoDeclaracion"));
        return this.namedJDBCTemplate.query(
                CONTEO_AGRUPADO_DETALLES_COMPLEMENTARIA,
                mapSqlParameterSource,
                new GrupoAfectacionCumpMapper());
    }

    @Override
    @Transactional(readOnly = true)
     public List<DocumentoAprobar> listarDocumentosIcep(GrupoAfectacionCumpDTO grupoAfectacionCumpDTO, Paginador paginador, Map condiciones) {
        List<List<DocumentoAprobar>> lst;
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("situacionIcep", condiciones.get("anuladoPadron"));
        mapSqlParameterSource.addValue("tieneMultaComplementaria", condiciones.get("multacomplementaria"));
        mapSqlParameterSource.addValue("tieneMultaExtemporaneidad", condiciones.get("multaExtemporaneidad"));
        mapSqlParameterSource.addValue("ultimoEstado", condiciones.get("canceladoAutoridad"));
        mapSqlParameterSource.addValue("declaracionNormal", condiciones.get("tipoDeclaracion"));
        mapSqlParameterSource.addValue("vigilancia", grupoAfectacionCumpDTO.getVigilancia());
        mapSqlParameterSource.addValue("admonlocal", grupoAfectacionCumpDTO.getIdAdmonLocal());
        mapSqlParameterSource.addValue("inicio", paginador.getRowInicial());
        mapSqlParameterSource.addValue("fin", paginador.getRowFinal());

        lst = (List<List<DocumentoAprobar>>) this.namedJDBCTemplate.query(
                DETALLES_PARA_COMPLEMENTARIA,
                mapSqlParameterSource,
                new DocumentoAprobarIcepMapper());
        if (lst != null && lst.size() > 0) {
            return (List<DocumentoAprobar>) lst.get(0);
        } else {
            return null;
        }
    }

    
}
