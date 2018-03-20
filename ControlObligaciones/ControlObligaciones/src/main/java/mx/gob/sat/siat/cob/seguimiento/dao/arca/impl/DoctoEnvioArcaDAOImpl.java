/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.arca.impl;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import mx.gob.sat.siat.cob.seguimiento.dao.arca.DoctoEnvioArcaDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.arca.mapper.DocsAsociadosMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.arca.mapper.MultaDocumentoMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.arca.mapper.ObligacionPeriodoMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.arca.mapper.ReqAnteriorOrigenMultaAntecesorMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.arca.mapper.RequerimientoAnteriorArcaMapper;
import static mx.gob.sat.siat.cob.seguimiento.dao.arca.sql.DoctoEnviaArcaSQL.DETALLE_MULTA_ARCA;
import static mx.gob.sat.siat.cob.seguimiento.dao.arca.sql.DoctoEnviaArcaSQL.SELECT_MULTA_DOCTO;
import static mx.gob.sat.siat.cob.seguimiento.dao.arca.sql.DoctoEnviaArcaSQL.SELECT_OBLIGACION_PERIODO_CARGA_ARCHIVOS;
import static mx.gob.sat.siat.cob.seguimiento.dao.arca.sql.DoctoEnviaArcaSQL.SELECT_ORIGEN_MULTA_ANTECESOR;
import static mx.gob.sat.siat.cob.seguimiento.dao.arca.sql.DoctoEnviaArcaSQL.SELECT_ORIGEN_MULTA_ANTECESOR2;
import static mx.gob.sat.siat.cob.seguimiento.dao.arca.sql.DoctoEnviaArcaSQL.SELECT_ORIGEN_MULTA_POSTERIORES;
import static mx.gob.sat.siat.cob.seguimiento.dao.arca.sql.DoctoEnviaArcaSQL.SELECT_REQANTERIORES_COB;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.DocumentoARCA;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.ObligacionPeriodo;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.RequerimientosAnteriores;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DetalleDocumento;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
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
public class DoctoEnvioArcaDAOImpl implements DoctoEnvioArcaDAO {

    private static Logger logger = Logger.getLogger(DoctoEnvioArcaDAOImpl.class);
    @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG)
    private JdbcTemplate template;

    /**
     * @param numControl
     * @return
     */
    @Propagable(catched = true)
    @Override
    public List<ObligacionPeriodo> obtenerObligacionPeriodo(String numControl) {
        List<ObligacionPeriodo> lstObligaPeriodo = (List<ObligacionPeriodo>) this.template.query(SELECT_OBLIGACION_PERIODO_CARGA_ARCHIVOS,
                new Object[]{numControl},
                new ObligacionPeriodoMapper());

        return lstObligaPeriodo;
    }

    /**
     * @param numControles
     * @return
     */
    @Propagable(catched = true)
    @Override
    public List<RequerimientosAnteriores> obtenerReqAnteriores(Set<String> numControles) {
        StringBuilder numControlBuilder = new StringBuilder();
        Object[] controles = numControles.toArray();
        for (int i = 0; i < numControles.size(); i++) {
            if (i == 0) {
                numControlBuilder.append(" docto.numerocontrol = ").append(" '").append((String) controles[i]).append("' ");
            } else {
                numControlBuilder.append(" OR docto.numerocontrol = ").append("'")
                        .append((String) controles[i]).append("' ");
            }
        }
        String selectReqsAnteriores = SELECT_REQANTERIORES_COB;
        selectReqsAnteriores = selectReqsAnteriores.
                replace("replaceSQLReqsAnteriores", numControlBuilder.toString());
        List<RequerimientosAnteriores> lstReqAnteriores =
                (List<RequerimientosAnteriores>) this.template.query(selectReqsAnteriores,
                new RequerimientoAnteriorArcaMapper());

        return lstReqAnteriores;
    }

    /**
     *
     * @param numControl
     * @param detallesDocumento
     * @return
     */
    @Propagable(catched = true)
    @Override
    public List<ObligacionPeriodo> multaObligacionPeriodoArca(String numControl,
            List<DetalleDocumento> detallesDocumento) {

        String selectDetalleMulta = DETALLE_MULTA_ARCA;
        List<String> iceps = new ArrayList<String>();
        for (DetalleDocumento detalle : detallesDocumento) {
            iceps.add(String.valueOf(detalle.getClaveIcep()));
        }
        selectDetalleMulta = selectDetalleMulta.replace("replaceSQL", iceps.toString().replace("[", "").replace("]", ""));

        try {
            return (List<ObligacionPeriodo>) this.template.queryForObject(selectDetalleMulta,
                    new Object[]{numControl},
                    new ObligacionPeriodoMapper());
        } catch (EmptyResultDataAccessException e) {
            logger.debug("No hay resultados \n" + selectDetalleMulta);
            return null;
        }
    }

    /**
     * @param numControl
     * @param idResolucion
     * @return
     */
    @Propagable(catched = true)
    @Override
    public DocumentoARCA multaDoctoArca(String numControl, long idResolucion) {
        try {
            return this.template.queryForObject(SELECT_MULTA_DOCTO,
                    new Object[]{numControl, idResolucion},
                    new MultaDocumentoMapper());
        } catch (EmptyResultDataAccessException e) {
            logger.debug("No hay resultados\n" + SELECT_MULTA_DOCTO);
            return null;
        }

    }

    /**
     *
     * @param numControl
     * @return
     */
    @Propagable(catched = true)
    @Override
    public List<RequerimientosAnteriores> origenMultaArca(String numControl) {
        List<RequerimientosAnteriores> reqsAnteriores = (List<RequerimientosAnteriores>) this.template.query(SELECT_ORIGEN_MULTA_ANTECESOR,
                new Object[]{numControl},
                new ReqAnteriorOrigenMultaAntecesorMapper());

        return reqsAnteriores;
    }

    /**
     * Metodo para consultar documentos anteriores dado un numero de control
     *
     * @param numControl
     * @return
     */
    @Propagable(catched = true)
    @Override
    public List<RequerimientosAnteriores> origenMultaArcaAntecesores(String numControl) {
        List<RequerimientosAnteriores> reqsAnteriores = (List<RequerimientosAnteriores>) this.template.query(SELECT_ORIGEN_MULTA_ANTECESOR2,
                new Object[]{numControl},
                new int[]{Types.VARCHAR},
                new DocsAsociadosMapper());

        return reqsAnteriores;
    }

    /**
     * Metodo para consultar documentos posteriores dado un numero de control
     *
     * @param numControl
     * @return
     */
    @Propagable(catched = true)
    @Override
    public List<RequerimientosAnteriores> origenMultaArcaPosteriores(String numControl) {
        List<RequerimientosAnteriores> reqsAnteriores = (List<RequerimientosAnteriores>) this.template.query(SELECT_ORIGEN_MULTA_POSTERIORES,
                new Object[]{numControl},
                new int[]{Types.VARCHAR},
                new DocsAsociadosMapper());

        return reqsAnteriores;
    }
}
