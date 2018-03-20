package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.SgtDocumentoDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.DocumentoSQL;
import static mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.DocumentoSQL.INSERTAR_ESTADOS_DOCUMENTO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql.SgtDocumentoSQL;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Documento;
import mx.gob.sat.siat.cob.seguimiento.util.Propagable;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author root
 */
@Repository
public class SgtDocumentoDAOImpl implements SgtDocumentoDAO, DocumentoSQL {

    private Logger log = Logger.getLogger(SgtDocumentoDAOImpl.class.getName());
    @Autowired(required = false)
    @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG)
    private JdbcTemplate template;

    /**
     * @param documento
     * @return
     */
    @Override
    @Propagable
    public int guardaSgtDocumento(Documento documento) {
        int respuesta = 0;
        log.debug("Guardando en la tabla sgtdocumento");
        log.debug(INSERTAR_ESTADOS_DOCUMENTO);
        respuesta = template.update(INSERTAR_ESTADOS_DOCUMENTO,
                new Object[]{
            documento.getNumeroControl(),
            documento.getEsUltimoEstado()},
                new int[]{
            Types.VARCHAR, Types.DECIMAL});
        return respuesta;
    }

    /**
     * proceso de insertar en batch
     *
     * @param documentos
     * @return
     */
    @Override
    @Propagable
    public int guardaSgtDocumentoBatch(final List<Documento> documentos) {
        int respuesta = 0;
        log.debug("Guardando en la tabla sgtdocumento");
        log.debug(INSERTAR_ESTADOS_DOCUMENTO);
        template.batchUpdate(INSERTAR_ESTADOS_DOCUMENTO, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Documento documento = documentos.get(i);
                ps.setString(1, documento.getNumeroControl());
                ps.setLong(2, documento.getEsUltimoEstado());
            }

            @Override
            public int getBatchSize() {
                return documentos.size();
            }
        });
        return respuesta;
    }

    @Override
    @Propagable
    public void actualizarBitacoraEdoDocumento(Documento documento) {
        template.update(SgtDocumentoSQL.INSERT,
                new Object[]{documento.getNumeroControl(),
            documento.getUltimoEstado()},
                new int[]{Types.VARCHAR,
            Types.NUMERIC});
    }

    /**
     *
     * @return
     */
    public Logger getLog() {
        return log;
    }

    /**
     *
     * @param log
     */
    public void setLog(Logger log) {
        this.log = log;
    }

    /**
     *
     * @return
     */
    public JdbcTemplate getTemplate() {
        return template;
    }

    /**
     *
     * @param template
     */
    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }
}
