/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.dbd2.impl;

import java.sql.Types;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import mx.gob.sat.siat.cob.seguimiento.dao.dbd2.HistoricoCumplimientoDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.dbd2.mapper.HistoricoCumplimientoBaseMapper;
import mx.gob.sat.siat.cob.seguimiento.dao.dbd2.sql.HistoricoCumplimientoSQL;
import mx.gob.sat.siat.cob.seguimiento.dto.db2.HistoricoCumplimiento;
import mx.gob.sat.siat.cob.seguimiento.dto.db2.Paginador;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DetalleDocumento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DocumentoAprobar;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import mx.gob.sat.siat.cob.seguimiento.util.Utilerias;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author rodrigo
 */
@Transactional
@Repository
public class HistoricoCumplimientoDAOImpl implements HistoricoCumplimientoDAO {

    @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_DB2)
    private JdbcTemplate template;
    private final Logger log = Logger.getLogger(HistoricoCumplimientoDAOImpl.class);
    private static boolean flgImprimeConsultas = false;

    public static boolean isFlgImprimeConsultas() {
        return flgImprimeConsultas;
    }

    public static void setFlgImprimeConsultas(boolean flgImprimeConsultas) {
        HistoricoCumplimientoDAOImpl.flgImprimeConsultas = flgImprimeConsultas;
    }

    /**
     *
     * @param paginador
     * @param fechaHistorico
     * @return
     */
    @Override
    public List<HistoricoCumplimiento> consultarCumplimientos(Paginador paginador, Date fechaHistorico) {
        return template.query(HistoricoCumplimientoSQL.CONSULTA_CUMPLIMIENTO,
                new Object[]{fechaHistorico,
                    paginador.getRowInicial(),
                    paginador.getRowFinal()},
                new int[]{Types.DATE, Types.NUMERIC, Types.NUMERIC},
                new HistoricoCumplimientoBaseMapper(false));
    }

    /**
     *
     * @param fechaHistorico
     * @return
     */
    @Override
    public long consultarTamanio(Date fechaHistorico) {
        return template.queryForObject(HistoricoCumplimientoSQL.CONTAR_CUMPLIMIENTOS,
                new Object[]{fechaHistorico},
                new int[]{Types.DATE}, Long.class);
    }

    @Override
    @Propagable(catched = true)
    public List<HistoricoCumplimiento> buscarCumplimientos(List<DocumentoAprobar> documentos) {
        StringBuilder padron = new StringBuilder();
        for (DocumentoAprobar documentoAprobar : documentos) {
            padron.append("(PADRON.C_IDC_ICDOENN1 =");
            padron.append(documentoAprobar.getBoid());
            padron.append(" AND ");
            padron.append(Utilerias.formatearParaSQLInNumeric(generarClaves(documentoAprobar.getDetalles()), "PADRON.C_ICE_ICEP1"));
            padron.append(") OR ");
        }
        padron.delete(padron.length() - 3, padron.length());
        String query = VALIDAR_CUMPLIMIENTOS.replace(IN_BOID_ICEP_PADRON, padron.toString());
        StringBuilder cumplimientos = new StringBuilder();
        cumplimientos.append("(");
        for (DocumentoAprobar documentoAprobar : documentos) {
            cumplimientos.append("(HISTORICOCUMP.C_IDE_ICDOENN1 =");
            cumplimientos.append(documentoAprobar.getBoid());
            cumplimientos.append(" AND ");
            cumplimientos.append(Utilerias.formatearParaSQLInNumeric(generarClaves(documentoAprobar.getDetalles()), "HISTORICOCUMP.C_ICE_ICEP1"));
            cumplimientos.append(") OR ");
        }
        cumplimientos.delete(cumplimientos.length() - 3, cumplimientos.length());
        cumplimientos.append(")");
        query = query.replace(IN_BOID_ICEP_CUMPLIMIENTO, cumplimientos.toString());
        if (isFlgImprimeConsultas()) {
            log.error("[ " + query + " ]");
        }
        return template.query(query, new HistoricoCumplimientoBaseMapper(true));
    }

    private Set<String> generarClaves(List<DetalleDocumento> detalles) {
        Set<String> iceps = new HashSet<String>();
        for (DetalleDocumento detalleDocumento : detalles) {
            iceps.add(detalleDocumento.getClaveIcep().toString());
        }
        return iceps;
    }

    @Override
    public List<HistoricoCumplimiento> buscarCumplimientos(List<DocumentoAprobar> documentos, List<Integer> tiposDeclaracion) {

        StringBuilder tipoDeclaracion = new StringBuilder();

        StringBuilder padron = new StringBuilder();
        for (DocumentoAprobar documentoAprobar : documentos) {
            padron.append("(PADRON.C_IDC_ICDOENN1 =");
            padron.append(documentoAprobar.getBoid());
            padron.append(" AND ");
            padron.append(Utilerias.formatearParaSQLInNumeric(generarClaves(documentoAprobar.getDetalles()), "PADRON.C_ICE_ICEP1"));
            padron.append(") OR ");
        }
        padron.delete(padron.length() - 3, padron.length());
        String query = VALIDAR_CUMPLIMIENTOS_TIPOSDECLARACION.replace(IN_BOID_ICEP_PADRON, padron.toString());
        StringBuilder cumplimientos = new StringBuilder();
        cumplimientos.append("(");
        for (DocumentoAprobar documentoAprobar : documentos) {
            cumplimientos.append("(HISTORICOCUMP.C_IDE_ICDOENN1 =");
            cumplimientos.append(documentoAprobar.getBoid());
            cumplimientos.append(" AND ");
            cumplimientos.append(Utilerias.formatearParaSQLInNumeric(generarClaves(documentoAprobar.getDetalles()), "HISTORICOCUMP.C_ICE_ICEP1"));
            cumplimientos.append(") OR ");
        }
        cumplimientos.delete(cumplimientos.length() - 3, cumplimientos.length());
        cumplimientos.append(")");
        query = query.replace(IN_BOID_ICEP_CUMPLIMIENTO, cumplimientos.toString());

        if (tiposDeclaracion != null && tiposDeclaracion.size() > 0) {
            tipoDeclaracion.append(" HISTORICOCUMP.C_COB_TDIEPCO1 IN (");
            for (int i = 0; i < tiposDeclaracion.size(); i++) {
                if (i < 1) {
                    tipoDeclaracion.append(tiposDeclaracion.get(i));
                } else {
                    tipoDeclaracion.append(",");
                    tipoDeclaracion.append(tiposDeclaracion.get(i));
                }
            }
            tipoDeclaracion.append(") AND ");
        } else {
            tipoDeclaracion.append("");
        }

        query = query.replace(TIPO_DECLARACION, tipoDeclaracion.toString());
        if (isFlgImprimeConsultas()) {
            log.error("[ " + query + " ]");
        }
        return template.query(query, new HistoricoCumplimientoBaseMapper(true));
    }
}
