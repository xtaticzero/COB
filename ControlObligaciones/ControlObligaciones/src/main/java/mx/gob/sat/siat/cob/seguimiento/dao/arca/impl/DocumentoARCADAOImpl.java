/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.arca.impl;

import java.sql.Types;
import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dao.arca.DocumentoARCADAO;
import mx.gob.sat.siat.cob.seguimiento.dao.arca.mapper.DocumentoArcaMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.arca.mapper.EdoDoctoARCAMapper;
import static mx.gob.sat.siat.cob.seguimiento.dao.arca.sql.DocumentoARCASQL.*;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.DocumentoARCA;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.enums.EstadoDocArcaEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.enums.EstadoMultaResolucionArca;
import mx.gob.sat.siat.cob.seguimiento.exception.ARCADAOExcepcion;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import mx.gob.sat.siat.cob.seguimiento.util.Utilerias;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Juan
 */
@Repository
public class DocumentoARCADAOImpl implements DocumentoARCADAO {

    private Logger log = Logger.getLogger(DocumentoARCADAOImpl.class);
    @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_ARCA)
    private JdbcTemplate template;

    @Propagable(catched = true)
    @Override
    public Integer insert(List<DocumentoARCA> doctos) {
        Integer registro = null;
        for (DocumentoARCA docto : doctos) {
            Double importe = null;
            if (!Utilerias.isVacio(docto.getImporte())) {
                importe = Double.parseDouble(docto.getImporte());
            }
            registro = template.update(INSERT_T_DOCUMENTO_ALL, new Object[]{
                docto.getIdAlsc(),
                docto.getIdResolucion(),
                docto.getContribuyente(),
                docto.getRfc(),
                docto.getCurp(),
                docto.getDescripcionDireccion(),
                docto.getCodPostal(),
                docto.getCrh(),
                docto.getResolucion(),
                importe,
                docto.getIdDocumentoPlantilla(),
                docto.getIdEstadoDocumento(),
                docto.getIdMedioEnvio(),
                docto.getIdTipoDocumento(),
                docto.getId(),
                docto.getIdTipoPersona(),
                docto.getInfromacionQR()},
                    new int[]{
                Types.INTEGER,
                Types.INTEGER,
                Types.VARCHAR,
                Types.VARCHAR,
                Types.VARCHAR,
                Types.VARCHAR,
                Types.VARCHAR,
                Types.INTEGER,
                Types.VARCHAR,
                Types.DECIMAL,
                Types.INTEGER,
                Types.INTEGER,
                Types.INTEGER,
                Types.INTEGER,
                Types.VARCHAR,
                Types.VARCHAR,
                Types.VARCHAR
            });
        }
        return registro;
    }

    /**
     * Metodo que actualiza el estado del documento
     *
     * @param docto
     */
    @Propagable
    @Override
    public Integer actualizaEstadoDocto(DocumentoARCA docto) {

        return template.update(ACTUALIZA_ESTADO, new Object[]{
            docto.getIdEstadoDocumento(),
            docto.getId()},
                new int[]{
            Types.DECIMAL,
            Types.VARCHAR});

    }

    /**
     *
     * @param documento
     */
    @Propagable
    @Override
    public void update(DocumentoARCA documento) {
        template.update(UPDATE, new Object[]{
            documento.getIdTipoDocumento(),
            documento.getIdMedioEnvio(),
            documento.getIdEstadoDocumento(),
            documento.getIdDocumentoPlantilla(),
            documento.getIdAlsc(),
            documento.getContribuyente(),
            documento.getRfc(),
            documento.getCurp(),
            documento.getDescripcionDireccion(),
            documento.getCrh(),
            documento.getResolucion(),
            documento.getImporte(),
            documento.getId()},
                new int[]{
            Types.INTEGER,
            Types.TINYINT,
            Types.INTEGER,
            Types.INTEGER,
            Types.TINYINT,
            Types.VARCHAR,
            Types.VARCHAR,
            Types.VARCHAR,
            Types.VARCHAR,
            Types.INTEGER,
            Types.VARCHAR,
            Types.DECIMAL,
            Types.VARCHAR
        });
    }

    /**
     * Metodo que cambia el estado de documento mediante el numero de control
     *
     * @param id
     * @return
     */
    @Propagable(catched = true)
    @Override
    public Integer cancelaDocto(String id) {
        return template.update(ACTUALIZA_ESTADO, new Object[]{
            EstadoDocArcaEnum.ARCA_CANCELADO_POR_SIAT.getIdEdoDoc(),
            id},
                new int[]{
            Types.DECIMAL,
            Types.VARCHAR});
    }

    /**
     * Metodo que obtiene el estado de un documento
     *
     * @param id
     * @return
     */
    @Propagable(catched = true)
    @Override
    public Integer getEstadoDocumento(String id) {
        Integer edoDoc = null;
        List<Integer> lstEstados = template.query(GET_ESTADO_DOCUMENTO, new Object[]{id}, new int[]{Types.VARCHAR}, new EdoDoctoARCAMapper());
        if (lstEstados != null && (lstEstados.size() > 0)) {
            edoDoc = lstEstados.get(0);
        } else {
            return -1;
        }
        return edoDoc;
    }

    @Propagable(catched = true)
    @Override
    public DocumentoARCA consultarDocumentoXId(String id) {
        try {
            return (DocumentoARCA) template.queryForObject(SELECT_X_ID_DOCUMENTO, new Object[]{id}, new DocumentoArcaMapper());
        } catch (EmptyResultDataAccessException e) {
            log.debug(e);
            return null;
        }
    }

    @Propagable(catched = true)
    @Override
    public Integer eliminarDocumentoEnCascada(String id) {
        Integer afectados = template.update(DELETE_T_OBLIGACION_X_ID, new Object[]{id});
        afectados += template.update(DELETE_T_PERIODO_X_ID, new Object[]{id});
        afectados += template.update(DELETE_T_DOCUMENTO_X_ID, new Object[]{id});

        return afectados;
    }

    @Propagable(catched = true)
    @Override
    public Integer eliminarRequerimientoAnterior(String id) {
        return template.update(DELETE_T_REQ_ANTERIOR_X_ID, new Object[]{id});
    }

    @Propagable(catched = true)
    @Override
    public Integer eliminarDocumento(String id) {
        return template.update(DELETE_T_DOCUMENTO_X_ID, new Object[]{id});

    }

    @Propagable(catched = true)
    @Override
    public Integer cancelarMultaSIR(String numeroResolucion) {
        return template.update(CANCELAR_MULTA_SIR, new Object[]{EstadoMultaResolucionArca.CANCELACION.getId(), numeroResolucion,
            EstadoMultaResolucionArca.CANCELACION.getId(), EstadoMultaResolucionArca.CANCELACION_APLICADA.getId()},
                new int[]{Types.DECIMAL, Types.VARCHAR, Types.DECIMAL, Types.DECIMAL});
    }

    @Propagable(catched = true)
    @Override
    public Integer eliminarDocumentoResolucion(String id) {
        return template.update(DELETE_T_DOCUMENTO_RESOLUCION_X_ID, new Object[]{id});
    }

    @Propagable
    @Override
    public Integer consultarDocumentoPorIdVigilancia(Long idVigilancia, Integer idAlsc, String fechaenvio) throws ARCADAOExcepcion {
        try {
            return (Integer) template.queryForObject(SELECT_X_ID_VIGILANCIA,
                    new Object[]{idVigilancia, idAlsc, fechaenvio}, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            log.debug(e);
            return null;
        }
    }

    @Propagable
    @Override
    public Integer consultarDocumentoResolucionPorIdVigilancia(Long idVigilancia, Integer idAlsc, String fechaenvio) throws ARCADAOExcepcion {
        try {
            return (Integer) template.queryForObject(SELECT_RESOLUCIONES_X_ID_VIGILANCIA,
                    new Object[]{idVigilancia, idAlsc, fechaenvio}, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            log.debug(e);
            return null;
        }
    }

    @Override
    public Integer deleteDocumentoPorIdVigilancia(Long idVigilancia, Integer idAlsc, String fechaMonitor) throws ARCADAOExcepcion {
        return template.update(DELETE_DOCUMENTO, new Object[]{
            idVigilancia,
            idAlsc,
            fechaMonitor},
                new int[]{
            Types.INTEGER,
            Types.INTEGER,
            Types.VARCHAR
        });
    }

    @Override
    public Integer deleteDocumentoResolucionPorIdVigilancia(Long idVigilancia, Integer idAlsc, String fechaMonitor) throws ARCADAOExcepcion {
        return template.update(DELETE_RESOLUCION, new Object[]{
            idVigilancia,
            idAlsc, fechaMonitor},
                new int[]{
            Types.INTEGER,
            Types.INTEGER,
            Types.VARCHAR
        });
    }
}
